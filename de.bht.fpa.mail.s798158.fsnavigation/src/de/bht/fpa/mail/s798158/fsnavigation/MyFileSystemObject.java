package de.bht.fpa.mail.s798158.fsnavigation;

import java.util.ArrayList;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s798158.common.IDirectory;

public abstract class MyFileSystemObject implements IDirectory {
  protected final java.io.File file;

  public MyFileSystemObject(final java.io.File file) {
    this.file = file;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.bht.fpa.mail.s798158.fsnavigation.IDirectory#hasChildren()
   */
  @Override
  public boolean hasChildren() {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.bht.fpa.mail.s798158.fsnavigation.IDirectory#getChildren()
   */
  @Override
  public ArrayList<IDirectory> getChildren() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see de.bht.fpa.mail.s798158.fsnavigation.IDirectory#getName()
   */
  @Override
  public String getName() {
    return file.getName();
  }

  @Override
  public String getAbsolutePath() {
    return file.getAbsolutePath();
  }

  @Override
  public ArrayList<Message> getMessages() {
    return null;
  }

  @Override
  public String toString() {
    return this.file.getAbsolutePath();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    int filehashcode;
    if (file == null) {
      filehashcode = 0;
    } else {
      filehashcode = file.hashCode();
    }
    result = prime * result + filehashcode;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof MyFileSystemObject)) {
      return false;
    }
    MyFileSystemObject other = (MyFileSystemObject) obj;
    if (file == null) {
      if (other.file != null) {
        return false;
      }
    } else if (!file.equals(other.file)) {
      return false;
    }
    return true;
  }

}
