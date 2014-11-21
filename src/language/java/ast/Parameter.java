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
 * The <code>Parameter</code> class represents a parameter declaration
 * in a method declaration.  A parameter consists of a type, one
 * defined variable and an optional 'final' property.
 */

public final class Parameter
extends Node
{
    public final static int COL_VARIABLE = 0;
    public final static int COL_TYPE = 1;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					VariableDeclarator.class, "The variable."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Type.class, "The variable's type.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }



    private static java.util.Map s_attributes;

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
	map.put("Final", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE, "True if the parameter is final."));
    }

    public int getCollectionCount()
    {
	return 2;
    }


    public static final String LABEL = "parameter";

    private Type m_type;
    private VariableDeclarator m_dec;
    private boolean m_isFinal;

    public Parameter()
    {
    }
    
    /**
     * Returns <code>true</code> if the parameter is final.
     */

    public boolean isFinal()
    {
	return m_isFinal;
    }
    
    /** 
     * Set the finality of the parameter.
     */

    public void setFinal(boolean isFinal)
    {
	m_isFinal = isFinal;
    }
    
    /**
     * Sets the variable declarator for the parameter.
     */

    public void setVariable(VariableDeclarator dec)
    {
	m_dec = dec;
	dec.setParent(this, COL_VARIABLE);
    }

    /**
     * Returns the variable declarator for the parameter.
     */

    public VariableDeclarator getVariable()
    {
	return m_dec;
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }
    
    /**
     * Sets the type of the variable.
     */

    public void setType(Type type)
    {
	m_type = type;
	m_type.setParent(this, COL_TYPE);
    }

    /**
     * Returns the type of the variable.
     */

    public Type getType()
    {
	return m_type;
    }

    public Iterator children(int col)
    {
	if(col == COL_TYPE)
	{
	    return new SingleValueIterator("Type");
	}
	else if(col == COL_VARIABLE)
	{
	    return new SingleValueIterator("Variable");
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
	else if(col == COL_VARIABLE)
	{
	    return (m_dec == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }
    public boolean removeChild(TreeNode n)
    {
	switch(n.getCollection())
	{
	case COL_TYPE:
	    return false;
	case COL_VARIABLE:
	    return false;
	default:
	    debug.Debug.assert(false);
	    break;
	}
	
	return false;
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_TYPE)
	{
	    return m_type;
	}
	else if(col == COL_VARIABLE)
	{
	    return m_dec;
	}
	return null;
    }



    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_TYPE)
	{
	    setType((Type)n);
	}
	else if(col == COL_VARIABLE)
	{
	    setVariable((VariableDeclarator)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }



}
