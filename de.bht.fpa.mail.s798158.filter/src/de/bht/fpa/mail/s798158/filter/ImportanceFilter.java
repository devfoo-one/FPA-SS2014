package de.bht.fpa.mail.s798158.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Importance;

public class ImportanceFilter implements IFilter {

  private final Importance filter;

  public ImportanceFilter(final Importance filter) {
    if (filter == null) {
      throw new IllegalArgumentException("could not create filter. Importance is null");
    }
    this.filter = filter;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    if (messagesToFilter == null) {
      return null;
    }
    final Set<Message> returnSet = new HashSet<Message>();
    for (final Message m : messagesToFilter) {
      if (m.getImportance() == filter) {
        returnSet.add(m);
      }
    }
    return returnSet;
  }
}
