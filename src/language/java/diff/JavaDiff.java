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
import treediff.edit.*;
import language.java.parser.JavaParser;
import language.java.ast.CompilationUnit;

/**
 * Diffs two Java programs to produce an edit script that can
 * reproduce the second Java program from the first.  This program
 * optionally can generate HTML files that allows the two programs to
 * be viewed visually.
 *
 * If the class is run from the command line, a properties file can be
 * specified.  If no properties file is specified on the command line,
 * the default properties file, jdiff.properties, which is located in
 * the same directory as the running program, is used. The following
 * table describes the properties that can be defined in the
 * properties file, and their default value if they are not specified.
 * </p> <table border="1"> <thead> <tr
 * style="font-weight: bold;"> <td class="name"> Name </td> <td
 * class="desc"> Default value </td> <td> Description </td> </tr>
 * </thead> <tbody> <tr> <td> debugMode </td> <td> false </td> <td> If
 * true is specified, assertions will be enabled, and XML files that
 * represent the tree structures of the two programs will be output
 * before the programs are diffed.  </td> </tr> <tr> <td> DTDFPI </td>
 * <td> -//DePaul//DTD Tree Delta//EN </td> <td> The formal public
 * identifier. There really is no reason why this should be changed.
 * </td> </tr> <tr> <td> DTDURI </td> <td> treedelta.dtd </td> <td>
 * The URI of the tree delta file. JavaPatch will validate the
 * structure of the program based on this URI, so it must be correct.
 * </td> </tr> <tr> <td> verbose </td> <td> false </td> <td> If true
 * is specified, program output will be displayed to standard output
 * </td> </tr> </tbody> </table>
 * 
 */

