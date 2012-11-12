/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;

import Filters.GenericProcessor;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;

/**
 *
 * @author phil
 */
public class DilateProcessor extends GenericProcessor<PlanarImage, PlanarImage> {

    @Override
    public void process(PlanarImage image) {
        /*float[] kernelData = {
         0, 0, 1, 0, 0,
         0, 1, 1, 1, 0,
         1, 1, 1, 1, 1,
         0, 1, 1, 1, 0,
         0, 0, 1, 0, 0};
         KernelJAI kernel = new KernelJAI(5, 5, kernelData);*/
        float[] kernelData = {
            0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
            0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
            0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
            0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
            0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
            0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,};

        KernelJAI kernel = new KernelJAI(11, 11, kernelData);


        ParameterBlock pb = new ParameterBlock();

        pb.addSource(image);

        pb.add(kernel);

        addOutput(JAI.create("dilate", pb));
    }

    @Override
    public void flush() {
    }
}
