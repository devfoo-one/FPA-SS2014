package de.bht.fpa.mail.s798158.fsnavigation.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import de.bht.fpa.mail.s798158.fsnavigation.MyDirectory;
import de.bht.fpa.mail.s798158.fsnavigation.NavigationView;
import de.bht.fpa.mail.s798158.fsnavigation.SelectionHelper;

public class GetHistoryBaseDirectoryDialog extends Dialog {
  private ListViewer list;
  private MyDirectory selectedDirectory;

  public GetHistoryBaseDirectoryDialog(Shell parentShell) {
    super(parentShell);
  }

  public GetHistoryBaseDirectoryDialog(IShellProvider parentShell) {
    super(parentShell);
  }

  @Override
  // http://www.vogella.com/tutorials/EclipseDialogs/article.html#dialogs_purpose
  public Control createDialogArea(Composite parent) {
    final Composite container = (Composite) super.createDialogArea(parent);
    list = new ListViewer(container);
    list.setContentProvider(ArrayContentProvider.getInstance());
    list.setLabelProvider(new LabelProvider() {
      @Override
      public String getText(Object element) {
        if (element instanceof MyDirectory) {
          MyDirectory dir = (MyDirectory) element;
          return dir.getFile().getAbsolutePath();
        } else {
          return "No base directories in history";
        }

      }
    });
    if (getHistoryEntries() != null) {
      list.setInput(getHistoryEntries());
    } else {
      list.setInput(new Object[] { "bla" });
      list.getList().setEnabled(false);
    }

    return container;
  }

  @Override
  protected void okPressed() {
    this.selectedDirectory = SelectionHelper.handleStructuredSelection(list.getSelection(), MyDirectory.class);
    super.okPressed();
  }

  @Override
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText("Select Base Directory in History");
  } // http://www.vogella.com/tutorials/EclipseDialogs/article.html

  public MyDirectory getSelectedDirectory() {
    return this.selectedDirectory;
  } // http://www.vogella.com/tutorials/EclipseDialogs/article.html

  private Object[] getHistoryEntries() {
    File historyFile = new File(NavigationView.HISTORYPATH);
    final BufferedReader history;
    // HashSet vermeidet doppelte Eintraege
    final HashSet<MyDirectory> historyDirectories = new HashSet<MyDirectory>();
    try {
      history = new BufferedReader(new FileReader(historyFile));
      // BufferedReader wegen readLine(), das gibts nich am FileReader
      String row = null;
      while ((row = history.readLine()) != null && row.length() > 0) {
        historyDirectories.add(new MyDirectory(new File(row)));
      }
      history.close();
    } catch (FileNotFoundException e) {
      return null;
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    return historyDirectories.toArray();
  }
}
