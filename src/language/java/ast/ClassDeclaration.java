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
 * The <code>ClassDeclaration</code> class represents the definition
 * of a class.  A class declaration extends one super class and
 * optionally implements multiple interfaces.  
 */

public final class ClassDeclaration 
extends TypeDeclaration
{

    /**
     * class - the node's label
     */

    public static final String LABEL = "class";

    /**
     * 1 - the collection identifier for the collection of interfaces
     * that this class implements.  
     */

    public static final int COL_IMPLEMENTS = 1;


    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Node.class, "The field, class, and method declarations."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Name.class, "The implemented interfaces.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    public int getCollectionCount()
    {
	return s_collectionData.length;
    }



    private java.util.Vector m_implements;
    private String m_super;
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


    protected void initializeAttributeMap(java.util.Map map)
    {
	map.put("Extends", new TreeNode.AttributeMetaData(String.class, true, null, 
							  "The name of the class that this class extends."));
	super.initializeAttributeMap(map);
    }



    public ClassDeclaration()
    {
	this("");
    }

    /**
     * Constructor for <code>ClassDeclaration</code> objects.  The
     * signature is the name of the class.  
     */

    public ClassDeclaration(String name)
    {
	super(name);
	m_implements = new java.util.Vector();
    }

    /**
     * Sets the name of the super class that this class extends.
     */

    public void setExtends(String parent)
    {
	m_super = parent;
    }
    
    /**
     * Returns the name of the class that this class extends, or
     * <code>null</code> if it doesn't explicitly extend any class.  
     */

    public String getExtends()
    {
	return m_super;
    }

    public void accept(ASTTraverser traverser)
    {
	traverser.visit(this);
    }

    /**
     * Adds an interface to the list of interfaces that this class
     * implements.  
     * @param n the name of the interface
     * @param pos the position of name in the collection
     */

    public void addImplementedInterface(Name n, int pos)
    {
	m_implements.add(pos, n);
	n.setParent(this, COL_IMPLEMENTS);
    }

    /**
     * Appends an interface to the list of interfaces that this class implements.
     * @param n the name of the interface
     */

    public void addImplementedInterface(Name n)
    {
	m_implements.addElement(n);
	n.setParent(this, COL_IMPLEMENTS);
    }

    /**
     * Returns an iterator which iterators over the names of
     * interfaces that this class implements.
     */

    public Iterator implementedInterfaces()
    {
	return m_implements.iterator();
    }

    /**
     * Sets the list of interfaces that the class extends.  
     */

    public void addImplementsList(java.util.Collection c)
    {
	// m_implements.retainAll(c);
	m_implements.clear();
	if(c == null) return;

	Iterator i = c.iterator();
	while(i.hasNext())
	{
	    addImplementedInterface((Name)(i.next()));
	}
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    public String toString()
    {
	Vector v = getStandardDescriptiveElements();
	v.addElement(nullString(getName()));
	return createDescriptiveTuple(LABEL, v.iterator());
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_IMPLEMENTS)
	{
	    addImplementedInterface((Name)n, pos);
	}
	else
	{
	    super.addChild(n, col, pos);
	}
    }

    public boolean removeChild(TreeNode n)
    {
	if(n.getCollection() == COL_IMPLEMENTS)
	{
	    return m_implements.remove(n);
	}
	return super.removeChild(n);
    }
    
    public int childCount(int col)
    {
	if(col == COL_IMPLEMENTS)
	{
	    return m_implements.size();
	}
	return super.childCount(col);
    }

    public java.util.Iterator children(int col)
    {
	if(col == COL_IMPLEMENTS)
	{
	    return implementedInterfaces();
	}
	return super.children(col);
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_IMPLEMENTS)
	{
	    return (TreeNode)m_implements.get(pos);
	}
	return null;
    }

}
