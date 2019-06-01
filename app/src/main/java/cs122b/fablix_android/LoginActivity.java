package cs122b.fablix_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET;
    private EditText passET;
    private Button loginBtn;
    private TextView titleTv;
    private TextView resTV;
    private static final String TAG = "MainActivity";
    private  String awsAPIURL = FetchURL.LOGIN_URL;

    String username = "tshpark@gmail.com";
    String password = "2001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity: onCreate: started.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        Log.d(TAG, "onCreate: ended.");
    }
    private void initView(){
        Log.d(TAG, "MainActivity: initView: started.");
        emailET = findViewById(R.id.emailET);
        passET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.loginBtn);
        resTV = findViewById(R.id.resView);
        Log.d(TAG, "MainActivity: initView: Ended.");
    }

    public void connectToTomcat(View view)
    {
        Log.d(TAG, "MainActivity: connectToTomcat: started.");
        String email = emailET.getText().toString();
        String pass = passET.getText().toString();
        String data = "username="+ emailET.getText().toString() + "&" +"password="+ passET.getText().toString();
        //String data = "username="+ username + "&" +"password="+ password;
        new LoginTask().execute(awsAPIURL,data);
        Log.d(TAG, "MainActivity: connectToTomcat: ended.");
    }


    private class LoginTask extends AsyncTask<String, Void, Boolean> {
        private final static String TAG = "LoginTask";
        String res = "no";
        @Override
        protected Boolean doInBackground(String... params) {
            Log.d(TAG, "doInBackground: started.");
            String urlString = params[0]; // URL to call
            String data = params[1]; //data to post
            // OutputStream out = null;
            NukeSSLCerts.nuke();
            try {

                Log.e(TAG, "starting url post connection");
                URL url = new URL(urlString);
                Log.e(TAG, url.toString());

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("User-Agent", "Android");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");
                urlConnection.setDoOutput(true);

                OutputStream os = urlConnection.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                urlConnection.connect();


                int responseCode = urlConnection.getResponseCode();
                Log.e(TAG,"POST Response Code :: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                        Log.d(TAG, "doInBackground: inputLine during while loop: " + inputLine);
                    }
                    in.close();

                    // print result
                    Log.e(TAG,response.toString());

                } else {
                    Log.e(TAG,"POST request not worked");
                    Log.d(TAG, "doInBackground: ended.");
                    return false;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Log.d(TAG, "doInBackground: ended.");
            return true;
        }




        protected void onPostExecute(Boolean result) {
            Log.d(TAG, "onPostExecute: started.");
            Log.e(TAG,String.valueOf(result));

            if(result) {
                Log.d(TAG, "onPostExecute: result=true & Intent creation.");
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                Log.d(TAG, "onPostExecute: finished intent creation.");
                startActivity(intent);
            }
            else
                Toast.makeText(getApplicationContext(),"Invalid User or Password!", Toast.LENGTH_LONG).show();

            Log.d(TAG, "onPostExecute: ended.");
        }
    }
}
