/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;

import Filters.GenericProcessor;
import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import javax.media.jai.PlanarImage;

/**
 *
 * @author phil
 */
public class ROIProcessor extends GenericProcessor<PlanarImage, PlanarImage> {

    @Override
    public void process(PlanarImage image) {
        Rectangle rectangle = new Rectangle(0,40, image.getWidth(), 120);
        image = PlanarImage.wrapRenderedImage((RenderedImage)image.getAsBufferedImage(rectangle, image.getColorModel()));
        addOutput(image);
    }

    @Override
    public void flush() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
