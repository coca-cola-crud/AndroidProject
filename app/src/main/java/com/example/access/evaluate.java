package com.example.access;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class evaluate extends AppCompatActivity {
    private String Name = "张三";
    private String Id = "17123011";
    private boolean ifAssess = false;

    //显示时间
    private static final int msgKey1 = 1;
    private TextView mTime;
    public class TimeThread extends Thread {
        @Override
        public void run () {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while(true);
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    mTime.setText(getTime());
                    break;
                default:
                    break;
            }
        }
    };
    //获得当前年月日时分秒星期
    public String getTime(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        String mHour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));//时
        String mMinute = String.valueOf(c.get(Calendar.MINUTE));//分
        String mSecond = String.valueOf(c.get(Calendar.SECOND));//秒
        if("1".equals(mWay)){
            mWay ="Sunday";
        }else if("2".equals(mWay)){
            mWay ="Monday";
        }else if("3".equals(mWay)){
            mWay ="Tuesday";
        }else if("4".equals(mWay)){
            mWay ="Wednesday";
        }else if("5".equals(mWay)){
            mWay ="Thursday";
        }else if("6".equals(mWay)){
            mWay ="Friday";
        }else if("7".equals(mWay)){
            mWay ="Saturday";
        }
        return mWay+"  "+mYear + "年" + mMonth + "月" + mDay+"日  "+mHour+":"+mMinute;
    }

    //提示框
    public void showToast(View v){
        SweetAlertDialog pDialog = new SweetAlertDialog(v.getContext(), SweetAlertDialog.SUCCESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("完成");
        pDialog.setContentText("感谢您的评价");
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener(){

            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent=new Intent(evaluate.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //语音提示
    private String voicer = "xiaoyan";
    private String TAG = "VoiceActivity";

    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private SpeechSynthesizer speechSynthesizer;
    private List<String> playList;
    private void init() {
        playList = new ArrayList();
        speechSynthesizer = SpeechSynthesizer.createSynthesizer(this, new InitListener() {
            @Override
            public void onInit(int i) {
                Log.d(TAG, "InitListener init() code = " + i);
                if (i != ErrorCode.SUCCESS) {
                    Toast.makeText(evaluate.this,"初始化失败："+i,Toast.LENGTH_LONG).show();
                } else {
                    // 初始化成功，之后可以调用startSpeaking方法
                    // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                    // 正确的做法是将onCreate中的startSpeaking调用移至这里
                    setParams();
                }

            }
        });
    }


    private void say(String text) {
        playList.add(text);
        if(speechSynthesizer.isSpeaking()){
            Log.e(TAG,"isSpeaking");
            return;
        }
        startSpeaking(text);
    }
    private void startSpeaking(String text) {
        speechSynthesizer.startSpeaking(text, new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {
                Log.e(TAG,"onSpeakBegin");
            }
            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }
            @Override
            public void onSpeakPaused() {

            }
            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }
            @Override
            public void onCompleted(SpeechError speechError) {

                if(playList != null && playList.size()>0){
                    Iterator<String> iterator = playList.iterator();
                    if (iterator.hasNext()) {
                        String str = iterator.next();
                        iterator.remove();
                    }

                    if(playList.size()>0){
                        startSpeaking(playList.get(0));
                    }
                }
            }
            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {
            }
        });

    }
    private void setParams() {
        speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        speechSynthesizer.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
        // 设置在线合成发音人
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, voicer);
        //设置合成语速
        speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        speechSynthesizer.setParameter(SpeechConstant.VOLUME, "50");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SpeechUtility.createUtility( evaluate.this,SpeechConstant.APPID +"=035cef09");
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.setFinishOnTouchOutside(false);
        //语音播报
        init();


        //显示时间
        mTime = (TextView) findViewById(R.id.timeET);
        new TimeThread().start();

        //显示员工信息
        TextView userName = findViewById(R.id.name);
        userName.setText(Name);
        TextView userID = findViewById(R.id.userId);
        userID.setText(Id);

        //评价事件
        ImageView satisfy = findViewById(R.id.happy);
        ImageView littleSatisfy = findViewById(R.id.littlehappy);
        ImageView ordinary = findViewById(R.id.littleunhappy);
        ImageView angry = findViewById(R.id.unhappy);
        satisfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(v);
                say("感谢您的评价");

            }
        });

        littleSatisfy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(v);
                say("感谢您的评价");
            }
        });
        ordinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(v);
                say("感谢您的评价");
            }
        });
        angry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.high_opinion_dialog_layout,null,false);
                final AlertDialog dialog = new AlertDialog.Builder(v.getContext()).setView(view).create();
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);

                Button cancel = view.findViewById(R.id.btn_cancel);
                Button agree = view.findViewById(R.id.btn_agree);
                TextView comment = view.findViewById(R.id.comment);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();

                    }
                });
                agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(comment.getText().toString().equals(""));
                        if(comment.getText().toString().equals("")){
                            //dialog.hide();
                            new SweetAlertDialog(v.getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("失败")
                                    .setContentText("不能为空")
                                    .show();
                        }else{
                            dialog.hide();
                            showToast(v);
                            say("感谢您的评价");
                        }
                    }
                });
            }
        });
    }
}
