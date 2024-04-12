package com.example.pickuppal.reversegeocodingapi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response (
    @Json(name="results") val results: List<Results>
)