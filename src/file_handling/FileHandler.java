package file_handling;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import application_frames.MainFrame;
import core_classes.Feature;
import core_classes.Layer;

public class FileHandler {
	
	 static Point point = null;
	 private static ArrayList<Point> pointlist = new ArrayList<Point>();
	 
	 /**
	  * Reading CSV
	  * @param file
	  * @throws IOException
	  */
	 public static void readFromCSV(File file) throws IOException {
             
	       FileReader filereader = null;
	       BufferedReader bfreader = null;
		       
		       
	        /**
	         * Reading the  CSV file 
	         */
	          try {
	        	  filereader = new FileReader(file);
	        	 
	        	  bfreader = new BufferedReader(filereader);
		         
		          String line;
		          int count = 0;
		          
		          while((line = bfreader.readLine()) != null) {
		        	  
		        	  String [] splitline= line.split(";"); 
		        	  String id  =  splitline[0];
		        	  String type = splitline[1];
		        	  String sprx = splitline[3];
		   	   	      String spry = splitline[4];
		        	  // skip the first line
		        	  if(count == 0) {
	        			  count = count+1;
	        			  continue;
	        		  }
		        	  if(count == 1) {
	        			  if(type.equals("POINT")) {
	        				  readPointFeature(splitline);
	        			  } else if (type.equals("POLYGON")) {
	        				  readPolygonFeature(splitline);
	        			  } else if(type.equals("POLYLINE")) { 
		        	      readPolylineFeature(splitline);
		        	     }
	        		  }			        			 			        						        						        	  			        	  		        	 			        	        		  
		       } // while close  
	 
	       }catch(IOException e){
	        	  e.printStackTrace();
	        	  
	        	  
	      }finally {
	        	  filereader.close();
	          }
		          
   }
	 /**
	  * Reading PolylineFeatire  from CSV file.
	  * @param spliter
	  */
	 private static void readPolylineFeature(String[] spliter) {
		 
		   int i;
	       int j ;
	       int  xcoordpoly =0;
	       int  ycoordpoly = 0;
	       try {
				   String sprx = spliter[3];
			   	   String spry = spliter[4];
			   	   String id = spliter[0];
				   String type = spliter[1];
				   String isellipse = spliter[2];
				   String []strxcoord = sprx.split(",");
			   	   String []strycoord = spry.split(",");
     	 
		           for( i=0;i<strxcoord.length; i++) {
				   		xcoordpoly = (int) Double.parseDouble(strxcoord[i]);
				   		ycoordpoly = (int) Double.parseDouble(strycoord[i]);
						point = new Point(xcoordpoly,ycoordpoly);
						pointlist.add(point);
					    System.out.println( "ID " +id +"  Type " + type + "  Is_Ellipse " + isellipse + "  Coordinates " +point);
			
			
		 }
	       }catch(Exception e) {
	    	   e.printStackTrace();
	       }
	}
	 
	 /**
	  * * Reading PolygoneFeatire from CSV file.
	  * @param spliter
	  */
	private static void readPolygonFeature(String[] spliter) {
		// TODO Auto-generated method stub
	       int i;
	       int j ;
	       int xcoord1 =0;
	       int ycoord1 = 0;
	       try {
		         String sprx = spliter[3];
	   	         String spry = spliter[4];
		   	     String id = spliter[0];
			     String type = spliter[1];
			     String isellipse = spliter[2];			 
		   	     String []strxcoord = sprx.split(",");
		   	     String []strycoord = spry.split(",");
		   	
	   		     for( i=0;i<strxcoord.length; i++) {
	   				xcoord1 = (int) Double.parseDouble(strxcoord[i]);
	   			    ycoord1 = (int) Double.parseDouble(strycoord[i]);
	   				point = new Point(xcoord1,ycoord1);
	 		        pointlist.add(point);
	 		        System.out.println( "ID " +id +"  Type " + type + "  Is_Ellipse " + isellipse + "  Coordinates " +point);
   			
		         }
	   		     
	       }catch(Exception e) {
	    	   e.printStackTrace();
	       }
	}
 	 
	/**
	  * Reading (x,y) PointFeatire coordinates from CSV file.
	  * @param spliter
	  */
	 public static void readPointFeature(String[] spliter) {
		 
		 try {
			 
			 String id = spliter[0];
			 String type = spliter[1];
			 String isellipse = spliter[2];
			 
	       	 double a =  Double.parseDouble(spliter[3]);
	       	 double b =  Double.parseDouble(spliter[4]);
	       	 int xcoord= (int) a;
	         int ycoord = (int) b;
	         point = new Point(xcoord,ycoord);
		    Ellipse2D ellipse = new Ellipse2D.Double(xcoord, ycoord, 10, 10);
		    //DrawingPanel.ellipses.add(ellipse);
		    System.out.println(ellipse);
		    pointlist.add(point);
			System.out.println( "ID " +id +"  Type " + type + "  Is_Ellipse " + isellipse + "  Coordinates " +point);
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
	}
  
	 /**
	  * Writing to CSV 
	  * @param file
	  * @throws IOException
	  */
	 public static void writeToCSV() throws IOException{
		 // cant set path for the created file
		 File file = new File("‪filely1.txt");
		 FileWriter filewriter = null;
		 BufferedWriter bufferedWriter = null;
		 /**
		  * Reading a file to write to
		  */
		 try {
			 file.createNewFile();
			 filewriter = new FileWriter("‪filely1.txt",true);
			 bufferedWriter = new BufferedWriter(filewriter);
			 String x=null,y=null;
			/*for(Point e:DrawingPanel.listpont) {
				x = Integer.toString(e.x);
				y = Integer.toString(e.y);
				System.out.println(x);
				filewriter.write(x+";"+y);
				
				System.out.println("Written into the file");
				
			}*/
			 
			 
		 }catch(Exception e) {
			  e.printStackTrace();
		 }finally {
			 filewriter.close();
			 
		 }
		 
		 
	 }
	 
	 public static void writeToCSV(List<Layer> listOfLayers) {
		 
		 JFileChooser saveSessionFileChooser = new JFileChooser();
		 int saveSessionReturnVal = saveSessionFileChooser.showSaveDialog(MainFrame.panel);
		 
		 if (saveSessionReturnVal == JFileChooser.APPROVE_OPTION) {
				
			try {
				File saveSession = saveSessionFileChooser.getSelectedFile();
				BufferedWriter sessionWriter = new BufferedWriter(new FileWriter(saveSession.getPath() + ".csv"));
				
				for(Layer layer : listOfLayers) {
					 
					 String fetureString = layer.getLayerName();
					 
					 for(Feature feature : layer.getListOfFeatures()) {
						 fetureString += ";" + layer.getId();
						 fetureString += ";" + layer.getLayerType();
						 fetureString += ";" + feature.isEllipse();
						 fetureString += ";";
						 for(Double d : feature.getCoordinatesArrayXY()[0]) {
							 fetureString += d + "," ;
						 }
						 fetureString += ";";
						 for(Double d : feature.getCoordinatesArrayXY()[1]) {
							 fetureString += d + "," ;
						 }
						 fetureString += ";" + feature.getRadiusX();
						 fetureString += ";" + feature.getRadiusY();
						 
						 sessionWriter.write(fetureString + "\n"); 
					 }
				 }
				
				sessionWriter.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 
		 
	 }
	 
}
