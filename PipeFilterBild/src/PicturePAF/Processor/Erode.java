/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;

import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;

import Filters.GenericProcessor;

/**
 * Each time when there is a new input image, on the image the JAI erode operator will be executed with an 11x11 matrix. The new image will be then immediately
 * creates as a new output.
 */
public class Erode extends GenericProcessor<PlanarImage, PlanarImage> {

	@Override
	public void process(PlanarImage data) {
		//@formatter:off
		float[] kernelData = {
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		//@formatter:on

		KernelJAI kernel = new KernelJAI(11, 11, kernelData);

		ParameterBlock pb = new ParameterBlock();
		pb.addSource(data);
		pb.add(kernel);

		addOutput(JAI.create("erode", pb));
	}

	@Override
	public void flush() {
		// no-op.
	}

}
