package net.divbyzero.gpx.tests;

import net.divbyzero.gpx.*;
import net.divbyzero.gpx.parser.GPXParser;
import net.divbyzero.gpx.parser.ParsingException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by
 * User: kgignatyev
 */
public class SegmentSimplifierTest {

    @Test
    public void testReduceNumberOfPoints() throws Exception {
        GPX gpx = GPXParser.parseFile(new File("data/az-bdr.gpx"));
        double precisionInMeters = 3200;
        GPX simplifiedGPX = SegmentSimplifier.simplifyGpx(gpx, precisionInMeters, false);
        int trackNum = 0;
        for (Track track : simplifiedGPX.getTracks()) {
            assertEquals(1, track.getSegments().size());

            int segmentSize = track.getSegments().get(0).getWaypoints().size();
            System.out.println("segmentSize = " + segmentSize);

            trackNum++;
        }

        FileUtils.write(new File("data/simplified.gpx"), GPXWriter.toGpxString(simplifiedGPX));
    }
}
