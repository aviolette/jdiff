package treediff;

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


import debug.*;
import java.io.*;
import java.util.*;
import treediff.edit.*;
import tree.*;

/**
 * <p>
 * The <code>Diff</code> class is used to compare any two trees that
 * implement the interfaces defined in the <a
 * href="..\tree\package-summary.html">tree</a> package.  A Diff
 * object takes two files, which are instances of a specified grammar,
 * and produces an output file that is an edit script that
 * reconstructs the second file from the first.  The edit script can
 * be parsed using the <a
 * href="edit\EditScript.html">EditScript</a> class in the <a
 * href="edit\package-summary.html">edit</a> package.
 * </p>
 *
 * <p>
 *
 * To support generic comparison of tree structures, two objects must
 * be supplied as input: the parser object and the comparator object.
 * The parser object supports the <a
 * href="..\tree\Parser.html">Parser</a> interface.  The parser will
 * generate the abstract syntax tree for any supplied grammar
 * instance.  The comparator object is an object that supports the
 * <code>Comparator</code> interface.  This class will be used to
 * determine equivalence of nodes when the initial matching set is
 * generated and when the nodes' children are aligned.  </p>
 * 
 * <p> 
 *  
 * The algorithm used by this class is based on the algorithm
 * described in <i>Change Detection in Hierarchical Structured
 * Information</i> by Chawathe, Rajarman, Garcia-Molina, and Widom in
 * the Proceedings of the 1996 SIGMOD International Conference on
 * Management of Data.  However, some slight modifications were made
 * to support multiple valued attributes and non-homogenous child
 * collections.  For nodes with multiple attributes, the Comparator
 * class is used to determine if the node is equivalent enough to
 * another node.  For nodes with more than one child collection, extra
 * logic was added to aid in the breadth first traversal of the tree.
 *
 * </p>
 * <p>
 * 
 * 
 * @author Andrew Violette
 * 
 * */

