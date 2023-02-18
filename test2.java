package opencv;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
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

public class test2
{
   public static void main( String[] args )
   {
	  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	  /*Membrane;*/
	  
      Mat mat_o=Imgcodecs.imread("C:/Users/93709/Downloads/19_07_31/19_07_31/Process_5999 - TR.tif"); 
      
      
      Mat mat_p= new Mat();
      Imgproc.cvtColor(mat_o, mat_p, Imgproc.COLOR_BGR2GRAY);
      Mat mat_threshold = new Mat();
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
      if(maxvalue==0){
    	  System.out.println("There is no membrane");
    	  return;
      }
      for(int x=0;x<mat_p.rows();x++) {
    	  for(int y=0;y<mat_p.cols();y++) {
    		  double[] data1=mat_o.get(x,y);
    		  data1[0]=data1[0]*255/maxvalue;
    		  mask.put(x,y,data1);
    	  }
      }
      
      Imgproc.threshold(mask, mat_threshold, 52, 255, Imgproc.THRESH_BINARY);
      List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
      Mat hierarchy = new Mat();
      Imgproc.findContours(mat_threshold, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      
      /*nuclear*/
      Mat nuclear = Imgcodecs.imread("C:/Users/93709/Downloads/19_07_31/19_07_31/Process_5999 - DAPI.tif");
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
      /*final_cell*/
      Mat final_cell=Mat.zeros(mat_o.size(), 0);
      for(int k=0;k<contours.size();k++) {
    	  if(Imgproc.contourArea(contours.get(k))>100)
    	  {
    		  Mat mask_e=Mat.zeros(mat_o.size(), 0);
    		  Imgproc.drawContours(mask_e,contours,k,new Scalar(255,255,255),-1);
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
    			  Core.bitwise_or(final_cell,mask_e,final_cell);
    		  }
    	  }
      }
      List<MatOfPoint> final_cell_contours = new ArrayList<MatOfPoint>();
      Mat final_cell_hierarchy = new Mat();
      Imgproc.findContours(final_cell, final_cell_contours, final_cell_hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
      for(int x=0;x<final_cell_contours.size();) {
    	  if(Imgproc.contourArea(final_cell_contours.get(x))==0) {
    		  final_cell_contours.remove(x);
    	  }
    	  else {
    		  x++;
    	  }
      }
      if(final_cell_contours.size()==0) {
    	  System.out.println("There is no membrane");
    	  return;
      }

      /*bead*/
      
      Mat bead_o=Imgcodecs.imread("C:/Users/93709/Downloads/19_07_31/19_07_31/Process_5999 - GFP.tif");
      Mat bead_p= new Mat();
      Imgproc.cvtColor(bead_o, bead_p, Imgproc.COLOR_BGR2GRAY);
      double maxvalue_b=0;
      for(int x=0;x<bead_p.rows();x++) {
    	  for(int y=0;y<bead_p.cols();y++) {
    		  double[] data_b=bead_p.get(x,y);
    		  if (data_b[0]>maxvalue_b){
    			  maxvalue_b=data_b[0];
    			  
    		  }
    		 
    		  
    	  }
      }
      Mat bead_mask=Mat.zeros(bead_p.size(), 0);
      for(int x=0;x<bead_p.rows();x++) {
    	  for(int y=0;y<bead_p.cols();y++) {
    		  double[] data1_b=bead_p.get(x,y);
    		  data1_b[0]=data1_b[0]*255/maxvalue_b;
    		  bead_mask.put(x,y,data1_b);
    	  }
      }
      Mat bead_threshold = new Mat();
      Imgproc.threshold(bead_mask, bead_threshold, 53, 255, Imgproc.THRESH_BINARY);
      List<MatOfPoint> bead_contours = new ArrayList<MatOfPoint>();
      Mat bead_hierarchy = new Mat();
      Imgproc.findContours(bead_threshold, bead_contours, bead_hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE); 
      ArrayList<Double>bead_area=new ArrayList<Double>();
      for(int x=0;x<bead_contours.size();) {
    	  if(Imgproc.contourArea(bead_contours.get(x))>0) {
    		  bead_area.add(Imgproc.contourArea(bead_contours.get(x)));
    		  x++;
    	  }
    	  else {
    		  bead_contours.remove(x);
    	  }
      }
     Collections.sort(bead_area);
     int index=bead_area.size()/2;
     double sample=1.5*bead_area.get(index);
     Mat final_bead=Mat.zeros(mat_o.size(), 0);
     Imgproc.drawContours(final_bead, bead_contours, -1,new Scalar(255,255,255),-1);
     
     
     Mat final_mask=Mat.zeros(mat_o.size(), 0);
     
     for(int k=0;k<final_cell_contours.size();) {
    	
    	 Mat temp=Mat.zeros(bead_o.size(), 0);
    	 Imgproc.drawContours(temp,final_cell_contours,k,new Scalar(255,255,255),-1);
    	 Core.bitwise_and(temp, final_bead, temp);
    	 List<MatOfPoint> temp_contours = new ArrayList<MatOfPoint>();
         Mat temp_hierarchy = new Mat();
         Imgproc.findContours(temp, temp_contours, temp_hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
         if(Imgproc.contourArea(final_cell_contours.get(k))>10000) {
        	 Imgproc.drawContours(bead_o, final_cell_contours, k,new Scalar(0,0,255));
         }
         else {
        	 Imgproc.drawContours(bead_o, final_cell_contours, k,new Scalar(255,255,0));
         }
         Imgproc.drawContours(temp,final_cell_contours,k,new Scalar(255,255,255));
         /*HighGui.imshow(String.format("Number %d", k+1), temp);*/
         int count=0;
         for(int m=0;m<temp_contours.size();m++) {
       	  double temp_area=Imgproc.contourArea(temp_contours.get(m));
       	  if(temp_area>sample) {
       		  double temp_count=temp_area/sample;
       		  count=count+(int)temp_count+1;  	  
       	  }
       	  else{
       		  count=count+1;
       	  }
         }
         System.out.println(String.format("Number %d, the amount of the beads inside is "+count, k+1));
         Core.bitwise_or(final_mask,temp,final_mask);
         System.out.println(Imgproc.contourArea(final_cell_contours.get(k)));
         k++;
     }
     Imgproc.drawContours(bead_o, bead_contours, -1,new Scalar(0,255,0));
     
     HighGui.imshow("beads",bead_o);
     /*HighGui.imshow("final",final_mask);*/
     
     HighGui.waitKey(0);
     
   }
}