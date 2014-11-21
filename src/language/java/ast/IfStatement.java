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
 * The <code>IfStatement</code> represents a statement in the
 * following form&#58; <code>if (expr) statement else
 * statement</code>.
 */

public final class IfStatement
extends Statement
{

    public static final String LABEL = "if";

    public static final int COL_EXPRESSION = 0;
    public static final int COL_THEN = 1;
    public static final int COL_ELSE = 2;

    public int getCollectionCount()
    {
	return 3;
    }

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression that determines whether the 'if' or 'else' will be executed."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Statement.class, "The statement that is executed if the condition is true."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_1,
					Statement.class, "The statement that is executed if the condition is false.")
    };

    public final TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }



    private Expression m_expression;
    private Statement m_then;
    private Statement m_else;

    /**
     * Constructs an empty IfStatement.  */

    public IfStatement()
    {
    }


    /**
     * Sets the expression that is wrapped in parantheses.
     *
     */

    public void setExpression(Expression e)
    {
	m_expression = e;
	e.setParent(this, COL_EXPRESSION);
    }

    /**
     * Returns the expression that is wrapped in parantheses.
     */

    public Expression getExpression()
    {
	return m_expression;
    }

    /**
     * Sets the statement that executes when the condition is
     * evaluated to true.  
     */

    public void setThen(Statement s)
    {
	m_then = s;
	s.setParent(this, COL_THEN);
    }

    /**
     * Returns the statement that executes when the condition is
     * evaluated to true.
     */
    
    public Statement getThen()
    {
	return m_then;
    }

    /**
     * Sets the statement that executes when the condition is
     * evaluated to false.
     */

    public void setElse(Statement s)
    {
	m_else = s;
	if( s != null)
	{
	    s.setParent(this, COL_ELSE);
	}
    }

    /**
     * Returns the statement that executes when the condition is
     * evaluated to true.
     */

    public Statement getElse()
    {
	return m_else;
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
	else if(col == COL_THEN)
	{
	    return new SingleValueIterator("Then");
	}
	else if(col == COL_ELSE)
	{
	    return new SingleValueIterator("Else");
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
	else if(col == COL_THEN)
	{
	    return (m_then == null) ? 0 : 1;
	}
	else if(col == COL_ELSE)
	{
	    return (m_else == null) ? 0 : 1;
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
	else if(col == COL_THEN)
	{
	    setThen((Statement)n);
	}
	else if(col == COL_ELSE)
	{
	    setElse((Statement)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    
    public boolean removeChild(TreeNode node)
    {
	int col = node.getCollection();
	if(col == COL_EXPRESSION)
	{
	    return false;
	}
	else if(col == COL_THEN)
	{
	    return false;
	}
	else if(col == COL_ELSE)
	{
	    setElse(null);
	    return true;
	}
	return false;
    }

    public TreeNode childAt(int col, int pos)
    {
	switch(col)
	{
	case COL_EXPRESSION:
	    return m_expression;
	case COL_THEN:
	    return m_then;
	case COL_ELSE:
	    return m_else;
	}
	return null;
    }

}