public final class JavaDiff
   implements Runnable
{
   /**
    * The default properties file, if none is specified
    */
   private static final String DEFAULT_PROPERTY_FILE = "jdiff.properties";

   private String m_file1;
   private String m_file2;
   private String m_scriptFile;
   private String m_htmlRoot;
   private String m_propertyFile;
   private Properties m_properties;
   private CompilationUnit m_tree1;
   private Vector m_filters;
   private boolean m_verbose;
   private boolean m_overrideFilters;
   private String m_comparatorClass;
   private boolean m_displayVersion;

   /**
    * This initialization block populates the properties and the
    * filters list.
    */

   {
	m_overrideFilters = false;
	m_displayVersion = false;
	m_filters = new Vector();
	populateInitialProperties();
   }

   /**
    * Constructs a <code>JavaDiff</code> object that is suitable for
    * running within a thread.  
    *
    * @param file1 the file that will be compared with the second file
    * @param file2 the second file
    * @param scriptFile the file that will contain the edit script
    */

   protected JavaDiff(final String file1, final String file2, 
			    final String scriptFile)
   {
	m_file1 = file1;
	m_file2 = file2;
	m_scriptFile = scriptFile;
	interpretProperties();
   }

   /**
    * Constructs a <code>JavaDiff</code> object that takes the command
    * line arguments of the program that launched it.  The command line
    * has to be in the form: 
    * 
    * <code> language.java.diff.JavaDiff &lt;file1&gt;
    * &lt;file2&gt; &lt;out&gt; [-html &lt;htmlfile&gt;] [-props &lt;propsfile&gt;]
    * </code>
    */

   private JavaDiff(final String args[])
   {
	parseCommandLine(args);
	loadProperties();
	interpretProperties();
   }

   /**
    * Enables/disables debugging.  If debugging is enabled, assertions
    * will be turned on, and two extra files will be output as a
    * result of the diff: tree1.xml and tree2.xml.  These files
    * contain the tree structures of the two Java programs before they
    * were diffed.
    */

   public void enableDebugging(boolean enabled)
   {
	debug.Debug.debugMode = enabled;
   }

   /** 
    * Sets whether or not verbose output is echoed to standard out.  
    * @see #isVerbose */

   public void setVerbose(boolean verbose)
   {
	m_verbose = verbose;
   }
   
   /**
    * Returns whether or not the output is verbose.
    * @see #setVerbose
    */
   
   public boolean isVerbose()
   {
	return m_verbose;
   }

   private void appendFilter(final String filterName)
   {
	try
	{
	   Class c = Class.forName(filterName);
	   DiffFilter f = (DiffFilter)c.newInstance();
	   appendFilter(f);
	   
	}
	catch(ClassNotFoundException ce)
	{
	   System.err.println("The filter "+
				    filterName+" could not be found.");
	   ce.printStackTrace();
	}
	catch(ClassCastException ex)
	{
	   System.err.println("The filter "
				    +filterName+" does not implement DiffFilter.");
	}
	catch(Exception e)
	{
		   System.err.println("The filter "
					    +filterName+" could not be instantiated.");
		   e.printStackTrace();
	}
   }

   /**
    * Appends the filter to the current filter list.
    */

   public void appendFilter(final DiffFilter filter)
   {
	m_filters.addElement(filter);
   }

   /**
    * Plugs default values into the property list.  Before the
    * property list can be used, <code>interpretProperties()</code>
    * must be called.
    * @see #interpretProperties()
    */

   private void populateInitialProperties()
   {
	m_properties = new Properties();
	m_properties.setProperty("DTDFPI", "-//DePaul//DTD Tree Delta//EN");
	m_properties.setProperty("DTDURI", "treedelta.dtd");
	m_properties.setProperty("debugMode", "false");
	m_properties.setProperty("verbose", "false");
	m_properties.setProperty("comparator", "language.java.diff.JavaComparator");

	if(!m_overrideFilters)
	{
	   String filters = m_properties.getProperty("filters");
	   if(filters != null)
	   {
		StringTokenizer tokenizer = new StringTokenizer(filters, ",", false);
		while(tokenizer.hasMoreElements())
		{
		   String s = tokenizer.nextToken();
		   appendFilter(s);
		}
	   }
	}
   }

   /**
    * Interprets the values in the properties file (and the default
    * values that were not specified in the properties file), and
    * populates this object's instance variables with the results.
    */

   private void interpretProperties()
   {
	Debug.debugMode = 
	   (new Boolean(m_properties.getProperty("debugMode"))).booleanValue();
	EditScript.setDTDURI(m_properties.getProperty("DTDURI"));
	EditScript.setDTDFPI(m_properties.getProperty("DTDFPI"));
	setVerbose((new Boolean(m_properties.getProperty("verbose"))).booleanValue());
	String s = m_properties.getProperty("comparator");
	if(s != null)
	{
	   m_comparatorClass = s;
	}
	
   }

   /**
    * Loads the properties from the properties file.
    */

   private void loadProperties()
   {
	if(m_propertyFile == null)
	{
	   final File f = new File(DEFAULT_PROPERTY_FILE);
	   if(f.exists())
	   {
		m_propertyFile = DEFAULT_PROPERTY_FILE;
	   }
	}
	if(m_propertyFile != null)
	{
	   m_properties = new Properties(m_properties);
	   try
	   {
		m_properties.load(new BufferedInputStream(new FileInputStream(m_propertyFile)));
	   }
	   catch(IOException e)
	   {
		System.err.println("Could not load properties file.");
		e.printStackTrace();
	   }
	}
   }

   /**
    * Parses the command line parameters
    * @see #main(String args[]) for command line details.
    */

   private void parseCommandLine(final String args[])
   {
	if(args.length == 1 && args[0].equals("-v"))
	{
	   m_displayVersion = true;
	   return;
	}

	if(args.length < 2)
	{
	   System.err.println("usage: java language.java.JavaDiff <source1> <source2> [<out> [-html <file>]] [-props <file>] [-v]\n");
	   System.err.println("\t-html <file> \tThe base name of the file that will display the results of the diff");
	   System.err.println("\t-props <file>\tThe properties file that has the configuration parameters for jdiff");
	   System.err.println("\t-v            \tDisplays version information");
	   System.exit(-1);
	}
	
	m_file1 = args[0];
	m_file2= args[1];

	int ind = 2;

	// if the XML file is not specified, standard output will 
	// be used in writeScript() when the script is being written

	m_scriptFile = null;

	if((args.length >= 3) && (args[2].charAt(0) != '-'))
	{
	   m_scriptFile = args[2];
	   ind = 3;
	}
	
	Debug.debugMode = false;
	for(int i=ind; i < args.length; i++) 
	{
	   if(args[i].equals("-props"))
	   {
		if(i + 1 < args.length)
		{
		   i++;
		   m_propertyFile = args[i];
		}
		else
		{
		   System.err.println("-props ignored...");
		}
	   }
	   else if(args[i].equals("-v"))
	   {
		m_displayVersion = true;
	   }
	   else if(args[i].equals("-html"))
	   {
		if(m_scriptFile == null)
		{
		   System.err.println("-html ignored because script file needs to be output to file...");
		   continue;
		}
		if(i + 1< args.length)
		{
		   i++;
		   m_htmlRoot = args[i];
		}
		else
		{
		   System.err.println("-html ingored...");
		}
	   }
	   // override filters specified in property file
	   else if(args[i].equals("-o"))
	   {
		m_overrideFilters = true;
	   }
	   else if(args[i].startsWith("-filter:"))
	   {
		String filterName = args[i].substring(8);
		appendFilter(filterName);
	   }
	}
   }

   /**
    * The edit script uses this class to cycle through each edit
    * operation and apply the filters.  
    */

   private class FilterHelper
	implements EditScript.EditScriptDispatch
   {

	private void genericTraverse(Edit e, Iterator i, String label)
	{
	   boolean remove = false;
	   Iterator it = m_filters.iterator();
	   while(it.hasNext())
	   {
		DiffFilter filter = (DiffFilter)it.next();
		if("move".equals(label)) remove = filter.move((Move)e);
		else if("insert".equals(label)) remove = filter.insert((Insert)e);
		else if("delete".equals(label)) remove = filter.delete((Delete)e);
		else if("update".equals(label)) remove = filter.update((Update)e);
		if(remove)
		{
		   i.remove();
		   break;
		}
	   }
	}
	public void move(Move edit, Iterator i)
	{
	   genericTraverse(edit, i, "move");
	}
	public void delete(Delete edit, Iterator i)
	{
	   genericTraverse(edit, i, "delete");
	}
	public void insert(Insert edit, Iterator i)
	{
	   genericTraverse(edit, i, "insert");
	}
	public void update(Update edit, Iterator i)
	{
	   genericTraverse(edit, i, "update");
	}
   }

   /**
    * Applies the filters to the edit operations in the edit script.
    */

   private void filter(EditScript script)
   {
	try
	{
	   JavaParser parser = 
		new JavaParser(new InputStreamReader(new FileInputStream(m_file1)));
	   m_tree1 = parser.parseCU(true);
	}
	catch(java.io.IOException e2)
	{
	   e2.printStackTrace();
	}
	catch(com.metamata.parse.ParseException e)
	{
	   e.printStackTrace();
	}

	Iterator i = m_filters.iterator();
	
	while(i.hasNext())
	{
	   DiffFilter f = (DiffFilter)i.next();
	   f.setRoot(m_tree1);
	}

	script.foreach(new FilterHelper());
   }

   /**
    * Runs the diff, applies the filters, and generates the tree delta
    * outputs the HTML (if specified).
    */

   public void run()
   {

	if(m_displayVersion)
	{
	   System.out.println("JavaDiff version 0.9");
	   System.exit(0);
	}

	Comparator c = null;
	try
	{
	   Class cl = Class.forName(m_comparatorClass);
	   c = (Comparator)cl.newInstance();
	}
	catch(Exception e)
	{
	   System.err.println("Could not instantiate comparator of class "+m_comparatorClass+".");
	   e.printStackTrace();
	   return;
	}

	final Diff differ = new Diff(m_file1, m_file2, 
					     c, new FastMatch(c),
					     new language.java.parser.JParser(), 
					     Debug.debugMode, m_verbose);

	differ.run();

	filter(differ.getScript());

	writeScript(differ.getScript());

	writeHTMLFile();
   }
   
   /**
    * Writes out the HTML files that represent the changes in the Java
    * programs.  
    */
   
   private void writeHTMLFile()
   {
	if(m_htmlRoot != null)
	{
	   try
	   {
		final JavaParser parser = 
		   new JavaParser(new InputStreamReader(new FileInputStream(m_file1)));
		final CompilationUnit tree1 = parser.parseCU(true);
		final DiffToHTMLGenerator diffWriter = new DiffToHTMLGenerator(m_htmlRoot);
		diffWriter.start(m_file1, m_scriptFile, m_tree1, tree1);
		diffWriter.close();
	   }
	   catch(IOException e)
	   {
		e.printStackTrace();
	   }
	   catch(com.metamata.parse.ParseException e1)
	   {
		e1.printStackTrace();
	   }
	}
   }

   /**
    * Writes out the edit script to a file.
    */

   private  void writeScript(final EditScript script)
   {
	try
	{
	   Writer writer = null;
	   if(m_scriptFile == null)
	   {
		writer = new OutputStreamWriter(System.out);
	   }
	   else
	   {
		writer = new BufferedWriter(new FileWriter(m_scriptFile));
	   }

	   script.write(writer);
	}
	catch(IOException e)
	{
	   e.printStackTrace();
	}
   }


   /**
    * Diffs the Java language.  The two files, specified by
    * <code>source1</code> and <code>source2</code>, will be compared
    * and an edit script will be generated in <code>out</code>.  If
    * the html_root is specified several HTML files will be generated.
    * The main HTML file will be the file <html_root>.html.  This file
    * visually displays the differences between the two files and
    * allows navigation to the deltas.
    *
    * Usage&#58; 
    *
    *
    * <br> <code>javautil.JavaDiff &lt;source1&gt; &lt;source2&gt;
    * &lt;out&gt;  [html_root]</code>
    * */

   public static void main(String args[])
   {
	(new JavaDiff(args)).run();
   }
}

