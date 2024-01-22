import java.util.*;

public class IntCollection {
  public static class IntCollectionIterator { 
    java.util.Iterator iterator;
    public IntCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public int next() { 
      return ((Integer) iterator.next()).intValue();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public IntCollection(Collection _collection) { collection=_collection; } 
  public boolean add(int arg1) { return collection.add(new Integer(arg1)); }
  public boolean contains(int arg1) { return collection.contains(new Integer(arg1)); }
  public boolean remove(int arg1) { return collection.remove(new Integer(arg1)); }
  public IntCollectionIterator iterator() { 
   return new IntCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (IntCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public int[] toArray() { 
  Integer[] tmp = (Integer[])collection.toArray();
  int[] ans = new int[tmp.length];
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].intValue();
  return ans;
}
  public int[] toArray(int[] arg1) { 
  Integer[] tmp = (Integer[])collection.toArray();
  int[] ans = (arg1.length >= tmp.length) ? arg1 : new int[tmp.length];
  for (int i=ans.length-1; i>=tmp.length; --i) ans[i]=0;
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].intValue();
  return ans;
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (IntCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (IntCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (IntCollection arg1)  { return collection.retainAll(arg1.collection); }

}
