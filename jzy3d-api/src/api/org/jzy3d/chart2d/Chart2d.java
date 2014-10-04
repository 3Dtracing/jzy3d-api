package org.jzy3d.chart2d;

import java.util.HashMap;
import java.util.Map;

import javax.media.opengl.GLCapabilities;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.chart.factories.IChartComponentFactory;
import org.jzy3d.chart.factories.IChartComponentFactory.Toolkit;
import org.jzy3d.chart2d.primitives.LineSerie2d;
import org.jzy3d.chart2d.primitives.Serie2d;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.primitives.axes.layout.renderers.DateTickRenderer;
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

public class Chart2d extends AWTChart{
    
    public Chart2d() {
        super();
    }

    public Chart2d(IChartComponentFactory factory, Quality quality, String windowingToolkit, GLCapabilities capabilities) {
        super(factory, quality, windowingToolkit, capabilities);
    }

    public Chart2d(IChartComponentFactory factory, Quality quality, String windowingToolkit) {
        super(factory, quality, windowingToolkit);
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

    public Chart2d(float timeMax, float ymin, float ymax, String xlabel, String ylabel){
        this(new Chart2dComponentFactory(), Quality.Intermediate, Toolkit.newt.toString());
        
        IAxeLayout axe = getAxeLayout();
        axe.setYAxeLabel(ylabel);
        axe.setXAxeLabel( xlabel );
        axe.setZAxeLabelDisplayed(false);

        axe.setTickLineDisplayed(false);
        
        axe.setXTickRenderer( new DateTickRenderer( "mm:ss" ) );

        
        View view = getView();
        view.setViewPositionMode(ViewPositionMode.TOP);
        view.setBoundManual(new BoundingBox3d(0, timeMax, ymin, ymax, -1, 1));
        view.setSquared(true);
        view.getCamera().setViewportMode(ViewportMode.STRETCH_TO_FILL);
    }
    
    public Serie2d getSerie(String name){
        Serie2d s = null;
        if(!series.keySet().contains(name)){
            s = new LineSerie2d(name);
            addDrawable(s.getDrawable());
        }
        else{
            s = series.get(name);
        }
        return s;
    }
    
    Map<String,Serie2d> series = new HashMap<String,Serie2d>();
}
