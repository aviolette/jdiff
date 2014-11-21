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
 * The <code>LocalVariableDeclaration</code> class represents the
 * declaration of a variable within a block of code.  
 */

public final class LocalVariableDeclaration
extends VariableDeclaration
{
    public static final String LABEL = "variable";
    private static java.util.Map s_attributes;
    private boolean m_bAsStatement;

    protected Map attributeMap()
    {
	if(s_attributes == null)
	{
	    s_attributes = new HashMap();
	    initializeAttributeMap(s_attributes);
	}
	return s_attributes;
    }

    public void initializeAttributeMap(Map map)
    {
	map.put("AsStatement", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE, "True if the declaration is in the context of a statement, or false if it is defined within an initializer portion of a for-statement."));
	super.initializeAttributeMap(map);
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }

    /**
     * Constructs a local variable declaration.
     */

    public LocalVariableDeclaration()
    {
	setFinal(false);
    }
    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    public final void setAsStatement(boolean b)
    {
	m_bAsStatement = b;
    }

    public final boolean isAsStatement()
    {
	return m_bAsStatement;
    }
    
}





