/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pipes;

/**
 * Eine Pipe zum Daten Pushen
 * @author phil
 * @param <T> Datentyp für die übertragung
 */
public interface PushPipe <T> extends Pipe<T> {
    
    /**
     * Eine Methode zum Daten in die Pipe zu pushen
     * @param data 
     */
    public abstract void push(T data);
}
