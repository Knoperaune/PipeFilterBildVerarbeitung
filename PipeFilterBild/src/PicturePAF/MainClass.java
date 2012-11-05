/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF;

import PicturePAF.Pipes.ImageViewerDoubleOutputPushPipe;
import PicturePAF.Pipes.ImageViewerPullSource;
import PicturePAF.Pipes.ImageViewerPushDest;
import PicturePAF.Pipes.ImageViewerPullPipe;
import PicturePAF.Pipes.ImageViewerPushPipe;
import PicturePAF.Processor.Threshold;
import PicturePAF.Processor.Dilate;
import PicturePAF.Processor.POI;
import PicturePAF.Processor.OneBand;
import PicturePAF.Processor.Erode;
import PicturePAF.Processor.Median;
import PicturePAF.Processor.LogicalOr;
import Filters.DoubleInputPullFilter;
import Filters.DoubleInputPushFilter;
import Filters.PullFilter;
import Filters.PushFilter;
import Pipes.PullPipe;
import java.awt.Rectangle;
import java.util.Scanner;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;

/**
 *
 * @author Kno
 */
public class MainClass {

    private POI _poiProcessor = new POI(new Rectangle(0, 60, 430, 60));
    private OneBand _oneBandProcessor = new OneBand(0);
    private Threshold _thresholdProcessor = new Threshold(0, 30, 255);
    private Median _medianProcessor = new Median(MedianFilterDescriptor.MEDIAN_MASK_SQUARE, 5);
    private Threshold _bwProcessor = new Threshold(0, 254, 0);
    private Erode _erodeProcessor = new Erode();
    private Dilate _dilateProcessor = new Dilate();
    private LogicalOr _logicalOrProcessor = new LogicalOr();

    /**
     * Creates a push pipeline and pushes the given image on the source.
     *
     * @param image the image to push to the source
     */
    public void pushMain(PlanarImage image) {
        ImageViewerPushDest dest = new ImageViewerPushDest();

        // last filter --> logical or

        DoubleInputPushFilter<PlanarImage, PlanarImage, PlanarImage> logicalOrFilter = new DoubleInputPushFilter<PlanarImage, PlanarImage, PlanarImage>(
                _logicalOrProcessor, dest);

        ImageViewerPushPipe pipe = new ImageViewerPushPipe(logicalOrFilter, "dilate --> logical or");

        // dilate
        PushFilter<PlanarImage, PlanarImage> dilateFilter = new PushFilter<PlanarImage, PlanarImage>(_dilateProcessor,
                pipe);
        pipe = new ImageViewerPushPipe(dilateFilter, "erode --> dilate");

        // erode
        PushFilter<PlanarImage, PlanarImage> erodeFilter = new PushFilter<PlanarImage, PlanarImage>(_erodeProcessor, pipe);
        pipe = new ImageViewerPushPipe(erodeFilter, "black white threshold --> erode");

        // black-white threshold
        PushFilter<PlanarImage, PlanarImage> bwFilter = new PushFilter<PlanarImage, PlanarImage>(_bwProcessor, pipe);
        pipe = new ImageViewerPushPipe(bwFilter, "median --> black white threshold");

        // median
        PushFilter<PlanarImage, PlanarImage> medianFilter = new PushFilter<PlanarImage, PlanarImage>(_medianProcessor,
                pipe);
        pipe = new ImageViewerPushPipe(medianFilter, "threshold --> median");

        // threshold
        PushFilter<PlanarImage, PlanarImage> thresholdFilter = new PushFilter<PlanarImage, PlanarImage>(
                _thresholdProcessor, pipe);

        // switch to threshold and logicalor
        pipe = new ImageViewerDoubleOutputPushPipe(thresholdFilter, logicalOrFilter, "one band --> threshold and logical or");

        // convert to one band
        PushFilter<PlanarImage, PlanarImage> oneBandFilter = new PushFilter<PlanarImage, PlanarImage>(
                _oneBandProcessor, pipe);
        pipe = new ImageViewerPushPipe(oneBandFilter, "POI --> one band");

        // first filter --> POI filter
        PushFilter<PlanarImage, PlanarImage> poiFilter = new PushFilter<PlanarImage, PlanarImage>(_poiProcessor, pipe);

        // source
        ImageViewerPushPipe source = new ImageViewerPushPipe(poiFilter, "source");

        source.push(image);

    }
    
