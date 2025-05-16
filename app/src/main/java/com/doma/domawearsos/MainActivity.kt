package com.doma.domawearsos

import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listButton = findViewById<Button>(R.id.addTaskButton)
        listButton.setOnClickListener { showAlertDialog() }

        val checkAudioButton = findViewById<Button>(R.id.button_check_audio)
        checkAudioButton.setOnClickListener { checkAudioOutput() }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Ação do Botão")
        builder.setMessage("Botão acionado!")

        builder.setPositiveButton(
            "OK"
        ) { dialog, which ->

        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun checkAudioOutput() {
        val audioHelper = AudioHelper(this)

        val isSpeakerAvailable =
            audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BUILTIN_SPEAKER)
        val isBluetoothHeadsetConnected =
            audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)

        Toast.makeText(this, "Alto-falante disponível: $isSpeakerAvailable", Toast.LENGTH_SHORT)
            .show()
        Toast.makeText(
            this,
            "Fone de ouvido Bluetooth conectado: $isBluetoothHeadsetConnected", Toast.LENGTH_SHORT
        ).show()
    }

    internal inner class AudioHelper(private val context: Context) {
        private val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager

        fun audioOutputAvailable(type: Int): Boolean {
            if (!context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
                return false
            }
            val devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
            for (device in devices) {
                if (device.type == type) {
                    return true
                }
            }
            return false
        }
    }
}
