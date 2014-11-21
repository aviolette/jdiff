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
 * The <code>Identifier</code> class represents a single Java
 * identifier (a string of letters and numbers that are not Java
 * keywords).
 */

public final class Identifier
extends PrimaryExpression
{
    private String m_name;
    public static final String LABEL = "identifier";

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
	map.put("Name", new TreeNode.AttributeMetaData(String.class, false, 
						       null, "The name of the identifier."));
    }

    public int getCollectionCount()
    {
	return 0;
    }

    /**
     * Constructs an <code>Identifier</code> object.
     */

    public Identifier()
    {
    }

    /**
     * Constructs an <code>Identifier</code> object initialized with a name.
     */

    public Identifier(String name)
    {
	m_name = name;
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    /**
     * Returns true if the object equals the identifier.  If the
     * object is an identifier, it is equal if the string it contains
     * equals the string contained by the other identifier.  If the
     * object is not an identifier, this method always returns
     * <code>false</code>.  
     */

    public boolean equals(Object o)
    {
	if(o == null) return false;
	if(!(o instanceof Identifier)) return false;
	if(((Identifier)o).m_name == null) return false;
	return ((o instanceof Identifier) && (((Identifier)o).m_name.equals(m_name)));
    }

    /**
     * Returns the value of the identifier.
     */

    public String getName()
    {
	return m_name;
    }

    public String toString()
    {
	java.util.Vector v = getStandardDescriptiveElements();
	v.addElement(getName());
	return createDescriptiveTuple(LABEL, v.iterator());	
    }

    /**
     * Sets the value of the identifier.
     */

    public void setName(String n)
    {
	m_name = n;
    }
    
    protected String getPrivateLabel()
    {
	return LABEL;
    }
    public void addChild(TreeNode n, int col, int pos)
    {
	debug.Debug.assert(false);
    }

    public boolean removeChild(TreeNode n)
    {
	return false;
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

    public TreeNode childAt(int col, int pos)
    {
	return null;
    }

}
