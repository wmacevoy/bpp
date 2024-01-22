import java.util.*;

public class WrappedIntCollection {
  public static class WrappedIntCollectionIterator { 
    java.util.Iterator iterator;
    public WrappedIntCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public Integer next() { 
      return (Integer) iterator.next();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public WrappedIntCollection(Collection _collection) { collection=_collection; } 
  public boolean add(Integer arg1) { return collection.add(arg1); }
  public boolean contains(Integer arg1) { return collection.contains(arg1); }
  public boolean remove(Integer arg1) { return collection.remove(arg1); }
  public WrappedIntCollectionIterator iterator() { 
   return new WrappedIntCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (WrappedIntCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public Integer[] toArray() { 
  return (Integer[])collection.toArray(); 
}
  public Integer[] toArray(Integer[] arg1) { 
  return (Integer[])collection.toArray(); 
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (WrappedIntCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (WrappedIntCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (WrappedIntCollection arg1)  { return collection.retainAll(arg1.collection); }

}
