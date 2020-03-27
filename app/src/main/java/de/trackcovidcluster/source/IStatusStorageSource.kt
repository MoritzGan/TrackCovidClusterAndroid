package de.trackcovidcluster.source

interface IStatusStorageSource {

    fun setStatus(status : Int)

    fun setMaybeInfectedStatus()

    fun getStatus() : Int
}
