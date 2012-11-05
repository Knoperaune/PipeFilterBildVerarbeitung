/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Pipes.PullPipe;
import Pipes.PushPipe;

/**
 * Dies ist die "Pumpe" für die Pipeline. Immer when (@link #doit()) aufgerufen wird, wird das Ergebnis einen Schritt weitergegeben
 * @author phil
 * @param <SourceType> Datentyp der Quelle
 * @param <DestinationType> Datentyp der Senke
 */
public class Pump<SourceType, DestinationType> extends Filter<SourceType, DestinationType>{
    private PullPipe<SourceType> _source;
    private PushPipe<DestinationType> _destination;
    
    /**
     * Erzeugt neue Pumpe
     * @param processor
     * @param source
     * @param destination 
     */
	public Pump(GenericProcessor<SourceType, DestinationType> processor, PullPipe<SourceType> source,
			PushPipe<DestinationType> destination) {
		super(processor);

		if (source == null) {
			throw new IllegalArgumentException("source shall not be null!");
		}
		if (destination == null) {
			throw new IllegalArgumentException("destination shall not be null!");
		}

		_source = source;
		_destination = destination;
	}
        
        /**
	 * Pulls data until the processor has a new result and then pushes this
	 * result down side the pipe.
	 * 
	 * @return true when data were pushed down side the pipeline
	 */
	public boolean doit() {
		SourceType sourceData;

		while (!getProcessor().isThereOutput()) {
			sourceData = _source.pull();

			if (sourceData == null) {
				getProcessor().flush();
				break;
			} else {
				getProcessor().process(sourceData);
			}
		}

		DestinationType destData = (DestinationType) getProcessor().getOutput();
		_destination.push(destData);

		return (destData != null);
	}

}
