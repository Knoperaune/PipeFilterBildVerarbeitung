/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import java.util.LinkedList;
import java.util.NoSuchElementException;


/**
 * Ein Processor um die Daten der Quelle zu bearbeiten. Er macht die Arbeit für den Filter.
 * Sie gibt ermöglicht Zugriff auf die Daten nach dem Filterprozess 
 * 
 * @param <SourceType> Datentyp der Quelle
 * @param <DestinationType> Datentyp der Senke
 * @author phil
 */
public abstract class GenericProcessor<SourceType, DestinationType> {
    private LinkedList<DestinationType> _data;
    
    /**
     * Erzeugt neue Liste
     */
    public GenericProcessor(){
        _data = new LinkedList<DestinationType>();
    }
    
    /**
     * Zeigt ob Daten verarbeitet wurden
     */
    public boolean isThereOutput(){
        return !_data.isEmpty();
    }
    
    /**
     * Gibt das oberste Objekt zurückt und entfernt es, wenn keine vorhanden ist, kommt 'null' zurück
     */
    public DestinationType getOutput(){
        try {
          return _data.pop();
        } catch (NoSuchElementException  nsee) {
            return null;
        }
    }
    /**
     * Fügt Ausgabe der Liste hinzu die schon verarbeitet wurden
     * @param data 
     */
    public void addOutput(DestinationType data){
        _data.add(data);
    }
    
    /**
     * Fügt Daten der Liste hinzu welche verarbeitet werden müssen
     * @param data 
     */
    public abstract void process(SourceType data);
    
    /**
     *  Wird aufgerufen wenn kein Input mehr vorhanden ist
     */
    public abstract void flush();
}
