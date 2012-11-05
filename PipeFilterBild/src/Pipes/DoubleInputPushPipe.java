/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pipes;

import Filters.DoubleInputPushFilter;

/**
 *
 * @author phil
 */
public class DoubleInputPushPipe<S2> implements PushPipe<S2> {

private DoubleInputPushFilter<?,S2,?> _destination;

    /**
     * Erzeugt neue doppel Input Pushpipe
     * @param destination 
     */
    public DoubleInputPushPipe(DoubleInputPushFilter<?,S2,?> destination) {
            if (destination == null) {
                    throw new IllegalArgumentException("destination must not be null!");
            }

            _destination = destination;
    }
    
    @Override
    public void push(S2 data) {
        _destination.newInput2(data);
    }
    
}
