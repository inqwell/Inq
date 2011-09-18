/**
 * Copyright (C) 2011 Inqwell Ltd
 *
 * You may distribute under the terms of the Artistic License, as specified in
 * the README file.
 */

/*
 * $Archive: /src/com/inqwell/any/client/AnyRadio.java $
 * $Author: sanderst $
 * $Revision: 1.4 $
 * $Date: 2011-05-07 22:03:22 $
 */

package com.inqwell.any.client;

import java.awt.Container;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import com.inqwell.any.AbstractComposite;
import com.inqwell.any.Any;
import com.inqwell.any.AnyException;
import com.inqwell.any.AnyNull;
import com.inqwell.any.AnyRuntimeException;
import com.inqwell.any.AnyString;
import com.inqwell.any.Array;
import com.inqwell.any.ConstString;
import com.inqwell.any.Descriptor;
import com.inqwell.any.Event;
import com.inqwell.any.Func;
import com.inqwell.any.Map;
import com.inqwell.any.RuntimeContainedException;
import com.inqwell.any.Set;
import com.inqwell.any.Transaction;
import com.inqwell.any.beans.Facade;

// TODO: rebase away from AnyComponent...

public class AnyRadio extends AnyToggleButton
{
  private AbstractButton b_;
  private AnyButtonGroup g_;
  
	private static Set     radioProperties__;
	private static Any     buttonGroup__ = new ConstString("buttonGroup");

  static
  {
  	radioProperties__ = AbstractComposite.set();
  	radioProperties__.add(buttonGroup__);
  }
  
	public void setObject(Object o)
	{
		if (!(o instanceof AbstractButton))
			throw new IllegalArgumentException
									("AnyRadio wraps javax.swing.AbstractButton and sub-classes");


    b_ = (AbstractButton)o;
    
    Facade f = getParentComponent();
    if (f instanceof AnyButtonGroup)
      g_ = (AnyButtonGroup)f;
      

		super.setObject(b_);
		
		// Put an action listener on the button so we can notify
		// our parent AnyButtonGroup.
    addAdaptedEventListener(new ModelUpdateListener(actionEventType__));
	}
	
  public Container getComponent()
  {
    return b_;
  }

  public void setRenderInfo(RenderInfo r)
  {
    if (r != null && !isRenderer())
    {
      // Radios set their text to either their enum external value or
      // their label, with the priority on the former
      if (!setTextFromButtonGroup(r))
      {
        String s = r.getLabel();
        if (s.equals(AnyString.EMPTY.toString()))
          s = null;
        b_.setText(s);
      }
    }
    super.setRenderInfo(r);
  }

	
	public void setButtonGroup(Any g)
	{
    if (g_ != null)
    {
      ButtonGroup bg = g_.getButtonGroup();
      bg.remove(b_);
      g_.removeButton(this);
    }
    
    g_ = null;
    
    if (!AnyNull.isNull(g))
    {
      if (!(g instanceof AnyButtonGroup))
        throw new AnyRuntimeException("Not a button group");
      
  		g_ = (AnyButtonGroup)g;
  		
		  g_.getButtonGroup().add(b_);
      g_.addButton(this);
      setTextFromButtonGroup(getRenderInfo());
    }
	}
	
	public Any getButtonGroup()
	{
		return g_;
	}
	
	Any getRadioData() throws AnyException
	{
    Any ret = null;
    RenderInfo r = getRenderInfo();
    if (r != null)
    {
      // Get the value we represent
      ret = r.resolveDataNode(getContextNode(), true);
    }
    return ret;
	}
	
  protected void setValueToComponent(Any v)
  {
  }

  protected Object getPropertyOwner(Any property)
	{
		if (radioProperties__.contains(property))
		  return this;
		
		return super.getPropertyOwner(property);
	}
	
	ButtonModel getButtonModel()
	{
    return b_.getModel();
  }
	
	private void notifyButtonGroup()
	{
    try
    {
      Any dataItem = null;
      RenderInfo r = getRenderInfo();
      if (r != null)
      {
        // Get the value we represent
        dataItem = r.resolveDataNode(getContextNode(), true);
      }
      if (g_ != null)
        g_.radioChanged(dataItem);
    }
    catch(AnyException e)
    {
      throw new RuntimeContainedException(e);
    }
	}
	
	// Check if the button group is rendering an enum and if so
	// whether any value our renderinfo represents is part of
	// that enum. If so then set our text property from to the
	// enum's external value and return true.
	private boolean setTextFromButtonGroup(RenderInfo myRenderInfo)
	{
	  boolean ret = false;

	  RenderInfo r = null;
	  if (g_ != null)
	    r = g_.getRenderInfo();
	  
	  if (r != null)
	  {
  	  Descriptor d = r.getDescriptor();
      if (d != Descriptor.degenerateDescriptor__ && d != null)
      {
        Any        f = r.getField();
        
        if (d.isEnum(f) && myRenderInfo != null)
        {
          try
          {
            Any v = myRenderInfo.resolveResponsibleData(getContextNode());
            Map enums = d.getEnums();
            enums = (Map)enums.get(f);
            
            if (enums.contains(v))
            {
              v = enums.get(v);
              b_.setText(v.toString());
              ret = true;
            }
          }
          catch(AnyException e)
          {
            throw new RuntimeContainedException(e);
          }
        }
      }
    }
	  
	  return ret;
	}
  
  private class ModelUpdateListener extends EventBinding
  {
    public ModelUpdateListener(Array eventTypes)
    {
      super(eventTypes, false);
    }

    protected Any execExpr(Transaction t, Any context, Func expr, Event e) throws AnyException
    {
      notifyButtonGroup();
      return null;
    }
  }
}

