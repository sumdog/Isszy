package sum.isszy.dialog;

import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ISettingsDialog extends JDialog
{
    public ISettingsDialog()
    {
	super(new Frame(),"Isszy Preferences");
	construct();
    }


    private JTabbedPane tablet = new JTabbedPane();


    private JPanel p_buttons = new JPanel(new FlowLayout());
      private JButton b_ok = new JButton ("OK");
      private JButton b_cancel = new JButton ("Cancel");

    private void construct()
    {
	//construct buttom OK/Cancel area
	p_buttons.add(b_ok);
	p_buttons.add(b_cancel);

	//put everything together
	getContentPane().add(tablet, BorderLayout.CENTER);
	getContentPane().add(p_buttons, BorderLayout.SOUTH);
 
	//some default window stuff
	setSize(300,300);
	setResizable(false);
    }
}
