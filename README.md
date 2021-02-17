# FAA-MVA

A simple application for converting from the [FAA MVA](https://www.faa.gov/air_traffic/flight_info/aeronav/digital_products/mva_mia/mva/) files to KML files that can be loaded into
Google Earth or Foreflight.

## Requires

* Java JDK 1.8+
* Gradle 1.6+


## Running Interactively

```
% gradle run -q --console=plain
FAA MVA charts are available at https://www.faa.gov/air_traffic/flight_info/aeronav/digital_products/mva_mia/mva/
Output: /Users/crawford/Desktop

url> https://www.faa.gov/air_traffic/flight_info/aeronav/digital_products/mva_mia/aixm/A11_MVA_FUS3_2019.xml
Read 93169 bytes.
Write /Users/crawford/Desktop/A11_MVA_FUS3_2019.kml
url>
```
