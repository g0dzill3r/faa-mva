package com.etherfirma.mva.xml

import com.etherfirma.mva.util.URLFetcher

internal val URL = "https://www.faa.gov/air_traffic/flight_info/aeronav/digital_products/mva_mia/aixm/ZOA_QMV_MVA_2018.xml"

fun main (args: Array<String>) {
    val str = URLFetcher.fetch (URL)
    println (str)
    return
}

// EOF