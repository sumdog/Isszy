package sum.event;

import java.io.File;
import java.util.EventListener;

public interface FileListListener extends EventListener
{
    public void filesSelected(File[] f);
    public void dirSelected(File d);
    public void mountSelected(String s);
}
