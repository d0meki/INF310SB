/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocioArbolBinarioDeB;

/**
 *
 * @author USUARIO
 */
public class NodoBinario <K,V>{
    private K clave;
    private V valor;
    NodoBinario<K,V> hijoIzquierdo;
    NodoBinario<K,V> hijoDrecho;

    public NodoBinario(K clave,V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }

    public NodoBinario<K, V> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public NodoBinario<K, V> getHijoDrecho() {
        return hijoDrecho;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public void setHijoIzquierdo(NodoBinario<K, V> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public void setHijoDrecho(NodoBinario<K, V> hijoDrecho) {
        this.hijoDrecho = hijoDrecho;
    }
    
    public boolean esVacioHijoIzquierdo(){
    return NodoBinario.esNodoVacio(this.getHijoIzquierdo());// manera correcta  
    }
    
    public boolean esVacioHijoDerecho(){
    return NodoBinario.esNodoVacio(this.getHijoDrecho());// manera correcata
    }
    public boolean esHoja(){
    return this.esVacioHijoDerecho() && this.esVacioHijoIzquierdo();
    }
    
    public boolean esNodoCompleto(){
    return !this.esVacioHijoDerecho() && !this.esVacioHijoIzquierdo();
    }
   public static boolean esNodoVacio(NodoBinario nodo){ //se utiliza apra que devuelva una isntacia de clase
        return nodo == NodoBinario.nodoVacio();// ya sea falso o verdadero pero no nunca error
    }
   public static NodoBinario<?,?> nodoVacio(){// y va acompañado de este metodo
       return null;
   }
}
