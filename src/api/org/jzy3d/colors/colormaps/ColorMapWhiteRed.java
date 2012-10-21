package org.jzy3d.colors.colormaps;

import org.jzy3d.colors.Color;
import org.jzy3d.colors.IColorMappable;


public class ColorMapWhiteRed implements IColorMap {
	    
    /**
     * Creates a new instance of ColorMapHotCold.
     * 
     * A ColorMapHotCold objects provides a color for points standing
     * between a Zmin and Zmax values.
     * 
     * The points standing outside these [Zmin;Zmax] boundaries are assigned
     * to the same color than the points standing on the boundaries. 
     * 
     * The hot-cold colormap is a progressive transition from blue, 
     * to white and last, red.
     */
    public ColorMapWhiteRed() {
    	direction = true;
    }
    
    public void setDirection(boolean isStandard){
    	direction = isStandard;
    }
    
    public boolean getDirection(){
    	return direction;
    }

    
    public Color getColor( IColorMappable colorable, double x, double y, double z ){
        return getColor( x, y, z, colorable.getMin(), colorable.getMax() );        
    }
    
    /**
     * A helper for getColor( ColorMappable waferview, Point3d pt ) that calls
     * other helper functions
     */
    private Color getColor( double x, double y, double z, double zMin, double zMax ){
        
        double rel_value = 0;
        
        if( z < zMin )
            rel_value = 0;
        else if( z > zMax )
            rel_value = 1;
        else{
        	if(direction)
        		rel_value = ( z - zMin ) / ( zMax - zMin );
        	else
        		rel_value = ( zMax - z ) / ( zMax - zMin );
        }
        
        return new Color( 1.0f, (float)rel_value, (float)rel_value );
    }
    
    public Color getColor( IColorMappable colorable, double z ){
        return getColor( 0.0f, 0.0f, z, colorable.getMin(), colorable.getMax() );        //To re-use the existing code
    }

    /**************************************************************************/
    
    private boolean direction;
}
