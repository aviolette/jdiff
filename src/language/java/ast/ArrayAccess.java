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
 * The <code>ArrayAccess</code> class represents the access of a value
 * from the array.  It is composited of two expressions: the
 * expression that evaluates to the array, and the expression that
 * represents the index of the value in the array.  
 * @author Andrew Violette
 */

public class ArrayAccess
extends PrimaryExpression
{
    /**
     * arrayaccess - the node's label
     */

    public static final String LABEL = "arrayaccess";

    /**
     * 0 - the collection identifier for the expression that evaluates
     * to an array.  
     */

    public static final int COL_EXPRESSION = 0;
    
    /**
     * 1 - the collection identifier for the expression that
     * represents the index of the value that is being retrieved from
     * the array.  
     */

    public static final int COL_ACCESS_EXPRESSION = 1;

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

    public String getPrivateLabel()
    {
	return LABEL;
    }

    public int getCollectionCount()
    {
	return 2;
    }
    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression that evaluates to an array"),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression that evaluates to the array index")
	
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


    private PrimaryExpression m_expression;
    private Expression m_accessExpression;

    /**
     * Constructs an <code>ArrayAccess</code> object.
     * @param e1 the expression that evaluates to an array
     * @param e2 the expression that represents the index being accessed
     */

    public ArrayAccess(PrimaryExpression e1, Expression e2)
    {
	setExpression(e1);
	setAccessExpression(e2);
    }

    /**
     * Constructs an empty <code>ArrayAccess</code> object.
     */

    public ArrayAccess()
    {
    }

    /**
     * Returns the expression that represents the index being accessed.
     */

    public Expression getAccessExpression()
    {
	return m_accessExpression;
    }

    /**
     * Sets the expression that represents the index being accessed.
     */

    public void setAccessExpression(Expression e)
    {
	m_accessExpression = e;
	e.setParent(this, COL_ACCESS_EXPRESSION);
    }

    /**
     * Returns the expression that represents the array.
     */

    public PrimaryExpression getExpression()
    {
	return m_expression;
    }
    
    /**
     * Sets the expression that represents the array.
     */

    public void setExpression(PrimaryExpression e)
    {
	m_expression = e;
	e.setParent(this, COL_EXPRESSION);
    }
    
    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }
    
    public Iterator children(int col)
    {
	if(col == COL_EXPRESSION)
	{
	    return new SingleValueIterator("Expression");
	}
	else if(col == COL_ACCESS_EXPRESSION)
	{
	    return new SingleValueIterator("AccessExpression");
	}
	debug.Debug.assert(false);
	return null;
    }

    public int childCount(int col)
    {
	if(col == COL_EXPRESSION)
	{
	    return (m_expression == null) ? 0 : 1;
	}
	else if(col == COL_ACCESS_EXPRESSION)
	{
	    return (m_accessExpression == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_EXPRESSION)
	{
	    setExpression((PrimaryExpression)n);
	}
	else if(col == COL_ACCESS_EXPRESSION)
	{
	    setAccessExpression((Expression)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    
    public boolean removeChild(TreeNode node)
    {
	debug.Debug.assert((node.getCollection() == COL_EXPRESSION) || 
			   (node.getCollection() == COL_ACCESS_EXPRESSION));
	return false;
    }
    
    public TreeNode childAt(int col, int pos)
    {
	debug.Debug.assert(pos == 0);
	switch(col)
	{
	case COL_EXPRESSION:
	    return m_expression;
	case COL_ACCESS_EXPRESSION:
	    return m_accessExpression;
	}
	return null;
    }

}
