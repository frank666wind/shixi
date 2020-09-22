package com.example.express;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.TabActivity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.view.LayoutInflater;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ExpressmanInterfaceActivity extends TabActivity {


    private final int MSG_HELLO = 0;

    private Handler mHandler;

    private TabHost myTabhost;
    public static final String EXTRA_MESSAGE = "com.example.express.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myTabhost=this.getTabHost();
        LayoutInflater.from(this).inflate(R.layout.activity_expressman_interface,myTabhost.getTabContentView(),true);
        Intent intent = getIntent();
        String username = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        new CustomThread().start();//新建并启动CustomThread实例


        myTabhost.addTab(
                myTabhost.newTabSpec("doing").setIndicator("当前派送订单").setContent(R.id.listView1)
        );
        myTabhost.addTab(
                myTabhost.newTabSpec("completed").setIndicator("历史订单").setContent(R.id.listView2)
        );

        String str = username;
        mHandler.obtainMessage(MSG_HELLO, str).sendToTarget();//发送消息到CustomThread实例

    };


    class CustomThread extends Thread {
        Map<String,Object> map = new HashMap<String, Object>();
        ArrayList<Map<String,Object>> listitem1 = new ArrayList<Map<String,Object>>();
        ArrayList<Map<String,Object>> listitem2= new ArrayList<Map<String,Object>>();

        @Override

        public void run() {

            //建立消息循环的步骤

            Looper.prepare();//1、初始化Looper

            mHandler = new Handler(){//2、绑定handler到CustomThread实例的Looper对象

                public void handleMessage (final Message msg) {//3、定义处理消息的方法
                    String message;
                    switch(msg.what) {

                        case MSG_HELLO:

                            Log.d("Test", "CustomThread receive msg:" + (String) msg.obj);
                            message = (String) msg.obj;
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + msg.what);
                    }


                    try {

                        String json = "{\n" +
                                "    \"username\":\""+message+"\",\n" +
                                "    \"complete\":0\n" +
                                "}";
                        FormBody.Builder params = new FormBody.Builder();
                        params.add("username",message);
                        //创建http客户端
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url("http://10.26.7.88:8080/order/selectOrder")
                                .post(RequestBody.create(MediaType.parse("application/json"),json))
                                .build();
                        Response response = client.newCall(request).execute();
                        String responseData = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseData);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            map = new HashMap<String, Object>();
                            map.put("name",jsonObject.getString("receiver"));
                            map.put("number",""+jsonObject.getInt("orderid"));
                            map.put("phone",jsonObject.getString("phone"));
                            map.put("address",jsonObject.getString("address"));
                            listitem1.add(map);
                        }

                        json = "{\n" +
                                "    \"username\":\""+message+"\",\n" +
                                "    \"complete\":1\n" +
                                "}";
                        request = new Request.Builder()
                                .url("http://10.26.7.88:8080/order/selectOrder")
                                //.url("http://10.26.7.88:8080/user/getUser")
                                //.post(params.build())
                                .post(RequestBody.create(MediaType.parse("application/json"),json))
                                .build();
                        response = client.newCall(request).execute();
                        responseData = response.body().string();
                        jsonArray = new JSONArray(responseData);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            map = new HashMap<String, Object>();
                            map.put("name",jsonObject.getString("receiver"));
                            map.put("number",""+jsonObject.getInt("orderid"));
                            map.put("phone",jsonObject.getString("phone"));
                            map.put("address",jsonObject.getString("address"));
                            listitem2.add(map);
                        }


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ListView list = (ListView) findViewById(R.id.listView1);
                                SimpleAdapter listItemAdapter = new SimpleAdapter(
                                        ExpressmanInterfaceActivity.this,
                                        listitem1,
                                        R.layout.listitem,
                                        new String[]{"name", "phone", "address","status","number"},
                                        new int[]{R.id.name,R.id.phone,R.id.address}
                                );

                                list.setAdapter(listItemAdapter);
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(AdapterView<?>arg0, View arg1, int arg2, long arg3){
                                        Map<String,Object> clkmap = (Map<String, Object>) arg0.getItemAtPosition(arg2);
                                        Intent intent = new Intent(ExpressmanInterfaceActivity.this, ExpressDetailActivity.class);
                                        String message = "{\"orderid\":"+clkmap.get("number").toString()+","+
                                                "\"receiver\":\""+clkmap.get("name").toString()+"\","+
                                                "\"phone\":\""+clkmap.get("phone").toString()+"\","+
                                                "\"address\":\""+clkmap.get("address").toString()+"\","+
                                                "\"complete\":0"+
                                                "}";
                                        intent.putExtra(EXTRA_MESSAGE, message);
                                        startActivity(intent);
                                    }
                                });


                                list = (ListView) findViewById(R.id.listView2);
                                listItemAdapter = new SimpleAdapter(
                                        ExpressmanInterfaceActivity.this,
                                        listitem2,
                                        R.layout.listitem,
                                        new String[]{"name", "phone", "address","status","number"},
                                        new int[]{R.id.name,R.id.phone,R.id.address}
                                );
                                list.setAdapter(listItemAdapter);
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                                    @Override
                                    public void onItemClick(AdapterView<?>arg0, View arg1, int arg2, long arg3){
                                        Map<String,Object> clkmap = (Map<String, Object>) arg0.getItemAtPosition(arg2);
                                        Intent intent = new Intent(ExpressmanInterfaceActivity.this, ExpressDetailActivity.class);
                                        String message = "{\"orderid\":"+clkmap.get("number").toString()+","+
                                                "\"receiver\":\""+clkmap.get("name").toString()+"\","+
                                                "\"phone\":\""+clkmap.get("phone").toString()+"\","+
                                                "\"address\":\""+clkmap.get("address").toString()+"\","+
                                                "\"complete\":1"+
                                                "}";
                                        intent.putExtra(EXTRA_MESSAGE, message);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });


                    }catch (Exception e){
                        e.printStackTrace();
                        ToastDisplay("error");
                    }
                }

            };

            Looper.loop();//4、启动消息循环

        }

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