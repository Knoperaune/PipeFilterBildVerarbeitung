/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Pipes.PullPipe;

/**
 *
 * @author phil
 */
public class DoubleInputPullFilter <S1, S2, D> extends PullFilter<S1, D> {

	private PullPipe<S2> _source2;

	/**
	 * Creates a new two input pulled filter
	 * 
	 * @param processor the processor for this filter
	 * @param source1 the pipe from where the pump pulls data which will put into the processor by calling {@link TwoInputProcessor#process(Object)}.
	 * @param source2 the pipe from where the pump pulls data which will put into the processor by calling {@link TwoInputProcessor#process2(Object)}.
	 */
	public DoubleInputPullFilter(DoubleInputGenericProcessor<S1,S2,D> processor, PullPipe<S1> source1, PullPipe<S2> source2) {
		super(processor, source1);

		_source2 = source2;
	}

	@Override
	public D getOutput() {
		while (!((DoubleInputGenericProcessor<S1,S2,D>)getProcessor()).isThereOutput()) {

			int source = ((DoubleInputGenericProcessor<S1,S2,D>)getProcessor()).getMissingSource();

			if (feedProcessor(source)) {
				break;
			}

		}

		return ((DoubleInputGenericProcessor<S1,S2,D>)getProcessor()).getOutput();
	}

	/**
	 * Feeds the processor with data from the given source.<br>
	 * When the source has no more data, the corresponding flush method on the processor will be calles.
	 * 
	 * @param source the source to pull from.
	 * @return True if no more data could be pulled.
	 * @throws IndexOutOfBoundsException when the source is not known
	 */
	protected boolean feedProcessor(int source) {
		switch (source) {
			case 1:
				S1 sourceData = _source.pull();
				if (sourceData == null) {
					((DoubleInputGenericProcessor<S1,S2,D>)getProcessor()).flush();
					return true;
				} else {
					((DoubleInputGenericProcessor<S1,S2,D>)getProcessor()).process(sourceData);
				}
				break;
			case 2:
				S2 sourceData2 = _source2.pull();
				if (sourceData2 == null) {
					((DoubleInputGenericProcessor<S1,S2,D>)getProcessor()).flushDoubleInputProcessor();
					return true;
				} else {
					((DoubleInputGenericProcessor<S1,S2,D>)getProcessor()).processDoubleInputProcessor(sourceData2);
				}
				break;
			default:
				throw new IndexOutOfBoundsException("Source #" + source + " unknown.");
		}

		return false;
	}

}