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
 * The <code>ReturnStatement</code> class represents a statement in
 * the form <code>return [&lt;expression&gt;];</code>.  
 */

public final class ReturnStatement
extends Statement
{

    public final static int COL_EXPRESSION = 0;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_1,
					Expression.class, "The expression.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    public int getCollectionCount()
    {
	return 1;
    }

    public final static String LABEL = "return";

    private Expression m_expression;

    public ReturnStatement()
    {
    }
    public void accept(ASTTraverser traverser)
    {
	traverser.visit(this);
    }

    public void setExpression(Expression e)
    {
	m_expression = e;
	if( e != null)
	{
	    e.setParent(this, COL_EXPRESSION);
	}
    }

    public Expression getExpression()
    {
	return m_expression;
    }
    
    protected final String getPrivateLabel()
    {
	return LABEL;
    }
    public Iterator children(int col)
    {
	if(col == COL_EXPRESSION)
	{
	    return new SingleValueIterator("Expression");
	}
	debug.Debug.assert(false);
	return null;
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_EXPRESSION)
	{
	    return m_expression;
	}
	return null;
    }

    public int childCount(int col)
    {
	if(col == COL_EXPRESSION)
	{
	    return (m_expression == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    public boolean removeChild(TreeNode n)
    {
	if(n.getCollection() == COL_EXPRESSION)
	{
	    setExpression(null);
	    return true;
	}
	else
	{
	    debug.Debug.assert(false);
	}
	return false;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_EXPRESSION)
	{
	    setExpression((Expression)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    
}
