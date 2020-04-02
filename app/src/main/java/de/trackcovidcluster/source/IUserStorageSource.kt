package de.trackcovidcluster.source

import org.json.JSONObject

interface IUserStorageSource {

    fun isUserExisting(): Boolean

    fun createUserKeys()

    fun getUserPublicKey(): String?

    fun setPublicKey(publicKey: String)

    fun getUUIDsFromUser(): String?

    fun getUserUUID(): String?

    fun setUUIDsFromServer(uuidsJson: JSONObject)
}
