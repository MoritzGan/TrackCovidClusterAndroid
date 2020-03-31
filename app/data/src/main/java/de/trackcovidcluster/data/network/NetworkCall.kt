package de.trackcovidcluster.data.network

import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.entities.Request
import io.reactivex.Observable

class NetworkCall(private val trackCovidAPI: TrackCovidClusterAPI) {

    fun getStatus(body: Request): Observable<List<String>?> {
        return trackCovidAPI.getStatusFromAPI(body = body)
            .map { response ->
                response.answer.encounters
            }
    }

    fun getPublicKey(): Observable<String> {
        val body = Request(
            command = "RequestServerPubKey"
        )
        return trackCovidAPI.getPublicKey(body = body)
            .map { response ->
                response.answer.publicKey
            }
    }

    fun getUUIDs() : Observable<List<String>?> {
        val body = Request(
            command = "UUIDPoll"
        )
        return trackCovidAPI.getUUIDs(body = body)
            .map { response ->
                response.answer.uuids
            }
    }

    fun sendBundle(userUUID: String) : Observable<String> {
        val body = Request(
            command = "ClusterSubmission",
            uuid = userUUID
        )
        return trackCovidAPI.sendBundle(body = body)
            .map { response ->
                response.answer.clusters.toString()
            }
    }
}
