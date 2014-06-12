package de.bht.fpa.mail.s798158.filter;

import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;

public class Main {

  protected Main() {
  };

  public static void main(String[] args) {
    final int messageCount = 50;

    System.out.println("Testtool für die Filter");

    RandomTestDataProvider testDataProvider = new RandomTestDataProvider(messageCount);
    final List<Message> messages = testDataProvider.getMessages();

    System.out.println("========== Read(false) ==========");
    final Set<Message> filteredMessages1 = new ReadFilter(false).filter(messages);
    for (final Message message : filteredMessages1) {
      System.out.println(message);
    }

    System.out
        .println("\r\n========== Union(Sender(\"me@this.com\",contains),Recipient(\"foo@bar.de\",is)) ==========");
    final Set<Message> filteredMessages2 = new UnionFilter(new SenderFilter("me@this.com", FilterOperator.CONTAINS),
        new RecipientsFilter("foo@bar.de", FilterOperator.IS)).filter(messages);
    // final Set<Message> filteredMessages2 = new UnionFilter(new
    // SenderFilter("hotmail", FilterOperator.CONTAINS),
    // new RecipientsFilter("stulle_heidi@home.de",
    // FilterOperator.IS)).filter(messages);

    for (final Message message : filteredMessages2) {
      System.out.println(message);
    }

    System.out.println("\r\n========== Intersection(Sender(\"mueller\", startsWith), Read(true)) ==========");
    final Set<Message> filteredMessages3 = new IntersectionFilter(new SenderFilter("mueller",
        FilterOperator.STARTS_WITH), new ReadFilter(true)).filter(messages);
    // final Set<Message> filteredMessages3 = new IntersectionFilter(new
    // SenderFilter("karl", FilterOperator.STARTS_WITH),
    // new ReadFilter(true)).filter(messages);
    for (final Message message : filteredMessages3) {
      System.out.println(message);
    }

  }
}
