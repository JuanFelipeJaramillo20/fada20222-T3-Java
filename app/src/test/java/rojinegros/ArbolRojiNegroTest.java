package rojinegros;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rojinegros.data.ArbolRojiNegroGenerador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArbolRojiNegroTest {

    private ArbolRojinegro arb1;
    private ArbolRojinegro arb2;
    private ArbolRojinegro arb3;
    private ArbolRojinegro arb4;

    @BeforeEach
    private void setUp() {
        ArbolRojiNegroGenerador gen = new ArbolRojiNegroGenerador();
        arb1 = gen.ejemplo1();
        arb2 = gen.ejemplo2();
        arb3 = gen.ejemplo3();
        arb4 = gen.ejemplo4();
    }

    private int altura(ArbolRojinegro arb) {
        int alturaIzq = 0;
        int alturaDer = 0;
        if (arb.getIzq() != null) {
            alturaIzq = 1 + altura(arb.getIzq());
        }

        if (arb.getDer() != null) {
            alturaIzq = 1 + altura(arb.getDer());
        }

        return (alturaIzq > alturaDer) ? alturaIzq : alturaDer;

    }

    @Test
    void recorridoTest() throws Exception {
        assertEquals(arb1.bfs(), "2 1 4 3 5");
        assertEquals(arb1.inorden(), "1 2 3 4 5");
        assertEquals(arb2.bfs(), "5 1 8 6 9");
        assertEquals(arb2.inorden(), "1 5 6 8 9");
        assertEquals(arb3.bfs(), "4 2 5 1 3");
        assertEquals(arb3.inorden(), "1 2 3 4 5");
        assertEquals(arb4.bfs(), "8 5 9 1 6");
        assertEquals(arb4.inorden(), "1 5 6 8 9");
    }

    @Test
    void insertarTest() throws Exception {
        int num[] = { 7, 6, 12, 10, 9, 11, 14, 15, 13, -3, 44, 66, 72, -10, -13 };
        ArbolRojinegro instancia = new ArbolRojinegro(null, null, 8, true);

        for (int i = 0; i < num.length; i++) {
            instancia.insertar(num[i]);
        }
        assertEquals(instancia.inorden(), "-13 -10 -3 6 7 8 9 10 11 12 13 14 15 44 66 72");
        assertTrue(altura(instancia) <= 2 * Math.log(15) / Math.log(2) + 1);

        int numB[] = { 22, 1, 2, 3, 9, 14, 17, 0, 33, 7, 13, 19, -1, -7, -9, 50, -22, 66, -14, -32 };
        ArbolRojinegro instanciaB = new ArbolRojinegro(null, null, 20, true);

        for (int i = 0; i < numB.length; i++) {
            instanciaB.insertar(numB[i]);
        }

        assertEquals(instanciaB.inorden(), "-32 -22 -14 -9 -7 -1 0 1 2 3 7 9 13 14 17 19 20 22 33 50 66");
        assertTrue(altura(instancia) <= 2 * Math.log(20) / Math.log(2) + 1);

    }

    @Test
    void maximoTest() throws Exception {

        assertEquals(arb1.maximo(), 5);
        assertEquals(arb2.maximo(), 9);
        assertEquals(arb3.maximo(), 5);
        assertEquals(arb4.maximo(), 9);
    }

    @Test
    void minimoTest() throws Exception {

        assertEquals(arb1.minimo(), 1);
        assertEquals(arb2.minimo(), 1);
        assertEquals(arb3.minimo(), 1);
        assertEquals(arb4.minimo(), 1);
    }

    @Test
    void searchTest() throws Exception {

        assertEquals(arb1.search(5).getValor(), 5);
        assertEquals(arb2.search(6).getValor(), 6);
        assertEquals(arb3.search(3).getValor(), 3);
        assertEquals(arb4.search(9).getValor(), 9);

        assertNull(arb1.search(30));
        assertNull(arb2.search(-3));
        assertNull(arb3.search(18));
        assertNull(arb4.search(3));
    }

    @Test
    void rotacionIzquierdaTest() throws Exception {

        // Execute
        arb1.rotacionIzquierda(4);

        // Assert
        assertEquals(arb1.bfs(), "4 2 5 1 3");

        // Execute
        arb2.rotacionIzquierda(8);

        // Assert
        assertEquals(arb2.bfs(), "8 5 9 1 6");

    }

    @Test
    void rotacionDerechaTest() throws Exception {

        // Execute
        arb3.rotacionDerecha(2);

        // Assert
        assertEquals(arb3.bfs(), "2 1 4 3 5");

        // Execute
        arb4.rotacionDerecha(5);

        // Assert
        assertEquals(arb4.bfs(), "5 1 8 6 9");
    }
}
