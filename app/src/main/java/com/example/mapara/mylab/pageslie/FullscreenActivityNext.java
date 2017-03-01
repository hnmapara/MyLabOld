package com.example.mapara.mylab.pageslie;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.mapara.mylab.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivityNext extends Activity {
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
        setContentView(R.layout.activity_fullscreen_next);
        System.out.println("----Secondactivity activity---onCreates---");
    }

}
