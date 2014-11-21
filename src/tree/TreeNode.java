package tree;

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
 * Andrew Violette - aviolette@acm.org
 *
 */


/**
 * <p> A <code>TreeNode</code> represents a node in an abstract syntax
 * tree.  A node has an identifier, a label, a set of named
 * attributes, and a series of child collections.  
 *
 * </p>
 *
 * <p> 
 *
 * Each node supports a collection of attributes.  The set of
 * attributes names can be enumerated with <a
 * href="#attributeNames()">attributeNames()</a> and queried/set with
 * <a href="#getAttribute()">getAttributes</a> and <a
 * href="#setAttribute(java.lang.String,
 * java.lang.Object)">setAttribute</a> respectively.  Each attribute
 * that is supported by the node has associated metadata that allows
 * for a client class to query the type and nullability of the
 * attribute.  The meta data can be queried by calling <a
 * href="#attributeMetaData(java.lang.String)">attributeMetaData()</a>.
 *
 *  </p>
 *
 * <p>
 * 
 * Each node also supports multiple child node collections.  Each
 * collection is indexed by a zero based number, and can have a
 * cardinatilty of 0..1, 1..1, 0..n, or 1..n.  Each collection has a
 * numeric identifier that starts at index 0.  The number of
 * collections that a node supports can be queried by calling <a
 * href="#getCollectionCount()">getCollectionCount()</a>.  Given a
 * collection identifier, a call to <a
 * href="#children(int)">children()</a> will return an Iterator to the
 * nodes of the specified collection.  Child nodes can be
 * added/removed by calling <a href="#addChild(tree.TreeNode, int,
 * int)">addChild()</a> and <a
 * href="#removeChild(tree.TreeNode)">removeChild()</a> respectively.
 * In addition the number of nodes in a specific collection can be
 * queried by calling <a href="#childCount(int)">childCount()</a>.
 *
 * </p>
 *
 * <p>
 *
 * A node maintains a reference to the root object in the tree, its
 * parent object, and the collection identifier of the collection in
 * the parent node that the node is contained in.  The root node can
 * be retrieved with <a href="#getRoot()">getRoot()</a>, the parent
 * node can be retrieved with <a href="#getParent()">getParent()</a>,
 * and the parent collection can be retrieved by calling <a
 * href="#getCollection()">getCollection()</a>.
 *
 * </p>
 * 
 * @author Andrew Violette
 *   
 */



public interface TreeNode
{
   /**
    * In breadth first traversal of the tree, a node with color WHITE
    * has not yet been discovered.  */

   public static final int WHITE = 0;

   /**
     * In breadth first traversal of the tree, a node with color BLACK
     * has been discovered along with all its child nodes.  
     */

   public static final int BLACK = 1;

   /**
     * In breadth first traversal of the tree, a node with color GRAY
     * has been discovered, but its remaining child nodes have not yet
     * been discovered.  
     */

   public static final int GRAY = 2;

   /**
     * Contains the association between the nodes' labels and their classes.
     *
     */
    
   public static LabelRegistry registry = new LabelRegistry();

   /**
     * Returns the depth of the node within the tree.
     */

   public int getDepth();

   /**
     * Returns the "color" of the node.  The color can be used to
     * describe whether all the node's children have been discovered
     * during a traversal. 
     * @see #setColor
     */

   public int getColor();

   /**
     * Sets the "color" of the node.
     * @see #getColor
     */

   public void setColor(int color);

   /**
     * Returns true if the node is in order relative to its peers.  Used by JDiff.
     */

   public boolean isInOrder();

   public TreeNode copy();

    
    /**
     * Sets whether the node is in order relative to its peers.
     */

   public void setInOrder(boolean inOrder);

   /**
     * Returns the node's ID.
     */

   public int getID();

   /**
     * Returns the node's class's label.
     */

   public String getLabel();

   /**
     * Returns the id of the collection, owned by this node's parent,
     * that contains the node.  
     */

   public int getCollection();

   /**
     * Returns the node's root node.  If the node has not been
     * inserted into a tree, it returns <code>null</code>.
     */

   public RootNode getRoot();

   public TreeNode childAt(int col, int pos);


    /**
     * returns <code>true</code> if the node has any children.
     */

   public boolean hasChildren();

   /**
     * Returns the number of collections that this node has.
     */

   public int getCollectionCount();

   /**
     * Returns the collection meta data associated with the collection ID.
     */

   public TreeNode.CollectionMetaData getCollectionMetaData(int id);


   /**
     * Returns the number of elements in the collection specified.  If
     * the ID is invalid, 0 is returned.  */
   public int childCount(int col);

   /**
     * Returns an iterator to the collection specified.  If the
     * collection id is invalid, or the collection specifies a single
     * value collection, the Iterator will be null.  
     */

   public java.util.Iterator children(int col);

   /**
     * Returns the <code>Node</code>'s parent node, or
     * <code>null</code> if it has not been inserted into a tree.  
     */

