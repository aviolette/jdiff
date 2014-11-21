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
 * Andrew Violette 
 * aviolette@acm.org
 *
 */

/**
 * The <code>RootNode</code> class is a special subinterface of
 * <code>TreeNode</code> that represents the root node in an abstract
 * syntax tree.  It contains methods that allow querying of nodes
 * within the tree.  
 */

public interface RootNode 
extends TreeNode
{
    /**
     * Returns a <code>Vector</code> that contains all the nodes in
     * the tree with the specified label.  
     */
    
    public java.util.Vector chain(String label);

    /**
     * Returns a node in the tree with the specified ID, or
     * <code>null</code> if the node was not a member of the tree.  
     */

    public TreeNode find(int id);

    /**
     * Returns the number of elements in the tree.
     */

    public long getDescendentCount();

    /**
     * Returns true if the RootNode is indexed.  If it is
     * indexed, children can be retrieved from the tree in constant
     * time.  Otherwise, children are retrieved in
     * theta(N<sup>2</sup>) time, where N is the number of nodes in the tree.
     */

    public boolean isIndexed();

    /**
     * Returns an <code>Iterator</code> that iterates over the labels
     * that are present in the abstract syntax tree.
     */

    public java.util.Iterator labels();

}
