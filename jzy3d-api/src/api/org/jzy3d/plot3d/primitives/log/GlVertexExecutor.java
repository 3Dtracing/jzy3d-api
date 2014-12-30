package org.jzy3d.plot3d.primitives.log;

import javax.media.opengl.GL;

import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.log.transformers.AxeTransform;
import org.jzy3d.plot3d.primitives.log.transformers.LogTransformer;

public abstract class GlVertexExecutor {
	public static void Vertex(GL gl, Coord3d c3d,LogTransformer transformers){
		gl.getGL2().glVertex3d(transformers.getX().compute(c3d.x), transformers.getY().compute(c3d.y), transformers.getZ().compute(c3d.z));
	}
}
