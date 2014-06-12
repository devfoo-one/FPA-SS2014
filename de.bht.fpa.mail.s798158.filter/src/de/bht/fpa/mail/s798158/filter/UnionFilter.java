package de.bht.fpa.mail.s798158.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class UnionFilter implements IFilter {

  private final List<IFilter> filter;

  public UnionFilter(List<IFilter> filter) {
    this.filter = filter;
  }

  public UnionFilter(IFilter... filters) {
    final List<IFilter> filterList = new ArrayList<IFilter>();
    for (IFilter f : filters) {
      filterList.add(f);
    }
    this.filter = filterList;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    if (messagesToFilter == null) {
      return null;
    }
    final Set<Message> returnSet = new HashSet<Message>();
    for (final IFilter f : filter) {
      returnSet.addAll(f.filter(messagesToFilter));
    }
    return returnSet;
  }

}