   public TreeNode getParent();

   /**
     * Adds a node to the specified collection at the specified position.
     * @param node the node to add
     * @param col the collection identifier
     * @param pos the position to add the node in the collection
     */

   public void addChild(TreeNode node, int col, int pos);

   /**
     * Removes the node from the parent collection.
     * @param node the node to remove
     * @return <code>true</code> if the object was removed,
     * <code>false</code> if it wasn't in the collection.  
     */

   public boolean removeChild(TreeNode node);

   /**
     * Returns <code>true</code> if the node has the specified
     * attribute.  
     * @see #attributeNames
     * @see #setAttribute
     * @see #getAttribute
     */

   public boolean hasAttribute(String name);

   /**
     * Returns an iterator that iterates over the node's attribute names.
     * @see #hasAttribute
     * @see #setAttribute
     * @see #getAttribute
     */

   public java.util.Iterator attributeNames();

   /**
     * Returns the attribute meta data for the specified attribute.
     */

   public TreeNode.AttributeMetaData attributeMetaData(String name);

   /**
     * Sets the attribute's value.  
     * 
     * @exception InvalidAttributeException
     * if the attribute does not exist, or if the value is of an
     * incompatible type.
     * @see #getAttribute
     * @see #attributeNames
     * @see #hasAttribute
     */

   public void setAttribute(String name, Object o)
	throws InvalidAttributeException;

    /**
     * Returns the attribute's value
     *
     * @exception InvalidAttributeException if the attribute does not exist
     * @see #setAttribute
     * @see #attributeNames
     * @see #hasAttribute
     */

   public Object getAttribute(String name)
	throws InvalidAttributeException;


    /**
     * The <code>CollectionMetaData</code> class contains information
     * about a collections properties such as its cardinality,
     * containing class, and description.
     */

   public final class CollectionMetaData
   {

	/**
	 * A cardinality of 1..1 
	 */

	public static final int CARD_1_1 = 0;
	
	/**
	 * A cardinality of 0..1
	 */
	
	public static final int CARD_0_1 = 1;

	/**
	 * A cardinality of 0..n
	 */

	public static final int CARD_0_n = 2;

	/**
	 * A cardinality of 1..n
	 */

	public static final int CARD_1_n = 3;

	private int m_cardinality;
	private Class m_childType;
	private String m_description;

	/**
	 * Constructs a <code>CollectionMetaData</code> object.
	 * @param cardinality the cardinality of the collection
	 * @param childType the class of objects that is supported by a collection
	 * @param desc the description of the collection.
	 */

	public CollectionMetaData(int cardinality, Class childType, String desc)
	{
	   m_cardinality = cardinality;
	   m_childType = childType;
	   m_description = desc;
	}

	/**
	 * Returns the cardinality.
	 */

	public int getCardinality()
	{
	   return m_cardinality;
	}

	/**
	 * Returns the class of the collection's members.
	 */

	public Class getChildType()
	{
	   return m_childType;
	}

	/**
	 * Returns the description of the collection
	 */

	public String getDescription()
	{
	   return m_description;
	}

   }

   /**
     * The <code>AttributeMetaData</code> class describes properties
     * of an node's attributes.  These properties include an
     * attribute's data type, and whether <code>null</code> is an
     * acceptable value for the attribute.  */

   public final class AttributeMetaData
   {
	private Class m_type;
	private boolean m_isNullable;
	private Object m_default;
	private String m_description;
	/**
	 * Constructs an <code>AttributeMetaData</code> object.
	 * @param type the data type of the attribute.
	 * @param isNullable if the type is non-primitive, and this is
	 * <code>true</code>, then null is an acceptable value for the
	 * attribute.  If the type is non-primitive and this value is
	 * <code>false</code> then <code> null is unacceptable.  If
	 * the type is primitive, this value is ignored.
	 * @param def the default value of the object
	 * @param description the description of the attribute
	 */


	public AttributeMetaData(Class type, boolean isNullable, Object def, String description)
	{
	   m_type = type;
	   m_isNullable = isNullable;
	   m_default = def;
	   m_description = description;
	}

	/**
	 * Returns the default value for this attribute.
	 */

	public Object getDefault()
	{
	   return m_default;
	}

	/**
	 * Returns the description of the attribute.
	 */

	public String getDescription()
	{
	   return m_description;
	}


	/**
	 * Returns the data type of the attribute.
	 */

	public Class getType()
	{
	   return m_type;
	}

	/**
	 * Returns <code>true</code> if the attribute is nullable.
	 */

	public boolean isNullable()
	{
	   return m_isNullable;
	}
   }
   
   /**
    * Returns the number of paths to this node's deepest child.  If
    * this node has no children, it will be 0.
    */

   public int getMaxDepth();
   
   /**
    * Returns the largest number of children that this node or any of
    * its child node's contains.
    */
   
   public int getMaxBreadth(); 

}
