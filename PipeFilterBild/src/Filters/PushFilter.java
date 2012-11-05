/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Pipes.PushPipe;

/**
 * Implementierung eines PushFilters welcher hinter der Pumpe ist
 * @author phil
 */
public class PushFilter<SourceType, DestinationType> extends Filter<SourceType, DestinationType>  {
    private PushPipe<DestinationType> _destination;
    
   /**
    * Erzeugt neuen PushFilter
    * @param processor
    * @param destination 
    */
	public PushFilter(GenericProcessor<SourceType,DestinationType> processor, PushPipe<DestinationType> destination) {
		super(processor);

		if (destination == null) {
			throw new IllegalArgumentException("destination shall not be null!");
		}

		_destination = destination;
	}
        
        /**
         * Gibt das Ziel zurück
         * @return 
         */
	protected PushPipe<DestinationType> getDestination() {
		return _destination;
	}
        
        /**
         * Aufgerufen wenn Daten in die Pipe gepusht werden
         * @param data 
         */
	public void newInput(SourceType data) {
		if (data == null) {
			getProcessor().flush();
		} else {
			getProcessor().process(data);
		}

		while (getProcessor().isThereOutput()) {
			_destination.push(((GenericProcessor<SourceType,DestinationType>)getProcessor()).getOutput());
		}

		if (data == null) {
			_destination.push(null);
		}
	}

}
