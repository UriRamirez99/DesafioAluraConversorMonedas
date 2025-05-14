package ConversorDeMonedaPrincipal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// Clase encargada de conectarse a la API de ExchangeRate y obtener la tasa de conversión
public class ExchangeRateService {
    // URL base de la API. NOTA: No debe incluir la moneda base aún
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/0549ead1c0045c281cee4780/latest/";

    /*
     * Obtiene la tasa de conversión entre dos monedas.
     * @param origen Moneda de origen (por ejemplo "USD")
     * @param destino Moneda de destino (por ejemplo "ARS")
     * @return La tasa de cambio como double
     * @throws ConversionException Si ocurre un error en la conexión o en los datos
     */
    public double obtenerTasa(String origen, String destino) throws ConversionException {
        // Construimos la URL final usando la moneda de origen
        String url = API_URL + origen;

        // Creamos el cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Construimos el request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            // Enviamos el request y recibimos la respuesta como String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Mostramos el cuerpo de la respuesta para depuración (opcional)
            // System.out.println(response.body());

            // Parseamos el JSON usando Gson
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            // Validamos que la respuesta indique "success"
            if (!json.has("result") || !json.get("result").getAsString().equals("success")) {
                throw new ConversionException("Error en la respuesta de la API: " + json);
            }

            // Obtenemos el objeto de tasas de conversión
            JsonObject rates = json.getAsJsonObject("conversion_rates");

            // Validamos que contenga la moneda destino
            if (rates == null || !rates.has(destino)) {
                throw new ConversionException("Moneda de destino no encontrada en la respuesta de la API");
            }

            // Retornamos la tasa de cambio como double
            return rates.get(destino).getAsDouble();

        } catch (IOException | InterruptedException e) {
            // Capturamos errores de conexión o interrupciones
            throw new ConversionException("Error al obtener datos desde la API: " + e.getMessage());
        }
    }
}
