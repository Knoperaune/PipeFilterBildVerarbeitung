/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

/**
 * 
 * @author phil
 * @param <SourceType> Datentyp der Quelle
 * @param <DestinationType> Datentyp der Senke
 */
public abstract class Filter<SourceType, DestinationType> {
    /**
     * Der Processor des Filters
     */
    private GenericProcessor<SourceType, DestinationType> _processor;

    /**
     * Erzeut neuen Filter
     * @param processor 
     */
    public Filter(GenericProcessor<SourceType,DestinationType> processor) {
            if (processor == null) {
                    throw new IllegalArgumentException("process must not be null!");
            }

            _processor = processor;
    }
    /**
     * Gibt den Processor des Filters zurück
     * @return 
     */
    public GenericProcessor getProcessor() {
            return _processor;
    }
}
