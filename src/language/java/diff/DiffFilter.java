package language.java.diff;

/* 
 * Copyright (C) 2000  Andrew Violette
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA 
 *
 * Andrew Violette 
 * aviolette@acm.org
 *
 */


import treediff.edit.*;
import tree.*;
import java.util.*;

/**
 * The <code>DiffFilter</code> class is used to represent a filter
 * that is applied to a series of individual edit operations.
 */

public abstract class DiffFilter
{
   private RootNode m_root;

   /**
    * Returns the root node of the tree that the edit script refers to.
    */

   public final RootNode getRoot()
   {
	return m_root;
   }

   /**
    * Sets the root node of the tree that the edit script refers to.
    * This must be set before the filter is applied.  
    */

   public final void setRoot(RootNode node)
   {
	m_root = node;
   }

   protected void filterOnBoolean(Map values, String name, boolean value)
   {
	Object o = values.get(name);
	if(o != null)
	{
	   Boolean b = new Boolean(o.toString());
	   if(b.booleanValue() == value) values.remove(name);
	}
   }

   public DiffFilter()
   {
   }
    
   public abstract boolean move(Move edit);
   public abstract boolean insert(Insert edit);
   public abstract boolean delete(Delete delete);
   public abstract boolean update(Update update);
}
