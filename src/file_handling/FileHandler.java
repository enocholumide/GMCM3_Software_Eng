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
import features.PointItem;
import features.PolygonItem;
import features.PolylineItem;



public class FileHandler {
	
	// First  Datum WGS84 ellipsoid parameter
		public static final double  sllipsoidalHeight = 160; // Common average ellipsoidal hight 
		public static final double  semiMajorAxisWGS84 = 6378137.0; 
		public static final double  semiMinorAxisWGS84 = 6356752.314245179;
		
		//second datum Gausskruger ellipsoid parameter
		public static final double  semiMajorAxisGausskruger = 6377397.155; 
		public static final double  semiMinorAxisGaussKruger = 6356078.962818189;
		
		//third datum  Lambert ellipsoid parameter
		public static final double  semiMajorAxisLambert = 6378137.0;
		public static final double  semiMinorAxisLamberet = 6356752.314140356;
		
		static double eccentrycitysquare ;
		static double eccentrycityPrimesquare;
		static double flattening;
		static double inverseFlattening;
		static double semiMajorAxisForCurvature ;
	    static double radiusOfCurvature ;
    
    
    static List<Point2D> poinsList2d = new ArrayList<Point2D>();
	
	 static Point point = null;
	 private static ArrayList<Point> pointlist = new ArrayList<Point>();
	 
	 /**s
	  * Reading CSV
	  * @param file
	  * @throws IOException
	  */
	 public static Feature readFromCSV() throws IOException {
		 
       	  
		 Feature FeatureInfo = null; 
		 
	     JFileChooser CSVFile = new JFileChooser ();
	     int geoJsonReturnValu = CSVFile.showSaveDialog(MainFrame.panel);
	     File file = CSVFile.getSelectedFile();
         JSONParser parser = new JSONParser();
     
     
       if (geoJsonReturnValu == JFileChooser.APPROVE_OPTION) {
 
		            
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
		        	  
		        	  String [] splitedline= line.split(";"); 
		        	  String id  =  splitedline[0];
		        	  String type = splitedline[1];
		        	  String sprx = splitedline[3];
		   	   	      String spry = splitedline[4];
		   	   	      // seting type to uppercase to be tested for gemometry type.
		   	   	      type.toUpperCase();
		        	  // skip the first line
		        	  if(count == 0) {
	        			  count = count+1;
	        			  continue;
	        		  }
		        	  if(count == 1) {
	        			  if(type.equals("POINT")) {
	        				  readPointFeature(splitedline);
	        			  } else if (type.equals("POLYGON")) {
	        				  readPolygonFeature(splitedline);
	        			  } else if(type.equals("POLYLINE")) { 
		        	      readPolylineFeature(splitedline);
		        	     }
	        		  }			        			 			        						        						        	  			        	  		        	 			        	        		  
		       } // while close  
	 
	       }catch(IOException e){
	        	  e.printStackTrace();
	        	  
	        	  
	      }finally {
	        	  filereader.close();
	          }
	          
       }        
	          
			return null;
               
   }
	 
	/** 
	 * Readin GeoJson File
	 * @param layer
	 * @param file
	 */
	 public static Feature readFromGeoJson(String SelectedDatum) {
		 String slectedDatum = SelectedDatum;
		
	 
		
	     if(slectedDatum.equals("WGS84")) {
	    	 
			setParametr(semiMajorAxisWGS84, semiMinorAxisWGS84); 
			
		   }else if(slectedDatum.equals("GaussKrurger")) {
			   
			   
			setParametr(semiMajorAxisGausskruger, semiMinorAxisGaussKruger); 
			
		   } else if(slectedDatum.equals("Lambert")){
			   
		      setParametr(semiMajorAxisLambert, semiMinorAxisLamberet); 
		
		   }
    	

    	
		     Feature FeatureInfo = null; 
		     JFileChooser geoJsonFile = new JFileChooser ();
		     int geoJsonReturnValu = geoJsonFile.showSaveDialog(MainFrame.panel);
		     File saveSession = geoJsonFile.getSelectedFile();
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
						    poinsList2d.add(point2d);
							System.out.println(poinsList2d);				
						}
						
					}
					
					System.out.println( poinsList2d.size());
					
				}catch (Exception e) {
						e.printStackTrace();
						
						
					}
		        }
			return FeatureInfo; 
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
	 private static PolylineItem readPolylineFeature(String[] spliter) {
		   PolylineItem  PolylineInfo = null;
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
		return PolylineInfo;
	}
	 
	 /**
	  * * Reading PolygoneFeatire from CSV file.
	  * @param spliter
	  */
	private static PolygonItem readPolygonFeature(String[] spliter) {
		PolygonItem PolygonInfor = null;
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
		return PolygonInfor;
	}
 	 
	/**
	  * Reading (x,y) PointFeatire coordinates from CSV file.
	  * @param spliter
	  */
	 public static PointItem readPointFeature(String[] spliter) {
		 PointItem PointInfo = null;
		 int id = 0 ;
		 try {
			 
			 String iid = spliter[0];
			 String type = spliter[1];
			 String isellipse = spliter[2];
			 id = Integer.parseInt(iid);
	       	 double a =  Double.parseDouble(spliter[3]);
	       	 double b =  Double.parseDouble(spliter[4]);
	       	 int xcoord= (int) a;
	         int ycoord = (int) b;
	         point = new Point(xcoord,ycoord);
		    Ellipse2D ellipse = new Ellipse2D.Double(xcoord, ycoord, 10, 10);
		    //DrawingPanel.ellipses.add(ellipse);
		    System.out.println(ellipse);
		    pointlist.add(point);
		    
			
			 
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 PointInfo = new PointItem (id , point);
		 System.out.println(PointInfo);
		return PointInfo;
		
	}
  
	 /**
	  * Writing to CSV 
	  * @param file
	  * @throws IOException
	  */	 
	 public static boolean writeToCSV(List<Layer> listOfLayers) {
		 
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
				return true;
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		 }
		
		 
		 return false;
		 
	 }
	 
}
