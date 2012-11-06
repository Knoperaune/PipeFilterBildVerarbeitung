/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.old.Processor;

import Filters.GenericProcessor;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;


/**
 * Each time when there is a new input image, on the image the JAI dilate operator will be executed with an 5x5 matrix. The new image will be then immediately
 * creates as a new output.
 */
public class Dilate extends GenericProcessor<PlanarImage, PlanarImage> {

	@Override
	public void process(PlanarImage data) {

		//@formatter:off
		float[] kernelData = {
				0, 0, 1, 0, 0,
				0, 1, 1, 1, 0,
				1, 1, 1, 1, 1,
				0, 1, 1, 1, 0,
				0, 0, 1, 0, 0 };
		//@formatter:on

		KernelJAI kernel = new KernelJAI(5, 5, kernelData);

		ParameterBlock pb = new ParameterBlock();
		pb.addSource(data);
		pb.add(kernel);

		addOutput(JAI.create("dilate", pb));
	}

	@Override
	public void flush() {
		// no-op.
	}
}
