package net.divbyzero.gpx;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by
 * User: kgignatyev
 */
public class GPXWriter {

    public static String toGpxString( GPX d){
        StringWriter out = new StringWriter();
        out.write(header());
        for (Track track : d.getTracks()) {
            writeTrack(out,track);
        }
        out.write("</gpx>");

        return out.toString();
    }

    private static void writeTrack(StringWriter out, Track track) {
        out.write("<trk>\n");
        if( track.name != null){
            out.write("<name>"+track.name+"</name>\n");
        }
        for (TrackSegment trackSegment : track.getSegments()) {
            writeSegment(out,trackSegment);
        }
        out.write("</trk>\n");
    }

    static NumberFormat formatter = new DecimalFormat("#0.0000000");

    private static void writeSegment(StringWriter out, TrackSegment trackSegment) {
        out.write("<trkseg>\n");
        for (Waypoint waypoint : trackSegment.getWaypoints()) {
           out.write( "      <trkpt lat=\""+formatter.format(waypoint.getCoordinate().getLatitude())+"\" " +
                   "lon=\""+formatter.format(waypoint.getCoordinate().getLongitude())+"\">\n" +
                           "        <ele>1.0</ele>\n" +
                           "      </trkpt>\n");
        }
        out.write("</trkseg>\n");

    }


    public static String header(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n" +
                "<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" creator=\"mcytravel\" version=\"1.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\">\n" +
                "\n" +
                "  <metadata>\n" +
                "    <link href=\"http://www.garmin.com\">\n" +
                "      <text>Garmin International</text>\n" +
                "    </link>\n" +
                "  </metadata>\n" +
                "\n" ;

    }

}
