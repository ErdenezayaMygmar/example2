package MN.NUM.LAB4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    EditText etPasswordChange,etNewPasswordChange,etConfirmPasswordChange;
    DatabaseHelper db;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        db = new DatabaseHelper(this);
        etPasswordChange = findViewById(R.id.etPasswordChange);
        etNewPasswordChange = findViewById(R.id.etNewPasswordChange);
        etConfirmPasswordChange = findViewById(R.id.etConfirmPasswordChange);

        Intent intent = getIntent();
         username = intent.getStringExtra("username");
         password = intent.getStringExtra("password");
    }
    public void btnSave(View view){
        String oldPass = etPasswordChange.getText().toString().trim();
        String newPass = etNewPasswordChange.getText().toString().trim();
        String confPass = etConfirmPasswordChange.getText().toString().trim();
        if(password.equals(oldPass)){
            if(newPass.equals(confPass)){
                boolean res = db.updatePassword(username,newPass);
                if(res){
                    Toast.makeText(this, "Password changed", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Confirmation password mismatch", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Old password mismatch", Toast.LENGTH_SHORT).show();
        }
    }
}