import java.util.*;

public class LongCollection {
  public static class LongCollectionIterator { 
    java.util.Iterator iterator;
    public LongCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public long next() { 
      return ((Long) iterator.next()).longValue();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public LongCollection(Collection _collection) { collection=_collection; } 
  public boolean add(long arg1) { return collection.add(new Long(arg1)); }
  public boolean contains(long arg1) { return collection.contains(new Long(arg1)); }
  public boolean remove(long arg1) { return collection.remove(new Long(arg1)); }
  public LongCollectionIterator iterator() { 
   return new LongCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (LongCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public long[] toArray() { 
  Long[] tmp = (Long[])collection.toArray();
  long[] ans = new long[tmp.length];
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].longValue();
  return ans;
}
  public long[] toArray(long[] arg1) { 
  Long[] tmp = (Long[])collection.toArray();
  long[] ans = (arg1.length >= tmp.length) ? arg1 : new long[tmp.length];
  for (int i=ans.length-1; i>=tmp.length; --i) ans[i]=0L;
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].longValue();
  return ans;
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (LongCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (LongCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (LongCollection arg1)  { return collection.retainAll(arg1.collection); }

}
