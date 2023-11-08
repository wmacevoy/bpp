/**
   BPP: The Beanshell Preprocessor, version 0.4
   Copyright (C) 2003-2004  Warren D. MacEvoy jr.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package bpp;

import java.lang.reflect.*;
import java.util.*;

/** <p>This class simplifies the generation of java source code by the preprocessor.</p>

<br/>The Beanshell Preprocessor
<br/>Copyright (C) 2003 Warren MacEvoy
*/
public class Format {

  /** Maps primitive names to wrapped names.  For example,
      WRAPPERS.get("int")="Integer". */
  public static final Map WRAPPERS;
  static {
    Map map=new TreeMap();
    map.put("boolean","Boolean");
    map.put("byte","Byte");
    map.put("char","Character");
    map.put("short","Short");
    map.put("int","Integer");
    map.put("long","Long");
    map.put("float","Float");
    map.put("double","Double");
    WRAPPERS=map;
  }

  /** Maps wrapped name to primitive name.  For example,
      PRIMITIVES.get("Integer")="int". */

  public static final Map PRIMITIVES;
  static {
    Map map=new TreeMap();
    map.put("Boolean","boolean");
    map.put("Byte","byte");
    map.put("Character","char");
    map.put("Short","short");
    map.put("Integer","int");
    map.put("Long","long");
    map.put("Float","float");
    map.put("Double","double");
    PRIMITIVES=map;
  }

  /**
     <p>Default initial values for primitives. It
     goes from the name of the primitive to a wrapped version of the
     default value for that primitive type.  The specific entries are:
     <ul><table border=1>
     <tr><td><b>key</b></td><td><b>value</b></td></tr>
     <tr><td>"boolean"</td><td>Boolean(false)</td></tr>
     <tr><td>"byte"</td><td>Byte(0)</td></tr>
     <tr><td>"char"</td><td>Character('\0')</td></tr>
     <tr><td>"short"</td><td>Short(0)</td></tr>
     <tr><td>"int"</td><td>Integer(0)</td></tr>
     <tr><td>"long"</td><td>Long(0L)</td></tr>
     <tr><td>"float"</td><td>Float(0E0f)</td></tr>
     <tr><td>"double"</td><td>Double(0E0)</td></tr>
     </table></ul>
     </p>
  */
  public static final Map DEFAULTS;
  static {
    Map map=new TreeMap();
    map.put("boolean",new Boolean(false));
    map.put("byte",new Byte((byte)0));
    map.put("char",new Character('\0'));
    map.put("short",new Short((short)0));
    map.put("int",new Integer(0));
    map.put("long",new Long(0L));
    map.put("float",new Float(0.0f));
    map.put("double",new Double(0.0));
    DEFAULTS=map;
  }

  public static boolean isPrimitive(String type) {
    return getWrapper(type) != null;
  }

  /** <p></p>

  Long (java.lang.Integer) or short (Integer) names are ok. */
  public static boolean isWrapper(String type) {
    return getPrimitive(type) != null;
  }

  /** getWrapper("char")="Character".  If the type argument is not a
      primitive type name, like "int" or "char", getWrapper()
      returns null. */
  public static String getWrapper(String type) {
    return (String) WRAPPERS.get(type);
  }

  /** getPrimitive("Character")="char".  If the type argument is not a
      primitive type name, like "Boolean" or "java.lang.Character",
      getPrimitive() returns null. */
  public static String getPrimitive(String type) {
    if (type.startsWith("java.lang.") && type.indexOf('.',10)==-1) {
	    type=type.substring(10);
    }
    return (String) PRIMITIVES.get(type);
  }

  /**
     <p>Selectively wrap primitive types.</p>

     <p>For example, <tt>wrap("String","x")</tt> will produce "x", while
     <tt>wrap("boolean","x")</tt> will produce "new Boolean(x)".
     The type name must be the primitive type.
     </p>

  */
  public static String wrap(String type,String exp) {
    String wrapper = getWrapper(type);
    return wrapper == null ? exp :
	    "new " + wrapper + "(" + exp + ")";
  }

  /**
     <p>Selectively unwrap primitive types.</p>

     <p>For example, <tt>unwrap("String","x")</tt> will produce "x", while
     <tt>unwrap("boolean","x")</tt> will produce "x.booleanValue()".
     The type name must be the primitive type.
     </p>

  */
  public static String unwrap(String type,String exp) {
    return isPrimitive(type) ? exp + "." + type + "Value()" : exp;
  }

