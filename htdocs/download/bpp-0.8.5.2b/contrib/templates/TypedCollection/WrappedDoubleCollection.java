import java.util.*;

public class WrappedDoubleCollection {
  public static class WrappedDoubleCollectionIterator { 
    java.util.Iterator iterator;
    public WrappedDoubleCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public Double next() { 
      return (Double) iterator.next();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public WrappedDoubleCollection(Collection _collection) { collection=_collection; } 
  public boolean add(Double arg1) { return collection.add(arg1); }
  public boolean contains(Double arg1) { return collection.contains(arg1); }
  public boolean remove(Double arg1) { return collection.remove(arg1); }
  public WrappedDoubleCollectionIterator iterator() { 
   return new WrappedDoubleCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (WrappedDoubleCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public Double[] toArray() { 
  return (Double[])collection.toArray(); 
}
  public Double[] toArray(Double[] arg1) { 
  return (Double[])collection.toArray(); 
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (WrappedDoubleCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (WrappedDoubleCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (WrappedDoubleCollection arg1)  { return collection.retainAll(arg1.collection); }

}
