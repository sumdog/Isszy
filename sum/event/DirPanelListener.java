package sum.event;
/**
 *This interface is for handling events passed from a JDirPanel.
 */
import java.io.File;
import java.util.EventListener;

public interface DirPanelListener extends EventListener
{
    /**
     *called when a directory is selected.<BR>
     *@param dir File object containing the abstract pathname of the directory that was selected
     */
    public void directorySelected(File dir);

}
