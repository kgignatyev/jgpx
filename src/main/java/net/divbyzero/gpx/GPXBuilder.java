package net.divbyzero.gpx;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by
 * User: kgignatyev
 */
public class GPXBuilder {
    static Namespace ns = Namespace.getNamespace("http://www.topografix.com/GPX/1/1");
    static NumberFormat formatter = new DecimalFormat("#0.0000000");

    public static Document buildGPX(GPX d) {
        Element gpx = new Element("gpx");

        gpx.setNamespace(ns);
        Document document = new Document(gpx);
        for (Track track : d.getTracks()) {
            buildTrack(gpx, track);
        }
        return document;
    }

    private static void buildTrack(Element gpx, Track track) {
        Element trk = new Element("trk", ns);
        if (track.name != null) {
            Element nameE = new Element("name", ns);
            nameE.addContent(track.name);
            trk.addContent(nameE);
        }
        for (TrackSegment trackSegment : track.getSegments()) {
            buildSegment(trk, trackSegment);
        }
        gpx.addContent(trk);
    }

    private static void buildSegment(Element trk, TrackSegment trackSegment) {
        Element trkSeg = new Element("trkseg", ns);
        for (Waypoint waypoint : trackSegment.getWaypoints()) {
            Element trkp = new Element("trkpt", ns);
            trkp.setAttribute("lat", formatter.format(waypoint.getCoordinate().getLatitude()));
            trkp.setAttribute("lon", formatter.format(waypoint.getCoordinate().getLongitude()));
            Element ele = new Element("ele", ns);
            ele.addContent("1.0");
            trkp.addContent(ele);
            trkSeg.addContent(trkp);
        }
        trk.addContent(trkSeg);
    }

    public static String toGpxString(GPX d) throws IOException {
        StringWriter out = new StringWriter();
        writeGpx(d, out);
        return out.toString();
    }

    public static void writeGpx(GPX d, Writer w) throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(buildGPX(d), w);
    }
}
