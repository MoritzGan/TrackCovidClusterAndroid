package de.trackcovidcluster.data.entities

import com.google.gson.annotations.SerializedName

data class RequestClusters(
    @SerializedName("Command")
    val command: String,
    @SerializedName("UUID")
    val uuid: String? = null,
    @SerializedName("Clusters")
    val clusters: ArrayList<String?>
)