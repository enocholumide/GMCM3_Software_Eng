package file_handling;

import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application_frames.MainFrame;
import core_classes.Feature;
import core_classes.Layer;



public class FileHandler {
	
	// First  Datum WGS84 ellipsoid parameter
		public static final double  sllipsoidalHeight = 160; // Common average ellipsoidal hight 
		public static final double  semiMajorAxisWGS84 = 6378137.0; 
		public static final double  semiMinorAxisWGS84 = 6356752.314245179;
		
		//second datum Gausskruger ellipsoid parameter
		public static final double  semiMajorAxisGauskruger = 6377397.155; 
		public static final double  semiMinorAxisGausKruger = 6356078.962818189;
		
		//third datum  Lambert ellipsoid parameter
		public static final double  semiMajorAxisLambert = 6378137.0;
		public static final double  semiMinorAxisLamberet = 6356752.314140356;
		
		static double eccentrycitysquare ;
		static double eccentrycityPrimesquare;
		static double flattening;
		static double inverseFlattening;
		static double semiMajorAxisForCurvature ;
	    static double radiusOfCurvature ;
    
    
    static List<Point2D> poinsList = new ArrayList<Point2D>();
	
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
	 * Readin GeoJson File
	 * @param layer
	 * @param file
	 */
	 public static void readFromGeoJson() {
	 
	 JFileChooser geoJsonFile = new JFileChooser ();
	 int geoJsonReturnValu = geoJsonFile.showSaveDialog(MainFrame.panel);
	 File saveSession = geoJsonFile.getSelectedFile();
	 
	 setParametr(semiMajorAxisWGS84, semiMinorAxisWGS84);
     System.out.println(eccentrycityPrimesquare);
     // the datum parameters are set according to the selected datum
     /*if(SelcectedDatum == "WGS84") {
		setParametr(semiMajorAxisWGS84, semiMinorAxisGausKruger); 
		System.out.println("Selcted Datum Is WGS84");
	   }else if(SelcectedDatum == "GaussKrurger") {
		setParametr(semiMajorAxisGauskruger, semiMinorAxisGausKruger); 
		System.out.println("Selcted Datum Is GaussKurger");
	   } else if(SelcectedDatum == "Lambert"){
	      setParametr(semiMajorAxisLambert, semiMinorAxisLamberet); 
	
	   }*/
    	

    	
	
    JSONParser parser = new JSONParser();
     
     
    if (geoJsonReturnValu == JFileChooser.APPROVE_OPTION) {
 
		try {
			 FileReader readfile = new FileReader (saveSession);
			
			Object obj = parser.parse(readfile);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray feature = (JSONArray) jsonObject.get("features");
			String id = (String) jsonObject.get("type");
			
			for(int i= 0; i<feature.size();i++) {
							
				int start = (feature.get(i).toString()).indexOf("[");
				int end = (feature.get(i).toString()).lastIndexOf("]");
				
				String coords = (feature.get(i).toString()).substring(start, end);
				
				String replacer1 = coords.replace("[", "");
				String replacer2 = replacer1.replace("]", "");					
				String[] coordsString = replacer2.split(",");
				
				for(int j = 0 ; j < coordsString.length - 1; j++) {
									
					double latitude = Double.parseDouble(coordsString[j]);
					double longitude = Double.parseDouble(coordsString[j+1]);
					
					double latitudeInDegree = latitude * Math.PI / 180;
					double longitudeInDegree = longitude * Math.PI / 180;
					System.out.println(semiMajorAxisForCurvature);				
					radiusOfCurvature = (semiMajorAxisForCurvature)/(Math.sqrt(1-(eccentrycitysquare*Math.sin(latitudeInDegree))));
					double xWordCoord =  (radiusOfCurvature + sllipsoidalHeight ) * Math.cos(latitudeInDegree)*Math.cos(longitudeInDegree);
					double yWordCoord = (radiusOfCurvature + sllipsoidalHeight) * Math.cos(latitudeInDegree)*Math.sin(longitudeInDegree);
				    Point2D point2d = new Point2D.Double(xWordCoord, yWordCoord);	
				    // world coordinates must be converted to image coordinates
					poinsList.add(point2d);
					System.out.println(poinsList);				
				}
				
			}
			
			System.out.println( poinsList.size());
			
		}catch (Exception e) {
				e.printStackTrace();
				
				
			}
        } 
    }
	 /*
	     * seting transformation parameter
	     */
	    public static void setParametr(Double semiMajorAxis , Double semiMinorAxis) {
	    	double SemiMajorAxis = semiMajorAxis;
	    	double SemiMinorAxis = semiMinorAxis ;
	    	
	    	 semiMajorAxisForCurvature = semiMajorAxis;
	    	eccentrycitysquare = ((SemiMajorAxis * SemiMajorAxis ) - ( SemiMinorAxis * SemiMinorAxis))/(SemiMajorAxis * SemiMajorAxis );
	    	eccentrycityPrimesquare = ((SemiMajorAxis * SemiMajorAxis ) - ( SemiMinorAxis * SemiMinorAxis))/( SemiMinorAxis * SemiMinorAxis);
	    	flattening = (SemiMajorAxis - SemiMinorAxis)/SemiMajorAxis ;
	    	inverseFlattening = 1/ ((SemiMajorAxis - SemiMinorAxis)/SemiMajorAxis ) ;
	    	
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