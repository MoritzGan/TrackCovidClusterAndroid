package de.trackcovidcluster.source

import android.content.SharedPreferences
import de.trackcovidcluster.status.Constants
import javax.inject.Inject

class StatusStorageSource @Inject constructor(
    private val mSharedPreferences: SharedPreferences
) : IStatusStorageSource {

    companion object {
        private const val STATUS = "STATUS"
        private const val LAST_CONTACT = "LAST_CONTACT"
    }

    override fun setStatus(status: Int) {
        mSharedPreferences.edit().putInt(STATUS, status).apply()
    }

    override fun setMaybeInfectedStatus() {
        mSharedPreferences.edit().putInt(STATUS, Constants.MAYBE_INFECTED).apply()
    }

    override fun getStatus(): Int =
        mSharedPreferences.getInt(STATUS, -1)

    override fun setContactTime(time: Int) {
        mSharedPreferences.edit().putInt(LAST_CONTACT, time).apply()
    }

    override fun getContactTime() = mSharedPreferences.getInt(LAST_CONTACT, 0)
}
