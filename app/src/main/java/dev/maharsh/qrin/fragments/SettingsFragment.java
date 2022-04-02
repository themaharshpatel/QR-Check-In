package dev.maharsh.qrin.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import dev.maharsh.qrin.DayNightSettings;
import dev.maharsh.qrin.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    SharedPreferences.OnSharedPreferenceChangeListener spChanges;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        spChanges = (sharedPreferences, key) -> {
            try {
                if(key!=null){
                    if (key.equals("preference_night_mode"))
                        DayNightSettings.setDefaultMode(getContext());
                }
            }
            catch (Exception ignored){
            }

        };
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(spChanges);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(spChanges);
    }
}