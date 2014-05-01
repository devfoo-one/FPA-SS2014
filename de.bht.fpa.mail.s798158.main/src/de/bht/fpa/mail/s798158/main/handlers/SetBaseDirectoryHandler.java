package de.bht.fpa.mail.s798158.main.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SetBaseDirectoryHandler extends AbstractHandler {
  /**
   * The constructor.
   */
  public SetBaseDirectoryHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

    // TODO: DEBUG! ENTFERNEN
    // DirectoryDialog dd = new DirectoryDialog(window.getShell());
    // final String dir = dd.open();

    // TODO: DEBUG!
    final String dir = "/users/tom/Downloads";
    if (dir != null) {
      final TreeViewer viewer = (NavigationView) window.getActivePage().getViewReferences();
      viewer.update(dir);

    }
    // MessageDialog.openInformation(
    // window.getShell(),
    // "Main",
    // "Hello, Eclipse world");
    return null;
  }
}
