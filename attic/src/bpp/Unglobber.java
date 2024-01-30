package bpp;

import java.io.*;
import java.util.*;

/** 

<p>Convenience class to unglob file names with the following conventions:

<table border=2>
<th><td>form</td><td>function</td></th>
<tr><td>-</td><td>1 parent directory (like ..)</td></tr>
<tr><td>--</td><td>0 or more parent directories</td></tr>
<tr><td>+</td><td>1 child directory</td></tr>
<tr><td>++</td><td>0 or more child directories</tr>
<tr><td><i>name</i></td><td><i>name</i>, with * wildcard supported (matching any character except a directory separator</td></tr>
<tr><td>?regexp</td><td>Matches the given regular expression</td><tr>
</table>

</p>

<p>Examples:
<ul>
<li><code>--/classes</code> --- all files named <code>classes</code> in this or a parent directory.</li>
<li><code>--/java/lib/++/*.jar</code> --- would match all of the following:
<code>./java/lib/local/myjars/jar1.jar</code>, <code>../java/lib/jar2.jar</code> and <code>../../java/lib/ext/j.jar</code>.</li>
<li><code>--/java/?(doc|docs)/++/?.*\.(htm|html)</code> all the <code>.htm</code> and <code>.html</code> files in a subfolder of <code>java/doc</code> or <code>java/docs</code> in this or a parent directroy.</li>
</ul>
</p>

<p>WARNING: Using ++ after -- will result in an infinite recursion.</p>

*/

public class Unglobber {

  private static class REFilenameFilter implements FilenameFilter {
    java.util.regex.Pattern pattern;
    String re;
    REFilenameFilter(String re) { this(java.util.regex.Pattern.compile(re)); }
    REFilenameFilter(java.util.regex.Pattern _pattern) { pattern=_pattern; }
    public boolean accept(File dir,String file) {
      return pattern.matcher(file).matches();
    }
  }

  private static class DirectoriesOnlyFilenameFilter implements FilenameFilter {
    public boolean accept(File dir, String file) {
      return 
        !file.equals(".") 
        && !file.equals("..") 
        && new File(dir,file).isDirectory();
    }
  }
  private static final DirectoriesOnlyFilenameFilter DIRECTORIES_ONLY
    = new DirectoriesOnlyFilenameFilter();

  private abstract static class Pattern {
    Pattern[] patterns;
    int index;
    public abstract void apply(LinkedList path);
    protected void applyNext(LinkedList path) {
      patterns[index+1].apply(path);
    }
  }

  private static class PatternAdd extends Pattern {
    public String separator;
    public Collection collection;
    PatternAdd() { this(new ArrayList()); }
    PatternAdd(Collection _collection) { 
      this(_collection,System.getProperty("file.separator"));
    }
    PatternAdd(Collection _collection, String _separator) { 
      collection=_collection; 
      separator=_separator;
    }
    public void apply(LinkedList path) {
      collection.add(path.getLast());
    }
  }

  private static class PatternName extends Pattern {
    String name;
    PatternName(String _name) { name=_name; }
    public void apply(LinkedList path) {
      File f = new File((File)path.getLast(),name);
      if (f.exists()) {
        path.addLast(f);
        applyNext(path);
        path.removeLast();
      }
    }
  }

  private static class PatternRE extends Pattern {
    FilenameFilter fnf;
    PatternRE(String re) { this(new REFilenameFilter(re)); }
    PatternRE(FilenameFilter _fnf) { fnf=_fnf; }
    public void apply(LinkedList path) {
      File[] files = ((File)path.getLast()).listFiles(fnf);
      for (int i=0; i<files.length; ++i) {
        path.addLast(files[i]);
        applyNext(path);
        path.removeLast();
      }
    }
  }

  private static class PatternPlus extends Pattern {
    public void apply(LinkedList path) {
      File root = (File)path.getLast();
      if (!root.isDirectory()) return;
      File[] subs = root.listFiles(DIRECTORIES_ONLY);
      for (int i=0; i<subs.length; ++i) {
        path.addLast(subs[i]);
        applyNext(path);
        path.removeLast();
      }
    }
  }

