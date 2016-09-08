package sum.isszy.dialog;

import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import sum.isszy.IsszyPrefs;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class ISettingsDialog extends JDialog implements ActionListener
{
    public ISettingsDialog()
    {
	super(new JFrame(),"Isszy Preferences",true);
	this.setModal(true);
	construct();
    }

    private JTabbedPane tablet = new JTabbedPane();
      private JPanel p_general = new JPanel();
       private JPanel p_general1 = new JPanel();
       private JPanel p_general2 = new JPanel();
        private JTextField tf_general_idir = new JTextField(18);
        private JButton b_general_browse = new JButton(new FolderIcon16());
        private JCheckBox cb_general_save = new JCheckBox();
      private JPanel p_image = new JPanel();
      private JPanel p_image_left = new JPanel();
      private JPanel p_image_right = new JPanel();
        private JPanel p_image1 = new JPanel();
        private JPanel p_image2 = new JPanel();
        private JPanel p_image3 = new JPanel();
        private JPanel p_image5 = new JPanel();
         private JCheckBox cb_image_zoom = new JCheckBox();
         private JCheckBox cb_image_center = new JCheckBox();
         private JCheckBox cb_image_slide = new JCheckBox();
         private JTextField tf_image_time = new JTextField(2);
      private JPanel p_sort = new JPanel();
       private JPanel p_sort1 = new JPanel();
       private JPanel p_sort2 = new JPanel();
       private JPanel p_sort3 = new JPanel();
        private JTextField tf_sort_sdir = new JTextField(18);
        private JButton b_sort_browse = new JButton(new FolderIcon16());
        private JCheckBox cb_sort_prompt = new JCheckBox();
        private JTextField tf_sort_trash = new JTextField(18);
        private JButton b_sort_trash = new JButton(new FolderIcon16());
    private JPanel p_buttons = new JPanel(new FlowLayout());
      private JButton b_ok = new JButton ("OK");
      private JButton b_cancel = new JButton ("Cancel");


    private void construct()
    {
	//read in current setings
	cb_image_center.setSelected(IsszyPrefs.getCenterImage());
	cb_image_zoom.setSelected(IsszyPrefs.getZoomImage());
	cb_image_slide.setSelected(IsszyPrefs.getSlideShow());
	cb_general_save.setSelected(IsszyPrefs.getSaveSettings());
	cb_sort_prompt.setSelected(IsszyPrefs.getPromptOverwrite());
	tf_sort_sdir.setText(IsszyPrefs.getSortDirectory());
	tf_general_idir.setText(IsszyPrefs.getInitialDirectory());
	tf_image_time.setText(  new Integer( IsszyPrefs.getSlideTime() ).toString()  );
    tf_sort_trash.setText(IsszyPrefs.getTrashDirectory());

	//setup action listeners
	b_general_browse.addActionListener(this);
	b_sort_browse.addActionListener(this);
	b_ok.addActionListener(this);
	b_cancel.addActionListener(this);
	b_sort_trash.addActionListener(this);

	//construct General Tab
	p_general.setLayout(new GridLayout(2,0));
	p_general1.setLayout(new FlowLayout(FlowLayout.CENTER));
	p_general2.setLayout(new FlowLayout(FlowLayout.CENTER));
	p_general1.add(new JLabel("Initial Directory"));
	p_general1.add(tf_general_idir);
	p_general1.add(b_general_browse);
	b_general_browse.setToolTipText("Browse");
	p_general2.add(cb_general_save);
	p_general2.add(new JLabel("Save Window Placement on Exit"));
	p_general.add(p_general1);
	p_general.add(p_general2);

	//construct Image tab
	p_image.setLayout(new BoxLayout(p_image,BoxLayout.X_AXIS));
	p_image_left.setLayout(new BoxLayout(p_image_left,BoxLayout.Y_AXIS));
	p_image_right.setLayout(new BoxLayout(p_image_right,BoxLayout.Y_AXIS));
	p_image1.setLayout(new FlowLayout(FlowLayout.LEFT));
	p_image2.setLayout(new FlowLayout(FlowLayout.LEFT));
	p_image3.setLayout(new FlowLayout(FlowLayout.LEFT));
	p_image5.setLayout(new FlowLayout(FlowLayout.LEFT));
	p_image1.add(cb_image_center);
	p_image1.add(new JLabel("Center Image"));
	p_image2.add(cb_image_zoom);
        p_image2.add(new JLabel("Zoom Image"));
	p_image3.add(cb_image_slide);
	p_image3.add(new JLabel("Slide Show Multiple Images"));
	p_image5.add(new JLabel("Slide Show Interval"));
	p_image5.add(tf_image_time);
	p_image_left.add(p_image1);
	p_image_left.add(p_image2);
       	p_image_right.add(p_image3);
	p_image_right.add(p_image5);

        p_image.add(p_image_left);
	p_image.add(p_image_right);

	//construct Sorting Tab
	p_sort.setLayout(new GridLayout(3,0));
	p_sort1.setLayout(new FlowLayout(FlowLayout.CENTER));
	p_sort2.setLayout(new FlowLayout(FlowLayout.CENTER));
	p_sort1.add(new JLabel("Sort Directory   "));
	p_sort1.add(tf_sort_sdir);
	p_sort1.add(b_sort_browse);
	b_sort_browse.setToolTipText("Browse");
	p_sort2.add(cb_sort_prompt);
	p_sort2.add(new JLabel("Prompt Before Overwrite"));
	p_sort3.setLayout(new FlowLayout(FlowLayout.CENTER));
	p_sort3.add(new JLabel("Trash Directory"));
	p_sort3.add(tf_sort_trash);
	p_sort3.add(b_sort_trash);
	p_sort.add(p_sort1);
	p_sort.add(p_sort3);
	p_sort.add(p_sort2);
	


	//put all tabs on table
	tablet.addTab("General",p_general);
	tablet.addTab("Image",p_image);
	tablet.addTab("Sorting",p_sort);

	//construct buttom OK/Cancel area
	p_buttons.add(b_ok);
	p_buttons.add(b_cancel);

	//put everything together
	getContentPane().add(tablet, BorderLayout.CENTER);
	getContentPane().add(p_buttons, BorderLayout.SOUTH);
 
	//some default window stuff
	setSize(450,170);
	setResizable(false);
	//center our window
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation( (int) ((size.getWidth()-400)/2), (int) ((size.getHeight() - 190)/2));
    }


    public void actionPerformed(ActionEvent e)
    {

	//take care of both browse buttons and the File Dialog
	JFileChooser choose = new JFileChooser();
	choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	choose.setMultiSelectionEnabled(false);

	if(e.getSource() == b_general_browse)
	    {
		choose.setCurrentDirectory(new File(tf_general_idir.getText()));
		if(choose.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION)
		    { tf_general_idir.setText(choose.getSelectedFile().toString()); }
	    }
	else if(e.getSource() == b_sort_browse)
	    {
		choose.setCurrentDirectory(new File(tf_sort_sdir.getText()));
		if(choose.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION)
		    { tf_sort_sdir.setText(choose.getSelectedFile().toString()); }
	    }
	else if(e.getSource() == b_sort_trash)
	{
		choose.setCurrentDirectory(new File(tf_sort_trash.getText()));
		if(choose.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION)
		    { tf_sort_trash.setText(choose.getSelectedFile().toString()); }		
	}
	//take care of our three buttons at the bottom
	else if(e.getSource() == b_ok)
	    {
		try
		    {
			IsszyPrefs.setInitialDirectory(tf_general_idir.getText());
			IsszyPrefs.setSortDirectory(tf_sort_sdir.getText());
			IsszyPrefs.setZoomImage(cb_image_zoom.isSelected());
			IsszyPrefs.setCenterImage(cb_image_center.isSelected());
			IsszyPrefs.setSlideShow(cb_image_slide.isSelected());
			IsszyPrefs.setSaveSettings(cb_general_save.isSelected());
			IsszyPrefs.setPromptOverwrite(cb_sort_prompt.isSelected());
			IsszyPrefs.setSlideTime(tf_image_time.getText());
			IsszyPrefs.setTrashDirectory(tf_sort_trash.getText());
			if(e.getSource() == b_ok) //the only difference between OK and Apply is Ok closes window
			    { this.setVisible(false); }
		    }
		catch(IOException io)
		    {
			JOptionPane.showMessageDialog(new JFrame(),"Please check to make sure you've entered valid directories", "Invalid Directory Information",JOptionPane.ERROR_MESSAGE);
		    }
		catch(NumberFormatException nfe)
		    {
			JOptionPane.showMessageDialog(new JFrame(),"Value for Slide Show Interval Must be an Integer", "Invalid Slide Show Interval",JOptionPane.ERROR_MESSAGE);
		    }
	    }
	else if(e.getSource() == b_cancel)
	    {
		this.setVisible(false);
	    }


    }
}

