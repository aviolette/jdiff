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
 * The <code>FieldDeclaration</code> class defines a series of field
 * variables.  
 */

public final class FieldDeclaration
extends VariableDeclaration
{
    public static final String LABEL = "field";

    private boolean m_transient = false;
    private boolean m_volatile = false;
    private boolean m_static = false;
    private boolean m_final = false;
    private Visibility m_visibility = Visibility.DEFAULT;

    private static java.util.Map s_attributes;

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
	map.put("Visibility", new TreeNode.AttributeMetaData(Visibility.class, true, 
							     "package private", 
							     "The visibility of the field."));
	map.put("Transient", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE, ""));
	map.put("Volatile", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE, ""));
	map.put("Static", new TreeNode.AttributeMetaData(Boolean.TYPE, false, Boolean.FALSE, ""));
	super.initializeAttributeMap(map);
    }

    public FieldDeclaration()
    {
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }

    public void setVisibility(Visibility visibility)
    {
	m_visibility = visibility;
    }

    public Visibility getVisibility()
    {
	return m_visibility;
    }

    public void setStatic(boolean isStatic)
    {
	m_static = isStatic;
    }

    public boolean isStatic()
    {
	return m_static;
    }
    public void setTransient(boolean isTransient)
    {
	m_transient = isTransient;
    }
    public boolean isTransient()
    {
	return m_transient;
    }

    public void setVolatile(boolean isVolatile)
    {
	m_volatile = isVolatile;
    }

    public boolean isVolatile()
    {
	return m_volatile;
    }

    protected String getPrivateLabel()
    {
	return LABEL;
    }


}

