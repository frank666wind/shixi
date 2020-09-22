package com.example.express;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdministratorInterfaceActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.express.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_interface);

        Context context = getApplicationContext();
        CharSequence text = "登录成功";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void Search(View view){
        EditText editText = (EditText)findViewById(R.id.editTextTextPersonName3);
        String message = editText.getText().toString();

        //TextView textView = findViewById(R.id.textView8);
        //textView.setText(message);

        Intent intent = new Intent(this, ExpressmanInterfaceActivity.class);
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
}