import java.util.*;

public class ShortCollection {
  public static class ShortCollectionIterator { 
    java.util.Iterator iterator;
    public ShortCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public short next() { 
      return ((Short) iterator.next()).shortValue();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public ShortCollection(Collection _collection) { collection=_collection; } 
  public boolean add(short arg1) { return collection.add(new Short(arg1)); }
  public boolean contains(short arg1) { return collection.contains(new Short(arg1)); }
  public boolean remove(short arg1) { return collection.remove(new Short(arg1)); }
  public ShortCollectionIterator iterator() { 
   return new ShortCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (ShortCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public short[] toArray() { 
  Short[] tmp = (Short[])collection.toArray();
  short[] ans = new short[tmp.length];
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].shortValue();
  return ans;
}
  public short[] toArray(short[] arg1) { 
  Short[] tmp = (Short[])collection.toArray();
  short[] ans = (arg1.length >= tmp.length) ? arg1 : new short[tmp.length];
  for (int i=ans.length-1; i>=tmp.length; --i) ans[i]=((short) 0);
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].shortValue();
  return ans;
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (ShortCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (ShortCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (ShortCollection arg1)  { return collection.retainAll(arg1.collection); }

}
