package de.bht.fpa.mail.s798158.fsnavigation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class NavigationView extends ViewPart {
  public static final String HISTORYPATH = System.getProperty("user.home") + File.separator + "FPAMailer_history.csv";
  public static final String ID = "de.bht.fpa.mail.s798158.fsnavigation.NavigationView";
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

    // Aufgabe 6, SelectionProvider festlegen
    this.getSite().setSelectionProvider(viewer);

    viewer.addSelectionChangedListener(new ISelectionChangedListener() {
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        // Event auslesen und mithilfe von SelectionHelper ausgewaehltes Objekt
        // auf MyFileSystemObject casten
        ArrayList<Message> messageList = new ArrayList<Message>();
        MyFileSystemObject selectedFSO = SelectionHelper
            .handleStructuredSelectionEvent(event, MyFileSystemObject.class);

        if (selectedFSO != null) {
          messageList = selectedFSO.getMessages();
          System.out.println("Selected directory: " + selectedFSO.getAbsolutePath());
          System.out.println("Number of messages: " + messageList.size());
          for (Message message : messageList) {
            System.out.println(message);
          }
        }
      }
    });
  }

  /**
   * We will set up a model to initialize tree hierarchy.
   */
  private Object createModel() {
    File historyFile = new File(HISTORYPATH);
    final BufferedReader history;
    String lastHistoryRow = null;
    try {
      history = new BufferedReader(new FileReader(historyFile));
      // BufferedReader wegen readLine(), das gibts nich am FileReader
      String row = null;
      while ((row = history.readLine()) != null) {
        lastHistoryRow = row;
      }
      history.close();
    } catch (FileNotFoundException e) {
      return new MyDirectory(new java.io.File(System.getProperty("user.home")));
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
    if (lastHistoryRow != null) {
      return new MyDirectory(new File(lastHistoryRow));
    } else {
      return new MyDirectory(new java.io.File(System.getProperty("user.home")));
    }
    // Our root item is simply a dummy Object. Here you need to provide your own
    // root class.
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  public void updateModel(MyFileSystemObject arg) {
    viewer.setInput(arg);
    viewer.refresh();
    BufferedWriter history;
    final File historyFile = new File(NavigationView.HISTORYPATH);
    try {
      history = new BufferedWriter(new FileWriter(historyFile, true));
      // true am FileWriter fuer append
      // http://beginnersbook.com/2014/01/how-to-append-to-a-file-in-java/
      history.write(arg.getAbsolutePath());
      history.newLine();
      history.close();
    } catch (IOException e) {
      try {
        historyFile.createNewFile();
        history = new BufferedWriter(new FileWriter(historyFile, true));
        history.write(arg.getAbsolutePath());
        history.newLine();
        history.close();
      } catch (IOException e1) {
        System.err.println("could not create path-history file. " + e.getMessage());
      }
    }
  }
}