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


import tree.*;

/**
 * The <code>EmptyStatement</code> represents a no-op statement
 * (whitespace ending in a semicolon).  
 */

public final class EmptyStatement
extends Statement
{
    public static final String LABEL = "empty";

    public int getCollectionCount()
    {
	return 0;
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	debug.Debug.assert(false);
    }
    public TreeNode childAt(int col, int pos)
    {
	return null;
    }


    public java.util.Iterator children(int col)
    {
	debug.Debug.assert(false);
	return null;
    }
    public boolean removeChild(TreeNode n)
    {
	return false;
    }
    
    public int childCount(int col)
    {
	debug.Debug.assert(false);
	return 0;
    }

}
