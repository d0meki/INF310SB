/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parbolbinariodebusqueda;

import javax.swing.JOptionPane;
import negocioArbolBinarioDeB.AVL;
import negocioArbolBinarioDeB.ArbolBinarioBusqueda;
import negocioArbolBinarioDeB.IArbolBusqueda;

/**
 *
 * @author USUARIO
 */
public class ParbolBinarioDeBusqueda {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
           IArbolBusqueda <Integer, Integer> arbolb = new ArbolBinarioBusqueda<>();
          arbolb.insertar(2, 11);
          arbolb.insertar(1, 22);
          arbolb.insertar(3, 33);
          arbolb.insertar(4, 34);
          arbolb.insertar(5, 35);
//        arbolb.insertar(75, 1);
//        arbolb.insertar(60, 2);
//        arbolb.insertar(43, 3);
//        arbolb.insertar(68, 4);
//        arbolb.insertar(90, 5);
//        arbolb.insertar(70, 6);
//        arbolb.insertar(85, 7);
//        arbolb.insertar(22, 8);
//        arbolb.insertar(56, 9);
/*INSERCION RECURSIVA*/
//        arbolb.insertarRec(68, 4);
//        arbolb.insertarRec(90, 5);
//        arbolb.insertarRec(70, 6);
//        arbolb.insertarRec(85, 7);
//        arbolb.insertarRec(22, 8);
//        arbolb.insertarRec(56, 9);
        //arbolb.insertar(92, 10);
        System.out.println("Recorrido por Niveles: "+arbolb.recorridoEnPorNiveles());
        System.out.println("Recorrido en PreOrden: "+arbolb.recorridoEnPreOrden());
        System.out.println("Recorrido en PostOrden: "+arbolb.recorridoEnPostOrden());
        System.out.println("Recorrido en InOrden recursivo: "+arbolb.recorridoEnInOrden());
        System.out.println("Recorrido en PostOrden recursivo: "+arbolb.recorridoEnPostOrdenRec());
        System.out.println("Recorrido en PreOrden recursivo: "+arbolb.recorridoEnPreOrdenRec());
//        int valorAbuscar = Integer.parseInt(JOptionPane.showInputDialog("Digita una llave abuscar")); 
//        System.out.println("El valor de la llave "+valorAbuscar+" es "+arbolb.buscar(valorAbuscar));
//        int valorAbuscar = Integer.parseInt(JOptionPane.showInputDialog("Digita una llave abuscar")); 
//        System.out.println("El valor de la llave "+valorAbuscar+" es "+arbolb.buscarRec(valorAbuscar));
        System.out.println("La cantidad de nodos es: " + arbolb.size());
        System.out.println("La llave con menor valor es: "+arbolb.minimo());
        System.out.println("La llave con mayor valor es: "+arbolb.maximo());
        System.out.println("Altura del Arbol es: " + arbolb.altura());
        System.out.println("Altura Recursiva del Arbol es: " + arbolb.alturaRec());
        System.out.println("El nivel del arbol es: "+arbolb.nivel());
//        int nivelAbuscar = Integer.parseInt(JOptionPane.showInputDialog("Digita el nivel abuscar")); 
//        System.out.println("tine nodo completo? en el nivel " + nivelAbuscar + " " + arbolb.tieneNodosCompletosEnNivel(nivelAbuscar));
       // int valorAEliminar = Integer.parseInt(JOptionPane.showInputDialog("Digita una llave a eliminar")); 
       // arbolb.eliminarNodo(valorAEliminar);
       //arbolb.eliminarNodo(22);
        //System.out.println("Recorrido por Niveles: "+arbolb.recorridoEnPorNiveles());
        arbolb.ImprimirArbol();
    }
    
}
