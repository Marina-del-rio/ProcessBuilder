import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        int option = 0;

        while(option!=5){
            menu();
            option = sc.nextInt();

            switch(option){
                case 1 -> {
                    ejecutarComando();
                }
                case 2 -> {
                    lanzarAplicacion();
                }
                case 3 ->{
                    comandoConRedireccion();
                }
                case 4 -> {
                    comandoconEntorno();
                }
                case 5 ->{
                    System.out.println("Vuelva pronto");
                }
                default -> {
                    System.out.println("Opción no válida");
                }
            }
        }
    }
    private static void ejecutarComando() {
        System.out.println("Introduce un comando a ejecutar");
        String comando = sc.nextLine();
        try {
            ProcessBuilder pb = new ProcessBuilder(comando);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("El proceso ha finalizado" + exitCode);

        } catch (IOException e) {
            System.out.println("Error al ejecutar el comando: " + e.getMessage());
        }catch (InterruptedException e) {
        System.out.println("La espera fue interrumpida: " + e.getMessage());
        Thread.currentThread().interrupt();
        }
    }

    private static void lanzarAplicacion() {
    }

    private static void comandoConRedireccion(){

    }
    private static void comandoconEntorno(){

    }

    /**
     * Muestra el menú principal de la aplicación de gestión de procesos.
     * <p>
     * Este menú ofrece al usuario las diferentes opciones disponibles:
     * <ul>
     *     <li>1. Ejecutar comando</li>
     *     <li>2. Lanzar aplicación</li>
     *     <li>3. Ejecutar comando con redirección</li>
     *     <li>4. Ejecutar comando con variables de entorno</li>
     *     <li>5. Salir</li>
     * </ul>
     *
     * @return una cadena de texto con el menú formateado que se mostrará al usuario.
     */
    private static String menu(){
        return "\n=== Gestión de Procesos con ProcessBuilder === \n" +
                "1. Ejecutar comando\n"+
                "2. Lanzar aplicacion\n"+
                "3. Ejecutar comando con redirección\n"+
                "4. Ejecutar comando con variables de entorno\n"+
                "5. Salir\n"+
                "Selecciona una opcion";
    }
}