    /**
	 * Creates a pulled pipeline and adds the given image to the source pipe. Finally it will pull the destination of the pipe.
	 * 
	 * @param image the source image
	 */
	public void pullMain(PlanarImage image) {

		PullPipe<PlanarImage> pipe = new ImageViewerPullSource(image);

		// POI
		PullFilter<PlanarImage, PlanarImage> poiFilter = new PullFilter<PlanarImage, PlanarImage>(_poiProcessor, pipe);

		pipe = new ImageViewerPullPipe(poiFilter, "POI --> oneBand");

		// one Band

		PullFilter<PlanarImage, PlanarImage> oneBandFilter = new PullFilter<PlanarImage, PlanarImage>(
				_oneBandProcessor, pipe);

		ImageViewerPullPipe switchPipe = new ImageViewerPullPipe(oneBandFilter, "oneBand --> threshold");

		// threshold
		PullFilter<PlanarImage, PlanarImage> thresholdFilter = new PullFilter<PlanarImage, PlanarImage>(
				_thresholdProcessor, switchPipe);

		pipe = new ImageViewerPullPipe(thresholdFilter, "threshold --> median");

		// median
		PullFilter<PlanarImage, PlanarImage> medianFilter = new PullFilter<PlanarImage, PlanarImage>(_medianProcessor,
				pipe);

		pipe = new ImageViewerPullPipe(medianFilter, "median --> bw threshold");

		// bw threshold
		PullFilter<PlanarImage, PlanarImage> bwThresholdFilter = new PullFilter<PlanarImage, PlanarImage>(
				_bwProcessor, pipe);

		pipe = new ImageViewerPullPipe(bwThresholdFilter, "bw threshold --> erode");

		// erode
		PullFilter<PlanarImage, PlanarImage> erodeFilter = new PullFilter<PlanarImage, PlanarImage>(_erodeProcessor, pipe);

		pipe = new ImageViewerPullPipe(erodeFilter, "erode --> dilate");

		// dilate
		PullFilter<PlanarImage, PlanarImage> dilateFilter = new PullFilter<PlanarImage, PlanarImage>(_dilateProcessor,
				pipe);

		pipe = new ImageViewerPullPipe(dilateFilter, "dilate --> logical or");

		DoubleInputPullFilter<PlanarImage, PlanarImage, PlanarImage> logicalOrFilter = new DoubleInputPullFilter<PlanarImage, PlanarImage, PlanarImage>(
				_logicalOrProcessor, pipe, switchPipe);

		pipe = new ImageViewerPullPipe(logicalOrFilter, "oneBand --> threshold");

		pipe.pull();

	}


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean done = false;

        while (!done) {

            String fileName = /*"loetstellen.jpg"*/
                        "C://Users//phil//Documents//NetBeansProjects//PipeFilterBildVerarbeitung//PipeFilterBild//src//PicturePAF//loetstellen.jpg";


            PlanarImage image = JAI.create("fileload", fileName);

            MainClass main = new MainClass();

            if (image != null) {
                System.out.print("Use pus(h) or pul(l): ");

                String input = scanner.nextLine().trim();
                if (input.equals("h")) {
                    main.pushMain(image);
                    done = true;
                } else if (input.equals("l")) {
                    main.pullMain(image);
                    done = true;
                } else {
                    System.out.println("Unknown input.");
                    System.out.println();
                }
            }
        }
    }
}
