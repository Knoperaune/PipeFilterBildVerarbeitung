/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;

import Filters.GenericProcessor;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.media.jai.operator.MedianFilterShape;

/**
 *
 * @author phil
 */
public class MedianProcessor extends GenericProcessor<PlanarImage, PlanarImage> {

    MedianFilterShape _shape;
    int _size;
    
    
    public MedianProcessor(MedianFilterShape shape, int size) {
        _shape = shape;
        _size = size;
    }
    
    @Override
    public void process(PlanarImage image) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(image);
        pb.add(_shape);
        pb.add(_size);
        addOutput(JAI.create("MedianFilter", pb));
    }

    @Override
    public void flush() {
    }
    
}
