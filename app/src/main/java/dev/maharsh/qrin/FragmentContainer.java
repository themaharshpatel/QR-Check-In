package dev.maharsh.qrin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

public class FragmentContainer extends AppCompatActivity {

    NavController navController;
    MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        navController = NavHostFragment
                .findNavController(Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.fragment_container)));
        toolbar = findViewById(R.id.top_AppBar);
        setSupportActionBar(toolbar);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.splashScreen,R.id.homeFragment
                ).build();
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);


        new Handler(Looper.myLooper()).post(()-> navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == R.id.homeFragment)
            {
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.profile_menu);
            }
            else
            {
                toolbar.getMenu().clear();
            }
            if(destination.getId() == R.id.splashScreen){
                Objects.requireNonNull(getSupportActionBar()).hide();
            }
            else
                Objects.requireNonNull(getSupportActionBar()).show();
        }));
        toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId()==R.id.menu_profile_settings){
                navController.navigate(R.id.action_homeFragment_to_settingsFragment);
            }
            return false;
        });


    }
}