package com.etherfirma.mva.kml

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

@JsonRootName("kml")
data class KML (
    @set:JsonProperty("Document")
    var document: Document,
) {
    @JacksonXmlProperty(isAttribute = true)
    var ns: String = "http://www.opengis.net/kml/2.2"
}

@JsonRootName("Document")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class Document (
    @JacksonXmlProperty(isAttribute = true)
    var id: String,

    @set:JsonProperty("Style")
    var style: Style? = null,

    @set:JsonProperty("Schema")
    var schema: Schema,

    @set:JsonProperty("Folder")
    var folder: Folder,

    @set:JsonProperty("Placemark")
    var placemarks: MutableList<Placemark>? = null,
)

@JsonRootName ("SimpleField")
data class SimpleField (
    @JacksonXmlProperty(isAttribute = true)
    var name: String,
    @JacksonXmlProperty(isAttribute = true)
    var type: String = "string",
)

@JsonRootName("Schema")
data class Schema (
    @JacksonXmlProperty(isAttribute = true)
    var id: String,
    @JacksonXmlProperty(isAttribute = true)
    var name: String,

    @set:JsonProperty("SimpleField")
    var simpleFields: List<SimpleField> = arrayListOf ()
)

@JsonRootName("Folder")
data class Folder (
    var name: String,

    @set:JsonProperty("Placemark")
    var placemarks: List<Placemark> = listOf ()
)

@JsonRootName ("Placemark")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class Placemark(
    var name: String? = null,
    var description: String? = null,
    var styleUrl: String? = null,

    @set:JsonProperty ("Style")
    var style: Style? = null,

    @set:JsonProperty("ExtendedData")
    var extendedData: ExtendedData? = null,

    @set:JsonProperty("Polygon")
    var polygon: Polygon? = null,

    @set:JsonProperty ("Point")
    var point: Point? = null
)

@JsonRootName ("Point")
data class Point (
    var coordinates: String
)

@JsonRootName ("IconStyle")
data class IconStyle (
    @set:JsonProperty("Icon")
    var icon: Icon
)

@JsonRootName ("IconStyle")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class Icon (
    var value: Any? = null
)

@JsonRootName ("Style")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class Style (
    @JacksonXmlProperty(isAttribute = true)
    var id: String? = null,

    @set:JsonProperty("LineStyle")
    var lineStyle: LineStyle? = null,

    @set:JsonProperty("PolyStyle")
    var polyStyle: PolyStyle? = null,

    @set:JsonProperty("IconStyle")
    var iconStyle: IconStyle? = null
)

@JsonRootName ("LineStyle")
data class LineStyle (
    var color: String
)

@JsonRootName ("PolyStyle")
data class PolyStyle (
    var fill: Int
)

@JsonRootName ("ExtendedData")
data class ExtendedData (
    @set:JsonProperty("SchemaData")
    var schemaData: SchemaData
)

@JsonRootName ("SchemaData")
data class SchemaData (
    @JacksonXmlProperty(isAttribute = true)
    var schemaUrl: String,

    @set:JsonProperty("SimpleData")
    var simpleData: List<SimpleData> = arrayListOf ()
)

@JsonRootName ("SimpleData")
data class SimpleData (
    @JacksonXmlProperty(isAttribute = true)
    var name: String,

    @JacksonXmlText
    var value: String
)

@JsonRootName ("Polygon")
data class Polygon (
    @set:JsonProperty("outerBoundaryIs")
    var outerBoundaryIs: OuterBoundaryIs
)

@JsonRootName ("OuterBoundaryIs")
data class OuterBoundaryIs (
    @set:JsonProperty("LinearRing")
    var linearRing: LinearRing
)

@JsonRootName ("LinearRing")
data class LinearRing (
    var coordinates: String
)

// EOF