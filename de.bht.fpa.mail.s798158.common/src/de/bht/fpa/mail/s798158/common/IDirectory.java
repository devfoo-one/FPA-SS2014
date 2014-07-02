package de.bht.fpa.mail.s798158.common;

import java.util.ArrayList;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public interface IDirectory {

  public abstract boolean hasChildren();

  public abstract ArrayList<IDirectory> getChildren();

  public abstract String getName();

  public abstract String getAbsolutePath();

  public abstract ArrayList<Message> getMessages();

}