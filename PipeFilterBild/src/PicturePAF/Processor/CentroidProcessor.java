/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.Processor;

import Filters.GenericProcessor;
import java.awt.RenderingHints;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.JAI;
import javax.media.jai.ParameterBlockJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.ROI;
import javax.media.jai.RenderedOp;
import javax.media.jai.registry.RenderedRegistryMode;

/**
 *
 * @author phil
 */
public class CentroidProcessor extends GenericProcessor<PlanarImage, PlanarImage>  {

    @Override
    public void process(PlanarImage image) {
        /*ParameterBlock pb = new ParameterBlock();
        pb.addSource(image);
        addOutput(JAI.create("centroid", pb));*/
        
        /*
        ParameterBlockJAI pbj = new ParameterBlockJAI(
			"centroid", RenderedRegistryMode.MODE_NAME
		);
        pbj.setSource("Source0", image);
        ROI roi = new ROI(image);
        pbj.setParameter("roi", roi);
       RenderedOp asdf = JAI.create("centroid", pbj);
       addOutput(image);*/
       ParameterBlock pb = new ParameterBlock();
                ROI roi = new ROI(JAI.create("bandselect", image, new int[] { 0 }));
		pb.add(roi);
                pb.addSource(image); 

		PlanarImage centroidImage = JAI.create("centroid", pb, RenderingHints.KEY_ANTIALIASING);
                addOutput(centroidImage);
        
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
