package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import android.os.Build;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {
    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,badd,bsub,bmul,bdiv,bcal;
    private TextView t1,t2;
    private String s1="",operation="",display_text="";
    private int num1=0,num2=0,result=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button) findViewById(R.id.b1);
        b2=(Button) findViewById(R.id.b2);
        b3=(Button) findViewById(R.id.b3);
        b4=(Button) findViewById(R.id.b4);
        b5=(Button) findViewById(R.id.b5);
        b6=(Button) findViewById(R.id.b6);
        b7=(Button) findViewById(R.id.b7);
        b8=(Button) findViewById(R.id.b8);
        b9=(Button) findViewById(R.id.b9);
        b0=(Button) findViewById(R.id.b0);
        badd=(Button) findViewById(R.id.badd);
        bsub=(Button) findViewById(R.id.bsub);
        bmul=(Button) findViewById(R.id.bmul);
        bdiv=(Button) findViewById(R.id.bdiv);
        bcal=(Button) findViewById(R.id.bcal);
        t1=(TextView) findViewById(R.id.disp1);
        t2=(TextView) findViewById(R.id.disp2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES. TIRAMISU) {

            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1+="1";
                display_text+="1";
                t1.setText(display_text);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1+="2";
                display_text+="2";
                t1.setText(display_text);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1+="3";
                display_text+="3";
                t1.setText(display_text);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1+="4";
                display_text+="4";
                t1.setText(display_text);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1+="5";
                display_text+="5";
                t1.setText(display_text);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1+="6";
                display_text+="6";
                t1.setText(display_text);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1+="7";
                display_text+="7";
                t1.setText(display_text);
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1+="8";
                display_text+="8";
                t1.setText(display_text);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1+="9";
                display_text+="9";
                t1.setText(display_text);
            }
        });

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1+="0";
                display_text+="0";
                t1.setText(display_text);
            }
        });

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation="add";
                num1=Integer.parseInt(s1);
                s1="";
                display_text+="+";
                t1.setText(display_text);
            }
        });

        bsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation="sub";
                num1=Integer.parseInt(s1);
                s1="";
                display_text+="-";
                t1.setText(display_text);
            }
        });

        bmul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation="mul";
                num1=Integer.parseInt(s1);
                s1="";
                display_text+="*";
                t1.setText(display_text);
            }
        });

        bdiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operation="div";
                num1=Integer.parseInt(s1);
                s1="";
                display_text+="/";
                t1.setText(display_text);
            }
        });

        bcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num2=Integer.parseInt(s1);
                s1="";
                switch(operation)
                {
                    case "add":
                        result=num1+num2;
                        break;
                    case "sub":
                        result=num1-num2;
                        break;
                    case "mul":
                        result=num1*num2;
                        break;
                    case "div":
                        result=num1/num2;
                        break;
                    default:
                        result=0;
                }
                t2.setText("="+result);
                operation="";
                display_text="";
                notf();
            }
        });
    }

    private void notf()
    {
        String channel_id="NOTIFICATIONS";
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),channel_id);
        builder.setSmallIcon(R.drawable.calc_notf);
        builder.setContentTitle("Calculator Results");
        builder.setContentText("Result: "+result);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent in = new Intent(getApplicationContext(), notification_activity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.putExtra("res",result);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,in,PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pi);


        NotificationManager nm=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Example channel name and description
            CharSequence channelName = "Calculator Results";
            String channelDescription = "Results of operation done in the calculator app";

            // Importance level: High
            int importance = NotificationManager.IMPORTANCE_HIGH;

            // Create a notification channel
            NotificationChannel channel = new NotificationChannel(channel_id, channelName, importance);
            channel.setDescription(channelDescription);

            // Set notification LED color (if needed)
            channel.setLightColor(Color.GREEN);

            // Enable vibration for notifications
            channel.enableVibration(true);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        nm.notify(0,builder.build());

    }
}