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


import java.util.*;
import com.sun.xml.tree.XmlDocument;
import org.w3c.dom.*;
import tree.*;


/**
 * Given two abstract syntax trees T<sub>1</sub> and T<sub>2</sub> the
 * Insert operation inserts a node from T<sub>2</sub> into
 * T<sub>1</sub>.  The new node will be identical to the original node
 * from T<sub>2</sub> except for its identifier.  The following steps
 * are performed&#58; 
 * <br> 
 * <ol>
 *
 * <li> The class of the node is retrieved based on the
 * <code>label</code> parameter.  The class information is retrieved
 * by calling <code>getClassFromLabel</code> in the <code>Node</code>
 * class.
 * <li> A new node is created of the specified type.
 * <li> The new node is populated with the data specified in the
 * <code>values</code> collection.  The diff/merging agent is
 * responsible for defining the semantics of the values in the value
 * collection.  
 * <li> The new node is inserted as the <code>pos</code> child of the
 * node <code>y</code> in its collection <code>col</code>.  Again, the
 * diff/merging agent is responsbile for the definition of the
 * <code>col</code> and <code>pos</code>
 * </ol>
 * */

public class Insert
extends Edit
{
    private int m_x;
    private int m_y;
    private String m_type;
    private int m_col;
    private int m_pos;
    private Map m_values;
    private TreeNode m_nodeY;
    private RootNode m_tree;

    /**
     * Constructs an Insert operation.
     * @param y the node from T<sub>1</sub> that will contain the new
     * node.
     * @param label the class of the node that is inserted. 
     * @param col the collection of node y that will contain the new node.
     * @param pos the position in the collection y that the node will
     * be placed.
     * @param values the values of Node x that will be applied to the
     * new node created under y.  */

    public Insert(TreeNode y, String label, int col, int pos, Map values)
    {
	// m_x is assigned either when the operation is loaded from a file, or
	// when apply() is called
	m_x = 0;

	m_nodeY = y;
	m_type = label;
	m_col = col;
	m_pos = pos;
	m_values = new HashMap();
	if(values != null)
	{
	    m_values.putAll(values);
	}
    }

    /**
     * Constructs an empty <code>Insert</code> object.  It should be
     * loaded with <code>load()</code> before it is used.  
     */

    Insert()
    {
	
    }


    /**
     * Loads the insert operation from the XML file.
     * @param e the XML element representing the 'insert' node in the XML tree.
     * @param tree the root node of the AST that is the target of the
     * Insert operation.  This will be used to lookup the parent node
     * in T<sub>1</sub> of the node being inserted, since its ID is
     * stored in the file.
     */

    protected void load(Element e, RootNode tree)
	throws EditParseException
    {
	m_type = e.getAttribute("type");
	String target = e.getAttribute("target");
	String col = e.getAttribute("col");
	String pos = e.getAttribute("pos");
	m_col = (new Integer(col)).intValue();
	m_pos = (new Integer(pos)).intValue();

	m_nodeY = tree.find((new Integer(target)).intValue());
	if(m_nodeY == null) { m_y = (new Integer(target)).intValue(); }
	m_tree = tree;

	m_values = new HashMap();

	loadCompositeAttributes(m_values, e);
    }

    /**
     * Applies the insert operation to tree T<sub>1</sub>.  A new node
     * is created based on the label's class mapping, the new node is
     * added to the tree, and updated with the values in the values
     * collection.
     *
     * @throw EditApplicationException if the child node could not be
     * created.  This might occur if an invalid label was specified.  */


    

    TreeNode apply(EditListener listener)
	throws EditApplicationException
    {
	Class c = TreeNode.registry.getClassFromLabel(m_type);

	TreeNode child;
	try
	{
	    child = (TreeNode)c.newInstance();
	    if(m_nodeY == null)
	    {
		m_nodeY = m_tree.find(m_y);
	    }

	    m_nodeY.addChild(child, m_col, m_pos);
	    //	    debug.Debug.assert(child.getID() > 0);
	    updateValues(child, m_values);
	    m_x = child.getID();
	    //	    debug.Debug.assert(child.getParent() != null && (child.getParent() == m_nodeY));
	    setNode(child);
	    if(listener != null) listener.insertApplied(this);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	    throw new EditApplicationException("Could not instantiate the child node class: "+m_type);
	}

	return child;
    }

    public void write(Element e, XmlDocument d)
	throws DOMException
    {
	Element ins = d.createElement("insert");
	ins.setAttribute("id", (new Integer(m_x)).toString());
	ins.setAttribute("target", (new Integer(m_nodeY.getID())).toString());
	ins.setAttribute("type", m_type);
	ins.setAttribute("col", (new Integer(m_col)).toString());
	ins.setAttribute("pos", (new Integer(m_pos)).toString());
	e.appendChild(ins);
	if(m_values != null)
	{
	    writeCompositeAttributes(m_values, ins, d);
	}
    }

}





