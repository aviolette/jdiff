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
 * The <code>Name</code> class represents a collection of Java identifiers.
 */

public final class Name
extends PrimaryExpression
{
    public static final String LABEL = "name";

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
	map.put("Value", new TreeNode.AttributeMetaData(String.class, false, null, "The name."));
    }

    public int getCollectionCount()
    {
	return 0;
    }


    private java.util.Vector m_ids;

    public Name()
    {
	m_ids = new java.util.Vector();
    }

    public Name(String identifier)
    {
	this();
	addIdentifier(identifier);
    }

    public boolean equals(Object o)
    {
	if(o == null) return false;

	if(o == this) return true;

	if(o instanceof String)
	{
	    return getValue().equals(o);
	}

	else if(o instanceof Name)
	{
	    return getValue().equals(((Name)o).getValue());
	}

	return false;

    }

    public void setValue(String s)
    {
	m_ids.clear();
	StringTokenizer tokenizer = new StringTokenizer(s, ".", false);
	while(tokenizer.hasMoreTokens())
	{
	    addIdentifier(tokenizer.nextToken());
	}
    }

    public void addIdentifier(String identifier)
    {
	m_ids.addElement(identifier);
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    protected final String getPrivateLabel()
    {
	return LABEL;
    }

    public String getValue()
    {
	Identifier id;
	StringBuffer s = new StringBuffer();
	for(java.util.Iterator i = m_ids.iterator(); i.hasNext(); )
	{
	    s.append((String)i.next());
	    if(i.hasNext())
	    {
		s.append(".");
	    }
	}
	return s.toString();
    }

    public boolean removeChild(TreeNode n)
    {
	return false;
    }
    public void addChild(TreeNode n, int col, int pos)
    {
	debug.Debug.assert(false);
    }

    public java.util.Iterator children(int col)
    {
	debug.Debug.assert(false);
	return null;
    }
    
    public int childCount(int col)
    {
	debug.Debug.assert(false);
	return 0;
    }

    public String toString()
    {
	java.util.Vector v = getStandardDescriptiveElements();
	v.addElement(getValue());
	return createDescriptiveTuple(LABEL, v.iterator());	
    }

    public TreeNode childAt(int col, int pos)
    {
	return null;
    }
}
