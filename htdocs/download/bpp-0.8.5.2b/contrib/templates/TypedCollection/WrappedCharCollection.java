import java.util.*;

public class WrappedCharCollection {
  public static class WrappedCharCollectionIterator { 
    java.util.Iterator iterator;
    public WrappedCharCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public Character next() { 
      return (Character) iterator.next();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public WrappedCharCollection(Collection _collection) { collection=_collection; } 
  public boolean add(Character arg1) { return collection.add(arg1); }
  public boolean contains(Character arg1) { return collection.contains(arg1); }
  public boolean remove(Character arg1) { return collection.remove(arg1); }
  public WrappedCharCollectionIterator iterator() { 
   return new WrappedCharCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (WrappedCharCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public Character[] toArray() { 
  return (Character[])collection.toArray(); 
}
  public Character[] toArray(Character[] arg1) { 
  return (Character[])collection.toArray(); 
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (WrappedCharCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (WrappedCharCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (WrappedCharCollection arg1)  { return collection.retainAll(arg1.collection); }

}
