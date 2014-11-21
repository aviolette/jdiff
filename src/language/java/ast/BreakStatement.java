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
 * The <code>BreakStatement</code> node represents a break statement.
 * A break statement has the form: <code>break [&lt;label&gt;]</code>.  
 */

public final class BreakStatement
extends Statement
{
    private String m_label;
    /**
     * break - the node's label
     */
    public static final String LABEL = "break";
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
	map.put("BreakLabel", new TreeNode.AttributeMetaData(String.class, true, null, "The target of the break statement."));
    }

    public int getCollectionCount()
    {
	return 0;
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    /**
     * Constructs an empty break statement.  */

    public BreakStatement()
    {
    }

    /**
     * Returns the label that the break targets, or <code>null</code>
     * if it goes to the end of the inner-most loop.  
     */

    public String getBreakLabel()
    {
	return m_label;
    }
    
    /**
     * Sets the label that the break targets.  
     * @param label Either a label or a <code>null</code> if the break
     * goes to the inner-most loop.  
     */

    public void setBreakLabel(String label)
    {
	m_label = label;
    }
    
    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    public String toString()
    {
	java.util.Vector v = getStandardDescriptiveElements();
	v.addElement(nullString(getBreakLabel()));
	return createDescriptiveTuple(LABEL, v.iterator());	
    }
    public void addChild(TreeNode n, int col, int pos)
    {
	debug.Debug.assert(false);
    }
    public boolean removeChild(TreeNode node)
    {
	debug.Debug.assert(false);
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
	debug.Debug.assert(false);
	return null;
    }
}
