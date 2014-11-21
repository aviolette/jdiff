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
 * The <code>InfixExpression</code> class represents an expression in
 * the following form: &lt;expression&gt; &lt;operator&gt;
 * &lt;expression&gt;.
 */

public final class InfixExpression
extends Expression
{
    public static final String LABEL = "binexp";

    public static final int COL_RIGHT = 0;
    public static final int COL_LEFT = 1;

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
	map.put("Operator", new TreeNode.AttributeMetaData(Integer.TYPE, false, 
			   new Integer(0), "The operator that separates the two expressions."));
    }

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The right expression."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The left expression.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


    public int getCollectionCount()
    {
	return 2;
    }


    private Expression m_right;
    private Expression m_left;
    private int m_op;

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    /**
     * Constructs an empty expression.
     */
    
    public InfixExpression()
    {
    }
  
    /**
     * Constructs an <code>InfixExpression</code> object.
     * @param e1 the left hand expression
     * @param o the operator constant.  Defined as static variables in
     * the <code>Operator</code> class.
     * @param e2 the right hand expression
     * @see Operator 
     */

    public InfixExpression(Expression e1, int o, Expression e2)
    {
	setLeft(e1);
	setOperator(o);
	setRight(e2);
    }

    /**
     * Sets the operator in the infix expression.
     * @see Operator
     */

    public void setOperator(int o)
    {
	m_op = o;
    }

    /**
     * Returns the operator in the expression
     * @see Operator
     */

    public int getOperator()
    {
	return m_op;
    }

    /**
     * Sets the right hand expression
     */
    
    public void setRight(Expression e)
    {
	m_right = e;
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
    
    public Expression getRight()
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
	    setRight((Expression)n);
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
	if(col == COL_RIGHT)
	{
	    return m_right;
	}
	else if(col == COL_LEFT)
	{
	    return m_left;
	}
	return null;
    }

}
