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
 * The <code>ConstructorDeclaration</code> class represents the
 * definition of a constructor for a class.  
 */

public final class ConstructorDeclaration
extends AbstractMethodDeclaration
{

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

    private final static TreeNode.CollectionMetaData s_collectionData[] = 
    {
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Parameter.class, "The parameter list."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_1_1,
					Block.class, "The code block."),
	new TreeNode.CollectionMetaData(TreeNode.CollectionMetaData.CARD_0_n,
					Name.class, "The throws list.")
    };

    public TreeNode.CollectionMetaData getCollectionMetaData(int id)
    {
	return s_collectionData[id];
    }

    
    /**
     * constructor - the node's label
     */

    public static final String LABEL = "constructor";

    public final String getPrivateLabel()
    {
	return LABEL;
    }

    public ConstructorDeclaration()
    {
    }

    public void accept(ASTTraverser visitor)
    {
	visitor.visit(this);
    }
    
}


