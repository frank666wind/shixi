package com.example.express;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.express.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.button);
        Button registerBtn = findViewById(R.id.button2);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    //变量定义和绑定
                    EditText username = (EditText) findViewById(R.id.editTextTextPersonName);
                    EditText password = (EditText) findViewById(R.id.editTextTextPassword);
                    String usernameS = username.getText().toString();
                    String passwordS = password.getText().toString();
                    int userType;

                    @Override
                    public void run() {

                        try {
                            //查询用户名是否已注册
                            FormBody.Builder params = new FormBody.Builder();
                            params.add("username",usernameS);
                            //创建http客户端
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url("http://10.26.7.88:8080/user/getUser")
                                    .post(params.build())
                                    .build();
                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            String usernameD="";
                            String passwordD="";
                            if(responseData.length()!=0){
                                JSONObject jsonObject = new JSONObject(responseData);
                                usernameD = jsonObject.getString("username");
                                passwordD = jsonObject.getString("password");
                                userType = jsonObject.getInt("type");
                            }
                            if(usernameS.equals(usernameD)){
                                if(passwordS.equals(passwordD)){
                                    Intent intent;
                                    if(userType==0){
                                        intent = new Intent(LoginActivity.this,ExpressmanInterfaceActivity.class);
                                    }else{
                                        intent = new Intent(LoginActivity.this,AdministratorInterfaceActivity.class);
                                    }
                                    intent.putExtra(EXTRA_MESSAGE,usernameS);
                                    startActivity(intent);
                                    ToastDisplay("登录成功");
                                }else{
                                    ToastDisplay("密码错误");
                                }

                            }else{
                                ToastDisplay("账户未注册");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            ToastDisplay("error");
                        }
                    }
                }).start();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }



    public void ToastDisplay(final CharSequence text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
}