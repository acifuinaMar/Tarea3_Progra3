/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tarea3_progra3;
/**
 *
 * @author maryori
 */
public class ArbolB {
    NodoB raiz;
    int m;  // orden del árbol
    int t;

    public ArbolB(int m) {
        this.m = m;
        this.t = (int) Math.ceil(m/2.0);
        this.raiz = new NodoB(m, true);
    }
    //Método para insertar
    public void insertar(int k) {
        if (raiz == null) {
            raiz = new NodoB(m, true);
        }

        if (raiz.nClaves == m - 1) {
            // Crear nueva raíz
            NodoB nuevaRaiz = new NodoB(m, false);
            nuevaRaiz.hijos[0] = raiz;
            raiz = nuevaRaiz;

            // Dividir el hijo (raíz original)
            raiz.dividirHijo(0);

            // Insertar en el subárbol correcto
            if (k > raiz.claves[0]) {
                raiz.hijos[1].insertarAux(k);
            } else {
                raiz.hijos[0].insertarAux(k);
            }
        } else {
            raiz.insertarAux(k);
        }
        raiz.debug("");
    }
    //Metodo para eliminar
    public void eliminar(int k) {
        if (raiz == null) {
            System.out.println("El árbol está vacío");
            return;
        }

        raiz.eliminar(k);

        // Si la raíz queda vacía
        if (raiz.nClaves == 0) {
            if (raiz.hoja) {
                raiz = null;
            } else {
                raiz = raiz.hijos[0];
            }
        }
    }
    //Metodo para buscar una clave
    public boolean buscar(int k){
        return raiz != null && raiz.buscar(k) != null;
    }
    
    public void imprimirArbol() {
        if (raiz != null) {
            raiz.imprimir(0);
        } else {
            System.out.println("El árbol está vacío.");
        }
    }
}
