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

/**
 * The <code>LabeledStatement</code> class represents a statement that
 * has a string label.  
 */

public final class LabeledStatement
extends Statement
{
    private String m_label;
    private Statement m_statement;

    public final static String LABEL = "label";
    public final static int COL_STATEMENT = 0;

    private static java.util.Map s_attributes;
    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Statement.class, "The statement.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
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

    protected void initializeAttributeMap(Map map)
    {
	map.put("StatementLabel", new TreeNode.AttributeMetaData(String.class, false, null, "The label."));
    }

    public int getCollectionCount()
    {
	return 1;
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    /**
     * Returns the statement's label.
     */

    public String getStatementLabel()
    {
	return m_label;
    }
    
    /**
     * Sets the statement's label.
     */

    public void setStatementLabel(String label)
    {
	m_label = label;
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    /**
     * Sets the statement.
     */

    public void setStatement(Statement s)
    {
	m_statement = s;
	s.setParent(this, COL_STATEMENT);
    }

    /**
     * Returns the statement.
     */
    
    public Statement getStatement()
    {
	return m_statement;
    }

    public int childCount(int col)
    {
	if(col == COL_STATEMENT)
	{
	    return (m_statement == null) ? 0 : 1;
	}
	return 0;
    }

    public void addChild(TreeNode n, int col, int pos)
    {
	debug.Debug.assert(col == COL_STATEMENT);
	debug.Debug.assert(pos == 0);

	if(col == COL_STATEMENT)
	{
	    setStatement((Statement)n);
	}
    }

    public boolean removeChild(TreeNode node)
    {
	if(node.getCollection() == COL_STATEMENT)
	{
	    return false;
	}
	return false;
    }
    public Iterator children(int col)
    {
	debug.Debug.assert(col == COL_STATEMENT);
	if(col == COL_STATEMENT)
	{
	    return new SingleValueIterator("Statement");
	}
	return null;
    }

    public TreeNode childAt(int col, int pos)
    {
	if(col == COL_STATEMENT)
	{
	    return m_statement;
	}
	return null;
    }

}
