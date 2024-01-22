import java.util.*;

public class WrappedByteCollection {
  public static class WrappedByteCollectionIterator { 
    java.util.Iterator iterator;
    public WrappedByteCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public Byte next() { 
      return (Byte) iterator.next();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public WrappedByteCollection(Collection _collection) { collection=_collection; } 
  public boolean add(Byte arg1) { return collection.add(arg1); }
  public boolean contains(Byte arg1) { return collection.contains(arg1); }
  public boolean remove(Byte arg1) { return collection.remove(arg1); }
  public WrappedByteCollectionIterator iterator() { 
   return new WrappedByteCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (WrappedByteCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public Byte[] toArray() { 
  return (Byte[])collection.toArray(); 
}
  public Byte[] toArray(Byte[] arg1) { 
  return (Byte[])collection.toArray(); 
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (WrappedByteCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (WrappedByteCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (WrappedByteCollection arg1)  { return collection.retainAll(arg1.collection); }

}
