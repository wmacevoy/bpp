import java.util.*;

public class WrappedLongCollection {
  public static class WrappedLongCollectionIterator { 
    java.util.Iterator iterator;
    public WrappedLongCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public Long next() { 
      return (Long) iterator.next();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public WrappedLongCollection(Collection _collection) { collection=_collection; } 
  public boolean add(Long arg1) { return collection.add(arg1); }
  public boolean contains(Long arg1) { return collection.contains(arg1); }
  public boolean remove(Long arg1) { return collection.remove(arg1); }
  public WrappedLongCollectionIterator iterator() { 
   return new WrappedLongCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (WrappedLongCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public Long[] toArray() { 
  return (Long[])collection.toArray(); 
}
  public Long[] toArray(Long[] arg1) { 
  return (Long[])collection.toArray(); 
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (WrappedLongCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (WrappedLongCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (WrappedLongCollection arg1)  { return collection.retainAll(arg1.collection); }

}
