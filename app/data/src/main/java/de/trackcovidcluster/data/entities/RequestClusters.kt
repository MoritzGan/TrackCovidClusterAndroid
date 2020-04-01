package de.trackcovidcluster.data.entities

import com.google.gson.annotations.SerializedName

data class RequestClusters(
    @SerializedName("Command")
    val command: String,
    @SerializedName("Clusters")
    val clusters: ArrayList<String?>,
    @SerializedName("UUID")
    val uuid: String? = null
)
