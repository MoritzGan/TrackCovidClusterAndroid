package de.trackcovidcluster.server

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ServerAdapter {

    private val sendJsonDataToServer: SendJsonToServer ? = null

    fun sendDataToServer (
        clusters: Array<String?>?,
        uuid: String?
    ) {
        val post_dict = JSONObject()
        try {
            post_dict.put("Command", "ClusterSubmission")
            post_dict.put("Clusters", clusters)
            post_dict.put("UUID", uuid)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        if (post_dict.length() > 0) {
            sendJsonDataToServer!!.execute(post_dict.toString())
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun statePoll(userid: String): String? {

        val state = JSONObject()
        val data = userid.toByteArray(StandardCharsets.UTF_8)
        val encrData =
            Base64.encodeToString(data, Base64.DEFAULT)
        try {
            state.put("Command", "StatePoll")
            state.put("UUID", encrData)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return if (state.length() > 0) {
            // Returns the Answer from the server
            sendJsonDataToServer!!.doInBackground(state.toString())
        } else {
            null
        }
    }

    // Returns the Answer from the server (pkey)
    val publicKey: String ?
        get() {
            val state = JSONObject()
            try {
                state.put("Command", "RequestServerPubKey")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return if (state.length() > 0) {
                // Returns the Answer from the server (pkey)
                sendJsonDataToServer!!.doInBackground(state.toString())
            } else {
                null
            }
        }
}