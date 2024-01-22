import java.util.*;

public class FloatCollection {
  public static class FloatCollectionIterator { 
    java.util.Iterator iterator;
    public FloatCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public float next() { 
      return ((Float) iterator.next()).floatValue();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public FloatCollection(Collection _collection) { collection=_collection; } 
  public boolean add(float arg1) { return collection.add(new Float(arg1)); }
  public boolean contains(float arg1) { return collection.contains(new Float(arg1)); }
  public boolean remove(float arg1) { return collection.remove(new Float(arg1)); }
  public FloatCollectionIterator iterator() { 
   return new FloatCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (FloatCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public float[] toArray() { 
  Float[] tmp = (Float[])collection.toArray();
  float[] ans = new float[tmp.length];
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].floatValue();
  return ans;
}
  public float[] toArray(float[] arg1) { 
  Float[] tmp = (Float[])collection.toArray();
  float[] ans = (arg1.length >= tmp.length) ? arg1 : new float[tmp.length];
  for (int i=ans.length-1; i>=tmp.length; --i) ans[i]=0.0f;
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].floatValue();
  return ans;
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (FloatCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (FloatCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (FloatCollection arg1)  { return collection.retainAll(arg1.collection); }

}
