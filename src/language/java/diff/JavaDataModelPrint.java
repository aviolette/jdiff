package language.java.diff;

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

import language.java.ast.*;
import tree.*;

import com.sun.xml.tree.XmlDocument;
import org.w3c.dom.*;
import java.util.*;
import java.io.*;

/**
 * Builds an XML file that describes the data model of the Java abstract
 * syntax tree.
 *
 */

public class JavaDataModelPrint
{

    private static String cardinalityToString(int i)
    {
	switch(i)
	{
	case TreeNode.CollectionMetaData.CARD_0_n:
	    return "0..n";
	case TreeNode.CollectionMetaData.CARD_1_1:
	    return "1..1";
	case TreeNode.CollectionMetaData.CARD_0_1:
	    return "0..1";
	case TreeNode.CollectionMetaData.CARD_1_n:
	    return "1..n";
	default:
	    debug.Debug.assert(false);
	    return "";
	}

    }

    private static void writeNodeChildren(language.java.ast.Node n, 
							Element parent, XmlDocument doc)
    {
	int count = n.getCollectionCount();
	if(count == 0) return;
	
	Element e = doc.createElement("collections");
	
	for(int i = 0; i < count; i++)
	{
	    Element col = doc.createElement("collection");
	    Element id = doc.createElement("id");
	    
	    id.appendChild(doc.createTextNode((new Integer(i)).toString()));
	    col.appendChild(id);

	    TreeNode.CollectionMetaData md = n.getCollectionMetaData(i);

		Element children = doc.createElement("children");
		Element child = doc.createElement("child");
		
		System.out.println("node: "+n+", md: "+md+", child: "+child);
		child.appendChild(doc.createTextNode(md.getChildType().toString()));

		children.appendChild(child);
		col.appendChild(children);

		Element card = doc.createElement("cardinality");
		card.appendChild(doc.createTextNode(cardinalityToString(md.getCardinality())));
		col.appendChild(card);

		Element description = doc.createElement("description");
		description.appendChild(doc.createTextNode(md.getDescription()));
		col.appendChild(description);


	    e.appendChild(col);
	}

	parent.appendChild(e);
    }

    private static void writeAttributes(language.java.ast.Node n, Element parent, XmlDocument doc)
    {
	Iterator i = n.attributeNames();
	if(!i.hasNext()) return;
	
	Element e = doc.createElement("attributes");

	while(i.hasNext())
	{
	    String aname = i.next().toString();
	    Element attr = doc.createElement("attribute");
	    Element name = doc.createElement("attrname");

	    name.appendChild(doc.createTextNode(aname));

	    TreeNode.AttributeMetaData md = n.attributeMetaData(aname);
	    
	    Element type = doc.createElement("attrtype");
	    type.appendChild(doc.createTextNode(md.getType().toString()));
	    
	    Element nullable = doc.createElement("attrnull");
	    nullable.appendChild(doc.createTextNode((new Boolean(md.isNullable())).toString()));

	    Element desc = doc.createElement("description");
	    desc.appendChild(doc.createTextNode(md.getDescription()));

	    Element def = doc.createElement("attrdefault");
	    Object o = md.getDefault();
	    if(o == null)
	    {
		o = "null";
	    }
	    def.appendChild(doc.createTextNode(o.toString()));
	    
	    
	    attr.appendChild(name);
	    attr.appendChild(type);
	    attr.appendChild(nullable);
	    attr.appendChild(def);
	    attr.appendChild(desc);
	    e.appendChild(attr);
	}
	
	parent.appendChild(e);
    }

    private static void writeNode(language.java.ast.Node n, Element parent, XmlDocument doc)
	throws Exception
    {
	Element e = doc.createElement("label");
	Element name = doc.createElement("name");
	name.appendChild(doc.createTextNode(n.getLabel()));
	e.appendChild(name);
	Element c = doc.createElement("class");
	String cl = n.getClass().toString();
	cl = cl.substring(6);
	
	c.appendChild(doc.createTextNode(cl));
	e.appendChild(c);

	writeNodeChildren(n, e, doc);
	writeAttributes(n, e, doc);
	parent.appendChild(e);
    }

    private static class NodeLabelComparator
	implements Comparator
    {
	public int compare(Object o1, Object o2)
	{
	    return (o1.toString().compareTo(o2.toString()));
	}
    }

   /**
    * Builds an XML file that describes the Java data model.  Usage&#58; 
    * <br>
    * <code>javautil.JavaDataModelPrint &lt;output file&gt;</code>
    */

    public static void main(String args[])
    {

	if(args.length != 1)
	{
	    System.err.println("usage: javautil.JavaDataModelPrint <file>");
	}

	try
	{
	    debug.Debug.debugMode = false;

	    CompilationUnit.registerAllNodes();
	    Iterator i = language.java.ast.Node.allLabels();
	    XmlDocument doc = new XmlDocument();
	    
	    Element e = doc.createElement("datamodel");

	    TreeSet s = new TreeSet(new NodeLabelComparator() );
	    while(i.hasNext())
	    {
		s.add(i.next());
	    }

	    i = s.iterator();

	    while(i.hasNext())
	    {
		String label = i.next().toString();
		Class c = language.java.ast.Node.getClassFromLabel(label);
		language.java.ast.Node n = (language.java.ast.Node)c.newInstance();
		writeNode(n, e, doc);
	    }
	    
	    doc.appendChild(e);
	    doc.write(new BufferedWriter(new FileWriter(args[0])));

	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
}
