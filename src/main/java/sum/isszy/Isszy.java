package sum.isszy;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sum.isszy.dialog.IInitialDialog;

/**
 *the entry point class for the Isszy Image Sorter program.<BR>
 *This is the entry point for the Isszy program, also referred to as the
 *<I>main</I> class or <I>driver</I> class.
 */
public class Isszy {

  public static final String VERSION = "2.1";

  public static void main(String[] args) {

    if (IsszyPrefs.getFirstStart()) {
      IInitialDialog start = new IInitialDialog();
      start.setVisible(true);
    }
    
    /* Apple Menubar Support */
    try {
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      System.setProperty("com.apple.mrj.application.apple.menu.about.name",
          "Isszy");
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
      System.out.println("ClassNotFoundException: " + e.getMessage());
    } catch (InstantiationException e) {
      System.out.println("InstantiationException: " + e.getMessage());
    } catch (IllegalAccessException e) {
      System.out.println("IllegalAccessException: " + e.getMessage());
    } catch (UnsupportedLookAndFeelException e) {
      System.out.println("UnsupportedLookAndFeelException: " + e.getMessage());
    }
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        IsszyGui main = new IsszyGui();
        main.setVisible(true);
      }
    });


  }
}
