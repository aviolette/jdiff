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
 * The <code>ClassAccess</code> class represents the accessing of a
 * class object from a type.  The format is the following:
 * &lt;type&gt;.class.
 */

public final class ClassAccess
extends PrimaryExpression
{
    private Type m_type;

    /**
     * 0 - The collection identifier for the ClassAccess's type.  
     */

    public static final int COL_TYPE = 0;

    public int getCollectionCount()
    {
	return 1;
    }
    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Type.class, "The type whose class object is being accessed.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


    /**
     * class_access - the node's label.
     */

    public static final String LABEL = "class_access";

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    /**
     * Constructs a ClassAccess object.  
     * @param t a non-null type field describing the type that is
     * being accessed.
     */

    public ClassAccess(Type t)
    {
	setType(t);
    }

    public ClassAccess()
    {
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    /**
     * Returns the type of the class field that is being accessed.
     */

    public Type getType()
    {
	return m_type;
    }

    /**
     * Sets the type of the class field that is being accessed.
     */

    public void setType(Type t)
    {
	m_type = t;
	t.setParent(this, COL_TYPE);
    }

    public Iterator children(int col)
    {
	if(col == COL_TYPE)
	{
	    return new SingleValueIterator("Type");
	}
	debug.Debug.assert(false);
	return null;
    }

    public int childCount(int col)
    {
	if(col == COL_TYPE)
	{
	    return (m_type == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    public boolean removeChild(TreeNode n)
    {
	if(n.getCollection() == COL_TYPE)
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
	if(col == COL_TYPE)
	{
	    setType((Type)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    
    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_TYPE)
	{
	    return m_type;
	}
	return null;
    }

}



