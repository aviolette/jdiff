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


import tree.*;

/**
 * The <code>MatchingSet</code> class defines a service that
 * given a node from one tree, it attempts to provide a matching node
 * in another tree.  Implementations of this algorithm define the
 * criteria for what it means for two nodes to match.
 * */

public interface MatchingSet
{
    /**
     * Returns a matching node in T<sub>1</sub> given a node from
     * T<sub>1</sub> or <code>null</code> if nothing was found.  
     * @param node a node from T<sub>2</sub>
     */

    public TreeNode matchT2toT1(TreeNode node);

    /**
     * Returns a matching node in T<sub>2</sub> given a node from
     * T<sub>2</sub>, or <code>null</code> if nothing was found.
     * @param node a node from T<sub>1</sub> 
     */
    public TreeNode matchT1toT2(TreeNode node);

    /**
     * Builds the initial set of matching nodes.
     * @param root1 the root node of T<sub>1</sub>
     * @param root2 the root node of T<sub>2</sub>
     */

    public void buildMatchSet(RootNode root1, RootNode root2);
    
    /**
     * Adds <code>n1</code> and <code>n2</code>, from T<sub>1</sub>
     * and T<sub>2</sub> respectfully, to the set of matched tuples
     * inside the matched set.  
     */
    public void add(TreeNode n1, TreeNode n2);
    
    /**
     * Returns a mapping from T<sub>2</sub> to T<sub>1</sub>.
     */

    public java.util.Map getSet();
}
