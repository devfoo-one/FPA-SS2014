package de.bht.fpa.mail.s798158.imapnavigation;

import java.util.ArrayList;
import java.util.List;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s798158.common.IDirectory;

public class IMAPAccountCollection implements IDirectory {
  private final ArrayList<IMAPAccount> accounts = new ArrayList<IMAPAccount>();

  public IMAPAccountCollection(final List<IMAPAccount> accounts2) {
    if (accounts2 != null && accounts2.size() > 0) {
      this.accounts.addAll(accounts2);
    }
  }

  public IMAPAccountCollection(IMAPAccount... accounts) {
    for (IMAPAccount a : accounts) {
      this.accounts.add(a);
    }
  }

  @Override
  public boolean hasChildren() {
    return this.accounts.size() > 0;
  }

  @Override
  public ArrayList<IDirectory> getChildren() {
    ArrayList<IDirectory> returnList = new ArrayList<IDirectory>();
    if (hasChildren()) {
      returnList.addAll(accounts);
    }
    return returnList;
  }

  @Override
  public String getName() {
    return "IMAPAccountCollection";
  }

  @Override
  public String getAbsolutePath() {
    return "IMAPAccountCollection";
  }

  @Override
  public List<Message> getMessages() {
    return null;
  }

}
