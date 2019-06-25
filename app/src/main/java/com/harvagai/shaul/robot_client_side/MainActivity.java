package com.harvagai.shaul.robot_client_side;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    //Constants and protocol
    final static boolean SIGN_IN = true;
    final static boolean SIGN_UP = false;
    final static  int KEY_EMAIL = 1;
    final static int KEY_PASSWORD = 2;

    //Connection to the server
    //Credit to http://www.myandroidsolutions.com/2013/03/31/android-tcp-connection-enhanced/#.W_0NQmhvbcd
    private ListView List;
    private ArrayList<String> arrayList;

    //GUI elements
    private Button btnSignIn, btnSignUp, btnConfirm;
    private EditText etEmail, etPassword, etConfirmPassword;
    private TextView tvConfirmPassword;
    private CheckBox cbRememberMe;

    //Shared preference
    SharedPreferences spRememberMe;

    //variables
    boolean intention = SIGN_IN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvConfirmPassword = findViewById(R.id.tvConfirmPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);

        spRememberMe = getSharedPreferences("remember_me",MODE_PRIVATE);

        String email,password;
        if(spRememberMe.contains("Password"))
        {
            email = spRememberMe.getString("Email","");
            password = spRememberMe.getString("Password","");
            etEmail.setText(email);
            signIn(email,password,false);
        }

    }

    public void onClickSignIn(View v)
    {
        if (intention != SIGN_IN) {
            tvConfirmPassword.setVisibility(View.INVISIBLE);
            etConfirmPassword.setVisibility(View.INVISIBLE);
            cbRememberMe.setVisibility(View.VISIBLE);
            intention = SIGN_IN;
        }
    }

    public void onClickSignUp(View v)
    {
        if (intention != SIGN_UP) {
            tvConfirmPassword.setVisibility(View.VISIBLE);
            etConfirmPassword.setVisibility(View.VISIBLE);
            cbRememberMe.setVisibility(View.VISIBLE);
            intention = SIGN_UP;
        }
    }

    public void onClickConfirm(View v) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if (intention == SIGN_IN) {
            signIn(email, password);
        } else if (intention == SIGN_UP) {
            signUp(email, password, confirmPassword);
        }
    }


    protected void goToMenu(int authority)
    {
        Intent i = new Intent(this,MenuActivity.class);
        i.putExtra("authority",authority);
        startActivity(i);
    }

    protected void signIn(String email, String password, boolean alertUser)
    {
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {
            if(alertUser) {
                Toast.makeText(this, "Make sure to fill all the fields", Toast.LENGTH_SHORT).show();
            }
        } else if (!isEmailValid(email)) {
            if(alertUser) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            }
        } else if (!isPasswordValid(password)) {
            if(alertUser) {
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_LONG).show();
            }
        } else {

            String msgStr = "200###" + etEmail.getText().toString() + "###" + etPassword.getText().toString();
            Log.d("ToServer", msgStr);
            //TODO- Handle response from server
            Connection conn = new Connection();
            String[] args = new String[4];
            args[Connection.IP] = "192.168.5.112";
            args[Connection.PORT] = "1267";
            args[Connection.CODE] = "200";
            args[Connection.MESSAGE] = msgStr;
            if (cbRememberMe.isChecked()) {
                spRememberMe.edit().putString("Email", email).apply();
                if (!spRememberMe.edit().putString("Password", password).commit())//if commit failed
                {
                    if(alertUser) {
                        Toast.makeText(this, "Failed to remember you", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                spRememberMe.edit().putString("Email", email).apply();
                if (!spRememberMe.edit().putString("Password", "").commit())//if commit failed
                {
                    Log.d("Shared Preference", "Failed to commit changes");
                }
            }
            goToMenu(4);//MISC NUMBER
        }
    }

    protected void signIn(String email,String password) {
        signIn(email,password,true);
    }

    protected void signUp(String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(email) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Makre sure to fill all the fields", Toast.LENGTH_SHORT).show();
        } else if (!isEmailValid(email)) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        } else if (!isPasswordValid(password)) {
            Toast.makeText(this, "Invalid Password.\npassword must be at least 8 digits long and must contain" +
                    " one alphabetic character, one digit and one sign ($!*?+_=.\\/-) ", Toast.LENGTH_LONG).show();
        } else if (!TextUtils.equals(password, confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        } else {
            String msgStr = "203###" + email + "###" + password;
            Log.d("ToServer", msgStr);
            //TODO- Handle response from server
            if (cbRememberMe.isChecked()) {
                spRememberMe.edit().putString("Email", email).apply();
                if (!spRememberMe.edit().putString("Password", password).commit())//if commit failed
                {
                    Toast.makeText(this, "Failed to remember you", Toast.LENGTH_SHORT).show();
                }
                goToMenu(1);//MISC NUMBER
            } else {
                spRememberMe.edit().putString("Email", email).apply();
                if (!spRememberMe.edit().putString("Password", "").commit())//if commit failed
                {
                    Log.d("Shared Preference", "Failed to commit changes");//TODO secret
                }
                goToMenu(1);//MISC NUMBER
            }
        }
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return  matcher.matches();
    }

    public boolean isPasswordValid(String password)
    {
        String regExpn =
                "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[$!*?+_=.\\/-])[A-Za-z0-9$!*?+_=.\\/-]{8,}$";

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
