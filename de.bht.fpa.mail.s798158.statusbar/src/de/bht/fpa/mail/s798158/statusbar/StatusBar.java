package de.bht.fpa.mail.s798158.statusbar;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.bht.fpa.mail.s000000.common.rcp.statusbar.StatusBarHelper;
import de.bht.fpa.mail.s798158.fsnavigation.MyFileSystemObject;
import de.bht.fpa.mail.s798158.fsnavigation.SelectionHelper;

public class StatusBar implements IStartup {

  @Override
  public void earlyStartup() {
    // http://help.eclipse.org/helios/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fui%2FIStartup.html
    final IWorkbench workbench = PlatformUI.getWorkbench();
    workbench.getDisplay().asyncExec(new Runnable() {
      @Override
      public void run() {
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        if (window != null) {
          window.getActivePage().addSelectionListener(new ISelectionListener() {
            @Override
            public void selectionChanged(IWorkbenchPart part, ISelection selection) {
              MyFileSystemObject selectedFSO = SelectionHelper.handleStructuredSelection(selection,
                  MyFileSystemObject.class);
              if (selectedFSO != null) {
                final String message = "Directory '" + selectedFSO.getFile().getAbsolutePath() + "' was selected";
                StatusBarHelper.setMessage(message);
              }
            }
          });
        }
      }
    });
  }
}
