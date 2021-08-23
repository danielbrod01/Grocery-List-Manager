package edu.qc.seclass.glm.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.SharedPreference;
import edu.qc.seclass.glm.custom.GLMFragment;
import edu.qc.seclass.glm.db.DBHelper;

public class SettingsFragment extends GLMFragment implements View.OnClickListener{
    View v;
    private static SettingsFragment instance;

    public static SettingsFragment instance(){
        if (instance == null)
            instance = new SettingsFragment();
        return instance;
    }

    private SettingsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreference sharedPreference = new SharedPreference(getActivity());

        if(sharedPreference.loadNightModeState()){
            getActivity().setTheme(R.style.Theme_GLM);
        }
        else{
            getActivity().setTheme(R.style.Theme_GLM);
        }

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch myswitch = (Switch) v.findViewById(R.id.darkmodeSwitch);
        if(sharedPreference.loadNightModeState()){
            myswitch.setChecked(true);
        }
        myswitch.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if(isChecked){
                sharedPreference.setNightModeState(true);
                getActivity().setTheme(R.style.Theme_GLM);
            }
            else{
                sharedPreference.setNightModeState(false);
//                instance.getView().setBackgroundColor(Color.WHITE);
            }
        }));
        setupHyperlinks();

        /* This Line needs to be commented out in order for user setting to be retained*/
//        return inflater.inflate(R.layout.fragment_settings, container, false);
        return v;
    }
    // sets up the hyper links for the extra info in the settings page
    private void setupHyperlinks() {
        TextView covidTextView = v.findViewById(R.id.textInfoLine3);
        TextView termsTextView = v.findViewById(R.id.textInfoLine4);
        TextView privacyTextView = v.findViewById(R.id.textInfoLine5);
        privacyTextView.setMovementMethod(LinkMovementMethod.getInstance());
        termsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        covidTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.back_settingsFragment).setOnClickListener(this);
        this.glm.setAppTitle("App Settings");


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_settingsFragment){

        }
    }

}
