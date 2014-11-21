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
 * Andrew Violette - aviolette@acm.org
 *
 */

import com.sun.xml.parser.Resolver;
import com.sun.xml.tree.XmlDocument;
import org.w3c.dom.*;
import java.util.*;
import java.io.*;

public class TreePrinter
{

    private Vector m_queue;
    private Map m_nodes;

    public TreePrinter()
    {
    }

    private void printProperties(TreeNode node, XmlDocument factory, Element xmlNode)
    {
	Iterator i = node.attributeNames();
	Element properties = factory.createElement("properties");
	xmlNode.appendChild(properties);

	while(i.hasNext())
	{
	    try
	    {
		String name = i.next().toString();
		Element xmlProperty = factory.createElement("property");
		xmlProperty.setAttribute("name", name);
		Object value = node.getAttribute(name);
		if(value == null) value = "";
		xmlProperty.appendChild(factory.createTextNode(value.toString()));
		properties.appendChild(xmlProperty);
	    
	    }
	    catch(InvalidAttributeException e)
	    {
		e.printStackTrace();
	    }
	}

    }

    private void printNode(final TreeNode node, final XmlDocument factory)
    {
	final Element xmlNode = factory.createElement("node");
	m_nodes.put(node, xmlNode);
	xmlNode.setAttribute("id", (new Integer(node.getID())).toString());
	xmlNode.setAttribute("label", node.getLabel());
	xmlNode.setAttribute("parentcol", (new Integer(node.getCollection())).toString());

	printProperties(node, factory, xmlNode);
	printCollections(node, factory, xmlNode);
	final TreeNode parent = node.getParent();
	final Element xmlParent = findParentCol(parent, node.getCollection());
	xmlParent.appendChild(xmlNode);

    }

    private Element findParentCol(TreeNode node, int col)
    {
	final Element xmlParent = (Element)m_nodes.get(node);
	final NodeList nodes = xmlParent.getChildNodes();
	for(int i=0; i < nodes.getLength(); i++)
	{
	    final Element e = (Element)nodes.item(i);
	    if(e.getTagName().equals("collection"))
	    {
		String attr = e.getAttribute("id");
		if(attr.equals((new Integer(col)).toString()))
		{
		    return e;
		}
	    }
	}
	return xmlParent;

    }

    private void printCollections(TreeNode node, XmlDocument factory, Element parent)
    {
	final int count = node.getCollectionCount();
	for(int i=0; i < count; i++)
	{
	    final Element xmlNode = factory.createElement("collection");
	    TreeNode.CollectionMetaData md = node.getCollectionMetaData(i);
	    xmlNode.setAttribute("id", (new Integer(i)).toString());
	    xmlNode.setAttribute("description", md.getDescription());
	    parent.appendChild(xmlNode);
	}
    }

    public void paintWhite(RootNode node)
    {
	for(int i=0; i < node.getDescendentCount(); i++)
	{
	    node.find(i).setColor(TreeNode.WHITE);
	}
    }

    public void printTree(Writer writer, RootNode root)
	throws java.io.IOException
    {
	m_nodes = new HashMap();

	if(root.getColor() != TreeNode.WHITE)
	{
	    paintWhite(root);
	}

	final XmlDocument doc = new XmlDocument();
	final Element e = doc.createElement("tree");
	doc.appendChild(e);

	m_nodes.put(null, e);

	m_queue = new Vector(1000);
	colorGray(root);
	TreeNode node;

	while(!m_queue.isEmpty())
	{
	    node = (TreeNode)m_queue.firstElement();
	    printNode(node, doc);
	    colorChildrenGray(node);
	    m_queue.remove(0);
	    node.setColor(TreeNode.BLACK);
	}

	doc.write(writer);
	
    }

    private void colorGray(final TreeNode n)
    {
	if(n.getColor() == TreeNode.WHITE)
	{
	    n.setColor(TreeNode.GRAY);
	    m_queue.addElement(n);
	}
    }

    private void colorChildrenGray(final TreeNode n)
    {
	final int count = n.getCollectionCount();
	for(int i=0; i < count; i++)
	{
	    Iterator j = n.children(i);
	    while(j.hasNext())
	    {
		TreeNode node = (TreeNode)j.next();
		colorGray(node);
	    }
	}
    }

}

