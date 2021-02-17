package com.etherfirma.mva

import com.etherfirma.mva.util.ParserUtil
import com.etherfirma.mva.xml.AIXMBasicMessage

internal val XML_FILE = "ZOA_RBL_MVA_2018.xml"

fun getSampleXml (): AIXMBasicMessage {
    return ParserUtil.parseResource<AIXMBasicMessage> (XML_FILE)
}

fun main (args: Array<String>) {
    val xml = getSampleXml()
    val el = xml.hasMember[0].airspace.timeSlice.airspaceTimeSlice.geometryComponent
    println (el)
    return
}

// EOF