  /**
     <p>Get default value for any type (as a string).</p>

     <p>For example, <tt>defval("String")</tt> will produce "((String)null)".  All non-primitive types, including wrapper types, are treated in this way.  The primitive types produce the following values:
     <ul><table border=1>
     <tr><td><b>primitive</b></td><td><b>result</b></td></tr>
     <tr><td>"boolean"</td><td>"false"</td></tr>
     <tr><td>"byte"</td><td>"((byte) 0)"</td></tr>
     <tr><td>"char"</td><td>"'\\0'"</td></tr>
     <tr><td>"short"</td><td>"((short) 0)"</td></tr>
     <tr><td>"int"</td><td>"0"</td></tr>
     <tr><td>"long"</td><td>"0L"</td></tr>
     <tr><td>"float"</td><td>"0f"</td></tr>
     <tr><td>"double"</td><td>"0.0"</td></tr>
     </table></ul>
     To initialize a wrapper type, use <tt>wrap(primitive,defval(primitive))</tt>.
     </p>
  */
  public static String defval(String type) {
    //
    // This is way easier in beanshell!
    //

    Object value=DEFAULTS.get(type);
    if (value == null) {
	    return "(("+type+") null)";
    } else {
	    // invoke literal(value) for specific type...
	    try {
        Method m=Format.class.getMethod("literal",new Class[] { value.getClass() });
        return (String) m.invoke(null,new Object[] { value });
	    } catch (NoSuchMethodException e) {
        throw new RuntimeException(e.getMessage());
	    } catch (IllegalAccessException e) {
        throw new RuntimeException(e.getMessage());
	    } catch (IllegalArgumentException e) {
        throw new RuntimeException(e.getMessage());
	    } catch (InvocationTargetException e) {
        throw new RuntimeException(e.getMessage());
	    }
    }
  }

  /**
     <p>Get correct name of a class</p>

     <p>This is better than using the java.lang.Class.getName directly,
     because that method returns strange names for array types.</p>
  */
  public static String typename(Class c) {
    return c.isArray() ? typename(c.getComponentType())+"[]" : c.getName();
  }

  /** <p>Simple "typeof" operator for Objects</p>

  <p>Returns typename(o.getClass())</p>
  */
  public static String typeof(Object o) { return typename(o.getClass()); }

  /** <p>Simple "typeof" operator for boolean primitives.  Returns "boolean".</p> */
  public static String typeof(boolean x) { return "boolean"; }

  /** <p>Simple "typeof" operator for byte primitives. Returns "byte".</p> */
  public static String typeof(byte x) { return "byte"; }

  /** <p>Simple "typeof" operator for char primitives. Returns "char".</p> */
  public static String typeof(char x) { return "char"; }

  /** <p>Simple "typeof" operator for short primitives. Returns "short".</p> */
  public static String typeof(short x) { return "short"; }

  /** <p>Simple "typeof" operator for int primitives. Returns "int".</p> */
  public static String typeof(int x) { return "int"; }

  /** <p>Simple "typeof" operator for long primitives. Returns "long".</p> */
  public static String typeof(long x) { return "long"; }

  /** <p>Simple "typeof" operator for float primitives. Returns "float".</p> */
  public static String typeof(float x) { return "float"; }

  /** <p>Simple "typeof" operator for double primitives. Returns "double".</p> */
  public static String typeof(double x) { return "double"; }

  /** Join the toString() representations of some objects together. */
  public static String join(char separator,Object [] list) {
    return join(new Character(separator).toString(),list);
  }

  /** Join the toString() representations of some objects together. */
  public static String join(String separator,Object [] list) {
    StringBuffer ans=new StringBuffer();
    boolean first=true;
    if (list != null) for (int i=0; i<list.length; ++i) {
	    if (first) first=false;
	    else ans.append(separator);
	    ans.append(list[i].toString());
    }
    return ans.toString();
  }

  /** <p>Split a string up.</p>

  <p>After splitting by the given separator, each entry is trimmed (with String.trim()) if the trim argument is true.  For example:
  <pre>
  split(',',",1, 3, 4",false)={"","1"," 3"," 4"}
  </pre>
  while
  <pre>
  split(',',",1, 3, 4",true)={"","1","3","4"}
  </pre>
  </p>
  */
  public static String[] split(char separator, String joined,boolean trim) {
    return split(new Character(separator).toString(),joined,trim);
  }

  /** <p>Split a string up.</p>

  <p>Just like the char-separator version of split.</p>

  */
  public static String[] split(String separator, String joined,boolean trim) {
    List slist = new LinkedList();
    int at=0;
    while (at < joined.length()) {
	    int cut=joined.indexOf(separator,at);
	    if (cut < 0) cut=joined.length();
	    String part=joined.substring(at,cut);
	    if (trim) part=part.trim();
	    slist.add(part);
	    at=cut+separator.length();
    }
    String [] ans = new String[slist.size()];
    slist.toArray(ans);
    return ans;
  }

