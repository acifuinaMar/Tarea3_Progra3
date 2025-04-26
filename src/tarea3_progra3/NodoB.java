/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tarea3_progra3;

/**
 *
 * @author motit
 */
public class NodoB {
    int[] claves;
    NodoB[] hijos;
    int nClaves;
    boolean hoja;
    int m;  // orden del árbol
    int t;  // grado mínimo (ceil(m/2))

    public NodoB(int m, boolean hoja) {
        this.m = m;
        this.t = (int) Math.ceil(m / 2.0)-1;
        this.hoja = hoja;
        this.claves = new int[m-1]; // ← máximo m-1 claves
        this.hijos = new NodoB[m];    // ← máximo m hijos
        this.nClaves = 0;
    }

    //Metodo auxiliar de insercion.Para cuando el nodo actuali no esta lleno
    public void insertarAux(int clave) {
        int i = nClaves - 1;

        if (hoja) {
            // Insertar manteniendo orden
            while (i >= 0 && clave < claves[i]) {
                claves[i + 1] = claves[i];
                i--;
            }
            claves[i + 1] = clave;
            nClaves++;
        } else {
            // Buscar hijo adecuado
            while (i >= 0 && clave < claves[i]) {
                i--;
            }
            i++;

            // Verificar si necesita división
            if (hijos[i].nClaves == m - 1) {
                dividirHijo(i);

                // Actualizar índice si es necesario
                if (clave > claves[i]) {
                    i++;
                }
            }
            hijos[i].insertarAux(clave);
        }
    }

    public void dividirHijo(int i) {
        NodoB y = hijos[i]; // Nodo a dividir
        NodoB z = new NodoB(m, y.hoja);
        z.nClaves = t - 1;

        // La clave MEDIA está en posición t (para m=3, posición 1)
        int claveMedia = y.claves[t]; // ¡Este es el cambio crucial!

        // Copiar claves mayores que la media a z
        for (int j = 0; j < t - 1; j++) {
            z.claves[j] = y.claves[j + t + 1];
            y.claves[j + t + 1] = 0; // Limpiar
        }

        // Copiar hijos si no es hoja
        if (!y.hoja) {
            for (int j = 0; j < t; j++) {
                z.hijos[j] = y.hijos[j + t + 1];
                y.hijos[j + t + 1] = null;
            }
        }

        y.nClaves = t; // y conserva t claves (no t-1)

        // Mover la clave media al padre
        for (int j = nClaves; j > i; j--) {
            claves[j] = claves[j - 1];
        }
        claves[i] = claveMedia; // Usar la clave media correcta

        // Ajustar hijos
        for (int j = nClaves + 1; j > i + 1; j--) {
            hijos[j] = hijos[j - 1];
        }
        hijos[i + 1] = z;

        nClaves++;
    }
    public void imprimir(int nivel) {
        System.out.print("Nivel " + nivel + ": ");
        for (int i = 0; i < nClaves; i++) {
            System.out.print(claves[i] + " ");
        }
        System.out.println();

        if (!hoja) {
            for (int i = 0; i <= nClaves; i++) {
                if (hijos[i] != null) {
                    hijos[i].imprimir(nivel + 1);
                }
            }
        }
    }
    public NodoB buscar(int clave) {
        int i = 0;
        // Buscar la primera clave mayor o igual a la clave buscada
        while (i < nClaves && clave > claves[i]) {
            i++;
        }

        // Si encontramos la clave, retornar este nodo
        if (i < nClaves && clave == claves[i]) {
            return this;
        }

        // Si es hoja y no encontramos la clave, retornar null
        if (hoja) {
            return null;
        }

        // Si no es hoja, seguir buscando en el hijo adecuado
        return hijos[i].buscar(clave);
    }
    
    public void eliminar(int clave) {
        int idx = encontrarClave(clave);

        if (idx < nClaves && claves[idx] == clave) {
            // Caso 1: La clave está en este nodo
            if (hoja) {
                eliminarDeHoja(idx);
            } else {
                eliminarDeNoHoja(idx);
            }
        } else {
            // Caso 2: La clave está en un subárbol
            if (hoja) {
                System.out.println("La clave " + clave + " no existe en el árbol.");
                return;
            }

            boolean ultimoHijo = (idx == nClaves);

            if (hijos[idx].nClaves < t) {
                llenarHijo(idx);
            }

            if (ultimoHijo && idx > nClaves) {
                hijos[idx - 1].eliminar(clave);
            } else {
                hijos[idx].eliminar(clave);
            }
        }
    }

    private int encontrarClave(int clave) {
        int idx = 0;
        while (idx < nClaves && claves[idx] < clave) {
            idx++;
        }
        return idx;
    }

    private void eliminarDeHoja(int idx) {
        // Simplemente mover las claves posteriores una posición hacia atrás
        for (int i = idx + 1; i < nClaves; i++) {
            claves[i - 1] = claves[i];
        }
        nClaves--;
    }

