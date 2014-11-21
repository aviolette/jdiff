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
 * The <code>ContinueStatement</code> node represents a continue statement.
 * A continue statement has the form: <code>continue [&lt;label&gt;]</code>.  
 */

public final class ContinueStatement
extends Statement
{
    private String m_label;
    public static final String LABEL = "continue";


    private static java.util.Map s_attributes;

    protected void initializeAttributeMap(Map map)
    {
	map.put("ContinueLabel", new TreeNode.AttributeMetaData(String.class, true, null, "The target of the continue statement."));
	super.initializeAttributeMap(map);
    }

    protected Map attributeMap()
    {
	if(s_attributes == null)
	{
	    s_attributes = new java.util.HashMap();
	    initializeAttributeMap(s_attributes);
	}
	return s_attributes;
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
     * Constructs an empty continue statement.  */

    public ContinueStatement()
    {
    }

    /**
     * Returns the label that the continue targets, or <code>null</code>
     * if it goes to the end of the inner-most loop.  
     */

    public String getContinueLabel()
    {
	return m_label;
    }
    
    /**
     * Sets the label that the continue targets.  
     * @param label Either a label or a <code>null</code> if the continue
     * goes to the inner-most loop.  
     */

    public void setContinueLabel(String label)
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
	v.addElement(nullString(getContinueLabel()));
	return createDescriptiveTuple(LABEL, v.iterator());	
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

    public TreeNode childAt(int col, int pos)
    {
	return null;
    }

}
