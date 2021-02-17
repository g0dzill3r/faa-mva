package com.etherfirma.mva.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

internal val kotlinXmlMapper = XmlMapper(JacksonXmlModule().apply {
    setDefaultUseWrapper(false)
}).registerKotlinModule()
    .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
    .configure(SerializationFeature.INDENT_OUTPUT, true)

object ParserUtil {
    internal inline fun <reified T : Any> parseResource (path: String): T {
        val resource = ResourceUtil.getAsString (path)
        return kotlinXmlMapper.readValue (resource)
    }

    internal inline fun <reified T : Any> parseString (str: String): T {
        return kotlinXmlMapper.readValue (str)
    }
}

fun <T> T.toXml (): String {
    return kotlinXmlMapper.writeValueAsString (this)
}

// EOF