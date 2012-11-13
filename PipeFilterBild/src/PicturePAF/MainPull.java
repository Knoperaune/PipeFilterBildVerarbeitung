/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PicturePAF;

import Filters.PullFilter;
import PicturePAF.Pipes.ImageViewerPullPipe;
import PicturePAF.Pipes.ImageViewerPullSource;
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
import javax.swing.text.PlainDocument;

/**
 *
 * @author phil
 */
public class MainPull {

    public static void main(String[] args) {
        String path = "C://Users//phil//Desktop//FHV//5. Semester//Systemarchitekturen//Übung//PipesAndFiltersGraphical//PipeFilterBildVerarbeitung//PipeFilterBild//src//PicturePAF//loetstellen.jpg";

        //Laden
        PlanarImage image = JAI.create("fileload", path);
        ImageViewerPullSource source = new ImageViewerPullSource(image);
        
        //Region of Interest
        PullFilter<PlanarImage, PlanarImage> ROI = new PullFilter<>(new ROIProcessor(), source);
        ImageViewerPullPipe ROIpipe = new ImageViewerPullPipe(ROI, "Source -> Region of Interest");
        
        //Threshold
        PullFilter<PlanarImage,PlanarImage> Threshold = new PullFilter<>(new ThresholdProcessor(0, 30, 255), ROIpipe);
        ImageViewerPullPipe ThresholdPipe = new ImageViewerPullPipe(Threshold, "ROI -> Threshold");
        
        //median
        PullFilter<PlanarImage, PlanarImage> Median = new PullFilter<>(new MedianProcessor(MedianFilterDescriptor.MEDIAN_MASK_SQUARE, 5), ThresholdPipe);
        ImageViewerPullPipe MedianPipe = new ImageViewerPullPipe(Median, "Threshold -> Median");
                
        //Opening Operator (erst erode dann dilate)
        //Erode
        PullFilter<PlanarImage,PlanarImage> erode = new PullFilter<>(new ErodeProcessor(), MedianPipe);
        ImageViewerPullPipe ErodePipe = new ImageViewerPullPipe(erode, "Median -> Opening Operator Erode");
        
        //Dilate
        PullFilter<PlanarImage,PlanarImage> dilate = new PullFilter<>(new DilateProcessor(), ErodePipe);
        ImageViewerPullPipe DilatePipe = new ImageViewerPullPipe(dilate, "Erode -> Opening Operator Dilate");
        
        //Result with threshold
        PullFilter<PlanarImage,PlanarImage> ResultThreshold = new PullFilter<>(new ThresholdProcessor(0, 254, 0), DilatePipe);
        ImageViewerPullPipe ResultThresholdPipe = new ImageViewerPullPipe(ResultThreshold, "Dilate -> Result");
        
        //Centroid
        PullFilter<PlanarImage, PlanarImage> centroid = new PullFilter<>(new CentroidProcessor(), ResultThresholdPipe);
        ImageViewerPullPipe centroidpipe = new ImageViewerPullPipe(centroid, "Result -> Centroid");
        
        //XOR
        PullFilter<PlanarImage, PlanarImage> xor = new PullFilter<>(new XorProcessor(path), centroidpipe);
        ImageViewerPullPipe xorpipe = new ImageViewerPullPipe(xor, "Centroid -> Result");
        
        xorpipe.pull();
        
    }
}
