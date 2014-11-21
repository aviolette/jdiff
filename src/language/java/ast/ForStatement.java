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
 * The <code>ForStatement</code> node represents a for-loop.  A for
 * loop is made up of an optional initializer, an optional update
 * expression, a loop condition, and executable statement.
 */

public final class ForStatement
extends Statement
{

    public static final int COL_CONDITIONAL = 0;
    public static final int COL_INITIALIZER = 1;
    public static final int COL_UPDATE = 2;
    public static final int COL_STATEMENT = 3;
    public static final int COL_INIT_STATEMENTS = 4;

    public int getCollectionCount()
    {
	return 5;
    }

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression whose value determines whether the loop continues."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_1,
					LocalVariableDeclaration.class, "The variable whose value is declared prior to starting the loop."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					StatementExpression.class, "The statements that are evaluated after the main statement and before the conditional expression is evaluated."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Statement.class, "The main statement that is executed each iteration of the for loop."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					StatementExpression.class, "The list of statement expression that are evaluated prior to the for loop.")
    };

    public final TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


    public final static String LABEL = "for";

    private Statement m_statement;
    private LocalVariableDeclaration m_init;
    private Vector m_updates;
    private Expression m_conditional;
    private Vector m_inits;

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    public ForStatement()
    {
	m_inits = new Vector();
	m_updates = new Vector();
    }

    public void addInit(StatementExpression e)
    {
	m_inits.addElement(e);
	e.setParent(this, COL_INIT_STATEMENTS);
    }

    public void addInit(StatementExpression e, int pos)
    {
	m_inits.add(pos, e);
	e.setParent(this, COL_INIT_STATEMENTS);
    }

    public Iterator initStatements()
    {
	return m_inits.iterator();
    }
    public Expression getConditional()
    {
	return m_conditional;
    }

    public void setConditional(Expression e)
    {
	m_conditional = e;
	if(e != null)
	{
	    m_conditional.setParent(this, COL_CONDITIONAL);
	}
    }

    public LocalVariableDeclaration getInitializer()
    {
	return m_init;
    }

    public void setInitializer(LocalVariableDeclaration s)
    {
	m_init = s;
	if(s != null)
	{
	    m_init.setParent(this, COL_INITIALIZER);
	}
    }

    public Iterator updates()
    {
	return m_updates.iterator();
    }

    public void addUpdate(StatementExpression e)
    {
	m_updates.addElement(e);
	e.setParent(this, COL_UPDATE);
    }

    public void addUpdate(StatementExpression e, int pos)
    {
	m_updates.add(pos, e);
	e.setParent(this, COL_UPDATE);
    }

    public Statement getStatement()
    {
	return m_statement;
    }

    public void setStatement(Statement s)
    {
	m_statement = s;
	if(s != null)
	{
	    s.setParent(this, COL_STATEMENT);
	}
    }
    public Iterator children(int col)
    {
	switch(col)
	{
	case COL_CONDITIONAL:
	    return new SingleValueIterator("Conditional");
	case COL_STATEMENT:
	    return new SingleValueIterator("Statement");
	case COL_UPDATE:
	    return updates();
	case COL_INITIALIZER:
	    return new SingleValueIterator("Initializer");
	case COL_INIT_STATEMENTS:
	    return initStatements();
	}
	debug.Debug.assert(false);
	return null;
    }


    public int childCount(int col)
    {
	switch(col)
	{
	case COL_CONDITIONAL:
	    return (m_conditional == null) ? 0 : 1;
	case COL_STATEMENT:
	    return (m_statement == null) ? 0 : 1;
	case COL_UPDATE:
	    return m_updates.size();
	case COL_INITIALIZER:
	    return (m_init == null) ? 0 : 1;
	case COL_INIT_STATEMENTS:
	    return m_inits.size();
	}
	debug.Debug.assert(false);
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {

	switch(col)
	{
	case COL_CONDITIONAL:
	    setConditional((Expression)n);
	    break;
	case COL_STATEMENT:
	    setStatement((Statement)n);
	    break;
	case COL_UPDATE:
	    addUpdate((StatementExpression)n, pos);
	    break;
	case COL_INITIALIZER:
	    setInitializer((LocalVariableDeclaration)n);
	    break;
	case COL_INIT_STATEMENTS:
	    addInit((StatementExpression)n, pos);
	default:
	    debug.Debug.assert(false);
	    break;
	}
    }
    
    public TreeNode childAt(int col, int pos)
    {
	switch(col)
	{
	case COL_CONDITIONAL:
	    return m_conditional;
	case COL_STATEMENT:
	    return m_statement;
	case COL_UPDATE:
	    return (TreeNode)m_updates.get(pos);
	case COL_INITIALIZER:
	    return m_init;
	case COL_INIT_STATEMENTS:
	    return (TreeNode)m_inits.get(pos);
	}
	return null;
    }

    public boolean removeChild(TreeNode n)
    {
	switch(n.getCollection())
	{
	case COL_CONDITIONAL:
	    setConditional(null);
	    return true;
	case COL_STATEMENT:
	    setStatement(null);
	    return true;
	case COL_UPDATE:
	    return m_updates.remove(n);
	case COL_INITIALIZER:
	    setInitializer(null);
	    return true;
	case COL_INIT_STATEMENTS:
	    return m_inits.remove(n);
	default:
	    debug.Debug.assert(false);
	    break;
	}
	return false;
    }

}
