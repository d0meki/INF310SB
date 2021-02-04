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
import java.util.Stack;
import javax.swing.JPanel;

/**
 *
 * @author USUARIO
 */
public class ArbolBinarioBusqueda <K extends Comparable<K>,V> 
implements IArbolBusqueda<K,V>{
    
    protected NodoBinario<K,V> raiz;
    private int saltos;
    public String grafica;
    private double tiempo=0;
    public ArbolBinarioBusqueda(){
        this.raiz = null;
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
    public boolean estaVacio(){
        return raiz==null;
    }
    /*
        instancia un arbol reconstruyendo en base a sus recorridos inOrden y (preorden o postorden)
        Si el parametro usando PreOrden es verdadero los parametros clavesNoInOrden y valoresNoInOrden tendran  
        el recorrido en preorden del arbol, caso contrario serane l post orden
    
        Lo que se quiere hacer con esto es recontruir el arbol en base a esos recorridos
    */
    public ArbolBinarioBusqueda(List<K>clavesInOrden,List<V> valoresInOrden,
                                List<K>clavesNoInOrden,List<V> valoresNoInOrden,
                                boolean usandoPreOrden){
       //validadcion es vacia
        if (clavesInOrden.isEmpty() ||
            clavesNoInOrden.isEmpty()||
            valoresInOrden.isEmpty()||
            valoresNoInOrden.isEmpty()) {
            throw new IllegalArgumentException("Los parametros no pueden ser vacios");
        }
        //validadcion es nulla
        if (clavesInOrden==null ||
            clavesNoInOrden==null||
            valoresInOrden==null||
            valoresNoInOrden==null) {
            throw new IllegalArgumentException("Los parametros no pueden ser nulos");
        }
        //validadcion es de dierente tamaño
        if (clavesInOrden.size()!= clavesNoInOrden.size()||
            clavesInOrden.size() != valoresInOrden.size()||
            clavesInOrden.size() != valoresNoInOrden.size()    ) {
            throw new IllegalArgumentException("Los parametros no pueden ser listas con diferentes tamaños");
        }
        if (usandoPreOrden) {
          this.raiz =  reconstruirConPreOrden(clavesInOrden,valoresInOrden,clavesNoInOrden,valoresNoInOrden);
        }else{
           this.raiz = reconstruirConPostOrden(clavesInOrden,valoresInOrden,clavesNoInOrden,valoresNoInOrden);
        }
    
    }
    private NodoBinario<K,V> reconstruirConPreOrden(List<K> clavesInOrden,List<V> valoresInOrden,
                                        List<K> clavesEnPreOrden,List<V> valoresEnPreOrden){
        if (clavesInOrden.isEmpty()) {
            return (NodoBinario<K, V>) NodoBinario.nodoVacio();
        }
        //optiene posicion clave padre en pre orden
        int posocionDeClavePAdreEnPreOrden = 0;
        K clavePadre = clavesEnPreOrden.get(posocionDeClavePAdreEnPreOrden);
        V valorPadre = valoresEnPreOrden.get(posocionDeClavePAdreEnPreOrden);
        //optiene posicion clave padre en in orden
        int posocionDeClavePAdreEnInOrden = this.posicionDeClaves(clavePadre, clavesInOrden);
    
        //para armar la rama izquierda
        List<K> clavesInOrdenPorIzquierda = clavesInOrden.subList(0, posocionDeClavePAdreEnInOrden);
        List<V> valoresInOrdenPorIzquierda = valoresInOrden.subList(0, posocionDeClavePAdreEnInOrden);    
        
        List<K> clavesPreOrdenPorIzquierda = clavesEnPreOrden.subList(0, posocionDeClavePAdreEnInOrden);
        List<V> valoresPreOrdenPorIzquierda = valoresEnPreOrden.subList(0, posocionDeClavePAdreEnInOrden);  
        NodoBinario<K,V> hijoIzquierdo = reconstruirConPreOrden(clavesInOrdenPorIzquierda,valoresInOrdenPorIzquierda,
                                                                clavesPreOrdenPorIzquierda,valoresPreOrdenPorIzquierda);
        
        //para armar la rama derecha
        
        //armando el nodoActual
        NodoBinario<K,V> nodoActual = new NodoBinario<>(clavePadre,valorPadre);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        //nodoActual.setHijoDerecho(hijoDerecho);
        return nodoActual;
    }
    private NodoBinario<K,V> reconstruirConPostOrden(List<K> clavesInOrden,List<V> valoresInOrden,
                                         List<K> clavesEnPostOrden,List<V> valoresEnPostOrden){
        
        return null;
    }
    private int posicionDeClaves(K claveABuscar, List<K> listaDeClaves){
        for (int i = 0; i < listaDeClaves.size(); i++) {
            K claveActual = listaDeClaves.get(i);
            if (claveActual.compareTo(claveABuscar)==0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void vaciar() {
        this.raiz =(NodoBinario<K,V>) NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int size() {//cuantos nodos tiene el arbol
        if (this.esArbolVacio()) {
            return 0;
        }
        int cantidadDeNodos = 0;
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);//si no es vacio empieza con el nodo raiz--offer inserta un elemento a la cola
        while(!pilaDeNodos.isEmpty()){//iteramos sobre la cola
            NodoBinario<K,V> nodoActual = pilaDeNodos.pop();
            cantidadDeNodos++;
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDrecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return cantidadDeNodos;
    }

    @Override
    public int alturaRec() {//La altura es el nivel + 1 del nodo mas profundo en el arbol
        return alturaR(this.raiz);
    }
    protected int alturaR(NodoBinario<K,V> nodoActual){
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaPorIzquierda = alturaR(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = alturaR(nodoActual.getHijoDrecho());
//        if (alturaPorIzquierda > alturaPorDerecha ) {
//            return alturaPorIzquierda + 1;
//        }else{
//            return alturaPorDerecha + 1;
//        }
        return alturaPorIzquierda > alturaPorDerecha? alturaPorIzquierda + 1: alturaPorDerecha + 1;
    }
    @Override
    public int altura(){
        if (this.esArbolVacio()) {
            return 0;
        }
        int alturaDelArbol = 0;
        Queue<NodoBinario<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);//si no es vacio empieza con el nodo raiz--offer inserta un elemento a la cola
        while(!colaDeNodos.isEmpty()){//iteramos sobre la cola
            int cantidadDeNodosEnLaCola = colaDeNodos.size();// para controlar la cantidad de nodos 
            int i =0;//que habia con el bucle principal
            while(i<cantidadDeNodosEnLaCola){
                NodoBinario<K,V> nodoActual = colaDeNodos.poll();
                if (!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if (!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDrecho());
                }
                i++;
            }//con este while se que estoy procesando todo un nivel
            alturaDelArbol++;
        }
        return alturaDelArbol;
    }

    @Override
    public int nivel() {//El nivel es el nivel mas profundo en el arbol
        return nivelR(this.raiz);
    }
    private int nivelR(NodoBinario<K,V> nodoActual){
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return -1;
        }
        int nivelPorIzquierda = nivelR(nodoActual.getHijoIzquierdo());
        int nivelPorDerecha = nivelR(nodoActual.getHijoDrecho());
        if (nivelPorIzquierda > nivelPorDerecha ) {
            return nivelPorIzquierda + 1;
        }else{
            return nivelPorDerecha + 1;
        }
        //return alturaPorIzquierda > alturaPorDerecha? alturaPorIzquierda + 1: alturaPorDerecha + 1;
    }

    @Override
    public K minimo() {//La menor clave posible que hay en el arbol
        if (this.esArbolVacio()) {
            return null;
        }
        NodoBinario<K,V> nodoActual = this.raiz;
        NodoBinario<K,V> nodoAnterior = (NodoBinario<K,V>) NodoBinario.nodoVacio();//empieza en el vacio
        while(!NodoBinario.esNodoVacio(nodoActual)){
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior.getClave();
    }
    /*
    vamos a hacer un metodo que retorne si un arbol binario tiene nodos completos
    es decir nodos que tengan sus dos hijos diferentes de vacio en el nivel n
    */
    @Override
    public boolean tieneNodosCompletosEnNivel(int nivelObjetivo){
        return  tieneNodosCompletosEnNivelR(this.raiz,nivelObjetivo,0);
    }
    
    private boolean tieneNodosCompletosEnNivelR(NodoBinario<K,V> nodoActual,int nivelObjetivo,int nivelActual){
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return false;
        }
        if (nivelActual == nivelObjetivo) { //encuentra que el nivel sea completo
            return !nodoActual.esVacioHijoIzquierdo()&&
                    !nodoActual.esVacioHijoDerecho();
        }
        boolean completoPorIzq = this.tieneNodosCompletosEnNivelR(nodoActual.getHijoIzquierdo(),nivelObjetivo,nivelActual+1);
        boolean completoPorDer = this.tieneNodosCompletosEnNivelR(nodoActual.getHijoDrecho(),nivelObjetivo,nivelActual+1);
        return completoPorIzq && completoPorDer;
    }
    @Override
    public K maximo() {//tarea  --- en vez de hijo izquierdo hacer por el derecho
        if (this.esArbolVacio()) {
            return null;
        }
        NodoBinario<K,V> nodoActual = this.raiz;
        NodoBinario<K,V> nodoAnterior = (NodoBinario<K,V>) NodoBinario.nodoVacio();//empieza en el vacio
        while(!NodoBinario.esNodoVacio(nodoActual)){
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoDrecho();
        }
        return nodoAnterior.getClave();
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (claveAInsertar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (valorAInsertar == null) {
            throw new IllegalArgumentException("Valor no puede ser nula");
        }
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(claveAInsertar,valorAInsertar);
            return;
        }
        NodoBinario<K,V> nodoActual = this.raiz;//empeiza en la raiz
        NodoBinario<K,V> nodoAnterior = (NodoBinario<K,V>) NodoBinario.nodoVacio();//empieza en el vacio
        
        while(!NodoBinario.esNodoVacio(nodoActual)){// mientras nodo actual sea diferente de vacio
            K claveActual = nodoActual.getClave();//se crea la variable y se asigna la clave del nodo actual
            nodoAnterior = nodoActual;
            if (claveAInsertar.compareTo(claveActual)<0) { //si es menor
                nodoActual = nodoActual.getHijoIzquierdo();
            }else if(claveAInsertar.compareTo(claveActual)>0){//si es mayor
                nodoActual = nodoActual.getHijoDrecho();
            }else{ //si es igual, es decir la clave ya existe, entonces remplazo su valor solamente
                nodoActual.setValor(valorAInsertar);
                return;
            }
        }
        //si llego hasta este punto, quiere decir que no existe en el arbo
        //la clave a insertar, entonces debo crear un nodo, con la clave y valor a insertar
        //y el nodo anterior es el padre de este nuevo nodo
        NodoBinario<K,V> nodoNuevo = new NodoBinario<>(claveAInsertar,valorAInsertar);
        K clavePadre = nodoAnterior.getClave();//se crea la variable y se asigna la clave del nodo actual
        if (claveAInsertar.compareTo(clavePadre)<0) { //si es menor
            nodoAnterior.setHijoIzquierdo(nodoNuevo);
        }else{
            nodoAnterior.setHijoDrecho(nodoNuevo);
        }   

    }
    @Override
    public void insertarRec(K clave, V valor)throws Exception {
        raiz=insertarRecursivo(clave,valor,raiz);
    }
    private NodoBinario insertarRecursivo(K clave,V valor,NodoBinario<K,V> nodoActual)throws Exception{
        if(NodoBinario.esNodoVacio(nodoActual)){//estaVacio(nodoAct)
            return new NodoBinario<K,V>(clave,valor); 
        }       
        if(clave.compareTo(nodoActual.getClave())<0){
            nodoActual.setHijoIzquierdo(insertarRecursivo(clave,valor,nodoActual.getHijoIzquierdo())); //
            return nodoActual;
        }else{
            if(clave.compareTo(nodoActual.getClave())>0){
                nodoActual.setHijoDrecho(insertarRecursivo(clave,valor,nodoActual.getHijoDrecho())); 
                return nodoActual;
            }else{
                throw new Exception("Dato Duplicado");
            }
        }
    }

    @Override
    public V eliminar(K claveAEliminar) {
//        if (claveAEliminar == null) {
//            throw new IllegalArgumentException("la clave a eliminar no puede ser nulo");
//        }
//        V valorAnterior = this.buscar(claveAEliminar);
//        if (valorAnterior==null) {
//            throw new IllegalArgumentException("la clave a eliminar no existe en el Arbol");
//            //en vez de al excepcion tambn se puede retornar nulo
//        }
//        this.raiz = eliminarR(this.raiz,claveAEliminar);
//        return valorAnterior;
        return null;
    }
    @Override
    public boolean eliminarNodo(K clave){
        try {
            this.raiz = eliminarR(raiz, clave);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    private NodoBinario<K,V> eliminarR(NodoBinario<K,V> nodoActual, K datoAEliminar) throws Exception {

        if (NodoBinario.esNodoVacio(nodoActual)) {
            throw new Exception();
        }
        K datoDelNodoActual = nodoActual.getClave();
        if (datoAEliminar.compareTo(datoDelNodoActual) > 0) {
            NodoBinario<K,V> nuevoHijoDerecho = eliminarR(nodoActual.getHijoDrecho(), datoAEliminar);
            nodoActual.setHijoDrecho(nuevoHijoDerecho);
            return nodoActual;
        }
        if (datoAEliminar.compareTo(datoDelNodoActual) < 0) {
            NodoBinario<K,V> nuevoHijoIzquierdo = eliminarR(nodoActual.getHijoIzquierdo(), datoAEliminar);
            nodoActual.setHijoIzquierdo(nuevoHijoIzquierdo);
            return nodoActual;
        }
        //  encontro al datoAEliminar 
        //caso 1
        if (nodoActual.esHoja()) {
            return (NodoBinario<K, V>) NodoBinario.nodoVacio();
        }
        //caso 2
        if (nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDrecho();
        }
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        //caso 3
        NodoBinario<K,V> datoSucesor = buscarDatoSucesor(nodoActual.getHijoDrecho());  //  dato sucesor 
        NodoBinario<K,V> nuevoNodoHijoDerecho = eliminarR(nodoActual.getHijoDrecho(), datoSucesor.getClave()); // cae un caso base 1 o 2
        nodoActual.setHijoDrecho(nuevoNodoHijoDerecho);
        nodoActual.setClave(datoSucesor.getClave());
        nodoActual.setValor(datoSucesor.getValor());
        return nodoActual;

    }

    /**
     * busca dato sucesor para eliminar un dato en el arbol
     *
     * @param nodoActual
     * @return
     */
    protected NodoBinario<K,V> buscarDatoSucesor(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoAnterior = nodoActual;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior;
    }
//    private NodoBinario<K,V> eliminarR(NodoBinario<K,V> nodoActual,K claveAEliminar){
//        if (NodoBinario.esNodoVacio(nodoActual)) {
//            return null;
//        }
//        K claveActual = nodoActual.getClave();
//        if (claveAEliminar.compareTo(claveActual)>0) {//clave a eliminar esta por el lado derecho
//            NodoBinario<K,V> posibleNuevoHijoDerecho = eliminarR(nodoActual.getHijoDrecho(),claveAEliminar);
//            nodoActual.setHijoDrecho(posibleNuevoHijoDerecho); // si vino el mismo se lo estoy recorfdando
//            //pero si vino otro le estoy diciendo cambio tu hijo
//            return nodoActual;
//        }
//        //clave a eliminar esta por el lado izquierdo
//        if (claveAEliminar.compareTo(claveActual)<0) {//clave a eliminar esta por el lado derecho
//            NodoBinario<K,V> posibleNuevoHijoIzquierdo = eliminarR(nodoActual.getHijoIzquierdo(),claveAEliminar);
//            nodoActual.setHijoDrecho(posibleNuevoHijoIzquierdo); // si vino el mismo se lo estoy recorfdando
//            //pero si vino otro le estoy diciendo cambio tu hijo
//            return nodoActual;
//        }
//        //si la ejecucion llego a este punto quiere decir que ya 
//        //encontro el nodo donde esta la clave que quiero eliminar
//        
//        //CASO 1: entonces hay que preguntar si es hoja
//        
//        if (nodoActual.esHoja()) {
//            return (NodoBinario<K,V>) (NodoBinario.nodoVacio());
//        }
//        //CASO 2: 
//        //CASO 2.1: solo tiene  hijo izquierdo
//        if (!nodoActual.esVacioHijoIzquierdo() //Si no es vacio el hijo izquiero
//                && nodoActual.esVacioHijoDerecho()) {//pero si el derecho por lo tanto solo tiene hijo izquierdo
//            return nodoActual.getHijoIzquierdo();
//        }
//        //CASO 2.2: solo tiene  hijo Derecho
//        if (!nodoActual.esVacioHijoDerecho() //Si no es vacio el hijo derecho
//                && nodoActual.esVacioHijoIzquierdo()) {//pero si el izquierdo por lo tanto solo tiene hijo derecho
//            return nodoActual.getHijoDrecho();
//        }
//        //CASO 3: tiene sus dos hijos
//        NodoBinario<K,V>nodoReemplazo = this.buscarNodoSucesor(nodoActual.getHijoDrecho());
//        //ya encontre el nodo remplazo o suscesor y con eso tengo 2 opciones
//        //usarlo este nodo reemplazo o solo usar su dato y su clave para reemplazarlo en el nodo actual
//        //ademas posiblemente cambiaria  algun hijo
//        NodoBinario<K,V> posibleNuevoHijoDerecho = eliminarR(nodoActual.getHijoDrecho(),
//                                                             nodoReemplazo.getClave() );//aqui eliminamos. este pude ser CASO1,CASO2
//        // nodo actual,opsion 1: le cambio la clave  el valor al nodo actual y lo dejo donde esta
//        //opsion 2: o lo desenlaso del nodo actual tanto su hijo izquierdo o su hijo derecho y
//        //utilizo el nodo reemplazo con el nuevo nodo
//        //en este caso lo mas faccil es remplazarlo
//        nodoActual.setHijoDrecho(posibleNuevoHijoDerecho); // Actualizar el hijo derecho por si cambio al hacer la eliminacion anterior
//                
//        nodoActual.setClave(nodoReemplazo.getClave());
//        nodoActual.setValor(nodoReemplazo.getValor());
//        /* ----Para ser mas formales-----
//        nodoReemplazo.setHijoDrecho(...);
//        nodoReemplazo.setHijoIzquierdo(...);
//       */
//        return nodoActual;
//    }
//    private NodoBinario<K,V> buscarNodoSucesor(NodoBinario<K,V> nodoActual){
//        NodoBinario<K,V> nodoAnterior=nodoActual;
//        
//        while(!NodoBinario.esNodoVacio(nodoActual)){
//        nodoAnterior = nodoActual; //guarda
//        nodoActual.getHijoIzquierdo();//muevo al hijo izquierdo
//        }
////        do{//por que si o si va entrar al menos una vez
////        nodoAnterior = nodoActual; //guarda
////        nodoActual.getHijoIzquierdo();//muevo al hijo izquierdo
////        }while (!NodoBinario.esNodoVacio(nodoActual));
//        return nodoAnterior;
//    }

    @Override
    public boolean contiene(K clave) { //funciona igual que el existe llama al metodo buscar, si devueve un dato null retorna falso 
        return this.buscar(clave) != null;//y si returna algun otro valor retornaria verdadero
    }

    @Override
    public V buscar(K claveABuscar) {// para saber que valor tiene la llave a buscar
        if (claveABuscar == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (this.esArbolVacio()) {
            return null;
        }
         NodoBinario<K,V> nodoActual = this.raiz;
         while(!NodoBinario.esNodoVacio(nodoActual)){// mientras nodo actual sea diferente de vacio
            K claveActual = nodoActual.getClave();//se crea la variable y se asigna la clave del nodo actual
      
            if (claveABuscar.compareTo(claveActual)== 0) {
                return nodoActual.getValor(); //retorna el valor que contiene esa clave
   
            }else if (claveABuscar.compareTo(claveActual)< 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            }else{
               nodoActual = nodoActual.getHijoDrecho();                
            }
         }
         //si llego a este punto quiere decir que no se ecnuentra la clave a buscar
         //en el arbol
        return null;
    }
    @Override
    public V buscarRec(K clave){
        if (clave == null) {
            throw new IllegalArgumentException("Clave no puede ser nula");
        }else{
            return buscarRecrusivo(clave,raiz);
        } 
    } 
    private V buscarRecrusivo(K claveABuscar,NodoBinario<K,V> nodoActual){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return null;
        }
        if(claveABuscar.compareTo(nodoActual.getClave())==0){
            return nodoActual.getValor();
        }else{
            if(claveABuscar.compareTo(nodoActual.getClave())<0){
                return buscarRecrusivo(claveABuscar, nodoActual.getHijoIzquierdo());
            }else{
                return buscarRecrusivo(claveABuscar, nodoActual.getHijoDrecho());
            }
        }
    }
    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new ArrayList<>();
        //para una implementacion recursiva se necesita
        //un metodo amigo que haga el grueso trabajo
        recorridoEnInOrdenR(this.raiz,recorrido);
        return recorrido;
    }
    private void recorridoEnInOrdenR(NodoBinario<K,V> nodoActual, List<K> recorrido){
        //simulamos el n para un caso base
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnInOrdenR(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrdenR(nodoActual.getHijoDrecho(), recorrido);
    }
    @Override
    public List<K> recorridoEnPostOrdenRec(){
        List<K> recorrido = new ArrayList<>();
        recorridoEnPostOrdenR(this.raiz,recorrido);
        return recorrido;
    }
    private void recorridoEnPostOrdenR(NodoBinario<K,V> nodoActual, List<K> recorrido){
        //simulamos el n para un caso base
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnPostOrdenR(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPostOrdenR(nodoActual.getHijoDrecho(), recorrido);
        recorrido.add(nodoActual.getClave());
       
    }
    

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);//si no es vacio empieza con el nodo raiz--offer inserta un elemento a la cola
        while(!pilaDeNodos.isEmpty()){//iteramos sobre la cola
            NodoBinario<K,V> nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDrecho());
            }
            if (!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new ArrayList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
        NodoBinario<K,V> nodoActual = this.raiz;
        //el proceso inicail antes de iterar en la pila
        meterPilaParaPostOrden(pilaDeNodos,nodoActual);
        
        //empezamos a iterar sobre la pila
        while(!pilaDeNodos.isEmpty()){
            nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K,V> nodoTope = pilaDeNodos.peek();//devuelve el elemento que esta en la cima de la pila
                if (!nodoTope.esVacioHijoDerecho()&& nodoTope.getHijoDrecho() != nodoActual) {
                    /*volver a hacer el mismo bucle inicial*/
                    meterPilaParaPostOrden(pilaDeNodos,nodoTope.getHijoDrecho());
                }
            }
        }
        return recorrido;
    }
    private void meterPilaParaPostOrden(Stack<NodoBinario<K,V>> pilaDeNodos, NodoBinario<K,V> nodoActual){
        while(!NodoBinario.esNodoVacio(nodoActual)){//iteramos sobre la cola
            pilaDeNodos.push(nodoActual);
           //recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            }else{
                nodoActual = nodoActual.getHijoDrecho();
            }   
        }
    }

    @Override
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

    @Override
    public List<K> recorridoEnPreOrdenRec() {
        List<K> recorrido = new ArrayList<>();
        //para una implementacion recursiva se necesita
        //un metodo amigo que haga el grueso trabajo
        recorridoEnPreOrdenR(this.raiz,recorrido);
        return recorrido;
    }
    private void recorridoEnPreOrdenR(NodoBinario<K,V> nodoActual, List<K> recorrido){
        //simulamos el n para un caso base
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorrido.add(nodoActual.getClave());
        recorridoEnPreOrdenR(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPreOrdenR(nodoActual.getHijoDrecho(), recorrido);
    }

    @Override
    public int cantidadDeHijoDerechoRec() {
        return cantidadDeHijoDerechoR(this.raiz);
    }
    private int cantidadDeHijoDerechoR(NodoBinario<K,V> nodoActual){
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        //esto calcula la cantidad de hijos derechos por la rama izquierda y por la rama derecha
        int hdPorRamaIzq = cantidadDeHijoDerechoR(nodoActual.getHijoIzquierdo());
        int hdPorRamaDer =cantidadDeHijoDerechoR(nodoActual.getHijoDrecho());
        if (!nodoActual.esVacioHijoDerecho()) {
            return hdPorRamaIzq + hdPorRamaDer + 1;
        }
        return hdPorRamaIzq + hdPorRamaDer;
        
    }
    @Override
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
    
}
