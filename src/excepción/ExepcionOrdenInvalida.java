/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepción;

/**
 *
 * @author USUARIO
 */
public class ExepcionOrdenInvalida extends Exception{
    public ExepcionOrdenInvalida(){
        super("Arbol con orden invalido");
    }
    public ExepcionOrdenInvalida(String message){
        super(message);
    }
}
