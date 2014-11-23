package mathjetpack.map;

import mathjetpack.entity.Entity;

import java.awt.*;

/**
 * Created by rom on 20/10/14.
 */
public class MapImage extends Entity {

    // The map to which this image belongs to
    private Map mMap;

    /*
       The image can be repeated in X or Y direction
       This is useful because it allows to have a small image and repeat it in any dimension
       to make it to fill the screen.
    */
    private boolean mRepeatX;
    private boolean mRepeatY;

    public MapImage(Map map) {
        super();

        mMap = map;

	setAcceleration(0, 0); 
    }

    /**
     * Sets whether repeat the image along the X axis
     */
    public void repeatX(boolean repeat) {
        mRepeatX = repeat;
    }

    
    /**
     * Sets whether repeat the image along the Y axis
     */
    public void repeatY(boolean repeat) {
        mRepeatY = repeat;
    }

    /**
     * Moves the image 
     */
    @Override
    public void move(double duration) {
        super.move(duration);

        if(mPosition.x <= -mWidth)
            mPosition.x += mWidth;
    }

    /**
     * Draws this Map Image
     * @param g The graphics onto which to paint
     */
    @Override
    public void draw(Graphics2D g) {

        if(mRepeatX && mRepeatY) {
            double x, y;
            for(y = mPosition.y; y <= mMap.getHeight(); y += mHeight)
                for(x = mPosition.x; x < mMap.getWidth(); x += mWidth)
                    g.drawImage(mImage, (int) x, (int) y, null);
        }
        else if(mRepeatY) {
            for (double y = mPosition.y; y <= mMap.getHeight(); y += mHeight)
                g.drawImage(mImage, (int) mPosition.x, (int) y, null);
        }
        else if(mRepeatX){
            for (double x = mPosition.x; x <= mMap.getWidth(); x += mWidth)
                g.drawImage(mImage, (int) x, (int) mPosition.y, null);
        }
        else
            g.drawImage(mImage, (int) mPosition.x, (int) mPosition.y, null);

    }

}
