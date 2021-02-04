/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocioArbolBinarioDeB;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author USUARIO
 */
public class AVL<K extends Comparable<K>,V>extends ArbolBinarioBusqueda<K,V> {
    private static final byte DIFERECIA_MAXIMA = 1;
    private int saltos;
    public String grafica;
    private double tiempo=0;
    public AVL(){
        this.saltos=0;
        this.grafica="";
        this.tiempo=0;
    }
    public int getSaltos() {
        return saltos;
    }

        public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }
    public void setSaltos(int saltos) {
        this.saltos = saltos;
    }
    private void iniciarT(){
        this.setTiempo(System.nanoTime());
    }
    private void finalizarT(){
        this.setTiempo((System.nanoTime()-this.getTiempo())/1000000);
    }
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (valorAInsertar == null) {
            throw new IllegalArgumentException("Valor no puede ser nula");
        }
//        if (super.contiene(claveAInsertar)) {
//            throw new RuntimeException("clave ya existe");
//        } se elimina por que el insertar remplaza
        super.raiz = this.insertar(this.raiz,claveAInsertar, valorAInsertar);//super hacer referencia al padre de esta clase
    }

    private NodoBinario<K, V> insertar(NodoBinario<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
         NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar,valorAInsertar);   
         return nuevoNodo;
        }
        K claveActual = nodoActual.getClave();
        if (claveAInsertar.compareTo(claveActual)>0) {
            NodoBinario<K,V> supuestoNuevoHijoDerecho = insertar(nodoActual.getHijoDrecho(),
                                                                 claveAInsertar,valorAInsertar);
            nodoActual.setHijoDrecho(supuestoNuevoHijoDerecho);
            return balancear(nodoActual);
        }
        if (claveAInsertar.compareTo(claveActual)<0) {
            NodoBinario<K,V> supuestoNuevoHijoIzquierdo = insertar(nodoActual.getHijoIzquierdo(),
                                                                 claveAInsertar,valorAInsertar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }
        //si llego aca quiere decir que en el nodo actual esta la clave a insertar
        nodoActual.setValor(valorAInsertar);
        return nodoActual;
    }

    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
        int alturaRamaIzq = alturaR(nodoActual.getHijoIzquierdo());
        int alturaRamaDer = alturaR(nodoActual.getHijoDrecho());
        int diferencia = alturaRamaIzq - alturaRamaDer;  
        if (diferencia > DIFERECIA_MAXIMA) {
            //si ay que valancear
            NodoBinario<K,V> hijoIzquierdo = nodoActual.getHijoIzquierdo();
            alturaRamaIzq = alturaR(nodoActual.getHijoIzquierdo());
            alturaRamaDer = alturaR(nodoActual.getHijoDrecho());
            if (alturaRamaDer>alturaRamaIzq) {
                return rotacionDobleADerecha(nodoActual);
            }else{
                return rotacionSimpleADerecha(nodoActual);
            }
            
        }else if(diferencia < -DIFERECIA_MAXIMA){ //desbalance por Der -> Rotacion Izquierda
                     NodoBinario<K,V> nodoHijoDer = nodoActual.getHijoDrecho();
                        //ver si hay rotacion doble por izquierda
                     alturaRamaIzq = alturaR(nodoHijoDer.getHijoIzquierdo());
                     alturaRamaDer = alturaR(nodoHijoDer.getHijoIzquierdo());
                       if(alturaRamaIzq > alturaRamaDer){
                          return rotacionDobleAIzquierda(nodoActual); 
                       }
                       // hace rotacion simple por izquierda
                     return rotacionSimpleAIzquierda(nodoActual);   
        } 
        return nodoActual;
    }
    //reotacion simple a derecha
//    private NodoBinario<K,V> rotacionSimpleADerecha(NodoBinario<K,V> nodoActual){
//        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoIzquierdo();//agarro el hijo izquierdo y lo guardo
//        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDrecho());// al nodo actual le cambio su hijo izquierdo por el hijo derecho del nodo que rota
//        nodoQueRota.setHijoDrecho(nodoActual);// y al nodo que rota hay q cambiarle su hijo derecho
//        //porque sino todos apuntarian al mismo nodo... el nodo que rota se convertiria en el padre del que era su papa
//        return nodoQueRota;//y lo retorno eso
//    }
    //rotacion doble a derecha
