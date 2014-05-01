package de.bht.fpa.mail.s798158.fsnavigation;

import java.util.ArrayList;

public abstract class MyFileSystemObject {
  protected final java.io.File file;

  public MyFileSystemObject(final java.io.File file) {
    this.file = file;
  }

  public boolean hasChildren() {
    return false;
  }

  public ArrayList<MyFileSystemObject> getChildren() {
    return null;
  }

  public String getName() {
    return file.getName();
  }

}
