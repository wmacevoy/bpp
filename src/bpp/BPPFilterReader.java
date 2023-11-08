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

import java.io.*;


/** 

<p>Implements a Beanshell preprocessor as a java.io.FilterReader.</p>

<hr/>
<br/>The Beanshell Preprocessor
<br/>Copyright (C) 2003-2004 Warren D. MacEvoy jr.

*/
public class BPPFilterReader extends bpp.FilterReader {
  public BPPFilterReader(Reader _in) {
    this(_in,BPPFilter.DEFAULT_MAX_LINE_LENGTH);
  }

  public BPPFilterReader(Reader _in, int _maxLineLength) {
    super(new BPPFilter(_maxLineLength),_in);
  }

  public void setSource(String _source) {
    ((BPPFilter)filter).setSource(_source);
  }

  public void setMinStage(int _minStage) {
    ((BPPFilter)filter).setMinStage(_minStage);
  }
}
