import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EXTERNA:
        while (true) {
            System.out.println("CONVERTIDOR DE DIVISAS");
            System.out.println("1 - Pesos Argentinos a dolares\n" +
                    "2 - Guaraníes a dolares\n" +
                    "3 - Reales a dolares\n" +
                    "4 - Salir");
            System.out.print("Ingrese una opción");

            Scanner leer = new Scanner(System.in);
            char opcion = leer.next().charAt(0);

            switch (opcion){
                case '1' :
                    convertir(911.04, "Pesos Argentinos");
                    break;
                case '2' :
                    convertir(7538.91,  "Guaraníes");
                    break;
                case '3' :
                    convertir(5.50, "Reales");
                    break;
                case '4' :
                    System.out.println("CERRAR");
                    break EXTERNA;
                default:
                    System.out.println("OPCIÓN INCORRECTA");
                    break;
            }
        }

    }

    static void convertir(double valorDolar, String pais){
        Scanner leer = new Scanner(System.in);
        System.out.printf("Ingrese la cantidad de pesos argentinos %s ", pais);
        double cantidadDeMoneda = leer.nextDouble();

        double dolares = cantidadDeMoneda / valorDolar;

        dolares = (double) Math.round(dolares *  100d) / 100;

        System.out.println("________________________________________________________");
        System.out.println("|               Tienes $" + dolares+ "Dolares          |");
        System.out.println("________________________________________________________");
    }
}
