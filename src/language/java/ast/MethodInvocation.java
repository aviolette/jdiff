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
 * The <code>MethodInvocation</code> class a node that represents the
 * invocation of an object's method.
 * 
 * @author Andrew Violette 
 */

public final class MethodInvocation
   extends PrimaryExpression
{
   public static final int COL_ARGUMENTS = 0;
   public static final int COL_EXPRESSION = 1;

   private static Map s_attributes;

   private final static TreeNode.CollectionMetaData s_collectionData[] = 
   {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
						  Expression.class, "The argument list."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
						  Expression.class, "The expression that evaluates to the method.")
   };

   protected void initializeAttributeMap(Map map)
   {
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

   public TreeNode.CollectionMetaData getCollectionMetaData(int id)
   {
	return s_collectionData[id];
   }

   public int getCollectionCount()
   {
	return 2;
   }

   private String m_name;
   private Expression m_expr;
   private Vector m_args;

   public static final String LABEL = "methodcall";

   public final String getName()
   {
	return m_name;
   }

   public final void setName(final String name)
   {
	debug.Debug.assert(name != null);
	m_name = name;
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

   public MethodInvocation()
   {
	m_args = new Vector();
   }

   public MethodInvocation(Expression expr, Vector args)
   {

	// According to the Java language specification an invocation
	// consists of any expression, but I cannot figure out when it
	// won't be a Name.  Since I assume this will always be a name,
	// I can return the string representation of the name in the
	// getName() method.

	setExpression(expr);

	if(expr instanceof Name)
	{
	   setName(((Name)expr).getValue());
	}

	m_args = args;
	
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

   public void addArgument(Expression e, int pos)
   {
	m_args.add(pos, e);
	e.setParent(this, COL_ARGUMENTS);
   }

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


   public String toString()
   {
	Vector v = getStandardDescriptiveElements();
	v.addElement(m_name);
	return createDescriptiveTuple(LABEL, v.iterator());
   }
   protected String getPrivateLabel()
   {
	return LABEL;
   }

   public Expression getExpression()
   {
	return m_expr;
   }

   public void setExpression(Expression expr)
   {
	m_expr = expr;
	expr.setParent(this, COL_EXPRESSION);
   }

   public Iterator children(int col)
   {
	if(col == COL_ARGUMENTS)
	{
	   return arguments();
	}
	else if(col == COL_EXPRESSION)
	{
	   return new SingleValueIterator("Expression");
	}
	debug.Debug.assert(false);
	return null;
   }

   public int childCount(int col)
   {
	if(col == COL_ARGUMENTS)
	{
	   return m_args.size();
	}
	else if(col == COL_EXPRESSION)
	{
	   return (m_expr == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
   }

   public void addChild(TreeNode n, int col, int pos)
   {
	if(col == COL_ARGUMENTS)
	{
	   addArgument((Expression)n, pos);
	}
	else if(col == COL_EXPRESSION)
	{
	   setExpression((Expression)n);
	}
	else
	{
	   debug.Debug.assert(false);
	}
   }
   public boolean removeChild(TreeNode n)
   {
	int col = n.getCollection();
	if(col == COL_ARGUMENTS)
	{
	   return m_args.remove(n);
	}
	else if(col == COL_EXPRESSION)
	{
	   debug.Debug.assert(n == m_expr);
	   m_expr = null;
	}
	else
	{
	   debug.Debug.assert(false);
	}
	return false;
   }

   public TreeNode childAt(int col, int pos)
   {
	if(col == COL_ARGUMENTS)
	{
	   return (TreeNode)m_args.get(pos);
	}
	else if(col == COL_EXPRESSION)
	{
	   return m_expr;
	}
	return null;
   }

}
