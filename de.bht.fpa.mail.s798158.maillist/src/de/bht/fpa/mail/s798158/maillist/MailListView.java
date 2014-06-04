package de.bht.fpa.mail.s798158.maillist;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.JAXB;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import de.ralfebert.rcputils.properties.BaseValue;
import de.ralfebert.rcputils.tables.TableViewerBuilder;
import de.ralfebert.rcputils.tables.ColumnBuilder;
import de.ralfebert.rcputils.tables.format.Formatter;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.bht.fpa.mail.s798158.fsnavigation.MyFileSystemObject;
import de.bht.fpa.mail.s798158.fsnavigation.NavigationView;
import de.bht.fpa.mail.s798158.fsnavigation.SelectionHelper;

public class MailListView extends ViewPart {

  private TableViewer tableviewer;
  private static final int IMPORTANCE_COLUMN_WIDTH = 70;
  private static final int RECEIVED_COLUMN_WIDTH = 90;
  private static final int READ_COLUMN_WIDTH = 40;
  private static final int SENDER_COLUMN_WIDTH = 175;
  private static final int RECIPIENTS_COLUMN_WIDTH = 175;
  private static final int SUBJECT_COLUMN_WIDTH = 320;

  // Aufgabe 6
  private final ISelectionListener listener = new ISelectionListener() {
    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
      // pruefen ob die Selection vom NavigationView kommt usw...
      if (part instanceof NavigationView && selection != null && selection instanceof TreeSelection) {
        MyFileSystemObject selectedFSO = SelectionHelper.handleStructuredSelection(selection, MyFileSystemObject.class);
        ArrayList<Message> messageList = new ArrayList<Message>();
        // FilenameFilter auf xml-Dateien
        FilenameFilter fileFilter = new FilenameFilter() {
          @Override
          public boolean accept(File dir, String name) {
            if (name.toLowerCase().endsWith(".xml")) {
              // pruefen ob es nicht ein Ordner mit dem Name *.xml ist
              if (new File(dir.getAbsolutePath() + File.separator + name).isFile()) {
                return true;
              }
            }
            return false;
          }
        };

        if (selectedFSO != null) {
          for (final java.io.File element : selectedFSO.getFile().listFiles(fileFilter)) {
            // XML File mit JAXB einlesen
            Message message = null;
            try {
              message = JAXB.unmarshal(element, Message.class);
            } catch (Exception e) {
              System.err.println(e.getMessage());
            }

            if (message != null && message.getId() != null) {
              // wenn message.getId() == null dann wohl falsches Format
              messageList.add(message);
            }
          }
        }

        tableviewer.setInput(messageList);
        tableviewer.refresh();
      }
    }
  };

  @Override
  public void createPartControl(Composite parent) {

    // Aufgabe 6
    getSite().getPage().addSelectionListener(listener);

    // Table in das Composite packen, dann müsste auch ein Input funktionieren
    // final Composite searchComposite = new Composite(parent, SWT.VERTICAL);
    final Composite tableComposite = new Composite(parent, SWT.VERTICAL);
    final TableViewerBuilder t = new TableViewerBuilder(tableComposite);

    final ColumnBuilder colImportance = t.createColumn("Importance");
    colImportance.bindToValue(MessageValues.IMPORTANCE);
    colImportance.setPixelWidth(IMPORTANCE_COLUMN_WIDTH);
    colImportance.build();

    final ColumnBuilder colReceived = t.createColumn("Received");
    colReceived.useAsDefaultSortColumn();
    colReceived.bindToValue(MessageValues.RECEIVED);
    colReceived.format(Formatter.forDate(new SimpleDateFormat("dd.MM.yyyy")));
    colReceived.setPixelWidth(RECEIVED_COLUMN_WIDTH);
    colReceived.build();

    final ColumnBuilder colRead = t.createColumn("Read");
    colRead.bindToValue(MessageValues.READ);
    colRead.setPixelWidth(READ_COLUMN_WIDTH);
    colRead.build();

    final ColumnBuilder colSender = t.createColumn("Sender");
    colSender.bindToValue(MessageValues.SENDER);
    colSender.bindToValue(new BaseValue<Message>() {
      @Override
      public Object get(Message message) {
        return message.getSender().getEmail();
      }
    });
    colSender.setPixelWidth(SENDER_COLUMN_WIDTH);
    colSender.build();

    final ColumnBuilder colRecipients = t.createColumn("Recipients");
    colRecipients.bindToValue(new BaseValue<Message>() {
      @Override
      public Object get(Message message) {
        // read first recipient
        final String seperator = ", ";
        String recipientString = "";
        for (Recipient r : message.getRecipients()) {
          recipientString += r.getEmail() + seperator;
        }
        // remove last seperator
        return recipientString.substring(0, recipientString.length() - seperator.length());
      }
    });
    colRecipients.setPixelWidth(RECIPIENTS_COLUMN_WIDTH);
    colRecipients.build();

    final ColumnBuilder colSubject = t.createColumn("Subject");
    colSubject.bindToValue(MessageValues.SUBJECT);
    colSubject.setPixelWidth(SUBJECT_COLUMN_WIDTH);
    colSubject.build();

    // Collection<Message> messages = new
    // RandomTestDataProvider(NUM_MESSAGES).getMessages();
    Collection<Message> messages = null;
    t.setInput(messages);
    tableviewer = t.getTableViewer();

    // die restlichen Felder ins Composite kippen
    // final Label searchLabel = new Label(searchComposite, SWT.NONE);
    // searchLabel.setText("Search:");
    // final Text foo = new Text(searchComposite, SWT.NONE);
    // searchLabel.setVisible(true);

  }

  @Override
  public void setFocus() {
    tableviewer.getTable().setFocus();
  }

  @Override
  public void dispose() {
    getSite().getPage().removeSelectionListener(listener);
  }

}
