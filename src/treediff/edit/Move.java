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


import com.sun.xml.tree.XmlDocument;
import org.w3c.dom.*;
import java.util.*;
import debug.*;
import tree.*;

/**
 * Moves a node from one location to another in a tree.
 */

public final class Move
   extends Edit
{

   private TreeNode m_parent;
   private int m_col;
   private int m_pos;
   private int m_parentID;
   private RootNode m_tree;

   protected Move()
   {
   }

   /**
    * Constructs a <code>Move</code> operation.
    * @param w the node that will be moved
    * @param parent the new parent of <code>w</code>
    * @param col the collection ID of the collection that
    * <code>w</code> will be placed in.
    * @param pos the position in the collection that the node will be
    * placed.  
    */

   public Move(TreeNode w, TreeNode parent, int col, int pos)
   {
	setNode(w);
	m_parent = parent;
	m_col = col;
	m_pos = pos;
   }

   public int getCollection()
   {
	return m_col;
   }

   public int getPosition()
   {
	return m_pos;
   }

   public TreeNode getParent()
   {
	return m_parent;
   }

   /**
    * Applies the move operation.  A move is done by deleting the old
    * node from the tree, and inserting it at its new location.
    *
    */

   TreeNode apply(EditListener listener)
	throws EditApplicationException
   {
	Delete delete = new Delete(getNode());
	delete.apply();
	if(m_parent == null)
	{
	   m_parent = m_tree.find(m_parentID);
	}
	debug.Debug.assert(m_parent != null);
	m_parent.addChild(getNode(), m_col, m_pos);
	if(listener != null) listener.moveApplied(this);
	return getNode();
   }

   /**
    * Writes the move operation to an XML writer.
    */

   public void write(Element e, XmlDocument d)
	throws DOMException
   {
	Element move = d.createElement("move");
	move.setAttribute("id", (new Integer(getNode().getID())).toString());
	move.setAttribute("parent", (new Integer(m_parent.getID())).toString());
	move.setAttribute("col", (new Integer(m_col)).toString());
	move.setAttribute("pos", (new Integer(m_pos)).toString());
	e.appendChild(move);
   }

    
   /**
    * Loads the operation from a diff file.  
    * @param e the XML element representing the move operation.
    * @throw EditParseException if the data presented in the element
    * or its sub elements could not be fully interpreted.
    */
    
   protected void load(Element e, RootNode tree) 
	throws EditParseException
   {
	m_col = (new Integer(e.getAttribute("col"))).intValue();
	m_pos = (new Integer(e.getAttribute("pos"))).intValue();
	setNode(extractNode("id", e, tree));
	m_parent = extractNode("parent", e, tree);
	m_parentID = (new Integer(e.getAttribute("parent"))).intValue();
	m_tree = tree;
   }

}