  private static class PatternPlusPlus extends Pattern {
    public void apply(LinkedList path) {
      File root = (File)path.getLast();
      if (!root.isDirectory()) return;
      applyNext(path);
      File[] subs = root.listFiles(DIRECTORIES_ONLY);
      for (int i=0; i<subs.length; ++i) {
        path.addLast(subs[i]);
        apply(path);
        path.removeLast();
      }
    }
  }

  private static class PatternMinus extends Pattern {
    public void apply(LinkedList path) {
      File root = (File)path.getLast();
      if (!root.isDirectory()) return;
      File down = new File(root,"..");
      try {
        if (down.isDirectory() && ! down.getCanonicalPath().equals(root.getCanonicalPath())) {
          path.addLast(down);
          applyNext(path);
          path.removeLast();
        }
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
    }
  }

  private static class PatternMinusMinus extends Pattern {
    public void apply(LinkedList path) {
      File root = (File)path.getLast();
      if (!root.isDirectory()) return;
      applyNext(path);
      File down = new File(root,"..");
      try {
        if (down.isDirectory() && ! down.getCanonicalPath().equals(root.getCanonicalPath())) {
          path.addLast(down);
          apply(path);
          path.removeLast();
        }
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
    }
  }
    
    
    private Pattern [] patterns;

  /** Construct an unglobber based on a string pattern which is split on the file.separator system property value. */    
    public Unglobber(String pattern) { this(bpp.Format.split(System.getProperty("file.separator"),pattern,false)); }

  /** Construct an unglobber already split into parts. */
  public Unglobber(String[] spattern) {
      ArrayList lpattern = new ArrayList();
      for (int i=0; i<spattern.length; ++i) {
        String ipattern = spattern[i];
        if (ipattern.equals("++")) {
          lpattern.add(new PatternPlusPlus());
        } else if (ipattern.equals("--")) {
          lpattern.add(new PatternMinusMinus());
        } else if (ipattern.equals("+")) {
          lpattern.add(new PatternPlus());
        } else if (ipattern.equals("-")) {
          lpattern.add(new PatternMinus());
        } else if (ipattern.length() > 0) {
          if (ipattern.charAt(0) == '?') {
            lpattern.add(new PatternRE(ipattern.substring(1)));
          } else {
            if (ipattern.indexOf('*')==-1) {
              lpattern.add(new PatternName(ipattern));
            } else {
              ipattern = ipattern.replaceAll("\\.","\\.");
              ipattern = ipattern.replaceAll("\\*",".*");
              lpattern.add(new PatternRE(ipattern));
            }
          }
        } else {
          lpattern.add(new PatternName("/"));
        }
      }
      lpattern.add(new PatternAdd());
      patterns = new Pattern[lpattern.size()];
      lpattern.toArray(patterns);
      for (int i=0; i<patterns.length; ++i) {
        patterns[i].index=i;
        patterns[i].patterns=patterns;
      }
    }

  /** Unglob a file set based on the given root file */
  public File[] unglob(File root) {
    ((PatternAdd) patterns[patterns.length-1]).collection.clear();
    LinkedList path = new LinkedList();
    path.add(root);
    patterns[0].apply(path);
    Collection c = ((PatternAdd) patterns[patterns.length-1]).collection;
    File[] ans = new File[c.size()];
    c.toArray(ans);
    return ans;
  }

  /** Unglob a file set based on the current working directory. */
  public File[] unglob() { return unglob(new File(".")); }

  /** Test the unglobber: unglob given args in the current working directory. */
  public static void main(String [] args) {
    for (int i=0; i<args.length; ++i) {
      Unglobber ug = new Unglobber(args[i]);
      System.out.println("unglob: " + args[i]);
      File[] ans = ug.unglob();
      for (int j=0; j<ans.length; ++j) {
        System.out.println(ans[j]);
      }
    }
  }
}
