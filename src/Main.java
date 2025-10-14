import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.lang.ProcessBuilder;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {

        int option = 0;

        while(option!=5){
            System.out.println(menu());
            option = sc.nextInt();
            sc.nextLine();

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

    /**
     * Ejecuta un comando del sistema operativo mediante entrada estándar y muestra su salida.
     *
     * <p>Utiliza la clase {@link ProcessBuilder} para generar un nuevo proceso del sistema operativo
     * que ejecuta el comando especificado por el usuario. Captura la salida estándar del proceso
     * y la muestra línea por línea en la consola.</p>
     *
     * <p>El método espera a que el proceso finalice su ejecución y muestra el código de salida
     * para indicar si la ejecución fue exitosa o no.</p>
     *
     * <p><b>Flujo de ejecución:</b></p>
     * <ol>
     *   <li>Solicita al usuario un comando mediante entrada estándar</li>
     *   <li>Crea un proceso usando {@link ProcessBuilder}</li>
     *   <li>Captura y muestra la salida del proceso línea por línea</li>
     *   <li>Espera la finalización del proceso y muestra el código de salida</li>
     * </ol>
     *
     * <p><b>Excepciones gestionadas:</b></p>
     * <ul>
     *   <li>{@link IOException} - Si ocurre un error de E/S al ejecutar el comando</li>
     *   <li>{@link InterruptedException} - Si la espera del proceso es interrumpida</li>
     * </ul>
     */
    private static void ejecutarComando() {
        System.out.println("Introduce un comando a ejecutar: ");
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

    /**
     Lanza una aplicación de edición de texto predeterminada según el sistema operativo detectado.
     * <p>
     * Este método identifica si el sistema operativo es Windows o Linux y selecciona un editor de texto
     * apropiado para abrirlo mediante {@link ProcessBuilder}. En Windows intenta abrir <b>Notepad</b>
     * y en Linux intenta abrir <b>Gedit</b>.
     * </p>
     * <p>
     * Si la aplicación no está instalada o no se encuentra en el PATH del sistema, se mostrará un mensaje
     * de error al usuario.
     * </p>
     * <h4>Excepciones controladas:</h4>
     * <ul>
     *   <li>{@link IOException} si la aplicación no se encuentra o no se puede ejecutar.</li>
     *   <li>{@link InterruptedException} si el hilo es interrumpido mientras espera que el proceso termine.</li>
     * </ul>
     * Este método es bloqueante porque utiliza {@code proceso.waitFor()},
     * es decir, no continúa hasta que el usuario cierre la aplicación lanzada.
     */

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
        System.out.println("Introduce el comando: ");
        String comando = sc.nextLine();

        System.out.println("El comando con entrada: " + comando);

        System.out.println("____Configuración de las variables del entorno____");
        Map<String, String> envVars = new HashMap<>();
        System.out.println("Variable (clave=valor, Enter para terminar): ");
        String line = sc.nextLine();

        while(!line.isEmpty()){
            String[] parts = line.split("=", 2);
            if(parts.length == 2){
                envVars.put(parts[0], parts[1]);
            }else{
                System.out.println("Formato incorrecto");
            }
            System.out.print("Variable (clave=valor, Enter para terminar): ");
            line = sc.nextLine();
        }

        try{
            ProcessBuilder pb;

            String os = System.getProperty("os.name").toLowerCase();
            if(os.contains("win")){
                pb = new ProcessBuilder("cmd", "/c", comando);
            }else{
                pb = new ProcessBuilder("bash", "-c", comando);
            }

            Map<String, String> env = pb.environment();
            env.putAll(envVars);

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String output;
            while ((output = reader.readLine()) != null){
                System.out.println(output);
            }

            int exitCode = process.waitFor();
            System.out.println("El proceso ha finalizado" + exitCode);

        }catch (IOException | InterruptedException e){
            System.out.println("Error al ejecutar el comando: " + e.getMessage());
        }
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
2. Lanzar aplicación
3. Ejecutar comando con redirección
4. Ejecutar comando con variables de entorno
5. Salir

Selecciona una opción:""";
    }
}
