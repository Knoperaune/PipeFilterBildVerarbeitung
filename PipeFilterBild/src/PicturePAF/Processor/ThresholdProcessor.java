/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;

import Filters.GenericProcessor;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import org.omg.Dynamic.Parameter;

/**
 *
 * @author phil
 */
public class ThresholdProcessor extends GenericProcessor<PlanarImage, PlanarImage> {

    double _low;
    double _high;
    double _map;

    @SuppressWarnings("empty-statement")
    public ThresholdProcessor(double low, double high, double map) {
        _low = low;
        _high = high;
        _map = map;
    }

    @Override
    public void process(PlanarImage image) {

        double[] low = {_low};
        double[] high = {_high};
        double[] map = {_map};

        ParameterBlock pb = new ParameterBlock();
        pb.addSource(image);
        pb.add(low);
        pb.add(high);
        pb.add(map);
        addOutput(JAI.create("threshold", pb));
    }

    @Override
    public void flush() {
        
    }
}
