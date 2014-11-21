package treediff.edit;

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


import tree.*;
import java.util.HashMap;
import com.sun.xml.tree.XmlDocument;
import org.w3c.dom.*;


/**
 * The <code>Delete</code> operation represents the removal of a node
 * from a tree.  When a delete is applied, all other nodes that are
 * directly or indirectly children of the node lose their connection
 * to the tree.
 * @author Andrew Violette
 */

public class Delete
   extends Edit
{

   protected Delete()
   {
   }

   /**
    * Constructs a <code>Delete</code> object that deletes
    * <code>node</code> from its tree.
    */

   public Delete(TreeNode node)
   {
	setNode(node);
   }

   /**
    * Applies the delete operation.  If the node does not have a
    * parent, nothing happens.
    * @exception EditApplicationException if an error occurs removing
    * @return the node that was deleted
    * the object from the tree.  
    */

   TreeNode apply(EditListener listener)
	throws EditApplicationException
   {
	TreeNode parent = getNode().getParent();
	debug.Debug.assert(parent != null);
	parent.removeChild(getNode());
	if(listener != null) listener.deleteApplied(this);
	return getNode();

   }



   public void write(Element e, XmlDocument d)
	throws DOMException
   {
	Element del = d.createElement("delete");
	del.setAttribute("id", (new Integer(getNode().getID())).toString());
	e.appendChild(del);
   }
    
   /**
    * Loads the delete operation from an XML element.
    */

   protected void load(Element e, RootNode tree)
	throws EditParseException
   {
	setNode(extractNode("id", e, tree));

   }



}
