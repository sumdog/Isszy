package sum.isszy.dialog;

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

public class ISettingsDialog extends JDialog
{
    public ISettingsDialog()
    {
	super(new JFrame(),"Isszy Preferences",true);
	construct();
    }


    private JTabbedPane tablet = new JTabbedPane();
      private JPanel p_general = new JPanel();
        private JTextField tf_general_idir = new JTextField();
        private JButton b_general_browse = new JButton("Browse");
        private JCheckBox cb_general_save = new JCheckBox();
      private JPanel p_image = new JPanel();
        private JCheckBox cb_image_zoom = new JCheckBox();
        private JCheckBox cb_image_center = new JCheckBox();
        private JCheckBox cb_image_slide = new JCheckBox();
      private JPanel p_sorting = new JPanel();
        private JTextField tf_sorting_sdir = new JTextField();
        private JCheckBox cb_sorting_prompt = new JCheckBox();
    private JPanel p_buttons = new JPanel(new FlowLayout());
      private JButton b_ok = new JButton ("OK");
      private JButton b_cancel = new JButton ("Cancel");
      private JButton b_apply = new JButton ("Apply");

    private void construct()
    {
	//construct General Tab

	//construct Image tab
	p_image.setLayout(new GridLayout(3,1));
	p_image.add(cb_image_center);
	p_image.add(new JLabel("Center Image"));
	p_image.add(cb_image_zoom);
        p_image.add(new JLabel("Zoom Image"));
	p_image.add(cb_image_slide);
	p_image.add(new JLabel("Slide Show Multiple Images"));

	//construct Sorting Tab


	//put all tabs on table
	tablet.addTab("General",p_general);
	tablet.addTab("Image",p_image);
	tablet.addTab("Sorting",p_sorting);

	//construct buttom OK/Cancel area
	p_buttons.add(b_ok);
	p_buttons.add(b_cancel);
	p_buttons.add(b_apply);

	//put everything together
	getContentPane().add(tablet, BorderLayout.CENTER);
	getContentPane().add(p_buttons, BorderLayout.SOUTH);
 
	//some default window stuff
	setSize(300,300);
	setResizable(false);
    }
}

