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
 * The <code>FieldAccess</code> class represents the access of a field
 * of a Java object.  
 */

public final class FieldAccess
extends PrimaryExpression
{

    public static final int COL_EXPRESSION = 0;

    private static HashMap s_attributes;

    public int getCollectionCount()
    {
	return 1;
    }


    protected void initializeAttributeMap(Map map)
    {
	map.put("Name", new TreeNode.AttributeMetaData(String.class, true, null, "The name of the field to access."));
	super.initializeAttributeMap(map);
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


    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression that evaluates to an object whose field is being accessed.")
    };

    public final TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


    public static final String LABEL = "field_access";

    private Expression m_expression;
    private String m_name;

    protected final String getPrivateLabel()
    {
	return LABEL;
    }

    public FieldAccess(Expression expr, String fieldName)
    {
	setExpression(expr);
	m_name = fieldName;
    }

    public FieldAccess()
    {
	
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    public String getName()
    {
	return m_name;
    }

    public Expression getExpression()
    {
	return m_expression;
    }

    public void setName(String name)
    {
	m_name = name;
    }

    public void setExpression(Expression expr)
    {
	m_expression = expr;
	expr.setParent(this, COL_EXPRESSION);
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

    public boolean removeChild(TreeNode n)
    {
	if(n.getCollection() == COL_EXPRESSION)
	{
	    return false;
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
    
    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_EXPRESSION)
	{
	    return m_expression;
	}
	return null;
    }

}
