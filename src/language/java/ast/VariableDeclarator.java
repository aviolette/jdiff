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
 * The <code>VariableDeclarator</code> class represents the definition
 * of a single variable.  An instance can either be the child of a
 * <code>Parameter</code> object or a <code>VariableDeclaration</code>
 * object.  
 */

public final class VariableDeclarator
extends Node
{
    public final static String LABEL = "vardec";

    private String m_name;
    private int m_dimension;
    private Node m_initializer;

    private static java.util.Map s_attributes;

    protected Map attributeMap()
    {
	if(s_attributes == null)
	{
	    s_attributes = new HashMap();
	    initializeAttributeMap(s_attributes);
	}
	return s_attributes;
    }

    protected void initializeAttributeMap(Map map)
    {
	map.put("Dimension", new TreeNode.AttributeMetaData(Integer.TYPE, false, new Integer(0), "The array dimension.  A value of zero means that it is not an array."));
	map.put("Name", new TreeNode.AttributeMetaData(String.class, false, null, "The name of the variable."));
    }

    public final static int COL_INITIALIZER = 0;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Node.class, "The array initializer or expression that initializes the variable.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }



    public int getCollectionCount()
    {
	return 1;
    }

    /**
     * Constructs a <code>VariableDeclarator</code>.
     */

    public VariableDeclarator()
    {
    }
    
    public void accept(ASTTraverser traverser)
    {
	traverser.visit(this);
    }
    
    /**
     * Returns the expression the variable is initialized with.  This
     * can either be an <code>Expression</code> object or a array
     * initializer.
     */

    public Node getInitializer()
    {
	return m_initializer;
    }

    /**
     * Sets the initializer expression.
     * @param initializer an expression or an array initializer
     */

    public void setInitializer(Node initializer)
    {
	m_initializer = initializer;
	if(initializer != null)
	{
	    initializer.setParent(this, COL_INITIALIZER);
	}
    }

    public String toString()
    {
	java.util.Vector v = getStandardDescriptiveElements();
	v.addElement(getName());
	v.addElement(new Integer(getDimension()));
	return createDescriptiveTuple(getLabel(), v.iterator());
    }

    /**
     * Returns the name of the variable.
     */

    public String getName()
    {
	return m_name;
    }

    /**
     * Sets the name of the variable.
     */

    public void setName(String name)
    {
	m_name = name;
    }

    /**
     * Sets the array dimension.  A value of zero means that there is
     * no array.
     */

    public void setDimension(int dimension)
    {
	m_dimension = dimension;
    }

    /**
     * Returns the array dimension.  A value of zero means that there
     * is no array.  
     */

    public int getDimension()
    {
	return m_dimension;
    }

    protected final String getPrivateLabel()
    {
	return LABEL;
    }

    public Iterator children(int col)
    {
	if(col == COL_INITIALIZER)
	{
	    return new SingleValueIterator("Initializer");
	}
	debug.Debug.assert(false);
	return null;
    }

    public int childCount(int col)
    {
	if(col == COL_INITIALIZER)
	{
	    return (m_initializer == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_INITIALIZER)
	{
	    setInitializer((Node)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    public boolean removeChild(TreeNode n)
    {
	if(n.getCollection() == COL_INITIALIZER)
	{
	    setInitializer(null);
	    return true;
	}
	debug.Debug.assert(false);
	return false;
    }
    
    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_INITIALIZER)
	{
	    return m_initializer;
	}
	return null;
    }

}
