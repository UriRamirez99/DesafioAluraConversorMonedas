package ConversorDeMonedaPrincipal;


import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CurrencyConverter converter = new CurrencyConverter();

        while (true) {
            System.out.println("\n===== Conversor de Monedas =====");
            System.out.println("1. Convertir USD a ARS");
            System.out.println("2. Convertir ARS a USD");
            System.out.println("3. Convertir USD a EUR");
            System.out.println("4. Convertir EUR a USD");
            System.out.println("5. Convertir USD a BRL");
            System.out.println("6. Convertir BRL a USD");
            System.out.println("7. Salir");
            System.out.println("Elija una opcion: ");

            int opcion = scanner.nextInt();

            if (opcion == 7) {
                System.out.println("Saliendo del conversor. ¡Hasta Pronto!");
                break;
            }


            System.out.println("Ingrese la cantidad que desea convertir: ");
            double cantidad = scanner.nextDouble();

            String origen = "", destino = "";

            switch (opcion) {
                case 1 -> {
                    origen = "USD";
                    destino = "ARS";
                }
                case 2 -> {
                    origen = "ARS";
                    destino = "USD";
                }
                case 3 -> {
                    origen = "USD";
                    destino = "EUR";
                }
                case 4 -> {
                    origen = "EUR";
                    destino = "USD";
                }
                case 5 -> {
                    origen = "USD";
                    destino = "BRL";
                }
                case 6 -> {
                    origen = "BRL";
                    destino = "USD";
                }
                default -> {
                    System.out.println("Opcion invalida. Intente nuevamente por favor");
                    continue;
                }
            }

            try  {
                double resultado = converter.convertir(origen, destino, cantidad);
                System.out.printf("%.2f %s equivalen a %.2f %s%n", cantidad, origen, resultado, destino);
            } catch (ConversionException e) {
                System.out.println("Error: "+e.getMessage());
            }

        }

        scanner.close();
    }
}
