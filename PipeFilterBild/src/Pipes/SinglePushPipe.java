/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pipes;

import Filters.PushFilter;
import org.apache.log4j.Logger;

/**
 * Eine Pipe mit einem input, pushed Daten in eine Richtung
 * @author phil
 */
public class SinglePushPipe<T> implements PushPipe<T> {

    private PushFilter<T, ?> _destination;
    
    /**
     * Erzeugt eine neue Push pipe
     * @param destination 
     */
	public SinglePushPipe(PushFilter<T, ?> destination) {
		if (destination == null) {
			throw new IllegalArgumentException("destination must not be null!");
		}

		_destination = destination;
	}
    
    @Override
    public void push(T data) {
    //System.out.println("destination: " + _destination + "; data: " + data);
        Logger.getLogger(SinglePullPipe.class).debug("destination: " + _destination + "; data: " + data);
    _destination.newInput(data);
    }
    
}
