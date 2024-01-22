import java.util.*;

public class DoubleCollection {
  public static class DoubleCollectionIterator { 
    java.util.Iterator iterator;
    public DoubleCollectionIterator(java.util.Iterator _iterator) { iterator=_iterator; }
    public boolean hasNext() { return iterator.hasNext(); }
    public double next() { 
      return ((Double) iterator.next()).doubleValue();
    }
    public void remove() { iterator.remove(); }
  }
  protected java.util.Collection collection;
  public DoubleCollection(Collection _collection) { collection=_collection; } 
  public boolean add(double arg1) { return collection.add(new Double(arg1)); }
  public boolean contains(double arg1) { return collection.contains(new Double(arg1)); }
  public boolean remove(double arg1) { return collection.remove(new Double(arg1)); }
  public DoubleCollectionIterator iterator() { 
   return new DoubleCollectionIterator(collection.iterator()); 
  }
  public int hashCode ()  { return collection.hashCode(); }
  public boolean equals (java.lang.Object arg1)  {
   return collection.equals(arg1); 
  }
  public boolean addAll (DoubleCollection arg1)  { 
    return collection.addAll(arg1.collection);
  }
  public int size ()  { return collection.size(); }
  public double[] toArray() { 
  Double[] tmp = (Double[])collection.toArray();
  double[] ans = new double[tmp.length];
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].doubleValue();
  return ans;
}
  public double[] toArray(double[] arg1) { 
  Double[] tmp = (Double[])collection.toArray();
  double[] ans = (arg1.length >= tmp.length) ? arg1 : new double[tmp.length];
  for (int i=ans.length-1; i>=tmp.length; --i) ans[i]=0.0;
  for (int i=tmp.length-1; i>=0; --i) ans[i]=tmp[i].doubleValue();
  return ans;
  }
  public void clear ()  { collection.clear(); }
  public boolean isEmpty ()  { return collection.isEmpty(); }
  public boolean containsAll (DoubleCollection arg1)  { return collection.containsAll(arg1.collection); }

  public boolean removeAll (DoubleCollection arg1)  { return collection.removeAll(arg1.collection); }

  public boolean retainAll (DoubleCollection arg1)  { return collection.retainAll(arg1.collection); }

}
