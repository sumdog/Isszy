/**
  *This class constructs a file list which can be placed
  *on the sides of a borderlayout and present the user
  *with a dual list interface similar to the one found
  *in the Gnome program GQview.<BR>
  *@author <A HREF=mailto:skhanna@csc.tntech.edu>Sumit Khanna</a>
  */
package sum.component;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Vector;
import java.util.Collections;

import sum.event.FileListListener;

public class JSideFileList extends JComponent implements FileFilter, ActionListener, ListSelectionListener, MouseListener
{
  //The top componets for controls
  private Container con_top = new Container();
   private GridBagLayout gbl_controls = new GridBagLayout();
    private GridBagConstraints gbc   = new GridBagConstraints();
     private final JButton b_parent = new JButton("parent");
     private final JButton b_refresh= new JButton("refresh");
     private JComboBox jcb_mounts = new JComboBox(File.listRoots());

   //Vectors to hold Dir/File info for JList
   private Vector vfiles = new Vector();
   private Vector vdirs = new Vector();
   private File current; //current directory we are in

   //a reference to a file list listener
   private FileListListener listener;

    //The file and dir lists which will be placed in the center
    private JList jl_dirs = new JList();
      private JScrollPane jsp_dirs = new JScrollPane(jl_dirs);
    private JList jl_files= new JList();
     private JScrollPane jsp_files= new JScrollPane(jl_files);
  private JSplitPane jsp_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,jsp_dirs,jsp_files);

  /**
    *constructs a file list set to the current directory.
    */
  public JSideFileList()
  {
    //this complicated looking thing is a little hack to get the default
    //directory's abstract pathname without haveing to deail with the trailing "."
    current = (new File(".")).getAbsoluteFile().getParentFile();
    construct();
  }

  /**
    *constructs a file list set to the directory denoted by the file object.<br>
    *@param f Directory from which to construct the list view.
    */
  public JSideFileList(File f)
  {
    current = f;
    construct();
  }

  /**
    *constructs a file list set to the directory denoted by the abstract path name.<br>
    *@param s Abstract pathname of directory
    */
  public JSideFileList(String s)
  {
    current = new File(s);
    construct();
  }

  /**
   *refreshes the current list of files.<BR>
   */
  public void refreshFiles()
  { scanDir(current); }

  /**
    *aranges the components and layouts.<BR>
    *This functions is called after the constructors are finished
    *setting instance variables.
    */
  private void construct()
   {
       //sets the primary layout of the component
       setLayout(new BorderLayout());

         //the top container for the directory controls
         con_top.setLayout(gbl_controls);

           //the first row of components in con_top
           gbc.weightx = 1.0;
           gbc.fill = GridBagConstraints.HORIZONTAL; //get them to fill horizontally
           gbl_controls.setConstraints(b_parent, gbc); //add constraints to button
           con_top.add(b_parent); //add button to layout

           //last item of row one (REMAINDER)
           gbc.gridwidth = GridBagConstraints.REMAINDER;
           gbl_controls.setConstraints(b_refresh, gbc);
           con_top.add(b_refresh);

           //drop down box on next row for mount points
           gbl_controls.setConstraints(jcb_mounts, gbc);
           con_top.add(jcb_mounts);

	   //sizes the divider to 30% dirs, 70% file list
	   jsp_split.setDividerLocation((double) .50);

           //add listeners
           jl_dirs.addMouseListener(this);
           jl_files.addListSelectionListener(this);
           b_parent.addActionListener(this);
           b_refresh.addActionListener(this);
           jcb_mounts.addActionListener(this);

       //adds the top piece to the component
       add(con_top, BorderLayout.NORTH);
       //adds the Dirs/File list views to center
       add(jsp_split, BorderLayout.CENTER);

       //initial file scan
       scanDir(current);
   }

   /**
     *scans a new dir and displays it in the list boxes.
     */
   private void scanDir(File f)
   {
      //clears the two vectors
      vdirs.clear();
      vfiles.clear();

      //dumps all files at location to temp array after being filtered
      //the filter also adds the files to the vector
      File[] ftemp = f.listFiles(this);

      //sort the two vectors
      Collections.sort(vdirs);
      Collections.sort(vfiles);

      //check to see if we have a parent and set the button accordingly
      if(current.getParent() == null)
        { b_parent.setEnabled(false); }
      else
        { b_parent.setEnabled(true); }

       //sets the list data elements for JList boxes
       jl_dirs.setListData(vdirs);
       jl_files.setListData(vfiles);


   }//end scanDir

   /**
     *adds a FileListListener.<BR>
     *Please do not try and add more than one listener. If you try
     *to add a second listener, it will overwrite the first.
     *@param l FileListListener to recieve file and dir selection events
     */
   public void addFileListListener(FileListListener l)
    {  listener = l; }

    //implementation of FileFilter
   public boolean accept(File f)
   {
     if( f.isDirectory() )
     {
       vdirs.add(f.getName());
       return true;
     }
     else
     {
        String temp = f.toString().toLowerCase();
        if(temp.endsWith("jpg") || temp.endsWith("gif") || temp.endsWith("png"))
        {
          vfiles.add(f.getName());
          return true;
        }
     }

        return false;
   }//end accept()

