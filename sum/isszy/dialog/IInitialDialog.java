package sum.isszy.dialog;

import sum.isszy.IsszyPrefs;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Toolkit;

public class IInitialDialog extends JDialog implements ActionListener
{

    public IInitialDialog()
    {
	super(new JFrame(),"Isszy First Time Setup",true);
	this.setModal(true);
	construct();
    }

    private JPanel p_from = new JPanel();
    private JPanel p_to = new JPanel();
    private JPanel p_buttons = new JPanel();
    private JTextField tf_from = new JTextField(20);
    private JTextField tf_to = new JTextField(20);
    private JButton b_from = new JButton(new FolderIcon16());
    private JButton b_to = new JButton(new FolderIcon16());
    private JButton b_ok = new JButton("OK");
    private JButton b_exit = new JButton("Exit");

    private void construct()
    {
	this.setSize(420,140); //set size of our window
	//center our window
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation( (int) ((size.getWidth()-420)/2), (int) ((size.getHeight() - 140)/2));

	//set layout for our three major panels
	p_from.setLayout(new FlowLayout(FlowLayout.LEFT));
	p_to.setLayout(new FlowLayout(FlowLayout.LEFT));
	p_buttons.setLayout(new FlowLayout(FlowLayout.CENTER));

	//construct the From panel
	p_from.add(new JLabel("Images to Sort"));
	p_from.add(tf_from);
	p_from.add(b_from);
	b_from.setToolTipText("Browse");

	//construct the To panel
	p_to.add(new JLabel("Destination     "));
	p_to.add(tf_to);
	p_to.add(b_to);
	b_to.setToolTipText("Browse");

	//the bottom main buttons
	p_buttons.add(b_ok);
	p_buttons.add(b_exit);
	
	//slap it all together
	this.getContentPane().setLayout(new GridLayout(3,0));
	this.getContentPane().add(p_from);
	this.getContentPane().add(p_to);
	this.getContentPane().add(p_buttons);
	this.getContentPane().setVisible(true);

	//some action listeners
	b_to.addActionListener(this);
	b_from.addActionListener(this);
	b_ok.addActionListener(this);
	b_exit.addActionListener(this);

	//a message to explain to user what's going on
	JOptionPane.showMessageDialog(new JFrame(),"Welcome to Isszy Image Sorter.\nSince this is your first time using this application,\nyou'll need to setup an initial directory of photos\nyou wish to sort and a directory to sort those \nphotos into.", "Welcome to Isszy", JOptionPane.PLAIN_MESSAGE);
    }

    public void actionPerformed(ActionEvent e)
    {
	JFileChooser choose = new JFileChooser();
	choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	choose.setMultiSelectionEnabled(false);

	if(e.getSource() == b_to)
	    {
		choose.setCurrentDirectory(new File(tf_to.getText()));
		if(choose.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION)
		    { tf_to.setText(choose.getSelectedFile().toString()); }
	    }
	else if(e.getSource() == b_from)
	    {
		choose.setCurrentDirectory(new File(tf_from.getText()));
		if(choose.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION)
		    { tf_from.setText(choose.getSelectedFile().toString()); }
	    }
	else if(e.getSource() == b_exit)
	    {
		IsszyPrefs.setFirstStart(true);
		System.exit(2);
	    }
	else if(e.getSource() == b_ok)
	    {
		try
		{
		    IsszyPrefs.setInitialDirectory(tf_from.getText());
		    IsszyPrefs.setSortDirectory(tf_to.getText());
		    IsszyPrefs.setFirstStart(false);
		    this.setVisible(false);
		}
		catch(IOException o)
		{
		    JOptionPane.showMessageDialog(new JFrame(),"Please check to make sure you've entered valid directories", "Invalid Directory Information",JOptionPane.ERROR_MESSAGE);
		}
	    }
    }//end actionPerformed
}//end class
