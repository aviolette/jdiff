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

import java.io.*;
import java.util.*;
import debug.Debug;
import tree.*;

/**
 * The <code>CompilationUnit</code> is the root level node
 * for the Java abstract syntax tree.  The children of the compilation
 * unit are the import declarations and type declarations.
 */

public final class CompilationUnit
extends Node
implements RootNode
{
    private static Map s_attributes; 
    private String m_package;
    private Vector m_imports;
    private TypeDeclaration m_typedef;
    
    private HashMap m_allNodes;
    private boolean m_isIndexed;
    private Node m_children[];
    private long m_childCount;

    /**
     * root - the node's label.
     */

    public static final String LABEL = "root";

    /**
     * 0 - the collection identifier for the collection of import declarations.
     */
    public static final int COL_IMPORT = 0;

    /**
     * 1 - the collection identifier for the collection of type declarations.
     */
    public static final int COL_CLASS = 1;

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					ImportDeclaration.class, "The import declarations."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					TypeDeclaration.class, "The type declaration.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }


    public int getCollectionCount()
    {
	return 2;
    }

    protected Map attributeMap()
    {
	if(s_attributes == null)
	{
	    s_attributes = new HashMap();
	    initializeAttributeMap(s_attributes);
	}
	return s_attributes;
    }

    protected void initializeAttributeMap(final Map map)
    {
	map.put("Package", new TreeNode.AttributeMetaData(String.class, true, null, "The name of the package that the type is defined in."));
    }

    private int m_currentID;
    private static CompilationUnit s_current;
    private Set m_labels;

    public static void registerAllNodes()
    {
	registerNode(ArrayAccess.LABEL, ArrayAccess.class);
	registerNode(ArrayInitializer.LABEL, ArrayInitializer.class);
	registerNode(Block.LABEL, Block.class);
	registerNode(BreakStatement.LABEL, BreakStatement.class);
	registerNode(Cast.LABEL, Cast.class);
	registerNode(ClassAccess.LABEL, ClassAccess.class);
	registerNode(ClassDeclaration.LABEL, ClassDeclaration.class);
	registerNode(LABEL, CompilationUnit.class);
	registerNode(ConditionalExpression.LABEL, ConditionalExpression.class);
	registerNode(ConstructorDeclaration.LABEL, ConstructorDeclaration.class);
	registerNode(ConstructorInvocation.LABEL, ConstructorInvocation.class);
	registerNode(ContinueStatement.LABEL, ContinueStatement.class);
	registerNode(EmptyStatement.LABEL, EmptyStatement.class);
	registerNode(FieldAccess.LABEL, FieldAccess.class);
	registerNode(FieldDeclaration.LABEL, FieldDeclaration.class);
	registerNode(ForStatement.LABEL, ForStatement.class);
	registerNode(Grouping.LABEL, Grouping.class);
	registerNode(Identifier.LABEL, Identifier.class);
	registerNode(IfStatement.LABEL, IfStatement.class);
	registerNode(ImportDeclaration.LABEL, ImportDeclaration.class);
	registerNode(InfixExpression.LABEL, InfixExpression.class);
	registerNode(Initialization.LABEL, Initialization.class);
	registerNode(Initializer.LABEL, Initializer.class);
	registerNode(InstanceOf.LABEL, InstanceOf.class);
	registerNode(InterfaceDeclaration.LABEL, InterfaceDeclaration.class);
	registerNode(LabeledStatement.LABEL, LabeledStatement.class);
	registerNode(Literal.LABEL, Literal.class);
	registerNode(LocalVariableDeclaration.LABEL, LocalVariableDeclaration.class);
	registerNode(MethodDeclaration.LABEL, MethodDeclaration.class);
	registerNode(MethodInvocation.LABEL, MethodInvocation.class);
	registerNode(Name.LABEL, Name.class);
	registerNode(Parameter.LABEL, Parameter.class);
	registerNode(PostfixExpression.LABEL, PostfixExpression.class);
	registerNode(ReturnStatement.LABEL, ReturnStatement.class);
	registerNode(StatementExpression.LABEL, StatementExpression.class);
	registerNode(Switch.LABEL, Switch.class);
	registerNode(Switch.Case.LABEL, Switch.Case.class);
	registerNode(SynchronizedBlock.LABEL, SynchronizedBlock.class);
	registerNode(TryBlock.LABEL, TryBlock.class);
	registerNode(TryBlock.CatchBlock.LABEL, TryBlock.CatchBlock.class);
	registerNode(ThrowStatement.LABEL, ThrowStatement.class);
	registerNode(Type.LABEL, Type.class);
	registerNode(UnaryExpression.LABEL, UnaryExpression.class);
	registerNode(VariableDeclarator.LABEL, VariableDeclarator.class);
	registerNode(WhileStatement.LABEL, WhileStatement.class);
    }
    static
    {
	// register all nodes here, because the CompilationUnit will always be the first
	// item created in the abstract syntax tree.

	registerAllNodes();
    }

    /**
     * Allocates a new node ID that is unique to the tree.
     */

    protected final int allocateID()
    {
	return m_currentID++;
    }

    /**
     * Constructs a compilation unit.
     */

    public CompilationUnit(final boolean indexNodes)
    {
	m_childCount = 0;
	m_labels = new HashSet();
	m_package = null;
	m_allNodes = new HashMap();
	m_isIndexed = indexNodes;
	m_imports = new Vector();
	s_current = this;

	setID(allocateID());
	Debug.assert(getID() == 0);
	if(m_isIndexed)
	
	{
	    m_children = new Node[10000];
	    m_children[0] = this;
	}
	add(this);
    }

    /**
     * Accepts an <code>ASTTraverser</code> visitor.
     */

    public void accept(final ASTTraverser traverser)
    {
	traverser.visit(this);
    }

    private final Vector retrieveNodesOfType(final String label)
    {
	Vector v = (Vector)m_allNodes.get(label);
	if(v == null)
	{
	    v = new Vector();
	    m_allNodes.put(label, v);
	}
	return v;
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    /**
     * Adds a new node to the tree
     */

    protected void add(final Node n)
    {
	Debug.assert(n.getID() >= 0);
	Debug.assert(m_isIndexed == false || n.getID() < m_children.length);

	if(m_isIndexed)
	{
	    m_children[n.getID()] = n;
	}
	m_childCount++;
	retrieveNodesOfType(n.getLabel()).addElement(n);
	m_labels.add(n.getLabel());
    }

    /**
     * Produces an iterator that iterates over all the nodes of a
     * specified type.  The label used should be one of the LABEL
     * constants defined for concrete nodes of the abstract syntax
     * tree.  
     */

    public Vector chain(final String label)
    {

	return retrieveNodesOfType(label);
    }

    public CompilationUnit()
    {
	this(false);
    }

    /**
     * Returns the last CompilationUnit created.
     */

    public static CompilationUnit getCurrentRoot()
    {
	return s_current;
    }


    public boolean isIndexed()
    {
	return m_isIndexed;
    }

    /**
     * Finds a node by its ID.  Currently this only works when the
     * tree is indexed.  
     */

    public final TreeNode find(final int id)
    {
	Debug.assert(isIndexed());

	if(id < m_childCount)
	{
	    return m_children[(int)id];
	}
	return null;
    }

    /**
     * Returns an iterator that iterates over the labels of the nodes
     * that are descendents of this element.  
     * 
     * @see #chain
     */

    public final Iterator labels()
    {
	return m_labels.iterator();
    }
    
    public final long getDescendentCount()
    {
	return m_childCount;
    }

    /**
     * Sets the name of the package that contains the type definition.
     * <code>null</code> is a valid value.  
     */

    public void setPackage(String name)
    {
	m_package = name;
    }

    /**
     * Returns the name of the package that contains the type
     * definition.
     *
     */

    public String getPackage()
    {
	return m_package;
    }

    /**
     * Adds an <code>ImportDeclaration</code> to the end of the list
     * of import declarations.  
     */

    public void addImportDeclaration(final ImportDeclaration dec)
    {
	m_imports.addElement(dec);
	dec.setParent(this, COL_IMPORT);
    }

    /**
     * Adds an <code>ImportDeclaration</code> to the list of import
     * declarations at the position specified.  
     */

    public void addImportDeclaration(ImportDeclaration dec, int pos)
    {
	m_imports.add(pos, dec);
	dec.setParent(this, COL_IMPORT);
    }

    /**
     * Removes an <code>ImportDeclaration</code> from the list of
     * import declarations.  
     */

    public void removeImportDeclaration(ImportDeclaration dec)
    {
	m_imports.remove(dec);
    }

    public Iterator children(int id)
    {
	if(id == COL_IMPORT)
	{
	    return imports();
	}
	else if(id == COL_CLASS)
	{
	    return new SingleValueIterator("TypeDeclaration");
	}
	debug.Debug.assert(false);
	return null;
    }

    public int childCount(int id)
    {
	if(id == COL_IMPORT)
	{
	    return m_imports.size();
	}
	else if(id == COL_CLASS)
	{
	    return m_typedef == null ?  0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }

    /**
     * Returns an <code>Iterator</code> that iterates over the list of
     * import declarations.  
     */

    public Iterator imports()
    {
	return m_imports.iterator();
    }

    /**
     * Returns the type declaration defined in the
     * <code>CompilationUnit</code>.
     * 
     */

    public TypeDeclaration getTypeDeclaration()
    {
	return m_typedef;
    }

    /**
     * Sets the type declaration defined in the
     * <code>CompilationUnit</code>.
     *  
     */

    public void setTypeDeclaration(TypeDeclaration typeDef)
    {
	m_typedef = typeDef;
	if(typeDef != null)
	{
	    m_typedef.setParent(this, COL_CLASS);
	}
    }

    public void addChild(TreeNode node, int col, int pos)
    {
	switch(col)
	{
	case COL_CLASS:
	    setTypeDeclaration((TypeDeclaration)node);
	    break;
	case COL_IMPORT:
	    addImportDeclaration((ImportDeclaration)node, pos);
	    break;
	default:
	    debug.Debug.assert(false);
	    break;
	}

    }

    public boolean removeChild(TreeNode node)
    {
	debug.Debug.assert(node.getParent() == this);
	switch(node.getCollection())
	{
	case COL_CLASS:
	    if(node == getTypeDeclaration())
	    {
		setTypeDeclaration((TypeDeclaration)null);
		return true;
	    }
	    return false;
	case COL_IMPORT:
	    return m_imports.remove(node);
	default:
	    debug.Debug.assert(false);
	    break;
	}
	return false;
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_CLASS)
	{
	    return m_typedef;
	}
	else if(col == COL_IMPORT)
	{
	    return (TreeNode)m_imports.get(pos);
	}
	return null;
    }

}
