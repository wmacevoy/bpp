import java.util.*;

public class BooleanCollection {
  public static class BooleanCollectionIterator { 
    java.util.Iterator iterator;
    public BooleanCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public boolean next() { 
      return ((Boolean) iterator.next()).booleanValue();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public BooleanCollection(Collection _collection) { collection=_collection; } 
  public boolean add(boolean arg1) { return collection.add(new Boolean(arg1)); }
  public boolean contains(boolean arg1) { return collection.contains(new Boolean(arg1)); }
  public boolean remove(boolean arg1) { return collection.remove(new Boolean(arg1)); }
  public BooleanCollectionIterator iterator() { 
   return new BooleanCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (BooleanCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public boolean[] toArray() { 
  Boolean[] tmp = (Boolean[])collection.toArray();
  boolean[] ans = new boolean[tmp.length];
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].booleanValue();
  return ans;
}
  public boolean[] toArray(boolean[] arg1) { 
  Boolean[] tmp = (Boolean[])collection.toArray();
  boolean[] ans = (arg1.length >= tmp.length) ? arg1 : new boolean[tmp.length];
  for (int i=ans.length-1; i>=tmp.length; --i) ans[i]=false;
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].booleanValue();
  return ans;
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (BooleanCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (BooleanCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (BooleanCollection arg1)  { return collection.retainAll(arg1.collection); }

}
