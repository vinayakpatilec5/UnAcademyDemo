package com.vinayakpatilec5.admin.unacademytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private CustomProgressBarView customProgressBarView;
    private EditText ed_progressvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customProgressBarView = findViewById(R.id.customProgressBarView);
        ed_progressvalue = (EditText)findViewById(R.id.ed_progressvalue);
    }

    public void animateProgressBar(View view){
        if(!ed_progressvalue.getText().toString().isEmpty()) {
            int animValue = Integer.parseInt(ed_progressvalue.getText().toString());
            if (animValue > 100) {
                ed_progressvalue.setText("100");
                ed_progressvalue.setSelection(3);
                animValue = 100;
            }
            customProgressBarView.animatePath(animValue);
        }
    }
}
