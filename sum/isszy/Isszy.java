package sum.isszy;

import java.io.File;

/**
 *the entry point class for the Isszy Image Sorter program.<BR>
 *This is the entry point for the Isszy program, also referred to as the
 *<I>main</I> class or <I>driver</I> class.
 */
public class Isszy
{
   
    public static void main(String[]args)
    {
	IsszyGui main = new IsszyGui(new File("/home"),new File("/home"));
	main.setSize(640,480);
	main.setVisible(true);
    }
}
