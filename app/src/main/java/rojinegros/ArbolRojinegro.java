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
        if (this.raiz == null) {
            this.valor = x;
            this.black = true;
            this.raiz = this;
        } else {
            ArbolRojinegro nodo = this;
            ArbolRojinegro padre = null;
            while (nodo != null) {
                padre = nodo;
                if (x < nodo.getValor()) {
                    nodo = nodo.getIzq();
                } else {
                    nodo = nodo.getDer();
                }
            }

            ArbolRojinegro nuevoNodo = new ArbolRojinegro();
            nuevoNodo.setValor(x);
            nuevoNodo.setBlack(false);
            if (x < padre.getValor()) {
                padre.setIzq(nuevoNodo);
            } else {
                padre.setDer(nuevoNodo);
            }
            nuevoNodo.setPadre(padre);
            arreglarInsercion(nuevoNodo);
        }

    }

    public void arreglarInsercion(ArbolRojinegro nodo) throws Exception {
        
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
            return raiz;
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
        // cambia la raiz
        replaceParentsChild(parent, nodo, leftChild);
    }

    private void replaceParentsChild(ArbolRojinegro parent, ArbolRojinegro oldChild, ArbolRojinegro newChild) {
        if (parent == null) {
            // this.raiz = newChild;
            // ArbolRojinegro raizAntigua = this;
            this.setBlack(newChild.isBlack());
            this.valor = newChild.getValor();
            this.der = newChild.getDer();
            this.izq = newChild.getIzq();

            // el this debe modificarse tambien ya que el this siempre es la raiz
        } else if (parent.getIzq() == oldChild) {
            parent.setIzq(newChild);
        } else if (parent.getDer() == oldChild) {
            parent.setDer(newChild);
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.setPadre(parent);
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