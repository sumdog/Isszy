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


public class IsszyPrefs
{

    private static Preferences prefs = Preferences.userNodeForPackage(Isszy.class);

    /**
     *creates a prefrence object set with default values.<BR>
     */
    public IsszyPrefs()
    {
	//initalize all public vars
    }

    /**
     *writes all changed prefrences. <BR>
     */
    public void writePrefs()
    {
    }
}
