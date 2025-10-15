import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    // Guardamos las referencias originales a System.in y System.out
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    // Donde se almacenará lo que se imprime por pantalla durante los tests
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        // Preparamos un nuevo flujo para capturar la salida por consola
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        // Restauramos las entradas/salidas originales del sistema
        System.setOut(originalOut);
        System.setIn(originalIn);
    }


    /**
     * No abrimos el programa realmente (sería bloqueante),
     * pero verificamos que se detecta el sistema operativo y se intenta lanzar el proceso.
     */
    @Test
    void testLanzarAplicacion() {
        Main.lanzarAplicacion();
        String output = outContent.toString();

        assertTrue(output.contains("Sistema operativo detectado"), "Debe mostrar el sistema operativo detectado.");
        assertTrue(output.contains("Intentando lanzar el editor"), "Debe indicar el intento de abrir el editor.");
    }




}
