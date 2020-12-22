package MN.NUM.LAB4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    EditText etUsername,etAge,etSex,etPhoneNumber,etPassword,etConfirmPassword;
    DatePicker dpBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        etUsername = findViewById(R.id.etUsernameSignup);
        etAge = findViewById(R.id.etAgeSignup);
        etSex = findViewById(R.id.etSexSignup);
        etPhoneNumber = findViewById(R.id.etPhoneNumberSignup);
        etPassword = findViewById(R.id.etPasswordSignup);
        etConfirmPassword = findViewById(R.id.etConfirmPasswordSignup);
        dpBirthday = findViewById(R.id.dpBirthSignup);
        etUsername.setText(username);
    }

    public void saveUserInfo(View view){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean flag = databaseHelper.checkUsername(etUsername.getText().toString());
        String password1 = etPassword.getText().toString();
        String password2 = etConfirmPassword.getText().toString();
        if(flag){
            if(!password2.equals("") && password1.equals(password2)){
                int age = 0;
                String sex = null, phone = null, date = null;
                try{
                    age = Integer.parseInt(etAge.getText().toString());
                    sex = etSex.getText().toString();
                    phone = etPhoneNumber.getText().toString();
                    date = String.valueOf(dpBirthday.getDayOfMonth() + "/" + dpBirthday.getMonth() + "/" + dpBirthday.getYear());

                } catch (NumberFormatException e){
                    Toast.makeText(SignUp.this, "Zuv ugugdul oruulna uu!", Toast.LENGTH_SHORT).show();
                }

                long check = databaseHelper.addUser(etUsername.getText().toString(), password1, age, sex, phone, date);

                if (check > 0) {
                    Toast.makeText(SignUp.this, "Successful", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignUp.this, "Password is not matching", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignUp.this, "Burtgeltei hereglegch baina", Toast.LENGTH_SHORT).show();
        }
    }
    public void cancel(View view){
        Intent resIntent = new Intent();
        setResult(RESULT_CANCELED,resIntent);
        finish();
    }
}