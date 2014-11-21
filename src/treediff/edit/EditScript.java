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


import java.io.*;
import java.util.*;
import tree.*;

import com.sun.xml.parser.Resolver;
import com.sun.xml.tree.XmlDocument;
import org.xml.sax.InputSource;
import org.w3c.dom.*;

/**
 * The <code>EditScript</code> class maintains reading, writing of an
 * edit script to/from a file.  
 * @author Andrew Violette
 */

public class EditScript
{

   private EditListener m_listener;
   private int m_insertCount;

   private static String s_DTDURI =  "file:///c:/my documents/classes/thesis/code/treedelta.dtd";
   private static String s_DTDFPI = "-//DePaul//DTD Tree Delta//EN";

   public static void setDTDURI(String uri)
   {
	s_DTDURI = uri;
   }
   
   public static String getDTDURI()
   {
	return s_DTDURI;
   }

   public static void setDTDFPI(String fpi)
   {
	s_DTDFPI = fpi;
   }

   public static String getDTDFPI()
   {
	return s_DTDFPI;
   }

   /**
    * Adds a listener that will listen for edit operations to be
    * applied to the tree.  Only one listener can be added to the edit script.
    * @param e the listener
    * @exception TooManyListenersException if more than one listeners is added.
    */

   public void addEditListener(EditListener e)
	throws TooManyListenersException
   {
	m_listener = e;
   }
   
   private Vector m_operations;
   
   /**
    * Constructs an empty edit script.
    */
   
   public EditScript()
   {
	m_operations = new Vector();
   }

   private void loadOperation(Element e, RootNode tree)
   {
	try
	{
	   Edit ed = null;
	   if(e.getTagName().equalsIgnoreCase("insert"))
	   {
		ed = new Insert();
		m_insertCount++;
	   }
	   else if(e.getTagName().equalsIgnoreCase("update"))
	   {
		ed = new Update();
	   }
	   else if(e.getTagName().equalsIgnoreCase("delete"))
	   {
		ed = new Delete();
	   }
	   else if(e.getTagName().equalsIgnoreCase("move"))
	   {
		ed = new Move();
	   }
	   else
	   {
		debug.Debug.assert(false);
	   }
	   ed.load(e, tree);
	   m_operations.addElement(ed);
	}
	catch(EditParseException ex)
	{
	   System.err.println("An operation could not be loaded.  The exception is...");
	   ex.printStackTrace();
	}
   }

   /**
    * Loads the edit script from a file.
    *
    * @return the number of nodes that will be added to the tree
    */

   public int load(File f, RootNode tree)
   {
 	try
	{
	   InputSource input = Resolver.createInputSource(f);
	   XmlDocument doc = XmlDocument.createXmlDocument(input, false);
	   Element root = doc.getDocumentElement();
	   root.normalize();
	   NodeList list = root.getChildNodes();
	   int len = list.getLength();
	   for(int i=0; i < len; i++)
	   {
		Node n = list.item(i);

		if(n != null && n.getNodeType() == Node.ELEMENT_NODE)
		{
		   loadOperation((Element)n, tree);
		}
	   }
	   return m_insertCount;
	}
	catch(Exception e)
	{
	   e.printStackTrace();
	}
	return 0;
   }


   /**
    * Writes the edit script to the file.
    */

   public void write(Writer w)
	throws java.io.IOException, DOMException
   {
	XmlDocument doc = new XmlDocument();
	doc.setDoctype(s_DTDFPI, s_DTDURI, null);
	Element el = doc.createElement("tree_delta");
	
	Iterator i = operations();
	while(i.hasNext())
	{
	   ((Edit)(i.next())).write(el,doc);
	}
	
	doc.appendChild(el);
	doc.write(w);
	
	
   }
    
   /**
    * Applies all the operations in the edit script to the tree.  
    */

   public final void apply()
   {
	Iterator i = operations();
	try
	{
	   while(i.hasNext())
	   {
		Edit e = (Edit)i.next();
		e.apply(m_listener);
	   }
	}
	catch(EditApplicationException e)
	{
	   System.err.println("The edit script could not be applied.  The exception follows:");
	   e.printStackTrace();
	}
   }

   /**
    * Appends an operation to the edit script.
    */

   public final void append(Edit e)
   {
	m_operations.addElement(e);
   }

   /**
    * Returns an iterator that iterates across the operations that
    * make up the edit script.  
    */

   public final Iterator operations()
   {
	return m_operations.iterator();
   }

   /**
    * Defines an interface that the <code>foreach</code> operation
    * calls for each <code>Edit</code> operation in the edit script.
    * Implement this interface when it is necessary to cycle through
    * the edit operations.
    */

   public interface EditScriptDispatch
   {
	public void move(Move edit, Iterator i);
	public void delete(Delete edit, Iterator i);
	public void insert(Insert edit, Iterator i);
	public void update(Update edit, Iterator i);
   }
    

   /**
    * Cycles through each edit operation and sends the appropriate
    * event in the dispatch interface.  
    * @param dispatch the dispatch that whose appropriate methods will
    * be called for each edit operation.
    */

   public void foreach(EditScriptDispatch dispatch)
   {
	final Iterator i = m_operations.iterator();
	while(i.hasNext())
	{
	   Edit e = (Edit)i.next();
	   if(e instanceof Move) dispatch.move((Move)e, i);
	   else if(e instanceof Delete) dispatch.delete((Delete)e, i);
	   else if(e instanceof Update) dispatch.update((Update)e, i);
	   else if(e instanceof Insert) dispatch.insert((Insert)e, i);
	}
	
   }

   /**
    * Returns the total cost of the edit script.
    * @return the cost.
    */
   
   public final float cost()
   {
	final Iterator i = m_operations.iterator();
	float cost = (float)0.0;
	while(i.hasNext())
	{
	   cost += ((Edit)i.next()).cost();
	}
	return cost;
   }
   
}
