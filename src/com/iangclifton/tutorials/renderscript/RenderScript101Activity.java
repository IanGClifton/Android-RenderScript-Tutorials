package com.iangclifton.tutorials.renderscript;

import android.app.Activity;
import android.os.Bundle;

public class RenderScript101Activity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RenderScript101View(this));
    }
}