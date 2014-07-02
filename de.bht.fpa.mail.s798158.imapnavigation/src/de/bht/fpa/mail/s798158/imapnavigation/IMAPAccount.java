package de.bht.fpa.mail.s798158.imapnavigation;

import java.util.ArrayList;
import java.util.List;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s798158.common.IDirectory;

public class IMAPAccount implements IDirectory {
  private final Account account;

  public IMAPAccount(final Account account) {
    this.account = account;
  }

  @Override
  public boolean hasChildren() {
    return account.getFolders().size() > 0;
  }

  @Override
  public ArrayList<IDirectory> getChildren() {
    ArrayList<IDirectory> returnList = new ArrayList<IDirectory>();
    if (hasChildren()) {
      for (Folder f : account.getFolders()) {
        returnList.add(new IMAPDirectory(f));
      }
    }
    return returnList;
  }

  @Override
  public String getName() {
    return account.getName();
  }

  @Override
  public String getAbsolutePath() {
    return account.getHost();
  }

  @Override
  public List<Message> getMessages() {
    // ein Account kann keine Messages haben
    return new ArrayList<Message>();
  }

}
