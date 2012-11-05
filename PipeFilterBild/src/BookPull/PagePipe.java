/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BookPull;

import BookPAFF.ConsoleBook;
import Filters.PullFilter;
import Pipes.SinglePullPipe;
import Pipes.SinglePushPipe;

/**
 *
 * @author phil
 */
public class PagePipe {
    SinglePullPipe<CharSequence> _source;
    ConsoleBook _destination;

    public PagePipe(SinglePullPipe<CharSequence> source, ConsoleBook destination) {
        _source = source;
        _destination = destination;
    }
    
    public boolean doit() {
		CharSequence sourceData;

		while (/*!_source.getProcessor().isThereOutput()*/(sourceData = _source.pull())==null) {
			/*sourceData = _source.getOutput();
                        sourceData = _source.pull();*/
			if (sourceData == null) {
				//_source.getProcessor().flush();
				break;
			} else {
				//_source.getProcessor().process(sourceData);
			}
		}

		CharSequence destData = (CharSequence) sourceData;//_source.getProcessor().getOutput();
		_destination.push(destData);

		return (destData != null);
	}
    
    
}
