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

import java.io.*;
import language.java.ast.*;
import java.util.Iterator;
import language.java.parser.*;

/**
 * Prints out a Java abstract syntax tree (AST).
 */

public class JavaPrinter
extends ASTTraverser
{
    private IndentationWriter m_writer;

    private PrinterListener m_listener;
    private String m_endln;
    private String m_space;
    private String m_tab;

    public void addPrinterListener(PrinterListener listener)
    {
	m_listener = listener;
    }

    private void startingNode(final Node n)
    {
	if(m_listener != null)
	{
	    m_listener.startingNode(m_writer, n);
	}
    }

    private void finishingNode(final Node n)
    {
	if(m_listener != null)
	{
	    m_listener.finishingNode(m_writer, n);
	}
    }

    public JavaPrinter(final PrintWriter writer)
    {
	this(writer, "\t", " " ,"\n");
    }

    public JavaPrinter(final PrintWriter writer, String tab, 
		       String space, String endln)
    {
	m_writer = new IndentationWriter(writer, tab, space, endln);
	m_endln = endln;
	m_space = space;
	m_tab = tab;
    }
    
    
    public final void visitChildren(final Iterator i)
    {
	while(i.hasNext())
	{
	    Node child = (Node)i.next();
	    child.accept(this);
	}
    }

    private void writeNamePortion(AbstractMethodDeclaration node)
    {
	m_writer.write(m_space);
	m_writer.write(node.getName());
	m_writer.write("(");

	Iterator i = node.parameters();
	writeNodeList(i);

	m_writer.write(")");

	i = node.throwsList();
	if(i.hasNext())
	{
	    m_writer.incrementLevel();
	    endln();
	    m_writer.print("throws ");
	    writeNodeList2(i);
	    m_writer.decrementLevel();
	}
	Block b = node.getBlock();
	if(b != null)
	{
	    endln();
	    visit(b);
	}
	else
	{
	    m_writer.write(";");
	    endln();

	}
    }
    public void visit(MethodDeclaration node)
    {
	startingNode(node);
	StringBuffer modifiers = new StringBuffer(100);
	modifiers.append(node.getVisibility().toString());
	if(modifiers.length() > 0) { modifiers.append(m_space); }
	
	if(node.isFinal())
	{
	    modifiers.append("final ");
	}
	if(node.isStatic())
	{
	    modifiers.append("static ");
	}
	if(node.isAbstract())
	{
	    modifiers.append("abstract ");
	}
	if(node.isNative())
	{
	    modifiers.append("native ");
	}
	if(node.isSynchronized())
	{
	    modifiers.append("synchronized ");
	}

	m_writer.print(modifiers.toString());
	writeType(node.getReturnType());
	writeNamePortion(node);
	finishingNode(node);
    }
    protected void visit(Cast node)
    {
	startingNode(node);
	m_writer.write("(");
	node.getType().accept(this);
	m_writer.write(")");
	node.getExpression().accept(this);
	finishingNode(node);
    }
    protected void visit(Initializer node)
    {
	startingNode(node);
	if(node.isStatic())
	{
	    m_writer.print("static");
	    endln();
	}
	node.getBlock().accept(this);
	finishingNode(node);
    }
    protected void visit(ArrayInitializer node)
    {
	startingNode(node);
	m_writer.write("{");
	writeNodeList2(node.inits());
	m_writer.write("}");
	finishingNode(node);
    }
    protected void visit(InterfaceDeclaration node)
    {
	startingNode(node);
	String modifiers = node.getVisibility().toString();
	if(modifiers.length() > 0) { modifiers += m_space; };

	if(node.isAbstract())
	{
	    modifiers += "abstract ";
	}
	if(node.isFinal())
	{
	    modifiers += "final ";
	}
	if(node.isStatic())
	{
	    modifiers += "static ";
	}

	m_writer.print(modifiers + "interface ");
	m_writer.write(node.getName());
	endln();
	if(node.childCount(InterfaceDeclaration.COL_EXTENDS) > 0)
	{
	    m_writer.print("extends ");
	    writeNodeList2(node.extendedInterfaces());
	    endln();
	}
	
	m_writer.print("{");
	endln();
	m_writer.incrementLevel();
	visitChildren(node.declarations());
	m_writer.decrementLevel();
	endln();
	m_writer.print("}");
	endln();
	finishingNode(node);
    }
    protected void visit(Switch node)
    {
	startingNode(node);
	m_writer.print("switch(");
	node.getSwitch().accept(this);
	m_writer.write(")");
	endln();
	m_writer.print("{");
	endln();
	visitChildren(node.cases());
	m_writer.print("}");
	finishingNode(node);
    }

    protected void visit(Switch.Case node)
    {
	startingNode(node);
	Expression label = node.getCaseLabel();
	if(label != null)
	{
	    m_writer.print("case ");
	    label.accept(this);
	    m_writer.write(":");
	}
	else
	{
	    m_writer.print("default:");
	}
	endln();
	m_writer.incrementLevel();
	visitChildren(node.statements());
	m_writer.decrementLevel();
	finishingNode(node);
    }
    protected void visit(InstanceOf node)
    {
	startingNode(node);
	node.getLeft().accept(this);
	m_writer.write(" instanceof ");
	node.getRight().accept(this);
	finishingNode(node);
    }

    protected void visit(ConstructorInvocation node)
    {
	startingNode(node);
	if(node.isThis())
	{
	    m_writer.print("this(");
	}
	else
	{
	    m_writer.print("super(");
	}
	writeNodeList2(node.arguments());
	m_writer.write(")");
	endl();
	finishingNode(node);

    }
    public void visit(Type node) 
    {
	startingNode(node);
	writeType(node);
	finishingNode(node);
    }

    public void visit(Parameter node)
    {
	startingNode(node);
	if(node.isFinal())
	{
	    m_writer.write("final ");
	}
	writeType(node.getType());
	m_writer.write(m_space);
	visit(node.getVariable());	
	finishingNode(node);

    }
    
    public void visit(LocalVariableDeclaration v)
    {
	startingNode(v);
	String sig = "";
	if(v.isFinal())
	{
	    sig = "final ";
	}
	if(v.isAsStatement())
	{
	    m_writer.print(sig);
	}
	else
	{
	    m_writer.write(sig);
	}
	writeType(v.getType());
	m_writer.write(m_space);
	writeNodeList(v.variables());
	if(v.isAsStatement())
	{
	    endl();
	}
	finishingNode(v);

    }
    protected void visit(IfStatement node)
    {
	startingNode(node);
	m_writer.print("if (");
	node.getExpression().accept(this);
	m_writer.write(")");
	endln();
	node.getThen().accept(this);
	endln();
	Statement n = node.getElse();
	if(n != null)
	{
	    m_writer.print("else");
	    endln();
	    n.accept(this);
	}
	finishingNode(node);
	
    }

    public void visit(final ForStatement node)
    {
	startingNode(node);
	m_writer.print("for (");
	visitIfNonNull(node.getInitializer());
	m_writer.write("; ");
	visitIfNonNull(node.getConditional());
	m_writer.write("; ");
	writeNodeList2(node.updates());
	m_writer.write(")");
	endln();
	node.getStatement().accept(this);
	finishingNode(node);

    }
    protected void visit(ArrayAccess node)
    {
	startingNode(node);
	node.getExpression().accept(this);
	m_writer.write("[");
	node.getAccessExpression().accept(this);
	m_writer.write("]");
	finishingNode(node);

    }

    public void visit(BreakStatement node)
    {
	startingNode(node);
	m_writer.print("break");
	if(node.getBreakLabel() != null)
	{
	    m_writer.write(m_space + node.getBreakLabel());
	}
	endl();
	finishingNode(node);

    }

    protected void visit(StatementExpression node)
    {
	startingNode(node);
	if(node.isAsStatement())
	{
	    m_writer.print("");
	}
	node.getExpression().accept(this);
	if(node.isAsStatement())
	{
	    endl();
	}
	finishingNode(node);

    }

    public void visit(Block node)
    {
	startingNode(node);
	m_writer.print("{");
	endln();
	m_writer.incrementLevel();
	visitChildren(node.statements());
	m_writer.decrementLevel();
	m_writer.print("}");
	endln();
	finishingNode(node);

    }

    private void writeType(Type t)
    {
	startingNode(t);
	m_writer.write(t.getName());
	m_writer.write(generateDimension(t.getDimension()));
	finishingNode(t);

    }
    public void visit(CompilationUnit node)
    {
	startingNode(node);
	if(node.getPackage() != null)
	{
	    m_writer.write("package " + node.getPackage());
	    endl();
	}
	visitChildren(node.imports());
	visitIfNonNull(node.getTypeDeclaration());
	finishingNode(node);
    }
    public void visit(ImportDeclaration node)
    {
	startingNode(node);
	String s = "";
	if(node.isOnDemand())
	{
	    s = ".*";
	}
	m_writer.print("import " + node.getName() + s + ";");
	endln();
	finishingNode(node);
    }

    private void endln()
    {
	m_writer.write(m_endln);
    }

    public void visit(final FieldDeclaration node)
    {
	startingNode(node);
	String modifiers = node.getVisibility().toString();
	if(modifiers.length() > 0) { modifiers += m_space; }
	
	if(node.isTransient())
	{
	    modifiers += "transient ";
	}
	if(node.isVolatile())
	{
	    modifiers += "volatile ";
	}
	if(node.isStatic())
	{
	    modifiers += "static ";
	}
	if(node.isFinal())
	{
	    modifiers += "final ";
	}
	Type t = node.getType();

	m_writer.print(modifiers + t.getName());
	m_writer.write(generateDimension(t.getDimension()) + m_space);
	writeNodeList(node.variables());
	endl();
	finishingNode(node);
    }
    public void visit(EmptyStatement node)
    {
	startingNode(node);
	m_writer.print(";");
	endln();
	finishingNode(node);
    }

    public void visit(LabeledStatement node)
    {
	startingNode(node);
	m_writer.decrementLevel();
	m_writer.print(node.getStatementLabel() + ":");
	endln();
	m_writer.incrementLevel();
	node.getStatement().accept(this);
	finishingNode(node);
    }
    private void writeNodeList(Iterator i)
    {
	boolean first = true;

	while(i.hasNext())
	{
	    if(!first)
	    {
		m_writer.write(m_writer.indent("", 3));
	    }
	    ((Node)i.next()).accept(this);
	    if(i.hasNext())
	    {
		m_writer.write(",");
		endln();
	    }
	    first = false;
	}
    }

    public void visit(ConstructorDeclaration node)
    {
	startingNode(node);
	m_writer.print(node.getVisibility().toString());	
	writeNamePortion(node);
	
	
	finishingNode(node);
    }
    public void visit(PostfixExpression node)
    {
	startingNode(node);
	node.getExpression().accept(this);
	m_writer.write(Operator.asString(node.getOperator()));
	finishingNode(node);
    }
    private String generateDimension(int dim)
    {
	if(dim == 0) { return ""; }

	StringBuffer b = new StringBuffer(2*dim + 1);
	for(int i=0; i < dim; i++)
	{
	    b.append("[]");
	}
	return b.toString();
    }
    public void visit(VariableDeclarator node)
    {
	startingNode(node);
	StringBuffer buffer = new StringBuffer(50);
	m_writer.write(node.getName());
	m_writer.write(generateDimension(node.getDimension()));
	Node n = node.getInitializer();
	if(n != null)
	{
	    m_writer.write(" = ");
	    n.accept(this);
	}
	finishingNode(node);
    }
    public void visit(Literal node)
    {
	startingNode(node);
	if(node.getType().equals(Type.ST_CHAR))
	{
	    m_writer.write("'" + node.getValue().toString() + "'");
	}
	else if(node.getType().equals(Type.ST_STRING))
	{
	    m_writer.write("\"" + node.getValue() + "\"");
	}
	else if(node.getType().equals(Type.ST_NULL))
	{
	    m_writer.write("null");
	}
	else
	{
	    m_writer.write(node.getValue().toString());
	}
	finishingNode(node);
    }
    public void visit(UnaryExpression node)
    {
	startingNode(node);
	m_writer.write(Operator.asString(node.getOperator()));
	node.getExpression().accept(this);
	finishingNode(node);
    }
    public void visit(WhileStatement node)
    {
	startingNode(node);
	if(node.isDo())
	{
	    m_writer.print("do");
	    endln();
	    node.getStatement().accept(this);
	    m_writer.print("while(");
	    node.getExpression().accept(this);
	    m_writer.write(")");
	    endl();
	}
	else
	{
	    m_writer.print("while(");
	    node.getExpression().accept(this);
	    m_writer.write(")");
	    endln();
	    node.getStatement().accept(this);
	}
	finishingNode(node);
    }
    
    public void visit(ConditionalExpression node)
    {
	startingNode(node);
	node.getCondition().accept(this);
	m_writer.write(" ? ");
	node.getIfTrue().accept(this);
	m_writer.write(" : ");
	node.getIfFalse().accept(this);
	finishingNode(node);
	
    }

    public void visit(InfixExpression node)
    {
	startingNode(node);
	
	if(node.getLeft() != null) node.getLeft().accept(this);
	m_writer.write(m_space + Operator.asString(node.getOperator()) + m_space);
	if(node.getRight() != null) node.getRight().accept(this);
	finishingNode(node);
    }

    public void handleTypeDeclaration(TypeDeclaration node)
    {
    }

    public void visit(ClassDeclaration node)
    {
	startingNode(node);
	String superClass = node.getExtends();
	String modifiers = node.getVisibility().toString();
	if(modifiers.length() > 0) { modifiers += m_space; };

	if(node.isAbstract())
	{
	    modifiers += "abstract ";
	}
	if(node.isFinal())
	{
	    modifiers += "final ";
	}
	if(node.isStatic())
	{
	    modifiers += "static ";
	}

	endln();
	m_writer.print(modifiers + "class " + node.getName());
	endln();
	if(superClass != null)
	{
	    m_writer.print("extends " + superClass);
	    endln();
	}
	Iterator i = node.implementedInterfaces();
	boolean first = true;
	String s = null;
	while(i.hasNext())
	{
	    if(first)
	    {
		s = "implements ";
		first = false;
	    }
	    Name name = (Name)i.next();
	    s += name.getValue();
	    if(i.hasNext())
	    {
		s += ", ";
	    }
	}
	if(s != null)
	{
	    m_writer.print(s);
	    endln();
	}
	m_writer.print("{");
	endln();
	m_writer.incrementLevel();
	visitChildren(node.declarations());
	m_writer.decrementLevel();
	m_writer.print("}");
	endln();
	finishingNode(node);
    }

    public void visit(SynchronizedBlock node)
    {
	startingNode(node);
	m_writer.print("synchronized (");
	node.getExpression().accept(this);
	m_writer.write(")");
	endln();
	node.getBlock().accept(this);
	finishingNode(node);

    }
    public void visit(ContinueStatement node)
    {
	startingNode(node);
	m_writer.print("continue ");
	if(node.getContinueLabel() != null)
	{
	    m_writer.write(node.getContinueLabel());
	}
	endl();
	finishingNode(node);
    }
    public void visit(ThrowStatement node)
    {
	startingNode(node);
	m_writer.print("throw ");
	node.getExpression().accept(this);
	endl();
	finishingNode(node);
    }

    public void visit(Identifier node)
    {
	startingNode(node);
	m_writer.write(node.getName());
	finishingNode(node);
    }

    public void visit(ReturnStatement node)
    {
	startingNode(node);
	m_writer.print("return");
	if(node.getExpression() != null)
	{
	    m_writer.write(m_space);
	    node.getExpression().accept(this);
	}
	endl();
	finishingNode(node);
    }
    private void endl()
    {
	m_writer.write(";"+m_endln);

    }
    public void visit(ClassAccess node)
    {
	startingNode(node);
	m_writer.write(node.getType().getName());
	m_writer.write(".class");
	finishingNode(node);
    }
    public void visit(FieldAccess node)
    {
	startingNode(node);
	node.getExpression().accept(this);
	m_writer.write(".");
	m_writer.write(node.getName());
	finishingNode(node);

    }
    public void visit(Name node)
    {
	startingNode(node);
	m_writer.write(node.getValue());
	finishingNode(node);
    }

    private void visitStringList(Iterator i)
    {
	while(i.hasNext())
	{
	    m_writer.write(i.next().toString());
	    if(i.hasNext()) m_writer.write(", ");
	}
    }

    public void visit(MethodInvocation node)
    {
	startingNode(node);
	//	m_writer.write(node.getName());
	node.getExpression().accept(this);
	m_writer.write("(");
	writeNodeList2(node.arguments());
	m_writer.write(")");
	finishingNode(node);
    }
    protected void visit(TryBlock node)
    {
	startingNode(node);
	m_writer.print("try");
	endln();
	node.getTry().accept(this);
	visitChildren(node.catches());
	if(node.getFinally() != null)
	{
	    m_writer.print("finally");
	    endln();
	    node.getFinally().accept(this);
	}
	finishingNode(node);
    }
    protected void visit(TryBlock.CatchBlock node)
    {
	startingNode(node);
	m_writer.print("catch(");
	node.getParameter().accept(this);
	m_writer.write(")");
	endln();
	node.getBlock().accept(this);
	finishingNode(node);
    }

    private void writeNodeList2(Iterator i)
    {
	while(i.hasNext())
	{
	    Node n = (Node)i.next();
	    n.accept(this);
	    if(i.hasNext())
	    {
		m_writer.write(", ");
	    }
	}
    }

    protected void visit(Initialization node)
    {
	startingNode(node);
	m_writer.write("new ");
	m_writer.write(node.getType().getName());
	if(node.isArrayConstruction())
	{
	    Iterator i = node.inits();
	    while(i.hasNext())
	    {
		m_writer.write("[");
		Node n = (Node)i.next();
		n.accept(this);
		m_writer.write("]");
	    }
	}
	else
	{
	    m_writer.write("(");
	    writeNodeList2(node.inits());
	    m_writer.write(")");
	    visitIfNonNull(node.getClassBody());
	}
	finishingNode(node);
    }
  

    protected void visit(Grouping node)
    {
	startingNode(node);
	m_writer.write("(");
	node.getExpression().accept(this);
	m_writer.write(")");
	finishingNode(node);
    }

    public void print(tree.TreeNode node)
    {
	((Node)node).accept(this);
    }

    public static void main(String args[])
    {
	if(args.length != 1 && args.length != 2)
	{
	    System.err.println("usage: JavaPrinter <in> [<out>]");
	}
	try
	{
	    PrintWriter writer = null;
	    if(args.length == 2)
	    {
		writer = new PrintWriter(new FileOutputStream(args[1]));
	    }
	    else
	    {
		writer = new PrintWriter(System.out);
	    }

	    System.out.println("// Printing file " + args[0]+ "...");

	    JavaParser parser = new JavaParser(new InputStreamReader(new FileInputStream(args[0])));
	    CompilationUnit u = parser.parseCU();
	    JavaPrinter printer = new JavaPrinter(writer);
	    printer.visit(u);
	    writer.close();
	}
	catch(Throwable pe)
	{
	   pe.printStackTrace();

	}
    }

}
