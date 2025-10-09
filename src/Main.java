import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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

    }
    private static void lanzarAplicacion() {
    }

    private static void comandoConRedireccion(){

    }
    private static void comandoconEntorno(){

    }


    //Menu de la aplicacion
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