package com.etherfirma.mva.kml

import com.etherfirma.mva.util.ParserUtil
import com.etherfirma.mva.util.toXml
import com.etherfirma.mva.xml.AIXMBasicMessage
import java.io.File

private val XML_FILE = "ZOA_RBL_MVA_2018.xml"
//private val XML_FILE = "ZOA_QMV_MVA_2018.xml"

/**
 * A command-line app that converts local .xml files to the .kml equivalent.
 */

fun main (args: Array<String>) {
    val xml = ParserUtil.parseResource<AIXMBasicMessage> (XML_FILE)
    val kml = KMLConverter.convert (xml)
    println (kml.toXml ())

    File ("/Users/crawford/Desktop/ZOA_RBL_MVA_2018.kml").writeText (kml.toXml ())

    return
}

// EOF