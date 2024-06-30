import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONObject;

public class Main {

    private static final String API_KEY = "0d75aff3ce2b27417a9a33be"; // API Key proporcionada

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);

        while (true) {
            System.out.println("CONVERTIDOR DE DIVISAS");
            System.out.println("1 - Pesos Argentinos a dólares");
            System.out.println("2 - Guaraníes a dólares");
            System.out.println("3 - Reales a dólares");
            System.out.println("4 - Salir");
            System.out.print("Ingrese una opción: ");

            char opcion = leer.next().charAt(0);

            switch (opcion) {
                case '1':
                    convertir("ARS", "USD");
                    break;
                case '2':
                    convertir("PYG", "USD");
                    break;
                case '3':
                    convertir("BRL", "USD");
                    break;
                case '4':
                    System.out.println("Saliendo del programa.");
                    System.exit(0);
                default:
                    System.out.println("Opción incorrecta, por favor ingrese una opción válida.");
                    break;
            }
        }
    }

    static void convertir(String monedaOrigen, String monedaDestino) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + monedaOrigen))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar el código de respuesta
            if (response.statusCode() == 200) {
                // Procesar la respuesta JSON para obtener la tasa de cambio
                double valorDolar = obtenerValorDolar(response.body(), monedaDestino);
                if (valorDolar != -1) {
                    System.out.printf("Ingrese la cantidad de %s: ", monedaOrigen);
                    Scanner leer = new Scanner(System.in);
                    double cantidadDeMoneda = leer.nextDouble();
                    double dolares = cantidadDeMoneda * valorDolar;
                    dolares = (double) Math.round(dolares * 100d) / 100;
                    System.out.println("________________________________________________________");
                    System.out.printf("| Tienes $%.2f Dolares |\n", dolares);
                    System.out.println("________________________________________________________");
                } else {
                    System.out.println("No se encontró la tasa de cambio para la moneda destino.");
                }
            } else {
                System.out.println("Error al conectar con la API. Código de estado: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error al realizar la solicitud HTTP: " + e.getMessage());
        }
    }

    static double obtenerValorDolar(String responseBody, String monedaDestino) {
        // Parsear la respuesta JSON para obtener la tasa de cambio
        try {
            JSONObject json = new JSONObject(responseBody);
            JSONObject rates = json.getJSONObject("conversion_rates");
            return rates.getDouble(monedaDestino);
        } catch (Exception e) {
            System.err.println("Error al procesar la respuesta JSON: " + e.getMessage());
            return -1;
        }
    }
}