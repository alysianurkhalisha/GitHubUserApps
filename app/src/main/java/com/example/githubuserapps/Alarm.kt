package com.example.githubuserapps
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.githubuserapps.R

class Alarm : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var reminder: String
    private lateinit var reminderPreference: SwitchPreference


    companion object {
        private const val DEFAULT_VALUE = "none"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)
        reminder = resources.getString(R.string.reminder)
        reminderPreference = findPreference<SwitchPreference>(reminder) as SwitchPreference

        val alarm2= AlarmNotif()

        val shared = preferenceManager.sharedPreferences
        reminderPreference.isChecked = shared.getBoolean(reminder, true)

        reminderPreference.setOnPreferenceChangeListener { preference, newValue ->
            val switched = (preference as SwitchPreference).isChecked
            if (!switched) {
                alarm2.setAlarm(requireContext(), AlarmNotif.TYPE_ALARM,
                    "09:00", "Open Github User Apps")
            } else {
                alarm2.cancelAlarm(requireContext(), AlarmNotif.TYPE_ALARM)
            }
            true
        }

    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == reminder) {
            reminderPreference.isChecked = sharedPreferences.getBoolean(reminder, true)
        }
    }
}