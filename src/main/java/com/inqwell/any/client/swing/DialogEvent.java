/**
 * Copyright (C) 2011 Inqwell Ltd
 *
 * You may distribute under the terms of the Artistic License, as specified in
 * the README file.
 */

/*
 * $Archive: /src/com/inqwell/any/client/swing/DialogEvent.java $
 * $Author: sanderst $
 * $Revision: 1.2 $
 * $Date: 2011-04-07 22:18:22 $
 */
package com.inqwell.any.client.swing;

import java.awt.event.ActionEvent;
/**
 *
 */
public class DialogEvent extends ActionEvent
{
	public DialogEvent(Object source, int id, String command)
	{
		super(source, id, command);
	}

	public DialogEvent(Object source, int id, String command, int modifiers)
	{
		super(source, id, command, modifiers);
	}
}
