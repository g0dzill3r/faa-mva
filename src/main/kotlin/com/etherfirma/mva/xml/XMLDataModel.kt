package com.etherfirma.mva.xml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.util.ArrayList


@JsonRootName("AIXMBasicMessage")
data class AIXMBasicMessage(

    @set:JsonProperty("description")
    var description: String,

    @set:JsonProperty("boundedBy")
    var boundedBy: Any?,

    @set:JsonProperty("sequenceNumber")
    var sequenceNumber: Int,

    @set:JsonProperty("messageMetadata")
    var messageMetadata: Any?,

    @set:JsonProperty("hasMember")
    var hasMember: List<Member> = ArrayList(),

    var id: String
)

@JsonRootName("Member")
data class Member(
    @set:JsonProperty("Airspace")
    var airspace: Airspace,
    var type: String?
)

@JsonRootName("Member")
data class Airspace(

    @set:JsonProperty("description")
    var description: String,

    @set:JsonProperty("boundedBy")
    var boundedBy: Any?,

    @set:JsonProperty("timeSlice")
    var timeSlice: TimeSlice,

    var id: String?
)

@JsonRootName ("TimePeriod")
data class TimePeriod (
    var beginPosition: String,
    var endPosition: String? = null
)

@JsonRootName ("ValidTime")
data class ValidTime (
    @set:JsonProperty("TimePeriod")
    var timePeriod: TimePeriod,

    @JacksonXmlProperty(isAttribute = true)
    var type: String = "simple"
)

@JsonRootName("AirspaceTimeSlice")
data class AirspaceTimeSlice (
    var interpretation: String,
    var sequenceNumber: Int,
    var type: String,
    var designator: Any?,
    var localType: Any?,
    var name: String,
    var designatorICAO: Any?,
    var controlType: Any?,
    var upperLowerSeparation: Any?,
    var protectedRoute: Any?,
    var validTime: ValidTime?,

    @set:JsonProperty("geometryComponent")
    var geometryComponent: GeometryComponent
)

@JsonRootName("timeSlice")
data class TimeSlice(
    @set:JsonProperty("AirspaceTimeSlice")
    var airspaceTimeSlice: AirspaceTimeSlice
)

@JsonRootName("LinearRing")
data class LinearRing (
    var posList: String
)

@JsonRootName("Exterior")
data class Exterior (
    @set:JsonProperty("LinearRing")
    var linearRing: LinearRing
)

@JsonRootName("Interior")
data class Interior (
    @set:JsonProperty("LinearRing")
    var linearRing: LinearRing
)

@JsonRootName("PolygonPatch")
data class PolygonPatch (
    @set:JsonProperty("exterior")
    var exterior: Exterior,

    @set:JsonProperty("interior")
    var interior: Interior? = null,

    @set:JsonProperty("interpolation")
    var interpolation: String
)

@JsonRootName("Patch")
data class Patch (
    @set:JsonProperty("PolygonPatch")
    var polygonPath: PolygonPatch
)

@JsonRootName("Surface")
data class Surface (
    @set:JsonProperty("Patches")
    var patches: List<Patch> = ArrayList(),

    var srsName: String?,
    var id: String?
)

@JsonRootName("horizontalProjection")
data class HorizontalProjection (
    @set:JsonProperty("Surface")
    var surface: Surface
)

@JsonRootName("AirspaceVolume")
data class AirspaceVolume (
    var minimumLimit: String,
    var minimumLimitReference: String,
    var horizontalProjection: HorizontalProjection
)

@JsonRootName("TheAirspaceVolume")
data class TheAirspaceVolume (
    @set:JsonProperty("AirspaceVolume")
    var airspaceVolume: AirspaceVolume
)

@JsonRootName("AirspaceGeometryComponent")
data class AirspaceGeometryComponent (

    @set:JsonProperty("operation")
    var operation: Any?,

    @set:JsonProperty("operationSequence")
    var operationSequence: Any? ,

    @set:JsonProperty("TheAirspaceVolume")
    var theAirspaceVolume: TheAirspaceVolume
)

@JsonRootName("geometryComponent")
data class GeometryComponent (

    @set:JsonProperty("AirspaceGeometryComponent")
    var airspaceGeometryComponent: AirspaceGeometryComponent
)

// EOF