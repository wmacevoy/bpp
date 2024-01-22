import java.util.*;

public class WrappedShortCollection {
  public static class WrappedShortCollectionIterator { 
    java.util.Iterator iterator;
    public WrappedShortCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public Short next() { 
      return (Short) iterator.next();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public WrappedShortCollection(Collection _collection) { collection=_collection; } 
  public boolean add(Short arg1) { return collection.add(arg1); }
  public boolean contains(Short arg1) { return collection.contains(arg1); }
  public boolean remove(Short arg1) { return collection.remove(arg1); }
  public WrappedShortCollectionIterator iterator() { 
   return new WrappedShortCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (WrappedShortCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public Short[] toArray() { 
  return (Short[])collection.toArray(); 
}
  public Short[] toArray(Short[] arg1) { 
  return (Short[])collection.toArray(); 
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (WrappedShortCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (WrappedShortCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (WrappedShortCollection arg1)  { return collection.retainAll(arg1.collection); }

}
