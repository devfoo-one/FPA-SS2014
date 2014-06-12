package de.bht.fpa.mail.s798158.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;

public class RecipientsFilter implements IFilter {

  private final String filterText;
  private final FilterOperator filterMode;

  public RecipientsFilter(final String filterText, final FilterOperator filterMode) {
    if (filterText == null || filterText.equals("")) {
      throw new IllegalArgumentException("could not create filter. filterString is empty.");
    }
    if (filterMode == null) {
      throw new IllegalArgumentException("could not create filter. filterMode is null.");
    }
    this.filterText = filterText;
    this.filterMode = filterMode;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    if (messagesToFilter == null) {
      return null;
    }
    final Set<Message> returnSet = new HashSet<Message>();
    for (final Message m : messagesToFilter) {
      for (final Recipient recipient : m.getRecipients()) {
        if (StringCompareHelper.matches(recipient.getEmail(), this.filterText, this.filterMode)
            || StringCompareHelper.matches(recipient.getPersonal(), this.filterText, this.filterMode)) {
          returnSet.add(m);
        }
      }
    }
    return returnSet;
  }
}
