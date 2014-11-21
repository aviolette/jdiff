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

public final class SynchronizedBlock
extends Statement
{
    public static final int COL_BLOCK = 0;
    public static final int COL_EXPRESSION = 1;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression to synchronize."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Block.class, "The block to execute when safe.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    public int getCollectionCount()
    {
	return 2;
    }

    public static final String LABEL = "syncblock";

    private Block m_block;
    private Expression m_expression;

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    public String getPrivateLabel()
    {
	return LABEL;
    }

    public SynchronizedBlock()
    {
    }

    public void setBlock(Block b)
    {
	m_block = b;
	b.setParent(this, COL_BLOCK);
    }

    public Block getBlock()
    {
	return m_block;
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
    public Iterator children(int col)
    {
	if(col == COL_EXPRESSION)
	{
	    return new SingleValueIterator("Expression");
	}
	else if(col == COL_BLOCK)
	{
	    return new SingleValueIterator("Block");
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
	else if(col == COL_BLOCK)
	{
	    return (m_block == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    public boolean removeChild(TreeNode n)
    {
	int col = n.getCollection();
	if(col == COL_EXPRESSION)
	{
	    m_expression = null;
	    return true;
	}
	else if(col == COL_BLOCK)
	{
	    m_block = null;
	    return true;
	}
	debug.Debug.assert(false);
	return false;
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_EXPRESSION)
	{
	    return m_expression;
	}
	else if(col == COL_BLOCK)
	{
	    return m_block;
	}
	return null;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_EXPRESSION)
	{
	    setExpression((Expression)n);
	}
	else if(col == COL_BLOCK)
	{
	    setBlock((Block)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }

    
}
