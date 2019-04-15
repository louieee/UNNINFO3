package ng.edu.unn.unninfo;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.Preference;
import android.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener{
    //updates changes in settings
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
         Preference preference = findPreference(key);
            if (!(preference == null)){
                if (!(preference instanceof CheckBoxPreference)) {
                    String Value = sharedPreferences.getString(preference.getKey(), "");
                    setPreferenceSummary(preference, Value);
                }
            }
    }
// gets settings layout
    @Override
    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        super.setPreferenceScreen(preferenceScreen);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loads settings layout
        addPreferencesFromResource(R.xml.pref_unn_info);
        getPreferenceScreen().setLayoutResource(R.layout.activity_settings);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        //get access to all preferences in SharedPreferences
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        //get the total number of preferences
        int count = preferenceScreen.getPreferenceCount();
//
        for (int i = 0; i < count; i++) {
            Preference p = preferenceScreen.getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                String Value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, Value);
            }
        }
    }
// unregisters a change in settings
    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
// sets a particular setting description
    private void setPreferenceSummary(Preference p, String value){
        if (p instanceof ListPreference){
             ListPreference listPreference = (ListPreference)p;
             int prefIndex = listPreference.findIndexOfValue(value);
             if (prefIndex >= 0){
                 listPreference.setSummary(listPreference.getEntries()[prefIndex]);
             }
        }
    }
}