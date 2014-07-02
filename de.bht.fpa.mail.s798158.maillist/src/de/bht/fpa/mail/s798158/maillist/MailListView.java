package de.bht.fpa.mail.s798158.maillist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.ViewPart;

import de.ralfebert.rcputils.properties.BaseValue;
import de.ralfebert.rcputils.tables.TableViewerBuilder;
import de.ralfebert.rcputils.tables.ColumnBuilder;
import de.ralfebert.rcputils.tables.format.Formatter;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;
import de.bht.fpa.mail.s000000.common.table.MessageValues;
import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s798158.common.IDirectory;
import de.bht.fpa.mail.s000000.common.rcp.selection.SelectionHelper;

public class MailListView extends ViewPart implements IExecutionListener {

  private TableViewer tableviewer;
  private Text searchText;
  private ViewFilterPermanent permanentFilter;
  private static final int IMPORTANCE_COLUMN_WIDTH = 70;
  private static final int RECEIVED_COLUMN_WIDTH = 90;
  private static final int READ_COLUMN_WIDTH = 40;
  private static final int SENDER_COLUMN_WIDTH = 175;
  private static final int RECIPIENTS_COLUMN_WIDTH = 175;
  private static final int SUBJECT_COLUMN_WIDTH = 320;
  private ArrayList<Message> messageList = new ArrayList<>();

  // Aufgabe 6
  private final ISelectionListener listener = new ISelectionListener() {

    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
      // pruefen ob die Selection vom NavigationView kommt usw...
      if (selection != null && selection instanceof TreeSelection) {
        IDirectory selectedFSO = SelectionHelper.handleStructuredSelection(selection, IDirectory.class);
        if (selectedFSO != null) {
          messageList = selectedFSO.getMessages();
          tableviewer.setInput(messageList);
          tableviewer.refresh();
        }
      }
    }
  };

  @Override
  public void createPartControl(Composite parent) {

    // Aufgabe 8
    final IWorkbench workbench = PlatformUI.getWorkbench();
    ICommandService commandService = (ICommandService) workbench.getService(ICommandService.class);
    commandService.addExecutionListener(this);

    // Aufgabe 6
    getSite().getPage().addSelectionListener(listener);

    // Table in das Composite packen, dann müsste auch ein Input funktionieren
    parent.setLayout(new GridLayout(1, false));
    final Composite searchComposite = new Composite(parent, SWT.NONE);
    searchComposite.setLayout(new GridLayout(2, false));
    searchComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
    Label searchLabel = new Label(searchComposite, SWT.NONE);
    searchLabel.setText("Search: ");
    searchText = new Text(searchComposite, SWT.BORDER);
    searchText.setLayoutData(new GridData(SWT.FILL, SWT.END, true, false, 1, 1));
    final Composite tableComposite = new Composite(parent, SWT.NONE);
    tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    // Zusatzaufgabe 5.1 permanentFilter konfigurieren
    permanentFilter = new ViewFilterPermanent("") {
      @Override
      public boolean select(Viewer viewer, Object parentElement, Object element) {
        final Message m = (Message) element;
        final boolean c1 = StringCompareHelper.matches(m.getSubject(), this.getSearchString(), FilterOperator.CONTAINS);
        final boolean c2 = StringCompareHelper.matches(m.getSubject(), this.getSearchString(), FilterOperator.CONTAINS);
        final String received = Formatter.forDate(new SimpleDateFormat("dd.MM.yyyy")).format(m.getReceived());
        final boolean c3 = StringCompareHelper.matches(received, this.getSearchString(), FilterOperator.CONTAINS);
        final String sent = Formatter.forDate(new SimpleDateFormat("dd.MM.yyyy")).format(m.getSent());
        final boolean c4 = StringCompareHelper.matches(sent, this.getSearchString(), FilterOperator.CONTAINS);
        boolean c5 = false;
        for (final Recipient recipient : m.getRecipients()) {
          boolean cr1 = StringCompareHelper.matches(recipient.getEmail(), this.getSearchString(),
              FilterOperator.CONTAINS);
          boolean cr2 = StringCompareHelper.matches(recipient.getPersonal(), this.getSearchString(),
              FilterOperator.CONTAINS);
          if (cr1 || cr2) {
            c5 = true;
            break;
          }
        }
        final boolean c6 = StringCompareHelper.matches(m.getSender().getEmail(), this.getSearchString(),
            FilterOperator.CONTAINS);
        final boolean c7 = StringCompareHelper.matches(m.getSender().getPersonal(), this.getSearchString(),
            FilterOperator.CONTAINS);
        return c1 || c2 || c3 || c4 || c5 || c6 || c7;
      }
    };

    searchText.addKeyListener(new KeyListener() {
      @Override
      public void keyPressed(KeyEvent e) {
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // hier nicht die eigenen Filter verwenden, sonst läuft das nicht mehr
        // ohne Plugin!
        permanentFilter.setSearchString(searchText.getText());
        tableviewer.refresh();
      }
    });

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
        return message.getSender().getEmail() + "<" + message.getSender().getPersonal() + ">";
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
          recipientString += r.getEmail() + "<" + r.getPersonal() + ">" + seperator;
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

    t.setInput(null);
    tableviewer = t.getTableViewer();
    tableviewer.addFilter(permanentFilter);

    // Aufgabe 6
    getSite().setSelectionProvider(tableviewer);

    tableviewer.setFilters(new ViewerFilter[] { permanentFilter });
  }

  @Override
  public void setFocus() {
    tableviewer.getTable().setFocus();
  }

  @Override
  public void dispose() {
    getSite().getPage().removeSelectionListener(listener);
  }

  @Override
  public void notHandled(String commandId, NotHandledException exception) {
  }

  @Override
  public void postExecuteFailure(String commandId, ExecutionException exception) {
  }

  @Override
  public void postExecuteSuccess(String commandId, Object returnValue) {
    if (returnValue instanceof IFilter) {
      ViewerFilter[] emptyFilters = new ViewerFilter[1];
      // permanentFilter setzen und alle alten löschen
      emptyFilters[0] = permanentFilter;
      tableviewer.setFilters(emptyFilters);

      // wenn ein Filter kam dann hier den Filter laden
      final IFilter filter = (IFilter) returnValue;
      tableviewer.addFilter(new ViewerFilter() {
        @Override
        public boolean select(Viewer viewer, Object parentElement, Object element) {
          // input aus dem viewer holen (muss hier sein wegen Aktualisierungen)
          final Set<Message> filteredMessages = filter.filter(messageList);
          if (filteredMessages.contains(element)) {
            return true;
          }
          return false;
        }
      });
      tableviewer.refresh();
    }

  }

  @Override
  public void preExecute(String commandId, ExecutionEvent event) {
  }

}
