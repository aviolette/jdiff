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

import java.util.Iterator;
import tree.*;

/**
 * The <code>Initialization</code> class represents the construction
 * of a new array or object.  An expression of this type either takes
 * the form: <code>'new' ( &lt;primitive_type&gt; | &lt;name&gt; )
 * ('[' &lt;expression&gt; ']' )+</code>,or <code>new &lt;name&gt; '('
 * (&lt;expression&gt;)* ')' [&lt;classBody&gt;]</code>.  The former
 * constructs an array, the later constructs an object.  
 */

public final class Initialization
extends PrimaryExpression
{

    public static final String LABEL = "init";
    public static final int COL_TYPE = 0;
    public static final int COL_CLASSBODY = 1;
    public static final int COL_ARGUMENT = 2;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Type.class, "The type of object that is being created."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_1,
					Block.class, "The definition of the class if an anonymous class is being used."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Expression.class, "The list of expression being passed into the constructor.")
    };

    public final TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    public int getCollectionCount()
    {
	return 3;
    }



    private Type m_type;
    private Block m_classBody;
    private java.util.Vector m_arguments;

    protected String getPrivateLabel()
    {
	return LABEL;
    }
    
    public Initialization()
    {
	m_arguments = new java.util.Vector();
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    /**
     * If the <code>Initialization</code> expression constructs an
     * array, this method will return true.  if the
     * <code>Initialization</code> expression constructs an object,
     * this method will return false.  The type, must be set before
     * this method can return an accurate answer.  
     * 
     */

    public boolean isArrayConstruction()
    {
	if(m_type != null)
	{
	    return m_type.getDimension() > 0;
	}
	return false;
    }

    /**
     * Sets the class body.  If this is non-null, it defines an
     * anonymous class body that is defined and constructed in one
     * step.
     * @param b the block that makes up the anonymous class body.
     */

    public void setClassBody(Block b)
    {
	m_classBody = b;
	if(b != null)
	{
	    b.setParent(this, COL_CLASSBODY);
	}
    }

    /**
     * Returns the class body.  If this is non-null, it defines an
     * anonymous class body that isdefined and constructed in one
     * step.
     */
    
    public Block getClassBody()
    {
	return m_classBody;
    }

    /**
     * Returns the type that is being constructed.
     */

    public Type getType()
    {
	return m_type;
    }

    /**
     * Sets the type that is being constructed.
     */

    public void setType(Type t)
    {
	m_type = t;
	if(m_type != null)
	{
	    m_type.setParent(this, COL_TYPE);
	}
    }

    /**
     * If the expression creates a new array, this function adds an
     * expression to the list of dimension initializers.  If the
     * expression creates a new Object, this function adds an
     * expression to the list of constructor arguments.  
     * @see #isArrayConstruction
     * @param e the expression */

    public void addInit(Expression e)
    {
	m_arguments.addElement(e);
	e.setParent(this, COL_ARGUMENT);
    }

    /**
     * If the expression creates a new array, this function adds an
     * expression to the list of dimension initializers.  If the
     * expression creates a new Object, this function adds an
     * expression to the list of constructor arguments.  
     * @see #isArrayConstruction
     * @param e the expression
     * @param pos the position the expression should be placed in the
     * collection.
     */

    public void addInit(Expression e, int pos)
    {
	m_arguments.add(pos, e);
	e.setParent(this, COL_ARGUMENT);
    }
    
    /**
     * Returns either list of dimension initializers (expressions that
     * initialize each dimension of the array), if the construction is
     * an array construction, or the list of arguments (Expressions)
     * if the construction is a object construction.
     * @see #isArrayConstruction
     * @return an iterator that iterates over the list of initializers.  
     */

    public java.util.Iterator inits()
    {
	return m_arguments.iterator();
    }

    /**
     * Returns the number of expressions in the inits list.
     */

    public int getInitCount()
    {
	return m_arguments.size();
    }

    public Iterator children(int col)
    {
	if(col == COL_TYPE)
	{
	    return new SingleValueIterator("Type");
	}
	else if(col == COL_CLASSBODY)
	{
	    return new SingleValueIterator("ClassBody");
	}
	else if(col == COL_ARGUMENT)
	{
	    return inits();
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
	else if(col == COL_CLASSBODY)
	{
	    return (m_classBody == null) ? 0 : 1;
	}
	else if(col == COL_ARGUMENT)
	{
	    return m_arguments.size();
	}
	debug.Debug.assert(false);
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_TYPE)
	{
	    setType((Type)n);
	}
	else if(col == COL_CLASSBODY)
	{
	    setClassBody((Block)n);
	}
	else if(col == COL_ARGUMENT)
	{
	    addInit((Expression)n, pos);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }

    public boolean removeChild(TreeNode n)
    {
	switch(n.getCollection())
	{
	case COL_TYPE:
	    setType((Type)n);
	    return true;
	case COL_CLASSBODY:
	    setClassBody(null);
	    return true;
	case COL_ARGUMENT:
	    return m_arguments.remove(n);
	default:
	    debug.Debug.assert(false);
	}
	return false;
    }

    public TreeNode childAt(int col, int pos)
    {
	switch(col)
	{
	case COL_TYPE:
	    return m_type;
	case COL_CLASSBODY:
	    return m_classBody;
	case COL_ARGUMENT:
	    return (TreeNode)m_arguments.get(pos);
	}
	return null;
    }

}
