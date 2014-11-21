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
 * The <code>InterfaceDeclaration</code> class represents the
 * definition of an interface.
 */

public final class InterfaceDeclaration
extends TypeDeclaration
{

    /**
     * interface - the node's label.
     */

    public static final String LABEL = "interface";

    /**
     * 1 - the collection identifier for the list of interfaces that
     * this interface extends.  
     */

    public static final int COL_EXTENDS = 1;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Node.class, "The list of declarations (fields, classes, methods, etc.) that are defined by the interface declaration."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Name.class, "The list of interfaces that the interface extends.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    public int getCollectionCount()
    {
	return s_collectionData.length;
    }

    private Vector m_extends;
    private static Map s_attributes;

    protected Map attributeMap()
    {
	if(s_attributes == null)
	{
	    s_attributes = new HashMap();
	    initializeAttributeMap(s_attributes);
	}
	return s_attributes;
    }

    public String getPrivateLabel()
    {
	return LABEL;
    }

    /**
     * Constructs an <code>InterfaceDeclaration</code> object.
     */

    public InterfaceDeclaration()
    {
	this("");
    }

    /**
     * Constructs an <code>InterfaceDeclaration</code> object.
     */

    public InterfaceDeclaration(String name)
    {
	super(name);
	m_extends = new Vector();
    }

    /**
     * Returns an iterator that iterates over the list of interfaces
     * this interface extends.  
     */

    public Iterator extendedInterfaces()
    {
	return m_extends.iterator();
    }

    /**
     * Adds an interface name that this interface extends.
     */

    public void addExtendedInterface(Name n, int pos)
    {
	m_extends.add(pos, n);
	n.setParent(this, COL_EXTENDS);
    }

    /**
     * Appends an interface name to the list of interfaces that this
     * interface extends.
     */

    public void addExtendedInterface(Name n)
    {
	m_extends.addElement(n);
	n.setParent(this, COL_EXTENDS);
    }


    public void accept(ASTTraverser traverser)
    {
	traverser.visit(this);
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_EXTENDS)
	{
	    addExtendedInterface((Name)n, pos);
	}
	else
	{
	    super.addChild(n, col, pos);
	}
    }

    public boolean removeChild(TreeNode n)
    {
	if(n.getCollection() == COL_EXTENDS)
	{
	    return m_extends.remove(n);
	}
	return super.removeChild(n);
    }
    
    public int childCount(int col)
    {
	if(col == COL_EXTENDS)
	{
	    return m_extends.size();
	}
	return super.childCount(col);
    }

    public java.util.Iterator children(int col)
    {
	if(col == COL_EXTENDS)
	{
	    return extendedInterfaces();
	}
	return super.children(col);
    }
    
    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_EXTENDS)
	{
	    return (TreeNode)m_extends.get(pos);
	}
	return super.childAt(col, pos);
    }
    
}
