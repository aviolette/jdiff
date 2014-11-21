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
 * The <code>VariableDeclaration</code> class represents a collection
 * of variable declarations of a specific type.  The children of this
 * class are <code>VariableDeclarator</code> objects that define the a
 * variable and possibly initialize it with a value from an expression
 * or an array intializer.  
 *
 */

public abstract class VariableDeclaration
extends Node
{

    public static final int COL_TYPE = 0;
    public static final int COL_VARIABLES = 1;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Type.class, "The type."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_n,
					VariableDeclarator.class, "The variables.")
    };

    public final TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    public int getCollectionCount()
    {
	return 2;
    }

    protected void initializeAttributeMap(Map map)
    {
	map.put("Final", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE, "True if the variables are final."));
	super.initializeAttributeMap(map);

    }
    private Type m_type;
    private boolean m_final;
    private Vector m_variables;

    /**
     * Constructs an empty <code>VariableDeclaration</code> object.
     */

    public VariableDeclaration()
    {
	m_final = false;
	m_variables = new Vector();
    }
    
    /**
     * Sets the type of the declarations.
     */

    public final void setType(Type t)
    {
	m_type = t;
	m_type.setParent(this, COL_TYPE);
    }

    /**
     * Returns the type of the declarations.
     */

    public final Type getType()
    {
	return m_type;
    }

    /**
     * Returns true if the variables are final.
     */

    public final boolean isFinal()
    {
	return m_final;
    }

    /**
     * Sets whether the variables are final.
     */

    public final void setFinal(boolean isFinal)
    {
	m_final = isFinal;
    }

    public final void addVariable(VariableDeclarator v)
    {
	m_variables.addElement(v);
	v.setParent(this, COL_VARIABLES);
    }

    public final void addVariable(VariableDeclarator v, int pos)
    {
	m_variables.add(pos, v);
	v.setParent(this, COL_VARIABLES);
    }


    public final Iterator variables()
    {
	return m_variables.iterator();
    }

    public final void addChild(TreeNode n, int col, int pos)
    {
	switch(col)
	{
	case COL_VARIABLES:
	    addVariable((VariableDeclarator)n, pos);
	    break;
	case COL_TYPE:
	    setType((Type)n);
	    break;
	default:
	    debug.Debug.assert(false);
	    break;
	}
    }
    public final boolean removeChild(TreeNode n)
    {
	switch(n.getCollection())
	{
	case COL_VARIABLES:
	    return m_variables.remove(n);
	case COL_TYPE:
	    // not nullable
	    return false;
	default:
	    debug.Debug.assert(false);
	    break;
	}
	return false;
    }

    public final Iterator children(int col)
    {
	if(col == COL_TYPE)
	{
	    return new SingleValueIterator("Type");
	}
	else if(col == COL_VARIABLES)
	{
	    return variables();
	}
	return null;
    }

    public final int childCount(int col)
    {
	if(col == COL_TYPE)
	{
	    return m_type == null ? 0 : 1;
	}
	else if(col == COL_VARIABLES)
	{
	    return m_variables.size();
	}
	return 0;
    }
    
    public final TreeNode childAt(int col, int pos)
    {
	if(col == COL_TYPE)
	{
	    return m_type;
	}
	else if(col == COL_VARIABLES)
	{
	    return (TreeNode)m_variables.get(pos);
	}
	return null;
    }
}

