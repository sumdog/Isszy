/**
  *Isszy Preference Handler.<BR>
  *this object will be set to the settings found in the 
  *windows registery or UNIX $HOME/.java directory or to 
  *defaults if either no user settings exist or no 
  *user settings are suported by operating system. It's important
  *to note that if those settings are changed by something other than 
  *this object, this object will not reflect the changes. The settings
  *are ready from the system once upon the objects initilization and written
  *back to the system only when writePrefs() is called. If you wish to 
  *cancle a change in perferences, simply set the reference to this object
  *to null.
  *@author <A HREF="mailto:skhanna@csc.tntech.edu">Sumit Khanna</a>
  */
package sum.isszy;

import java.util.prefs.Preferences;
import java.io.File;
import java.io.IOException;

public class IsszyPrefs
{

    private static Preferences prefs = Preferences.userNodeForPackage(Isszy.class);

    public static boolean getZoomImage()
    { return prefs.getBoolean("ZOOM",true); }

    public static boolean getCenterImage()
    { return prefs.getBoolean("CENTER",true); }

    public static boolean getSlideShow()
    { return prefs.getBoolean("SLIDE", true); }

    public static boolean getSaveSettings()
    { return prefs.getBoolean("SAVE", false); }

    public static boolean getPromptOverwrite()
    { return prefs.getBoolean("OVERWRITE",true); }

    public static String getInitialDirectory()
    { return prefs.get("IDIR","./"); }

    public static String getSortDirectory()
    { return prefs.get("SDIR","./"); }

    public static boolean getFirstStart()
    { return prefs.getBoolean("FIRST",true); }

    public static void setFirstStart(boolean b)
    { prefs.putBoolean("FIRST",b); }

    public static void setZoomImage(boolean b)
    { prefs.putBoolean("ZOOM",b); }

    public static void setCenterImage(boolean b)
    { prefs.putBoolean("CENTER",b); }

    public static void setSlideShow(boolean b)
    { prefs.putBoolean("SLIDE",b); }

    public static void setSaveSettings(boolean b)
    { prefs.putBoolean("SAVE",b); }

    public static void setPromptOverwrite(boolean b)
    { prefs.putBoolean("OVERWRITE",b); }

    public static void setInitialDirectory(String s) throws IOException
    { 
	if( (new File(s).isDirectory()) )
	    { prefs.put("IDIR",s); }
	else
	    { throw new IOException("Invalid Directory"); }
    }

    public static void setSortDirectory(String s) throws IOException
    { 
	if( (new File(s).isDirectory()) )
	    { prefs.put("SDIR",s); }
	else
	    {throw new IOException("Invalid Directory"); }
    }

    public static void setSlideTime(String s) throws NumberFormatException
    { prefs.putInt("SLIDETIME", Integer.parseInt(s)); }

    public static void setSlideTime(int i)
    { prefs.putInt("SLIDETIME",i); }

    public static int getSlideTime()
    { return prefs.getInt("SLIDETIME", 1); }
    
    public static int getHeight()
    { return prefs.getInt("HEIGHT",480);}
    
    public static int getWidth()
    { return prefs.getInt("WIDTH",640); }
    
    public static void setHeight(int x)
    { prefs.putInt("HEIGHT",x); }
    
    public static void setWidth(int x)
    { prefs.putInt("WIDTH",x); }
    
     public static int getXPosition()
    { return prefs.getInt("XPOSITION",-1);}
    
    public static int getYPosition()
    { return prefs.getInt("YPOSITION",-1); }
    
    public static void setXPosition(int x)
    { prefs.putInt("XPOSITION",x); }
    
    public static void setYPosition(int x)
    { prefs.putInt("YPOSITION",x); }
}