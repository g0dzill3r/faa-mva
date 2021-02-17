package com.etherfirma.mva.kml

import com.etherfirma.mva.util.ParserUtil
import com.etherfirma.mva.util.URLFetcher
import com.etherfirma.mva.util.toXml
import com.etherfirma.mva.xml.AIXMBasicMessage
import java.io.File

fun interpreter (prompt: String = "> ", func: (String) -> Unit) {
    while (true) {
        print (prompt)
        val str = readLine()
        if (str.isNullOrEmpty()) {
            break;
        }
        func (str)
    }
    return
}

private fun generateFilename (url: String): String {
    val i = url.lastIndexOf ("/")
    val j = url.indexOf (".", i)
    return url.substring (i + 1, j)
}

private val FAA_URL = "https://www.faa.gov/air_traffic/flight_info/aeronav/digital_products/mva_mia/mva/";

/**
 * A handy command-line application for iteratively converting from a URL to a local .kml file.
 */

fun main (args: Array<String>) {
    println ("FAA MVA charts are available at $FAA_URL\n")
    interpreter ("url> ") { url ->
        val str = URLFetcher.fetch (url)
        println ("Read ${str.length} bytes.")

        val xml = ParserUtil.parseString<AIXMBasicMessage> (str)
        val kml = KMLConverter.convert (xml)

        val fragment = generateFilename (url)
        val home = System.getProperty("user.home")
        val filename = home + File.separatorChar + "Desktop" + File.separatorChar + fragment + ".kml"
        File (filename).writeText (kml.toXml ())
        println ("Write $filename")
    }
    return
}

// EOF