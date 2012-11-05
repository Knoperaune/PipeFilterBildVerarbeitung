/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Pipes.PullPipe;

/**
 * Implementierung eines PullFilters welcher vor der Pumpe ist
 * @author phil
 */
public class PullFilter<SourceType, DestinationType> extends Filter<SourceType, DestinationType> {
    protected PullPipe<SourceType> _source;
    
    	/**
         * Erzeugt neuen PullFilter
         * @param processor
         * @param source 
         */
	public PullFilter(GenericProcessor<SourceType, DestinationType> processor, PullPipe<SourceType> source) {
		super(processor);

		if (source == null) {
			throw new IllegalArgumentException("the source shall not be null!");
		}

		_source = source;
	}

        
        /**
	 * This method is called by the pipe which has this filter as source when a
	 * new processor output should be pulled.
	 * 
	 * @return a result of the processor or null if there are no more data
	 */
	public DestinationType getOutput() {
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

		return (DestinationType) getProcessor().getOutput();
	}
}
