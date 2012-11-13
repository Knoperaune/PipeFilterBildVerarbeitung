/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;

import Filters.GenericProcessor;
import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 *
 * @author phil
 */
public class XorProcessor extends GenericProcessor<PlanarImage, PlanarImage> {

    PlanarImage original;

    public XorProcessor(String pathToOriginal) {
        PlanarImage temp = JAI.create("fileload", pathToOriginal);
        Rectangle rectangle = new Rectangle(0,40, temp.getWidth(), 120);
        original = PlanarImage.wrapRenderedImage((RenderedImage)temp.getAsBufferedImage(rectangle, temp.getColorModel()));
    }
    
    
    @Override
    public void process(PlanarImage image) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(image);
        pb.addSource(original);
        addOutput(JAI.create("xor", pb));
        JAI.create("filestore", JAI.create("xor", pb), "finishedpull.jpg", "JPEG");
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
