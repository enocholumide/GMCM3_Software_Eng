package tester;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core_classes.Layer;
import database.DatabaseConnection;
import features.PointItem;
import features.PolygonItem;
import features.PolylineItem;

/**
 * Created by isaac on 22/11/17.
 */
public class TestDatabaseConnection {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        /**
         * Class for testing database functionality.
         *   |                                                     |
         *   |                                                     |
         *   |   PUT YOUR DATABASE CREDENTIALS HERE TO RUN TEST    |
         *   |                                                     |
         *   |                                                     |
         *   V                                                     v
         */
        // Initialize database connection
        DatabaseConnection databaseConnection = new DatabaseConnection(
            "localhost",
            5432,
            "softeng_db",
            "postgres",
            "postgres"
        );

        // =============================================================================================================
        // TEST POLYGONS
        // =============================================================================================================

        PolygonItem poly0 = new PolygonItem(0, new Polygon(new int[] {0,1,1,0}, new int[] {0,0,1,0}, 4));
        PolygonItem poly1 = new PolygonItem(1, new Polygon(new int[] {0,2,2,0}, new int[] {0,0,2,0}, 4));
        PolygonItem poly2 = new PolygonItem(2, new Polygon());
        poly2.setEllipse(true, new Point2D.Double(1, 1), 1,1);

        Layer myPolygonLayer = new Layer(0,true,"Polygon","myPolygonLayer");
        myPolygonLayer.getListOfFeatures().add(poly0);
        myPolygonLayer.getListOfFeatures().add(poly1);
        myPolygonLayer.getListOfFeatures().add(poly2);

        databaseConnection.writeTable("polygon_table", myPolygonLayer);

        // =============================================================================================================
        // TEST POLYLINES
        // =============================================================================================================

        // TODO polyline test not working

        Path2D path0 = new Path2D.Double();
        path0.moveTo(0,0);
        path0.lineTo(1,1);
        path0.lineTo(2,2);
        PolylineItem polyline0 = new PolylineItem(0, path0);
        polyline0.setShape(path0);

        Layer myPolylineLayer = new Layer(1, true, "Polyline", "myPolylineLayer");

        myPolylineLayer.getListOfFeatures().add(polyline0);

        databaseConnection.writeTable("polyline_table", myPolylineLayer);

        // =============================================================================================================
        // TEST POINTS
        // =============================================================================================================

        // TODO polygon test not working

        PointItem point0 = new PointItem(0, new Point2D.Double(0,0));
        PointItem point1 = new PointItem(1, new Point2D.Double(1,1));
        PointItem point2 = new PointItem(2, new Point2D.Double(2,2));

        point0.setGeometry(new Point2D.Double(0,0));
        point1.setGeometry(new Point2D.Double(1,1));
        point0.setGeometry(new Point2D.Double(2,2));

        Layer myPointLayer = new Layer(2, true, "Point", "myPointLayer");

        myPointLayer.getListOfFeatures().add(point0);
        myPointLayer.getListOfFeatures().add(point1);
        myPointLayer.getListOfFeatures().add(point2);

        databaseConnection.writeTable("point_table", myPointLayer);

        // =============================================================================================================
        // TEST DROPPING TABLES (uncomment to test dropping)
        // =============================================================================================================

        //databaseConnection.dropTable("polygon_table");
        //databaseConnection.dropTable("polyline_table");

        // =============================================================================================================
        // TEST GET TABLES
        // =============================================================================================================

        ArrayList<String[]> tables = databaseConnection.getTables();
        for (int i=0; i<tables.size(); i++) {
            System.out.println(tables.get(i)[0]);
        }

    }

}
