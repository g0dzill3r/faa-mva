package com.etherfirma.mva.util

import java.io.IOException
import java.io.InputStream

object ResourceUtil {

    /**
     *
     */

    fun getAsStream (path: String): InputStream {
        val cls = Thread.currentThread().contextClassLoader
        val inputStream = cls.getResourceAsStream(path) ?: throw IOException ("Resource not found: $path")
        return inputStream
    }

    /**
     * Used to fetch the contents of a resource as a String.
     */

    fun getAsString (path: String): String {
        val cls = Thread.currentThread().contextClassLoader
        val inputStream = cls.getResourceAsStream(path) ?: throw IOException ("Resource not found: $path")
        val bufferSize = 1024
        val buffer = CharArray(bufferSize)
        val out = StringBuilder()
        val istr = java.io.InputStreamReader(inputStream, "UTF-8")
        while (true) {
            val rsz = istr.read(buffer, 0, buffer.size)
            if (rsz < 0)
                break
            out.append(buffer, 0, rsz)
        }
        val results = out.toString ()
        return results
    }
}

// EOF