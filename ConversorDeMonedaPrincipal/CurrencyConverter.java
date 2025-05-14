package ConversorDeMonedaPrincipal;

public class CurrencyConverter {
    private final ExchangeRateService rateService;

    public CurrencyConverter() {
        this.rateService = new ExchangeRateService();
    }

    public double convertir(String origen, String destino, double cantidad) throws ConversionException {
        double tasa = rateService.obtenerTasa(origen, destino);
        return cantidad * tasa;
    }
}
