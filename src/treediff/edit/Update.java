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
import java.util.*;
import com.sun.xml.tree.XmlDocument;
import org.w3c.dom.*;

/**
 * Updates a node <code>n</code> with the values specified in the
 * values collection.  The following operations occur during an update
 * operation&#58;
 * <ol>
 * 
 * <li> If the update operation was serialized from a file, its node
 * object is retrieved by keying its node ID to the abstract syntax
 * tree's indexable list.
 *
 * <li> The values of the <code>values</code> collection are applied
 * to the node in a manner defined by the diffing agent.  
 * </ol> */

public class Update
extends Edit
{
    private int m_nodeID;
    private Map m_values;

    /**
     * Constructs an empty <code>Update</code> object. 
     */

    protected Update()
    {
    }
    
    /**
     * Constructs an update object that updates the node
     * <code>n</code> with the values specified in the values
     * collection.  
     */

    public Update(TreeNode n, Map values)
    {
	setNode(n);
	m_values = values;
    }

    /**
     * Applies the update operation to the tree.  The node specified
     * as the target of the operation will be updated with the values
     * specified in the map.  It is up for each node to define how it
     * will handle the application of these values to themselves.  
     *
     * @throw EditApplicationException if the node specified for the
     * update operation is invalid.
     */

    TreeNode apply(EditListener listener)
	throws EditApplicationException       
    {
	updateValues(getNode(), m_values);
	if(listener != null) listener.updateApplied(this);
	return getNode();
    }

    /**
     * Returns the ID of the target node.
     */

    private int getID()
    {
	if(getNode() != null)
	{
	    return getNode().getID();
	}
	return m_nodeID;
    }

    /**
     * Writes the move operation to an XML writer.
     */

    public void write(Element e, XmlDocument d)
	throws DOMException
    {
	Element move = d.createElement("update");
	move.setAttribute("id", (new Integer(getID())).toString());
	if(m_values != null)
	{
	    writeCompositeAttributes(m_values, move, d);
	}
	e.appendChild(move);
    }

    /**
     * Loads the operation from a DOM element.
     */

    protected void load(Element e, RootNode tree) 
	throws EditParseException
    {
	setNode(extractNode("id", e, tree));
	m_values = new HashMap();

	loadCompositeAttributes(m_values, e);
    }

    public Map values()
    {
	return m_values;
    }
    
}
