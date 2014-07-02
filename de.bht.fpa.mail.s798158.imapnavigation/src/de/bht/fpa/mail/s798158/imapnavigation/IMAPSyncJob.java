package de.bht.fpa.mail.s798158.imapnavigation;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import de.bht.fpa.mail.s000000.common.mail.imapsync.ImapHelper;
import de.bht.fpa.mail.s000000.common.mail.imapsync.SynchronizationException;
import de.bht.fpa.mail.s000000.common.mail.model.Account;

public class IMAPSyncJob extends Job {
  private final Account account;

  public IMAPSyncJob(Account account) {
    super(account.getName());
    this.account = account;
  }

  @Override
  protected IStatus run(IProgressMonitor monitor) {
    try {
      ImapHelper.syncAllFoldersToAccount(account, monitor);
    } catch (SynchronizationException e) {
      throw new RuntimeException("Synchronization of IMAP-Account " + account.getName() + " failed."
          + e.getLocalizedMessage());
    }
    return Status.OK_STATUS;
  }

}
