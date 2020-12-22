package MN.NUM.LAB4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfo extends AppCompatActivity {
    DatabaseHelper db;
    TextView etUsername;
    EditText etAge, etSex, etPhoneNumber;
    DatePicker dpBirthday;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        etUsername = findViewById(R.id.tvName);
        etAge = findViewById(R.id.etAge);
        etSex = findViewById(R.id.etSex);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        dpBirthday = findViewById(R.id.dpBirth);


        db = new DatabaseHelper(this);
        final Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        Cursor cs = db.getUserInfo(username);

        etUsername.setText(cs.getString(0));
        etAge.setText(cs.getString(1));
        etSex.setText(cs.getString(2));
        etPhoneNumber.setText(cs.getString(3));
        String split[]  = cs.getString(4).split("/");
        dpBirthday.updateDate(Integer.valueOf(split[2]), Integer.valueOf(split[1]), Integer.valueOf(split[0]));
    }
    public void changeInfoBtn(View view){
        String age, sex, phone_number, birthdate;
        age = etAge.getText().toString().trim();
        sex = etSex.getText().toString().trim();
        phone_number = etPhoneNumber.getText().toString().trim();
        birthdate = String.valueOf(dpBirthday.getDayOfMonth() + "/" + dpBirthday.getMonth()  +"/"+ dpBirthday.getYear());

        boolean res = db.update(username, age, sex, phone_number, birthdate);
        if(res) {
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Not updated", Toast.LENGTH_SHORT).show();
        }
    }
    public void changePassBtn(View view){
        Intent intentPassword = new Intent(this, ChangePassword.class);
        intentPassword.putExtra("username", username);
        intentPassword.putExtra("password", password);
        startActivityForResult(intentPassword, 2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mClose){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Change password canceled", Toast.LENGTH_SHORT).show();
            }

        }
    }
}