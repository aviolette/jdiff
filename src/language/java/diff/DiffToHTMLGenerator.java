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


import debug.Debug;
import java.io.*;
import java.util.*;
import treediff.*;
import tree.*;
import treediff.edit.*;
import language.java.parser.JavaParser;
import language.java.ast.CompilationUnit;
import com.sun.xml.parser.Resolver;
import com.sun.xml.tree.XmlDocument;
import org.xml.sax.InputSource;
import org.w3c.dom.*;

/**
 * The <code>DiffToHTMLGenerator</code> generates HTML files that
 * display a visual representation of the compared Java programs.
 * @author Andrew Violette
 */


public class DiffToHTMLGenerator
implements PrinterListener, EditListener
{


    private CompilationUnit m_tree1;
    private CompilationUnit m_tree2;
    private String m_htmlRoot;
    private Stack m_nodeStack;

    private XmlDocument m_frameDoc;

    private boolean m_2ndTree;
    private Vector m_deleted;
    private Vector m_inserted;
    private Vector m_moved;
    private Vector m_updated;
    private String[] m_labels;

    private boolean m_delayUpdate;
    private String m_currentTextClass;
    private int m_currentID;

    public DiffToHTMLGenerator(String htmlFile)
    {
	m_htmlRoot = htmlFile;
	m_frameDoc = new XmlDocument();
	initializeFrameset();
	initializeNavigationBar();
    }

    public void close()
	throws IOException
    {
	Writer writer;
	writer = new BufferedWriter(new FileWriter(m_htmlRoot + ".html"));
	m_frameDoc.write(writer);

	writeTree(m_htmlRoot + "l.html", "left", m_tree1);
	m_2ndTree = true;
	writeTree(m_htmlRoot + "r.html", "right", m_tree2);
	
    }

    private void writeTree(String file, String title, RootNode tree)
	throws IOException
    {
	PrintWriter pwriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
	
	beginHTMLDoc(pwriter, title);
	m_currentTextClass = "normal";
	JavaPrinter printer = new JavaPrinter(pwriter, 
					      "&nbsp;&nbsp;&nbsp;&nbsp;", 
					      "&nbsp;", "\n<br>");
	printer.addPrinterListener(this);
	printer.visit((CompilationUnit)tree);
	endHTMLDoc(pwriter);
	pwriter.close();
    }

    private boolean containsNode(Vector v, TreeNode node)
    {
	int id = node.getID();
	Iterator i = v.iterator();
	while(i.hasNext())
	{
	    if(((TreeNode)i.next()).getID() == id)
	    {
		return true;
	    }
	}
	return false;
    }

    private String getLabelForNode(TreeNode node)
    {
	
	if(m_labels[node.getID()] != null)
	{
	    return (String)m_labels[node.getID()];
	}
	String s = "delta" + (new Integer(++m_currentID)).toString();
	m_labels[node.getID()] = s;
	return s;
    }

    private void removeNode(Vector v, int id)
    {
	Iterator i = v.iterator();
	while(i.hasNext())
	{
	    TreeNode node = (TreeNode)i.next();
	    if(node.getID() == id)
	    {
		v.remove(node);
		return;
	    }
	}
    }
    
    private void modifyCurrentClass(IndentationWriter writer, TreeNode node, boolean remove)
    {
	Vector col = null;
	String classBefore = m_currentTextClass;
	if(m_2ndTree && containsNode(m_inserted, node))
	{
	    m_currentTextClass = "inserted";
	    col = m_inserted;
	}
	else if(containsNode(m_updated, node))
	{
	    if(!m_delayUpdate)
	    {
		m_currentTextClass = "updated";
	    }
	    col = m_updated;

	}
	else if(!m_2ndTree && containsNode(m_deleted, node))
	{
	    m_currentTextClass = "deleted";
	    col = m_deleted;
	}
	else if(containsNode(m_moved, node))
	{
	    m_currentTextClass = "moved";
	    col = m_moved;
	}
	else
	{
	    m_currentTextClass = "normal";
	}

	if(col != null && remove)
	{
	    removeNode(col, node.getID());
	}
	if(!classBefore.equals(m_currentTextClass)) 
	{
	    if(classBefore.equals("normal"))
	    {
		writer.write("</span>");
	    }
	    else
	    {
		writer.write("</a>");
	    }
	    if(m_currentTextClass.equals("normal"))
	    {
		writer.write("<span class=\""+m_currentTextClass+"\">");
	    }
	    else
	    {
		String name = "name=\""+getLabelForNode(node)+"\"";
		writer.write("<a "+name+" onclick=\"hiliteTarget(this)\" class=\""+m_currentTextClass+"\">");
	    }
	}

    }

    public void startingNode(IndentationWriter writer, TreeNode node)
    {
	m_nodeStack.push(node);
 
	modifyCurrentClass(writer, node, false);

    }

    public void finishingNode(IndentationWriter writer, TreeNode node)
    {

	m_nodeStack.pop();

	if(!m_nodeStack.empty())
	{
	    TreeNode n = (TreeNode)m_nodeStack.peek();
	    if(n != null)
	    {
		modifyCurrentClass(writer, n, false);
	    }
	}

    }

    public void updateApplied(Update update)
    {
	m_updated.addElement(update.getNode());
    }

    public void insertApplied(Insert insert)
    {
	m_inserted.addElement(insert.getNode());
    }

    public void moveApplied(Move move)
    {
	m_moved.addElement(move.getNode());
    }
    
    public void deleteApplied(Delete delete)
    {
	m_deleted.addElement(delete.getNode());
    }

    private void initializeFrameset()
    {
	final Element root = m_frameDoc.createElement("html");
	
	final Element frameset = m_frameDoc.createElement("frameset");
	frameset.setAttribute("rows", "*,120");

	final Element frameset2 = m_frameDoc.createElement("frameset");
	frameset2.setAttribute("cols", "50%,50%");
	

	Element e = m_frameDoc.createElement("frame");
	e.setAttribute("src", m_htmlRoot + "l.html");
	e.setAttribute("name", "leftFrame");
	frameset2.appendChild(e);
	
	e = m_frameDoc.createElement("frame");
	e.setAttribute("src", m_htmlRoot + "r.html");
	e.setAttribute("name", "rightFrame");
	frameset2.appendChild(e);

	frameset.appendChild(frameset2);

	e = m_frameDoc.createElement("frame");
	e.setAttribute("src", m_htmlRoot + "nav.html");
	e.setAttribute("name", "navbar");
	frameset.appendChild(e);

	root.appendChild(frameset);
	m_frameDoc.appendChild(root);
    }

    private void initializeNavigationBar()
    {
	try
	{
	    PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(m_htmlRoot + "nav.html")));
	    writer.println("<html>");
	    writer.println("<head>");
	    writer.println("<link rel=\"STYLESHEET\" href=\"diff.css\" type=\"text/css\" />");
	    writer.println("</head>");
	    writer.println("<body>");
	    writer.println("<table border=\"0\">");
	    writer.println("<tr><td width=\"300\" valign=\"top\">");
	    writer.println("<span class=\"inserted\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> inserted<br>");
	    writer.println("<span class=\"deleted\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> deleted<br>");
	    writer.println("<span class=\"updated\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> updated<br>");
	    writer.println("<span class=\"moved\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> moved<br>");
	    writer.println("</td>");
	    writer.println("<td valign=\"top\">");
	    writer.println("</td>");
	    writer.println("</tr>");
	    writer.println("</table>");
	    writer.println("</body>");
	    writer.println("</html>");
	    writer.close();
	}
	catch(IOException e)
	{
	    e.printStackTrace();
	}
    }

    private void printScript(PrintWriter writer)
    {
	writer.println("<script>");
	writer.println("function hiliteTarget(targetID)");
	writer.println("{");
	int loc = (m_2ndTree) ? 0 : 1;

	writer.print("\twindow.parent.frames("+loc+").location = \""+m_htmlRoot);
	if(m_2ndTree)
	{
	    writer.print("l.html#");
	}
	else
	{
	    writer.print("r.html#");
	}
	writer.println("\"+targetID;");
	writer.println("}");
	writer.println("</script>");
    }

    private void beginHTMLDoc(PrintWriter writer, String title)
    {
	writer.println("<html>");
	writer.println("<head>");
	writer.println("<title>"+title+"</title>");
	writer.println("<link rel=\"STYLESHEET\" href=\"diff.css\" type=\"text/css\" />");
	writer.println("</head>");
	writer.println("<body>");
	printScript(writer);
	writer.println("<code>");
	writer.println("<span class=\"normal\">");
    }

    private void endHTMLDoc(PrintWriter writer)
    {
	writer.println("</span>");
	writer.println("</code>");
	writer.println("</body>");
	writer.println("</html>");
    }
    

    /**
     * Builds the two trees (both from the file specified by original)
     * and transforms the second tree to the new file by applying the
     * edit operations in the edit script.
     *
     * @exception IOException if the file cannot be opened.
     * @exception com.metamata.parse.ParseException if the file cannot be parsed.
     * @param original the name of the original Java program
     * @param editScript the name of the edit script that transforms
     * the original Java program to the new one.  */

    public void start(String original, String editScript, CompilationUnit tree1, CompilationUnit tree2)
	throws IOException, com.metamata.parse.ParseException
    {
	m_tree1 = tree1;
	m_tree2 = tree2;
	m_deleted = new Vector();
	m_inserted = new Vector();
	m_moved = new Vector();
	m_updated = new Vector();
	m_nodeStack = new Stack();
	int size = (int)m_tree1.getDescendentCount();

	EditScript script = new EditScript();
	try
	{
	    script.addEditListener(this);
	    size += script.load(new File(editScript), m_tree2);
	    size += 1;
	    m_labels = new String[size];
	    script.apply();
	}
	catch(java.util.TooManyListenersException e)
	{
	    e.printStackTrace();
	}



    }

}
