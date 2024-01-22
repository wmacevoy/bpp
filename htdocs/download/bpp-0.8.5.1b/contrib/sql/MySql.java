package db.mysql;


public interface DBObject 
{
  public addChangeListener(DBObjectListener listener);
  public removeChangeListener(DBObjectListener listener);
}

public interface DB extends DBObject {
  public Map getTables();
}

interface Table extends Collection {
  public DB getDB();
  public TableView getTableView();
  public Collection getCollection();
  public Row getRow();
  public Column getColumns();
}

interface Query {
  public String getSql();
  public Result execute();
}

interface Result {
  public boolean hasNext();
  public Row next();
}

interface Value {
  public Stri

}
class MySqlValue {
  Object value;
  

}
class MySqlInteger extends MySqlValue {
  
  Integer get() { return (Integer) getAsObject(); }
  void set(Integer _value) { setAsObject(_value); }
}
