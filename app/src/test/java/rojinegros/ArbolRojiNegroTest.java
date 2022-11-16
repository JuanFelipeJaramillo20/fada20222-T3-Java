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

    @Test
    void insertar() throws Exception {
        int num[] = {8, 7, 6, 12, 10, 9, 11, 14, 15, 13};
        ArbolRojinegro instancia = new ArbolRojinegro();

        for (int i = 0; i < num.length; i++) {
            instancia.insertar(num[i]);
        }
        assertEquals(instancia.bfs(), "10 7 12 6 8 11 14 9 13 15");
        assertEquals(instancia.inorden(), "6 7 8 9 10 11 12 13 14 15");
        assertTrue(instancia.propiedadAlturaNegra());

        int numB[] = {20, 22, 1, 2, 3, 9, 14, 17, 0, 33, 7, 13, 19};
        ArbolRojinegro instanciaB = new ArbolRojinegro();

        for (int i = 0; i < numB.length; i++) {
            instanciaB.insertar(numB[i]);
        }
        assertEquals(instanciaB.bfs(), "9 2 20 1 3 14 22 0 7 13 17 33 19");
        assertEquals(instanciaB.inorden(), "0 1 2 3 7 9 13 14 17 19 20 22 33");
        assertTrue(instanciaB.propiedadAlturaNegra());
    }

    @Test
    void maximo() throws Exception {

        assertEquals(arb1.maximo(), 5);
        assertEquals(arb2.maximo(), 9);
        assertEquals(arb3.maximo(), 5);
        assertEquals(arb4.maximo(), 9);
    }

    @Test
    void minimo() throws Exception {

        assertEquals(arb1.minimo(), 1);
        assertEquals(arb2.minimo(), 1);
        assertEquals(arb3.minimo(), 1);
        assertEquals(arb4.minimo(), 1);
    }

    @Test
    void search() throws Exception {

        assertEquals(arb1.search(5).getValor(), 1);
        assertEquals(arb2.search(6).getValor(), 6);
        assertEquals(arb3.search(3).getValor(), 1);
        assertEquals(arb4.search(9).getValor(), 1);

        assertNull(arb1.search(30));
        assertNull(arb2.search(-3));
        assertNull(arb3.search(18));
        assertNull(arb4.search(3));
    }

    @Test
    void rotacionIzquierda() throws Exception {

        //Execute
        arb1.rotacionIzquierda(4);

        //Assert
        assertEquals(arb1.bfs(), "4 2 5 1 3");
        assertTrue(arb1.propiedadAlturaNegra());

        //Execute
        arb2.rotacionIzquierda(8);

        //Assert
        assertEquals(arb2.bfs(), "8 5 9 1 6");
        assertTrue(arb2.propiedadAlturaNegra());

    }

    @Test
    void rotacionDerecha() throws Exception {

        //Execute
        arb3.rotacionDerecha(4);

        //Assert
        assertEquals(arb3.bfs(), "2 1 4 3 5");
        assertTrue(arb3.propiedadAlturaNegra());

        //Execute
        arb4.rotacionIzquierda(8);

        //Assert
        assertEquals(arb4.bfs(), "5 1 8 6 9");
        assertTrue(arb4.propiedadAlturaNegra());
    }
}