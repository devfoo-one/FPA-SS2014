package de.bht.fpa.mail.s798158.imapnavigation;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s798158.common.IDirectory;
import de.bht.fpa.mail.s000000.common.rcp.selection.SelectionHelper;

public class IMAPNavigationView extends ViewPart {
  public static final String ID = "de.bht.fpa.s798158.imapnavigation.IMAPNavigationView";
  private TreeViewer viewer;

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl(Composite parent) {
    // a TreeViewer is a Jface widget, which shows trees
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

    // We set the ContentProvider for the TreeViewer. This class prepares data
    // to be shown by the TreeViewer.
    viewer.setContentProvider(new ViewContentProvider());

    // We set the LabelProvider. This class decides how elements in the tree are
    // shown by returning a text and an optional icon.
    viewer.setLabelProvider(new ViewLabelProvider());

    // Here we set the root of the tree. The framework will ask for more data
    // when the user expands tree items.
    viewer.setInput(createModel());

    this.getSite().setSelectionProvider(viewer);

    viewer.addSelectionChangedListener(new ISelectionChangedListener() {
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        // Event auslesen und mithilfe von SelectionHelper ausgewaehltes Objekt
        // auf MyFileSystemObject casten
        ArrayList<Message> messageList = new ArrayList<Message>();
        IDirectory selectedFSO = SelectionHelper.handleStructuredSelectionEvent(event, IDirectory.class);

        if (selectedFSO != null) {
          messageList = selectedFSO.getMessages();
          if (messageList != null) {
            System.out.println("Selected directory: " + selectedFSO.getAbsolutePath());
            System.out.println("Number of messages: " + messageList.size());
            for (Message message : messageList) {
              System.out.println(message);
            }
          }
        }
      }
    });

  }

  /**
   * We will set up a model to initialize tree hierarchy.
   */
  private Object createModel() {
    // Our root item is simply a dummy Object. Here you need to provide your own
    // root class.
    return new IMAPAccountCollection(new IMAPAccount(new DummyAccount().getDummyAccount()));
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}