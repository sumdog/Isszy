/**
 *A component for displaying directories for file moving/sorting.<BR>
 *This component was designed to displayed a column of buttons representing
 *all the directories within a directory.
 *@author <A HREF="skhanna@csc.tntech.edu">Sumit Khanna</A>
 */
package sum.component;

import javax.swing.JScrollPane;
import java.awt.Container;
import javax.swing.JButton;
import java.io.File;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import sum.event.DirPanelListener;

public class JDirPanel extends JScrollPane
{

    /**
     *contains the JButton objects
     */
    private JDir c_dirs;

    /**
     *the directory from which the buttons will be formed from.
     */
    private File current_file;

    /**
     *creates a container of buttons representing directories.<BR>
     *@param f the <code>File</code> object containing the abstract pathname of 
     *the directory from which the buttons will be generated from
     */
    public JDirPanel(File f)
    {
	//calls the default constructer
	super();
	//gets rid of our horizontal scrollbar since we won't need it
	horizontalScrollBarPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
	//creates a panel to hold our JDirButtons
	c_dirs = new JDir(f);
	//sets the viewport of the JScrollPane to make our panel scrollable
	setViewportView(c_dirs);

	//this component adapter will help the JDirButtons size properly to fit the 
	//width of the Panel
        addComponentListener(new ComponentAdapter() {
          public void componentResized(ComponentEvent e)
          {
	      //tells the component the maximum width they are alloted via the DirSizer interface implemented by
	      //the JDirPanel
              ((DirSizer)((JScrollPane)e.getSource()).getViewport().getView()).setDSize(((JScrollPane)e.getSource()).getWidth());
	      //refreshed the viewport
	      ((JScrollPane)e.getSource()).setViewportView( ((JScrollPane)e.getSource()).getViewport().getView()  );
          }
        });
    }

    /**
     *sets the current directory.<BR>
     *@param f the <code>File</code> objects containing the abstract pathname of the 
     *current directory.
     */
    public void setDir(File f)
    { 
	c_dirs.dir = f; 
	c_dirs.refresh();
    }

    /**
     *gets the current working directory.<BR>
     *@return a <code>File</code> objects containing the abstract pathname of the current working directory.
     */
    public File getDir()
    { 
	return c_dirs.dir; 
    }

    /**
     *sets the listener for this DirPanel.<BR>
     *Only one listener may be set at a time. Adding a second listener
     *will remove the first one.
     *@param l the DirPanelListener 
     */
    public void addDirPanelListener(DirPanelListener l)
    { c_dirs.listener = l; }

    public Dimension getMaximumSize()
    { return getPreferredSize(); }
    public Dimension getMinimumSize()
    { return getPreferredSize(); }
    public Dimension getPreferredSize()
    { return new Dimension(c_dirs.width,super.getPreferredSize().height); }


}

class JDir extends Container implements DirSizer, ActionListener
{
    /**
     *the file objects denoting the directory to scan.
     */
    File dir;

    /**
     *the width of the children buttons.
     */
    int width;

    /**
     *listener for panel.
     */
    DirPanelListener listener;

    /**
     *creates a new JDir.<BR>
     *@param f the file object denoting the directory to scan for subdirectories.
     */
    JDir (File f)
    {
	//call the default constructor
	super();
	//set the class variable
	dir = f;
	//lists all the directories as buttons
	refresh();
    }

    /**
     *refreshes the view of the current directory.<BR>
     *should be called if the user wishes to refresh the current view, e.g. when new directories
     *have been added/removed since last scan.
     */
    void refresh()
    {
	//creates a new layout to discard old buttons
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	//an array of files in the current directory
	File[] temp = dir.listFiles();
        //sort the array using Merge sort
	Arrays.sort(temp);
	//loop though each file and see if it is a directory
	//if it is, create a JDirButton to represent it
	for(int x = 0 ; x < temp.length ; x++)
	{
	    if(temp[x].isDirectory())
	    {
		add(new JDirButton(temp[x], this, this));    
	    }
	} 
    }
 
    //begin implemented functions
    public void setDSize(int width)
    {
	if(width > this.width) 
	  this.width = width;  
    }

    public int getDSize()
    { return width;  }

    public void actionPerformed(ActionEvent e)
    {
	if(listener != null)
	{
	    listener.directorySelected(( (JDirButton) e.getSource() ).getFile());
	}
    }

}

/**
 *This class is for createing JButtons with an extra field for holding a File object.
 */
class JDirButton extends JButton
{
    /**
     *the file from which the button title is formed.
     */
    private File file;

    /**
     *a sizer interface to determine the button width
     */
    private DirSizer size;

    /**
     *constructs a button with the title as the directory denoted by the abstract
     *pathname of a file object and whos size is denoted by a DirSizer interface.<BR>
     *@param a the file which will determine the name of the button.
     *@param s the dirsizer object which determines the size of the button.
     *@param l action listener for the button
     */
    public JDirButton(File a, DirSizer s, ActionListener l)
    { 
	super(a.getName()); 
        file = a;
        size = s;
        addActionListener(l);
	size.setDSize(super.getPreferredSize().width);
    }

    /**
     *gets the File object represented by button.<BR>
     *@return the file object used to construct this button.
     */
    File getFile()
    {return file;}

    //overridden functions
    public Dimension getMaximumSize()
    { return getPreferredSize(); }
    public Dimension getMinimumSize()
    { return getPreferredSize(); }
    public Dimension getPreferredSize()
    { return new Dimension(size.getDSize(),super.getPreferredSize().height); }


}


/**
 *an interface that lets the JDirButton objects communicate with the JDir container they are in
 *and get/send information about their width.
 */
interface DirSizer
{
    /**
     *send information about the current width.<BR>
     *@param width the width of the component.
     */
    public void setDSize(int width);
    /*gets information about the components width.<BR>
     *This typically indicates what the container would prefer the width of the
     *component to be.
     *@return the width the container perfers the component to be.
     */
    public int getDSize();
}
