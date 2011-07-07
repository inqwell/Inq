/**
 * Copyright (C) 2011 Inqwell Ltd
 *
 * You may distribute under the terms of the Artistic License, as specified in
 * the README file.
 */

/*
 * $Archive: /src/com/inqwell/any/channel/ChannelClosedException.java $
 * $Author: sanderst $
 * $Revision: 1.2 $
 * $Date: 2011-04-07 22:18:22 $
 */

package com.inqwell.any.channel;

import com.inqwell.any.AnyException;

/**
 * An exception that is thrown to indicate that a channel has
 * been closed and may no longer be read from.
 * @author $Author: sanderst $
 * @version $Revision: 1.2 $
 * @see com.inqwell.any.Any
 */ 
public class ChannelClosedException extends AnyException
{
	public ChannelClosedException() {}
	public ChannelClosedException(String s) { super(s); }
	
	public Object clone() throws CloneNotSupportedException { return super.clone(); }  
}
