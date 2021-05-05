package com.example.access;

import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;
import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.browseview);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        TableLayout table1 = findViewById(R.id.table1);
        TableRow row1 = findViewById(R.id.row1);
        CardView cardView = findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.login,null,false);
//                final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).setView(view).create();
//                dialog.show();
//                WindowManager m = getWindowManager();
//                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
//                android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
//                p.height = (int) (d.getHeight() * 0.54); //高度设置为屏幕的0.3
//                p.width = (int) (d.getWidth() * 0.4); //宽度设置为屏幕的0.5
//                dialog.getWindow().setAttributes(p); //设置生效
//
//                Button login = view.findViewById(R.id.btn_login);
//                login.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                            Intent intent=new Intent(MainActivity.this,evaluate.class);
//                            startActivity(intent);
//                    }
//                });
//
//            }
            Intent intent=new Intent(MainActivity.this,evaluate.class);
            startActivity(intent);}

        });




//        TextView timeET=findViewById( R.id.timeET );
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//        Date date = new Date(System.currentTimeMillis());
//        timeET.setText(simpleDateFormat.format(date));
    }
}