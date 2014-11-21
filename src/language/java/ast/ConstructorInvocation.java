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
 * The <code>ConstructorInvocation</code> class represents the
 * explicit invocation of a constructor.  Either another constructor
 * of the same class can be invoked, or the classes super class's
 * constructor can be invoked.  
 */

public final class ConstructorInvocation
extends Statement
{
    /**
     * 0 - The colleciton identifier for the list of constructor arguments
     */

    public static final int COL_ARGUMENTS = 0;
    
    /**
     * 1 - the collection identifier for the expression that proceeds
     * the call to a super class's constructor.  
     */

    public static final int COL_EXPRESSION = 1;


    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Expression.class, "The arguments.")
	
    };
	
    public int getCollectionCount()
    {
	return 1;
    }

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    private Expression m_expression;
    private Name m_name;
    private Vector m_args;
    private boolean m_isThis;

    public static final String LABEL = "constinv";
    private boolean m_bAsStatement;
    private static Map s_attributes;

    protected Map attributeMap()
    {
	if(s_attributes == null)
	{
	    s_attributes = new HashMap();
	    initializeAttributeMap(s_attributes);
	}
	return s_attributes;
    }

    public void initializeAttributeMap(Map map)
    {
	map.put("This", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE, "If false super() is invoked.  If true this() is invoked"));
	super.initializeAttributeMap(map);
    }

    private void setArguments(Vector v)
    {
	m_args = new Vector(v.size());
	Iterator i = v.iterator();
	while(i.hasNext())
	{
	    Expression e = (Expression)i.next();
	    m_args.addElement(e);
	    e.setParent(this, COL_ARGUMENTS);
	}
    }

    /**
     * Returns <code>true</code> if the constructor invocation invokes
     * another constructor of this class, or <code>false</code> if the
     * class' super class's constructor is invoked.
     */

    public boolean isThis()
    {
	return m_isThis;
    }
    
    /**
     * Set to <code>true</code> if the constructor invocation invokes
     * another constructor of this class, or <code>False</code> if the
     * class's super class's constructor is invoked.  
     */

    public void setThis(boolean b)
    {
	m_isThis = b;
    }

    /**
     * Constructs a <code>ConstructorInvocation</code> object.
     */

    public ConstructorInvocation()
    {
	m_args = new Vector();
    }

    /**
     * Constructs a <code>ConstructorInvocation</code> object.
     * @param expr the expression that proceeds the super class's constructor's invocation
     * @param args a collection of arguments to be added to the constructor invocation
     * @param isThis <code>true</code if the constructor invocation
     * invokes another constructor defined in the current class.
     */

    public ConstructorInvocation(Expression expr, Vector args, boolean isThis)
    {
	m_args = args;
	m_isThis = isThis;
	setExpression(expr);
	
	if(args != null)
	{

	    Iterator i = args.iterator();
	    while(i.hasNext())
	    {
		Expression e = (Expression)i.next();
		e.setParent(this, COL_ARGUMENTS);
	    }
	}
    }

    /**
     * Adds an argument to the specified location.
     */

    public void addArgument(Expression e, int pos)
    {
	m_args.add(pos, e);
	e.setParent(this, COL_ARGUMENTS);
    }

    /**
     * Returns an iterator that iterates over the list of arguments.
     */

    public Iterator arguments()
    {
	if(m_args == null)
	{
	    m_args = new Vector();
	}
	return m_args.iterator();
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }



    public void setExpression(Expression expr)
    {
	m_expression = expr;
	if(m_expression != null)
	{
	    expr.setParent(this, COL_EXPRESSION);
	}
    }
    public Expression getExpression()
    {
	return m_expression;
    }

    public String toString()
    {
	Vector v = getStandardDescriptiveElements();
	v.addElement(getExpression());
	return createDescriptiveTuple(LABEL, v.iterator());
    }
    protected String getPrivateLabel()
    {
	return LABEL;
    }

    public Iterator children(int col)
    {
	if(col == COL_EXPRESSION)
	{
	    return new SingleValueIterator("Expression");
	}
	else if(col == COL_ARGUMENTS)
	{
	    return arguments();
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
	else if(col == COL_ARGUMENTS)
	{
	    return m_args.size();
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
	else if(col == COL_ARGUMENTS)
	{
	    addArgument((Expression)n, pos);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    public boolean removeChild(TreeNode n)
    {
	int col = n.getCollection();
	if(col == COL_EXPRESSION)
	{
	    // not nullable
	    return false;
	}
	else if(col == COL_ARGUMENTS)
	{
	    return m_args.remove(n);
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
	else if(col == COL_ARGUMENTS)
	{
	    return (TreeNode)m_args.get(pos);
	}
	return null;
    }

}
