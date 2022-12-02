package rojinegros.data;

import rojinegros.ArbolRojinegro;

public class ArbolRojiNegroGenerador {
        /*
         * Ejemplo1 para rotación a la izquierda.
         */
        public ArbolRojinegro ejemplo1() {
                ArbolRojinegro nodoIzq = new ArbolRojinegro(
                                null,
                                null,
                                1,
                                true);

                ArbolRojinegro nodoDerIzq = new ArbolRojinegro(
                                null,
                                null,
                                3,
                                true);
                ArbolRojinegro nodoDerDer = new ArbolRojinegro(
                                null,
                                null,
                                5,
                                true);
                ArbolRojinegro nodoDer = new ArbolRojinegro(
                                nodoDerIzq,
                                nodoDerDer,
                                4,
                                false);
                ArbolRojinegro raiz = new ArbolRojinegro(
                                nodoIzq,
                                nodoDer,
                                2,
                                true);

                nodoDer.setFather(raiz);
                nodoIzq.setFather(raiz);
                nodoDerIzq.setFather(nodoDer);
                nodoDerDer.setFather(nodoDer);
                return raiz;
        }

        /*
         * Ejemplo2 para rotación a la izquierda.
         */
        public ArbolRojinegro ejemplo2() {
                ArbolRojinegro nodoIzq = new ArbolRojinegro(
                                null,
                                null,
                                1,
                                true);

                ArbolRojinegro nodoDerIzq = new ArbolRojinegro(
                                null,
                                null,
                                6,
                                true);

                ArbolRojinegro nodoDerDer = new ArbolRojinegro(
                                null,
                                null,
                                9,
                                true);

                ArbolRojinegro nodoDer = new ArbolRojinegro(
                                nodoDerIzq,
                                nodoDerDer,
                                8,
                                false);
                ArbolRojinegro raiz = new ArbolRojinegro(
                                nodoIzq,
                                nodoDer,
                                5,
                                true);
                nodoDer.setFather(raiz);
                nodoIzq.setFather(raiz);
                nodoDerIzq.setFather(nodoDer);
                nodoDerDer.setFather(nodoDer);
                return raiz;
        }

        /*
         * Ejemplo3 para rotación a la derecha.
         */
        public ArbolRojinegro ejemplo3() {
                ArbolRojinegro nodoIzqIzq = new ArbolRojinegro(
                                null,
                                null,
                                1,
                                true);

                ArbolRojinegro nodoIzqDer = new ArbolRojinegro(
                                null,
                                null,
                                3,
                                true);

                ArbolRojinegro nodoIzq = new ArbolRojinegro(
                                nodoIzqIzq,
                                nodoIzqDer,
                                2,
                                false);

                ArbolRojinegro nodoDer = new ArbolRojinegro(
                                null,
                                null,
                                5,
                                true);
                ArbolRojinegro raiz = new ArbolRojinegro(
                                nodoIzq,
                                nodoDer,
                                4,
                                true);

                nodoIzq.setFather(raiz);
                nodoDer.setFather(raiz);
                nodoIzqDer.setFather(nodoIzq);
                nodoIzqIzq.setFather(nodoIzq);
                return raiz;
        }

        /*
         * Ejemplo4 para rotación a la derecha.
         */
        public ArbolRojinegro ejemplo4() {
                ArbolRojinegro nodoIzqIzq = new ArbolRojinegro(
                                null,
                                null,
                                1,
                                true);
                ArbolRojinegro nodoIzqDer = new ArbolRojinegro(
                                null,
                                null,
                                6,
                                true);

                ArbolRojinegro nodoIzq = new ArbolRojinegro(
                                nodoIzqIzq,
                                nodoIzqDer,
                                5,
                                false);
                ArbolRojinegro nodoDer = new ArbolRojinegro(
                                null,
                                null,
                                9,
                                true);
                ArbolRojinegro raiz = new ArbolRojinegro(
                                nodoIzq,
                                nodoDer,
                                8,
                                true);

                nodoIzq.setFather(raiz);
                nodoDer.setFather(raiz);
                nodoIzqDer.setFather(nodoIzq);
                nodoIzqIzq.setFather(nodoIzq);
                return raiz;
        }
}
