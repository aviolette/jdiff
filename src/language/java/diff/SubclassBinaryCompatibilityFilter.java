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

import language.java.ast.*;
import treediff.edit.*;
import tree.*;
import java.util.*;

/**
 * The <code>SubclassBinaryCompatibilityFilter</code> filters out
 * interface changes that do not affect the binary compatibility with
 * a class's subclasses.
 *
 * 
 *
 * @author Andrew Violette
 */

final class SubclassBinaryCompatibilityFilter
   extends DiffFilter
{



   private void filterOnVisibility(Map values, TreeNode node)
   {
	Object o = values.get("Visibility");

	if(o != null)
	{
	   try
	   {
		Visibility newVis  = Visibility.fromString(o.toString());
		Visibility original = Visibility.fromString(node.getAttribute("Visibility").toString());
		if( filterSubclassVisibilityRules(original, newVis))
		{
		   values.remove("Visibility");
		}
	   }
	   catch(InvalidAttributeException e)
	   {
		e.printStackTrace();
	   }
	}
	
   }
   
   /**
    * Moves do not affect binary compatibility with subclasses.  Thus,
    * this always returns <code>true</code>.
    */

   public boolean move(Move edit) 
   { 
	return true;
   }

   /**
    * Inserts of nodes do not affect binary compatibility with
    * subclasses.  Unless, it is an insert of a parameter into an
    * existing method.
    */

   public boolean insert(Insert edit) 
   { 
	// TODO: Check to make sure that a check is made for a parameter
	// that is inserted into an existing method.

	return true;
   }
   public boolean delete(Delete edit) 
   { 
	TreeNode node = edit.getNode();
	String label = node.getLabel();
	
	if(label.equals(ClassDeclaration.LABEL) || 
	   label.equals(MethodDeclaration.LABEL) ||
	   label.equals(InterfaceDeclaration.LABEL) ||
	   label.equals(FieldDeclaration.LABEL))
	{
	   try
	   {
		Object o  = node.getAttribute("Visibility");
		Visibility v = Visibility.fromString(o.toString());
		if(v == Visibility.PRIVATE)
		{
		   return true;
		}
	   }
	   catch(Exception e)
	   {
	   }
	   return false;
	}

	return true;
   }

   

   public boolean update(Update edit) 
   { 

	// The node is retrieved by ID since the edit script is
	// populated with nodes from the original tree (the tree that
	// is diffed).  This tree already has the modifications made
	// to it, and thus cannot be used for any comparisons.

	TreeNode node = getRoot().find(edit.getNode().getID());

	Map values = edit.values();
	String label = node.getLabel();

	if(label.equals(ClassDeclaration.LABEL) || 
	   label.equals(MethodDeclaration.LABEL) || 
	   label.equals(InterfaceDeclaration.LABEL) ||
	   label.equals(FieldDeclaration.LABEL))
	{
	   filterOnBoolean(values, "Final", false);
	   filterOnBoolean(values, "Abstract", false);
	   filterOnVisibility(values, node);
	}
	else if(label.equals(ConstructorDeclaration.LABEL))
	{
	   filterOnVisibility(values, node);
	}
	// If there are no more properties to update on the node, then
	// it is OK to delete the update operation from the script

	return edit.values().size() == 0;
   }

   /**
    * Returns true if the new visibility is at a higher or equal
    * level of visibility than the original.  If it is, then the new
    * visibility does not affect any subclass' useage of the method.  
    *
    */

   private boolean filterSubclassVisibilityRules(Visibility original, Visibility newVis)
   {
	if(original.equals(newVis)) return true;

	// move this to the Visibility class.

	return (((newVis == Visibility.DEFAULT || newVis == Visibility.PROTECTED || 
		    newVis == Visibility.PUBLIC) &&
		   original == Visibility.PRIVATE) || 
		  ((original == Visibility.DEFAULT) && (newVis == Visibility.PUBLIC ||
								    newVis == Visibility.PROTECTED)) ||
		  (original == Visibility.PROTECTED && newVis == Visibility.PUBLIC));

   }
}
