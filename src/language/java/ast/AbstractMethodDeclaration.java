package language.java.ast;

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
import tree.*;

/**
 * The <code>AbstractMethodDeclaration</code> represents the common
 * functionality between constructor declarations and method
 * declarations.  
 */

public abstract class AbstractMethodDeclaration 
extends Node
{
    /**
     * 0 - The collection identifier for the parameters collection.
     */
    public static final int COL_PARAMETERS = 0;

    /**
     * 1 - The collection identifier for the method block.
     */

    public static final int COL_BLOCK = 1;

    /**
     * 2 - The collection identifier for the throw list.
     */
    public static final int COL_THROWS = 2;



    public int getCollectionCount()
    {
	return 3;
    }

    private Block m_block;
    private Vector m_parameters;
    private Visibility m_visibility = Visibility.DEFAULT;
    private String m_name;
    private Vector m_throws;

    protected void initializeAttributeMap(Map map)
    {
	map.put("Name", new TreeNode.AttributeMetaData(String.class, false, null, 
						       "The method or constructors name."));
	map.put("Visibility", new TreeNode.AttributeMetaData(Visibility.class, false, 
		"package private", "The method or constructor's visibility." ));
	super.initializeAttributeMap(map);
    }

    protected void resetParameters()
    {
	m_parameters = new Vector();
    }

    /**
     * Sets the name of the method
     */

    public final void setName(String s)
    {
	m_name = s;
    }

    /**
     * Returns the name of the method.
     */

    public final String getName()
    {
	return m_name;
    }

    /**
     * Sets the visibility of the method.
     */

    public final void setVisibility(Visibility visibility)
    {
	m_visibility = visibility;
    }
    
    /**
     * Returns the visibility of the method.
     */

    public final Visibility getVisibility()
    {
	return m_visibility;
    }
    
    protected AbstractMethodDeclaration()
    {
	m_visibility = Visibility.DEFAULT;
	resetParameters();
	m_throws = new Vector();
    }

    /**
     * Adds a parameter in the specified position.
     * @param p the parameter
     * @param pos the position
     */

    public void addParameter(Parameter p, int pos)
	throws ArrayIndexOutOfBoundsException
    {
	m_parameters.add(pos, p);
	p.setParent(this, COL_PARAMETERS);
    }

    /**
     * Resets the parameter list with the members of the vector.  
     */
    
    public void addParameters(Vector v)
    {
	m_parameters = v;
	Iterator i = v.iterator();
	while(i.hasNext())
	{
	    Parameter p = (Parameter)i.next();
	    p.setParent(this, COL_PARAMETERS);
	}
	
    }

    /**
     * Returns the block of code associated with the method.  This can
     * be <code.null</code> if the method is abstract.
     */

    public Block getBlock()
    {
	return m_block;
    }

    /**
     * Sets the block of code associated with the method.
     */

    public void setBlock(Block b)
    {
	m_block = b;
	if(b != null)
	{
	    b.setParent(this, COL_BLOCK);
	}
    }

    public void setThrowList(Vector v)
    {
	Iterator i = v.iterator();
	m_throws.clear();
	while(i.hasNext())
	{
	    addThrowable((Name)i.next());
	}
    }
    /**
     * Returns an iterator that iterates over the list of parameters.
     */

    public Iterator parameters()
    {
	return m_parameters.iterator();
    }

    /**
     * Add the name to the list of exceptions that the method throws.
     * @param name the name of the exception
     * @param pos the position to place the name in the collection
     */

    public void addThrowable(Name name, int pos)
    {
	m_throws.add(pos, name);
	name.setParent(this, COL_THROWS);
    }

    /**
     * Add the name at the end of the list of exceptions that the
     * method throws.  
     */

    public void addThrowable(Name name)
    {
	m_throws.addElement(name);
	name.setParent(this, COL_THROWS);
    }

    public Iterator throwsList()
    {
	return m_throws.iterator();
    }

    public int childCount(int id)
    {
	switch(id)
	{
	case COL_THROWS:
	    return m_throws.size();
	case COL_BLOCK:
	    return getBlock() == null ? 0 : 1;
	case COL_PARAMETERS:
	    return m_parameters.size();
	default:
	    debug.Debug.assert(false);
	    break;
	}
	return 0;
    }

    public void addChild(TreeNode node, int col, int pos)
    {
	switch(col)
	{
	case COL_THROWS:
	    addThrowable((Name)node, pos);
	    break;
	case COL_BLOCK:
	    setBlock((Block)node);
	    break;
	case COL_PARAMETERS:
	    try
	    {
		addParameter((Parameter)node, pos);
	    }
	    catch(Exception e)
	    {
		e.printStackTrace();
	    }
	    break;
	default:
	    debug.Debug.assert(false);
	    break;
	}
    }

    public boolean removeChild(TreeNode n)
    {
	switch(n.getCollection())
	{
	case COL_THROWS:
	    return m_throws.remove(n);
	case COL_BLOCK:
	    if(n != getBlock()) return false;
	    setBlock(null);
	    return true;
	case COL_PARAMETERS:
	    return m_parameters.remove(n);
	default:
	    debug.Debug.assert(false);
	    break;
	}
	return false;
    }

    public Iterator children(int id)
    {
	switch(id)
	{
	case COL_THROWS:
	    return throwsList();
	case COL_BLOCK:
	    return new SingleValueIterator("Block");
	case COL_PARAMETERS:
	    return parameters();
	default:
	    debug.Debug.assert(false);
	    break;
	}
	return null;
    }

    public TreeNode childAt(int col, int pos)
    {
	switch(col)
	{
	case COL_THROWS:
	    return (TreeNode)m_throws.get(pos);
	case COL_BLOCK:
	    debug.Debug.assert(pos == 0);
	    return m_block;
	case COL_PARAMETERS:
	    return (TreeNode)m_parameters.get(pos);
	default:
	    break;
	}
	return null;
    }
    
}
