package de.bht.fpa.mail.s798158.imapnavigation;

import java.util.ArrayList;
import java.util.List;

import de.bht.fpa.mail.s798158.common.IDirectory;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class IMAPDirectory implements IDirectory {
  private final Folder folder;

  public IMAPDirectory(final Folder folder) {
    this.folder = folder;
  }

  @Override
  public boolean hasChildren() {
    return folder.getFolders().size() > 0;
  }

  @Override
  public ArrayList<IDirectory> getChildren() {
    ArrayList<IDirectory> returnList = new ArrayList<IDirectory>();
    if (hasChildren()) {
      for (Folder f : folder.getFolders()) {
        returnList.add(new IMAPDirectory(f));
      }
    }
    return returnList;
  }

  @Override
  public String getName() {
    return folder.getFullName();
  }

  @Override
  public String getAbsolutePath() {
    return folder.getFullName();
  }

  @Override
  public List<Message> getMessages() {
    if (folder.getMessages() != null) {
      return folder.getMessages();
    } else {
      return new ArrayList<Message>();
    }

  }

}
