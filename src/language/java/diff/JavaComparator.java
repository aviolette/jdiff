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
import java.util.*;
/**
 * The <code>JavaComparator</code> class is used to roughly match
 * nodes in an abstract syntax tree.  If the nodes are not of the same
 * class, a match will never be made.  It is meant to be configureable
 * so that different properties can be used to configure when two
 * nodes are matched.  
 */
public final class JavaComparator
   extends ASTTraverser
   implements Comparator
{
   private Node m_n1 = null;
   private boolean m_ret = false;

   public void setFilterProperites(final Map map)
   {
	// Not implemented yet...
   }
   private boolean xorIsNull(final Object o1, final Object o2)
   {
	return (((o1 == null) && (o2 != null)) || (o1 != null) && (o2 == null));
   }

   public int compare(final Object o1, final Object o2)
   {
	if(!(o1 instanceof Node) || !(o2 instanceof Node)) return 1;
	return equal((Node)o1, (Node)o2) ? 0 :  1;
   }

   public boolean equal(final Object o1, final Object o2)
   {
	if(!(o1 instanceof Node) || !(o2 instanceof Node)) return false;
	return equal((Node)o1, (Node)o2);
   }

   public boolean equal(final Node n1, final Node n2)
   {

	if(xorIsNull(n1, n2)) return false;
	if(!n1.getLabel().equals(n2.getLabel())) return false;

	m_n1 = n1;
	n2.accept(this);

	return m_ret;
   }

   protected void visit(final CompilationUnit node)
   {
	// root nodes are always equal
	m_ret = true;
   }

   protected void visit(final ImportDeclaration node)
   {
	ImportDeclaration n2 = (ImportDeclaration)m_n1;
	m_ret = (n2.getName().equals(node.getName()) && (n2.isOnDemand() == node.isOnDemand()));
   }

   protected void compareTypeDeclaration(final TypeDeclaration node)
   {
	TypeDeclaration n2 = (TypeDeclaration)m_n1;
	if(n2.getClass() != node.getClass())
	{
	   m_ret = false;
	   return;
	}
	boolean ret = node.getDepth() == n2.getDepth();


	if(ret && node.getDepth() > 1)
	{
	   ret = node.getName().equals(n2.getName());
	}

	m_ret = ret;

   }

   protected void visit(final ClassDeclaration node)
   {
	compareTypeDeclaration(node);
   }

   protected void visit(final FieldDeclaration node)
   {
	m_ret = true;
   }

   protected void visit(final Cast node)
   {
	if(!parentsEqual(node, m_n1))
	{
	   m_ret = false;
	   return;
	}
	m_ret = node.getType().equals(((Cast)m_n1).getType());
   }

   protected void visit(final InterfaceDeclaration node)
   {
	compareTypeDeclaration(node);
   }

   protected void visit(final InstanceOf node)
   {
	m_ret = true;
   }

   protected void visit(final ForStatement node)
   {
	m_ret = true;
   }

   protected void visit(final Initializer node)
   {
	m_ret = true;
   }

   protected void compareMethodSignatures(final AbstractMethodDeclaration node)
   {
	// ret is declared locally, rather than using the m_ret
	// value because equal(..) is invoked below which modifies
	// the global m_ret value.

	AbstractMethodDeclaration n2 = (AbstractMethodDeclaration)m_n1;
	boolean ret = node.getName().equals(n2.getName());

	boolean paramsEqual = true;
	Iterator i = node.parameters();
	Iterator j = n2.parameters();

	while(i.hasNext() && j.hasNext())
	{
	   Parameter p1 = (Parameter)i.next();
	   Parameter p2 = (Parameter)j.next();
	   if(!p1.getType().equals(p2.getType()))
	   {
		paramsEqual = false;
		break;
	   }
	}
	paramsEqual = paramsEqual && !i.hasNext() && !j.hasNext();
	m_ret = ret && paramsEqual;
   }

   protected void visit(final MethodDeclaration node)
   {
	compareMethodSignatures(node);
   }

   private boolean parentsEqual(final Node n1, final Node n2)
   {
	Node p1 = (Node)n1.getParent();
	Node p2 = (Node)n2.getParent();

	
	return (new JavaComparator()).equal(p1, p2);
	
   }
   protected void visit(final ArrayInitializer node)
   {
	m_ret = true;
   }
   protected void visit(final InfixExpression node)
   {
	m_ret = true;
   }
   protected void visit(final ConstructorInvocation node)
   {
	m_ret = parentsEqual(node, m_n1);
   }
   protected void visit(final IfStatement node)
   {
	m_ret = true;
   }
   protected void visit(final StatementExpression node)
   {
	m_ret = true;
   }
   protected void visit(final PostfixExpression node)
   {
	m_ret = true;
   }
   protected void visit(final Type node) 
   {

	Type n1 = (Type)m_n1;

	m_ret = n1.getName().equals(node.getName()) && 
	   (n1.getDimension() == node.getDimension());
	
   }

   protected void visit(final LocalVariableDeclaration node)
   {
	if(!parentsEqual(m_n1, node))
	{
	   m_ret = false;
	   return;
	}

	m_ret = true;
   }

   protected void visit(final VariableDeclarator node)
   {
	VariableDeclarator n1 = (VariableDeclarator)m_n1;

	if(!parentsEqual(m_n1, node))
	{
	   m_ret = false;
	   return;
	}
	
	m_ret = node.getName().equals(n1.getName());
   }

   protected void visit(final Block node)
   {
	m_ret = parentsEqual(m_n1, node);
   }
   protected void visit(EmptyStatement node)
   {
	m_ret = true;
   }
   protected void visit(LabeledStatement node)
   {
	LabeledStatement n1 = (LabeledStatement)m_n1;
	m_ret = n1.getStatementLabel().equals(node.getStatementLabel());
   }
   protected void visit(BreakStatement node)
   {
	BreakStatement n1 = (BreakStatement)m_n1;
	if(xorIsNull(node.getBreakLabel(), n1.getBreakLabel())) 
	{ 
	   m_ret = false; 
	   return; 
	}
	else if(node.getBreakLabel() == null) 
	{
	   m_ret = true;
	   return;
	}

	m_ret = node.getBreakLabel().equals(n1.getBreakLabel());
   }
   protected void visit(ReturnStatement node)
   {
	m_ret = parentsEqual(node, m_n1);
   }
   protected void visit(WhileStatement node)
   {
	m_ret = parentsEqual(node, m_n1);
   }
   protected void visit(Parameter node)
   {
	Parameter p = (Parameter)m_n1;
	if(!equal(p.getType(), node.getType()))
	{
	   m_ret = false;
	   return;
	}
	m_ret = true;
   }

   protected void visit(Literal node)
   {
	Literal n1 = (Literal)m_n1;
	if((n1.getValue() == null) && (node.getValue() == null) )
	{ 
	   m_ret = true; 
	   debug.Debug.assert(n1.getType().getName().equals(Type.ST_NULL)); 
	   return; 
	}
	if(xorIsNull(n1.getValue(), node.getValue())) return;
	m_ret = n1.getValue().equals(node.getValue());
   }

   protected void visit(ConditionalExpression node)
   {
	m_ret = true;
   }

   protected void visit(Identifier node)
   {
	Identifier n1 = (Identifier)m_n1;
	m_ret = n1.getName().equals(n1.getName());
   }
   protected void visit(FieldAccess node)
   {
	FieldAccess n1 = (FieldAccess)m_n1;
	m_ret = n1.getName().equals(node.getName());
   }
   protected void visit(ArrayAccess node)
   {
	m_ret = true;
   }
   protected void visit(SynchronizedBlock node)
   {
	m_ret = true;
   }
   protected void visit(ContinueStatement node)
   {
	ContinueStatement n1 = (ContinueStatement)m_n1;
	if(xorIsNull(node.getContinueLabel(), n1.getContinueLabel())) 
	{ 
	   m_ret = false; 
	   return; 
	}
	else if(node.getContinueLabel() == null) 
	{
	   m_ret = true;
	   return;
	}

	m_ret = node.getContinueLabel().equals(n1.getContinueLabel());
   }
   protected void visit(ThrowStatement node)
   {
	m_ret = true;
   }
   protected void visit(ConstructorDeclaration node)
   {
	compareMethodSignatures(node);
   }
   protected void visit(ClassAccess node)
   {
	ClassAccess n1 = (ClassAccess)m_n1;
	m_ret = n1.getType().equals(node.getType());
   }
   protected void visit(UnaryExpression node)
   {
	m_ret = true;
   }

   protected void visit(Name node)
   {
	m_ret = node.equals(m_n1);
   }

   protected void visit(TryBlock node)
   {
	m_ret = true;
   }
   protected void visit(Switch node)
   {
	m_ret = true;
   }

   protected void visit(Switch.Case node)
   {
	m_ret = true;
   }

   protected void visit(TryBlock.CatchBlock node)
   {
	m_ret = true;
   }

   protected void visit(Initialization node)
   {
	m_ret = true;
   }
   protected void visit(Grouping node)
   {
	m_ret = true;
   }
   protected void visit(MethodInvocation node)
   {
	if(!parentsEqual(node, m_n1)) return;

	MethodInvocation n1 = (MethodInvocation)m_n1;
	if(n1.getName() == null || node.getName() == null) return;
	m_ret = n1.getName().equals(node.getName());

   }
    

    
}
