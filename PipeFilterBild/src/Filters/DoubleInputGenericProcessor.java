/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

/**
 *  Dieser Prozessor bietet die Möglichkeit zwei Quellen anzugeben
 * Ein Processor um die Daten der Quelle zu bearbeiten. Er macht die Arbeit für den Filter.
 * Sie gibt ermöglicht Zugriff auf die Daten nach dem Filterprozess 
 * @author phil
 * @param <SourceType1>Datentyp der ersten Quelle
 * @param <SourceType2>Datentyp der zweiten Quelle
 * @param <DestinationType> Datentyp der Senke
 */
public abstract class DoubleInputGenericProcessor<SourceType1,SourceType2,DestinationType> extends GenericProcessor<SourceType1, DestinationType> {

    /**
     * Wird von der zweiten Quelle aufgerufen um die Daten hinzuzufügen und zu verarbeiten
     * @param data 
     */
	public abstract void processDoubleInputProcessor(SourceType2 data);

    /**
     * Aufgerufen wenn die zweite Quelle keine Daten mehr zu Verarbeitung hat
     */
    public abstract void flushDoubleInputProcessor();
    
    /**
	 * Called within a pull pipeline to determine from which source data should be pulled.
	 * 
	 * @return the index of the input pipe to pull
	 */
	public abstract int getMissingSource();
}
