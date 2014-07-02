package de.bht.fpa.mail.s798158.imapnavigation;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import static de.bht.fpa.mail.s000000.common.mail.model.builder.Builders.*;

public class DummyAccount {

  public Account getDummyAccount() {
    // @formatter:off
    return newAccountBuilder()
    .host("de.somewhere.com") .username("alice") .password("secret") .name("Alice-IMAP") .folder(
    newFolderBuilder()
    .fullName("INBOX")
    .builtMessages(new RandomTestDataProvider(20).getMessages()) .folder(
    newFolderBuilder()
    .fullName("Customers")
    .builtMessages(new RandomTestDataProvider(30).getMessages())
    ) )
    .folder( newFolderBuilder()
    .fullName("Sent")
    .builtMessages(new RandomTestDataProvider(5).getMessages()) )
    .build();
    // @formatter:on
  }

}
