package treediff.edit;

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
import com.sun.xml.tree.XmlDocument;
import org.w3c.dom.*;
import tree.*;

/**
 * This class defines an edit operation that transforms one tree into
 * another.  Operations are provided that serialize the operation to
 * and from an XML document, and to apply the operation to a tree.  */

public abstract class Edit
{

    private TreeNode m_node;
    
    /**
     * Sets the node that the edit operation is being performed on.
     */

    protected final void setNode(final TreeNode node)
    {
	m_node = node;
    }

    /**
     * Returns the node that the edit operation is being performed on.
     */

    public final TreeNode getNode()
    {
	return m_node;
    }

    /**
     * Writes the edit operation to the XML document.
     * @param e the root XML element that will be the parent of this
     * element.
     * @param d the document that is used to create elements.  */

    public abstract void write(Element e, XmlDocument d);
    
    /**
     * Applies the edit operation to the specified tree.  
     */

    public final TreeNode apply() 
	throws EditApplicationException
    {
	return apply(null);
    }


    /**
     * Applies the edit operation to the specified tree.  The edit
     * operation then triggers an appropriate call to the specified
     * listener.
     */

    abstract TreeNode apply(EditListener listener)
	throws EditApplicationException;
    
    /**
     * Loads the operation from a diff file.  
     * @param e the XML element representing the edit operation.
     * @param tree the tree that will be used for Node lookup
     * @throw EditParseException if the data presented in the element
     * or its sub elements could not be fully interpreted.  */
    
    protected abstract void load(Element e, RootNode tree)
	throws EditParseException;

    /**
     * Loads a value tag from a file.  
     * 
     * @param map the map that will be populated with the values from
     * the file.
     * @param node the value node from the diff file.  */
    
    protected void loadCompositeAttributes(Map map, Element node)
    {
	NodeList list = node.getChildNodes();
	for(int i=0; i < list.getLength(); i++)
	{
	    Node n = list.item(i);
	    if(n.getNodeName().equalsIgnoreCase("value") && 
	       n.getNodeType() == Node.ELEMENT_NODE)
	    {
		Element e = (Element)n;
		NamedNodeMap m = e.getAttributes();
		n = m.getNamedItem("name");
		if(n == null) continue;
		String s = n.getNodeValue();
		if(s.length() == 0) continue;
		n = m.getNamedItem("value");
		if(n != null)
		{
		    map.put(s, n.getNodeValue());
		}
		n = m.getNamedItem("isnull");
		if(n != null)
		{
		    String isnull = n.getNodeValue();
		    if((new Boolean(isnull)).booleanValue())
		    {
			map.put(s, null);
		    }
		}
	    }
	}
    }

    /**
     * Writes property values to the element.
     * @param map a map that contains a mapping between the name of
     * the property and its value.
     * @param e the element that will contain the value tag.
     * @param d the document used to create document elements.
     */

    protected void writeCompositeAttributes(Map map, Element e, XmlDocument d)
    {
	Iterator i = map.keySet().iterator();
	while(i.hasNext())
	{
	    String name = i.next().toString();
	    Element value = d.createElement("value");
	    value.setAttribute("name", name);
	    Object o = map.get(name);
	    if(o == null)
	    {
		value.setAttribute("isnull", "true");
		value.setAttribute("value", "");
	    }
	    else
	    {
		value.setAttribute("value", o.toString());
	    }
	    e.appendChild(value);
	    
	}
    }

    protected TreeNode extractNode(String id, Element e, RootNode tree)
    {
	Integer i = new Integer(e.getAttribute(id));
	return (TreeNode)tree.find(i.intValue());
    }

    protected void updateValues(TreeNode node, Map values)
    {
	Iterator i = values.keySet().iterator();
	while(i.hasNext())
	{
	    String name = i.next().toString();
	    if(node.hasAttribute(name))
	    {
		try
		{
		    node.setAttribute(name, values.get(name));
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		}
	    }
	}
    }

   public float cost()
   {
	return (float)1.0;
   }
}
