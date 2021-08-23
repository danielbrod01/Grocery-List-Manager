package edu.qc.seclass.glm;

import android.content.Context;
import android.content.SharedPreferences;

import edu.qc.seclass.glm.fragments.SettingsFragment;

public class SharedPreference {
    SharedPreferences mySharedPreference;
    public SharedPreference (Context context) {
        mySharedPreference = context.getSharedPreferences("filename", Context.MODE_PRIVATE);

    }

    /* Method for setting the state of Night Mode*/
    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor = mySharedPreference.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }

    /* Method for loading the Night Mode state */
    public Boolean loadNightModeState(){
        return mySharedPreference.getBoolean("NightMode", false);
    }
}
