/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;


import Filters.GenericProcessor;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 * Each time when there is a new input image, a new image out of only one band of the input image will created as a new output.
 */
public class OneBand extends GenericProcessor<PlanarImage, PlanarImage> {

	private int _band;

	/**
	 * Creates a new one band processor
	 * 
	 * @param band the band which should be the result image
	 */
	public OneBand(int band) {
		_band = band;
	}

	@Override
	public void process(PlanarImage data) {
		addOutput(JAI.create("bandselect", data, new int[] { _band }));
	}

	@Override
	public void flush() {
		// no-op.
	}
}