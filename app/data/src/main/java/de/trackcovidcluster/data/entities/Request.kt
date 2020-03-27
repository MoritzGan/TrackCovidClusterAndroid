package de.trackcovidcluster.data.entities

import com.google.gson.annotations.SerializedName

data class Request(
    @SerializedName("Command")
    val command: String,
    @SerializedName("UUID")
    val uuid: String? = null
)
