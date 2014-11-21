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
 * The <code>ConditionalExpression</code> class represents an
 * expression that, depending on the evaluation of a predicate
 * expression, either evaluates the 'if true' expression or the 'if
 * false' expression.  
 */

public final class ConditionalExpression
extends Expression
{
    private Expression m_condition;
    private Expression m_ifTrue;
    private Expression m_ifFalse;

    /**
     * 0 - the collection identifier for the conditional expression.
     */

    public static final int COL_CONDITION = 0;
    
    /**
     * 1 - the collection identifier for the expression that is
     * evaluated if the condition is true.  
     */

    public static final int COL_TRUE = 1;
    
    /**
     * 2 - the collection identifier for the expression that is
     * evaluated if the condition is false.  
     */

    public static final int COL_FALSE = 2;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The conditional expression."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The true expression."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The false expression.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    public int getCollectionCount()
    {
	return 3;
    }

    /**
     * condexpr - the node's label.
     */

    public static final String LABEL = "condexpr";

    protected final String getPrivateLabel()
    {
	return LABEL;
    }

    /**
     * Constructs an empty conditional expression.
     */

    public ConditionalExpression()
    {
    }

    public void accept(ASTTraverser traverser)
    {
	traverser.visit(this);
    }
    
    /**
     * Sets the expression that should be evaluated to determine which
     * of the other expressions to evaluate.  
     */
    public void setCondition(Expression expr)
    {
	m_condition = expr;
	expr.setParent(this, COL_CONDITION);
    }
    
    /**
     * Returns the conditional expression.
     */

    public Expression getCondition()
    {
	return m_condition;
    }

    /**
     * Sets the expression that will be evaluated if the conditional
     * expression evaluates to true.  
     */

    public void setIfTrue(Expression expr)
    {
	m_ifTrue = expr;
	expr.setParent(this, COL_TRUE);
    }

    /**
     * Returns the expression that will be evaluated if the
     * conditional expression evaluates to true.
     */

    public Expression getIfTrue()
    {
	return m_ifTrue;
    }

    /**
     * Sets the expression that will be evaluated if the conditional
     * expression evaluates to false.  
     */

    public void setIfFalse(Expression expr)
    {
	m_ifFalse = expr;
	expr.setParent(this, COL_FALSE);
    }

    /**
     * Returns the expression that will be evaluated if the conditional
     * expression evaluates to false.  
     */

    public Expression getIfFalse()
    {
	return m_ifFalse;
    }
    public Iterator children(int col)
    {
	if(col == COL_TRUE)
	{
	    return new SingleValueIterator("IfTrue");
	}
	else if(col == COL_FALSE)
	{
	    return new SingleValueIterator("IfFalse");
	}
	else if(col == COL_CONDITION)
	{
	    return new SingleValueIterator("Condition");
	}
	debug.Debug.assert(false);
	return null;
    }

    public int childCount(int col)
    {
	if(col == COL_TRUE)
	{
	    return (m_ifTrue == null) ? 0 : 1;
	}
	else if(col == COL_FALSE)
	{
	    return (m_ifFalse == null) ? 0 : 1;
	}
	else if(col == COL_CONDITION)
	{
	    return (m_condition == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_TRUE)
	{
	    setIfTrue((Expression)n);
	}
	else if(col == COL_FALSE)
	{
	    setIfFalse((Expression)n);
	}
	else if(col == COL_CONDITION)
	{
	    setCondition((Expression)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    public boolean removeChild(TreeNode n)
    {
	boolean rc = true;
	switch(n.getCollection())
	{
	case COL_TRUE:
	    setIfTrue(null);
	    break;
	case COL_FALSE:
	    setIfFalse(null);
	    break;
	case COL_CONDITION:
	    setCondition(null);
	    break;
	default:
	    debug.Debug.assert(false);
	    rc = false;
	}
	return rc;
    }

    public TreeNode childAt(int col, int pos)
    {
	switch(col)
	{
	case COL_TRUE:
	    return m_ifTrue;
	case COL_FALSE:
	    return m_ifFalse;
	case COL_CONDITION:
	    return m_condition;
	}
	return null;
    }

}