//begin mouse listener interface
   public void mouseClicked(MouseEvent e)
   {
       //change the directory and rescan
       if(e.getSource() == jl_dirs && jl_dirs.getSelectedValue() != null )
       {
          current = new File(current, (String) jl_dirs.getSelectedValue()) ;
          scanDir(current);
          dispatchDirEvent(current);
          jsp_split.repaint();
       }

   }
   public void mouseExited(MouseEvent e)
   {}
   public void mouseEntered(MouseEvent e)
   {}
   public void mousePressed(MouseEvent e)
   {}
   public void mouseReleased(MouseEvent e)
   {
   }
//end mouse listener interface


//begin ListSelectionListener interface
   public void valueChanged(ListSelectionEvent e)
   {
       if(e.getSource() == jl_files)
       {
           Object[] temps = jl_files.getSelectedValues();
	   File[] tempf = new File[temps.length];
           for(int j=0; j < temps.length; j++)
	   {
	       tempf[j] = new File(current, (String) temps[j]);
	   }
	   dispatchFileEvent(tempf);
       }
   }
//end list listener interface
//begin action listener interface
   public void actionPerformed(ActionEvent e)
   {
       //mount point is changed and different than current
       if(e.getSource() == jcb_mounts && current.toString().startsWith( ((JComboBox) e.getSource()).getSelectedItem().toString() ) == false)
       {
           dispatchMountEvent(  ((JComboBox) e.getSource()).getSelectedItem().toString()   );
           current = new File( ((JComboBox) e.getSource()).getSelectedItem().toString()   );
           scanDir(current);
           jsp_split.repaint();
       }
       //refresh button
       else if(e.getSource() == b_refresh)
       {
          scanDir(current);
          jsp_split.repaint();
       }
       //parent directory button
       else if(e.getSource() == b_parent)
       {
          current = current.getParentFile();
          scanDir(current);
          jl_dirs.clearSelection();
          jl_files.clearSelection();
          jsp_split.repaint();
       }
   }
//end action listener interface

   /**
    *dispatches an event when a file has been selected.
    *@param f File that has been selected
    */
   protected void dispatchFileEvent(File[] f)
   {
      if(listener != null)
      { listener.filesSelected(f); }
   }
   /**
     *dispatches an event when a directory has been selected.
     *@param d Directory that has been selected
     */
   protected void dispatchDirEvent(File d)
   {
      if(listener != null)
      { listener.dirSelected(d); }
   }
   /**
     *dispatches an event when a mount point has been changed
     *@param s String representation of mount point
     */
   protected void dispatchMountEvent(String s)
   {
      if(listener != null)
      { listener.mountSelected(s); }
   }

}//end class


















