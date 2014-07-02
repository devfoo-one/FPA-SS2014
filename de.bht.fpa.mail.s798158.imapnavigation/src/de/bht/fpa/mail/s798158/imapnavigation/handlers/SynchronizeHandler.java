package de.bht.fpa.mail.s798158.imapnavigation.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import de.bht.fpa.mail.s000000.common.mail.imapsync.ImapHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s798158.imapnavigation.IMAPNavigationView;
import de.bht.fpa.mail.s798158.imapnavigation.IMAPSyncJob;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SynchronizeHandler extends AbstractHandler {
  /**
   * The constructor.
   */
  public SynchronizeHandler() {
  }

  /**
   * the command has been executed, so extract extract the needed information
   * from the application context.
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    final Account testaccount = ImapHelper.getAccount(IMAPNavigationView.IMAP_JOB_NAME);
    final IMAPSyncJob syncJob = new IMAPSyncJob(testaccount);
    syncJob.setUser(true);
    syncJob.schedule();
    return null;
  }
}
