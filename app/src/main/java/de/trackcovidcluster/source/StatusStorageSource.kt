package de.trackcovidcluster.source

import android.content.SharedPreferences
import de.trackcovidcluster.status.Constants
import javax.inject.Inject

class StatusStorageSource @Inject constructor(
    private val mSharedPreferences: SharedPreferences
) : IStatusStorageSource {

    companion object {
        private const val STATUS = "STATUS"
    }

    override fun setStatus(status: Int) {
        mSharedPreferences.edit().putInt(STATUS, status).apply()
    }

    override fun setMaybeInfectedStatus() {
        mSharedPreferences.edit().putInt(STATUS, Constants.MAYBE_INFECTED).apply()
    }

    override fun getStatus(): Int =
        mSharedPreferences.getInt(STATUS, -1)

}
