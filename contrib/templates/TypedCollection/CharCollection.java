import java.util.*;

public class CharCollection {
  public static class CharCollectionIterator { 
    java.util.Iterator iterator;
    public CharCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public char next() { 
      return ((Character) iterator.next()).charValue();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public CharCollection(Collection _collection) { collection=_collection; } 
  public boolean add(char arg1) { return collection.add(new Character(arg1)); }
  public boolean contains(char arg1) { return collection.contains(new Character(arg1)); }
  public boolean remove(char arg1) { return collection.remove(new Character(arg1)); }
  public CharCollectionIterator iterator() { 
   return new CharCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (CharCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public char[] toArray() { 
  Character[] tmp = (Character[])collection.toArray();
  char[] ans = new char[tmp.length];
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].charValue();
  return ans;
}
  public char[] toArray(char[] arg1) { 
  Character[] tmp = (Character[])collection.toArray();
  char[] ans = (arg1.length >= tmp.length) ? arg1 : new char[tmp.length];
  for (int i=ans.length-1; i>=tmp.length; --i) ans[i]='\0';
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].charValue();
  return ans;
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (CharCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (CharCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (CharCollection arg1)  { return collection.retainAll(arg1.collection); }

}
