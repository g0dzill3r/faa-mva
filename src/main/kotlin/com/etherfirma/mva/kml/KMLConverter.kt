package com.etherfirma.mva.kml

import com.etherfirma.mva.util.CentroidUtil
import com.etherfirma.mva.xml.AIXMBasicMessage
import com.etherfirma.mva.xml.Member

object KMLConverter {
    private fun getDocument (xml: AIXMBasicMessage): Document {
        val schema = getSchema (xml)
        val folder = getFolder (xml)
        val style = Style (id="noicon", iconStyle=IconStyle (Icon ()))
        return Document (id="root_doc", schema=schema, folder=folder, style=style)
    }

    /**
     * Generate the standard schema header for the KML file.
     */

    private fun getSchema (xml: AIXMBasicMessage): Schema {
        val simpleFields = listOf (
            SimpleField ("Description"),
            SimpleField ("beginPosition"),
            SimpleField ("interpretation"),
            SimpleField ("sequenceNumber", "int"),
            SimpleField ("type"),
            SimpleField ("designator"),
            SimpleField ("localType"),
            SimpleField ("designatorICAO"),
            SimpleField ("controlType"),
            SimpleField ("upperLowerSeparation"),
            SimpleField ("protectedRoute"),
            SimpleField ("operation"),
            SimpleField ("operationSequence"),
            SimpleField ("minimumLimit_uom"),
            SimpleField ("minimumLimitReference")
        )
        val schema = Schema ("Airspace", "Airspace", simpleFields)
        return schema
    }

    /**
     *
     */

    private fun toPlacemark (member: Member): Placemark {
        // Extract the bits of the XML entry we need access to

        val airspace = member.airspace
        val ats = airspace.timeSlice.airspaceTimeSlice
        val av = ats.geometryComponent.airspaceGeometryComponent.theAirspaceVolume

        // Create the corresponding placemark entity

        val name = av.airspaceVolume.minimumLimit;
        val description = airspace.description;
        val style = Style (lineStyle=LineStyle ("ff0000ff"), polyStyle=PolyStyle (0))
        val extendedData = ExtendedData (SchemaData ("#Airspace", listOf (
            SimpleData ("Description", airspace.description),
            SimpleData ("beginPosition", ats.validTime!!.timePeriod.beginPosition),
            SimpleData ("interpretation", ats.interpretation),
            SimpleData ("sequenceNumber", "" + ats.sequenceNumber),
            SimpleData ("type", ats.type),
            SimpleData ("minimumLimit_uom", "FT"),
            SimpleData ("minimumLimitReference", av.airspaceVolume.minimumLimitReference),
        )))
        val coordinates = CentroidUtil.parseCoordinatesLegacy (av.airspaceVolume.horizontalProjection.surface.patches[0].polygonPath.exterior.linearRing.posList);
        val polygon = Polygon (OuterBoundaryIs (LinearRing (CentroidUtil.encodeCoordinates (coordinates))));
        return Placemark (name=name, description=description, style=style, extendedData=extendedData, polygon=polygon)
    }

    /**
     *
     */

    private fun getPlacemarks (els: List<Member>): List<Placemark> {
        return els.map (::toPlacemark)
    }

    /**
     * Generate the folder
     */

    private fun getFolder (xml: AIXMBasicMessage): Folder {
        val placemarks = getPlacemarks (xml.hasMember)
        val folder = Folder ("Airspace", placemarks)
        return folder
    }

    /**
     * Generate a label that corresponds to the centroid of the associated polygon.
     */

    private fun generateLabel (placemark: Placemark): Placemark {
        val coords = CentroidUtil.parseCoordinates(placemark.polygon!!.outerBoundaryIs.linearRing.coordinates)
        val centroid = CentroidUtil.findCentroid (coords)
        val (lat, lon) = centroid
        val point = Point ("$lat,$lon")
        return Placemark (name=placemark.name, styleUrl="#noicon", point=point)
    }

    fun embellish (kml: KML) {
        // Now we need to go through each of the airspace entries and add an additional entry
        // containing the placemark with the altitude.

        val folder = kml.document.folder
        val labels = mutableListOf<Placemark> ()
        for (placemark in folder.placemarks) {
            labels.add (generateLabel (placemark))
        }
        kml.document.placemarks = labels
        return
    }

    fun convert (xml: AIXMBasicMessage): KML {
        val kml = KML (getDocument (xml))
        embellish (kml)
        return kml
    }
}

// EOF