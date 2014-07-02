package de.bht.fpa.mail.s798158.fsnavigation;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.xml.bind.JAXB;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s798158.common.IDirectory;

public class MyDirectory extends MyFileSystemObject implements IDirectory {

  public MyDirectory(File file) {
    super(file);
  }

  @Override
  public boolean hasChildren() {
    // FileFilter auf Directories
    FileFilter fileFilter = new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.isDirectory();
      }
    };

    return this.file.listFiles(fileFilter) != null && this.file.listFiles(fileFilter).length > 0;
  }

  @Override
  public ArrayList<IDirectory> getChildren() {
    final ArrayList<IDirectory> result = new ArrayList<IDirectory>();
    // FileFilter auf Directories
    FileFilter fileFilter = new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        return pathname.isDirectory();
      }
    };

    final File[] fileList = this.file.listFiles(fileFilter);
    if (fileList != null) {
      for (final java.io.File element : fileList) {
        if (element.isDirectory()) {
          result.add(new MyDirectory(element));
        }
        if (element.isFile()) {
          result.add(new MyFile(element));
        }
      }
    }

    return result;
  }

  @Override
  public ArrayList<Message> getMessages() {
    ArrayList<Message> messageList = new ArrayList<Message>();
    FilenameFilter fileFilter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        if (name.toLowerCase().endsWith(".xml")) {
          // pruefen ob es nicht ein Ordner mit dem Name *.xml ist
          if (new File(dir.getAbsolutePath() + File.separator + name).isFile()) {
            return true;
          }
        }
        return false;
      }
    };
    for (final java.io.File element : this.file.listFiles(fileFilter)) {
      // XML File mit JAXB einlesen
      Message message = null;
      try {
        message = JAXB.unmarshal(element, Message.class);
      } catch (Exception e) {
        System.err.println(e.getMessage());
      }

      if (message != null && message.getId() != null) {
        // wenn message.getId() == null dann wohl falsches Format
        messageList.add(message);
      }
    }
    return messageList;
  }

}
