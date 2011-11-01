package com.iangclifton.tutorials.renderscript;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.renderscript.RSSurfaceView;
import android.renderscript.RenderScriptGL;
import android.view.MotionEvent;

public class RenderScript101View extends RSSurfaceView {
	private Context mContext;
	private RenderScript101RS mRenderScript;
	private RenderScriptGL mRS;

	public RenderScript101View(Context context) {
		super(context);
		mContext = context;
		ensureRenderScript();
	}

	private void ensureRenderScript() {
		if (mRS == null) {
			final RenderScriptGL.SurfaceConfig sc = new RenderScriptGL.SurfaceConfig();
			mRS = createRenderScriptGL(sc);
		}
		if (mRenderScript == null) {
			mRenderScript = new RenderScript101RS(mRS, mContext.getResources(), R.raw.renderscript101);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		if (mRenderScript == null) {
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mRenderScript.setBackgroundBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon));
		}

		return true;
	}
}