//    private NodoBinario<K,V> rotacionDobleADerecha(NodoBinario<K,V> nodoActual){
//        nodoActual.setHijoIzquierdo(rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo()));
//        return this.rotacionSimpleADerecha(nodoActual);
//    }
//    private NodoBinario<K,V> rotacionSimpleAIzquierda(NodoBinario<K,V> nodoActual){
//        return null;    
//    }
//    private NodoBinario<K,V> rotacionDobleAIzquierda(NodoBinario<K,V> nodoActual){
//        return null;    
//    }
         private NodoBinario<K,V> rotacionDobleADerecha(NodoBinario<K,V> nodoProblema){
               // trabaja con 3 nodos hijo y nieto
             NodoBinario<K,V> nodoHijoIzq = nodoProblema.getHijoIzquierdo();
             NodoBinario<K,V> nodoNietoDer = rotacionSimpleAIzquierda(nodoHijoIzq);
             nodoProblema.setHijoIzquierdo(nodoNietoDer);
             
             return rotacionSimpleADerecha(nodoProblema);
       }
       private NodoBinario<K,V> rotacionDobleAIzquierda(NodoBinario<K,V> nodoProblema){
            // trabaja con 3 nodos hijo y nieto
           NodoBinario<K,V> nodoHijoDer = nodoProblema.getHijoDrecho();
           NodoBinario<K,V> nodoNietoIzq = rotacionSimpleADerecha(nodoHijoDer);
           nodoProblema.setHijoDrecho(nodoNietoIzq);
           return rotacionSimpleAIzquierda(nodoProblema);
           
       } 
       private NodoBinario<K,V> rotacionSimpleADerecha(NodoBinario<K,V> nodoProblema){
          NodoBinario<K,V> nodoHijoIzq = nodoProblema.getHijoIzquierdo();
          NodoBinario<K,V> nodoNietoDer = nodoHijoIzq.getHijoDrecho();
          nodoProblema.setHijoIzquierdo(nodoNietoDer);
          nodoHijoIzq.setHijoDrecho(nodoProblema);
          
          return nodoHijoIzq;
      } 
      private NodoBinario<K,V> rotacionSimpleAIzquierda(NodoBinario<K,V> nodoProblema){
                 NodoBinario<K,V> nodoHijoDer = nodoProblema.getHijoDrecho();
                 NodoBinario<K,V> nodoNietoIzq = nodoHijoDer.getHijoIzquierdo();
                  nodoProblema.setHijoDrecho(nodoNietoIzq);
                  nodoHijoDer.setHijoIzquierdo(nodoProblema);
               
                 return nodoHijoDer;
      }  
      public List<K> recorridoEnPorNiveles() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);//si no es vacio empieza con el nodo raiz--offer inserta un elemento a la cola
        while(!colaDeNodos.isEmpty()){//iteramos sobre la cola
            NodoBinario<K,V> nodoActual = colaDeNodos.poll();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDrecho());
            }
        }
        return recorrido;
    } 
      public void ImprimirArbol(){
        int m=alturaR(raiz);
        this.setSaltos(0);
        System.out.println(ImprimirArbol(raiz,m,m));
    }   
    private String ImprimirArbol(NodoBinario<K,V> aux,int alt,int ele){
        if(NodoBinario.esNodoVacio(aux)){
            return "";
        }
        this.setSaltos(this.getSaltos()+1);
        String Der=ImprimirArbol(aux.getHijoDrecho(), alt, ele-1);
        //String m="";
        String q="    ";
        String s="";
        int i=0;
        while((alt-ele)!=i){
            i++;
            s=s/*+m*/+q;
        }
        s=Der+s+"|"+String.valueOf(aux.getClave())+"|"+'\12';
        String Izq=ImprimirArbol(aux.getHijoIzquierdo(), alt, ele-1);
        s=s+Izq;
        return s;       
    }
      public static void main(String[] args) throws Exception {
      AVL<Integer,Integer> Arbavl = new AVL<>();
      Arbavl.insertar(2, 95);
      Arbavl.insertar(1, 34);
      Arbavl.insertar(3, 66);
      Arbavl.insertar(4, 67);
      Arbavl.insertar(5, 68);
      System.out.println(Arbavl.recorridoEnPorNiveles()); Arbavl.recorridoEnPorNiveles();
      Arbavl.ImprimirArbol();
      }
}

