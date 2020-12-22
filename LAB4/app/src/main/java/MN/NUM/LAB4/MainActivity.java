package MN.NUM.LAB4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etUsername,etPassword;
    DatabaseHelper db;
    public static final String MyPREFERENCES = "Prefs" ;
    public static final String Name = "Key";
    public static String user_name="";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);


        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        this.user_name = sharedpreferences.getString(Name, "");
        etUsername.setText(user_name);

    }
    public void signupBtn(View view){
        Intent signUpIntent = new Intent(MainActivity.this, SignUp.class);
        signUpIntent.putExtra("username", etUsername.getText().toString().trim());
        signUpIntent.putExtra("password", etPassword.getText().toString().trim());
        startActivityForResult(signUpIntent, 1);
    }
    public void signinBtn(View view){
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        System.out.println(username);
        boolean result = db.checkUser(username, password);
        if(result){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Name, username);
            editor.apply();
            Intent userInfoIntent = new Intent(MainActivity.this, UserInfo.class);
            userInfoIntent.putExtra("username", username);
            userInfoIntent.putExtra("password", password);
            startActivity(userInfoIntent);
        }
        else {
            Toast.makeText(MainActivity.this, "Username or Password incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this, "Sign up canceled", Toast.LENGTH_SHORT).show();
            }

        }
    }

}