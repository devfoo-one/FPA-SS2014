package de.bht.fpa.mail.s798158.fsnavigation.handlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s798158.fsnavigation.MyDirectory;
import de.bht.fpa.mail.s798158.fsnavigation.NavigationView;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SetBaseDirectory extends AbstractHandler {
  /**
   * The constructor.
   */
  public SetBaseDirectory() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

    org.eclipse.swt.widgets.DirectoryDialog dd = new org.eclipse.swt.widgets.DirectoryDialog(window.getShell());
    dd.setText("Set Base Directory");
    dd.setMessage("choose new base directory...");
    final String dir = dd.open();

    // vor Abgabe AUSkommentieren
    // final String dir = "/Users/tom/Downloads";

    if (dir != null) {
      final IWorkbenchPage page = window.getActivePage();
      final NavigationView navView = (NavigationView) page.findView(NavigationView.ID);
      final MyDirectory newPath = new MyDirectory(new File(dir));
      navView.updateModel(newPath);

      BufferedWriter history;
      final File historyFile = new File(NavigationView.HISTORYPATH);
      try {
        history = new BufferedWriter(new FileWriter(historyFile));
        history.append(dir);
        history.append(System.getProperty("line.separator"));
        history.close();
      } catch (IOException e) {
        try {
          historyFile.createNewFile();
          history = new BufferedWriter(new FileWriter(historyFile));
          history.append(dir);
          history.append(System.getProperty("line.separator"));
          history.close();
        } catch (IOException e1) {
          System.err.println("could not create path-history file. " + e.getMessage());
        }
      }
    }
    return null;
  }
}
