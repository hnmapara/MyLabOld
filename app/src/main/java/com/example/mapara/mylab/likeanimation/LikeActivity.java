package com.example.mapara.mylab.likeanimation;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.mapara.mylab.R;

/**
 * Created by mapara on 5/5/16.
 */
public class LikeActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_like);
            final LikeButtonView like1 = (LikeButtonView) findViewById(R.id.like1);
            final LikeButtonView like2 = (LikeButtonView) findViewById(R.id.like2);
            final LikeButtonView like3 = (LikeButtonView) findViewById(R.id.like3);
            final LikeButtonView like4 = (LikeButtonView) findViewById(R.id.like4);
            final LikeButtonView like5 = (LikeButtonView) findViewById(R.id.like5);

            like1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    like1.onClick1(v);

                    like2.ivStarEnable(false);
                    like3.ivStarEnable(false);
                    like4.ivStarEnable(false);
                    like5.ivStarEnable(false);
                }
            });

            like2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    like1.onClick1(v);
                    like2.onClick1(v);

                    like3.ivStarEnable(false);
                    like4.ivStarEnable(false);
                    like5.ivStarEnable(false);
                }
            });

            like3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    like1.onClick1(v);
                    like2.onClick1(v);
                    like3.onClick1(v);

                    like4.ivStarEnable(false);
                    like5.ivStarEnable(false);
                }
            });

            like4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    like1.onClick1(v);
                    like2.onClick1(v);
                    like3.onClick1(v);
                    like4.onClick1(v);

                    like5.ivStarEnable(false);
                }
            });

            like5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    like1.onClick1(v);
                    like2.onClick1(v);
                    like3.onClick1(v);
                    like4.onClick1(v);
                    like5.onClick1(v);
                }
            });


//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    like1.performClick();
////                    like2.performClick();
//                }
//            }, 3000);
        }
}
