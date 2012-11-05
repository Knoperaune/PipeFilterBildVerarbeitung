/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Pipes.PushPipe;

/**
 *
 * @author phil
 */
public class DoubleInputPushFilter<SourceType1,SourceType2,DestinationType> extends PushFilter<SourceType1, DestinationType> {

        /**
         * Erzeugt neuen PushFilter mit doppelten Input
         * @param processor
         * @param destination 
         */
        public DoubleInputPushFilter(DoubleInputGenericProcessor<SourceType1,SourceType2,DestinationType> processor, PushPipe<DestinationType> destination){
        super(processor, destination);
        }
        
        /**
	 * Called when the predecessor filter for source #2 pushes data into the
	 * pipe
	 * 
	 * @param data
	 *            the pushed input data
	 */
	public void newInput2(SourceType2 data) {
		if (data == null) {
			((DoubleInputGenericProcessor<SourceType1,SourceType2,DestinationType>)getProcessor()).flushDoubleInputProcessor();
		} else {
                    ((DoubleInputGenericProcessor<SourceType1,SourceType2,DestinationType>)getProcessor()).processDoubleInputProcessor(data);
		}

		while (getProcessor().isThereOutput()) {
			getDestination().push(((DoubleInputGenericProcessor<SourceType1,SourceType2,DestinationType>)getProcessor()).getOutput());
		}

		if (data == null) {
			getDestination().push(null);
		}
	}
}
