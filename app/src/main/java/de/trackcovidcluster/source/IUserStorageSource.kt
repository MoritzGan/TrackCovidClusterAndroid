package de.trackcovidcluster.source

interface IUserStorageSource {

    fun isUserExisting(): Boolean

    fun createUser()

    fun createUserKeys()

    fun getUserPublicKey(): String?

    fun getUUIDsFromUser(): String?
}
