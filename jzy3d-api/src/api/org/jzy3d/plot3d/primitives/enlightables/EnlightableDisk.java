package org.jzy3d.plot3d.primitives.enlightables;

import javax.media.opengl.GL;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import org.jzy3d.colors.Color;
import org.jzy3d.colors.ISingleColorable;
import org.jzy3d.events.DrawableChangedEvent;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.rendering.compat.GLES2CompatUtils;
import org.jzy3d.plot3d.rendering.view.Camera;
import org.jzy3d.plot3d.transform.Transform;

public class EnlightableDisk extends AbstractEnlightable implements
		ISingleColorable {

	/** Initialize a Cylinder at the origin. */
	public EnlightableDisk() {
		this(Coord3d.ORIGIN, 0f, 10f, 15, 15, Color.BLACK, true);
	}

	/** Initialize a cylinder with the given parameters. */
	public EnlightableDisk(Coord3d position, double radiusInner,
			double radiusOuter, int slices, int loops, Color color,
			boolean faceup) {
		super();
		bbox = new BoundingBox3d();

		if (faceup) {
			norm = new Coord3d(0, 0, 1);
		} else
			norm = new Coord3d(0, 0, -1);

		setPosition(position);
		setVolume(radiusInner, radiusOuter);
		setSlicing(slices, loops);
		setColor(color);
	}

	/********************************************************/

	@Override
    public void draw(GL gl, GLU glu, Camera cam) {
		doTransform(gl, glu, cam);

		if (gl.isGL2()) {
			gl.getGL2().glTranslated(x, y, z);
		} else {
			GLES2CompatUtils.glTranslatef((float)x, (float)y, (float)z);
		}

		applyMaterial(gl);
		gl.glLineWidth(wfwidth);

		// Draw
		GLUquadric qobj = glu.gluNewQuadric();

		if (gl.isGL2()) {
			if (facestatus) {
				if (wfstatus) {
					gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
					gl.glPolygonOffset(1.0f, 1.0f);
				}

				gl.getGL2().glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_FILL);
				gl.getGL2().glNormal3d(norm.x, norm.y, norm.z);
				gl.getGL2().glColor4f(color.r, color.g, color.b, color.a);
				glu.gluDisk(qobj, radiusInner, radiusOuter, slices, loops);

				if (wfstatus)
					gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);

			}
			if (wfstatus) {
				gl.getGL2().glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE);
				gl.getGL2().glNormal3d(norm.x, norm.y, norm.z);
				gl.getGL2().glColor4f(wfcolor.r, wfcolor.g, wfcolor.b, wfcolor.a);
				glu.gluDisk(qobj, radiusInner, radiusOuter, slices, loops);
			}
		} else {
			if (facestatus) {
				if (wfstatus) {
					gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
					gl.glPolygonOffset(1.0f, 1.0f);
				}

				GLES2CompatUtils.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_FILL);
				GLES2CompatUtils.glNormal3f((float)norm.x, (float)norm.y, (float)norm.z);
				GLES2CompatUtils.glColor4f(color.r, color.g, color.b, color.a);
				glu.gluDisk(qobj, radiusInner, radiusOuter, slices, loops);

				if (wfstatus)
					gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);

			}
			if (wfstatus) {
				GLES2CompatUtils.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE);
				GLES2CompatUtils.glNormal3f((float)norm.x, (float)norm.y, (float)norm.z);
				GLES2CompatUtils.glColor4f(wfcolor.r, wfcolor.g, wfcolor.b, wfcolor.a);
				glu.gluDisk(qobj, radiusInner, radiusOuter, slices, loops);
			}
		}
	}

	@Override
    public void applyGeometryTransform(Transform transform) {
		Coord3d change = transform.compute(new Coord3d(x, y, z));
		x = change.x;
		y = change.y;
		z = change.z;
		updateBounds();
	}

	@Override
    public void updateBounds() {
		bbox.reset();
		bbox.add(x + radiusOuter, y + radiusOuter, z);
		bbox.add(x - radiusOuter, y - radiusOuter, z);
	}

	/* */

	public void setData(Coord3d position, double radiusInner, double radiusOuter,
			int slices, int loops) {
		setPosition(position);
		setVolume(radiusInner, radiusOuter);
		setSlicing(slices, loops);
	}

	public void setPosition(Coord3d position) {
		this.x = position.x;
		this.y = position.y;
		this.z = position.z;

		updateBounds();
	}

	public void setVolume(double radiusInner, double radiusOuter) {
		if (radiusOuter < radiusInner)
			throw new IllegalArgumentException(
					"inner radius must be smaller than outer radius");

		this.radiusInner = radiusInner;
		this.radiusOuter = radiusOuter;

		updateBounds();
	}

	public void setSlicing(int verticalWires, int horizontalWires) {
		this.slices = verticalWires;
		this.loops = horizontalWires;
	}

	/********************************************************/

	@Override
    public void setColor(Color color) {
		this.color = color;

		fireDrawableChanged(new DrawableChangedEvent(this,
				DrawableChangedEvent.FIELD_COLOR));
	}

	@Override
    public Color getColor() {
		return color;
	}

	/********************************************************/

	private double x;
	private double y;
	private double z;

	private int slices;
	private int loops;
	private double radiusInner;
	private double radiusOuter;

	private Color color;

	protected Coord3d norm;
}
