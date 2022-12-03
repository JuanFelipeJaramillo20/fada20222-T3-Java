package rojinegros;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.Queue;

public class ArbolRojinegro {
    @Getter
    @Setter
    private ArbolRojinegro izq;

    @Getter
    @Setter
    private ArbolRojinegro der;

    @Getter
    @Setter
    private int valor;

    @Getter
    @Setter
    private boolean black; // Si es negro True, en otro caso rojo

    @Getter
    @Setter
    private ArbolRojinegro father;

    @Getter
    @Setter
    private ArbolRojinegro raiz;

    public ArbolRojinegro(ArbolRojinegro izq,
            ArbolRojinegro der,
            int valor,
            boolean black) {
        this.izq = izq;
        this.der = der;
        this.valor = valor;
        this.black = black;
    }

    public ArbolRojinegro() {
        this.izq = null;
        this.der = null;
        this.black = true;
    }
    /*
     * Metodos a implementar
     */

    public void insertar(int x) throws Exception {
        ArbolRojinegro nodo = raiz;
        ArbolRojinegro padre = null;

        while (nodo != null) {
            padre = nodo;
            if (x < nodo.getValor()) {
                nodo = nodo.getIzq();
            } else if (x > nodo.getValor()) {
                nodo = nodo.getDer();
            } else {
                throw new Exception("El arbol ya contiene un nodo con el valor: " + x);
            }
        }

        ArbolRojinegro nuevoNodo = new ArbolRojinegro();
        nuevoNodo.setValor(x);
        nuevoNodo.setBlack(false);
        if (padre == null) {
            raiz = nuevoNodo;
        } else if (x < padre.getValor()) {
            padre.setIzq(nuevoNodo);
        } else {
            padre.setDer(nuevoNodo);
        }
        nuevoNodo.setFather(padre);

        ArreglarArbolDespuesDeInsercion(nuevoNodo);
        correcionRaiz();
    }

    private void ArreglarArbolDespuesDeInsercion(ArbolRojinegro nodo) throws Exception {
        ArbolRojinegro padre = nodo.getFather();

        // Caso 1: El padre es nulo, estamos en la raiz
        if (padre == null) {
            // Regla 2: La raiz siempre debe ser negra
            nodo.black = true;
            return;
        }

        // Si el padre es de color negro, no hay que hace nada
        if (padre.isBlack()) {
            return;
        }

        // Casos cuando el padre es rojo
        ArbolRojinegro abuelo = padre.getFather();

        ArbolRojinegro tio = getTio(padre);

        // Caso 3: Si el tio es rojo, hay que cambiar el color del padre, abuelo y tio
        if (tio != null && !tio.isBlack()) {
            padre.setBlack(true);
            abuelo.setBlack(false);
            tio.setBlack(true);

            // Como cambiamos el color del padre, abuelo y tio puede que hayamos cambiado el
            // color de la raiz, por eso llamamos nuevamente el método con el abuelo para
            // revisar y corregir si es necesario
            ArreglarArbolDespuesDeInsercion(abuelo);
        }

        // Si el padre es el hijo izquierdo del abuelo
        else if (padre == abuelo.getIzq()) {
            // Caso 4,1: El tio es negro y el nodo es "hijo interno" izquierdo->derecho del
            // abuelo
            if (nodo == padre.getDer()) {
                rotacionIzquierda(padre.getValor());
                // Ahora "padre" apunta al nuedo nodo raiz del sub-arbol rotado, luego será
                // recoloreado
                padre = nodo;
            }

            // Caso 5.1: El tio es negro y el nodo es "hijo externo" izquierdo->izquierdo
            // del abuelo
            rotacionDerecha(abuelo.getValor());

            // Recoloreamos el padre y el abuelo
            padre.setBlack(true);
            abuelo.setBlack(false);
        }

        // Si el padre es hijo derecho del abuelo
        else {
            // Caso 4,2: El tio es negro y el nodo es "hijo interno" derecho->izquierdo del
            // abuelo
            if (nodo == padre.getIzq()) {
                rotacionDerecha(padre.getValor());

                // Ahora "padre" apunta al nuedo nodo raiz del sub-arbol rotado, luego será
                // recoloreado
                padre = nodo;
            }

            // Case 5.2: El tio es negro y el nodo es "hijo externo" derecho->dercho del
            // abuelo
            rotacionIzquierda(abuelo.getValor());

            // Recoloreamos el padre y el abuelo
            padre.setBlack(true);
            abuelo.setBlack(false);
        }
    }

