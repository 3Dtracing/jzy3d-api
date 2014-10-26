package org.jzy3d.chart2d;

import java.util.HashMap;
import java.util.Map;

import javax.media.opengl.GLCapabilities;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.chart.factories.IChartComponentFactory.Toolkit;
import org.jzy3d.chart2d.primitives.LineSerie2d;
import org.jzy3d.chart2d.primitives.ScatterPointSerie2d;
import org.jzy3d.chart2d.primitives.ScatterSerie2d;
import org.jzy3d.chart2d.primitives.Serie2d;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.primitives.axes.layout.renderers.ElapsedTimeTickRenderer;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.View;
import org.jzy3d.plot3d.rendering.view.ViewportMode;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;

// TODO:
// AXEBOX ticks too long
// AXEBOX Y label sur le côté
// X Labels centrés
// 
// Interface de LineSerie fournie par Chart2d package, using x, y float args

public class Chart2d extends AWTChart {
    public Chart2d() {
        this(Toolkit.newt);
    }

    public Chart2d(Toolkit toolkit) {
        this(new Chart2dComponentFactory(), Quality.Intermediate, toolkit.toString());

        layout2d();
    }

    public void layout2d() {
        IAxeLayout axe = getAxeLayout();
        axe.setZAxeLabelDisplayed(false);
        axe.setTickLineDisplayed(false);

        View view = getView();
        view.setViewPositionMode(ViewPositionMode.TOP);
        view.setSquared(true);
        view.getCamera().setViewportMode(ViewportMode.STRETCH_TO_FILL);
    }

    public void asTimeChart(float timeMax, float ymin, float ymax, String xlabel, String ylabel) {
        IAxeLayout axe = getAxeLayout();
        axe.setYAxeLabel(ylabel);
        axe.setXAxeLabel(xlabel);
        axe.setXTickRenderer(new ElapsedTimeTickRenderer());

        View view = getView();
        view.setBoundManual(new BoundingBox3d(0, timeMax, ymin, ymax, -1, 1));
    }

    public Serie2d getSerie(String name, Serie2d.Type type) {
        Serie2d serie = null;
        if (!series.keySet().contains(name)) {
            serie = newSerie(name, type, serie);
            addDrawable(serie.getDrawable());
        } else {
            serie = series.get(name);
        }
        return serie;
    }

    public Serie2d newSerie(String name, Serie2d.Type type, Serie2d serie) {
        if (Serie2d.Type.LINE.equals(type))
            serie = new LineSerie2d(name);
        else if (Serie2d.Type.SCATTER.equals(type))
            serie = new ScatterSerie2d(name);
        else if (Serie2d.Type.SCATTER_POINTS.equals(type))
            serie = new ScatterPointSerie2d(name);
        else
            throw new IllegalArgumentException("Unsupported serie type " + type);
        return serie;
    }

    /* */

    public Chart2d(IChartComponentFactory factory, Quality quality, String windowingToolkit, GLCapabilities capabilities) {
        super(factory, quality, windowingToolkit, capabilities);
    }

    public Chart2d(IChartComponentFactory factory, Quality quality, String windowingToolkit) {
        super(factory, quality, windowingToolkit);
        layout2d();
    }

    public Chart2d(IChartComponentFactory components, Quality quality) {
        super(components, quality);
    }

    public Chart2d(Quality quality, String windowingToolkit) {
        super(quality, windowingToolkit);
    }

    public Chart2d(Quality quality) {
        super(quality);
    }

    public Chart2d(String windowingToolkit) {
        super(windowingToolkit);
    }

    /* */

    protected Map<String, Serie2d> series = new HashMap<String, Serie2d>();
}
