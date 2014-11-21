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
 * The <code>MethodDeclaration</code> class defines a method declaration.
 */

public final class MethodDeclaration 
extends AbstractMethodDeclaration
{
    public static final int COL_TYPE = 3;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Parameter.class, "The parameter list."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Block.class, "The code block."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Name.class, "The throws list."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Type.class, "The return type.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    public int getCollectionCount()
    {
	return s_collectionData.length;
    }

    private boolean m_native = false;
    private boolean m_synchronized = false;
    private Type m_returnType;
    private boolean m_static = false;
    private boolean m_final = false;
    private boolean m_abstract = false;

    public static final String LABEL = "method";
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
	map.put("Native", new TreeNode.AttributeMetaData(Boolean.TYPE,  false, Boolean.FALSE, 
							 "True if the method is native."));
	map.put("Synchronized", new TreeNode.AttributeMetaData(Boolean.TYPE,  false, 
							       Boolean.FALSE, "True if the method is synchronized."));
	map.put("Static", new TreeNode.AttributeMetaData(Boolean.TYPE,  false,
		Boolean.FALSE, "True if the method is static." ));
	map.put("Final", new TreeNode.AttributeMetaData(Boolean.TYPE,  false,
		Boolean.FALSE, "True if the method is final."));
	map.put("Abstract", new TreeNode.AttributeMetaData(Boolean.TYPE,  false,
		Boolean.FALSE, "True if the method is abstract."));
	super.initializeAttributeMap(map);
    }



    public void setReturnType(Type t)
    {
	if(t == null)
	{
	    m_returnType = new Type("void");
	}
	else
	{
	    m_returnType = t;
	}
	m_returnType.setParent(this, COL_TYPE);
    }

    public void setAbstract(boolean isAbstract)
    {
	m_abstract = isAbstract;
    }

    public boolean isAbstract()
    {
	return m_abstract;
    }
    public boolean isFinal()
    {
	return m_final;
    }
    public void setFinal(boolean isFinal)
    {
	m_final = isFinal;
    }

    public boolean isStatic()
    {
	return m_static;
    }

    public void setStatic(boolean isStatic)
    {
	m_static = isStatic;
    }

    public Type getReturnType()
    {
	return m_returnType;
    }

    public MethodDeclaration()
    {
	super();
    }

    public String toString()
    {
	Vector v = getStandardDescriptiveElements();
	v.addElement(getName());
	v.addElement(getReturnType());
	v.addElement(createDescriptiveTuple("parameters", parameters()));
	return createDescriptiveTuple(getPrivateLabel(), v.iterator());

    }
    
    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    public boolean isNative()
    {
	return m_native;
    }
    
    public void setNative(boolean isNative)
    {
	m_native = isNative;
    }

    public boolean isSynchronized()
    {
	return m_synchronized;
    }

    public void setSynchronized(boolean isSynchronized)
    {
	m_synchronized = isSynchronized;
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    public boolean removeChild(TreeNode n)
    {
	if(n.getCollection() == COL_TYPE)
	{
	    return false;
	}
	return super.removeChild(n);
    }
    public void addChild(TreeNode node, int col, int pos)
    {
	if(col == COL_TYPE)
	{
	    setReturnType((Type)node);
	}
	else
	{
	    super.addChild(node, col, pos);
	}
    }
    
    public Iterator children(int id)
    {
	if(id == COL_TYPE)
	{
	    return new SingleValueIterator("ReturnType");
	}
	return super.children(id);
    }

    public int childCount(int id)
    {
	if(id == COL_TYPE)
	{
	    return 1;
	}
	return super.childCount(id);
    }
    
    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_TYPE)
	{
	    return m_returnType;
	}
	return super.childAt(col, pos);
    }

}
