package com.iangclifton.tutorials.renderscript;

import android.app.Activity;
import android.os.Bundle;

/**
 * Simple Activity that sets up our custom RenderScript101View.
 * 
 * @author Ian G. Clifton
 */
public class RenderScript101Activity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new RenderScript101View(this));
	}
}