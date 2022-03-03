package com.example.module1;

import android.os.Bundle;

import com.example.annotationlib.BYView;
import com.example.annotationlib.DIActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;


@DIActivity
public class SecondActivity extends AppCompatActivity {

    @BYView(R.id.secondText)
    TextView secondText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        secondText.setText("我是sencodText,我是被APT找到的");
    }

}
