package sum.component;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.io.File;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;

/**
 *This class provides a swing component which will display an image.<BR>
 *This panel has support for scrollbars, image zooming, image centering,
 *and various other things that will help the user better view this image
 *that if you simply drew it to a simple awt canvas.
 *@author <A HREF="mailto:skhanna@csc.tntech.edu">Sumit Khanna</a>
 */
public class JImagePane extends JScrollPane
{
    /**
     *the component which the image will be painted onto.
     */   
    private ImageCanvas canvas;

    /**
     *creates a blank ImagePane.<BR>
     *The image pane's image will be centered but not zoomed by default.
     */
    public JImagePane()
    {
	super(new ImageCanvas());
        canvas = (ImageCanvas) this.getViewport().getView();
    }

    /**
     *sets an Image for the panel.<br>
     *The image must be a supported by the JVM's default toolkit (e.g. 
     *a jpeg, gif or png as of JDK 1.3).
     *@param f file object containing the abstract pathname for the image
     */
    public void setImage(File f)
    {
        canvas.setImage(f);
	setViewportView(canvas);
    }
    /**
     *determines if image is centered.<BR>
     *@param b determines if you want to center the image.
     */
    public void setCenter(boolean b)
    { canvas.center = b; }
    /**
     *determines if the image is zoomed.<BR>
     *If the image is larger than the current size of it's parent
     *container, then the image will shrink to fit. If not, it will 
     *simply remain the same size.
     *@param b determines if you want to zoom the image.
     */
    public void setZoom(boolean b)
    { canvas.zoom = b;    }
    /**
     *returns if the image is currently centered.<BR>
     *@return true if image is centered.
     */
    public boolean getCenter()
    { return canvas.center;   }
    /**
     *returns if the image is currently zoomed.<BR>
     *@return true if the image is zoomed.
     */
    public boolean getZoom()
    { return canvas.zoom;    }
}

/**
 *the class used by JImagePane to control drawing the image
 */
class ImageCanvas extends JPanel
{
    /**
     *the width and height of the image
     */
    private int width, height;

    /**
     *indicates if the user wants to zoom image
     */    
    boolean zoom;

    /**
     *indicates if the user wants to have image centered
     */
    boolean center;

    /**
     *the image that's currently loaded/displayed
     */
    private Image image;

    /**
     *set to the default toolkit for this operating system
     */
    private Toolkit toolkit = Toolkit.getDefaultToolkit();

    /**
     *creates an empty canvas
     */
    public ImageCanvas()
    {
	super();
	zoom = false;
	center = true;
    }

    public Dimension getMaximumSize()
      { return this.getPreferredSize(); }
    public Dimension getPreferredSize()
      { 
	  if(!zoom)
	  {
	      return new Dimension(width,height);  
	  }
	  else
	  {
	      return new Dimension(0,0);
	  }
      }
    public Dimension getMinimumSize()
      { return this.getPreferredSize();  }

    public void setImage(File f)
    {
	//creates the image using the default toolkit
	image = toolkit.createImage(f.getAbsolutePath());

	//waits until the image is loaded (heigh and width are -1
	//while the image is still loading)
	do
	    {
		width = image.getWidth(this);
		height = image.getHeight(this);
	    }
	while(width == -1 || height == -1);

	//refreshes the view with the newly loaded component
        repaint();
    }

    public void paint(Graphics g)
    {
        //draws our background
        //g.setColor(background);                    //set background color
        g.fillRect(0,0,getWidth(),getHeight());    //paint a rectange the size of the component

	int x,y,w,h; //going to be used to position image
	int pwidth  = getParent().getWidth(); //width of the parent container
	int pheight = getParent().getHeight(); //height of the parent container

	if(image != null) //make sure we have an image first
        { 
	    //checks to see if we need to zoom (out image is bigger than the fitted area and the
	    //user has requested zoom)
	    if(zoom && pwidth < image.getWidth(this) && !(pheight < image.getHeight(this)) )
		{
		    //if the image is wider than the parent window, but not taller
		    width = pwidth;
		    height = (image.getHeight(this) * width) / image.getWidth(this);
		}
	    else if(zoom && !(pwidth < image.getWidth(this)) && pheight < image.getHeight(this))
		{
		    //if the image is taller than the parent window, but not wider
		    height = pheight;
		    width = (image.getWidth(this) * height) / image.getHeight(this);

		}
	    else if(zoom && pwidth < image.getWidth(this) && pheight < image.getHeight(this))
		{
                    //thank you Jason Aderholt for this wonderful piece of code which adjusts the aspect ratio
		    //if the image width AND height are both larger than the parent window
		    float ratio, p_ratio;
		    ratio = (float)image.getHeight(this) / (float)image.getWidth(this);
		    p_ratio = (float) pheight / (float) pwidth;
		    for(float scale=0; true; scale++)
   		    {
			if(p_ratio < ratio) 
			    {
				height = pheight;
				width = (int) (((float)image.getWidth(this)) * ((float)height) / ((float) image.getHeight(this)));
				break;
			    }
			else if(p_ratio >= ratio)
			    {
				width = pwidth;
				height = (int) (((float)image.getHeight(this)) * ((float)width) / ((float) image.getWidth(this)));
				break;
			    }

		    }

		}
	    else
	        {
		    //else the user never requested zoom to begin with.
		   width = image.getWidth(this);
		   height = image.getHeight(this);
		}


	    //checks to see if user wants image centered and if it needs to be
	    if(center && pwidth > width && !(pheight > height) )
		{ 
		    x = (pwidth - width) / 2; 
		    y = 0;
		}
	    else if(center && !(pwidth > width) && pheight > height ) 
		{ 
		    y = (pheight - height) / 2;
		    x = 0;
		}
	    else if(center && pwidth > width && pheight > height)
		{
		    x = (pwidth - width) / 2;
		    y = (pheight - height) / 2;
		}
	    else
		{
		    x = y = 0;
		}

	    //finally we draw the image
	    g.drawImage(image,x,y,width,height,this);
	}
    }
}





