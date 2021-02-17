package com.etherfirma.mva.util

import com.etherfirma.mva.util.CentroidUtil.parseCoordinates

/**
 * A utility class for calculating the centroid of a polygon defined by a series
 * of contiguous points that define the perimeter.
 */

object CentroidUtil {

    /**
     *
     */

    fun findCentroid (v: List<Pair<Double, Double>>): Pair<Double, Double> {
        var x: Double = 0.0
        var y: Double = 0.0
        val n = v.size
        var signedArea = 0.0

        // For all vertices
        for (i in 0 until n) {
            val x0 = v[i].first
            val y0 = v[i].second
            val x1 = v[(i + 1) % n].first
            val y1 = v[(i + 1) % n].second

            // Calculate value of A using shoelace formula
            val a = x0 * y1 - x1 * y0
            signedArea += a

            // Calculating coordinates of centroid of polygon
            x += (x0 + x1) * a
            y += (y0 + y1) * a
        }
        signedArea *= 0.5
        x /= (6 * signedArea)
        y /= (6 * signedArea)
        return Pair<Double, Double> (x, y)
    }

    /**
     *
     */

    fun parseCoordinatesLegacy (str: String): List<Pair<Double, Double>> {
        val iter = str.split (" ").iterator()
        val coords = mutableListOf<Pair<Double, Double>> ()
        while (iter.hasNext()) {
            val lat = iter.next ().toDouble()
            val lon = iter.next ().toDouble ()
            coords.add (Pair (lat, lon))
        }
        return coords
    }

    /**
     *
     */

    fun parseCoordinates (str: String): List<Pair<Double, Double>> {
        val els = str.split (" ")
        val coords = mutableListOf<Pair<Double, Double>> ()
        for (el in els) {
            val (lat, lon) = el.split (",")
            coords.add (Pair (lat.toDouble(), lon.toDouble()))
        }
        return coords
    }

    /**
     *
     */

    fun encodeCoordinates (coords: List<Pair<Double, Double>>): String {
        val buf = StringBuffer ()
        for (coord in coords) {
            if (buf.isNotEmpty()) {
                buf.append (" ")
            }
            val (lat, lon) = coord
            buf.append ("$lat,$lon")
        }
        return buf.toString ()
    }
}


fun main (args: Array<String>) {

    // Test encoding

    val str = "-123.08 39.86528 -123.12389 39.94056 -123.16389 40.065 -123.12278 40.06028 -123.11444 40.18526 -122.9875 40.11861 -122.90361 40.12444 -122.94194 40.06333 -122.9725 40.00861 -122.8923 39.9584 -122.97583 39.92194 -123.08 39.86528"
    val coords = CentroidUtil.parseCoordinates (str)
    for (coord in coords) {
        val (lat, lon) = coord
        println ("[$lat, $lon]")
    }

    // Test decoding

    println (str)
    println (CentroidUtil.encodeCoordinates (coords))

    // Calculate the centroid of the polygon

    val centroid = CentroidUtil.findCentroid (coords)
    val (lat, lon) = centroid
    println ("centroid = $lat,$lon")

    return
}

// EOF