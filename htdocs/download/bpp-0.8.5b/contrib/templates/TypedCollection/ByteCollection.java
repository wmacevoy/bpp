import java.util.*;

public class ByteCollection {
  public static class ByteCollectionIterator { 
    java.util.Iterator iterator;
    public ByteCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public byte next() { 
      return ((Byte) iterator.next()).byteValue();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public ByteCollection(Collection _collection) { collection=_collection; } 
  public boolean add(byte arg1) { return collection.add(new Byte(arg1)); }
  public boolean contains(byte arg1) { return collection.contains(new Byte(arg1)); }
  public boolean remove(byte arg1) { return collection.remove(new Byte(arg1)); }
  public ByteCollectionIterator iterator() { 
   return new ByteCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (ByteCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public byte[] toArray() { 
  Byte[] tmp = (Byte[])collection.toArray();
  byte[] ans = new byte[tmp.length];
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].byteValue();
  return ans;
}
  public byte[] toArray(byte[] arg1) { 
  Byte[] tmp = (Byte[])collection.toArray();
  byte[] ans = (arg1.length >= tmp.length) ? arg1 : new byte[tmp.length];
  for (int i=ans.length-1; i>=tmp.length; --i) ans[i]=((byte) 0);
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].byteValue();
  return ans;
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (ByteCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (ByteCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (ByteCollection arg1)  { return collection.retainAll(arg1.collection); }

}
