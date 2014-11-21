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
 * The <code>ThrowStatement</code> node represents a throw statement.
 * A throw statement has the form: <code>throw [&lt;expression&gt;]</code>.  
 */

public final class ThrowStatement
   extends Statement
{
   private Expression m_expression;
   public static final String LABEL = "throw";

   public static final int COL_EXPRESSION = 0;

   
   private final static TreeNode.CollectionMetaData s_collectionMetaData[] =
   {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_1,
						  Expression.class, "The Expression to throw.")
   };

   public TreeNode.CollectionMetaData getCollectionMetaData(int id)
   {
	return s_collectionMetaData[id];
   }



   public int getCollectionCount()
   {
	return 1;
   }


   protected String getPrivateLabel()
   {
	return LABEL;
   }

   /**
    * Constructs an empty throw statement.  */

   public ThrowStatement()
   {
   }

   /**
    * Returns the label that the throw targets, or <code>null</code>
    * if it goes to the end of the inner-most loop.  
    */

   public Expression getExpression()
   {
	return m_expression;
   }
    
   /**
    * Sets the label that the throw targets.  
    * @param label Either a label or a <code>null</code> if the throw
    * goes to the inner-most loop.  
    */

   public void setExpression(Expression e)
   {
	m_expression = e;
	if(e != null)
	{
	   e.setParent(this, COL_EXPRESSION);
	}
	
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
   public boolean removeChild(TreeNode node)
   {
	if(node.getCollection() == COL_EXPRESSION)
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

   public TreeNode childAt(int col, int pos)
   {
	if(col == COL_EXPRESSION)
	{
	   return m_expression;
	}
	return null;
   }
    
}
