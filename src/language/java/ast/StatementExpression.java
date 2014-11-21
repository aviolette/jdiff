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

public class StatementExpression
extends Statement
{
    public static final String LABEL = "stexpr";
    
    public static final int COL_EXPRESSION = 0;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


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
	map.put("AsStatement", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE, "True if the statement expression is in the context of a statement, or false if it is in the context of a for-loop."));
	super.initializeAttributeMap(map);
    }

    private boolean m_asStatement;

    public void setAsStatement(boolean b)
    {
	m_asStatement = b;
    }

    public boolean isAsStatement()
    {
	return m_asStatement;
    }

    public String getPrivateLabel()
    {
	return LABEL;
    }

    public int getCollectionCount()
    {
	return 1;
    }

    private Expression m_expression;

    public StatementExpression(boolean asStatement)
    {
	m_asStatement = asStatement;
    }

    public StatementExpression()
    {
	this(false);
    }

    public StatementExpression(Expression e)
    {
	setExpression(e);
    }
    
    public Expression getExpression()
    {
	return m_expression;
    }
    
    public void setExpression(Expression e)
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
	debug.Debug.assert(false);
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
    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_EXPRESSION)
	{
	    return m_expression;
	}
	return null;
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
    
    public boolean removeChild(TreeNode node)
    {
	if(node.getCollection() == COL_EXPRESSION)
	{
	    return false;
	}
	else
	{
	    debug.Debug.assert(false);
	}
	return false;
    }


}
