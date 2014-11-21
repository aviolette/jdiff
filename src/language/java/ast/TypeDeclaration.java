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
 * The <code>TypeDeclaration</code> class represents the definition
 * of a class or interface.  It defines the common attributes (such as
 * visibility and name) between interface and class definitions.
 */

public abstract class TypeDeclaration
extends Node
{
    public static final int COL_DECLARATIONS = 0;

    private boolean m_abstract;
    private boolean m_final;
    private boolean m_static;

    public int getCollectionCount()
    {
	return 1;
    }

    private String m_name;
    private Visibility m_visibility;
    private Vector m_declarations;

    protected void initializeAttributeMap(Map map)
    {
	super.initializeAttributeMap(map);
	map.put("Final", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE,
							"True if the type is final."));
	map.put("Static", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE,
							 "True if the type is static."));
	map.put("Abstract", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE,
							   "True if the type is abstract."));
	map.put("Name", new TreeNode.AttributeMetaData(String.class, false, null,
						       "The type's name."));
	map.put("Visibility", new TreeNode.AttributeMetaData(Visibility.class, false, 
							     "package private", "The type's visibility."));
    }


    /**
     * Constructs a <code>TypeDeclaration</code> object.
     * @param name the name of the type
     */

    public TypeDeclaration(String name)
    {
	m_name = name;
	m_visibility = Visibility.DEFAULT;
	m_declarations = new Vector();
    }

    /**
     * Returns true if the class is abstract.
     */

    public boolean isAbstract()
    {
	return m_abstract;
    }

    /**
     * Sets whether the class is abstract.
     */

    public void setAbstract(boolean isAbstract)
    {
	m_abstract = isAbstract;
    }

    /**
     * Sets the visibility of the class or interface.
     */

    public final void setVisibility(Visibility visibility)
    {
	m_visibility = visibility;
    }

    /**
     * Returns the visibility of the class or interface.
     */

    public final Visibility getVisibility()
    {
	return m_visibility;
    }

    /**
     * Sets the name of the class or interface.
     */

    public final void setName(String name)
    {
	m_name = name;
    }

    /**
     * Returns the name of the class or interface.
     */

    public final String getName()
    {
	return m_name;
    }

    public final Iterator declarations()
    {
	return m_declarations.iterator();
    }

    public final void addDeclaration(Node n)
    {
	m_declarations.addElement(n);
	n.setParent(this, COL_DECLARATIONS);
    }

    public final void addDeclaration(Node n, int pos)
    {
	m_declarations.add(pos, n);
	n.setParent(this, COL_DECLARATIONS);
    }
    /**
     * Sets whether the class is static.  This is only valid for inner
     * classes.  
     */

    public void setStatic(boolean isStatic)
    {
	m_static = isStatic;
    }
    
    /**
     * Returns whether the class is static.  This is only valid for
     * inner classes.  
     */
    
    public boolean isStatic()
    {
	return m_static;
    }

    /**
     * Returns true if the class is final.
     */

    public boolean isFinal()
    {
	return m_final;
    }

    /**
     * Sets whether the class is final.
     */

    public void setFinal(boolean isFinal)
    {
	m_final = isFinal;
    }

    public boolean removeChild(TreeNode n)
    {
	if(n.getCollection() == COL_DECLARATIONS)
	{
	    return m_declarations.remove(n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
	return false;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_DECLARATIONS)
	{
	    addDeclaration((Node)n, pos);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }
    public int childCount(int id)
    {
	if(id == COL_DECLARATIONS)
	{
	    return m_declarations.size();
	}
	return 0;
    }

    public Iterator children(int id)
    {
	if(id == COL_DECLARATIONS)
	{
	    return declarations();
	}
	return null;
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_DECLARATIONS)
	{
	    return (TreeNode)m_declarations.get(pos);
	}
	return null;
    }

}

