package de.trackcovidcluster.data.network

import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import de.trackcovidcluster.data.entities.Answer
import de.trackcovidcluster.data.entities.Request
import io.reactivex.Observable

class NetworkCall(private val trackCovidAPI: TrackCovidClusterAPI) {

    fun getStatus(body: Request): Observable<Answer> {
        return trackCovidAPI.getStatusFromAPI(body = body)
            .map { response ->
                response.answer
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
}
