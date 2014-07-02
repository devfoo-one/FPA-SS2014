package de.bht.fpa.mail.s798158.common;

import java.util.ArrayList;
import java.util.List;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

public interface IDirectory {

  abstract boolean hasChildren();

  abstract ArrayList<IDirectory> getChildren();

  public abstract String getName();

  public abstract String getAbsolutePath();

  public abstract List<Message> getMessages();

}