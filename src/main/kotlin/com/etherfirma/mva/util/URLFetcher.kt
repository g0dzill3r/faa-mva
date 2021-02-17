package com.etherfirma.mva.util

import java.net.URL
import java.io.InputStreamReader

import java.io.BufferedReader
import java.io.InputStream


object URLFetcher {
    fun fetch (str: String): String {
        return fetch (URL (str))
    }

    fun fetch (url: URL): String {
        val istr: InputStream = url.openConnection().getInputStream()
        val reader = BufferedReader(InputStreamReader(istr))
        try {
            val buf = StringBuffer()

            var line: String? = null
            while (reader.readLine().also { line = it } != null) {
                buf.append (line)
                buf.append ("\n")
            }
            return buf.toString ()
        }
        finally {
            reader.close()
        }
    }

}

// EOF