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
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import java.util.Vector;
import java.util.Collections;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

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
     *refreshes the current directory view.<BR>
     */
    public void refresh()
    {
	c_dirs.refresh();
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
    { return new Dimension(c_dirs.width ,super.getPreferredSize().height); }


}

class JDir extends Container implements DirSizer, ActionListener, MouseListener, DirectoryPopupListener
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
	//creates a new layout
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
	//discard old buttons
	removeAll();

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
		add(new JDirButton(temp[x], this, this, this));    
	    }
	}
	//makes the new buttons appear in the layout
        validate();
    }

    //begin implemented functions
    public void setDSize(int width)
    {
	if(width > this.width) 
	  this.width = width;  
    }

    public int getDSize()
    { return width;  }

    //action listener
    public void actionPerformed(ActionEvent e)
    {
	if(listener != null)
	{
	    listener.directorySelected(( (JDirButton) e.getSource() ).getFile());
	}
    }
    //mouse listener
    public void mouseClicked(MouseEvent e)
    {}
    public void mouseEntered(MouseEvent e)
    {}
    public void mouseExited(MouseEvent e)
    {}
    public void mousePressed(MouseEvent e)
    {
	//detect right click for popup menu
	if(e.isPopupTrigger())
	{
	    //retrueves a new JMenu from the DirectoryPopup class (inner class down below) and converts
	    //it to a popup menu and then displays it where the mouse was clicked in the panel
	    (new DirectoryPopup( ((JDirButton)e.getSource()).getFile() ,this )).getPopupMenu().show( (Container) e.getSource(),e.getX(),e.getY());
	}
    }
    public void mouseReleased(MouseEvent e)
    {
	//on some platforms the popup is tirggired when 
	//the mouse is released, on others when pressed
	//so we'll call the other funcion from here
	mousePressed(e);
    }
    //end mouse listener

    //being directory popup listener
    public void DirectorySelected(File f)
    {
	if(listener != null)
	    { listener.directorySelected(f); }
    }
    //end directory popup listener

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
     *@param m mouse listener for the button
     */
    public JDirButton(File a, DirSizer s, ActionListener l, MouseListener m)
    { 
	super(a.getName()); 
        file = a;
        size = s;
        addActionListener(l);
	addMouseListener(m);
	size.setDSize(super.getPreferredSize().width);
    }

    /**
     *gets the File object represented by button.<BR>
     *@return the file object used to construct this button.
     */
    public File getFile()
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

/**
 *class for directoy list popups seen when user makes a "popup" click (tyically the 
 *right mouse button) in the DirPanel
 */
class DirectoryPopup extends JMenu implements ActionListener
{

    /**
     *file object which menu represents.<br>
     */
    private File file;

    /**
     *listener object for communication.<br>
     */
    private DirectoryPopupListener listener;

    /**
     *menu item labled "Sort Image"
     */
    private JMenuItem mi_sort = new JMenuItem("Sort Image");

    /**
     *menu item labled "New Folder"
     */
    private JMenuItem mi_mkdir = new JMenuItem("New Folder");

    /**
     *internal constructor for popup menu.<BR>
     *@param file directory to list.
     *@param l DirectoryPopupListener used for communication
     */
    public DirectoryPopup(File file,  DirectoryPopupListener l)
    {
	super(file.getName(),true);
	//setup file and listener variable for later
	this.file = file;
	this.listener = l;

	//an array of files in the current directory
	File[] temp = file.listFiles();
	//a Vector to store directories later
	Vector dirs = new Vector();
	//loop though each file and see if it is a directory
	for(int count = 0 ; count < temp.length ; count++)
	{
	    if(temp[count].isDirectory())
	    { dirs.addElement(temp[count]); }
	}
        //sort the dirs using Merge sort
	Collections.sort(dirs);

	//adds top two menu itmes and seperator
	this.add(mi_sort);
	this.add(mi_mkdir);
	this.addSeparator();

	//adds listeners
	mi_sort.addActionListener(this);
	mi_mkdir.addActionListener(this);

	for(int count=0; count < dirs.size() ; count++)
	{
	    this.add( ( new DirectoryPopup( ((File) dirs.elementAt(count)) ,  l ) ));
	}

    }


    /**
     *returns the file the menu object represents.<br>
     *@return file object menu represents
     */
    private File getFile()
    { return file; }

    //begin Implemented ActionListener
    public void actionPerformed(ActionEvent e)
    {
	if(e.getSource() == mi_sort && listener != null)
	    { listener.DirectorySelected(file); }
	else if(e.getSource() == mi_mkdir)
	    {
		String newdir = JOptionPane.showInputDialog(new JFrame(),"Enter a Directory Name","New Directory",JOptionPane.QUESTION_MESSAGE);
		if (newdir != null)
		    {
			if( (new File(file.toString(),newdir)).mkdirs() == false)
			    { JOptionPane.showMessageDialog(new JFrame(),"Unable to Create Directory","Error",JOptionPane.ERROR_MESSAGE); }
			else
			    {
				if(JOptionPane.showConfirmDialog(new JFrame(),"Would you like to move the currently selected files to this new directory?","Move Files",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				    {
					if(listener != null)
					    {
						listener.DirectorySelected(new File(file.toString(),newdir));
					    }
				    }
			    }
		    }
	    }
    }

}

/**
 *used for interaction between the JDirList and recursive construction of menu objects.
 */
interface DirectoryPopupListener
{
    /**
     *called when the user selects a directory to sort the image to.<BR>
     *@param f directory to sort image to.
     */
    public void DirectorySelected(File f);
}
