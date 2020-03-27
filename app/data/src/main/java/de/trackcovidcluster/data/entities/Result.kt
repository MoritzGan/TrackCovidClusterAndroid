package de.trackcovidcluster.data.entities

import com.google.gson.annotations.SerializedName
import de.trackcovidcluster.data.entities.Answer

data class Result(
    @SerializedName("Answer")
    val answer: Answer
)
