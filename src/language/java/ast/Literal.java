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
 * The <code>Literal</code> class represents a literal value such as a
 * string, integer, boolean, etc.  For example, "foo", 13.23, and true
 * are literals.
 */

public final class Literal
extends PrimaryExpression
{

    public static final String LABEL = "literal";

    private Object m_value;
    private Type m_type;

    public static final int COL_TYPE = 0;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Type.class, "The type of the literal.")
    };

    public final TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    public int getCollectionCount()
    {
	return 1;
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
	map.put("Value", new TreeNode.AttributeMetaData(Object.class, false, null, "The value of the literal."));
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    /**
     * Constructs a literal.
     */

    public Literal()
    {
    }

    /**
     * Constructs a literal associated with a type.
     */

    public Literal(Type type)
    {
	setType(type);
    }

    /**
     * Sets the literal's value.  If the literal is a primitive type,
     * it should be converted to one of its peer classes.
     */

    public void setValue(Object o)
    {
	m_value = o;
    }
    
    protected String getPrivateLabel()
    {
	return LABEL;
    }

    /**
     * Returns the literal's value.
     */

    public Object getValue()
    {
	return m_value;
    }

    public String toString()
    {
	java.util.Vector v = getStandardDescriptiveElements();
	v.addElement(m_value);
	v.addElement(m_type);
	return createDescriptiveTuple(LABEL, v.iterator());
    }

    /**
     * Sets the type associated with the primitive.
     */

    public void setType(Type type)
    {
	m_type = type;
	m_type.setParent(this, COL_TYPE);
    }

    /**
     * Returns the primitive's type.
     */

    public Type getType()
    {
    	return m_type;
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_TYPE)
	{
	    return m_type;
	}
	return null;
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
	    // not nullable
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

}