    private void eliminarDeNoHoja(int idx) {
        int clave = claves[idx];

        // Caso 3a: El hijo anterior tiene al menos t claves
        if (hijos[idx].nClaves >= t) {
            int predecesor = obtenerPredecesor(idx);
            claves[idx] = predecesor;
            hijos[idx].eliminar(predecesor);
        }
        // Caso 3b: El hijo siguiente tiene al menos t claves
        else if (hijos[idx + 1].nClaves >= t) {
            int sucesor = obtenerSucesor(idx);
            claves[idx] = sucesor;
            hijos[idx + 1].eliminar(sucesor);
        }
        // Caso 3c: Combinar hijos
        else {
            combinarHijos(idx);
            hijos[idx].eliminar(clave);
        }
    }

    private int obtenerPredecesor(int idx) {
        NodoB actual = hijos[idx];
        while (!actual.hoja) {
            actual = actual.hijos[actual.nClaves];
        }
        return actual.claves[actual.nClaves - 1];
    }

    private int obtenerSucesor(int idx) {
        NodoB actual = hijos[idx + 1];
        while (!actual.hoja) {
            actual = actual.hijos[0];
        }
        return actual.claves[0];
    }

    private void combinarHijos(int idx) {
        NodoB hijo = hijos[idx];
        NodoB hermano = hijos[idx + 1];

        // Mover la clave del nodo actual al hijo
        hijo.claves[t - 1] = claves[idx];

        // Copiar claves del hermano
        for (int i = 0; i < hermano.nClaves; i++) {
            hijo.claves[i + t] = hermano.claves[i];
        }

        // Copiar hijos del hermano si no es hoja
        if (!hijo.hoja) {
            for (int i = 0; i <= hermano.nClaves; i++) {
                hijo.hijos[i + t] = hermano.hijos[i];
            }
        }

        // Mover claves en el nodo actual
        for (int i = idx + 1; i < nClaves; i++) {
            claves[i - 1] = claves[i];
        }

        // Mover hijos en el nodo actual
        for (int i = idx + 2; i <= nClaves; i++) {
            hijos[i - 1] = hijos[i];
        }

        hijo.nClaves += hermano.nClaves + 1;
        nClaves--;
    }

    private void llenarHijo(int idx) {
        // Caso 3a: Tomar una clave del hermano izquierdo
        if (idx != 0 && hijos[idx - 1].nClaves >= t) {
            prestarDeIzquierdo(idx);
        }
        // Caso 3b: Tomar una clave del hermano derecho
        else if (idx != nClaves && hijos[idx + 1].nClaves >= t) {
            prestarDeDerecho(idx);
        }
        // Caso 3c: Combinar con un hermano
        else {
            if (idx != nClaves) {
                combinarHijos(idx);
            } else {
                combinarHijos(idx - 1);
            }
        }
    }

    private void prestarDeIzquierdo(int idx) {
        NodoB hijo = hijos[idx];
        NodoB hermano = hijos[idx - 1];

        // Mover claves en el hijo
        for (int i = hijo.nClaves - 1; i >= 0; i--) {
            hijo.claves[i + 1] = hijo.claves[i];
        }

        // Mover la clave del padre al hijo
        hijo.claves[0] = claves[idx - 1];

        // Mover hijos si no es hoja
        if (!hijo.hoja) {
            for (int i = hijo.nClaves; i >= 0; i--) {
                hijo.hijos[i + 1] = hijo.hijos[i];
            }
            hijo.hijos[0] = hermano.hijos[hermano.nClaves];
        }

        // Mover la clave del hermano al padre
        claves[idx - 1] = hermano.claves[hermano.nClaves - 1];

        hijo.nClaves++;
        hermano.nClaves--;
    }

    private void prestarDeDerecho(int idx) {
        NodoB hijo = hijos[idx];
        NodoB hermano = hijos[idx + 1];

        // Mover la clave del padre al hijo
        hijo.claves[hijo.nClaves] = claves[idx];

        // Mover la primera clave del hermano al padre
        claves[idx] = hermano.claves[0];

        // Mover hijos si no es hoja
        if (!hijo.hoja) {
            hijo.hijos[hijo.nClaves + 1] = hermano.hijos[0];
        }

        // Mover claves en el hermano
        for (int i = 1; i < hermano.nClaves; i++) {
            hermano.claves[i - 1] = hermano.claves[i];
        }

        // Mover hijos en el hermano si no es hoja
        if (!hermano.hoja) {
            for (int i = 1; i <= hermano.nClaves; i++) {
                hermano.hijos[i - 1] = hermano.hijos[i];
            }
        }

        hijo.nClaves++;
        hermano.nClaves--;
    }
    public void debug(String indent) {
        System.out.print(indent + "Claves: ");
        for (int i = 0; i < nClaves; i++) {
            System.out.print(claves[i] + " ");
        }
        System.out.println();

        if (!hoja) {
            for (int i = 0; i <= nClaves; i++) {
                if (hijos[i] != null) {
                    System.out.println(indent + "Hijo " + i + ":");
                    hijos[i].debug(indent + "  ");
                }
            }
        }
    }
    }
