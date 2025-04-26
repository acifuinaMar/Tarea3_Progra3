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

    public ArbolB(int m) {
        this.m = m;
        this.raiz = new NodoB(m, true);
    }
    //Método para insertar
    public void insertar(int k) {
        if (raiz == null) {
            raiz = new NodoB(m, true);
        }

        if (raiz.nClaves == m - 1) {
            NodoB nuevaRaiz = new NodoB(m, false);
            nuevaRaiz.hijos[0] = raiz;
            raiz = nuevaRaiz;
            raiz.dividirHijo(0);

            // Decidir en qué hijo insertar
            int i = 0;
            if (k > raiz.claves[0]) {
                i = 1;
            }
            raiz.hijos[i].insertarAux(k);
        } else {
            raiz.insertarAux(k);
        }
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
