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

import debug.*;
import java.util.Iterator;
import tree.*;

/**
 * The <code>WhileStatement</code> represents a while-loop or a
 * do-while-loop.  
 */

public final class WhileStatement
extends Statement
{
    /**
     * 0 - the collection identifier for the loop condition
     */

    public final static int COL_CONDITION = 0;

    /**
     * 1 - the collection identifier for the statement that is
     * executed while the loop condition is true.
     */

    public final static int COL_STATEMENT = 1;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression that determines whether there should be another iteration."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Statement.class, "The statement that gets executed as a result of the while.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    public int getCollectionCount()
    {
	return 2;
    }

    /**
     * while - the class's label.
     */

    public final static String LABEL = "while";

    private Statement m_statement;
    private Expression m_condition;
    private boolean m_isDo;

    /**
     * Constructs a <code>WhileStatement</code> node.  
     * @param isDo <code>true</code> if the node is a do-while-statement
     * <code>false</code> otherwise.
     */

    public WhileStatement(boolean isDo)
    {
	m_isDo = isDo;
    }

    /**
     * Constructs a <code>WhileStatement</code> node.  The node will
     * represent a while-loop rather than a do-while-loop.
     */

    public WhileStatement()
    {
	this(false);
    }

    /**
     * Set true if the statement represents a do-while-loop.  Set
     * false if the statement represents a while-loop.
     */

    public void setDo(boolean isDo)
    {
	m_isDo = isDo;
    }

    /**
     * Returns <code>true</code> if the statement represents a
     * do-while-loop, returns <code>false</code> otherwise.
     */

    public boolean isDo()
    {
	return m_isDo;
    }

    /** 
     * Sets the statement that is executed while the loop condition is
     * true.
     */

    public void setStatement(Statement s)
    {
	m_statement = s;
	s.setParent(this, COL_STATEMENT);
    }

    /**
     * Returns the statement that is executed while the loop condition
     * is true.  
     */

    public Statement getStatement()
    {
	return m_statement;
    }


    public Expression getExpression()
    {
	return m_condition;
    }

    public void setExpression(Expression e)
    {
	m_condition = e;
	e.setParent(this, COL_CONDITION);
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }
    public Iterator children(int col)
    {
	if(col == COL_CONDITION)
	{
	    return new SingleValueIterator("Expression");
	}
	else if(col == COL_STATEMENT)
	{
	    return new SingleValueIterator("Statement");
	}
	debug.Debug.assert(false);
	return null;
    }

    public int childCount(int col)
    {
	if(col == COL_CONDITION)
	{
	    return (m_condition == null) ? 0 : 1;
	}
	else if(col == COL_STATEMENT)
	{
	    return (m_statement == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_CONDITION)
	{
	    setExpression((Expression)n);
	}
	else if(col == COL_STATEMENT)
	{
	    setStatement((Statement)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    public boolean removeChild(TreeNode n)
    {
	int col = n.getCollection();
	if(col == COL_CONDITION)
	{
	    return false;
	}
	else if(col == COL_STATEMENT)
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
	if(col == COL_CONDITION)
	{
	    return m_condition;
	}
	else if(col == COL_STATEMENT)
	{
	    return m_statement;
	}
	
	return null;
    }

}
