package de.bht.fpa.mail.s798158.filter;

import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;

public class Main {

  public static void main(String[] args) {
    final int messageCount = 50;

    System.out.println("Testtool für die Filter");

    RandomTestDataProvider testDataProvider = new RandomTestDataProvider(messageCount);
    final List<Message> messages = testDataProvider.getMessages();

    // final Set<Message> filteredMessages = new Sender("schmidt_heidi@home.de",
    // FilterOperator.IS).filter(messages);
    // final Set<Message> filteredMessages = new Text("Who",
    // FilterOperator.CONTAINS).filter(messages);
    final Set<Message> filteredMessages = new Read(true).filter(messages);

    for (final Message message : filteredMessages) {
      System.out.println(message);
    }
    System.out.println("number of messages: " + filteredMessages.size());

  }

}
