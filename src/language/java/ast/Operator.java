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
 * The <code>Operator</code> class is a utility class that contains
 * the constant definitions for all the operators.  
 */

public final class Operator
{

    /**
     * - the subtraction operator
     */
    public final static int MINUS = 1;
    /**
     * / the division operator
     */
    public final static int DIVIDE = 3;
    /**
     * && the logical AND operator
     */
    public final static int LOG_AND = 4;
    /**
     * || the logical OR operator
     */
    public final static int LOG_OR = 5;
    /**
     * ! the logical NOT operator
     */
    public final static int LOG_NOT = 6;
    /**
     * & the bitwise AND operator
     */
    public final static int BIT_AND = 7;
    /**
     * | the bitwise inclusive OR operator
     */
    public final static int BIT_OR = 8;
    /**
     * ~ the compliment operator
     */
    public final static int BIT_NOT = 9;
    /**
     * ^ the bitwise exclusive OR operator
     */
    public final static int BIT_XOR = 10;
    /**
     * == the equality operator
     */
    public final static int EQ = 11;
    /**
     * != the inequality operator
     */
    public final static int NEQ = 12;
    /**
     * < the less than operator
     */
    public final static int LT = 13;
    /**
     * <= the less than equal operator
     */
    public final static int LE = 14;
    /**
     * > the greater than operator
     */
    public final static int GT = 15;
    /**
     * >= the greater than equal operator
     */
    public final static int GE = 16;
    /**
     * << the left shift operator
     */
    public final static int LS = 17;
    /**
     * >> the right shift operator
     */
    public final static int RS = 18;
    /**
     * >>> the unsigned right shift operator
     */
    public final static int URS = 19;
    /**
     * % the modulo operator
     */
    public final static int MOD = 20;
    /**
     * * the multiply operator
     */
    public final static int MULT = 21;

    /**
     * ++ the increment operator
     */

    public final static int INCR = 22;

    /**
     * -- the decrement operator
     */

    public final static int DECR = 23;
    /**
     * + the addition operator
     */
    public final static int PLUS = 24;

    /**
     * = the assignment operator
     */

    public final static int ASSIGN = 25;

    /**
     * += the plus and assign operator
     */

    public final static int PLUSASSIGN = 26;

    /**
     * *= the multiply and assign operator
     */

    public final static int TIMESASSIGN = 27;
    
    /**
     * /= the divide and assign operator
     */

    public final static int DIVIDEASSIGN = 28;

    /**
     * %= the modulo and assign operator
     */
    
    public final static int MODULOASSIGN = 29;
    
    /**
     * -= the subtract and assign operator
     */

    public final static int MINUSASSIGN = 30;

    /**
     * <<= the shift left and assign operator
     */

    public final static int SLASSIGN = 31;

    /**
     * >>= the shift right and assign operator
     */

    public final static int SRASSIGN = 32;

    /**
     * >>>= the unsigned shift right and assign operator
     */

    public final static int USRASSIGN = 33;

    /**
     * &= the and and assign operator
     */

    public final static int ANDASSIGN = 34;

    /**
     * ^= the exclusive or and assign operator
     */

    public final static int XORASSIGN = 35;

    /**
     * |= the inclusive or and assign operator
     */

    public final static int ORASSIGN = 36;

    
    private Operator()
    {
	
    }

    public final static String asString(int op)
    {
	switch(op)
	{
	case PLUS:
	    return "+";
	case MINUS:
	    return "-";
	case DIVIDE:
	    return "/";
	case LOG_AND:
	    return "&&";
	case LOG_OR:
	    return "||";
	case LOG_NOT:
	    return "!";
	case BIT_AND:
	    return "&";
	case BIT_OR:
	    return "|";
	case BIT_NOT:
	    return "~";
	case BIT_XOR:
	    return "^";
	case EQ:
	    return "==";
	case NEQ:
	    return "!=";
	case LT:
	    return "<";
	case LE:
	    return "<=";
	case GT:
	    return ">";
	case GE:
	    return ">=";
	case LS:
	    return "<<";
	case RS:
	    return ">>";
	case URS:
	    return ">>>";
	case MOD:
	    return "%";
	case MULT:
	    return "*";
	case INCR:
	    return "++";
	case DECR:
	    return "--";
	case ASSIGN:
	    return "=";
	case PLUSASSIGN:
	    return "+=";
	case TIMESASSIGN:
	    return "*=";
	case DIVIDEASSIGN:
	    return "/=";
	case MODULOASSIGN:
	    return "%=";
	case MINUSASSIGN:
	    return "-=";
	case SLASSIGN:
	    return "<<=";
	case SRASSIGN:
	    return ">>=";
	case USRASSIGN:
	    return ">>>=";
	case ANDASSIGN:
	    return "&=";
	case XORASSIGN:
	    return "^=";
	case ORASSIGN:
	    return "|=";
	    
	}

	debug.Debug.assert(false);
	return "";
    }
}
