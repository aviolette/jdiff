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
 * The <code>Initializer</code> node represents either a static or
 * instance initialization block.
 */

public final class Initializer
extends Node
{

    public static final String LABEL = "initializer";

    public static final int COL_BLOCK = 0;

    private static java.util.Map s_attributes;

    private boolean m_isStatic;

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
	map.put("Static", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE, "Specifies whether the block is static or not."));
    }


    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Block.class, "The block that contians the initialization statements.")
    };

    public final TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


    public int getCollectionCount()
    {
	return 1;
    }

    private Block m_block;

    /**
     * Constructs an empty initializer.
     */

    public Initializer()
    {
    }

    public void setStatic(boolean b)
    {
	m_isStatic = b;
    }

    public boolean isStatic()
    {
	return m_isStatic;
    }

    /**
     * Constructs a initializer, initialized with an block.
     * @param e a block that is non-null.
     */

    public Initializer(Block e)
    {
	setBlock(e);
    }

    /**
     * Sets the initialization block
     *
     */

    public void setBlock(Block e)
    {
	m_block = e;
	e.setParent(this, COL_BLOCK);
    }

    /**
     * Returns the block that is wrapped in parantheses.
     */

    public Block getBlock()
    {
	return m_block;
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    public Iterator children(int col)
    {
	if(col == COL_BLOCK)
	{
	    return new SingleValueIterator("Block");
	}
	debug.Debug.assert(false);
	return null;
    }

    public int childCount(int col)
    {
	if(col == COL_BLOCK)
	{
	    return (m_block == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_BLOCK)
	{
	    setBlock((Block)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    
    public boolean removeChild(TreeNode node)
    {
	if(node.getCollection() == COL_BLOCK)
	{
	    setBlock(null);
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
	if(col == COL_BLOCK)
	{
	    return m_block;
	}
	return null;
    }

}
