package de.bht.fpa.mail.s798158.imapnavigation;

import java.util.ArrayList;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s798158.common.IDirectory;

public class IMAPAccount implements IDirectory {

  @Override
  public boolean hasChildren() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public ArrayList<IDirectory> getChildren() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getAbsolutePath() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<Message> getMessages() {
    return null;
  }

}
