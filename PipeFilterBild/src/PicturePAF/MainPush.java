/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF;

import Filters.PullFilter;
import Filters.PushFilter;
import PicturePAF.Pipes.ImageViewerPullPipe;
import PicturePAF.Pipes.ImageViewerPullSource;
import PicturePAF.Pipes.ImageViewerPushDest;
import PicturePAF.Pipes.ImageViewerPushPipe;
import PicturePAF.Processor.CentroidProcessor;
import PicturePAF.Processor.DilateProcessor;
import PicturePAF.Processor.ErodeProcessor;
import PicturePAF.Processor.MedianProcessor;
import PicturePAF.Processor.ROIProcessor;
import PicturePAF.Processor.ThresholdProcessor;
import PicturePAF.Processor.XorProcessor;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.operator.MedianFilterDescriptor;

/**
 *
 * @author phil
 */
public class MainPush {

    public static void main(String[] args) {
        String path = "C://Users//phil//Desktop//FHV//5. Semester//Systemarchitekturen//Übung//PipesAndFiltersGraphical//PipeFilterBildVerarbeitung//PipeFilterBild//src//PicturePAF//loetstellen.jpg";
        ImageViewerPushDest dest = new ImageViewerPushDest();

        //xor
        PushFilter<PlanarImage, PlanarImage> xor = new PushFilter<>(new XorProcessor(path), dest);
        ImageViewerPushPipe xorpipe = new ImageViewerPushPipe(xor, "centroid");



        //centroid
        PushFilter<PlanarImage, PlanarImage> centroid = new PushFilter<>(new CentroidProcessor(), xorpipe);
        ImageViewerPushPipe centroidpipe = new ImageViewerPushPipe(centroid, "resultthreshold");

        //Result with threshold
        PushFilter<PlanarImage, PlanarImage> ResultThreshold = new PushFilter<>(new ThresholdProcessor(0, 254, 0), centroidpipe);
        ImageViewerPushPipe ResultThresholdPipe = new ImageViewerPushPipe(ResultThreshold, "Dilate -> Result");

        //Dilate
        PushFilter<PlanarImage, PlanarImage> dilate = new PushFilter<>(new DilateProcessor(), ResultThresholdPipe);
        ImageViewerPushPipe DilatePipe = new ImageViewerPushPipe(dilate, "Erode -> Opening Operator Dilate");

        //Opening Operator (erst erode dann dilate)
        //Erode
        PushFilter<PlanarImage, PlanarImage> erode = new PushFilter<>(new ErodeProcessor(), DilatePipe);
        ImageViewerPushPipe ErodePipe = new ImageViewerPushPipe(erode, "Median -> Opening Operator Erode");

        //median
        PushFilter<PlanarImage, PlanarImage> Median = new PushFilter<>(new MedianProcessor(MedianFilterDescriptor.MEDIAN_MASK_SQUARE, 5), ErodePipe);
        ImageViewerPushPipe MedianPipe = new ImageViewerPushPipe(Median, "Threshold -> Median");

        //Threshold
        PushFilter<PlanarImage, PlanarImage> Threshold = new PushFilter<>(new ThresholdProcessor(0, 30, 255), MedianPipe);
        ImageViewerPushPipe ThresholdPipe = new ImageViewerPushPipe(Threshold, "ROI -> Threshold");

        //Region of Interest
        PushFilter<PlanarImage, PlanarImage> ROI = new PushFilter<>(new ROIProcessor(), ThresholdPipe);
        ImageViewerPushPipe ROIpipe = new ImageViewerPushPipe(ROI, "Source -> Region of Interest");

        //Laden
        PlanarImage image = JAI.create("fileload", path);
        ROIpipe.push(image);
    }
}
