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

public final class Switch
extends Statement
{

    public static final String LABEL = "switch";
    public static final int COL_CASE = 0;
    public static final int COL_SWITCH = 1;

    private Vector m_cases;
    private Expression m_switch;

    public final String getPrivateLabel()
    {
	return LABEL;
    }
    public int getCollectionCount()
    {
	return 2;
    }
    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Switch.Case.class, "The collection of cases."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Expression.class, "The expression that is evaluated to determine which case to activate.")
	
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }
    
    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }
    
    public Switch()
    {
	m_cases = new Vector();
    }

    public final Expression getSwitch()
    {
	return m_switch;
    }

    public void setSwitch(Expression e)
    {
	m_switch = e;
	e.setParent(this, COL_SWITCH);
    }

    public Iterator cases()
    {
	return m_cases.iterator();
    }

    public void addCase(Case s)
    {
	m_cases.addElement(s);
	s.setParent(this, COL_CASE);
    }

    public void addCase(Case s, int pos)
    {
	m_cases.add(pos, s);
	s.setParent(this, COL_CASE);
    }


    public Iterator children(int col)
    {
	if(col == COL_CASE)
	{
	    return cases();
	}
	else if(col == COL_SWITCH)
	{
	    return new SingleValueIterator("Switch");
	}
	debug.Debug.assert(false);
	return null;
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_CASE)
	{
	    return (TreeNode)m_cases.get(pos);
	}
	else if(col == COL_SWITCH)
	{
	    return m_switch;
	}
	return null;
    }

    public int childCount(int col)
    {
	if(col == COL_CASE)
	{
	    return m_cases.size();
	}
	else if(col == COL_SWITCH)
	{
	    return (m_switch == null) ? 0 : 1;
	}
	debug.Debug.assert(false);
	return 0;
    }


    public void addChild(TreeNode n, int col, int pos)
    {
	if(col == COL_CASE)
	{
	    addCase((Case)n, pos);
	}
	else if(col == COL_SWITCH)
	{
	    setSwitch((Expression)n);
	}
	else
	{
	    debug.Debug.assert(false);
	}
    }

    public boolean removeChild(TreeNode n)
    {
	int col = n.getCollection();
	if(col == COL_CASE)
	{
	    return m_cases.remove(n);
	}
	else if(col == COL_SWITCH)
	{
	    return false;
	}
	else
	{
	    debug.Debug.assert(false);
	}
	return false;
    }
    

    public final static class Case
	extends Node
    {
	public static final String LABEL = "case";
	public static final int COL_LABEL = 0;
	public static final int COL_STATEMENTS = 1;

	private Expression m_label;
	private Vector m_statements;
	private final static TreeNode.CollectionMetaData s_collectionData[] = 
	{
	    new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_1,
					    Expression.class, "The expression that is evaluated to determine if the case matches the switch expression.  If this is null, the case node represents the default case."),
	    new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Node.class, "The statements that make up the case body.")
	
	};

	public TreeNode.CollectionMetaData getCollectionMetaData(int id)
	{
	    return s_collectionData[id];
	}
    

	public String getPrivateLabel()
	{
	    return LABEL;
	}

	public int getCollectionCount()
	{
	    return 2;
	}
	public void accept(ASTTraverser visitor)
	{
	    visitor.visit(this);
	}
	public Case()
	{
	    m_statements = new Vector();
	}
	
	public void setCaseLabel(Expression e)
	{
	    m_label = e;
	    if(e != null)
	    {
		e.setParent(this,COL_LABEL);
	    }
	}
	
	public Expression getCaseLabel()
	{
	    return m_label;
	}

	public void addStatement(Node s)
	{
	    m_statements.addElement(s);
	    s.setParent(this, COL_STATEMENTS);
	}

	public void addStatement(Node s, int pos)
	{
	    m_statements.add(pos, s);
	    s.setParent(this, COL_STATEMENTS);
	}
	
	public Iterator statements()
	{
	    return m_statements.iterator();
	}

	public Iterator children(int col)
	{
	    
	    if(col == COL_LABEL)
	    {
		return new SingleValueIterator("CaseLabel");
	    }
	    else if(col == COL_STATEMENTS)
	    {
		return m_statements.iterator();
	    }
	    debug.Debug.assert(false);
	    return null;
	}

	public TreeNode childAt(int col, int pos)
	{
	    if(col == COL_LABEL)
	    {
		return m_label;
	    }
	    else if(col == COL_STATEMENTS)
	    {
		return (TreeNode)m_statements.get(pos);
	    }
	    return null;
	}
	
	public int childCount(int col)
	{
	    if(col == COL_LABEL)
	    {
		return (m_label == null) ? 0 : 1;
	    }
	    else if(col == COL_STATEMENTS)
	    {
		return m_statements.size();
	    }
	    debug.Debug.assert(false);
	    return 0;
	}


	public void addChild(TreeNode n, int col, int pos)
	{
	    if(col == COL_LABEL)
	    {
		setCaseLabel((Expression)n);
	    }
	    else if(col == COL_STATEMENTS)
	    {
		addStatement((Node)n, pos);
	    }
	    else
	    {
		debug.Debug.assert(false);
	    }
	}

	public boolean removeChild(TreeNode n)
	{
	    int col = n.getCollection();
	    if(col == COL_LABEL)
	    {
		setCaseLabel(null);
		return true;
	    }
	    else if(col == COL_STATEMENTS)
	    {
		return m_statements.remove(n);
	    }
	    else
	    {
		debug.Debug.assert(false);
	    }
	    return false;
	}
    


    }

    

}
