/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tarea3_progra3;

import java.util.Scanner;

/**
 *
 * @author maryori
 */
public class Tarea3_Progra3 {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.print("Ingrese el orden (m) del árbol B: ");
        int m = entrada.nextInt();
        ArbolB arbol = new ArbolB(m);

        int opcion;
        do {
            System.out.println("\n--- MENÚ ÁRBOL B ---");
            System.out.println("1. Insertar clave");
            System.out.println("2. Eliminar clave");
            System.out.println("3. Buscar clave");
            System.out.println("4. Imprimir arbol");
            System.out.println("5. Salir");
            System.out.print("Opción: ");
            opcion = entrada.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la clave a insertar: ");
                    int claveInsertar = entrada.nextInt();
                    arbol.insertar(claveInsertar);
                break;
                case 2:
                    System.out.print("Ingrese la clave a eliminar: ");
                    int claveEliminar = entrada.nextInt();
                    arbol.eliminar(claveEliminar);
                break;
                case 3:
                    System.out.print("Ingrese la clave a buscar: ");
                    int claveBuscar = entrada.nextInt();
                    boolean encontrado = arbol.buscar(claveBuscar);
                    System.out.println(encontrado ? "Clave encontrada." : "Clave no encontrada.");
                break;
                case 4:
                    System.out.println("----------- Arbol B ------------");
                    arbol.imprimirArbol();
                break;
                case 5:
                    System.out.println("Saliendo...");
                break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 5);
    }
}
