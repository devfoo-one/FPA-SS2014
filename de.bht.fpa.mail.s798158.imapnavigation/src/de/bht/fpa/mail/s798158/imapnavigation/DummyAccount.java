package de.bht.fpa.mail.s798158.imapnavigation;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import static de.bht.fpa.mail.s000000.common.mail.model.builder.Builders.newAccountBuilder;
import static de.bht.fpa.mail.s000000.common.mail.model.builder.Builders.newFolderBuilder;

public class DummyAccount {
  private static final int TWENTY = 20;
  private static final int THIRTY = 30;
  private static final int FIVE = 5;

  public Account getDummyAccount() {
    // @formatter:off
    return newAccountBuilder()
    .host("de.somewhere.com") .username("alice") .password("secret") .name("Alice-IMAP") .folder(
    newFolderBuilder()
    .fullName("INBOX")
    .builtMessages(new RandomTestDataProvider(TWENTY).getMessages()) .folder(
    newFolderBuilder()
    .fullName("Customers")
    .builtMessages(new RandomTestDataProvider(THIRTY).getMessages())
    ))
    .folder(newFolderBuilder()
    .fullName("Sent")
    .builtMessages(new RandomTestDataProvider(FIVE).getMessages()))
    .build();
    // @formatter:on
  }

}
