package de.bht.fpa.mail.s798158.fsnavigation;

import java.io.File;
import java.util.ArrayList;

public abstract class MyFileSystemObject {
  private final java.io.File file;

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

  public File getFile() {
    return file;
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
