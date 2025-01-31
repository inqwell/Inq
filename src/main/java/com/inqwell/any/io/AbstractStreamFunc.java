/**
 * Copyright (C) 2011 Inqwell Ltd
 *
 * You may distribute under the terms of the Artistic License, as specified in
 * the README file.
 */

/* 
 * $Archive: /src/com/inqwell/any/io/AbstractStreamFunc.java $
 * $Author: sanderst $
 * $Revision: 1.2 $
 * $Date: 2011-04-07 22:18:22 $
 */

package com.inqwell.any.io;

import com.inqwell.any.Any;
import com.inqwell.any.Func;
import com.inqwell.any.AbstractFunc;
import com.inqwell.any.AnyException;

/**
 * A function whose purpose is to optionally replace the object being
 * streamed.  Provides the stream instance as well so that, even within
 * a given action, the replacement can be varied.
 */
public abstract class AbstractStreamFunc extends    AbstractFunc
                                         implements StreamFunc
{
  public Any exec(Any a) throws AnyException
  {
    throw new UnsupportedOperationException("Must implement only exec(a, s)");
  }
}
