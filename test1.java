package opencv;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.math.BigDecimal;

import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.Imgproc;

public class test1
{
   public static void main( String[] args )
   {
	  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	  Mat mat_o=Imgcodecs.imread("C:/Users/93709/Downloads/19_07_10/19_07_10/Process_5798 - TR.tif");
	  Mat mat_p= new Mat();
	  Imgproc.cvtColor(mat_o, mat_p, Imgproc.COLOR_RGB2GRAY);
	  Mat mask=Mat.zeros(mat_p.size(), 0);
      double maxvalue=0;
      for(int x=0;x<mat_p.rows();x++) {
    	  for(int y=0;y<mat_p.cols();y++) {
    		  double[] data=mat_o.get(x,y);
    		  if (data[0]>maxvalue){
    			  maxvalue=data[0];
    			  
    		  }
    		 
    		  
    	  }
      }
      for(int x=0;x<mat_p.rows();x++) {
    	  for(int y=0;y<mat_p.cols();y++) {
    		  double[] data1=mat_o.get(x,y);
    		  data1[0]=data1[0]*255/maxvalue;
    		  mask.put(x,y,data1);
    	  }
      }
      Mat gau=new Mat();
      Imgproc.GaussianBlur(mask, gau, new Size(3,3), 0,0);
      
      
      Mat processed=Mat.zeros(gau.size(), 0);
      int j,k;
      
      
      for(int m=0;m<=900;) {
    	  for(int n=0;n<=1200;) {
    		  double sum=0;
    		  Mat temp=Mat.zeros(gau.size(), 0);
    		  List<Double> gray_value= new ArrayList<>();
    		  for(j=m;j<=m+100;j++) {
    	    	  for(k=n;k<=n+100;k++) {
    	    		  double[] data_g=gau.get(j, k);
    	    		  //System.out.println(data_g[0]);
    	    		  gray_value.add(data_g[0]);
    	    		  temp.put(j,k,data_g[0]);
    	    		  BigDecimal s1=new BigDecimal(Double.toString(sum));
    	    		  BigDecimal s2=new BigDecimal(Double.toString(data_g[0]));
    	    		  sum=s1.add(s2).doubleValue();
    	    	  }
    	      }
    	      Collections.sort(gray_value);
    	      Mat temp_t=new Mat();
    	      double threshold= 1.2*sum/gray_value.size();
    	      System.out.println(threshold);
    	      if (threshold>35) {
    	    	  Imgproc.threshold(temp, temp_t, threshold, 255, Imgproc.THRESH_BINARY);
    	    	  Core.bitwise_or(processed,temp_t,processed);
    	      }
    	      
    	      n=n+25;
    	  }
    	  m=m+25;
      }
      /*for(int x=400;x<=500;x++) {
    	  for(int y=264;y<=364;y++) {
    		  double[] data_g=gau.get(x,y);
    		  gray_value.add(data_g[0]);
    		  temp.put(x,y,data_g[0]);
    	  }
      }
      Collections.sort(gray_value);
      Mat temp_t=new Mat();
      double threshold= gray_value.get(gray_value.size()/3);
      if (threshold>30) {
    	  Imgproc.threshold(temp, temp_t, threshold, 255, Imgproc.THRESH_BINARY);
      }
      System.out.println(threshold);
      Imgcodecs.imwrite("D:/gau.jpg", gau);
      List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
      Mat hierarchy = new Mat();
      Imgproc.findContours(temp_t, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      Imgproc.drawContours(mat_o, contours, -1,new Scalar(0,255,0));
      Imgproc.rectangle(gau, new Point(264,400), new Point(364,500), new Scalar(255,255,255));
      HighGui.imshow("gau",gau);
      HighGui.imshow("mask",temp_t);
      HighGui.imshow("mat_o",mat_o);*/
      HighGui.imshow("final", processed);
      HighGui.waitKey(0);
	
   }
     
}
