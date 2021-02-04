/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocioArbolBinarioDeB;

import java.util.List;

/**
 *
 * @author USUARIO
 */
public interface IArbolBusqueda<K extends Comparable<K>,V> {
    void vaciar();
    boolean esArbolVacio();
    int size();
    int altura();
    int alturaRec();
    int cantidadDeHijoDerechoRec();
    int nivel();
    K minimo();
    K maximo();
    void insertar(K clave,V valor);
    void insertarRec(K clave,V valor)throws Exception;
    V eliminar(K clave);
    boolean eliminarNodo(K clave);
    boolean contiene(K clave);
    V buscar(K clave);
    V buscarRec(K clave);
    boolean tieneNodosCompletosEnNivel(int nivelObjetivo);
    List<K>recorridoEnInOrden();
    List<K>recorridoEnPreOrden();
    List<K>recorridoEnPostOrden();
    List<K>recorridoEnPostOrdenRec();
    List<K>recorridoEnPreOrdenRec();
    List<K>recorridoEnPorNiveles();
    void ImprimirArbol();
}
