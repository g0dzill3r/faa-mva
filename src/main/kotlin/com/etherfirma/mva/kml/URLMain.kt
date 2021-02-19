package com.etherfirma.mva.kml

import com.etherfirma.mva.faa.FAA
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



/**
 * A handy command-line application for iteratively converting from a URL to a local .kml file.
 */

fun main (args: Array<String>) {
    println ("FAA source files are available at:")
    println (" - MVA: ${FAA.URL.MVA}")
    println (" - MIA: ${FAA.URL.MIA}")

    val home = System.getProperty("user.home") + File.separatorChar + "Desktop"
    println ("Output to: $home")
    println ()

    interpreter ("url> ") { url ->
        val str = URLFetcher.fetch (url)
        println ("Read ${str.length} bytes.")

        val xml = ParserUtil.parseString<AIXMBasicMessage> (str)
        val kml = KMLConverter.convert (xml)

        val fragment = generateFilename (url)
        val filename = home + File.separatorChar + fragment + ".kml"
        val converted = kml.toXml ()
        File (filename).writeText (converted)
        println ("Wrote ${converted.length} bytes to $filename")
    }
    return
}

// EOF