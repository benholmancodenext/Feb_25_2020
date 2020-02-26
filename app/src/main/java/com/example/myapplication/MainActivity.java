package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String THEME_KEY = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Get the preferred theme. This uses "SharedPreferences" to store key-value pairs. For this,
        we're using a string as the key to look up an integer which denotes the Theme ID.
        Note: setTheme needs to be called before setContentView
         */
        final SharedPreferences sharedPreferences = getSharedPreferences("mySharedPreferencesFile", Context.MODE_PRIVATE);
        // The 2nd argument here is the "default" which means if we can't find anything, just use R.style.AppTheme
        final int themeResource = sharedPreferences.getInt(THEME_KEY, R.style.AppTheme);

        // Set the theme based on what we found in sharedPreferences
        setTheme(themeResource);

        // Now that the theme is set, we can set the content view. This is required because layout
        // inflation uses the currently active theme to create the views.
        setContentView(R.layout.activity_main);

        // Store a reference to "this" context. Will be used below in the click listener
        final Context activityContext = this;

        // Get our button from the inflated layout
        Button goToNextScreenButton = findViewById(R.id.goToSecondActivityButton);

        // Set a new click listener on our button
        goToNextScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make a new Intent pointing from here to the 2nd Activity
                Intent myIntent = new Intent(activityContext, MySecondActivity.class);
                // Actually start the new intent
                startActivity(myIntent);
            }
        });

        Button changeThemeButton = findViewById(R.id.toggleThemeButton);
        changeThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check to see which theme is active. This uses the value pulled out of SharedPrefs above
                // Swap to a new theme by putting the value into SharedPrefs
                if (themeResource == R.style.AppTheme) {
                    Log.d(TAG, "Switching to AppThemeLight");
                    sharedPreferences.edit().putInt(THEME_KEY, R.style.AppThemeLight).apply();
                } else {
                    Log.d(TAG, "Switching to AppTheme");
                    sharedPreferences.edit().putInt(THEME_KEY, R.style.AppTheme).apply();
                }
                // Recreates this Activity so that we can apply our new theme. This will go back up
                // to onCreate and run this code again, thus applying the new theme.
                recreate();
            }
        });
    }
}
