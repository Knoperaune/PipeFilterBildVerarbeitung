/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;

import Filters.GenericProcessor;

import java.awt.Rectangle;

import javax.media.jai.PlanarImage;

/**
 *
 * @author phil
 */
public class POI  extends GenericProcessor<PlanarImage, PlanarImage> {

	private Rectangle _poi;

	/**
	 * Creates a new POI processor
	 * 
	 * @param poi the region which should be contained in the output
	 */
	public POI(Rectangle poi) {
		_poi = poi;
	}

	@Override
	public void process(PlanarImage data) {
		addOutput(PlanarImage.wrapRenderedImage(data.getAsBufferedImage(_poi, data.getColorModel())));
	}

	@Override
	public void flush() {
		// no-op.
	}

}
