package com.example.express;

import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import android.view.View;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ExpressDetailActivity extends Activity {
    //定位都要通过LocationManager这个类实现
    private LocationManager locationManager;
    private String provider;
    private final int MSG_HELLO = 0;
    private Handler mHandler;

    @SuppressWarnings("static-access")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_detail);

        Intent intent = getIntent();
        final String message = intent.getStringExtra(ExpressmanInterfaceActivity.EXTRA_MESSAGE);

        final TextView textorderid = findViewById(R.id.orderid);
        TextView textreceiver = findViewById(R.id.receiver);
        TextView textphone = findViewById(R.id.phone);
        TextView textaddress = findViewById(R.id.address);
        final TextView textcomplete = findViewById(R.id.status);
        TextView textgpsG = findViewById(R.id.gpsG);
        TextView textgpsA = findViewById(R.id.gpsA);



        try {
            String status="";
            JSONObject jsonObject = new JSONObject(message);
            textorderid.setText("订单号："+jsonObject.getInt("orderid"));
            textreceiver.setText("收货人："+jsonObject.getString("receiver"));
            textphone.setText("手机号："+jsonObject.getString("phone"));
            textaddress.setText("地址："+jsonObject.getString("address"));

            if(jsonObject.getInt("complete")==1){
                status = "已完成";
            }else{
                status = "未完成";
            }
            textcomplete.setText("订单状态："+status);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"failed",Toast.LENGTH_LONG).show();
        }




        Button button = findViewById(R.id.completeorder);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                try {
                    if(new JSONObject(message).getInt("complete")==1){
                        Toast.makeText(ExpressDetailActivity.this,"订单已经完成，无需重复操作",Toast.LENGTH_LONG).show();
                    }else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                TextView longitudeT = (TextView) findViewById(R.id.gpsG);
                                TextView latitudeT = (TextView) findViewById(R.id.gpsA);
                                TextView orderT = (TextView) findViewById(R.id.orderid);
                                String longitudeS = longitudeT.getText().toString();
                                String latitudeS = latitudeT.getText().toString();
                                String orderS = orderT.getText().toString();
                                longitudeS = longitudeS.replace("经度为：","");
                                latitudeS = latitudeS.replace("纬度为：","");
                                orderS = orderS.replace("订单号：","");
                                try {

                                    String json = "{\n" +
                                            "    \"longitude\":"+longitudeS+",\n" +
                                            "    \"latitude\":"+latitudeS+",\n" +
                                            "    \"orderid\":"+orderS+"\n" +
                                            "}";
                                    ToastDisplay(json);

                                    //创建http客户端
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder()
                                            .url("http://10.26.7.88:8080/order/updateGps")
                                            .post(RequestBody.create(MediaType.parse("application/json"),json))
                                            .build();
                                    Response response = client.newCall(request).execute();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ExpressDetailActivity.this,"订单完成，GPS信息已上传",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }catch(Exception e){
                                    e.printStackTrace();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ExpressDetailActivity.this,"failed",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        //获取定位服务
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取当前可用的位置控制器
        List<String> list = locationManager.getProviders(true);

        if (list.contains(LocationManager.GPS_PROVIDER)) {
            //是否为GPS位置控制器
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            provider = LocationManager.NETWORK_PROVIDER;

        } else {
            Toast.makeText(this, "请检查网络或GPS是否打开",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            //获取当前位置，这里只用到了经纬度
            textgpsG.setText("经度为：" + location.getLongitude());
            textgpsA.setText("纬度为：" + location.getLatitude());
        }

        //绑定定位事件，监听位置是否改变
        //第一个参数为控制器类型第二个参数为监听位置变化的时间间隔（单位：毫秒）
        //第三个参数为位置变化的间隔（单位：米）第四个参数为位置监听器
        locationManager.requestLocationUpdates(provider, 2000, 2, locationListener);


    }


    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String arg0) {

        }

        @Override
        public void onProviderDisabled(String arg0) {

        }

        @Override
        public void onLocationChanged(Location arg0) {

        }
    };
    //关闭时解除监听器
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
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
