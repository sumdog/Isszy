package sum.isszy.dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
//import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Toolkit;
import sum.component.JImagePane;
import java.awt.Dimension;

public class IOverwriteDialog extends JDialog implements ActionListener
{

    private JImagePane image_left = new JImagePane();
    private JImagePane image_right = new JImagePane();
    private File filea, fileb;
    private JPanel top = new JPanel();
    private JPanel center = new JPanel();
    private JPanel mid = new JPanel();
    private JPanel bottom = new JPanel();
    private JPanel p_buttons = new JPanel();
    private JButton b_yes = new JButton("Yes");
    private JButton b_no = new JButton("No");
    protected boolean result;

    public static boolean showOverwriteDialog(File a, File b)
    {
	IOverwriteDialog dialog = new IOverwriteDialog(a,b);
	dialog.setVisible(true);
	return dialog.result;
    }

    private IOverwriteDialog(File a, File b)
    {
	super(new JFrame(),"Overwrite File",true);

	try
	    {
		filea = a;
		fileb = b;
		image_left.setImage(a);
		image_right.setImage(b);
	    }
	catch(IOException e)
	    {
		//error message dialog here
	    }

	this.setModal(true);
	//	this.setResizeable(false);
	construct();
    }


    private void construct()
    {
	this.setSize(500,300); //set size of our window
	//center our window
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation( (int) ((size.getWidth()-500)/2), (int) ((size.getHeight() - 300)/2));

	getContentPane().setLayout(new BorderLayout());

	//setup images
	center.setLayout(new GridLayout(1,2));
	image_left.setZoom(true);
	image_right.setZoom(true);
	image_left.setCenter(true);
	image_right.setCenter(true);
	center.add(image_left);
	center.add(image_right);
	//setup image labes
	mid.setLayout(new GridLayout(0,2));
	mid.add(new JLabel("Source",SwingConstants.CENTER));
	mid.add(new JLabel("Destination",SwingConstants.CENTER));
	mid.add(new JLabel(  fileSizeFormat(filea.length()),SwingConstants.CENTER  ));
	mid.add(new JLabel(  fileSizeFormat(fileb.length()),SwingConstants.CENTER  ));
	mid.add(new JLabel(  image_left.getDimension().width+"x"+image_left.getDimension().height,SwingConstants.CENTER));
	mid.add(new JLabel(  image_right.getDimension().width+"x"+image_right.getDimension().height,SwingConstants.CENTER));
	bottom.setLayout(new BorderLayout());
	bottom.add(mid, BorderLayout.CENTER);
	bottom.add(p_buttons, BorderLayout.SOUTH);

	p_buttons.setLayout(new FlowLayout());
	p_buttons.add(b_yes);
	p_buttons.add(b_no);

	top.setLayout(new FlowLayout());
	top.add(new JLabel("Duplicate File Exists. Overwrite File?",SwingConstants.CENTER ));


	this.getContentPane().add(top,BorderLayout.NORTH);
	this.getContentPane().add(center,BorderLayout.CENTER);
	this.getContentPane().add(bottom,BorderLayout.SOUTH);

	b_yes.addActionListener(this);
	b_no.addActionListener(this);
	

    }

    public void actionPerformed(ActionEvent e)
    {
	if(e.getSource() == b_yes)
	    { 
		result = true;
		this.setVisible(false);
	    }
	else if(e.getSource() == b_no)
	    {
		result = false;
		this.setVisible(false);
	    }
    }//end actionPerformed

 
    private String fileSizeFormat(long size)
    {
	if(size < 1000)
	    { return new String(size+" bytes"); }
        if(size < 1000000)
	    { return new String(size/1000+" kB");}
	if(size < 1000000000)
	    {return new String(size/1000000+"mB");}
	return new Long(size).toString();
    }

}//end class
