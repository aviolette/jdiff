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
 * The <code>InstanceOf</code> class represents an expression in
 * the following form: &lt;expression&gt; instanceof
 * &lt;type&gt;.
 */

public final class InstanceOf
extends Expression
{
    public static final String LABEL = "instanceof";

    public static final int COL_RIGHT = 0;
    public static final int COL_LEFT = 1;

    private static java.util.Map s_attributes;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Type.class, "The type."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


    protected Map attributeMap()
    {
	if(s_attributes == null)
	{
	    s_attributes = new HashMap();
	    initializeAttributeMap(s_attributes);
	}
	return s_attributes;
    }

    public int getCollectionCount()
    {
	return 2;
    }


    private Type m_right;
    private Expression m_left;
    protected String getPrivateLabel()
    {
	return LABEL;
    }

    /**
     * Constructs an empty expression.
     */
    
    public InstanceOf()
    {
    }
  
    /**
     * Constructs an <code>InstanceOf</code> object.
     * @param e1 the left hand expression
     * @param t the right hand type
     * @see Operator 
     */

    public InstanceOf(Expression e1, Type t)
    {
	setLeft(e1);
	setRight(t);
    }

    /**
     * Sets the right hand expression
     */
    
    public void setRight(Type t)
    {
	m_right = t;
	if(m_right != null)
	{
	    m_right.setParent(this, COL_RIGHT);
	}
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }
    
    /**
     * Returns the right hand expression
     */
    
    public Type getRight()
    {
	return m_right;
    }

    /**
     * Sets the left hand expression
     */

    public void setLeft(Expression e)
    {
	m_left = e;
	if(m_left != null)
	{
	    m_left.setParent(this, COL_LEFT);
	}
    }

    /**
     * Returns the left hand expression.
     */

    public Expression getLeft()
    {
	return m_left;
	
    }
    public Iterator children(int col)
    {
	if(col == COL_RIGHT)
	{
	    return new SingleValueIterator("Right");
	}
	else if(col == COL_LEFT)
	{
	    return new SingleValueIterator("Left");
	}
	debug.Debug.assert(false);
	return null;
    }

    public int childCount(int col)
    {
	if(col == COL_RIGHT)
	{
	    return (m_right == null) ? 0 : 1;
	}
	else if(col == COL_LEFT)
	{
	    return (m_left == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }


    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_RIGHT)
	{
	    setRight((Type)n);
	}
	else if(col == COL_LEFT)
	{
	    setLeft((Expression)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }

    public boolean removeChild(TreeNode n)
    {
	int col = n.getCollection();
	if(col == COL_RIGHT)
	{
	    setRight(null);
	    return true;
	}
	else if(col == COL_LEFT)
	{
	    setLeft(null);
	    return true;
	}
	else
	{
	    debug.Debug.assert(false);
	}
	return false;
    }

    public TreeNode childAt(int col, int pos)
    {
	switch(col)
	{
	case COL_RIGHT:
	    return m_right;
	case COL_LEFT:
	    return m_left;
	}
	return null;
    }

}
