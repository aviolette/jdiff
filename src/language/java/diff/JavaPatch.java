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

import treediff.edit.EditScript;
import language.java.parser.JParser;
import java.io.*;
import tree.*;

/**
 * Applies an edit script generated with <code>JavaDiff</code> to a
 * Java program and produces the second Java program.
 * @author Andrew Violette
 */

public final class JavaPatch
{

   /**
    * Applies an edit script to a Java program to produce another
    * program.
    * <br><br>
    * Usage&#58; <code>javautil.JavaPatch &lt;file&gt; &lt;editscript&gt; [&lt;output file&gt;]</code>
    */
   
   public static void main(String args[])
   {
	if(args.length < 2)
	{
	   System.err.println("usage: JPatch <file> <editScript> <output>");
	   System.exit(-1);
	}
	try
	{
	   // Parse the Java file and produce the AST
	   InputStreamReader reader = new InputStreamReader(new FileInputStream(args[0]));
	   
	   Parser parser = new JParser();
	   
	   RootNode root = parser.parse(reader, true);
	   
	   String s = (args.length == 3) ? args[2] : null;
	   
	   PrintWriter writer = null;
	   if(args.length == 3)
	   {
		writer = new PrintWriter(new FileOutputStream(args[2]));
	   }
	   else
	   {
		writer = new PrintWriter(System.out);
	   }
	   
	   JavaPrinter printer = new JavaPrinter(writer);
	   
	   EditScript script = new EditScript();
	   script.load(new File(args[1]), root);
	   script.apply();
	   
	   printer.print(root);
	   
	   writer.close();
	}
	catch(Exception e)
	{
	   e.printStackTrace();
	}
	
   }
}
