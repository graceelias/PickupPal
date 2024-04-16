package com.example.pickuppal.directionsapi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DirectionsPolyline(
    @Json(name="points") val points: String
)