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


/**
 * The <code>ASTTraverser</code> class should be extended by any class
 * that wants to traverse the abstract syntax tree.  Each abstract
 * syntax tree (AST) node extends from the <a
 * href="Node.html">Node</a> class.  The <code>accept()</code> method
 * on the <code>Node</code> object will call the appropriate
 * <code>visit</code> method on the <code>ASTTraverser</code> object
 * for the type of Node being visited.  This is an implementation of
 * the Visitor pattern.
 *
 * @see Node 
 * @author Andrew Violette 
 *
 */


public abstract class ASTTraverser
{
    /**
     * Visits any concrete node if the object specified is non-null.
     * 
     */

    public final void visitIfNonNull(Node n)
    {
	if(n != null)
	{
	    n.accept(this);
	}
    }

    protected abstract void visit(InstanceOf node);
    protected abstract void visit(CompilationUnit node);
    protected abstract void visit(ImportDeclaration node);
    protected abstract void visit(ClassDeclaration node);
    protected abstract void visit(FieldDeclaration node);
    protected abstract void visit(MethodDeclaration node);
    protected abstract void visit(LocalVariableDeclaration node);
    protected abstract void visit(Parameter node);
    protected abstract void visit(VariableDeclarator node);
    protected abstract void visit(Block node);
    protected abstract void visit(EmptyStatement node);
    protected abstract void visit(LabeledStatement node);
    protected abstract void visit(BreakStatement node);
    protected abstract void visit(ReturnStatement node);
    protected abstract void visit(WhileStatement node);
    protected abstract void visit(Literal node);
    protected abstract void visit(ConditionalExpression node);
    protected abstract void visit(Identifier node);
    protected abstract void visit(FieldAccess node);
    protected abstract void visit(ClassAccess node);
    protected abstract void visit(Name node);
    protected abstract void visit(MethodInvocation node);
    protected abstract void visit(Type node);
    protected abstract void visit(ForStatement node);
    protected abstract void visit(InfixExpression node);
    protected abstract void visit(UnaryExpression node);
    protected abstract void visit(ConstructorDeclaration node);
    protected abstract void visit(ContinueStatement node);
    protected abstract void visit(ThrowStatement node);
    protected abstract void visit(SynchronizedBlock node);
    protected abstract void visit(PostfixExpression node);
    protected abstract void visit(TryBlock node);
    protected abstract void visit(TryBlock.CatchBlock node);
    protected abstract void visit(Initialization node);
    protected abstract void visit(StatementExpression node);
    protected abstract void visit(Initializer node);
    protected abstract void visit(Grouping node);
    protected abstract void visit(ArrayAccess node);
    protected abstract void visit(ConstructorInvocation node);
    protected abstract void visit(ArrayInitializer node);
    protected abstract void visit(IfStatement node);
    protected abstract void visit(Cast node);
    protected abstract void visit(InterfaceDeclaration node);
    protected abstract void visit(Switch node);
    protected abstract void visit(Switch.Case node);
}
