import java.util.*;

public class WrappedBooleanCollection {
  public static class WrappedBooleanCollectionIterator { 
    java.util.Iterator iterator;
    public WrappedBooleanCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public Boolean next() { 
      return (Boolean) iterator.next();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public WrappedBooleanCollection(Collection _collection) { collection=_collection; } 
  public boolean add(Boolean arg1) { return collection.add(arg1); }
  public boolean contains(Boolean arg1) { return collection.contains(arg1); }
  public boolean remove(Boolean arg1) { return collection.remove(arg1); }
  public WrappedBooleanCollectionIterator iterator() { 
   return new WrappedBooleanCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (WrappedBooleanCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public Boolean[] toArray() { 
  return (Boolean[])collection.toArray(); 
}
  public Boolean[] toArray(Boolean[] arg1) { 
  return (Boolean[])collection.toArray(); 
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (WrappedBooleanCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (WrappedBooleanCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (WrappedBooleanCollection arg1)  { return collection.retainAll(arg1.collection); }

}
