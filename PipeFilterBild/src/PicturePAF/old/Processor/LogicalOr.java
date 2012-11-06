/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.old.Processor;

import Filters.DoubleInputGenericProcessor;
import java.util.LinkedList;
import java.util.Queue;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 * A processor which generates a new image out of to input images using the JAI logical or operator.
 */
public class LogicalOr extends DoubleInputGenericProcessor<PlanarImage, PlanarImage, PlanarImage> {

	private Queue<PlanarImage> _imagesSourceOne;
	private Queue<PlanarImage> _imagesSourceTwo;

	/**
	 * Creates a new logical or processor
	 */
	public LogicalOr() {
		_imagesSourceOne = new LinkedList<PlanarImage>();
		_imagesSourceTwo = new LinkedList<PlanarImage>();
	}

	@Override
	public void process(PlanarImage data) {
		_imagesSourceOne.add(data);
		processImpl();
	}

	@Override
	public void processDoubleInputProcessor(PlanarImage data) {
		_imagesSourceTwo.add(data);
		processImpl();
	}

	/**
	 * As long there are images from both inputs, this method will generate new output images.
	 */
	private void processImpl() {
		while (!_imagesSourceOne.isEmpty() && !_imagesSourceTwo.isEmpty()) {
			addOutput(JAI.create("or", _imagesSourceOne.poll(), _imagesSourceTwo.poll()));
		}
	}

	@Override
	public void flush() {
		flushImpl();
	}

	@Override
	public void flushDoubleInputProcessor() {
		flushImpl();
	}

	/**
	 * When there are images only from one source, the image will be flushed without ORing it to another.
	 */
	private void flushImpl() {
		if (!_imagesSourceOne.isEmpty() && !_imagesSourceTwo.isEmpty()) {
			processImpl();
		}

		while (!_imagesSourceOne.isEmpty()) {
			addOutput(_imagesSourceOne.poll());
		}

		while (!_imagesSourceTwo.isEmpty()) {
			addOutput(_imagesSourceTwo.poll());
		}
	}

	@Override
	public int getMissingSource() {
		if (_imagesSourceOne.isEmpty()) {
			return 1;
		}

		return 2;
	}
}
