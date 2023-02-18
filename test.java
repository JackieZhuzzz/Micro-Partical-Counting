package opencv;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

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
public class test
{
   public static void main( String[] args )
   {
	  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	  Mat mat_o=Imgcodecs.imread("C:/Users/93709/Downloads/19_08_08/19_08_08/Process_6071 - TR.tif");
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
      Mat threshold= new Mat();
      Imgproc.threshold(gau, threshold, 52, 255, Imgproc.THRESH_BINARY);
      List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
      Mat hierarchy = new Mat();
      Imgproc.findContours(threshold, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      Mat final_cell= Mat.zeros(mat_o.size(), 0);
      for(int i=0;i<contours.size();) {
    	  Mat temp= Mat.zeros(mat_o.size(), 0);
    	  if(Imgproc.contourArea(contours.get(i))>10000) {
    		  Imgproc.drawContours(temp,contours,i,new Scalar(255,255,255),-1);
    		  Core.bitwise_and(temp, gau, temp);
    		  Imgproc.threshold(temp, temp, 90, 255, Imgproc.THRESH_BINARY);
    	  }
    	  else{
    		  Imgproc.drawContours(temp,contours,i,new Scalar(255,255,255),-1);
    	  }
    	  Core.bitwise_or(final_cell,temp,final_cell);
    	  i++;
      }
      List<MatOfPoint> contours_1 = new ArrayList<MatOfPoint>();
      Mat hierarchy_1 = new Mat();
      Imgproc.findContours(final_cell, contours_1, hierarchy_1, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      Mat final_cell_1= Mat.zeros(mat_o.size(), 0);
      for(int i=0;i<contours_1.size();) {
    	  Mat temp= Mat.zeros(mat_o.size(), 0);
    	  if(Imgproc.contourArea(contours_1.get(i))>10000) {
    		  Imgproc.drawContours(temp,contours_1,i,new Scalar(255,255,255),-1);
    		  Core.bitwise_and(temp, gau, temp);
    		  Imgproc.threshold(temp, temp, 120, 255, Imgproc.THRESH_BINARY);
    	  }
    	  else{
    		  Imgproc.drawContours(temp,contours_1,i,new Scalar(255,255,255),-1);
    	  }
    	  Core.bitwise_or(final_cell_1,temp,final_cell_1);
    	  i++;
      }
      List<MatOfPoint> contours_2 = new ArrayList<MatOfPoint>();
      Mat hierarchy_2 = new Mat();
      Imgproc.findContours(final_cell_1, contours_2, hierarchy_2, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      Mat final_cell_2= Mat.zeros(mat_o.size(), 0);
      for(int i=0;i<contours_2.size();) {
    	  Mat temp= Mat.zeros(mat_o.size(), 0);
    	  if(Imgproc.contourArea(contours_2.get(i))>8000) {
    		  Imgproc.drawContours(temp,contours_2,i,new Scalar(255,255,255),-1);
    		  Core.bitwise_and(temp, gau, temp);
    		  Imgproc.threshold(temp, temp, 170, 255, Imgproc.THRESH_BINARY);
    	  }
    	  else{
    		  Imgproc.drawContours(temp,contours_2,i,new Scalar(255,255,255),-1);
    	  }
    	  Core.bitwise_or(final_cell_2,temp,final_cell_2);
    	  i++;
      }
      List<MatOfPoint> contours_3 = new ArrayList<MatOfPoint>();
      Mat hierarchy_3 = new Mat();
      Imgproc.findContours(final_cell_2, contours_3, hierarchy_3, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      Mat final_cell_3= Mat.zeros(mat_o.size(), 0);
      for(int i=0;i<contours_3.size();) {
    	  Mat temp= Mat.zeros(mat_o.size(), 0);
    	  if(Imgproc.contourArea(contours_3.get(i))>8000) {
    		  Imgproc.drawContours(temp,contours_3,i,new Scalar(255,255,255),-1);
    		  Core.bitwise_and(temp, gau, temp);
    		  Imgproc.threshold(temp, temp, 200, 255, Imgproc.THRESH_BINARY);
    	  }
    	  else{
    		  Imgproc.drawContours(temp,contours_3,i,new Scalar(255,255,255),-1);
    	  }
    	  Core.bitwise_or(final_cell_3,temp,final_cell_3);
    	  i++;
      }
      List<MatOfPoint> contours_4 = new ArrayList<MatOfPoint>();
      Mat hierarchy_4 = new Mat();
      Imgproc.findContours(final_cell_3, contours_4, hierarchy_4, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      for(int i=0;i<contours_4.size();) {
    	  if(Imgproc.contourArea(contours_4.get(i))<300) {
    		  contours_4.remove(i);
    	  }
    	  else {
    		  i++;
    	  }
    	  
      }
      Mat final_cell_4= Mat.zeros(mat_o.size(), 0);
      Imgproc.drawContours(final_cell_4,contours_4,-1,new Scalar(255,255,255),-1);
      /*Nuclear*/
      Mat nuclear = Imgcodecs.imread("C:/Users/93709/Downloads/19_08_08/19_08_08/Process_6071 - DAPI.tif");
      Mat nuclear_p= new Mat();
      Imgproc.cvtColor(nuclear, nuclear_p, Imgproc.COLOR_BGR2GRAY);
      Mat nuclear_mask=Mat.zeros(nuclear_p.size(), 0);
      double maxvalue_n=0;
      for(int x=0;x<nuclear_p.rows();x++) {
    	  for(int y=0;y<nuclear_p.cols();y++) {
    		  double[] data_n=nuclear_p.get(x,y);
    		  if (data_n[0]>maxvalue_n){
    			  maxvalue_n=data_n[0];
    			  
    		  }
    		 
    		  
    	  }
      }
     
      for(int x=0;x<nuclear_p.rows();x++) {
    	  for(int y=0;y<nuclear_p.cols();y++) {
    		  double[] data1_n=nuclear_p.get(x,y);
    		  data1_n[0]=data1_n[0]*255/maxvalue_n;
    		  /*nuclear_mask.put(x,y,data1_n);*/
    		  if(data1_n[0]<=150) {
    			 nuclear_mask.put(x,y,data1_n); 
    		  }
    		  else {
    			  nuclear_mask.put(x,y,0);
    		  }
    	  }
      }
      Mat nuclear_threshold= new Mat();
      Imgproc.threshold(nuclear_mask, nuclear_threshold, 33, 255, Imgproc.THRESH_BINARY);
      Mat final_cell_5=Mat.zeros(mat_o.size(), 0);
      for(int k=0;k<contours_4.size();k++) {
    	  if(Imgproc.contourArea(contours_4.get(k))>100)
    	  {
    		  Mat mask_e=Mat.zeros(mat_o.size(), 0);
    		  Imgproc.drawContours(mask_e,contours_4,k,new Scalar(255,255,255),-1);
    		  int flag=0;
    		  for(int x=0;x<nuclear_p.rows();x++) {
    	    	  for(int y=0;y<nuclear_p.cols();y++) {
    	    		  double[] data_m=mask_e.get(x,y);
    	    		  double[] data_n=nuclear_threshold.get(x,y);
    	    		  double t=255;
    	    		  if(data_m[0]==t && data_n[0]==t) {
    	    			  flag=1;
    	    		  }	  
    	    	  }
    	      }
    		  if(flag==1) {
    			  Core.bitwise_or(final_cell_5,mask_e,final_cell_5);
    		  }
    	  }
      }
      List<MatOfPoint> final_cell_contours = new ArrayList<MatOfPoint>();
      Mat final_cell_hierarchy = new Mat();
      Imgproc.findContours(final_cell_5, final_cell_contours, final_cell_hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      if(final_cell_contours.size()==0) {
    	  System.out.println("There is no membrane");
    	  return;
      }
      System.out.println(final_cell_contours.size());

      HighGui.imshow("final", final_cell_5);
      HighGui.waitKey(0);

   }
     
}
