import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.lang.ProcessBuilder;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        int option = 0;

        while(option!=5){
            System.out.println(menu());
            option = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch(option){
                case 1 -> ejecutarComando();
                case 2 -> lanzarAplicacion();
                case 3 -> comandoConRedireccion();
                case 4 -> comandoconEntorno();
                case 5 -> System.out.println("Vuelva pronto");
                default -> System.out.println("Opción no válida");
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
            System.out.println("El proceso ha finalizado con código de salida: " + exitCode);

        } catch (IOException e) {
            System.out.println("Error al ejecutar el comando: " + e.getMessage());
        }catch (InterruptedException e) {
            System.out.println("La espera fue interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static void lanzarAplicacion() {
        String os = System.getProperty("os.name").toLowerCase();
        String command;

        if (os.contains("win")) {
            command = "notepad.exe";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            command = "gedit"; // A common Linux GUI text editor
        } else {
            System.out.println("Sistema operativo no compatible para esta función.");
            return;
        }

        System.out.println("Sistema operativo detectado: " + os);
        System.out.println("Intentando lanzar el editor de texto por defecto: " + command);
        System.out.println("(Si el programa no se abre, puede que no esté instalado o en su PATH)");

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            Process proceso = pb.start();
            int exitCode = proceso.waitFor();
            System.out.println("La aplicación '" + command + "' ha finalizado con código de salida: " + exitCode);

        } catch (IOException e) {
            System.err.println("Error al lanzar la aplicación '" + command + "'.");
            System.err.println("Causa: " + e.getMessage());
            System.err.println("Por favor, asegúrese de que el programa está instalado y accesible en el PATH del sistema.");
        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido.");
            e.printStackTrace();
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    private static void comandoConRedireccion(){
        System.out.println("Función no implementada.");
    }
    private static void comandoconEntorno(){
        System.out.println("Función no implementada.");
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
        return """

=== Gestión de Procesos con ProcessBuilder === 
1. Ejecutar comando
2. Lanzar aplicacion
3. Ejecutar comando con redirección
4. Ejecutar comando con variables de entorno
5. Salir
Selecciona una opcion""";
    }
}