public class Diff
implements Runnable
{
   private LinkedList m_queue;
   private EditScript m_editScript;
   private MatchingSet m_matched;
   private RootNode m_tree1;
   private RootNode m_tree2;
   private Comparator m_comparator;
   private String m_in1;
   private String m_in2;
   private boolean m_verbose = true;
   private LCSSolver m_lcs;
   private Parser m_parser;
   private boolean m_generateDebugInfo;
   private Vector m_listeners;

   private void out(String s)
   {
	if(m_verbose)
	{
	   System.out.println(s);
	}
   }

    /**
     * Constructs a Diff object.
     * @param in1 the file name of the first Java program
     * @param in2 the file name of the second Java program
     */

    public Diff(String in1, String in2, Comparator c, MatchingSet set, Parser parser, boolean debug, boolean verbose)
    {
	m_comparator = c;
	m_matched = set;
	m_lcs = new LCSSolver(m_comparator);
	m_in1 = in1;
	m_in2 = in2;
	m_parser = parser;
	m_generateDebugInfo = debug;
	m_verbose = verbose;
    }

    private void colorGray(final TreeNode n)
    {
	if(n.getColor() == TreeNode.WHITE)
	{
	    n.setColor(TreeNode.GRAY);
	    //	    m_queue.addElement(n);
	    m_queue.add(n);
	}
    }

    private void alignChildren(final TreeNode node)
    {
	final TreeNode sibling = m_matched.matchT2toT1(node);
	final int count = node.getCollectionCount();
	for(int i=0; i < count; i++)
	{
	    align(sibling, node, i);
	}
    }

    private void colorChildrenGray(final TreeNode n)
    {
	final int count = n.getCollectionCount();
	for(int i=0; i < count; i++)
	{
	    Iterator j = n.children(i);
	    while(j.hasNext())
	    {
	      TreeNode node = (TreeNode)j.next();
		colorGray(node);
	    }
	}
    }

    /** 
     * Generates the edit script which makes T<sub>1</sub> isomorphic
     * to T<sub>2</sub>.  The tree T<sub>2</sub> is descended
     * breadth-first.  If <code>n</code> is the current node in the
     * descent, the following will be true after it is processed&#58;
     *
     * <br> 
     * <ol> 

     * <li> If <code>n</code> was not matched in T<sub>1</sub>, an
     * Insert operation was added to the edit
     * script, and applied to T<sub>1</sub>.  The new node in
     * T<sub>1</sub> has all the matching attributes of
     * <code>n</code>.
     *
     * <li> Otherwise if <code>n</code> was matched in T<sub>1</sub>
     * the following will be true&#58;
     *
     * <ol> 
     * <li>If <code>n</code>'s attributes differed from the
     * matched node, <code>n1</code> in T<sub>1</sub>, an Update
     * operation was added to the edit script that transformed all of
     * <code>n1</code>'s properties to that of <code>n</code>'s.
     *
     * <li>If <code>n</code>'s position in T<sub>2</sub> differed from
     * that of its matched node, <code>n1</code> in T<sub>1</sub>, a
     * Move operation was generated that moves <code>n1</code> to
     * <code>n</code>'s parent's collection.  
     *
     * </ol> 
     * 
     *<li>All the children of <code>n</code> are aligned with its
     *matched node in T<sub>1</sub>.

     * <li>A mapping will exist that maps <code>n</code> to a node in
     * T<sub>1</sub>.  
     * 
     * </ol> */

    protected final void buildEditScript()
    {
	 m_queue = new LinkedList();
	 colorGray(m_tree2);
	 TreeNode node;
	 
	 while(!m_queue.isEmpty())
	 {
	    node = (TreeNode)m_queue.getFirst();
	    doEdits(node);
	    alignChildren(node);
	    colorChildrenGray(node);
	    m_queue.remove(0);
	    node.setColor(TreeNode.BLACK);
	 }
    }

    /**
     * Removes nodes in T<sub>1</sub>, but not in T<sub>2</sub>.  For
     * each node that is in T<sub>1</sub> that does not match a node
     * in T<sub>2</sub>, a <code>Delete</code> object is appended to
     * the edit script that removes the node from T<sub>1</sub>.
     * Whenever an unmatched node is encountered, the entire sub-tree
     * is deleted in T<sub>1</sub> with one Delete operation.
     *
     * <br>
     * <table>
     * <tr> 
     * <td style="vertical-align: top">&#171;precondition&#187;&#58;
     * </td> 
     * <td><code>true</code>
     * </td> 
     * </tr>
     * <tr>
     * <td style="vertical-align: top">&#171;postcondition&#187;&#58;</td> 
     * <td> <code>T<sub>1</sub>.nodes->forAll(node |
     * matched.T1toT2(node) &lt;&gt; null) and <br>
     * T<sub>2</sub>.nodes->forAll(node | matched.T2toT1(node)
     * &lt;&gt; null)</code> </td>
     * </tr>
     * </table> */

   protected final void pruneUnmatchedNodes()
   {
	TreeNode node;

	// assert(m_queue.isEmpty())

	colorGray(m_tree1);

	while(!m_queue.isEmpty())
	{
	   node = (TreeNode)m_queue.getFirst();
	   if(m_matched.matchT1toT2(node) == null)
	   {
		Delete d = new Delete(node);
		m_editScript.append(d);
	   }
	   else
	   {
		colorChildrenGray(node);
	   }
	   m_queue.remove(0);
	   node.setColor(TreeNode.BLACK);
	}
   }

    /**
     *
     * Finds the n2's partner's appropriate position in T<sub>1</sub>.
     * This algorithm finds the position of the closest sibling of
     * <code>n2</code> to its left that is marked 'inorder'.  If
     * <code>n2</code> is the leftmost sibling in the collection, 0 is
     * returned.  Once the sibling is found, it is matched in
     * T<sub>1</sub>.  The algorithm returns the position of the
     * sibling's partner in T<sub>1</sub>, plus one.
     *
     * 
     * <br>
     *
     * <table>
     * <tr> 
     * <td style="vertical-align: top">&#171;precondition&#187;&#58;
     * </td> 
     * <td>
     * </td> 
     * </tr>
     * <tr>
     * <td style="vertical-align: top">&#171;postcondition&#187;&#58;</td> 
     * <td>
     * </td>
     * </tr>
     * </table>
     *  */

    protected final int findPosition(final TreeNode n2)
    {
	final int col = n2.getCollection();
	final TreeNode y = n2.getParent();
	
	// n2 is the root node
	if(y == null)
	{
	    return 0;
	}

	Iterator i = y.children(col);
	int pos = 0;
	boolean firstInOrder = false;
	boolean foundFirst = false;
	boolean found = false;
	TreeNode u = null;

	// The objective of this loop is to find the rightmost node to
	// the left of n2 that is marked "inorder".  If the inorder
	// item happens to be first, 0 is returned.

	while(i.hasNext())
	{
	    TreeNode v = (TreeNode)i.next();

	    firstInOrder = (v.isInOrder() && !foundFirst);
	    if(firstInOrder)
	    {
		foundFirst = true;
	    }
	    if(v == n2 && firstInOrder)
	    {
		return 0;
	    }
	    else if(v == n2)
	    {
		found = true;
	    }
	    else if(found && v.isInOrder())
	    {
		u = m_matched.matchT2toT1(v);
		break;
	    }
	    else if(found)
	    {
	    }
	}
	TreeNode pu;
	if(u == null)
	{
	    pos =  (m_matched.matchT2toT1(y)).childCount(col);
	    return pos;
	}
	pu = (TreeNode)u.getParent();
	i = pu.children(col);
	pos = 0;
	while(i.hasNext())
	{
	    if(u == (i.next()))
	    {
		return pos;
	    }
	    pos++;
	       
	}
	return pos-1;
	
    }

    /**
     * Aligns the matching children of n1 and n2 so that they occupy
     * the same positions in T<sub>1</sub> and T<sub>2</sub>.  Move
     * operations are appended to the edit script so that the
     * appropriate operations can then be played back.
     *
     * @param n1 the node from T<sub>1</sub>
     * @param n2 the node from T<sub>2</sub>
     * @param col the collection to align
     */

    protected final void align(final TreeNode n1, final TreeNode n2, final int col)
    {
	markAllOutOfOrder(n2.children(col));
	markAllOutOfOrder(n1.children(col));

	// 
	final Vector s1 = constructMatchingSequence(n1.children(col), true);
	final Vector s2 = constructMatchingSequence(n2.children(col), false);

	final Vector unsolvedS1 = new Vector();
	final Vector unsolvedS2 = new Vector();

	// Hashtable is used instead of HasMap because there should be no null values
	final Map t2Tot1 = new Hashtable();
	final Map t1Tot2 = new Hashtable();

	m_lcs.lcs(s1, s2, unsolvedS1, unsolvedS2, t2Tot1, t1Tot2);

	Iterator i = t2Tot1.keySet().iterator();

	// Mark all nodes that participate in the LCS as in-order

	while(i.hasNext())
	{
	    final TreeNode c = (TreeNode)i.next();
	    final TreeNode matched = (TreeNode)t2Tot1.get(c);
	    c.setInOrder(true);
	    matched.setInOrder(true);
	}
	
	i = m_matched.getSet().keySet().iterator();

	// Iterate across all matched nodes in T2

	while(i.hasNext())
	{
	    TreeNode c = (TreeNode)i.next();
	    
	    // If the current node from T2 is not matched in the LCS
	    // and the parent of the current node is the child of the
	    // node whose children are being aligned, find the matched
	    // node in T1, find the matching node's position and move
	    // it to that new position.

	    if(!t2Tot1.containsKey(c) && c.getParent() == n2 && c.getCollection() == col)
	    {
		final TreeNode matched = m_matched.matchT2toT1(c);
		if(matched.getParent() != n1) continue;
		int k = findPosition(c);

		// Here I am asserting that during an alignment the matched node
		// in T1 and the node in T2 have matching parents.

		debug.Debug.assert(debug.Debug.debugMode &&
				   (m_matched.matchT1toT2(matched.getParent()) == c.getParent()));

		// A move consists of a delete and a insert.  Thus if
		// the index of the move is to the last item in the
		// collection, it will fail because the delete
		// operation removes an item from the collection,
		// thereby decrementing the collection count.  When
		// the insert operation is applied, an out of bounds
		// exception will be thrown because the index is too
		// high.  To get around this problem, if the index is
		// to the last item in the collection, the index is
		// just decremented by one.  REVISIT THIS LATER
		// BECAUSE I AM NOT SURE IF IT IS CORRECT.  Maybe one
		// solution is to do the insert before doing the
		// delete.

		if(k == matched.getParent().childCount(col))
		{
		    k=k-1;
		}

		final Move move = new Move(matched, n1, col, k);
		m_editScript.append(move);
		try
		{
		    move.apply();
		}
		catch(EditApplicationException e)
		{
		    debug.Debug.assert(false);
		}
	    }
	}
	
    }


    private Vector constructMatchingSequence(Iterator i,
					     boolean useT1toT2Match)
    {
	final Vector v = new Vector();
	while(i.hasNext())
	{
	    final TreeNode c1 = (TreeNode)i.next();
	    TreeNode matched = null;
	    if(useT1toT2Match)
	    {
		matched = m_matched.matchT1toT2(c1);
	    }
	    else
	    {
		debug.Debug.assert(c1 != null);
		matched = m_matched.matchT2toT1(c1);
	    }
	    if(matched != null)
	    {
		v.addElement(c1);
	    }
	}
	return v;
    }

    private void markAllOutOfOrder(Iterator i)
    {
	while(i.hasNext()) 
	{ 
	    final TreeNode c = (TreeNode)i.next(); 
	    c.setInOrder(false);
	}
    }

    private RootNode produceTree(final String fileName)
	throws IOException, tree.ParseException
    {
	InputStreamReader reader;
	out("Parsing " + fileName + "...");
	reader = new InputStreamReader(new FileInputStream(fileName));
	return m_parser.parse(reader, true);
    }

    private void dumpNodes(RootNode tree)
    {
	final Iterator i = tree.labels();
	while(i.hasNext())
	{
	    String label = i.next().toString();
	    Vector nodesOfType = tree.chain(label);
	    debug.Debug.collectionPrint(nodesOfType, true);
	    
	}
    }
    private void dumpTree(RootNode tree, String file)
    {
	try
	{
	    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    TreePrinter tw = new TreePrinter();
	    tw.printTree(writer, tree);

	    writer.close();
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }

    /**
     * Produces the edit script for two Java files.  This involves
     * parsing the two Java files, producing the initial matching set
     * of nodes, and building the edit script that transforms
     * T<sub>1</sub> to T<sub>2</sub>.
     * 
     * @see getScript() 
     */


    public void run()
    {

	try
	{

	    // Generate the AST's
	    m_tree1 = produceTree(m_in1);
	    m_tree2 = produceTree(m_in2);
	    
	    m_editScript = new EditScript();
	    m_matched = new FastMatch(m_comparator);
	    
	    // Generate the initial set of matched nodes
	    m_matched.buildMatchSet(m_tree1, m_tree2);

	    // ensure that the root is matched
	    
	    if(m_matched.matchT1toT2(m_tree1) == null)
	    {
		 m_matched.add(m_tree1, m_tree2);
	    }

	    // dump all the nodes for debugging purposes, before the
	    // nodes from T1 are deleted

	    if(m_generateDebugInfo)
	    {

		dumpTree(m_tree1, "tree1.xml");
	    }
	    
	    // Descend the tree in breadth-first order and build the
	    // complete set of mappings.  All the move, update, and insert
	    // operations are constructed during this descent.
	    
	    buildEditScript();
	    
	    if(m_generateDebugInfo)
	    {
		dumpTree((RootNode)m_tree2, "tree2.xml");
	    }

	    // The second descent removes any un-matched nodes from T1,
	    // and appends the Delete operations to the edit script
	    
	    pruneUnmatchedNodes();

	    
	    
	    // At this point T1 will be isomorphic to T2, and the matcher
	    // will contain a complete set of mappings between T1 and T2.
	    
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	    if(e.getMessage() != null) System.err.println(e.getMessage());
	}
    }

    private void resetColors(RootNode node)
    {
	long count = node.getDescendentCount();
	for(int i=0; i <= count; i++)
	{
	    TreeNode child = (TreeNode)node.find(i);
	    child.setColor(TreeNode.WHITE);
	}
    }

    /**
     * Determines if the two nodes differ in values, and if their values
     * do differ, it constructs an Update operation that updates the
     * node <code>n1</code> with the differing values from node
     * <code>n2</code>.  The Update operation is applied to tree
     * T<sub>1</sub>, and is appended to the edit script. 
     * 
     * <table>
     * <tr> 
     * <td style="vertical-align: top">&#171;precondition&#187;&#58;
     * </td> 
     * <td><code>(n1 &lt;&gt; null) and (n2 &lt;&gt; null) and (n1.Label = n2.Label)</code>
     * </td> 
     * </tr>
     * <tr>
     * <td style="vertical-align: top">&#171;postcondition&#187;&#58;</td> 
     * <td>
     *
     * <code>diffAttributes(n1, n2).size &gt; 0 implies
     * (n2.attributeNames->forAll(name | n1.getAttribute(name) =
     * n2.getAttribute(name)) and editScript =
     * editScript@pre->append(<i>update operation</i>))</code>
     *
     * </td>
     * </tr>
     * </table>
     * 
     * @param n1 a node from tree T<sub>1</sub>
     * @param n2 the node that matches <code>n1</code> in tree T<sub>1</sub> */

    protected final void update(final TreeNode n1, final TreeNode n2)
    {
	try
	{
	    final Map values = diffAttributes(n1, n2);
	    if(values.size() > 0)
	    {
		final Update update = new Update(n1, values);
		m_editScript.append(update);
		update.apply();
	    }
	}
	catch(EditApplicationException ex2)
	{
	    ex2.printStackTrace();
	}
	catch(IncompatibleNodeException ex)
	{
	    ex.printStackTrace();
	}

    }

    /**
     * Moves the partner of <code>x</code> to its matching position in
     * its appropriate position in <code>x</code>'s parent node's
     * partner.  The move operation is appended to the edit script.
     * This function handles the case where w is not already a child
     * of x's parent's partner in T<sub>1</sub>.  The positional
     * alignment of w relative to its parent's partner takes place in
     * <code>align()</code>.
     *
     * @param n1 the partner of n2 in T<sub>1</sub>
     * @param n2 the current node in T<sub>2</sub>
     * @see #align
     * should be placed in.  */

    protected final void move(final TreeNode n1, final TreeNode n2)
    {
	// we don't need to do anything if this is the root node.
	if(n1.getID() == 0) return;

	final TreeNode y = n2.getParent();
	final TreeNode v = n1.getParent();
	final TreeNode z = m_matched.matchT2toT1(y);

	// If the current parent of n1 does not match the current
	// parent of n2's partner in T1, generate the move operation
	// that will put n1 in the correct place in T1.

	if(z != v)
	{
	    try
	    {
		final int k = findPosition(n2);
		final Move move = new Move(n1, z, n2.getCollection(), k);
		m_editScript.append(move);
		move.apply();
		
	    }
	    catch(EditApplicationException e)
	    {
		e.printStackTrace();
	    }
	}
    }

    private static boolean xorIsNull(final Object o1, final Object o2)
    {
	return (((o1 == null) && (o2 != null)) || 
		(o1 != null) && (o2 == null));
    }

    /**
     * Returns a <code>Map</code> that contains all the attribute
     * differences between <code>n2</code> and </code>n1</code>.  The
     * <code>Map</code> contains the attribute name as its key, and
     * node <code>n2</code>'s value as its value.  If <code>n1</code>
     * is <code>null</code>, the map will contain all the attributes
     * of <code>n2</code>.  <p>
     *
     * <table>
     * <tr> 
     * <td style="vertical-align: top">&#171;precondition&#187;&#58;
     * </td> 
     * <td><code>(n2 &lt;&gt; null) and (n2.Label = n1.Label)</code>
     * </td> 
     * </tr>
     * <tr>
     * <td style="vertical-align: top">&#171;postcondition&#187;&#58;</td> 
     *
     * <td> 
     * <code>
     * n2.attributeNames->forAll(name : String | <br>
     * &nbsp;&nbsp;&nbsp;(n1 = null or (n1.getAttribute(name) != n2.getAttribute(name))) implies<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;result.includes(n2.getAttribute(name))) and <br>
     * n2.attributeNames->forAll(name : String | <br>
     * &nbsp;&nbsp;&nbsp;(n1 &lt;&gt; null and (n1.getAttribute(name) = n2.getAttribute(name))) implies<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; not result.includes(n2.getAttribute(name)))
     * </code>
     * </td> 
     * </tr> 
     * </table>
     *
     * @param n1 the node from T<sub>1</sub>
     * @param n2 the node from T<sub>2</sub>
     * @exception IncompatibleNodeException if <code>(n1 &lt;&gt;
     * null) and (n1.getLabel() &lt;&gt; n2.getLabel())</code>
     * @return a map that contains the attributes in <code>n2</code> and not in <code>n1</code>.  
     * */

    protected final Map diffAttributes(final TreeNode n1, final TreeNode n2)
	throws IncompatibleNodeException
    {
	if(n1 != null && !n1.getLabel().equals(n2.getLabel())) 
	{
	    throw new IncompatibleNodeException();
	}
	final Map m = new HashMap();
	final Iterator i = n2.attributeNames();
	while(i.hasNext())
	{
	    try
	    {
		final String name = i.next().toString();
		Object n2Value = n2.getAttribute(name);
		Object n1Value = null;
		if(n1 != null)
		{
		    n1Value = n1.getAttribute(name);
		}
		if(n1 == null || 
		   xorIsNull(n1Value, n2Value) || (n2Value != null && !n2Value.equals(n1Value)))
		{
		    m.put(name, n2Value);
		}
	    }
	    catch(InvalidAttributeException e)
	    {
		e.printStackTrace();
	    }

	}
	return m;
    }

    /**
     * Inserts a node that matches node <code>n2</code> into the
     * parent node <code>parent1</code>.  The collection it is placed
     * in is determined by the collection that <code>n2</code> is
     * currently in.  The positioned it is placed in is determined by
     * the <code>findPosition()</code> function.  An Insert operation
     * is appended to the edit script.
     * @param parent1 The node in T<sub>1</sub> that will contain the
     * inserted node.
     * @param n2 The node in T<sub>2</sub> that will be copied and
     * then inserted into T<sub>1</sub> */

    protected final void insert(TreeNode parent1, TreeNode n2)
    {
	int pos = findPosition(n2);
	try
	{
	    final Map values = diffAttributes(null, n2);
	    debug.Debug.assert(parent1 != null);
	    final Insert insert = new Insert(parent1, n2.getLabel(), 
				       n2.getCollection(), pos, values);
	    final TreeNode newNode = insert.apply();

	    m_editScript.append(insert);
	    m_matched.add(newNode, n2);

	}
	catch(EditApplicationException e1)
	{
	    e1.printStackTrace();
	}
	catch(IncompatibleNodeException e)
	{
	    e.printStackTrace();
	}
    }

    /**
     * Applies the appropriate edit operations to the node
     * <code>n2</code> from T<sub>2</sub>.  If <code>n2</code> does
     * not have a match in T<sub>1</sub> a new node is created in
     * T<sub>1</sub> and an insert operation is created.  Otherwise,
     * appropriate update and move operations are applied.
     * @param n2 a node form T<sub>2</sub> 
     */

    protected final void doEdits(final TreeNode n2)
    {
	 final TreeNode n1 = m_matched.matchT2toT1(n2);
	 if(n1 != null)
	 {
	    update(n1, n2);
	    move(n1, n2);
	 }
	 else
	 {

	    insert(m_matched.matchT2toT1(n2.getParent()), n2);
	 }
    }
   
   /**
    * Returns the edit script that is the result of the diff.  This
    * value is only valid after <code>run()</code> is called.
    * 
    * @see run()
    */

   public final EditScript getScript()
   {
	return m_editScript;
   }


    /**
     * Returns the root node of T<sub>1</sub>.  During the Diff
     * process, T<sub>1</sub> is changed to be isomorphic to
     * T<sub>2</sub>.  If this is called before <code>run()</code>,
     * T<sub>1</sub> will be the parsed tree of the first Java
     * program.  If this is called after <code>run()</code>,
     * T<sub>1</sub> will be a tree isomorphic to T<sub>2</sub>.
     */

    public final RootNode getTree1()
    {
	return m_tree1;
    }

    /**
     * Returns the root node of T<sub>2</sub>.
     */

    public final RootNode getTree2()
    {
	return m_tree2;
    }

}
