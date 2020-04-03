package de.trackcovidcluster.source

import org.json.JSONObject

interface IUserStorageSource {

    fun isUserExisting(): Boolean

    fun generateAndSaveKeyPair()

    fun getServerPublicKey(): String?

    fun setPublicKey(publicKey: String)

    fun getUUIDsFromSharedPreferences(): String?

    fun getBase64UserPublicKey(): String?

    fun setUUIDsFromServer(uuidsJson: JSONObject)
}
