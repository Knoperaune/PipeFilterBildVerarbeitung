/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pipes;

/**
 * Eine Pipe zum Daten pullen
 * @author phil
 * @param <T> Datentyp für die übertragung
 */
public interface PullPipe<T> extends Pipe<T> {
    /**
     * Methode um Daten aufzurufen
     * @return die verfübgaben Daten oder null, wenn nichts da ist.
     */
    public abstract T pull();
}
