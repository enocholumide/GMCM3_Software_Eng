package file_handling;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
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
import application_frames.SettingsFrame;
import core_classes.Feature;
import core_classes.Layer;
import core_components.TableOfContents;
import features.PointItem;
import features.PolygonItem;
import features.PolylineItem;
import toolset.RPoint;
import toolset.Tools;

/**
 * the class handels files importing and exporting protocols
 * there are two file formats are supported the CSV and GeoJson formats
 * @author Musa Fadul
 * @since Dec 17, 2017
 */

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
	 
	/**
	 * Reading GeoJson File
	 * @param SelectedDatum
	 * @return
	 */
	 public static Feature readFromGeoJson(String SelectedDatum) {
		 String slectedDatum = SelectedDatum;
		 
		// tesing the sected datum 
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
					
					 List<RPoint> wcsPoints = new ArrayList<RPoint>();
					
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
											
							radiusOfCurvature = (semiMajorAxisForCurvature)/(Math.sqrt(1-(eccentrycitysquare*Math.sin(latitudeInDegree))));
							double xWordCoord =  (radiusOfCurvature + sllipsoidalHeight ) * Math.cos(latitudeInDegree)*Math.cos(longitudeInDegree);
							double yWordCoord = (radiusOfCurvature + sllipsoidalHeight) * Math.cos(latitudeInDegree)*Math.sin(longitudeInDegree);
						    Point2D point2d = new Point2D.Double(xWordCoord, yWordCoord);	
						    // world coordinates must be converted to image coordinates
						    poinsList2d.add(point2d);
							wcsPoints.add(new RPoint(point2d));
						}
						
						Tools.wcsToImageCoords(wcsPoints, MainFrame.panel);					
						for(RPoint point : wcsPoints) {
							System.out.println("X: " + point.getImagePoint().getX() + " Y: " + point.getImagePoint().getY());
						}
						
					}
					System.out.println(MainFrame.panel.getSize());
					System.out.println( poinsList2d.size());
					
				}catch (Exception e) {
						e.printStackTrace();
						
						
					}
		        }
			return FeatureInfo; 
    }
	 /**
	  * setting transformation parameter
	  * @param semiMajorAxis
	  * @param semiMinorAxis
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
		  * Reading PolylineFeature  from CSV file.
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
		  * * Reading PolygoneFeature from CSV file.
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
		  * Reading (x,y) PointFeature coordinates from CSV file.
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
			    System.out.println(ellipse);
			    pointlist.add(point);
			    
				
				 
			 }catch(Exception e) {
				 e.printStackTrace();
			 }
			 PointInfo = new PointItem (id , point);
			 return PointInfo;
			
		}
  
	 	/**
	 	 * Reading CSV
	 	 * @param newLayer
	 	 * @param geomSelected
	 	 * @return
	 	 * @throws IOException
	 	 */
		 public static Feature readFromCSV(Layer newLayer, String geomSelected) throws IOException {
			 
	       	  
			 Feature FeatureInfo = null;
			 
		     JFileChooser CSVFile = new JFileChooser ();
		     int geoJsonReturnValu = CSVFile.showOpenDialog(MainFrame.panel);
		     File file = CSVFile.getSelectedFile();
	         JSONParser parser = new JSONParser();
	     
	       if (geoJsonReturnValu == JFileChooser.APPROVE_OPTION) {
	            
		       FileReader filereader = null;
		       BufferedReader bfreader = null;    
		              
		          try {	  
		        	  
		        	  filereader = new FileReader(file);
		        	  bfreader = new BufferedReader(filereader);
			         
			          String line;
			          int count = 0;
			          
			          while((line = bfreader.readLine()) != null) {
			        	  
			        	// skip the first line
			        	  if(count > 0) {
			        		  
				        	  String [] splitedline= line.split(";");
				        	  if(splitedline.length > 6) {
				        		  
					        	  String layerType = splitedline[1];
					        	  boolean isEllipse = Boolean.valueOf(splitedline[2].toLowerCase());
					        	  Double aX[] = Tools.copyFromStringArray(splitedline[3].split(","));
					   	   	      Double aY[] = Tools.copyFromStringArray(splitedline[4].split(","));
					   	   	      double rx = Double.parseDouble(splitedline[5]);
					   	   	      double ry = Double.parseDouble(splitedline[6]);
					   	   	      
					   	   	      
						   	   	if(geomSelected.toUpperCase().equals(layerType.toUpperCase())) {
						   	   		MainFrame.createFeatureFromResultSet(newLayer, layerType, isEllipse, aX, aY, rx, ry);
						   	   	}
				        	} 
			        	  }
			        	  
			        	  count++;
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
	 * Writing to CSV 	 
	 * @param listOfLayers
	 * @return
	 */
	 public static boolean writeToCSV(List<Layer> listOfLayers) {
		 
		 JFileChooser saveSessionFileChooser = new JFileChooser();
		 int saveSessionReturnVal = saveSessionFileChooser.showSaveDialog(MainFrame.panel);
		 
		 if (saveSessionReturnVal == JFileChooser.APPROVE_OPTION) {
				
			try {
				File saveSession = saveSessionFileChooser.getSelectedFile();
				BufferedWriter sessionWriter = new BufferedWriter(new FileWriter(saveSession.getPath() + ".csv"));
				
				String header = "id;type;is_ellipse;x;y;rx;ry\n";
				sessionWriter.write(header);
				
				for(Layer layer : listOfLayers) {
					 
					if (layer.isVisible()) {
						
						for (Feature feature : layer.getListOfFeatures()) {

							if (feature.isVisibile()) {
								
								String fetureString = "";
								
								fetureString += layer.getId();
								fetureString += ";" + layer.getLayerType();
								fetureString += ";" + feature.isEllipse();
								fetureString += ";";
								
								if (!feature.isEllipse()) {
									
									int i = 0;
									for (Double d : feature.getCoordinatesArrayXY()[0]) {
										fetureString += d;
										if (i != feature.getCoordinatesArrayXY()[0].length - 1) {
											fetureString += ",";
										}
										i++;
									}
									fetureString += ";";
									
									i = 0;
									for (Double d : feature.getCoordinatesArrayXY()[1]) {
										fetureString += d;
										if (i != feature.getCoordinatesArrayXY()[1].length - 1) {
											fetureString += ",";
										}
									}
									
								} else {
									
									fetureString += feature.getCenter().getX() + ";";
									fetureString += feature.getCenter().getY();
								}
								
								fetureString += ";" + feature.getRadiusX();
								fetureString += ";" + feature.getRadiusY() + "\n";
								sessionWriter.write(fetureString);
							}
						} 
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