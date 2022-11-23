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
    private ArbolRojinegro padre;

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
        ArbolRojinegro nodo = this.raiz;
        ArbolRojinegro padre = null;

        // Traverse the tree to the getIzq() or getDer() depending on the x
        while (nodo != null) {
            padre = nodo;
            if (x < nodo.getValor()) {
                nodo = nodo.getIzq();
            } else if (x > nodo.getValor()) {
                nodo = nodo.getDer();
            } else {
                throw new IllegalArgumentException("BST already contains a nodo with x " + x);
            }
        }

        // Insert new nodo
        ArbolRojinegro nuevoNodo = new ArbolRojinegro();
        nuevoNodo.setValor(x);
        nuevoNodo.setBlack(false);
        if (padre == null) {
            this.raiz = nuevoNodo;
        } else if (x < padre.getValor()) {
            padre.setIzq(nuevoNodo);
        } else {
            padre.setDer(nuevoNodo);
        }
        nuevoNodo.setPadre(padre);

        arreglarInsercion(nuevoNodo);
    }

    public void arreglarInsercion(ArbolRojinegro nodo) throws Exception {
        ArbolRojinegro padre = nodo.getPadre();

        // Case 1: Parent is null, we've reached the root, the end of the recursion
        if (padre == null) {
            // Uncomment the following line if you want to enforce black roots (rule 2):
            // nodo.color = BLACK;
            return;
        }

        // Parent is black --> nothing to do
        if (padre.isBlack()) {
            return;
        }

        // From here on, parent is red
        ArbolRojinegro abuelo = padre.getPadre();

        // Case 2:
        // Not having a grandparent means that parent is the root. If we enforce black
        // roots
        // (rule 2), grandparent will never be null, and the following if-then block can
        // be
        // removed.
        if (abuelo == null) {
            // As this method is only called on red nodos (either on newly inserted ones -
            // or -
            // recursively on red grandparents), all we have to do is to recolor the root
            // black.
            padre.setBlack(true);
            return;
        }

        // Get the uncle (may be null/nil, in which case its color is BLACK)
        ArbolRojinegro tio = getUncle(padre);

        // Case 3: Uncle is red -> recolor parent, grandparent and uncle
        if (tio != null && !tio.isBlack()) {
            padre.setBlack(true);
            abuelo.setBlack(false);
            tio.setBlack(true);

            // Call recursively for grandparent, which is now red.
            // It might be root or have a red parent, in which case we need to fix more...
            arreglarInsercion(abuelo);
        }

        // Parent is left child of grandparent
        else if (padre == abuelo.getIzq()) {
            // Case 4a: Uncle is black and nodo is left->right "inner child" of its
            // grandparent
            if (nodo == padre.getDer()) {
                rotacionIzquierda(padre.getValor());

                // Let "parent" point to the new root nodo of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                padre = nodo;
            }

            // Case 5a: Uncle is black and nodo is left->left "outer child" of its
            // grandparent
            rotacionDerecha(abuelo.getValor());

            // Recolor original parent and grandparent
            padre.setBlack(true);
            abuelo.setBlack(false);
        }

        // Parent is right child of grandparent
        else {
            // Case 4b: Uncle is black and nodo is right->left "inner child" of its
            // grandparent
            if (nodo == padre.getIzq()) {
                rotacionDerecha(padre.getValor());

                // Let "parent" point to the new root nodo of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                padre = nodo;
            }

            // Case 5b: Uncle is black and nodo is right->right "outer child" of its
            // grandparent
            rotacionIzquierda(abuelo.getValor());

            // Recolor original parent and grandparent
            padre.setBlack(true);
            abuelo.setBlack(false);
        }
    }

    private ArbolRojinegro getUncle(ArbolRojinegro padre) {
        ArbolRojinegro abuelo = padre.getPadre();
        if (abuelo.getIzq() == padre) {
            return abuelo.getDer();
        } else if (abuelo.getDer() == padre) {
            return abuelo.getIzq();
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
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
        if (this.getValor() == x) {
            return this;
        } else {
            if (x >= this.getValor()) {
                if (this.getDer() != null) {
                    return this.getDer().search(x);
                } else {
                    return null;
                }
            } else {
                if (this.getIzq() != null) {
                    return this.getIzq().search(x);
                } else {
                    return null;
                }
            }
        }
    }

    public void rotacionIzquierda(int x) throws Exception {
        ArbolRojinegro arb = this.search(x);
        ArbolRojinegro y = arb.getDer();
        arb.setDer(y.getIzq());
        if (y.getIzq() != null) {
            y.getIzq().setPadre(arb);
        }
        y.setPadre(arb.getPadre());
        if (arb.getPadre() == null) {
            this.setRaiz(y);
        } else if (arb == arb.getPadre().getIzq()) {
            arb.getPadre().setIzq(y);
        } else {
            arb.getPadre().setDer(y);
        }
        y.setIzq(arb);
        arb.setPadre(y);
    }

    public void rotacionDerecha(int x) throws Exception {
        ArbolRojinegro nodo = this.search(x);
        ArbolRojinegro parent = nodo.getPadre();
        ArbolRojinegro leftChild = nodo.getIzq();

        nodo.setIzq(leftChild.getDer());
        if (leftChild.getDer() != null) {
            leftChild.getDer().setPadre(nodo);
        }

        leftChild.setDer(nodo);
        nodo.setPadre(leftChild);

        replaceParentsChild(parent, nodo, leftChild);
    }

    private void replaceParentsChild(ArbolRojinegro parent, ArbolRojinegro oldChild, ArbolRojinegro newChild) {
        if (parent == null) {
            this.raiz = newChild;
        } else if (parent.getIzq() == oldChild) {
            parent.setIzq(newChild);
        } else if (parent.getDer() == oldChild) {
            parent.setDer(newChild);
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.setPadre(newChild);
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