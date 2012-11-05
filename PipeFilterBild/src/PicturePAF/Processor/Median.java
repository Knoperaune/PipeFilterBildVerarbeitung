/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;

import Filters.GenericProcessor;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterShape;
/**
 * Each time when there is a new input image, on the image the JAI median operator will be executed. The new image will be then immediately created as a new
 * output.
 */
public class Median extends GenericProcessor<PlanarImage, PlanarImage> {

	private MedianFilterShape _shape;
	private int _size;

	/**
	 * Creates a new median processor
	 * 
	 * @param shape The shape of the mask to be used for Median Filtering
	 * @param size The size (width and height) of the mask to be used in Median Filtering.
	 */
	public Median(MedianFilterShape shape, int size) {
		_shape = shape;
		_size = size;
	}

	@Override
	public void process(PlanarImage data) {
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(data);
		pb.add(_shape);
		pb.add(_size);

		addOutput(JAI.create("medianfilter", pb));
	}

	@Override
	public void flush() {
		// no-op.
	}

}