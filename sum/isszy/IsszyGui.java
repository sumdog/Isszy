package sum.isszy;

import sum.component.*;
import sum.event.*;
import java.io.File;
import javax.swing.JSplitPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class IsszyGui extends JFrame implements FileListListener
{
    //source and destenation image sleectors
    private File lister, sorter;

    //split pane for left which includes the file list and 2nd split pane
    private JSplitPane sp_left;
      private JSideFileList sfl_list;
    //second split pane which contains image and right buttons
    //private JSplitPane sp_right;
      private JImagePane image = new JImagePane();
      private JPanel east = new JPanel();
        private JDirPanel dp_dirlist;
        private JPanel bottom_right = new JPanel(); 
          private JButton b_mkdir = new JButton("New Folder");
          private JButton b_trash = new JButton("Trash");

    public IsszyGui(File lister, File sorter)
    {
	super();
	this.lister = lister;
	this.sorter = sorter;
	construct();
    }

    private void construct()
    {
        //setup two main componets with vars specified by previous constructor
	dp_dirlist = new JDirPanel(sorter);
	sfl_list = new JSideFileList(lister);
 
        //setup west panel
	east.setLayout(new BorderLayout());
        bottom_right.setLayout(new  GridLayout(2,1));
        east.add(dp_dirlist, BorderLayout.CENTER);
	east.add(bottom_right, BorderLayout.SOUTH);
	bottom_right.add(b_mkdir);
	bottom_right.add(b_trash);

	//setup SplitPanes
	//sp_right = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,image,west);
	sp_left = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,sfl_list,image);

	//add listeners
	sfl_list.addFileListListener(this);

	//add everything to Frame
	getContentPane().setLayout(new BorderLayout());
	getContentPane().add(sp_left, BorderLayout.CENTER);
	getContentPane().add(east, BorderLayout.EAST);

    }

    //begin FileListListener implemented functions
    public void dirSelected(File dir)
    {}
    public void mountSelected(String mount)
    {}
    public void filesSelected(File[] files)
    {
	image.setImage(files[0]);
    }
    //end FileListListener implemented functions

}


