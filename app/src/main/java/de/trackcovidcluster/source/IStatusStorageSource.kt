package de.trackcovidcluster.source

interface IStatusStorageSource {

    fun setStatus(status: Int)

    fun setMaybeInfectedStatus()

    fun getStatus(): Int

    fun setContactTime(time: Int)

    fun getContactTime(): Int
}
