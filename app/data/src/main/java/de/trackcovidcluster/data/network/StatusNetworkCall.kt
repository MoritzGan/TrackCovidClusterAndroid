package de.trackcovidcluster.data.network

import com.example.data.entities.Request
import de.trackcovidcluster.data.entities.Answer
import de.trackcovidcluster.data.api.TrackCovidClusterAPI
import io.reactivex.Observable

class StatusNetworkCall(private val trackCovidAPI: TrackCovidClusterAPI) {

    fun getStatus(body: Request): Observable<Answer> {
        return trackCovidAPI.getStatusFromAPI(body = body)
            .map { response ->
                response.answer
            }
    }
}
