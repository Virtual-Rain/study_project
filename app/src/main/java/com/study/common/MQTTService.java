package com.study.common;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


import com.study.commonlibrary.uitls.MLog;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Random;

public class MQTTService extends Service {
    public static final String TAG = MQTTService.class.getSimpleName();

    private static MqttAndroidClient mClient;
    private MqttConnectOptions mConOpt;

    private String mHost = "ws://172.18.3.11:8083/mqtt";
    private String mUserName = "admin";
    private String mPassWord = "password";
    private static String mTopic = "testtopic";
    private String mClientId = "test";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        try {
            mClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void publish(String msg) {
        String topic = mTopic;
        Integer qos = 1;
        Boolean retained = false;

        try {
            mClient.publish(topic, msg.getBytes(), qos, retained.booleanValue());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        /*????????????????????????+??????+????????????*/
        String uri = mHost;
        String uuid = mClientId + new Random(10000000) + 1;
        mClient = new MqttAndroidClient(this, uri, uuid);
        /*??????MQTT????????????????????????*/
        mClient.setCallback(mMqttCallback);
        mConOpt = new MqttConnectOptions();
        /*????????????*/
        mConOpt.setCleanSession(true);
        /*?????????????????????????????????*/
        mConOpt.setConnectionTimeout(10);
        /*???????????????????????????*/
        mConOpt.setKeepAliveInterval(20);
        /*?????????*/
        mConOpt.setUserName(mUserName);
        /*??????*/
        mConOpt.setPassword(mPassWord.toCharArray());

        boolean doConnect = true;
        String message = "{\"terminal_uid\":\"" + mClientId + "\"}";
        String topic = mTopic;
        Integer qos = 0;
        Boolean retained = false;
        if ((!message.equals("")) || (!topic.equals(""))) {
            try {
                mConOpt.setWill(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
            } catch (Exception e) {
                doConnect = false;
                mIMqttActionListener.onFailure(null, e);
            }

        }
        MLog.e("doConnect=" + doConnect);
        if (doConnect) {
            doClientConnection();
        }
    }

    /*??????MQTT?????????*/
    private void doClientConnection() {
        if (!mClient.isConnected() && isConnectIsNormal()) {
            try {
                mClient.connect(mConOpt, null, mIMqttActionListener);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    /*MQTT??????????????????*/
    private IMqttActionListener mIMqttActionListener = new IMqttActionListener() {
        @Override
        public void onSuccess(IMqttToken asyncActionToken) {
            /*??????mTopic??????*/
            try {
                mClient.subscribe(mTopic, 1);
                MLog.e("???????????????" + mTopic);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
            /*????????????*/
            MLog.e("???????????????" + mTopic);
        }
    };

    /*??????MQTT????????????????????????*/
    private MqttCallback mMqttCallback = new MqttCallback() {
        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }

        @Override
        public void connectionLost(Throwable cause) {
            /*??????????????????*/
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            MLog.e("topic=" + topic + " message=" + message.toString());
        }


    };

    /*????????????????????????*/
    private boolean isConnectIsNormal() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            String name = networkInfo.getTypeName();
            return true;
        } else {
            return false;
        }
    }
}
