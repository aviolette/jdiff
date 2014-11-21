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
 * Initializes an array with some initial values.  An array
 * initializer can either contain expressions or other array
 * initializers.  For example, the right hand side of this expression
 * is the array initializer: int a[][] = {{1,2,3}, {3, 4, 5}}.
 */

public final class ArrayInitializer
extends PrimaryExpression
{
    /**
     * arrayinit - the node's label */

    public static final String LABEL = "arrayinit";

    /**
     * 0 - the collection identifier for the list of elements that
     * initialize the array
     */

    public static final int COL_INITS = 0;



    public String getPrivateLabel()
    {
	return LABEL;
    }

    public int getCollectionCount()
    {
	return 1;
    }

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_n,
					Node.class, 
					"The collection of expressions or other array initializers that initialze the array.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


    private Vector m_inits;

    /**
     * Constructs an <code>ArrayInitializer</code>
     */

    public ArrayInitializer()
    {
	m_inits = new Vector();
    }

    /**
     * Adds an initializer to the end of the list of initializers.
     */

    public void addInit(Node node)
    {
	m_inits.addElement(node);
	node.setParent(this, COL_INITS);
    }

    /**
     * Adds an initializer to the list of initializers at the specified position.
     */

    public void addInit(Node node, int pos)
    {
	m_inits.add(pos, node);
	node.setParent(this, COL_INITS);
    }

    /**
     * Returns an <code>Iterator</code> that iterates over the list of initializers.
     */

    public Iterator inits()
    {
	return m_inits.iterator();
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    public Iterator children(int col)
    {
	if(col == COL_INITS)
	{
	    return m_inits.iterator();
	}
	debug.Debug.assert(false);
	return null;
    }

    public int childCount(int col)
    {
	if(col == COL_INITS)
	{
	    return m_inits.size();
	}
	debug.Debug.assert(false);
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_INITS)
	{
	    addInit((Node)n, pos);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    
    public boolean removeChild(TreeNode node)
    {
	if(node.getCollection() == COL_INITS)
	{
	    return m_inits.remove(node);
	}
	debug.Debug.assert(false);
	return false;
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_INITS)
	{
	    return (TreeNode)m_inits.get(pos);
	}
	return null;
    }

}
