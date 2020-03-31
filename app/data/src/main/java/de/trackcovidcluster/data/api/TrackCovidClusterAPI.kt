package de.trackcovidcluster.data.api

import de.trackcovidcluster.data.entities.Request
import de.trackcovidcluster.data.entities.Result
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface TrackCovidClusterAPI {
    //https://api.trackcovidcluster.de:12345/json
    @POST("json")
    fun getStatusFromAPI(@Body body: Request): Observable<Result>

    @POST("json")
    fun getPublicKey(@Body body: Request): Observable<Result>

    @POST("json")
    fun getUUIDs(@Body body: Request): Observable<Result>

    @POST("json")
    fun sendBundle(@Body body: Request): Observable<Result>

    companion object {
        fun create(): TrackCovidClusterAPI {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.trackcovidcluster.de:12345/")
                .build()

            return retrofit.create(TrackCovidClusterAPI::class.java)
        }
    }
}

