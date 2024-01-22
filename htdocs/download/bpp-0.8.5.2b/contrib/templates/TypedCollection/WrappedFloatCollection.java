import java.util.*;

public class WrappedFloatCollection {
  public static class WrappedFloatCollectionIterator { 
    java.util.Iterator iterator;
    public WrappedFloatCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public Float next() { 
      return (Float) iterator.next();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public WrappedFloatCollection(Collection _collection) { collection=_collection; } 
  public boolean add(Float arg1) { return collection.add(arg1); }
  public boolean contains(Float arg1) { return collection.contains(arg1); }
  public boolean remove(Float arg1) { return collection.remove(arg1); }
  public WrappedFloatCollectionIterator iterator() { 
   return new WrappedFloatCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (WrappedFloatCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public Float[] toArray() { 
  return (Float[])collection.toArray(); 
}
  public Float[] toArray(Float[] arg1) { 
  return (Float[])collection.toArray(); 
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (WrappedFloatCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (WrappedFloatCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (WrappedFloatCollection arg1)  { return collection.retainAll(arg1.collection); }

}
