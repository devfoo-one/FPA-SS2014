package de.bht.fpa.mail.s798158.fsnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.bht.fpa.mail.s798158.fsnavigation.NavigationView;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class GetHistoryBaseDirectory extends AbstractHandler {
  /**
   * The constructor.
   */
  public GetHistoryBaseDirectory() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
    GetHistoryBaseDirectoryDialog dialog = new GetHistoryBaseDirectoryDialog(window.getShell());
    // http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/DialogExamples.htm
    if (dialog.open() == Window.OK) {
      final IWorkbenchPage page = window.getActivePage();
      final NavigationView navView = (NavigationView) page.findView(NavigationView.ID);
      navView.updateModel(dialog.getSelectedDirectory());
    }

    return null;
  }
}
