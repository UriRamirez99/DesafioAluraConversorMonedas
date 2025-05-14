package ConversorDeMonedaPrincipal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateService {
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/0549ead1c0045c281cee4780/latest/";


    public double obtenerTasa(String origen, String destino) throws ConversionException {
        String url = API_URL + origen;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            if (!json.has("result") || !json.get("result").getAsString().equals("success")) {
                throw new ConversionException("Error en la respuesta de la API: " + json);
            }

            JsonObject rates = json.getAsJsonObject("conversion_rates");

            if (rates == null || !rates.has(destino)) {
                throw new ConversionException("Moneda de destino no encontrada en la respuesta de la API");
            }

            return rates.get(destino).getAsDouble();

        } catch (IOException | InterruptedException e) {
            throw new ConversionException("Error al obtener datos desde la API: " + e.getMessage());
        }
    }
}
