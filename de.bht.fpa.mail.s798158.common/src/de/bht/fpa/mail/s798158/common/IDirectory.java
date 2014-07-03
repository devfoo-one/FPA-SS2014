package de.bht.fpa.mail.s798158.common;

import java.util.ArrayList;
import java.util.List;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public interface IDirectory {

  boolean hasChildren();

  ArrayList<IDirectory> getChildren();

  String getName();

  String getAbsolutePath();

  List<Message> getMessages();

}