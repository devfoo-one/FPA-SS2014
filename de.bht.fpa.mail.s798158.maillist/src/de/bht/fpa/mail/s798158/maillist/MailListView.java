package de.bht.fpa.mail.s798158.maillist;

import java.util.Collection;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.ralfebert.rcputils.tables.TableViewerBuilder;
import de.ralfebert.rcputils.tables.ColumnBuilder;

import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.table.MessageValues;

public class MailListView extends ViewPart {

  private TableViewer tableviewer;
  private static final int NUM_MESSAGES = 50;

  @Override
  public void createPartControl(Composite parent) {

    TableViewerBuilder t = new TableViewerBuilder(parent);

    ColumnBuilder colImportance = t.createColumn("Importance");
    colImportance.bindToValue(MessageValues.IMPORTANCE);
    colImportance.build();

    ColumnBuilder colReceived = t.createColumn("Received");
    colReceived.useAsDefaultSortColumn();
    colReceived.build();

    ColumnBuilder colRead = t.createColumn("Read");
    colRead.bindToValue(MessageValues.READ);
    colRead.build();

    ColumnBuilder colSender = t.createColumn("Sender");
    colSender.bindToValue(MessageValues.SENDER);
    colSender.build();

    ColumnBuilder colRecipients = t.createColumn("Recipients");
    colRecipients.bindToValue(MessageValues.RECIPIENT);
    colRecipients.build();

    ColumnBuilder colSubject = t.createColumn("Subject");
    colSubject.bindToValue(MessageValues.SUBJECT);
    colSubject.build();

    Collection<Message> messages = new RandomTestDataProvider(NUM_MESSAGES).getMessages();

    t.setInput(messages);
    tableviewer = t.getTableViewer();

  }

  @Override
  public void setFocus() {
    tableviewer.getTable().setFocus();

  }

}
