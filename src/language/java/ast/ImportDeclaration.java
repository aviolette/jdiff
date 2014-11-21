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
 * The <code>ImportDeclaration</code> class defines an import of a
 * package or class definition into type declaration's namespace.
 * 
 * @author Andrew Violette
 */

public final class ImportDeclaration
extends Node
{
    private boolean m_onDemand;
    private String m_name;
    public static final String LABEL = "import";

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
						       null, "The name of the package or class to import."));
	map.put("OnDemand", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE, "Specifies whether the Name field specifies a package or a class."));
    }

    public int getCollectionCount()
    {
	return 0;
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    public ImportDeclaration()
    {
    }

    public ImportDeclaration(String name, boolean onDemand)
    {
	m_onDemand = onDemand;
	m_name = name;
    }

    public boolean equivalent(Object o)
    {
	if(o instanceof ImportDeclaration)
	{
	    ImportDeclaration d = (ImportDeclaration)o;
	    return d.getSignature().equals(getSignature());
	}
	return false;
    }
    
    public void accept(ASTTraverser traverser)
    {
	traverser.visit(this);
    }

    private String getSignature()
    {
	return getName() + ((isOnDemand()) ? ".*" : "");
    }

    public String getName()
    {
	return m_name;
    }

    public void setName(String name)
    {
	m_name = name;
    }

    public void setOnDemand(boolean b)
    {
	m_onDemand = b;
    }

    public boolean isOnDemand()
    {
	return m_onDemand;
    }

    public String toString()
    {
	java.util.Vector v = getStandardDescriptiveElements();
	v.addElement(nullString(getName()));
	v.addElement(new Boolean(isOnDemand()));
	return createDescriptiveTuple(LABEL, v.iterator());
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
