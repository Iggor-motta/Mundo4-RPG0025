package com.doma.domawearsos

import android.content.Context
import android.media.AudioDeviceInfo
import android.content.pm.PackageManager
import android.media.AudioManager

class AudioHelper(private val appContext: Context) {
    private val audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun isAudioOutputAvailable(deviceType: Int): Boolean {
        val packageManager = appContext.packageManager
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
            return false
        }

        val devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        for (device in devices) {
            if (device.type == deviceType) {
                return true
            }
        }
        return false
    }
}
