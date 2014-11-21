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
 * The <code>Block</code> object represents a collection of statements
 * separated by "{" and "}".  
 *
 * @author Andrew Violette
 * 
 */

public final class Block 
extends Statement
{
    /**
     * block - the node's label.
     */

    public static final String LABEL = "block";

    /**
     * 0 - the collection identifier for the block's children
     */

    public static final int COL_STATEMENTS = 0;
    public int getCollectionCount()
    {
	return 1;
    }
    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Statement.class, "The collection of statements in the block.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    private Vector m_statements;

    /**
     * Constructs a block object
     */

    public Block()
    {
	m_statements = new Vector();
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }
    
    public void accept(final ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    /**
     * Returns an iterator that iterates across the block's collection
     * of statements.  
     */

    public Iterator statements()
    {
	return m_statements.iterator();
    }

    /**
     * Adds a statement to the block's collection of statements.
     */

    public void addStatement(final Node n, final int pos)
    {
	m_statements.add(pos, n);
	n.setParent(this, COL_STATEMENTS);
    }

    /**
     * Appends a statement to the block's collection of statements.  
     */

    public void addStatement(final Node n)
    {
	m_statements.addElement(n);
	n.setParent(this, COL_STATEMENTS);
    }

    public Iterator children(final int col)
    {
	debug.Debug.assert(col == COL_STATEMENTS);
	if(col == COL_STATEMENTS)
	{
	    return m_statements.iterator();
	}
	return null;
    }
    
    public int childCount(final int col)
    {
	debug.Debug.assert(col == COL_STATEMENTS);
	if(col == COL_STATEMENTS)
	{
	    return m_statements.size();
	}
	return 0;
    }

    public boolean removeChild(final TreeNode n)
    {
	if(n.getCollection() == COL_STATEMENTS)
	{
	    return m_statements.remove(n);
	}
	debug.Debug.assert(false);
	return false;
    }

    public void addChild(final TreeNode n, final int col, final int pos)
    {
	if(col == COL_STATEMENTS)
	{
	    addStatement((Node)n, pos);
	}
    }

    public TreeNode childAt(int col, int pos)
    {
	debug.Debug.assert(col == COL_STATEMENTS);
	return (TreeNode)m_statements.get(pos);
    }

}
