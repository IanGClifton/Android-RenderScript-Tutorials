#pragma version(1)
#pragma rs java_package_name(com.iangclifton.tutorials.renderscript);

#include "rs_graphics.rsh"

float4 gBgColor; // Background color as xyzw 4-part float
rs_allocation gBgImage; // Background image
rs_sampler gLinearClamp; // Sampler used by the program fragment
rs_program_fragment gSingleTextureFragmentProgram; // fragment shader
rs_program_store gProgramStoreBlendNone; // blend none, depth none program store
rs_program_vertex gProgramVertex; // Default vertex shader

static const float3 gBgVertices[4] = {
        { -1.0, -1.0, -1.0 },
        { 1.0, -1.0, -1.0 },
        { 1.0, 1.0, -1.0 },
        {-1.0, 1.0, -1.0 }
};

static void drawBackground() {
	if (gBgImage.p != 0) {
		rs_matrix4x4 projection, model;
		rsMatrixLoadOrtho(&projection, -1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 1.0f);
		rsgProgramVertexLoadProjectionMatrix(&projection);
		
		rsMatrixLoadIdentity(&model);
		rsgProgramVertexLoadModelMatrix(&model);
		
		rsgBindTexture(gSingleTextureFragmentProgram, 0, gBgImage);

	    rsgDrawQuad(
			gBgVertices[0].x, gBgVertices[0].y, gBgVertices[0].z,
			gBgVertices[1].x, gBgVertices[1].y, gBgVertices[0].z,
			gBgVertices[2].x, gBgVertices[2].y, gBgVertices[0].z,
			gBgVertices[3].x, gBgVertices[3].y, gBgVertices[0].z
		);
	} else {
		rsgClearColor(gBgColor.x, gBgColor.y, gBgColor.z, gBgColor.w);
	}
}

void init() {
	gBgColor = (float4) { 0.0f, 1.0f, 0.0f, 1.0f };
	rsDebug("Called init", rsUptimeMillis());
}

int root() {
	rsgBindProgramVertex(gProgramVertex);
	rsgBindProgramFragment(gSingleTextureFragmentProgram);
	rsgBindProgramStore(gProgramStoreBlendNone);
	
	drawBackground();
	return 16;
}