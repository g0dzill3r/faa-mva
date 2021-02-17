package com.etherfirma.mva.util

import org.w3c.dom.Document
import javax.xml.parsers.DocumentBuilderFactory

object XMLUtil {
    fun parse (str: String): Document {
        val istr = str.byteInputStream ()
        val xmlDoc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse (istr)
        xmlDoc.documentElement.normalize()
        return xmlDoc
    }
}

// EOF