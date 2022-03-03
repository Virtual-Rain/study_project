package com.study.common;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.study.common.widget.LargeImageView;
import com.study.common.widget.PaintTestView;
import com.study.common.widget.SampleBubbleView;
import com.study.commonlibrary.uitls.StringUtils;
import com.study.commonlibrary.widget.bubble.BubblePopupWindow;

import java.io.IOException;
import java.io.InputStream;

public class TestPaintActivity extends AppCompatActivity {

    private Button btnAsync;
    private Canvas mCanvas;
    private PaintTestView mPaint;
    private SampleBubbleView mBubble;
    private LargeImageView mLargeImageView;

    private Button mBubbleBtn;

    private boolean isBubbleStart = false;

    private MyAsyncTask myAsyncTask;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        btnAsync = findViewById(R.id.btn_async_task);
        mPaint = findViewById(R.id.paint);
        mBubble = findViewById(R.id.bubble);
        mBubbleBtn = findViewById(R.id.btn_sample_bubble);
        mLargeImageView = findViewById(R.id.large_image_view);

        inflater = LayoutInflater.from(this);
    }

    public void toMainActivity(View view) {
        Intent intent = new Intent(TestPaintActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void paintType(View view) {
        String content = ((Button) view).getText().toString();
        if (StringUtils.isNumberMoney(content)) {
            int type = Integer.valueOf(content);
            mPaint.paint(type);
        }
        ;
    }

    public void bubbleChange(View view) {
        isBubbleStart = !isBubbleStart;
        mBubbleBtn.setText(isBubbleStart ? "Stop" : "Start");
        if (isBubbleStart) {
            mBubble.startBubbleSync();
        } else {
            mBubble.stopAddBubble();
        }
    }

    public void asyncTask(View view) {
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("zx1990");
    }

    public void largeImage(View view) {
        try {
            InputStream inputStream = getAssets().open("world.jpg");
            mLargeImageView.setInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bubbleTop(View view) {
        View bubbleView = inflater.inflate(R.layout.bubble_popup_window, null);
        new BubblePopupWindow(TestPaintActivity.this)
                .setBubbleView(bubbleView)
                .show(view, Gravity.TOP, 0.5f);
    }

    /*AsyncTask*/
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private final static String TAG = "MyAsyncTask";

        public MyAsyncTask() {
            Log.i(TAG, "ZX constructor");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i(TAG, "params: " + strings[0]);
            publishProgress(13);
            return "world";
        }

        @Override
        protected void onPostExecute(String s) {
            btnAsync.setText(s);
            Log.i(TAG, "on post execute string : " + s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "on pre execute");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.i(TAG, "on progress update :" + values[0]);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myAsyncTask) {
            myAsyncTask.cancel(true);
        }
    }
}
