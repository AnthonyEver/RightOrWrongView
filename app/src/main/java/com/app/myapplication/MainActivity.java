package com.app.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements RightOrWrongView.CircleListener{

    RightOrWrongView cv;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cv = (RightOrWrongView) findViewById(R.id.rwview);
        tv = (TextView) findViewById(R.id.tv);
        cv.setRightOrWrong(RightOrWrongView.RIGHT);
        cv.setCircleListener(new RightOrWrongView.CircleListener() {
            @Override
            public void AnimFinish() {
                tv.setText("动画执行完毕");
            }
        });

    }

    public void onClick(View view) {
        cv.setRightOrWrong(false);
    }

    @Override
    public void AnimFinish() {

    }
}
