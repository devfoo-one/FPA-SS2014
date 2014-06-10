package de.bht.fpa.mail.s798158.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class Read implements IFilter {

  private final boolean filter;

  public Read(final boolean filter) {
    this.filter = filter;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    if (messagesToFilter == null) {
      return null;
    }
    final Set<Message> returnSet = new HashSet<Message>();
    for (final Message m : messagesToFilter) {
      if (m.isRead() == filter) {
        returnSet.add(m);
      }
    }
    return returnSet;
  }
}
