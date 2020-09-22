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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //加载界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Button button = findViewById(R.id.BtnRR);

        //按钮监听器
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new Thread(new Runnable() {
                    //变量定义和绑定
                    EditText username = (EditText)findViewById(R.id.UserNameR);
                    EditText password1 = (EditText)findViewById(R.id.PasswordR1);
                    EditText password2 = (EditText)findViewById(R.id.PasswordR2);
                    EditText adminiCode = (EditText)findViewById(R.id.AdminiCode);

                    String usernameS = username.getText().toString();
                    String passwordS1 = password1.getText().toString();
                    String passwordS2 = password2.getText().toString();
                    String adminiCodeS = adminiCode.getText().toString();

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
                            if(responseData.length()!=0){
                                JSONObject jsonObject = new JSONObject(responseData);
                                usernameD = jsonObject.getString("username");
                            }


                            if(usernameS.equals(usernameD)){
                                ToastDisplay("用户名已注册，请使用其他名称");
                            }else if(passwordS1.length()<8){
                                ToastDisplay("密码长度太短(应大于8位）");
                            }
                            else if (!(passwordS1.equals(passwordS2))){
                                ToastDisplay("密码不一致");
                            }else{
                                //
                                int userType;
                                if (adminiCodeS.equals("root1234")){
                                    userType = 1;
                                }
                                else{
                                    userType = 0;
                                }
                                //插入用户记录（创建新用户）
                                String json = "{    \n" +
                                        "    \"username\":\""+usernameS+"\",\n" +
                                        "    \"password\":\""+passwordS1+"\",\n" +
                                        "    \"type\":"+userType+"\n" +
                                        "}";
                                //创建http客户端
                                client = new OkHttpClient();
                                request = new Request.Builder()
                                        .url("http://10.26.7.88:8080/user/createUser")
                                        .post(RequestBody.create(MediaType.parse("application/json"),json))
                                        .build();
                                client.newCall(request).execute();
                                ToastDisplay("注册成功，请登录");
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }



                        }catch(Exception e){
                            e.printStackTrace();
                            ToastDisplay("error");
                        }
                    }
                }).start();
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