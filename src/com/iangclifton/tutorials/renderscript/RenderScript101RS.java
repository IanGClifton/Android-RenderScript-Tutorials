package com.iangclifton.tutorials.renderscript;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Float4;
import android.renderscript.Matrix4f;
import android.renderscript.ProgramFragment;
import android.renderscript.ProgramFragmentFixedFunction;
import android.renderscript.ProgramStore;
import android.renderscript.ProgramVertex;
import android.renderscript.ProgramVertexFixedFunction;
import android.renderscript.RenderScriptGL;
import android.renderscript.Sampler;

public class RenderScript101RS {

	private Sampler mLinearClamp;
	private ProgramStore mProgramStoreBlendNone;
	private ProgramVertex mProgramVertex;
	private RenderScriptGL mRS;
	private ScriptC_RenderScript101 mScript;
	private ProgramFragment mSingleTextureFragmentProgram;

	public RenderScript101RS(RenderScriptGL rs, Resources res, int resId) {
		mRS = rs;
		mScript = new ScriptC_RenderScript101(rs, res, resId);

		initProgramStore();
		initSampler();
		initProgramFragment();
		initProgramVertex();
		mRS.bindRootScript(mScript);

	}

	public void setBackgroundBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return;
		}
		final Allocation bitmapAllocation = Allocation.createFromBitmap(mRS, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_GRAPHICS_TEXTURE);
		mScript.set_gBgImage(bitmapAllocation);
	}

	public void setBackgroundColor(Float4 color) {
		mScript.set_gBgColor(color);
	}

	private void initProgramFragment() {
		final ProgramFragmentFixedFunction.Builder pfBuilder = new ProgramFragmentFixedFunction.Builder(mRS);
		pfBuilder.setTexture(ProgramFragmentFixedFunction.Builder.EnvMode.REPLACE, ProgramFragmentFixedFunction.Builder.Format.RGBA, 0);
		mSingleTextureFragmentProgram = pfBuilder.create();
		mScript.set_gSingleTextureFragmentProgram(mSingleTextureFragmentProgram);
	}

	private void initProgramStore() {
		mProgramStoreBlendNone = ProgramStore.BLEND_NONE_DEPTH_NONE(mRS);
		mScript.set_gProgramStoreBlendNone(mProgramStoreBlendNone);
	}

	private void initProgramVertex() {
		ProgramVertexFixedFunction.Builder pvb = new ProgramVertexFixedFunction.Builder(mRS);
		mProgramVertex = pvb.create();
		ProgramVertexFixedFunction.Constants pva = new ProgramVertexFixedFunction.Constants(mRS);
		((ProgramVertexFixedFunction) mProgramVertex).bindConstants(pva);
		Matrix4f proj = new Matrix4f();
		proj.loadProjectionNormalized(1, 1);
		pva.setProjection(proj);
		mScript.set_gProgramVertex(mProgramVertex);
	}

	private void initSampler() {
		mLinearClamp = Sampler.CLAMP_LINEAR(mRS);
		mScript.set_gLinearClamp(mLinearClamp);
	}
}
