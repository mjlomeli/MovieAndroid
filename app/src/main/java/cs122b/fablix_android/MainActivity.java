package cs122b.fablix_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    private static final boolean TESTING = false;        // change to false if doing the demo
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent;

        /*
        Change TESTING to false if its for the demo
         */
        if (TESTING) {
            intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            startActivity(intent);
        }
        else {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}
