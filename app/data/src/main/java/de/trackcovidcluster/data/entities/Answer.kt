package de.trackcovidcluster.data.entities

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("Encounters")
    val encounters: List<String>?,
    @SerializedName("ServerPubKey")
    val publicKey: String?
)
