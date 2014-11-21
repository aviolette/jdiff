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
 * The <code>TryBlock</code> represents a try-catch-finally block.
 */

public final class TryBlock
extends Statement
{
    
    public static final String LABEL = "try";

    public static final int COL_FINALLY = 2;
    public static final int COL_TRY = 0;
    public static final int COL_CATCH = 1;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Block.class, "The try block."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_n,
					CatchBlock.class, "The catch blocks."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_1,
					Block.class, "The finally block.")
	
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }
    
    public int getCollectionCount()
    {
	return 3;
    }

    private Block m_finallyBlock = null;
    private Block m_tryBlock = null;
    private Vector m_catches = null;

    public String getPrivateLabel()
    {
	return LABEL;
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    /**
     * Adds the catch block to the try statement
     */

    public void addCatch(TryBlock.CatchBlock b)
    {
	debug.Debug.assert(b != null);
	b.setParent(this, COL_CATCH);
	m_catches.addElement(b);
    }
    
    /**
     * Adds the catch block to the try statement at the specified
     * position in the collection 
     */

    public void addCatch(TryBlock.CatchBlock b, int pos)
    {
	debug.Debug.assert(b != null);
	b.setParent(this, COL_CATCH);
	m_catches.add(pos, b);
    }

    /**
     * Returns an iterator that iterates over the catch blocks.
     */

    public Iterator catches()
    {
	return m_catches.iterator();
    }

    /**
     * Constructs a <code>TryBlock</code> object.
     */

    public TryBlock()
    {
	m_catches = new Vector();
    }

    /**
     * Sets the finally block.
     */

    public void setFinally(Block b)
    {
	m_finallyBlock = b;
	if(b != null)
	{
	    b.setParent(this, COL_FINALLY);
	}
    }

    /**
     * Returns the try block
     */

    public Block getTry()
    {
	return m_tryBlock;
    }

    /**
     * Sets the try block.
     */

    public void setTry(Block b)
    {
	m_tryBlock = b;
	if(b != null)
	{
	    b.setParent(this, COL_TRY);
	}
    }

    /** 
     * Returns the finally block.  If it is <code>null</code> there is
     * no finally block.  
     */

    public Block getFinally()
    {
	return m_finallyBlock;
    }
    public TreeNode childAt(int col, int pos)
    {
	switch(col)
	{
	case COL_TRY:
	    return m_tryBlock;
	case COL_CATCH:
	    return (TreeNode)m_catches.get(pos);
	case COL_FINALLY:
	    return m_finallyBlock;
	}
	return null;
    }

    public Iterator children(int col)
    {
	switch(col)
	{
	case COL_TRY:
	    return new SingleValueIterator("Try");
	case COL_CATCH:
	    return catches();
	case COL_FINALLY:
	    return new SingleValueIterator("Finally");
	default:
	    debug.Debug.assert(false);
	}
	return null;
    }

    public int childCount(int col)
    {
	switch(col)
	{
	case COL_TRY:
	    return (m_tryBlock == null) ? 0 : 1;
	case COL_CATCH:
	    return m_catches.size();
	case COL_FINALLY:
	    return (m_finallyBlock == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	switch(col)
	{
	case COL_TRY:
	    setTry((Block)n);
	    break;
	case COL_CATCH:
	    addCatch((CatchBlock)n, pos);
	    break;
	case COL_FINALLY:
	    setFinally((Block)n);
	    break;
	default:
	    debug.Debug.assert(false);
	}

    }
    public boolean removeChild(TreeNode n)
    {
	switch(n.getCollection())
	{
	case COL_TRY:
	    return false;
	case COL_CATCH:
	    return m_catches.remove(n);
	case COL_FINALLY:
	    setFinally(null);
	    return true;
	}
	debug.Debug.assert(false);
	return false;
    }

    /**
     * The <code>CatchBlock</code> node represents one catch block
     * within a <code>TryBlock</code> statement.
     */

    public final static class CatchBlock
    extends Node
    {
	public static final String LABEL = "catch";
	public static final int COL_PARAM = 0;
	public static final int COL_BLOCK = 1;

	private final static TreeNode.CollectionMetaData s_collectionData[] = 
	{
	    new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					    Parameter.class, "The parameter."),
	    new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					    Block.class, "The catch block.")
	
	};
	
	public TreeNode.CollectionMetaData getCollectionMetaData(int id)
	{
	    return s_collectionData[id];
	}
	public int getCollectionCount()
	{
	    return 2;
	}
	
	private Block m_catchBlock;
	private Parameter m_catchParameter;

	public void setBlock(Block b)
	{
	    m_catchBlock = b;
	    b.setParent(this, COL_BLOCK);
	}

	public Block getBlock()
	{
	    return m_catchBlock;
	}

	public void setParameter(Parameter p)
	{
	    m_catchParameter = p;
	    p.setParent(this, COL_PARAM);
	}

	public Parameter getParameter()
	{
	    return m_catchParameter;
	}

	public String getPrivateLabel()
	{
	    return LABEL;
	}

	/**
	 * Constructs a <code>CatchBlock</code> object.
	 */

	public CatchBlock()
	{
	}

	public void accept(ASTTraverser visitor)
	{
	    visitor.visit(this);
	}

	public Iterator children(int col)
	{
	    switch(col)
	    {
	    case COL_PARAM:
		return new SingleValueIterator("Parameter");
	    case COL_BLOCK:
		return new SingleValueIterator("Block");
	    default:
		debug.Debug.assert(false);
	    }
	    return null;
	}

	public int childCount(int col)
	{
	    switch(col)
	    {
	    case COL_PARAM:
		return (m_catchBlock == null) ? 0 : 1;
	    case COL_BLOCK:
		return (m_catchParameter == null) ? 0 : 1;
	    }
	    debug.Debug.assert(false);
	    return 0;
	}

	public boolean removeChild(TreeNode n)
	{
	    return false;
	}
	public void addChild(TreeNode n, int col, int pos)
	{
	    switch(col)
	    {
	    case COL_PARAM:
		setParameter((Parameter)n);
		break;
	    case COL_BLOCK:
		setBlock((Block)n);
		break;
	    default:
		debug.Debug.assert(false);
	    }
	}
	public TreeNode childAt(int col, int pos)
	{
	    switch(col)
	    {
	    case COL_PARAM:
		return m_catchParameter;
	    case COL_BLOCK:
		return m_catchBlock;
	    }
	    return null;
	}
    }

}
