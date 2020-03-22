package de.trackcovidcluster.source

interface IUserStorageSource {

    fun isUserExisting(): Boolean

    fun createUser()
}
