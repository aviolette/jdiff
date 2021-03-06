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


import java.util.*;
import debug.Debug;
import tree.*;

/**
 * The <code>FastMatch</code> class is used to build and contain the
 * set of mappings between T<sub>1</sub> and T<sub>2</sub>.  It is an
 * implementation of the FastMatch algorithm in [2] .  The initial
 * matched set is generated by chaining together the nodes of equal
 * labels from both trees.  The longest common sequence of equivalent
 * nodes (as determined by the Comparator interface passed to the
 * constructor) is then generated.  Every matching node in the longest
 * common sequence is added to the match set.  Of the remaining nodes
 * that were not contained in the LCS, if their is an unmatched node
 * in the set from T<sub>1</sub> that matches a remaining node in
 * T<sub>2</sub>, the two are matched and added to the matched set.
 *
 * Matched nodes that are direct descendants of unmatched nodes will
 * not particpate in the final matched set.
 *
 * @author Andrew Violette 
 */

public final class FastMatch
   implements MatchingSet
{
   private Map m_matched;
   private Map m_reverseMatched;
   private RootNode m_tree1;
   private RootNode m_tree2;
   private Comparator m_comparator;

   public FastMatch(final Comparator comparator)
   {
	m_comparator = comparator;
   }

   /**
    * Given two trees, T<sub>1</sub> and T<sub>2</sub>, this method
    * takes a node of tree T<sub>2</sub> and returns a matching node
    * in T<sub>1</sub>.  If there was no matching node,
    * <code>null</code> is returned.
    * 
    * @return the matching Node form T<sub>1</sub>, or
    * <code>null</code> if no match was made.
    * @param n the node from T<sub>2</sub> */

   public TreeNode matchT2toT1(final TreeNode n)
   {
	return (TreeNode)m_matched.get(n);
   }

   public TreeNode matchT1toT2(final TreeNode n)
   {
	return (TreeNode)m_reverseMatched.get(n);
   }

   /**
    * Removes a match from the match set.
    * @param n a node from T<sub>2</sub>.
    */

   private void removeMatchFromT2toT1(final TreeNode n)
   {
	TreeNode n2 = matchT2toT1(n);
	if(n2 != null)
	{
	   m_matched.remove(n);
	   m_reverseMatched.remove(n2);
	}
	
   }

   /**
    * Removes all nodes that are direct descendants of
    * <code>node</code> (including <code>node</code> itself) from the
    * matched set.
    *
    * @param node a node from T<sub>2</sub> whose children will be removed.
    */

   private void removeUnatainableMatchesFromNode(final TreeNode node)
   {
	final int count = node.getCollectionCount();
	removeMatchFromT2toT1(node);
	for(int i=0; i < count; i++)
	{
	   final Iterator j = node.children(i);
	   while(j.hasNext())
	   {
		final TreeNode n2 = (TreeNode)j.next();
		removeUnatainableMatchesFromNode(n2);
	   }
	}
   }

   /**
    * Remove nodes from the matched set that are descendants of unmatched
    * nodes.  
    *
    * @param unmatched an iterator that will iterate through a list of
    * unmatched nodes from T<sub>2</sub>.  */

   private void removeUnatainableMatches(final Iterator unmatched)
   {
	while(unmatched.hasNext())
	{
	   TreeNode node = (TreeNode)unmatched.next();
	   removeUnatainableMatchesFromNode(node);
	}
   }

   /**
    * Build the set of partners in tree T<sub>1</sub> and
    * T<sub>2</sub>.  */

   public void buildMatchSet(final RootNode tree1, final RootNode tree2)
   {
	m_tree1 = tree1;
	m_tree2 = tree2;

	m_matched = new Hashtable();
	m_reverseMatched = new Hashtable();
	final LCSSolver lcsSolver = new LCSSolver(m_comparator);

	final Iterator i = TreeNode.registry.allLabels();
	final Vector unmatchedInT2 = new Vector();
	while(i.hasNext())
	{
	   final String label = i.next().toString();
	   final Vector s1 = m_tree1.chain(label);
	   final Vector s2 = m_tree2.chain(label);

	   final Vector unmatchedX = new Vector();
	   final Vector unmatchedY = new Vector();

	   // Create the matched set of nodes of the same label that participate in
	   // the LCS.
	   lcsSolver.lcs(s1, s2, unmatchedX, unmatchedY, m_matched, m_reverseMatched);

	   final Iterator j = unmatchedX.iterator();

	   // Augment the initial matched set, generated from the LCS, with nodes
	   // that did not participate in the LCS but are equivalent.

	   while(j.hasNext())
	   {
		final TreeNode n = (TreeNode)j.next();
		final Iterator k = unmatchedY.iterator();
		while(k.hasNext())
		{
		   final TreeNode n2 = (TreeNode)k.next();
		   if(m_comparator.compare(n, n2) == 0)
		   {
			add(n, n2);
			unmatchedY.remove(n2);
			break;
		   }
		}
	   }
	   unmatchedInT2.addAll(unmatchedY);
	}
	removeUnatainableMatches(unmatchedInT2.iterator());
   }

   public void add(final TreeNode n1, final TreeNode n2)
   {
	m_matched.put(n2, n1);
	m_reverseMatched.put(n1, n2);
   }

   public java.util.Map getSet()
   {
	return m_matched;
   }
}
