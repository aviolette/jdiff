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

import debug.Debug;
import java.util.*;
import java.lang.reflect.*;
import tree.*;

/**
 * The <code>Node</code> class represents a node in the abstract
 * syntax tree.  It provides basic functionality for tree traversal,
 * labelling and identification schemes, and hierarchical
 * relationships with other nodes in the AST.  
 * 
 * @author Andrew Violette
 */

public abstract class Node
   implements TreeNode, Cloneable
{
   private Node m_parent = null;
   private String m_label;
   private CompilationUnit m_root = null;
   private static HashMap s_allLabels;
   private int m_collection;
   private static Map s_attributes;
   private int m_maxBreadth;
   private int m_maxDepth;

   public final void setColor(int color)
   {
	this.color = color;
   }

   public final int getColor()
   {
	return color;
   }

   static 
   {
	s_allLabels = new HashMap();
   }

   /**
    * Binds a node's label with its its class.  Once a node is
    * registered, its associated class can retrieved with
    * <code>getClassFromLabel()</code>.  Also, its label will be
    * present in the set of iterated values when
    * <code>allLabels()</code> is called.  
    * @see #getClassFromLabel
    * @see #allLabels
    */

   protected static void registerNode(String label, Class c)
   {
	registry.register(label,c);
   }

   /**
    * Returns an iterator that iterates accross the labels (Strings)
    * that have been registered with <code>registerNode()</code>.
    * @see #registerNode
    * @see #getClassFromLabel
    */

   public static Iterator allLabels()
   {
	return registry.allLabels();
   }

   /**
    * Returns the node class associated with a label, or
    * <code>null</code> if the label is invalid.
    * @see #registerNode
    */

   public static Class getClassFromLabel(String label)
   {
	//	return (Class)s_allLabels.get(label);
	return registry.getClassFromLabel(label);
   }

   /**
    * Constructs a new node.
    */

   public Node()
   {
	m_id = -1;
   }
    

   /**
    * coloring scheme for breadth first traversal
    */
    
   private int color = WHITE;

   /**
    * Ordering variable used in diff.
    */

   private boolean inorder = true;

   public final boolean isInOrder()
   {
	return inorder;
   }

   public final void setInOrder(boolean b)
   {
	inorder = b;
   }

   /**
    * The node identifier.
    */

   private int m_id;

   /**
    * Sets the node ID.
    * @see #getID
    */

   protected final void setID(int id)
   {
	m_id = id;
   }

   /**
    * Returns the node ID.
    * @see #setID
    */

   public final int getID()
   {
	return m_id;
   }


   /**
    * Returns the collection ID of the parent's collection that this
    * node is contained in.  
    */

   public final int getCollection()
   {
	return m_collection;
   }

   public TreeNode.CollectionMetaData getCollectionMetaData(int id)
   {
	debug.Debug.assert(false);
	return null;
   }


   /**
    * Each subclass of <code>Node</code> must implement accept so
    * that <code>ASTTraverser</code> subclasses can traverse the
    * tree.  
    */

   public abstract void accept(ASTTraverser t);

   /**
    * Set the parent of the node.  This method also indirectly sets
    * the root node of the object.
    * If the parent is a <code>CompilationUnit</code> object, the
    * root node will also be the parent.  If the parent is not a
    * <code>CompilationUnit</code> object, the parent node will become
    * the root node.  If the parent's root node is
    * <code>null</code>, the last instantiated
    * <code>CompilationUnit</code> object will be the node's root.  
    *
    * <p> 
    * To untether a node from its parent, pass in <code>null</code>.
    * This will remove the relationship of the child to its parent
    * and root nodes.
    * 
    * <p>
    * If the object has an ID less than 1, a new object ID will be
    * allocated for the node from the root node. 
    * @see #getRoot
    * @see #getParent
    * @see #getID
    * @see #getCollection
    * @see CompilationUnit#allocateID 
    * @param parent the parent node of the object, or <code>null</code>
    * @param collection the collection of the parent that holds the child
    *
    */

   protected final void setParent(TreeNode parent, int collection)
   {
	m_parent = (Node)parent;
	m_collection = collection;
	debug.Debug.assert(parent != this);
	if(parent == null)
	{
	   m_parent = null;
	   m_root = null;
	}
	else if(m_parent != null)
	{
	   if(m_parent instanceof CompilationUnit)
	   {
		m_root = (CompilationUnit)m_parent;
	   }
	   else
	   {
		m_root = (CompilationUnit)parent.getRoot();
	   }

	   if(m_root == null)
	   {
		// we should only get here when the tree is loading,
		// and not when the tree is being manipulated after
		// loading.  The Node that the getCurrentRoot() method
		// returns changes everytime a new CompilationUnit is
		// created.

		m_root = CompilationUnit.getCurrentRoot();
	   }

	   Debug.assert(m_root != null);

	   if(m_id < 1)
	   {
		m_id = m_root.allocateID();
	   }
	   m_root.add(this);


	   m_parent.m_maxDepth = (m_maxDepth + 1 > m_parent.m_maxDepth) ? m_maxDepth + 1 : m_parent.m_maxDepth;
	   m_parent.m_maxBreadth = (m_maxBreadth + 1 > m_parent.m_maxBreadth) ? m_maxBreadth + 1 : m_parent.m_maxBreadth;

	}
	else if(!(this instanceof CompilationUnit))
	{
	   m_root = null;
	   m_id = -1;
	}
   }

   private void determineMaxBreadth()
   {
	final int count = getCollectionCount();

	for(int i = 0; i < count; i++)
	{
	   
	}
   }

   /**
    * Returns the root element of the AST that this node belongs to,
    * or <code>null</code> if this node has no root node.
    * 
    */

   public final RootNode getRoot()
   {
	if(this instanceof CompilationUnit)
	{
	   return (CompilationUnit)this;
	}
	return m_root;
   }

   /**
    * Returns the parent node of this node.
    */

   public final TreeNode getParent()
   {
	return m_parent;
   }

   /**
    * Returns <code>true</code> if the element has any direct
    * children.  
    */

   public boolean hasChildren()
   {
	return false;
   }

   /**
    * Returns the node's label.
    */

   protected abstract String getPrivateLabel();

   /**
    * Returns the node's label.
    */

   public final String getLabel()
   {
	return getPrivateLabel();
   }
    
   /**
    * Returns the parent's ID or -1 if there is no parent.
    */

   private final Integer getParentID()
   {
	TreeNode n = m_parent;
	if(n != null)
	{
	   return new Integer(n.getID());
	}
	return new Integer(-1);
   }
    
   /**
    * Returns a vector containing debug information pertaining to all nodes.
    */

   protected final Vector getStandardDescriptiveElements()
   {
	Vector v = new Vector();
	v.addElement(new Integer(getID()));
	v.addElement(getParentID());
	//	v.addElement(new Integer(getCollection()));
	//	v.addElement(new Integer(getDepth()));
	return v;
   }

   /**
    * Returns the node's depth in the abstract syntax tree.  The root
    * node has 0 depth.
    */

   public final int getDepth()
   {
	TreeNode n = this;
	int i = 0;
	while((n = n.getParent()) != null)
	{
	   i++;
	}
	return i;
   }

   protected final String createDescriptiveTuple(String label, Iterator i)
   {
	String s = label;
	s += "(";
	while(i.hasNext())
	{
	   Object o  = i.next();
	   if(o == null)
	   {
		s += "null";
	   }
	   else
	   {
		s += o.toString();
	   }
	   if(i.hasNext()) s += ", ";
	}
	s += ")";
	return s;
   }

   protected final String nullString(Object o)
   {
	if(o == null) return "";
	return o.toString();
   }


   /**
    * Returns a one-line string representation of the node for
    * debugging purposes.  This representation contains its label,
    * ID, parent ID, and any other information relevant to the
    * current node.  
    */

   public String toString()
   {
	return createDescriptiveTuple(getLabel(), getStandardDescriptiveElements().iterator());
   }

   public TreeNode copy()
   {
	try
	{
	   return (TreeNode)clone();
	}
	catch(CloneNotSupportedException e)
	{
	   e.printStackTrace();
	}
	return null;
   }

   /**
    * The <code>SingleValueIterator</code> class provides an
    * implementation of the <code>Iterator</code> iterface for single
    * value attributes of a node.  This is accomplished through some
    * reflection trickery and using common naming standards for all
    * attributes' accessor methods.  All attributes that use this
    * interface should have 'set' accessors that are in the form
    * <code>set &lt;name&gt;</code> and get accessors that are in the
    * form <code>is &lt;name&gt;</code> for attributes that are the
    * primitive type 'boolean', and <code>get &lt;name&gt;</code> for
    * all other types of attributes.
    * */

   protected class SingleValueIterator 
	implements Iterator
   {

	private Object m_obj = null;
	private String m_propName = null;

	public SingleValueIterator(String property)
	{
	   try
	   {
		Class c = Node.this.getClass();
		Method m = c.getMethod("get" + property, null);
		m_obj = m.invoke(Node.this, null);
		m_propName = property;
	   }
	   catch(Exception e)
	   {
		e.printStackTrace();
	   }
	}

	public boolean hasNext()
	{
	   return m_obj != null;
	}
	
	public Object next()
	{
	   Object o = m_obj;
	   m_obj = null;
	   return o;
	}

	public void remove()
	{
	   try
	   {
		Class c = Node.this.getClass();
		Class[] args = {Node.class};
		Method m = c.getMethod("set" + m_propName, args);
		m.invoke(Node.this, null);
	   }
	   catch(Exception e)
	   {
		e.printStackTrace();
	   }
	}
   }

   /**
    * Populates the attribute map with the attribute meta data for the object.  */

   protected void initializeAttributeMap(Map map)
   {
	
   }
    
   /**
    * Returns <code>true</code> if the object has the attribute specified.
    */

   public final boolean hasAttribute(String name)
   {
	return attributeMap().containsKey(name);
   }

   /**
    * Returns the attribute map.  The keys for this map represent
    * that attribute names supported by the node's class.  The values
    * represent the meta data (AttributeMetaData) for each attribute.  
    */

   protected Map attributeMap()
   {
	if(s_attributes == null)
	{
	   s_attributes = new HashMap();
	   //	    initializeAttributeMap(s_attributes);
	}
	return s_attributes;
   }

   /**
    * Returns an iterator that iterates over the attribute names
    * supported by the node's class.
    */

   public final Iterator attributeNames()
   {
	return attributeMap().keySet().iterator();
   }

   /**
    * Returns the attribute meta data for the specified attribute.
    * If the attribute is not supported by the node,
    * <code>null</code> is returned.  
    */

   public final TreeNode.AttributeMetaData attributeMetaData(String name)
   {
	return (TreeNode.AttributeMetaData)attributeMap().get(name);
   }


   private final Class convertToPrimitiveEquivalent(final Class c)
   {
	if(c == Boolean.class)
	{
	   return Boolean.TYPE;
	}
	else if(c == Integer.class)
	{
	   return Integer.TYPE;
	}
	debug.Debug.assert(false, c.toString());
	return null;
   }

   private Object castStringToPrimitiveType(Class c, String s)
   {
	if(c == Integer.TYPE)
	{
	   return new Integer(s);
	}
	else if(c == Boolean.TYPE)
	{
	   return new Boolean(s);
	}
	debug.Debug.assert(false);
	return null;

   }

   public final void setAttribute(String name, Object o)
	throws InvalidAttributeException
   {
	try
	{
	   TreeNode.AttributeMetaData md = attributeMetaData(name);
	   if(md == null)
	   {
		throw new InvalidAttributeException(name);
	   }
	   Class varClass = md.getType();
	   if(varClass.isPrimitive())
	   {

		if(o instanceof String)
		{
		   o = castStringToPrimitiveType(varClass, (String)o);
		}
		varClass = convertToPrimitiveEquivalent(o.getClass());
	   }
	   else if(Visibility.class.isAssignableFrom(varClass) && o.getClass() == String.class)
	   {
		o = Visibility.fromString((String)o);
	   }


	   Class thisClass = getClass();
	    

	   Class[] args = {varClass};
	   Method m = thisClass.getMethod("set" + name, args);
	   if(m == null)
	   {
		throw new InvalidAttributeException(name);
	   }
	   Object[] args2 = {o};
	   m.invoke(this, args2);
	}
	catch(InvalidAttributeException e1)
	{
	   throw e1;
	}
	catch(Exception e)
	{
	   e.printStackTrace();
	}
   }


   public final Object getAttribute(String name)
	throws InvalidAttributeException
   {
	try
	{
	   Class thisClass = getClass();
	   String prefix = "get";
	   TreeNode.AttributeMetaData md = attributeMetaData(name);
	   if(md == null)
	   {
		throw new InvalidAttributeException(name);
	   }
	   if(md.getType() == Boolean.TYPE)
	   {
		prefix = "is";
	   }
	   Method m = thisClass.getMethod(prefix + name, null);
	   if(m == null)
	   {
		throw new InvalidAttributeException(name);
	   }
	   return m.invoke(this, null);
	}
	catch(Exception e)
	{
	   e.printStackTrace();
	}
	return null;
	
   }

   public final int getMaxDepth()
   {
	return m_maxDepth;
   }

   public final int getMaxBreadth()
   {
	return m_maxBreadth;
   }

}