    private ArbolRojinegro getTio(ArbolRojinegro nodoPadre) throws Exception {
        ArbolRojinegro abuelo = nodoPadre.getFather();
        if (abuelo.getIzq() == nodoPadre) {
            return abuelo.getDer();
        } else if (abuelo.getDer() == nodoPadre) {
            return abuelo.getIzq();
        } else {
            throw new Exception("El padre no es hijo del abuelo");
        }
    }

    public int maximo() throws Exception {
        if (this.getDer() == null) {
            return this.getValor();
        } else {
            ArbolRojinegro temp = this;
            while (temp.getDer() != null) {
                temp = temp.getDer();
            }
            return temp.getValor();
        }
    }

    public int minimo() throws Exception {
        if (this.getIzq() == null) {
            return this.getValor();
        } else {
            ArbolRojinegro temp = this;
            while (temp.getIzq() != null) {
                temp = temp.getIzq();
            }
            return temp.getValor();
        }
    }

    public ArbolRojinegro search(int x) throws Exception {
        ArbolRojinegro iterador = this;
        while (iterador != null) {
            if (iterador.getValor() == x) {
                return iterador;
            } else if (iterador.getValor() < x) {
                if (iterador.getDer() != null) {
                    iterador = iterador.getDer();
                } else {
                    return null;
                }
            } else {
                if (iterador.getIzq() != null) {
                    iterador = iterador.getIzq();
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public void rotacionIzquierda(int x) throws Exception {
        // PRUEBA CON BFS ESTA INCORRECTA
        ArbolRojinegro node = search(x);
        ArbolRojinegro parent = node.getFather();
        ArbolRojinegro rightChild = node.getDer();

        node.der = rightChild.izq;
        if (rightChild.izq != null) {
            rightChild.izq.setFather(node);
        }

        rightChild.izq = node;
        node.setFather(rightChild);

        replaceParentsChild(parent, node, rightChild);
        correcionRaiz();
    }

    public void rotacionDerecha(int x) throws Exception {

        ArbolRojinegro node = this.search(x);
        ArbolRojinegro parent = node.getFather();
        ArbolRojinegro leftChild = node.getIzq();

        node.izq = leftChild.der;
        if (leftChild.der != null) {
            leftChild.der.setFather(node);
        }

        leftChild.setDer(node);
        node.setFather(leftChild);

        replaceParentsChild(parent, node, leftChild);
        correcionRaiz();
    }

    public void correcionRaiz() {
        if (this.raiz != null) {
            this.setDer(this.raiz.getDer());
            this.setIzq(this.raiz.getIzq());
            this.setBlack(this.raiz.isBlack());
            this.setFather(this.raiz.getFather());
            this.setValor(this.raiz.getValor());
            // AL EJECUTAR ESTE METODO PARA CORREGIR LA RAIZ, SE CAMBIAN LOS VALORES DEL
            // HIJO DERECHO POR SI MISMO, SE GENERA BUCLE INFINITO EN RIGTH
        }
    }

    private void replaceParentsChild(ArbolRojinegro parent, ArbolRojinegro oldChild, ArbolRojinegro newChild) {
        if (parent == null) {
            raiz = newChild;
        } else if (parent.izq == oldChild) {
            parent.izq = newChild;
        } else if (parent.der == oldChild) {
            parent.der = newChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.setFather(parent);
        }
    }

    /*
     * Area de pruebas, no modificar.
     */
    // Verificaciones
    /*
     * Busqueda por amplitud para verificar arbol.
     */
    public String bfs() {
        String salida = "";
        String separador = "";
        Queue<ArbolRojinegro> cola = new LinkedList<>();
        cola.add(this);
        while (cola.size() > 0) {
            ArbolRojinegro nodo = cola.poll();
            salida += separador + String.valueOf(nodo.getValor());
            separador = " ";
            if (nodo.getIzq() != null) {
                cola.add(nodo.getIzq());
            }
            if (nodo.getDer() != null) {
                cola.add(nodo.getDer());
            }
        }
        return salida;
    }

    /*
     * Recorrido inorder.
     * Verifica propiedad de orden.
     */
    public String inorden() {
        String recorrido = "";
        String separador = "";
        if (this.getIzq() != null) {
            recorrido += this.getIzq().inorden();
            separador = " ";
        }
        recorrido += separador + String.valueOf(this.getValor());
        if (this.getDer() != null) {
            recorrido += " " + this.getDer().inorden();
        }
        return recorrido;
    }

}