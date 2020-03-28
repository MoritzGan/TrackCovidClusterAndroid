package de.trackcovidcluster.source

interface IUserStorageSource {

    fun isUserExisting(): Boolean

    fun createUser()

    fun createUserKeys()

    fun getUUID() : String?
}
