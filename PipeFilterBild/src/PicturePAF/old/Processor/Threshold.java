/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF.old.Processor;
import Filters.GenericProcessor;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
/**
 *
 * @author phil
 */
public class Threshold  extends GenericProcessor<PlanarImage, PlanarImage> {

	private double _low;
	private double _high;
	private double _map;

	/**
	 * Creates a new threshold processor
	 * 
	 * @param low the low level
	 * @param high the high level
	 * @param map the constant the pixels will be mapped to
	 */
	public Threshold(double low, double high, double map) {
		_low = low;
		_high = high;
		_map = map;
	}

	@Override
	public void process(PlanarImage data) {
		double[] low = { _low };
		double[] high = { _high };
		double[] map = { _map };

		ParameterBlock pb = new ParameterBlock();
		pb.addSource(data);
		pb.add(low);
		pb.add(high);
		pb.add(map);

		addOutput(JAI.create("threshold", pb));
	}

	@Override
	public void flush() {
		// no-op.
	}

}
