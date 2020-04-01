package de.trackcovidcluster.source

interface IUserStorageSource {

    fun isUserExisting(): Boolean

    fun createUserKeys()

    fun getUserPublicKey(): String?

    fun getUUIDsFromUser(): String?

    fun getUserUUID(): String?

    fun getUUIDsFromServerOvr()

    fun sendClusterSubmission(arrayList: ArrayList<String?>) : String?
}
