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

import java.util.Iterator;
import tree.*;

/**
 * The <code>Cast</code> node represents a cast of an object to a
 * specific type.
 * 
 * @author Andrew Violette
 * @see Expression
 * @see Type
 *  */

public final class Cast
extends Expression
{

    /**
     * cast - the node's label
     */

    public static final String LABEL = "cast";

    /**
     * 0 - the collection identifier for the Expression that is being cast.
     */
    
    public static final int COL_EXPRESSION = 0;

    /**
     * 1 - the collection identifier for the Type that the expression is being cast to.
     */

    public static final int COL_TYPE = 1;

    public int getCollectionCount()
    {
	return 2;
    }

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression that is being cast."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Type.class, "The type that the expression is being cast to.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


    private Expression m_expression;
    private Type m_type;

    /**
     * Constructs an empty Cast.
     */

    public Cast()
    {
    }

    /**
     * Constructs a Cast, initialized with an expression.
     * @param e an expression that is non-null.
     */

    public Cast(Expression e)
    {
	setExpression(e);
    }

    /**
     * Sets the expression that is being cast to a specific type.
     *
     */

    public void setExpression(Expression e)
    {
	m_expression = e;
	e.setParent(this, COL_EXPRESSION);
    }

    /**
     * Returns the expression that is being cast to a specific type.
     */

    public Expression getExpression()
    {
	return m_expression;
    }

    /**
     * Sets the type that the expression is being cast to.
     */

    public void setType(Type t)
    {
	m_type = t;
	t.setParent(this, COL_TYPE);
    }

    /**
     * Returns the type that the expression is being cast to.
     */

    public Type getType()
    {
	return m_type;
    }

    protected String getPrivateLabel()
    {
	return LABEL;
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
	else if(col == COL_TYPE)
	{
	    return new SingleValueIterator("Type");
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
	else if(col == COL_TYPE)
	{
	    return (m_type == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_EXPRESSION)
	{
	    setExpression((Expression)n);
	}
	else if(col == COL_TYPE)
	{
	    setType((Type)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    
    public boolean removeChild(TreeNode node)
    {
	if(node.getCollection() == COL_EXPRESSION)
	{
	    return false;
	}
	else if(node.getCollection() == COL_TYPE)
	{
	    return false;
	}
	else
	{
	    debug.Debug.assert(false);
	}
	return false;
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_EXPRESSION)
	{
	    return m_expression;
	}
	else if(col == COL_TYPE)
	{
	    return m_type;
	}

	debug.Debug.assert(false);
	return null;
    }

}
