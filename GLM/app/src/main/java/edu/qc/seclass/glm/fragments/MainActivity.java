package edu.qc.seclass.glm.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.qc.seclass.glm.GroceryListManager;
import edu.qc.seclass.glm.R;
import edu.qc.seclass.glm.custom.GLMFragment;
import edu.qc.seclass.glm.datastructures.Constant;
import edu.qc.seclass.glm.db.Converter;
import edu.qc.seclass.glm.db.GLMDataBase;


import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private GroceryListManager glm;

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* This is where all the magic happens */
        glm = GroceryListManager.getInstance(new GLMDataBase(this), this);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(this);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        this.getSupportActionBar().setCustomView(R.layout.actionbar);

        Constant.calculateWidth(this.getResources().getDisplayMetrics().widthPixels);
        Converter.GLOBAL_WIDTH = this.getResources().getDisplayMetrics().widthPixels;

        this.launchFragment(HomeFragment.getInstance());
    }

    public void launchFragment(GLMFragment fragment){
        fragment.setGLM(this.glm);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    public void switchToSearchFragment(){
        bottomNav.setSelectedItemId(R.id.nav_search);
    }

    public void switchToHomeFragment(){
        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        GLMFragment selected = null;

        if (item.getItemId() == R.id.nav_home)
            selected = HomeFragment.getInstance();
        if (item.getItemId() == R.id.nav_search)
            selected = SearchFragment.instance(null);
        if (item.getItemId() == R.id.nav_settings)
            selected = SettingsFragment.instance();

        launchFragment(selected);

        return true;
    }
}