  /** <p>Left justify format in a width w using the given pad char.</p>

  <p>If s is longer than abs(w), it is <em>not</em> trimmed.  If w is negative, s is right justified instead.</p> */
  public static String left(String s,int w,char pad)
  {
    int absw= ( w > 0 ? w : -w);
    int slen=s.length();
    if (slen >= absw) return s;
    StringBuffer sb=new StringBuffer();
    if (w > 0) sb.append(s);
    for (int i=absw-slen; i>0; --i) sb.append(pad);
    if (w < 0) sb.append(s);
    return sb.toString();
  }

  /**  Left justify s in a width of w chars and a pad of ' '.  */
  public static String left(String s,int w) { return left(s,w,' '); }

  /** Right justify s in a width w with given pad char. */
  public static String right(String s,int w,char pad) { return left(s,-w,pad); }

  /** Right justify s with blanks for a pad character. */
  public static String right(String s,int w) { return left(s,-w,' '); }

  /** <p>Format wrapped number.</p>

  <p>f is the argument to the java.text.DecimalFormat used to format
  the number.  See that class for all that can be done</p>
  */
  public static String N(Number n,String f) {
    return new java.text.DecimalFormat(f).format(n);
  }

  /** <p>Format number.</p>

  <p>f is the argument to the java.text.DecimalFormat used to format
  the number.  See that class for all that can be done</p>
  */
  public static String N(double n,String f) { return N(new Double(n),f); }

  /** Format boolean as a java literal. */
  public static String literal(boolean x) { return x ? "true" : "false"; }

  /** Format Boolean as a java literal */
  public static String literal(Boolean x) {
    return literal(x.booleanValue());
  }

  /**

  <p>Format byte as a java literal</p>

  <p>Java does not really have a byte literal.  Instead, this method
  returns an explicitly casted integer literal, as in:
  <pre>
  literal((byte)3)="((byte) 3)"
  </pre>
  </p>
  */
  public static String literal(byte x) { return "((byte) "+Byte.toString(x)+")"; }

  /** Format Byte as a java literal */
  public static String literal(Byte x) {
    return literal(x.byteValue());
  }

  /**

  <p>Format short as a java literal.</p>

  <p>Java does not really have a short literal.  Instead, this method
  returns an explicitly casted integer literal, as in:
  <pre>
  literal((short) 3)="((short) 3)"
  </pre>
  </p>
  */

  public static String literal(short x) { return "((short) "+Short.toString(x)+")"; }

  /** Format Short as a java literal */
  public static String literal(Short x) {
    return literal(x.shortValue());
  }

  /** Format int as a java literal */
  public static String literal(int x) { return Integer.toString(x); }

  /** Format Integer as a java literal */
  public static String literal(Integer x) {
    return literal(x.intValue());
  }

  /** Format long as a java literal */
  public static String literal(long x) { return Long.toString(x)+"L"; }

  /** Format Long as a java literal */
  public static String literal(Long x) {
    return literal(x.longValue());
  }

  /** Format float as a java literal */
  public static String literal(float x) { return Float.toString(x)+"f"; }

  /** Format Float as a java literal */
  public static String literal(Float x) {
    return literal(x.floatValue());
  }

  /** Format double as a java literal */
  public static String literal(double x) {
    return Double.toString(x);
  }

  /** Format Double as a java literal */
  public static String literal(Double x) {
    return literal(x.doubleValue());
  }

  /** Format a single char into a StringBuffer as part of a char or String
      literal.  Normally only used as part of literal(char) and literal(String) */
  public static void encodeTo(char x,StringBuffer to) {
    switch(x) {
    case '\'': to.append("\\\'"); return;
    case '\"': to.append("\\\""); return;
    case '\\': to.append("\\\\"); return;
    case '\t': to.append("\\t"); return;
    case '\n': to.append("\\n"); return;
    case '\r': to.append("\\r"); return;
    case '\f': to.append("\\f"); return;
    }
    if (x >= 32 && x < 127) to.append(x);
    else if (x < 32 || (x >= 127 && x <= 255)) {
      if (x < 8) {
        to.append("\\00");
      } else if (x < 64) {
        to.append("\\0");
      } else {
        to.append("\\");
      }
      to.append(Integer.toOctalString(x));
    } else {
	    if (x < 0x1000) {
        to.append("\\u0");
	    } else {
        to.append("\\u");
	    }
	    to.append(Integer.toHexString(x));
    }
  }

  /** Format char as java literal */
  public static String literal(char x) {
    StringBuffer ans=new StringBuffer(4).append('\'');
    encodeTo(x,ans);
    ans.append('\'');
    return ans.toString();
  }

  /** Format Character as java literal */
  public static String literal(Character x) {
    return literal(x.charValue());
  }

  /** Format String as java literal */
  public static String literal(String s) {
    StringBuffer ans=new StringBuffer(2+(s.length()*5)/4).append('\"');
    for (int i=0; i<s.length(); ++i) encodeTo(s.charAt(i),ans);
    ans.append('\"');
    return ans.toString();
  }
}
