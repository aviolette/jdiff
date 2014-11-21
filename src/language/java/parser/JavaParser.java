package language.java.parser;
/**
 *
 * This grammar file was updated by Andrew Violette to build a syntax
 * tree for Java somewhere between November 1999 and February 2000.
 *

 */

import language.java.ast.*;
import java.util.*;
import tree.*;

public class JavaParser 
{
    
    
  public interface ScannerConstants {
  
    int EOF = 0;
    int SINGLE_LINE_COMMENT = 9;
    int FORMAL_COMMENT = 10;
    int MULTI_LINE_COMMENT = 11;
    int ABSTRACT = 13;
    int BOOLEAN = 14;
    int BREAK = 15;
    int BYTE = 16;
    int CASE = 17;
    int CATCH = 18;
    int CHAR = 19;
    int CLASS = 20;
    int CONST = 21;
    int CONTINUE = 22;
    int _DEFAULT = 23;
    int DO = 24;
    int DOUBLE = 25;
    int ELSE = 26;
    int EXTENDS = 27;
    int FALSE = 28;
    int FINAL = 29;
    int FINALLY = 30;
    int FLOAT = 31;
    int FOR = 32;
    int GOTO = 33;
    int IF = 34;
    int IMPLEMENTS = 35;
    int IMPORT = 36;
    int INSTANCEOF = 37;
    int INT = 38;
    int INTERFACE = 39;
    int LONG = 40;
    int NATIVE = 41;
    int NEW = 42;
    int NULL = 43;
    int PACKAGE = 44;
    int PRIVATE = 45;
    int PROTECTED = 46;
    int PUBLIC = 47;
    int RETURN = 48;
    int SHORT = 49;
    int STATIC = 50;
    int SUPER = 51;
    int SWITCH = 52;
    int SYNCHRONIZED = 53;
    int THIS = 54;
    int THROW = 55;
    int THROWS = 56;
    int TRANSIENT = 57;
    int TRUE = 58;
    int TRY = 59;
    int VOID = 60;
    int VOLATILE = 61;
    int WHILE = 62;
    int INTEGER_LITERAL = 63;
    int DECIMAL_LITERAL = 64;
    int HEX_LITERAL = 65;
    int OCTAL_LITERAL = 66;
    int FLOATING_POINT_LITERAL = 67;
    int EXPONENT = 68;
    int CHARACTER_LITERAL = 69;
    int STRING_LITERAL = 70;
    int IDENTIFIER = 71;
    int LETTER = 72;
    int DIGIT = 73;
    int LPAREN = 74;
    int RPAREN = 75;
    int LBRACE = 76;
    int RBRACE = 77;
    int LBRACKET = 78;
    int RBRACKET = 79;
    int SEMICOLON = 80;
    int COMMA = 81;
    int DOT = 82;
    int ASSIGN = 83;
    int GT = 84;
    int LT = 85;
    int BANG = 86;
    int TILDE = 87;
    int HOOK = 88;
    int COLON = 89;
    int EQ = 90;
    int LE = 91;
    int GE = 92;
    int NE = 93;
    int SC_OR = 94;
    int SC_AND = 95;
    int INCR = 96;
    int DECR = 97;
    int PLUS = 98;
    int MINUS = 99;
    int STAR = 100;
    int SLASH = 101;
    int BIT_AND = 102;
    int BIT_OR = 103;
    int XOR = 104;
    int REM = 105;
    int LSHIFT = 106;
    int RSIGNEDSHIFT = 107;
    int RUNSIGNEDSHIFT = 108;
    int PLUSASSIGN = 109;
    int MINUSASSIGN = 110;
    int STARASSIGN = 111;
    int SLASHASSIGN = 112;
    int ANDASSIGN = 113;
    int ORASSIGN = 114;
    int XORASSIGN = 115;
    int REMASSIGN = 116;
    int LSHIFTASSIGN = 117;
    int RSIGNEDSHIFTASSIGN = 118;
    int RUNSIGNEDSHIFTASSIGN = 119;
    int __jjVabstract = ABSTRACT;
    int __jjVboolean = BOOLEAN;
    int __jjVbreak = BREAK;
    int __jjVbyte = BYTE;
    int __jjVcase = CASE;
    int __jjVcatch = CATCH;
    int __jjVchar = CHAR;
    int __jjVclass = CLASS;
    int __jjVconst = CONST;
    int __jjVcontinue = CONTINUE;
    int __jjVdefault = _DEFAULT;
    int __jjVdo = DO;
    int __jjVdouble = DOUBLE;
    int __jjVelse = ELSE;
    int __jjVextends = EXTENDS;
    int __jjVfalse = FALSE;
    int __jjVfinal = FINAL;
    int __jjVfinally = FINALLY;
    int __jjVfloat = FLOAT;
    int __jjVfor = FOR;
    int __jjVgoto = GOTO;
    int __jjVif = IF;
    int __jjVimplements = IMPLEMENTS;
    int __jjVimport = IMPORT;
    int __jjVinstanceof = INSTANCEOF;
    int __jjVint = INT;
    int __jjVinterface = INTERFACE;
    int __jjVlong = LONG;
    int __jjVnative = NATIVE;
    int __jjVnew = NEW;
    int __jjVnull = NULL;
    int __jjVpackage = PACKAGE;
    int __jjVprivate = PRIVATE;
    int __jjVprotected = PROTECTED;
    int __jjVpublic = PUBLIC;
    int __jjVreturn = RETURN;
    int __jjVshort = SHORT;
    int __jjVstatic = STATIC;
    int __jjVsuper = SUPER;
    int __jjVswitch = SWITCH;
    int __jjVsynchronized = SYNCHRONIZED;
    int __jjVthis = THIS;
    int __jjVthrow = THROW;
    int __jjVthrows = THROWS;
    int __jjVtransient = TRANSIENT;
    int __jjVtrue = TRUE;
    int __jjVtry = TRY;
    int __jjVvoid = VOID;
    int __jjVvolatile = VOLATILE;
    int __jjVwhile = WHILE;
    int __jjV_iB = LPAREN;
    int __jjV_jB = RPAREN;
    int __jjV_1D = LBRACE;
    int __jjV_3D = RBRACE;
    int __jjV_1C = LBRACKET;
    int __jjV_3C = RBRACKET;
    int __jjV_1B = SEMICOLON;
    int __jjV_mB = COMMA;
    int __jjV_oB = DOT;
    int __jjV_3B = ASSIGN;
    int __jjV_4B = GT;
    int __jjV_2B = LT;
    int __jjV_bB = BANG;
    int __jjV_4D = TILDE;
    int __jjV_5B = HOOK;
    int __jjV_0B = COLON;
    int __jjV_3B_3B = EQ;
    int __jjV_2B_3B = LE;
    int __jjV_4B_3B = GE;
    int __jjV_bB_3B = NE;
    int __jjV_2D_2D = SC_OR;
    int __jjV_gB_gB = SC_AND;
    int __jjV_lB_lB = INCR;
    int __jjV_nB_nB = DECR;
    int __jjV_lB = PLUS;
    int __jjV_nB = MINUS;
    int __jjV_kB = STAR;
    int __jjV_pB = SLASH;
    int __jjV_gB = BIT_AND;
    int __jjV_2D = BIT_OR;
    int __jjV_4C = XOR;
    int __jjV_fB = REM;
    int __jjV_2B_2B = LSHIFT;
    int __jjV_4B_4B = RSIGNEDSHIFT;
    int __jjV_4B_4B_4B = RUNSIGNEDSHIFT;
    int __jjV_lB_3B = PLUSASSIGN;
    int __jjV_nB_3B = MINUSASSIGN;
    int __jjV_kB_3B = STARASSIGN;
    int __jjV_pB_3B = SLASHASSIGN;
    int __jjV_gB_3B = ANDASSIGN;
    int __jjV_2D_3B = ORASSIGN;
    int __jjV_4C_3B = XORASSIGN;
    int __jjV_fB_3B = REMASSIGN;
    int __jjV_2B_2B_3B = LSHIFTASSIGN;
    int __jjV_4B_4B_3B = RSIGNEDSHIFTASSIGN;
    int __jjV_4B_4B_4B_3B = RUNSIGNEDSHIFTASSIGN;
  
    int DEFAULT = 0;
    int IN_SINGLE_LINE_COMMENT = 1;
    int IN_FORMAL_COMMENT = 2;
    int IN_MULTI_LINE_COMMENT = 3;
  
    String[] tokenImage = {
      "<EOF>",
      "\" \"",
      "\"\\t\"",
      "\"\\n\"",
      "\"\\r\"",
      "\"\\f\"",
      "\"//\"",
      "<token of kind 7>",
      "\"/*\"",
      "<SINGLE_LINE_COMMENT>",
      "\"*/\"",
      "\"*/\"",
      "<token of kind 12>",
      "\"abstract\"",
      "\"boolean\"",
      "\"break\"",
      "\"byte\"",
      "\"case\"",
      "\"catch\"",
      "\"char\"",
      "\"class\"",
      "\"const\"",
      "\"continue\"",
      "\"default\"",
      "\"do\"",
      "\"double\"",
      "\"else\"",
      "\"extends\"",
      "\"false\"",
      "\"final\"",
      "\"finally\"",
      "\"float\"",
      "\"for\"",
      "\"goto\"",
      "\"if\"",
      "\"implements\"",
      "\"import\"",
      "\"instanceof\"",
      "\"int\"",
      "\"interface\"",
      "\"long\"",
      "\"native\"",
      "\"new\"",
      "\"null\"",
      "\"package\"",
      "\"private\"",
      "\"protected\"",
      "\"public\"",
      "\"return\"",
      "\"short\"",
      "\"static\"",
      "\"super\"",
      "\"switch\"",
      "\"synchronized\"",
      "\"this\"",
      "\"throw\"",
      "\"throws\"",
      "\"transient\"",
      "\"true\"",
      "\"try\"",
      "\"void\"",
      "\"volatile\"",
      "\"while\"",
      "<INTEGER_LITERAL>",
      "<DECIMAL_LITERAL>",
      "<HEX_LITERAL>",
      "<OCTAL_LITERAL>",
      "<FLOATING_POINT_LITERAL>",
      "<EXPONENT>",
      "<CHARACTER_LITERAL>",
      "<STRING_LITERAL>",
      "<IDENTIFIER>",
      "<LETTER>",
      "<DIGIT>",
      "\"(\"",
      "\")\"",
      "\"{\"",
      "\"}\"",
      "\"[\"",
      "\"]\"",
      "\";\"",
      "\",\"",
      "\".\"",
      "\"=\"",
      "\">\"",
      "\"<\"",
      "\"!\"",
      "\"~\"",
      "\"?\"",
      "\":\"",
      "\"==\"",
      "\"<=\"",
      "\">=\"",
      "\"!=\"",
      "\"||\"",
      "\"&&\"",
      "\"++\"",
      "\"--\"",
      "\"+\"",
      "\"-\"",
      "\"*\"",
      "\"/\"",
      "\"&\"",
      "\"|\"",
      "\"^\"",
      "\"%\"",
      "\"<<\"",
      "\">>\"",
      "\">>>\"",
      "\"+=\"",
      "\"-=\"",
      "\"*=\"",
      "\"/=\"",
      "\"&=\"",
      "\"|=\"",
      "\"^=\"",
      "\"%=\"",
      "\"<<=\"",
      "\">>=\"",
      "\">>>=\"",
    };
  
  }
  public static final int EOF = 0;
  public static final int SINGLE_LINE_COMMENT = 9;
  public static final int FORMAL_COMMENT = 10;
  public static final int MULTI_LINE_COMMENT = 11;
  public static final int ABSTRACT = 13;
  public static final int BOOLEAN = 14;
  public static final int BREAK = 15;
  public static final int BYTE = 16;
  public static final int CASE = 17;
  public static final int CATCH = 18;
  public static final int CHAR = 19;
  public static final int CLASS = 20;
  public static final int CONST = 21;
  public static final int CONTINUE = 22;
  public static final int _DEFAULT = 23;
  public static final int DO = 24;
  public static final int DOUBLE = 25;
  public static final int ELSE = 26;
  public static final int EXTENDS = 27;
  public static final int FALSE = 28;
  public static final int FINAL = 29;
  public static final int FINALLY = 30;
  public static final int FLOAT = 31;
  public static final int FOR = 32;
  public static final int GOTO = 33;
  public static final int IF = 34;
  public static final int IMPLEMENTS = 35;
  public static final int IMPORT = 36;
  public static final int INSTANCEOF = 37;
  public static final int INT = 38;
  public static final int INTERFACE = 39;
  public static final int LONG = 40;
  public static final int NATIVE = 41;
  public static final int NEW = 42;
  public static final int NULL = 43;
  public static final int PACKAGE = 44;
  public static final int PRIVATE = 45;
  public static final int PROTECTED = 46;
  public static final int PUBLIC = 47;
  public static final int RETURN = 48;
  public static final int SHORT = 49;
  public static final int STATIC = 50;
  public static final int SUPER = 51;
  public static final int SWITCH = 52;
  public static final int SYNCHRONIZED = 53;
  public static final int THIS = 54;
  public static final int THROW = 55;
  public static final int THROWS = 56;
  public static final int TRANSIENT = 57;
  public static final int TRUE = 58;
  public static final int TRY = 59;
  public static final int VOID = 60;
  public static final int VOLATILE = 61;
  public static final int WHILE = 62;
  public static final int INTEGER_LITERAL = 63;
  public static final int DECIMAL_LITERAL = 64;
  public static final int HEX_LITERAL = 65;
  public static final int OCTAL_LITERAL = 66;
  public static final int FLOATING_POINT_LITERAL = 67;
  public static final int EXPONENT = 68;
  public static final int CHARACTER_LITERAL = 69;
  public static final int STRING_LITERAL = 70;
  public static final int IDENTIFIER = 71;
  public static final int LETTER = 72;
  public static final int DIGIT = 73;
  public static final int LPAREN = 74;
  public static final int RPAREN = 75;
  public static final int LBRACE = 76;
  public static final int RBRACE = 77;
  public static final int LBRACKET = 78;
  public static final int RBRACKET = 79;
  public static final int SEMICOLON = 80;
  public static final int COMMA = 81;
  public static final int DOT = 82;
  public static final int ASSIGN = 83;
  public static final int GT = 84;
  public static final int LT = 85;
  public static final int BANG = 86;
  public static final int TILDE = 87;
  public static final int HOOK = 88;
  public static final int COLON = 89;
  public static final int EQ = 90;
  public static final int LE = 91;
  public static final int GE = 92;
  public static final int NE = 93;
  public static final int SC_OR = 94;
  public static final int SC_AND = 95;
  public static final int INCR = 96;
  public static final int DECR = 97;
  public static final int PLUS = 98;
  public static final int MINUS = 99;
  public static final int STAR = 100;
  public static final int SLASH = 101;
  public static final int BIT_AND = 102;
  public static final int BIT_OR = 103;
  public static final int XOR = 104;
  public static final int REM = 105;
  public static final int LSHIFT = 106;
  public static final int RSIGNEDSHIFT = 107;
  public static final int RUNSIGNEDSHIFT = 108;
  public static final int PLUSASSIGN = 109;
  public static final int MINUSASSIGN = 110;
  public static final int STARASSIGN = 111;
  public static final int SLASHASSIGN = 112;
  public static final int ANDASSIGN = 113;
  public static final int ORASSIGN = 114;
  public static final int XORASSIGN = 115;
  public static final int REMASSIGN = 116;
  public static final int LSHIFTASSIGN = 117;
  public static final int RSIGNEDSHIFTASSIGN = 118;
  public static final int RUNSIGNEDSHIFTASSIGN = 119;
  public static final int __jjVabstract = ABSTRACT;
  public static final int __jjVboolean = BOOLEAN;
  public static final int __jjVbreak = BREAK;
  public static final int __jjVbyte = BYTE;
  public static final int __jjVcase = CASE;
  public static final int __jjVcatch = CATCH;
  public static final int __jjVchar = CHAR;
  public static final int __jjVclass = CLASS;
  public static final int __jjVconst = CONST;
  public static final int __jjVcontinue = CONTINUE;
  public static final int __jjVdefault = _DEFAULT;
  public static final int __jjVdo = DO;
  public static final int __jjVdouble = DOUBLE;
  public static final int __jjVelse = ELSE;
  public static final int __jjVextends = EXTENDS;
  public static final int __jjVfalse = FALSE;
  public static final int __jjVfinal = FINAL;
  public static final int __jjVfinally = FINALLY;
  public static final int __jjVfloat = FLOAT;
  public static final int __jjVfor = FOR;
  public static final int __jjVgoto = GOTO;
  public static final int __jjVif = IF;
  public static final int __jjVimplements = IMPLEMENTS;
  public static final int __jjVimport = IMPORT;
  public static final int __jjVinstanceof = INSTANCEOF;
  public static final int __jjVint = INT;
  public static final int __jjVinterface = INTERFACE;
  public static final int __jjVlong = LONG;
  public static final int __jjVnative = NATIVE;
  public static final int __jjVnew = NEW;
  public static final int __jjVnull = NULL;
  public static final int __jjVpackage = PACKAGE;
  public static final int __jjVprivate = PRIVATE;
  public static final int __jjVprotected = PROTECTED;
  public static final int __jjVpublic = PUBLIC;
  public static final int __jjVreturn = RETURN;
  public static final int __jjVshort = SHORT;
  public static final int __jjVstatic = STATIC;
  public static final int __jjVsuper = SUPER;
  public static final int __jjVswitch = SWITCH;
  public static final int __jjVsynchronized = SYNCHRONIZED;
  public static final int __jjVthis = THIS;
  public static final int __jjVthrow = THROW;
  public static final int __jjVthrows = THROWS;
  public static final int __jjVtransient = TRANSIENT;
  public static final int __jjVtrue = TRUE;
  public static final int __jjVtry = TRY;
  public static final int __jjVvoid = VOID;
  public static final int __jjVvolatile = VOLATILE;
  public static final int __jjVwhile = WHILE;
  public static final int __jjV_iB = LPAREN;
  public static final int __jjV_jB = RPAREN;
  public static final int __jjV_1D = LBRACE;
  public static final int __jjV_3D = RBRACE;
  public static final int __jjV_1C = LBRACKET;
  public static final int __jjV_3C = RBRACKET;
  public static final int __jjV_1B = SEMICOLON;
  public static final int __jjV_mB = COMMA;
  public static final int __jjV_oB = DOT;
  public static final int __jjV_3B = ASSIGN;
  public static final int __jjV_4B = GT;
  public static final int __jjV_2B = LT;
  public static final int __jjV_bB = BANG;
  public static final int __jjV_4D = TILDE;
  public static final int __jjV_5B = HOOK;
  public static final int __jjV_0B = COLON;
  public static final int __jjV_3B_3B = EQ;
  public static final int __jjV_2B_3B = LE;
  public static final int __jjV_4B_3B = GE;
  public static final int __jjV_bB_3B = NE;
  public static final int __jjV_2D_2D = SC_OR;
  public static final int __jjV_gB_gB = SC_AND;
  public static final int __jjV_lB_lB = INCR;
  public static final int __jjV_nB_nB = DECR;
  public static final int __jjV_lB = PLUS;
  public static final int __jjV_nB = MINUS;
  public static final int __jjV_kB = STAR;
  public static final int __jjV_pB = SLASH;
  public static final int __jjV_gB = BIT_AND;
  public static final int __jjV_2D = BIT_OR;
  public static final int __jjV_4C = XOR;
  public static final int __jjV_fB = REM;
  public static final int __jjV_2B_2B = LSHIFT;
  public static final int __jjV_4B_4B = RSIGNEDSHIFT;
  public static final int __jjV_4B_4B_4B = RUNSIGNEDSHIFT;
  public static final int __jjV_lB_3B = PLUSASSIGN;
  public static final int __jjV_nB_3B = MINUSASSIGN;
  public static final int __jjV_kB_3B = STARASSIGN;
  public static final int __jjV_pB_3B = SLASHASSIGN;
  public static final int __jjV_gB_3B = ANDASSIGN;
  public static final int __jjV_2D_3B = ORASSIGN;
  public static final int __jjV_4C_3B = XORASSIGN;
  public static final int __jjV_fB_3B = REMASSIGN;
  public static final int __jjV_2B_2B_3B = LSHIFTASSIGN;
  public static final int __jjV_4B_4B_3B = RSIGNEDSHIFTASSIGN;
  public static final int __jjV_4B_4B_4B_3B = RUNSIGNEDSHIFTASSIGN;
  public static final int DEFAULT = 0;
  public static final int IN_SINGLE_LINE_COMMENT = 1;
  public static final int IN_FORMAL_COMMENT = 2;
  public static final int IN_MULTI_LINE_COMMENT = 3;
  public static final String[] tokenImage = ScannerConstants.tokenImage;

  public class __jjScanner extends com.metamata.parse.Scanner {

    private com.metamata.parse.MParseReader __jjcs;
    private __jjJavaParserTokenManager __jjtm;

    public __jjScanner(java.io.Reader input) {
      __jjcs = input instanceof com.metamata.parse.MParseReader ? (com.metamata.parse.MParseReader)input : new com.metamata.parse.SimpleReader(input, 1, 1);
      __jjtm = new __jjJavaParserTokenManager(__jjcs);
    }

    public com.metamata.parse.Token getNextToken() {
      return __jjtm.getNextToken();
    }

    public void switchTo(int state) {
      __jjtm.SwitchTo(state);
    }

    public int tokenCount() {
      return ScannerConstants.tokenImage.length;
    }

    public java.lang.String tokenImage(int kind) {
      return ScannerConstants.tokenImage[kind];
    }

    public char readChar() throws java.io.IOException {
      return __jjcs.readChar();
    }

    public void backup(int amount) {
      __jjcs.backup(amount);
    }

    public int getBeginLine() {
      return __jjcs.getBeginLine();
    }

    public int getBeginColumn() {
      return __jjcs.getBeginColumn();
    }

    public int getEndLine() {
      return __jjcs.getEndLine();
    }

    public int getEndColumn() {
      return __jjcs.getEndColumn();
    }

    /**
     * Lexical error occured.
     */
   final int LEXICAL_ERROR = 0;

    /**
     * An attempt wass made to create a second instance of a static token manager.
     */
    final int STATIC_LEXER_ERROR = 1;

    /**
     * Tried to change to an invalid lexical state.
     */
    final int INVALID_LEXICAL_STATE = 2;

    /**
     * Detected (and bailed out of) an infinite loop in the token manager.
     */
    final int LOOP_DETECTED = 3;

    class TokenMgrError extends com.metamata.parse.ScanError
    {
       /*
        * Ordinals for various reasons why an Error of this type can be thrown
        * have been moved out of the class above.
        */

       /**
        * Indicates the reason why the exception is thrown. It will have
        * one of the above 4 values.
        */
       int errorCode;

       /**
        * You can also modify the body of this method to customize your error messages.
        * For example, cases like LOOP_DETECTED and INVALID_LEXICAL_STATE are not
        * of end-users concern, so you can return something like : 
        *
        *     "Internal Error : Please file a bug report .... "
        *
        * from this method for such cases in the release version of your parser.
        */
       public String getMessage() {
          return super.getMessage();
       }

       /*
        * Constructors of various flavors follow.
        */

       public TokenMgrError() {
       }

       public TokenMgrError(String message, int reason) {
          super(message);
          errorCode = reason;
       }

       public TokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar, int reason) {
         this(
           ("Lexical error at line " +
           errorLine + ", column " +
           errorColumn + ".  Encountered: " +
           (EOFSeen ? "<EOF> " : ("\"" + 
           com.metamata.parse.Utils.addEscapes(String.valueOf(curChar)) +
           "\"") + " (" + (int)curChar + "), ") +
           "after : \"" +
           com.metamata.parse.Utils.addEscapes(errorAfter) + "\"")
           , reason);
       }

    }

     class __jjJavaParserTokenManager 
    {
    private final int jjStopAtPos(int pos, int kind)
    {
       jjmatchedKind = kind;
       jjmatchedPos = pos;
       return pos + 1;
    }
    private final int jjMoveStringLiteralDfa0_3()
    {
       switch(curChar)
       {
          case 42:
             return jjMoveStringLiteralDfa1_3(0x800L);
          default :
             return 1;
       }
    }
    private final int jjMoveStringLiteralDfa1_3(long active0)
    {
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          return 1;
       }
       switch(curChar)
       {
          case 47:
             if ((active0 & 0x800L) != 0L)
                return jjStopAtPos(1, 11);
             break;
          default :
             return 2;
       }
       return 2;
    }
    private final int jjMoveStringLiteralDfa0_2()
    {
       switch(curChar)
       {
          case 42:
             return jjMoveStringLiteralDfa1_2(0x400L);
          default :
             return 1;
       }
    }
    private final int jjMoveStringLiteralDfa1_2(long active0)
    {
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          return 1;
       }
       switch(curChar)
       {
          case 47:
             if ((active0 & 0x400L) != 0L)
                return jjStopAtPos(1, 10);
             break;
          default :
             return 2;
       }
       return 2;
    }
    private final int jjMoveStringLiteralDfa0_1()
    {
       return jjMoveNfa_1(0, 0);
    }
    private final void jjCheckNAdd(int state)
    {
       if (jjrounds[state] != jjround)
       {
          jjstateSet[jjnewStateCnt++] = state;
          jjrounds[state] = jjround;
       }
    }
    private final void jjAddStates(int start, int end)
    {
       do {
          jjstateSet[jjnewStateCnt++] = jjnextStates[start];
       } while (start++ != end);
    }
    private final void jjCheckNAddTwoStates(int state1, int state2)
    {
       jjCheckNAdd(state1);
       jjCheckNAdd(state2);
    }
    private final void jjCheckNAddStates(int start, int end)
    {
       do {
          jjCheckNAdd(jjnextStates[start]);
       } while (start++ != end);
    }
    private final void jjCheckNAddStates(int start)
    {
       jjCheckNAdd(jjnextStates[start]);
       jjCheckNAdd(jjnextStates[start + 1]);
    }
    private final int jjMoveNfa_1(int startState, int curPos)
    {
       int[] nextStates;
       int startsAt = 0;
       jjnewStateCnt = 3;
       int i = 1;
       jjstateSet[0] = startState;
       int j, kind = 0x7fffffff;
       for (;;)
       {
          if (++jjround == 0x7fffffff)
             ReInitRounds();
          if (curChar < 64)
          {
             long l = 1L << curChar;
             MatchLoop: do
             {
                switch(jjstateSet[--i])
                {
                   case 0:
                      if ((0x2400L & l) != 0L)
                      {
                         if (kind > 9)
                            kind = 9;
                      }
                      if (curChar == 13)
                         jjstateSet[jjnewStateCnt++] = 1;
                      break;
                   case 1:
                      if (curChar == 10 && kind > 9)
                         kind = 9;
                      break;
                   case 2:
                      if (curChar == 13)
                         jjstateSet[jjnewStateCnt++] = 1;
                      break;
                   default : break;
                }
             } while(i != startsAt);
          }
          else if (curChar < 128)
          {
             long l = 1L << (curChar & 077);
             MatchLoop: do
             {
                switch(jjstateSet[--i])
                {
                   default : break;
                }
             } while(i != startsAt);
          }
          else
          {
             int i2 = (curChar & 0xff) >> 6;
             long l2 = 1L << (curChar & 077);
             MatchLoop: do
             {
                switch(jjstateSet[--i])
                {
                   default : break;
                }
             } while(i != startsAt);
          }
          if (kind != 0x7fffffff)
          {
             jjmatchedKind = kind;
             jjmatchedPos = curPos;
             kind = 0x7fffffff;
          }
          ++curPos;
          if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
             return curPos;
          try { curChar = input_stream.readChar(); }
          catch(java.io.IOException e) { return curPos; }
       }
    }
    private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1)
    {
       switch (pos)
       {
          case 0:
             if ((active0 & 0x140L) != 0L || (active1 & 0x1002000000000L) != 0L)
                return 2;
             if ((active0 & 0x7fffffffffffe000L) != 0L)
             {
                jjmatchedKind = 71;
                return 32;
             }
             if ((active1 & 0x40000L) != 0L)
                return 8;
             return -1;
          case 1:
             if ((active0 & 0x100L) != 0L)
                return 0;
             if ((active0 & 0x403000000L) != 0L)
                return 32;
             if ((active0 & 0x7ffffffbfcffe000L) != 0L)
             {
                if (jjmatchedPos != 1)
                {
                   jjmatchedKind = 71;
                   jjmatchedPos = 1;
                }
                return 32;
             }
             return -1;
          case 2:
             if ((active0 & 0x77fffb3afeffe000L) != 0L)
             {
                if (jjmatchedPos != 2)
                {
                   jjmatchedKind = 71;
                   jjmatchedPos = 2;
                }
                return 32;
             }
             if ((active0 & 0x80004c100000000L) != 0L)
                return 32;
             return -1;
          case 3:
             if ((active0 & 0x14400902040b0000L) != 0L)
                return 32;
             if ((active0 & 0x63bff2b8faf4e000L) != 0L)
             {
                jjmatchedKind = 71;
                jjmatchedPos = 3;
                return 32;
             }
             return -1;
          case 4:
             if ((active0 & 0x2235f2b80ac06000L) != 0L)
             {
                if (jjmatchedPos != 4)
                {
                   jjmatchedKind = 71;
                   jjmatchedPos = 4;
                }
                return 32;
             }
             if ((active0 & 0x418a0000f0348000L) != 0L)
                return 32;
             return -1;
          case 5:
             if ((active0 & 0x222070a848c06000L) != 0L)
             {
                jjmatchedKind = 71;
                jjmatchedPos = 5;
                return 32;
             }
             if ((active0 & 0x115821002000000L) != 0L)
                return 32;
             return -1;
          case 6:
             if ((active0 & 0x300048804000L) != 0L)
                return 32;
             if ((active0 & 0x222040a800402000L) != 0L)
             {
                jjmatchedKind = 71;
                jjmatchedPos = 6;
                return 32;
             }
             return -1;
          case 7:
             if ((active0 & 0x2000000000402000L) != 0L)
                return 32;
             if ((active0 & 0x22040a800000000L) != 0L)
             {
                jjmatchedKind = 71;
                jjmatchedPos = 7;
                return 32;
             }
             return -1;
          case 8:
             if ((active0 & 0x20002800000000L) != 0L)
             {
                jjmatchedKind = 71;
                jjmatchedPos = 8;
                return 32;
             }
             if ((active0 & 0x200408000000000L) != 0L)
                return 32;
             return -1;
          case 9:
             if ((active0 & 0x2800000000L) != 0L)
                return 32;
             if ((active0 & 0x20000000000000L) != 0L)
             {
                jjmatchedKind = 71;
                jjmatchedPos = 9;
                return 32;
             }
             return -1;
          case 10:
             if ((active0 & 0x20000000000000L) != 0L)
             {
                jjmatchedKind = 71;
                jjmatchedPos = 10;
                return 32;
             }
             return -1;
          default :
             return -1;
       }
    }
    private final int jjStartNfa_0(int pos, long active0, long active1)
    {
       return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
    }
    private final int jjStartNfaWithStates_0(int pos, int kind, int state)
    {
       jjmatchedKind = kind;
       jjmatchedPos = pos;
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) { return pos + 1; }
       return jjMoveNfa_0(state, pos + 1);
    }
    private final int jjMoveStringLiteralDfa0_0()
    {
       switch(curChar)
       {
          case 33:
             jjmatchedKind = 86;
             return jjMoveStringLiteralDfa1_0(0x0L, 0x20000000L);
          case 37:
             jjmatchedKind = 105;
             return jjMoveStringLiteralDfa1_0(0x0L, 0x10000000000000L);
          case 38:
             jjmatchedKind = 102;
             return jjMoveStringLiteralDfa1_0(0x0L, 0x2000080000000L);
          case 40:
             return jjStopAtPos(0, 74);
          case 41:
             return jjStopAtPos(0, 75);
          case 42:
             jjmatchedKind = 100;
             return jjMoveStringLiteralDfa1_0(0x0L, 0x800000000000L);
          case 43:
             jjmatchedKind = 98;
             return jjMoveStringLiteralDfa1_0(0x0L, 0x200100000000L);
          case 44:
             return jjStopAtPos(0, 81);
          case 45:
             jjmatchedKind = 99;
             return jjMoveStringLiteralDfa1_0(0x0L, 0x400200000000L);
          case 46:
             return jjStartNfaWithStates_0(0, 82, 8);
          case 47:
             jjmatchedKind = 101;
             return jjMoveStringLiteralDfa1_0(0x140L, 0x1000000000000L);
          case 58:
             return jjStopAtPos(0, 89);
          case 59:
             return jjStopAtPos(0, 80);
          case 60:
             jjmatchedKind = 85;
             return jjMoveStringLiteralDfa1_0(0x0L, 0x20040008000000L);
          case 61:
             jjmatchedKind = 83;
             return jjMoveStringLiteralDfa1_0(0x0L, 0x4000000L);
          case 62:
             jjmatchedKind = 84;
             return jjMoveStringLiteralDfa1_0(0x0L, 0xc0180010000000L);
          case 63:
             return jjStopAtPos(0, 88);
          case 91:
             return jjStopAtPos(0, 78);
          case 93:
             return jjStopAtPos(0, 79);
          case 94:
             jjmatchedKind = 104;
             return jjMoveStringLiteralDfa1_0(0x0L, 0x8000000000000L);
          case 97:
             return jjMoveStringLiteralDfa1_0(0x2000L, 0x0L);
          case 98:
             return jjMoveStringLiteralDfa1_0(0x1c000L, 0x0L);
          case 99:
             return jjMoveStringLiteralDfa1_0(0x7e0000L, 0x0L);
          case 100:
             return jjMoveStringLiteralDfa1_0(0x3800000L, 0x0L);
          case 101:
             return jjMoveStringLiteralDfa1_0(0xc000000L, 0x0L);
          case 102:
             return jjMoveStringLiteralDfa1_0(0x1f0000000L, 0x0L);
          case 103:
             return jjMoveStringLiteralDfa1_0(0x200000000L, 0x0L);
          case 105:
             return jjMoveStringLiteralDfa1_0(0xfc00000000L, 0x0L);
          case 108:
             return jjMoveStringLiteralDfa1_0(0x10000000000L, 0x0L);
          case 110:
             return jjMoveStringLiteralDfa1_0(0xe0000000000L, 0x0L);
          case 112:
             return jjMoveStringLiteralDfa1_0(0xf00000000000L, 0x0L);
          case 114:
             return jjMoveStringLiteralDfa1_0(0x1000000000000L, 0x0L);
          case 115:
             return jjMoveStringLiteralDfa1_0(0x3e000000000000L, 0x0L);
          case 116:
             return jjMoveStringLiteralDfa1_0(0xfc0000000000000L, 0x0L);
          case 118:
             return jjMoveStringLiteralDfa1_0(0x3000000000000000L, 0x0L);
          case 119:
             return jjMoveStringLiteralDfa1_0(0x4000000000000000L, 0x0L);
          case 123:
             return jjStopAtPos(0, 76);
          case 124:
             jjmatchedKind = 103;
             return jjMoveStringLiteralDfa1_0(0x0L, 0x4000040000000L);
          case 125:
             return jjStopAtPos(0, 77);
          case 126:
             return jjStopAtPos(0, 87);
          default :
             return jjMoveNfa_0(3, 0);
       }
    }
    private final int jjMoveStringLiteralDfa1_0(long active0, long active1)
    {
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(0, active0, active1);
          return 1;
       }
       switch(curChar)
       {
          case 38:
             if ((active1 & 0x80000000L) != 0L)
                return jjStopAtPos(1, 95);
             break;
          case 42:
             if ((active0 & 0x100L) != 0L)
                return jjStartNfaWithStates_0(1, 8, 0);
             break;
          case 43:
             if ((active1 & 0x100000000L) != 0L)
                return jjStopAtPos(1, 96);
             break;
          case 45:
             if ((active1 & 0x200000000L) != 0L)
                return jjStopAtPos(1, 97);
             break;
          case 47:
             if ((active0 & 0x40L) != 0L)
                return jjStopAtPos(1, 6);
             break;
          case 60:
             if ((active1 & 0x40000000000L) != 0L)
             {
                jjmatchedKind = 106;
                jjmatchedPos = 1;
             }
             return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x20000000000000L);
          case 61:
             if ((active1 & 0x4000000L) != 0L)
                return jjStopAtPos(1, 90);
             else if ((active1 & 0x8000000L) != 0L)
                return jjStopAtPos(1, 91);
             else if ((active1 & 0x10000000L) != 0L)
                return jjStopAtPos(1, 92);
             else if ((active1 & 0x20000000L) != 0L)
                return jjStopAtPos(1, 93);
             else if ((active1 & 0x200000000000L) != 0L)
                return jjStopAtPos(1, 109);
             else if ((active1 & 0x400000000000L) != 0L)
                return jjStopAtPos(1, 110);
             else if ((active1 & 0x800000000000L) != 0L)
                return jjStopAtPos(1, 111);
             else if ((active1 & 0x1000000000000L) != 0L)
                return jjStopAtPos(1, 112);
             else if ((active1 & 0x2000000000000L) != 0L)
                return jjStopAtPos(1, 113);
             else if ((active1 & 0x4000000000000L) != 0L)
                return jjStopAtPos(1, 114);
             else if ((active1 & 0x8000000000000L) != 0L)
                return jjStopAtPos(1, 115);
             else if ((active1 & 0x10000000000000L) != 0L)
                return jjStopAtPos(1, 116);
             break;
          case 62:
             if ((active1 & 0x80000000000L) != 0L)
             {
                jjmatchedKind = 107;
                jjmatchedPos = 1;
             }
             return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0xc0100000000000L);
          case 97:
             return jjMoveStringLiteralDfa2_0(active0, 0x120010060000L, active1, 0L);
          case 98:
             return jjMoveStringLiteralDfa2_0(active0, 0x2000L, active1, 0L);
          case 101:
             return jjMoveStringLiteralDfa2_0(active0, 0x1040000800000L, active1, 0L);
          case 102:
             if ((active0 & 0x400000000L) != 0L)
                return jjStartNfaWithStates_0(1, 34, 32);
             break;
          case 104:
             return jjMoveStringLiteralDfa2_0(active0, 0x41c2000000080000L, active1, 0L);
          case 105:
             return jjMoveStringLiteralDfa2_0(active0, 0x60000000L, active1, 0L);
          case 108:
             return jjMoveStringLiteralDfa2_0(active0, 0x84100000L, active1, 0L);
          case 109:
             return jjMoveStringLiteralDfa2_0(active0, 0x1800000000L, active1, 0L);
          case 110:
             return jjMoveStringLiteralDfa2_0(active0, 0xe000000000L, active1, 0L);
          case 111:
             if ((active0 & 0x1000000L) != 0L)
             {
                jjmatchedKind = 24;
                jjmatchedPos = 1;
             }
             return jjMoveStringLiteralDfa2_0(active0, 0x3000010302604000L, active1, 0L);
          case 114:
             return jjMoveStringLiteralDfa2_0(active0, 0xe00600000008000L, active1, 0L);
          case 116:
             return jjMoveStringLiteralDfa2_0(active0, 0x4000000000000L, active1, 0L);
          case 117:
             return jjMoveStringLiteralDfa2_0(active0, 0x8880000000000L, active1, 0L);
          case 119:
             return jjMoveStringLiteralDfa2_0(active0, 0x10000000000000L, active1, 0L);
          case 120:
             return jjMoveStringLiteralDfa2_0(active0, 0x8000000L, active1, 0L);
          case 121:
             return jjMoveStringLiteralDfa2_0(active0, 0x20000000010000L, active1, 0L);
          case 124:
             if ((active1 & 0x40000000L) != 0L)
                return jjStopAtPos(1, 94);
             break;
          default :
             break;
       }
       return jjStartNfa_0(0, active0, active1);
    }
    private final int jjMoveStringLiteralDfa2_0(long old0, long active0, long old1, long active1)
    {
       if (((active0 &= old0) | (active1 &= old1)) == 0L)
          return jjStartNfa_0(0, old0, old1); 
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(1, active0, active1);
          return 2;
       }
       switch(curChar)
       {
          case 61:
             if ((active1 & 0x20000000000000L) != 0L)
                return jjStopAtPos(2, 117);
             else if ((active1 & 0x40000000000000L) != 0L)
                return jjStopAtPos(2, 118);
             break;
          case 62:
             if ((active1 & 0x100000000000L) != 0L)
             {
                jjmatchedKind = 108;
                jjmatchedPos = 2;
             }
             return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x80000000000000L);
          case 97:
             return jjMoveStringLiteralDfa3_0(active0, 0x204000000180000L, active1, 0L);
          case 98:
             return jjMoveStringLiteralDfa3_0(active0, 0x800000000000L, active1, 0L);
          case 99:
             return jjMoveStringLiteralDfa3_0(active0, 0x100000000000L, active1, 0L);
          case 101:
             return jjMoveStringLiteralDfa3_0(active0, 0x8000L, active1, 0L);
          case 102:
             return jjMoveStringLiteralDfa3_0(active0, 0x800000L, active1, 0L);
          case 105:
             return jjMoveStringLiteralDfa3_0(active0, 0x5050200000000000L, active1, 0L);
          case 108:
             return jjMoveStringLiteralDfa3_0(active0, 0x2000080010000000L, active1, 0L);
          case 110:
             return jjMoveStringLiteralDfa3_0(active0, 0x20010060600000L, active1, 0L);
          case 111:
             return jjMoveStringLiteralDfa3_0(active0, 0x2400080004000L, active1, 0L);
          case 112:
             return jjMoveStringLiteralDfa3_0(active0, 0x8001800000000L, active1, 0L);
          case 114:
             if ((active0 & 0x100000000L) != 0L)
                return jjStartNfaWithStates_0(2, 32, 32);
             return jjMoveStringLiteralDfa3_0(active0, 0x180000000000000L, active1, 0L);
          case 115:
             return jjMoveStringLiteralDfa3_0(active0, 0x2004022000L, active1, 0L);
          case 116:
             if ((active0 & 0x4000000000L) != 0L)
             {
                jjmatchedKind = 38;
                jjmatchedPos = 2;
             }
             return jjMoveStringLiteralDfa3_0(active0, 0x1028208050000L, active1, 0L);
          case 117:
             return jjMoveStringLiteralDfa3_0(active0, 0x400000002000000L, active1, 0L);
          case 119:
             if ((active0 & 0x40000000000L) != 0L)
                return jjStartNfaWithStates_0(2, 42, 32);
             break;
          case 121:
             if ((active0 & 0x800000000000000L) != 0L)
                return jjStartNfaWithStates_0(2, 59, 32);
             break;
          default :
             break;
       }
       return jjStartNfa_0(1, active0, active1);
    }
    private final int jjMoveStringLiteralDfa3_0(long old0, long active0, long old1, long active1)
    {
       if (((active0 &= old0) | (active1 &= old1)) == 0L)
          return jjStartNfa_0(1, old0, old1); 
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(2, active0, active1);
          return 3;
       }
       switch(curChar)
       {
          case 61:
             if ((active1 & 0x80000000000000L) != 0L)
                return jjStopAtPos(3, 119);
             break;
          case 97:
             return jjMoveStringLiteralDfa4_0(active0, 0x20000000e0808000L, active1, 0L);
          case 98:
             return jjMoveStringLiteralDfa4_0(active0, 0x2000000L, active1, 0L);
          case 99:
             return jjMoveStringLiteralDfa4_0(active0, 0x20000000040000L, active1, 0L);
          case 100:
             if ((active0 & 0x1000000000000000L) != 0L)
                return jjStartNfaWithStates_0(3, 60, 32);
             break;
          case 101:
             if ((active0 & 0x10000L) != 0L)
                return jjStartNfaWithStates_0(3, 16, 32);
             else if ((active0 & 0x20000L) != 0L)
                return jjStartNfaWithStates_0(3, 17, 32);
             else if ((active0 & 0x4000000L) != 0L)
                return jjStartNfaWithStates_0(3, 26, 32);
             else if ((active0 & 0x400000000000000L) != 0L)
                return jjStartNfaWithStates_0(3, 58, 32);
             return jjMoveStringLiteralDfa4_0(active0, 0x8008008000000L, active1, 0L);
          case 103:
             if ((active0 & 0x10000000000L) != 0L)
                return jjStartNfaWithStates_0(3, 40, 32);
             break;
          case 105:
             return jjMoveStringLiteralDfa4_0(active0, 0x20000000000L, active1, 0L);
          case 107:
             return jjMoveStringLiteralDfa4_0(active0, 0x100000000000L, active1, 0L);
          case 108:
             if ((active0 & 0x80000000000L) != 0L)
                return jjStartNfaWithStates_0(3, 43, 32);
             return jjMoveStringLiteralDfa4_0(active0, 0x4000800800004000L, active1, 0L);
          case 110:
             return jjMoveStringLiteralDfa4_0(active0, 0x200000000000000L, active1, 0L);
          case 111:
             if ((active0 & 0x200000000L) != 0L)
                return jjStartNfaWithStates_0(3, 33, 32);
             return jjMoveStringLiteralDfa4_0(active0, 0x180001000000000L, active1, 0L);
          case 114:
             if ((active0 & 0x80000L) != 0L)
                return jjStartNfaWithStates_0(3, 19, 32);
             return jjMoveStringLiteralDfa4_0(active0, 0x2000000000000L, active1, 0L);
          case 115:
             if ((active0 & 0x40000000000000L) != 0L)
                return jjStartNfaWithStates_0(3, 54, 32);
             return jjMoveStringLiteralDfa4_0(active0, 0x10300000L, active1, 0L);
          case 116:
             return jjMoveStringLiteralDfa4_0(active0, 0x14402000402000L, active1, 0L);
          case 117:
             return jjMoveStringLiteralDfa4_0(active0, 0x1000000000000L, active1, 0L);
          case 118:
             return jjMoveStringLiteralDfa4_0(active0, 0x200000000000L, active1, 0L);
          default :
             break;
       }
       return jjStartNfa_0(2, active0, active1);
    }
    private final int jjMoveStringLiteralDfa4_0(long old0, long active0, long old1, long active1)
    {
       if (((active0 &= old0) | (active1 &= old1)) == 0L)
          return jjStartNfa_0(2, old0, old1); 
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(3, active0, 0L);
          return 4;
       }
       switch(curChar)
       {
          case 97:
             return jjMoveStringLiteralDfa5_0(active0, 0x302000000000L);
          case 99:
             return jjMoveStringLiteralDfa5_0(active0, 0x10000000000000L);
          case 101:
             if ((active0 & 0x10000000L) != 0L)
                return jjStartNfaWithStates_0(4, 28, 32);
             else if ((active0 & 0x4000000000000000L) != 0L)
                return jjStartNfaWithStates_0(4, 62, 32);
             return jjMoveStringLiteralDfa5_0(active0, 0x400800004000L);
          case 104:
             if ((active0 & 0x40000L) != 0L)
                return jjStartNfaWithStates_0(4, 18, 32);
             return jjMoveStringLiteralDfa5_0(active0, 0x20000000000000L);
          case 105:
             return jjMoveStringLiteralDfa5_0(active0, 0x4800000400000L);
          case 107:
             if ((active0 & 0x8000L) != 0L)
                return jjStartNfaWithStates_0(4, 15, 32);
             break;
          case 108:
             if ((active0 & 0x20000000L) != 0L)
             {
                jjmatchedKind = 29;
                jjmatchedPos = 4;
             }
             return jjMoveStringLiteralDfa5_0(active0, 0x42000000L);
          case 110:
             return jjMoveStringLiteralDfa5_0(active0, 0x8000000L);
          case 114:
             if ((active0 & 0x8000000000000L) != 0L)
                return jjStartNfaWithStates_0(4, 51, 32);
             return jjMoveStringLiteralDfa5_0(active0, 0x1009000002000L);
          case 115:
             if ((active0 & 0x100000L) != 0L)
                return jjStartNfaWithStates_0(4, 20, 32);
             return jjMoveStringLiteralDfa5_0(active0, 0x200000000000000L);
          case 116:
             if ((active0 & 0x200000L) != 0L)
                return jjStartNfaWithStates_0(4, 21, 32);
             else if ((active0 & 0x80000000L) != 0L)
                return jjStartNfaWithStates_0(4, 31, 32);
             else if ((active0 & 0x2000000000000L) != 0L)
                return jjStartNfaWithStates_0(4, 49, 32);
             return jjMoveStringLiteralDfa5_0(active0, 0x2000000000000000L);
          case 117:
             return jjMoveStringLiteralDfa5_0(active0, 0x800000L);
          case 118:
             return jjMoveStringLiteralDfa5_0(active0, 0x20000000000L);
          case 119:
             if ((active0 & 0x80000000000000L) != 0L)
             {
                jjmatchedKind = 55;
                jjmatchedPos = 4;
             }
             return jjMoveStringLiteralDfa5_0(active0, 0x100000000000000L);
          default :
             break;
       }
       return jjStartNfa_0(3, active0, 0L);
    }
    private final int jjMoveStringLiteralDfa5_0(long old0, long active0)
    {
       if (((active0 &= old0)) == 0L)
          return jjStartNfa_0(3, old0, 0L);
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(4, active0, 0L);
          return 5;
       }
       switch(curChar)
       {
          case 97:
             return jjMoveStringLiteralDfa6_0(active0, 0x6000L);
          case 99:
             if ((active0 & 0x800000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 47, 32);
             else if ((active0 & 0x4000000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 50, 32);
             return jjMoveStringLiteralDfa6_0(active0, 0x400000000000L);
          case 100:
             return jjMoveStringLiteralDfa6_0(active0, 0x8000000L);
          case 101:
             if ((active0 & 0x2000000L) != 0L)
                return jjStartNfaWithStates_0(5, 25, 32);
             else if ((active0 & 0x20000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 41, 32);
             break;
          case 102:
             return jjMoveStringLiteralDfa6_0(active0, 0x8000000000L);
          case 103:
             return jjMoveStringLiteralDfa6_0(active0, 0x100000000000L);
          case 104:
             if ((active0 & 0x10000000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 52, 32);
             break;
          case 105:
             return jjMoveStringLiteralDfa6_0(active0, 0x2200000000000000L);
          case 108:
             return jjMoveStringLiteralDfa6_0(active0, 0x40800000L);
          case 109:
             return jjMoveStringLiteralDfa6_0(active0, 0x800000000L);
          case 110:
             if ((active0 & 0x1000000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 48, 32);
             return jjMoveStringLiteralDfa6_0(active0, 0x2000400000L);
          case 114:
             return jjMoveStringLiteralDfa6_0(active0, 0x20000000000000L);
          case 115:
             if ((active0 & 0x100000000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 56, 32);
             break;
          case 116:
             if ((active0 & 0x1000000000L) != 0L)
                return jjStartNfaWithStates_0(5, 36, 32);
             return jjMoveStringLiteralDfa6_0(active0, 0x200000000000L);
          default :
             break;
       }
       return jjStartNfa_0(4, active0, 0L);
    }
    private final int jjMoveStringLiteralDfa6_0(long old0, long active0)
    {
       if (((active0 &= old0)) == 0L)
          return jjStartNfa_0(4, old0, 0L);
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(5, active0, 0L);
          return 6;
       }
       switch(curChar)
       {
          case 97:
             return jjMoveStringLiteralDfa7_0(active0, 0x8000000000L);
          case 99:
             return jjMoveStringLiteralDfa7_0(active0, 0x2000002000L);
          case 101:
             if ((active0 & 0x100000000000L) != 0L)
                return jjStartNfaWithStates_0(6, 44, 32);
             else if ((active0 & 0x200000000000L) != 0L)
                return jjStartNfaWithStates_0(6, 45, 32);
             return jjMoveStringLiteralDfa7_0(active0, 0x200000800000000L);
          case 108:
             return jjMoveStringLiteralDfa7_0(active0, 0x2000000000000000L);
          case 110:
             if ((active0 & 0x4000L) != 0L)
                return jjStartNfaWithStates_0(6, 14, 32);
             break;
          case 111:
             return jjMoveStringLiteralDfa7_0(active0, 0x20000000000000L);
          case 115:
             if ((active0 & 0x8000000L) != 0L)
                return jjStartNfaWithStates_0(6, 27, 32);
             break;
          case 116:
             if ((active0 & 0x800000L) != 0L)
                return jjStartNfaWithStates_0(6, 23, 32);
             return jjMoveStringLiteralDfa7_0(active0, 0x400000000000L);
          case 117:
             return jjMoveStringLiteralDfa7_0(active0, 0x400000L);
          case 121:
             if ((active0 & 0x40000000L) != 0L)
                return jjStartNfaWithStates_0(6, 30, 32);
             break;
          default :
             break;
       }
       return jjStartNfa_0(5, active0, 0L);
    }
    private final int jjMoveStringLiteralDfa7_0(long old0, long active0)
    {
       if (((active0 &= old0)) == 0L)
          return jjStartNfa_0(5, old0, 0L);
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(6, active0, 0L);
          return 7;
       }
       switch(curChar)
       {
          case 99:
             return jjMoveStringLiteralDfa8_0(active0, 0x8000000000L);
          case 101:
             if ((active0 & 0x400000L) != 0L)
                return jjStartNfaWithStates_0(7, 22, 32);
             else if ((active0 & 0x2000000000000000L) != 0L)
                return jjStartNfaWithStates_0(7, 61, 32);
             return jjMoveStringLiteralDfa8_0(active0, 0x402000000000L);
          case 110:
             return jjMoveStringLiteralDfa8_0(active0, 0x220000800000000L);
          case 116:
             if ((active0 & 0x2000L) != 0L)
                return jjStartNfaWithStates_0(7, 13, 32);
             break;
          default :
             break;
       }
       return jjStartNfa_0(6, active0, 0L);
    }
    private final int jjMoveStringLiteralDfa8_0(long old0, long active0)
    {
       if (((active0 &= old0)) == 0L)
          return jjStartNfa_0(6, old0, 0L);
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(7, active0, 0L);
          return 8;
       }
       switch(curChar)
       {
          case 100:
             if ((active0 & 0x400000000000L) != 0L)
                return jjStartNfaWithStates_0(8, 46, 32);
             break;
          case 101:
             if ((active0 & 0x8000000000L) != 0L)
                return jjStartNfaWithStates_0(8, 39, 32);
             break;
          case 105:
             return jjMoveStringLiteralDfa9_0(active0, 0x20000000000000L);
          case 111:
             return jjMoveStringLiteralDfa9_0(active0, 0x2000000000L);
          case 116:
             if ((active0 & 0x200000000000000L) != 0L)
                return jjStartNfaWithStates_0(8, 57, 32);
             return jjMoveStringLiteralDfa9_0(active0, 0x800000000L);
          default :
             break;
       }
       return jjStartNfa_0(7, active0, 0L);
    }
    private final int jjMoveStringLiteralDfa9_0(long old0, long active0)
    {
       if (((active0 &= old0)) == 0L)
          return jjStartNfa_0(7, old0, 0L);
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(8, active0, 0L);
          return 9;
       }
       switch(curChar)
       {
          case 102:
             if ((active0 & 0x2000000000L) != 0L)
                return jjStartNfaWithStates_0(9, 37, 32);
             break;
          case 115:
             if ((active0 & 0x800000000L) != 0L)
                return jjStartNfaWithStates_0(9, 35, 32);
             break;
          case 122:
             return jjMoveStringLiteralDfa10_0(active0, 0x20000000000000L);
          default :
             break;
       }
       return jjStartNfa_0(8, active0, 0L);
    }
    private final int jjMoveStringLiteralDfa10_0(long old0, long active0)
    {
       if (((active0 &= old0)) == 0L)
          return jjStartNfa_0(8, old0, 0L);
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(9, active0, 0L);
          return 10;
       }
       switch(curChar)
       {
          case 101:
             return jjMoveStringLiteralDfa11_0(active0, 0x20000000000000L);
          default :
             break;
       }
       return jjStartNfa_0(9, active0, 0L);
    }
    private final int jjMoveStringLiteralDfa11_0(long old0, long active0)
    {
       if (((active0 &= old0)) == 0L)
          return jjStartNfa_0(9, old0, 0L);
       try { curChar = input_stream.readChar(); }
       catch(java.io.IOException e) {
          jjStopStringLiteralDfa_0(10, active0, 0L);
          return 11;
       }
       switch(curChar)
       {
          case 100:
             if ((active0 & 0x20000000000000L) != 0L)
                return jjStartNfaWithStates_0(11, 53, 32);
             break;
          default :
             break;
       }
       return jjStartNfa_0(10, active0, 0L);
    }
    final long[] jjbitVec0 = {
       0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
    };
    final long[] jjbitVec1 = {
       0x1ff00000fffffffeL, 0xffffffffffffc000L, 0xffffffffL, 0x600000000000000L
    };
    final long[] jjbitVec3 = {
       0x0L, 0x0L, 0x0L, 0xff7fffffff7fffffL
    };
    final long[] jjbitVec4 = {
       0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL
    };
    final long[] jjbitVec5 = {
       0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffL, 0x0L
    };
    final long[] jjbitVec6 = {
       0xffffffffffffffffL, 0xffffffffffffffffL, 0x0L, 0x0L
    };
    final long[] jjbitVec7 = {
       0x3fffffffffffL, 0x0L, 0x0L, 0x0L
    };
    private final int jjMoveNfa_0(int startState, int curPos)
    {
       int[] nextStates;
       int startsAt = 0;
       jjnewStateCnt = 52;
       int i = 1;
       jjstateSet[0] = startState;
       int j, kind = 0x7fffffff;
       for (;;)
       {
          if (++jjround == 0x7fffffff)
             ReInitRounds();
          if (curChar < 64)
          {
             long l = 1L << curChar;
             MatchLoop: do
             {
                switch(jjstateSet[--i])
                {
                   case 3:
                      if ((0x3ff000000000000L & l) != 0L)
                         jjCheckNAddStates(0, 6);
                      else if (curChar == 36)
                      {
                         if (kind > 71)
                            kind = 71;
                         jjCheckNAdd(32);
                      }
                      else if (curChar == 34)
                         jjCheckNAddStates(7, 9);
                      else if (curChar == 39)
                         jjAddStates(10, 11);
                      else if (curChar == 46)
                         jjCheckNAdd(8);
                      else if (curChar == 47)
                         jjstateSet[jjnewStateCnt++] = 2;
                      if ((0x3fe000000000000L & l) != 0L)
                      {
                         if (kind > 63)
                            kind = 63;
                         jjCheckNAddTwoStates(5, 6);
                      }
                      else if (curChar == 48)
                      {
                         if (kind > 63)
                            kind = 63;
                         jjCheckNAddStates(12, 14);
                      }
                      break;
                   case 0:
                      if (curChar == 42)
                         jjstateSet[jjnewStateCnt++] = 1;
                      break;
                   case 1:
                      if ((0xffff7fffffffffffL & l) != 0L && kind > 7)
                         kind = 7;
                      break;
                   case 2:
                      if (curChar == 42)
                         jjstateSet[jjnewStateCnt++] = 0;
                      break;
                   case 4:
                      if ((0x3fe000000000000L & l) == 0L)
                         break;
                      if (kind > 63)
                         kind = 63;
                      jjCheckNAddTwoStates(5, 6);
                      break;
                   case 5:
                      if ((0x3ff000000000000L & l) == 0L)
                         break;
                      if (kind > 63)
                         kind = 63;
                      jjCheckNAddTwoStates(5, 6);
                      break;
                   case 7:
                      if (curChar == 46)
                         jjCheckNAdd(8);
                      break;
                   case 8:
                      if ((0x3ff000000000000L & l) == 0L)
                         break;
                      if (kind > 67)
                         kind = 67;
                      jjCheckNAddStates(15, 17);
                      break;
                   case 10:
                      if ((0x280000000000L & l) != 0L)
                         jjCheckNAdd(11);
                      break;
                   case 11:
                      if ((0x3ff000000000000L & l) == 0L)
                         break;
                      if (kind > 67)
                         kind = 67;
                      jjCheckNAddTwoStates(11, 12);
                      break;
                   case 13:
                      if (curChar == 39)
                         jjAddStates(10, 11);
                      break;
                   case 14:
                      if ((0xffffff7fffffdbffL & l) != 0L)
                         jjCheckNAdd(15);
                      break;
                   case 15:
                      if (curChar == 39 && kind > 69)
                         kind = 69;
                      break;
                   case 17:
                      if ((0x8400000000L & l) != 0L)
                         jjCheckNAdd(15);
                      break;
                   case 18:
                      if ((0xff000000000000L & l) != 0L)
                         jjCheckNAddTwoStates(19, 15);
                      break;
                   case 19:
                      if ((0xff000000000000L & l) != 0L)
                         jjCheckNAdd(15);
                      break;
                   case 20:
                      if ((0xf000000000000L & l) != 0L)
                         jjstateSet[jjnewStateCnt++] = 21;
                      break;
                   case 21:
                      if ((0xff000000000000L & l) != 0L)
                         jjCheckNAdd(19);
                      break;
                   case 22:
                      if (curChar == 34)
                         jjCheckNAddStates(7, 9);
                      break;
                   case 23:
                      if ((0xfffffffbffffdbffL & l) != 0L)
                         jjCheckNAddStates(7, 9);
                      break;
                   case 25:
                      if ((0x8400000000L & l) != 0L)
                         jjCheckNAddStates(7, 9);
                      break;
                   case 26:
                      if (curChar == 34 && kind > 70)
                         kind = 70;
                      break;
                   case 27:
                      if ((0xff000000000000L & l) != 0L)
                         jjCheckNAddStates(18, 21);
                      break;
                   case 28:
                      if ((0xff000000000000L & l) != 0L)
                         jjCheckNAddStates(7, 9);
                      break;
                   case 29:
                      if ((0xf000000000000L & l) != 0L)
                         jjstateSet[jjnewStateCnt++] = 30;
                      break;
                   case 30:
                      if ((0xff000000000000L & l) != 0L)
                         jjCheckNAdd(28);
                      break;
                   case 31:
                      if (curChar != 36)
                         break;
                      if (kind > 71)
                         kind = 71;
                      jjCheckNAdd(32);
                      break;
                   case 32:
                      if ((0x3ff001000000000L & l) == 0L)
                         break;
                      if (kind > 71)
                         kind = 71;
                      jjCheckNAdd(32);
                      break;
                   case 33:
                      if ((0x3ff000000000000L & l) != 0L)
                         jjCheckNAddStates(0, 6);
                      break;
                   case 34:
                      if ((0x3ff000000000000L & l) != 0L)
                         jjCheckNAddTwoStates(34, 35);
                      break;
                   case 35:
                      if (curChar != 46)
                         break;
                      if (kind > 67)
                         kind = 67;
                      jjCheckNAddStates(22, 24);
                      break;
                   case 36:
                      if ((0x3ff000000000000L & l) == 0L)
                         break;
                      if (kind > 67)
                         kind = 67;
                      jjCheckNAddStates(22, 24);
                      break;
                   case 38:
                      if ((0x280000000000L & l) != 0L)
                         jjCheckNAdd(39);
                      break;
                   case 39:
                      if ((0x3ff000000000000L & l) == 0L)
                         break;
                      if (kind > 67)
                         kind = 67;
                      jjCheckNAddTwoStates(39, 12);
                      break;
                   case 40:
                      if ((0x3ff000000000000L & l) != 0L)
                         jjCheckNAddTwoStates(40, 41);
                      break;
                   case 42:
                      if ((0x280000000000L & l) != 0L)
                         jjCheckNAdd(43);
                      break;
                   case 43:
                      if ((0x3ff000000000000L & l) == 0L)
                         break;
                      if (kind > 67)
                         kind = 67;
                      jjCheckNAddTwoStates(43, 12);
                      break;
                   case 44:
                      if ((0x3ff000000000000L & l) != 0L)
                         jjCheckNAddStates(25, 27);
                      break;
                   case 46:
                      if ((0x280000000000L & l) != 0L)
                         jjCheckNAdd(47);
                      break;
                   case 47:
                      if ((0x3ff000000000000L & l) != 0L)
                         jjCheckNAddTwoStates(47, 12);
                      break;
                   case 48:
                      if (curChar != 48)
                         break;
                      if (kind > 63)
                         kind = 63;
                      jjCheckNAddStates(12, 14);
                      break;
                   case 50:
                      if ((0x3ff000000000000L & l) == 0L)
                         break;
                      if (kind > 63)
                         kind = 63;
                      jjCheckNAddTwoStates(50, 6);
                      break;
                   case 51:
                      if ((0xff000000000000L & l) == 0L)
                         break;
                      if (kind > 63)
                         kind = 63;
                      jjCheckNAddTwoStates(51, 6);
                      break;
                   default : break;
                }
             } while(i != startsAt);
          }
          else if (curChar < 128)
          {
             long l = 1L << (curChar & 077);
             MatchLoop: do
             {
                switch(jjstateSet[--i])
                {
                   case 3:
                   case 32:
                      if ((0x7fffffe87fffffeL & l) == 0L)
                         break;
                      if (kind > 71)
                         kind = 71;
                      jjCheckNAdd(32);
                      break;
                   case 1:
                      if (kind > 7)
                         kind = 7;
                      break;
                   case 6:
                      if ((0x100000001000L & l) != 0L && kind > 63)
                         kind = 63;
                      break;
                   case 9:
                      if ((0x2000000020L & l) != 0L)
                         jjAddStates(28, 29);
                      break;
                   case 12:
                      if ((0x5000000050L & l) != 0L && kind > 67)
                         kind = 67;
                      break;
                   case 14:
                      if ((0xffffffffefffffffL & l) != 0L)
                         jjCheckNAdd(15);
                      break;
                   case 16:
                      if (curChar == 92)
                         jjAddStates(30, 32);
                      break;
                   case 17:
                      if ((0x14404410000000L & l) != 0L)
                         jjCheckNAdd(15);
                      break;
                   case 23:
                      if ((0xffffffffefffffffL & l) != 0L)
                         jjCheckNAddStates(7, 9);
                      break;
                   case 24:
                      if (curChar == 92)
                         jjAddStates(33, 35);
                      break;
                   case 25:
                      if ((0x14404410000000L & l) != 0L)
                         jjCheckNAddStates(7, 9);
                      break;
                   case 37:
                      if ((0x2000000020L & l) != 0L)
                         jjAddStates(36, 37);
                      break;
                   case 41:
                      if ((0x2000000020L & l) != 0L)
                         jjAddStates(38, 39);
                      break;
                   case 45:
                      if ((0x2000000020L & l) != 0L)
                         jjAddStates(40, 41);
                      break;
                   case 49:
                      if ((0x100000001000000L & l) != 0L)
                         jjCheckNAdd(50);
                      break;
                   case 50:
                      if ((0x7e0000007eL & l) == 0L)
                         break;
                      if (kind > 63)
                         kind = 63;
                      jjCheckNAddTwoStates(50, 6);
                      break;
                   default : break;
                }
             } while(i != startsAt);
          }
          else
          {
             int i2 = (curChar & 0xff) >> 6;
             long l2 = 1L << (curChar & 077);
             MatchLoop: do
             {
                switch(jjstateSet[--i])
                {
                   case 3:
                   case 32:
                      if ((jjbitVec3[i2] & l2) == 0L)
                         break;
                      if (kind > 71)
                         kind = 71;
                      jjCheckNAdd(32);
                      break;
                   case 1:
                      if ((jjbitVec0[i2] & l2) != 0L && kind > 7)
                         kind = 7;
                      break;
                   case 14:
                      if ((jjbitVec0[i2] & l2) != 0L)
                         jjstateSet[jjnewStateCnt++] = 15;
                      break;
                   case 23:
                      if ((jjbitVec0[i2] & l2) != 0L)
                         jjAddStates(7, 9);
                      break;
                   default : break;
                }
             } while(i != startsAt);
          }
          if (kind != 0x7fffffff)
          {
             jjmatchedKind = kind;
             jjmatchedPos = curPos;
             kind = 0x7fffffff;
          }
          ++curPos;
          if ((i = jjnewStateCnt) == (startsAt = 52 - (jjnewStateCnt = startsAt)))
             return curPos;
          try { curChar = input_stream.readChar(); }
          catch(java.io.IOException e) { return curPos; }
       }
    }
    final int[] jjnextStates = {
       34, 35, 40, 41, 44, 45, 12, 23, 24, 26, 14, 16, 49, 51, 6, 8, 
       9, 12, 23, 24, 28, 26, 36, 37, 12, 44, 45, 12, 10, 11, 17, 18, 
       20, 25, 27, 29, 38, 39, 42, 43, 46, 47, 
    };
    public final String[] jjstrLiteralImages = {
    "", null, null, null, null, null, null, null, null, null, null, null, null, 
    "\141\142\163\164\162\141\143\164", "\142\157\157\154\145\141\156", "\142\162\145\141\153", "\142\171\164\145", 
    "\143\141\163\145", "\143\141\164\143\150", "\143\150\141\162", "\143\154\141\163\163", 
    "\143\157\156\163\164", "\143\157\156\164\151\156\165\145", "\144\145\146\141\165\154\164", 
    "\144\157", "\144\157\165\142\154\145", "\145\154\163\145", 
    "\145\170\164\145\156\144\163", "\146\141\154\163\145", "\146\151\156\141\154", 
    "\146\151\156\141\154\154\171", "\146\154\157\141\164", "\146\157\162", "\147\157\164\157", "\151\146", 
    "\151\155\160\154\145\155\145\156\164\163", "\151\155\160\157\162\164", "\151\156\163\164\141\156\143\145\157\146", 
    "\151\156\164", "\151\156\164\145\162\146\141\143\145", "\154\157\156\147", 
    "\156\141\164\151\166\145", "\156\145\167", "\156\165\154\154", "\160\141\143\153\141\147\145", 
    "\160\162\151\166\141\164\145", "\160\162\157\164\145\143\164\145\144", "\160\165\142\154\151\143", 
    "\162\145\164\165\162\156", "\163\150\157\162\164", "\163\164\141\164\151\143", "\163\165\160\145\162", 
    "\163\167\151\164\143\150", "\163\171\156\143\150\162\157\156\151\172\145\144", "\164\150\151\163", 
    "\164\150\162\157\167", "\164\150\162\157\167\163", "\164\162\141\156\163\151\145\156\164", 
    "\164\162\165\145", "\164\162\171", "\166\157\151\144", "\166\157\154\141\164\151\154\145", 
    "\167\150\151\154\145", null, null, null, null, null, null, null, null, null, null, null, "\50", 
    "\51", "\173", "\175", "\133", "\135", "\73", "\54", "\56", "\75", "\76", "\74", 
    "\41", "\176", "\77", "\72", "\75\75", "\74\75", "\76\75", "\41\75", "\174\174", 
    "\46\46", "\53\53", "\55\55", "\53", "\55", "\52", "\57", "\46", "\174", "\136", "\45", 
    "\74\74", "\76\76", "\76\76\76", "\53\75", "\55\75", "\52\75", "\57\75", "\46\75", 
    "\174\75", "\136\75", "\45\75", "\74\74\75", "\76\76\75", "\76\76\76\75", };
    public final String[] lexStateNames = {
       "DEFAULT", 
       "IN_SINGLE_LINE_COMMENT", 
       "IN_FORMAL_COMMENT", 
       "IN_MULTI_LINE_COMMENT", 
    };
    public final int[] jjnewLexState = {
       -1, -1, -1, -1, -1, -1, 1, 2, 3, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
       -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
       -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
       -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
       -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
    };
    final long[] jjtoToken = {
       0xffffffffffffe001L, 0xfffffffffffce8L, 
    };
    final long[] jjtoSkip = {
       0xe3eL, 0x0L, 
    };
    final long[] jjtoSpecial = {
       0xe00L, 0x0L, 
    };
    final long[] jjtoMore = {
       0x11c0L, 0x0L, 
    };
    private com.metamata.parse.MParseReader input_stream;
    private final int[] jjrounds = new int[52];
    private final int[] jjstateSet = new int[104];
    StringBuffer image;
    int jjimageLen;
    int lengthOfMatch;
    protected char curChar;
    public __jjJavaParserTokenManager(com.metamata.parse.MParseReader stream)
    {
       if (false)
          throw new Error("ERROR: Cannot use a CharStream class with a non-static lexical analyzer.");
       input_stream = stream;
    }
    public __jjJavaParserTokenManager(com.metamata.parse.MParseReader stream, int lexState)
    {
       this(stream);
       SwitchTo(lexState);
    }
    public void ReInit(com.metamata.parse.MParseReader stream)
    {
       jjmatchedPos = jjnewStateCnt = 0;
       curLexState = defaultLexState;
       input_stream = stream;
       ReInitRounds();
    }
    private final void ReInitRounds()
    {
       int i;
       jjround = 0x80000001;
       for (i = 52; i-- > 0;)
          jjrounds[i] = 0x80000000;
    }
    public void ReInit(com.metamata.parse.MParseReader stream, int lexState)
    {
       ReInit(stream);
       SwitchTo(lexState);
    }
    public void SwitchTo(int lexState)
    {
       if (lexState >= 4 || lexState < 0)
          throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", INVALID_LEXICAL_STATE);
       else
          curLexState = lexState;
    }
    
    private final com.metamata.parse.Token jjFillToken()
    {
       com.metamata.parse.Token t = com.metamata.parse.Token.newToken(jjmatchedKind);
       t.kind = jjmatchedKind;
       String im = jjstrLiteralImages[jjmatchedKind];
       t.image = (im == null) ? input_stream.GetImage() : im;
       t.beginLine = input_stream.getBeginLine();
       t.beginColumn = input_stream.getBeginColumn();
       t.endLine = input_stream.getEndLine();
       t.endColumn = input_stream.getEndColumn();
       return t;
    }
    
    int curLexState = 0;
    int defaultLexState = 0;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;
    
    public final com.metamata.parse.Token getNextToken() 
    {
      int kind;
      com.metamata.parse.Token specialToken = null;
      com.metamata.parse.Token matchedToken;
      int curPos = 0;
    
      EOFLoop :
      for (;;)
      {   
       try   
       {     
          curChar = input_stream.BeginToken();
       }     
       catch(java.io.IOException e)
       {        
          jjmatchedKind = 0;
          matchedToken = jjFillToken();
          matchedToken.specialToken = specialToken;
          return matchedToken;
       }
       image = null;
       jjimageLen = 0;
    
       for (;;)
       {
         switch(curLexState)
         {
           case 0:
             try { input_stream.backup(0);
                while (curChar <= 32 && (0x100003600L & (1L << curChar)) != 0L)
                   curChar = input_stream.BeginToken();
             }
             catch (java.io.IOException e1) { continue EOFLoop; }
             jjmatchedKind = 0x7fffffff;
             jjmatchedPos = 0;
             curPos = jjMoveStringLiteralDfa0_0();
             break;
           case 1:
             jjmatchedKind = 0x7fffffff;
             jjmatchedPos = 0;
             curPos = jjMoveStringLiteralDfa0_1();
             if (jjmatchedPos == 0 && jjmatchedKind > 12)
             {
                jjmatchedKind = 12;
             }
             break;
           case 2:
             jjmatchedKind = 0x7fffffff;
             jjmatchedPos = 0;
             curPos = jjMoveStringLiteralDfa0_2();
             if (jjmatchedPos == 0 && jjmatchedKind > 12)
             {
                jjmatchedKind = 12;
             }
             break;
           case 3:
             jjmatchedKind = 0x7fffffff;
             jjmatchedPos = 0;
             curPos = jjMoveStringLiteralDfa0_3();
             if (jjmatchedPos == 0 && jjmatchedKind > 12)
             {
                jjmatchedKind = 12;
             }
             break;
         }
         if (jjmatchedKind != 0x7fffffff)
         {
            if (jjmatchedPos + 1 < curPos)
               input_stream.backup(curPos - jjmatchedPos - 1);
            if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
            {
               matchedToken = jjFillToken();
               matchedToken.specialToken = specialToken;
           if (jjnewLexState[jjmatchedKind] != -1)
             curLexState = jjnewLexState[jjmatchedKind];
               return matchedToken;
            }
            else if ((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
            {
               if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
               {
                  matchedToken = jjFillToken();
                  if (specialToken == null)
                     specialToken = matchedToken;
                  else
                  {
                     matchedToken.specialToken = specialToken;
                     specialToken = (specialToken.next = matchedToken);
                  }
                  SkipLexicalActions(matchedToken);
               }
               else 
                  SkipLexicalActions(null);
             if (jjnewLexState[jjmatchedKind] != -1)
               curLexState = jjnewLexState[jjmatchedKind];
               continue EOFLoop;
            }
            MoreLexicalActions();
          if (jjnewLexState[jjmatchedKind] != -1)
            curLexState = jjnewLexState[jjmatchedKind];
            curPos = 0;
            jjmatchedKind = 0x7fffffff;
            try {
               curChar = input_stream.readChar();
               continue;
            }
            catch (java.io.IOException e1) { }
         }
         int error_line = input_stream.getEndLine();
         int error_column = input_stream.getEndColumn();
         String error_after = null;
         boolean EOFSeen = false;
         try { input_stream.readChar(); input_stream.backup(1); }
         catch (java.io.IOException e1) {
            EOFSeen = true;
            error_after = curPos <= 1 ? "" : input_stream.GetImage();
            if (curChar == '\n' || curChar == '\r') {
               error_line++;
               error_column = 0;
            }
            else
               error_column++;
         }
         if (!EOFSeen) {
            input_stream.backup(1);
            error_after = curPos <= 1 ? "" : input_stream.GetImage();
         }
         throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, LEXICAL_ERROR);
       }
      }
    }
    
    final void SkipLexicalActions(com.metamata.parse.Token matchedToken)
    {
       switch(jjmatchedKind)
       {
          default :
             break;
       }
    }
    final void MoreLexicalActions()
    {
       jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
       switch(jjmatchedKind)
       {
          case 7 :
             if (image == null)
                  image = new StringBuffer(new String(input_stream.GetSuffix(jjimageLen)));
             else
                image.append(new String(input_stream.GetSuffix(jjimageLen)));
             jjimageLen = 0;
                     backup(1);
             break;
          default : 
             break;
       }
    }
    }

  }

  private static JavaParser __jjcns = new JavaParser((JavaParser)null);

  public static com.metamata.parse.Scanner createNewScanner(java.io.Reader input) {
    return __jjcns.new __jjScanner(input);
  }

  public static com.metamata.parse.Scanner createNewScanner(java.io.InputStream input) {
    return createNewScanner(new java.io.InputStreamReader(input));
  }

  public static com.metamata.parse.Scanner createNewScanner() {
    return createNewScanner(System.in);
  }

  public com.metamata.parse.ParserState __jjstate;
  public static com.metamata.parse.ParserState __jjstaticstate = new com.metamata.parse.ParserState();

  private JavaParser(JavaParser dummy) {
  }

  public JavaParser() {
    this(System.in);
  }

  public JavaParser(java.io.InputStream input) {
    this(new java.io.InputStreamReader(input));
  }

  public JavaParser(java.io.Reader input) {
    __jjstate = new com.metamata.parse.ParserState();
    __jjstate.initialize(createNewScanner(input));
  }

  public JavaParser(com.metamata.parse.Scanner scanner) {
    __jjstate = new com.metamata.parse.ParserState();
    __jjstate.initialize(scanner);
  }

  public void initializeParser() {
    initializeParser(System.in);
  }

  public void initializeParser(java.io.InputStream input) {
    initializeParser(new java.io.InputStreamReader(input));
  }

  public void initializeParser(java.io.Reader input) {
    __jjstate.initialize(createNewScanner(input));
  }

  public void initializeParser(com.metamata.parse.Scanner scanner) {
    __jjstate.initialize(scanner);
  }

  public static void initializeStaticParser() {
    initializeStaticParser(System.in);
  }

  public static void initializeStaticParser(java.io.InputStream input) {
    initializeStaticParser(new java.io.InputStreamReader(input));
  }

  public static void initializeStaticParser(java.io.Reader input) {
    __jjstaticstate.initialize(createNewScanner(input));
  }

  public static void initializeStaticParser(com.metamata.parse.Scanner scanner) {
    __jjstaticstate.initialize(scanner);
  }

  public com.metamata.parse.Token getNextToken() {
    return __jjstate.getNextToken();
  }

  public com.metamata.parse.Token getToken(int index) {
    return __jjstate.getToken(index);
  }

  public static com.metamata.parse.Token getNextStaticParserToken() {
    return __jjstaticstate.getNextToken();
  }

  public static com.metamata.parse.Token getStaticParserToken(int index) {
    return __jjstaticstate.getToken(index);
  }

  public boolean lookingAhead() {
    return __jjstate.guessing;
  }

  public static boolean staticLookingAhead() {
    return __jjstaticstate.guessing;
  }

  public static void lookAheadPassed() {
    throw com.metamata.parse.GuessComplete.success;
  }

  public static void lookAheadFailed() {
    throw com.metamata.parse.GuessComplete.failure;
  }

private CompilationUnit m_unit = null;

    public CompilationUnit parseCU()
	throws com.metamata.parse.ParseException
    {
	return parseCU(false);
    }

    public CompilationUnit parseCU(boolean indexNodes)
	throws com.metamata.parse.ParseException
    {
	m_unit = new CompilationUnit(indexNodes);
	compilationUnit();
	return m_unit;
    }
	 
    public static void main(String[] args) 
    {
	JavaParser parser;
	if (args.length == 0) 
	{
	    System.out.println("Java Parser:  Reading from standard input . . .");
	    parser = new JavaParser(new java.io.InputStreamReader(System.in));
	} 
	else if (args.length == 1) 
	{
	    System.out.println("Java Parser:  Reading from file " + args[0] + " . . .");
	    try 
	    {
		parser = new JavaParser(new java.io.InputStreamReader(new java.io.FileInputStream(args[0])));
	    } 
	    catch (java.io.FileNotFoundException e) 
	    {
		System.out.println("Java Parser:  File " + args[0] + " not found.");
		return;
	    }
	} 
	else 
	{
	    System.out.println("Java Parser:  Usage is one of:");
	    System.out.println("         java JavaParser < inputfile");
	    System.out.println("OR");
	    System.out.println("         java JavaParser inputfile");
	    return;
	}
	try 
	{
	    parser.compilationUnit();
	    System.out.println("Java Parser:  Java program parsed successfully.");
	} 
	catch (com.metamata.parse.ParseException e) 
	{
	    System.out.println(e.getMessage());
	    System.out.println("Java Parser:  Encountered errors during parse.");
	}
    }

  /* WHITE SPACE */

  

  /* COMMENTS */

  

  

  

  

  

  /* RESERVED WORDS AND LITERALS */

  

  /* LITERALS */

  

  /* IDENTIFIERS */

  

  /* SEPARATORS */

  

  /* OPERATORS */

  

  /*****************************************
   * THE JAVA LANGUAGE GRAMMAR STARTS HERE *
   *****************************************/

  /*
   * Program structuring syntax follows.
   */

    public void compilationUnit() 
     throws com.metamata.parse.ParseException {
	{ }if(m_unit == null) { m_unit = new CompilationUnit(false); } TypeDeclaration n = null;{ }
	
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
packageDeclaration();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;

boolean __jjV4 = __jjstate.guessing;
int __jjV5 = 0;
com.metamata.parse.Token __jjV6 = null;
for (;;) {
if (__jjV4) {
  __jjV5 = __jjstate.la;
  __jjV6 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV7 = true;
for (;;) {
  try {
importDeclaration();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV7 = false;
    if (__jjV4) {
      __jjstate.la = __jjV5;
      __jjstate.laToken = __jjV6;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV7) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV7) {
  break;
}
}

boolean __jjV8 = __jjstate.guessing;
int __jjV9 = 0;
com.metamata.parse.Token __jjV10 = null;
for (;;) {
if (__jjV8) {
  __jjV9 = __jjstate.la;
  __jjV10 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV11 = true;
for (;;) {
  try {
n = typeDeclaration();if (!__jjstate.guessing)
{ m_unit.setTypeDeclaration(n); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV11 = false;
    if (__jjV8) {
      __jjstate.la = __jjV9;
      __jjstate.laToken = __jjV10;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV11) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV11) {
  break;
}
}
__jjstate.ematch(EOF);

    }


    private void packageDeclaration() 
     throws com.metamata.parse.ParseException {
	{ }Name n=null;{ }
	__jjstate.ematch(__jjVpackage);
n = name();__jjstate.ematch(__jjV_1B);
if (!__jjstate.guessing)
{ m_unit.setPackage(n.getValue()); }
    }


    private void importDeclaration() 
     throws com.metamata.parse.ParseException {
	{ }Name name; boolean onDemand = false;{ }
	__jjstate.ematch(__jjVimport);
name = name();
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_oB);
__jjstate.ematch(__jjV_kB);
if (!__jjstate.guessing)
{onDemand = true;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
__jjstate.ematch(__jjV_1B);
if (!__jjstate.guessing)
{ ImportDeclaration i = new ImportDeclaration(name.getValue(), onDemand); 
	  m_unit.addImportDeclaration(i); 
	}
    }


    private TypeDeclaration typeDeclaration() 
     throws com.metamata.parse.ParseException {
	{ }TypeDeclaration node = null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
	if (__jjV0 || !__jjstate.guessing) {
node = classDeclaration();	} else {

for (;;) {
int __jjV5 = __jjstate.la;
com.metamata.parse.Token __jjV6 = __jjstate.laToken;
boolean __jjV7 = true;
try {
int __jjV10 = __jjstate.la;
com.metamata.parse.Token __jjV11 = __jjstate.laToken;
boolean __jjV9 = true;
try {
__jjstate.ematch(__jjVabstract);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV9 = false;
  __jjstate.la = __jjV10;
  __jjstate.laToken = __jjV11;
}
if (!__jjV9) {
int __jjV14 = __jjstate.la;
com.metamata.parse.Token __jjV15 = __jjstate.laToken;
boolean __jjV13 = true;
try {
__jjstate.ematch(__jjVfinal);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV13 = false;
  __jjstate.la = __jjV14;
  __jjstate.laToken = __jjV15;
}
if (!__jjV13) {
__jjstate.ematch(__jjVpublic);
}
}
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV7 = false;
  __jjstate.la = __jjV5;
  __jjstate.laToken = __jjV6;
}
if (!__jjV7) {
  break;
}
}
__jjstate.ematch(__jjVclass);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
node = interfaceDeclaration();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
__jjstate.ematch(__jjV_1B);
}
}
if (!__jjstate.guessing)
{ return node; }return null;

    }

  /*
   * Declaration syntax follows.
   */

    private ClassDeclaration classDeclaration() 
     throws com.metamata.parse.ParseException {
	{ 
	    }ClassDeclaration dec = new ClassDeclaration(null);
	    boolean isAbstract = false;  
	    boolean isFinal = false;
	    Visibility visibility = Visibility.DEFAULT;{
	    
	}
	
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVabstract);
if (!__jjstate.guessing)
{ isAbstract = true; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfinal);
if (!__jjstate.guessing)
{ isFinal = true; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
__jjstate.ematch(__jjVpublic);
if (!__jjstate.guessing)
{ visibility = Visibility.PUBLIC; }}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
dec = unmodifiedClassDeclaration();if (!__jjstate.guessing)
{
	    dec.setAbstract(isAbstract);
	    dec.setFinal(isFinal);
	    dec.setVisibility(visibility);
	    return dec;
	}return null;

    }

    private ClassDeclaration unmodifiedClassDeclaration()
     throws com.metamata.parse.ParseException {
	{ 
	    }ClassDeclaration dec = new ClassDeclaration(null);
	    String name = null;
	    Name superClass = null;
	    Vector v = null;{ 
	}
	__jjstate.ematch(__jjVclass);
__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ name = getToken(0).toString();  }
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVextends);
superClass = name();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;

boolean __jjV5 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV5) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV4 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVimplements);
v = nameList(true);  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV5) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV4 = false;
    if (__jjV5) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV5 || !__jjstate.guessing || !__jjV4) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV5;
classBody(dec);if (!__jjstate.guessing)
{  
	    dec.setName(name);
	    dec.setExtends(superClass == null ? null : superClass.getValue());
	    dec.addImplementsList(v);
	    return dec;
	}return null;

    }

    private void classBody(ClassDeclaration dec) 
     throws com.metamata.parse.ParseException {
	{ }Node n = null;{ }
	__jjstate.ematch(__jjV_1D);

boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
n = classBodyDeclaration();if (!__jjstate.guessing)
{ dec.addDeclaration(n); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
__jjstate.ematch(__jjV_3D);

    }

    private ClassDeclaration nestedClassDeclaration() 
     throws com.metamata.parse.ParseException {
      {
	  }ClassDeclaration dec;
	  boolean isStatic = false; boolean isAbstract = false;
	  boolean isFinal = false; Visibility visibility = Visibility.DEFAULT;{
      }
      
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVstatic);
if (!__jjstate.guessing)
{ isStatic = true; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVabstract);
if (!__jjstate.guessing)
{ isAbstract = true; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfinal);
if (!__jjstate.guessing)
{ isFinal = true; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVpublic);
if (!__jjstate.guessing)
{ visibility = Visibility.PUBLIC; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVprotected);
if (!__jjstate.guessing)
{ visibility = Visibility.PROTECTED; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
__jjstate.ematch(__jjVprivate);
if (!__jjstate.guessing)
{ visibility = Visibility.PRIVATE; }}
}
}
}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
dec = unmodifiedClassDeclaration();if (!__jjstate.guessing)
{
	   dec.setStatic(isStatic);
	   dec.setFinal(isFinal);
	   dec.setAbstract(isAbstract);
	   dec.setVisibility(visibility);
	   return dec;
       }return null;

    }

    private Node classBodyDeclaration() 
     throws com.metamata.parse.ParseException {
	{ }Node n= null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
n = initializer();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
	if (__jjV4 || !__jjstate.guessing) {
n = nestedClassDeclaration();	} else {

for (;;) {
int __jjV9 = __jjstate.la;
com.metamata.parse.Token __jjV10 = __jjstate.laToken;
boolean __jjV11 = true;
try {
int __jjV14 = __jjstate.la;
com.metamata.parse.Token __jjV15 = __jjstate.laToken;
boolean __jjV13 = true;
try {
__jjstate.ematch(__jjVstatic);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV13 = false;
  __jjstate.la = __jjV14;
  __jjstate.laToken = __jjV15;
}
if (!__jjV13) {
int __jjV18 = __jjstate.la;
com.metamata.parse.Token __jjV19 = __jjstate.laToken;
boolean __jjV17 = true;
try {
__jjstate.ematch(__jjVabstract);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV17 = false;
  __jjstate.la = __jjV18;
  __jjstate.laToken = __jjV19;
}
if (!__jjV17) {
int __jjV22 = __jjstate.la;
com.metamata.parse.Token __jjV23 = __jjstate.laToken;
boolean __jjV21 = true;
try {
__jjstate.ematch(__jjVfinal);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV21 = false;
  __jjstate.la = __jjV22;
  __jjstate.laToken = __jjV23;
}
if (!__jjV21) {
int __jjV26 = __jjstate.la;
com.metamata.parse.Token __jjV27 = __jjstate.laToken;
boolean __jjV25 = true;
try {
__jjstate.ematch(__jjVpublic);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV25 = false;
  __jjstate.la = __jjV26;
  __jjstate.laToken = __jjV27;
}
if (!__jjV25) {
int __jjV30 = __jjstate.la;
com.metamata.parse.Token __jjV31 = __jjstate.laToken;
boolean __jjV29 = true;
try {
__jjstate.ematch(__jjVprotected);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV29 = false;
  __jjstate.la = __jjV30;
  __jjstate.laToken = __jjV31;
}
if (!__jjV29) {
__jjstate.ematch(__jjVprivate);
}
}
}
}
}
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV11 = false;
  __jjstate.la = __jjV9;
  __jjstate.laToken = __jjV10;
}
if (!__jjV11) {
  break;
}
}
__jjstate.ematch(__jjVclass);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV32 = __jjstate.guessing;
int __jjV34 = 0;
com.metamata.parse.Token __jjV35 = null;
if (__jjV32) {
  __jjV34 = __jjstate.la;
  __jjV35 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV33 = true;
for (;;) {
  try {
	if (__jjV32 || !__jjstate.guessing) {
n = nestedInterfaceDeclaration();	} else {

for (;;) {
int __jjV37 = __jjstate.la;
com.metamata.parse.Token __jjV38 = __jjstate.laToken;
boolean __jjV39 = true;
try {
int __jjV42 = __jjstate.la;
com.metamata.parse.Token __jjV43 = __jjstate.laToken;
boolean __jjV41 = true;
try {
__jjstate.ematch(__jjVstatic);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV41 = false;
  __jjstate.la = __jjV42;
  __jjstate.laToken = __jjV43;
}
if (!__jjV41) {
int __jjV46 = __jjstate.la;
com.metamata.parse.Token __jjV47 = __jjstate.laToken;
boolean __jjV45 = true;
try {
__jjstate.ematch(__jjVabstract);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV45 = false;
  __jjstate.la = __jjV46;
  __jjstate.laToken = __jjV47;
}
if (!__jjV45) {
int __jjV50 = __jjstate.la;
com.metamata.parse.Token __jjV51 = __jjstate.laToken;
boolean __jjV49 = true;
try {
__jjstate.ematch(__jjVfinal);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV49 = false;
  __jjstate.la = __jjV50;
  __jjstate.laToken = __jjV51;
}
if (!__jjV49) {
int __jjV54 = __jjstate.la;
com.metamata.parse.Token __jjV55 = __jjstate.laToken;
boolean __jjV53 = true;
try {
__jjstate.ematch(__jjVpublic);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV53 = false;
  __jjstate.la = __jjV54;
  __jjstate.laToken = __jjV55;
}
if (!__jjV53) {
int __jjV58 = __jjstate.la;
com.metamata.parse.Token __jjV59 = __jjstate.laToken;
boolean __jjV57 = true;
try {
__jjstate.ematch(__jjVprotected);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV57 = false;
  __jjstate.la = __jjV58;
  __jjstate.laToken = __jjV59;
}
if (!__jjV57) {
__jjstate.ematch(__jjVprivate);
}
}
}
}
}
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV39 = false;
  __jjstate.la = __jjV37;
  __jjstate.laToken = __jjV38;
}
if (!__jjV39) {
  break;
}
}
__jjstate.ematch(__jjVinterface);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV32) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV33 = false;
    if (__jjV32) {
      __jjstate.la = __jjV34;
      __jjstate.laToken = __jjV35;
    }
  }
  if (__jjV32 || !__jjstate.guessing || !__jjV33) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV32;
if (!__jjV33) {
boolean __jjV60 = __jjstate.guessing;
int __jjV62 = 0;
com.metamata.parse.Token __jjV63 = null;
if (__jjV60) {
  __jjV62 = __jjstate.la;
  __jjV63 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV61 = true;
for (;;) {
  try {
	if (__jjV60 || !__jjstate.guessing) {
n = constructorDeclaration();	} else {

int __jjV66 = __jjstate.la;
com.metamata.parse.Token __jjV67 = __jjstate.laToken;
boolean __jjV64 = true;
try {
int __jjV70 = __jjstate.la;
com.metamata.parse.Token __jjV71 = __jjstate.laToken;
boolean __jjV69 = true;
try {
__jjstate.ematch(__jjVpublic);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV69 = false;
  __jjstate.la = __jjV70;
  __jjstate.laToken = __jjV71;
}
if (!__jjV69) {
int __jjV74 = __jjstate.la;
com.metamata.parse.Token __jjV75 = __jjstate.laToken;
boolean __jjV73 = true;
try {
__jjstate.ematch(__jjVprotected);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV73 = false;
  __jjstate.la = __jjV74;
  __jjstate.laToken = __jjV75;
}
if (!__jjV73) {
__jjstate.ematch(__jjVprivate);
}
}
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV64 = false;
  __jjstate.la = __jjV66;
  __jjstate.laToken = __jjV67;
}
name();__jjstate.ematch(__jjV_iB);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV60) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV61 = false;
    if (__jjV60) {
      __jjstate.la = __jjV62;
      __jjstate.laToken = __jjV63;
    }
  }
  if (__jjV60 || !__jjstate.guessing || !__jjV61) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV60;
if (!__jjV61) {
boolean __jjV76 = __jjstate.guessing;
int __jjV78 = 0;
com.metamata.parse.Token __jjV79 = null;
if (__jjV76) {
  __jjV78 = __jjstate.la;
  __jjV79 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV77 = true;
for (;;) {
  try {
	if (__jjV76 || !__jjstate.guessing) {
n = methodDeclaration();	} else {
methodDeclarationLookahead();	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV76) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV77 = false;
    if (__jjV76) {
      __jjstate.la = __jjV78;
      __jjstate.laToken = __jjV79;
    }
  }
  if (__jjV76 || !__jjstate.guessing || !__jjV77) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV76;
if (!__jjV77) {
n = fieldDeclaration();}
}
}
}
}
if (!__jjstate.guessing)
{ return n; }return null;

     }

  // This production is to determine lookahead only.
    private void methodDeclarationLookahead() 
     throws com.metamata.parse.ParseException {
	
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVpublic);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVprotected);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVprivate);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVstatic);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVabstract);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
boolean __jjV24 = __jjstate.guessing;
int __jjV26 = 0;
com.metamata.parse.Token __jjV27 = null;
if (__jjV24) {
  __jjV26 = __jjstate.la;
  __jjV27 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV25 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfinal);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV24) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV25 = false;
    if (__jjV24) {
      __jjstate.la = __jjV26;
      __jjstate.laToken = __jjV27;
    }
  }
  if (__jjV24 || !__jjstate.guessing || !__jjV25) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV24;
if (!__jjV25) {
boolean __jjV28 = __jjstate.guessing;
int __jjV30 = 0;
com.metamata.parse.Token __jjV31 = null;
if (__jjV28) {
  __jjV30 = __jjstate.la;
  __jjV31 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV29 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVnative);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV28) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV29 = false;
    if (__jjV28) {
      __jjstate.la = __jjV30;
      __jjstate.laToken = __jjV31;
    }
  }
  if (__jjV28 || !__jjstate.guessing || !__jjV29) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV28;
if (!__jjV29) {
__jjstate.ematch(__jjVsynchronized);
}
}
}
}
}
}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
resultType();__jjstate.ematch(IDENTIFIER);
__jjstate.ematch(__jjV_iB);

    }

    private InterfaceDeclaration interfaceDeclaration() 
     throws com.metamata.parse.ParseException {
	{ }InterfaceDeclaration id = null; boolean isAbstract = false; Visibility v = Visibility.DEFAULT;{ }
	
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVabstract);
if (!__jjstate.guessing)
{ isAbstract = true; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
__jjstate.ematch(__jjVpublic);
if (!__jjstate.guessing)
{ v = Visibility.PUBLIC; }}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
id = unmodifiedInterfaceDeclaration();if (!__jjstate.guessing)
{
	    id.setAbstract(isAbstract);
	    id.setVisibility(v);
	    return id;
	}return null;

    }

    private InterfaceDeclaration nestedInterfaceDeclaration() 
     throws com.metamata.parse.ParseException {
	{ }InterfaceDeclaration id = null; 
	  boolean isFinal = false; 
	  boolean isStatic = false; 
	  boolean isAbstract = false; 
	  Visibility v = Visibility.DEFAULT;{ }
	
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVstatic);
if (!__jjstate.guessing)
{ isStatic = true; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVabstract);
if (!__jjstate.guessing)
{isAbstract = true; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfinal);
if (!__jjstate.guessing)
{isFinal = true; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVpublic);
if (!__jjstate.guessing)
{v = Visibility.PUBLIC; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVprotected);
if (!__jjstate.guessing)
{v = Visibility.PROTECTED; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
__jjstate.ematch(__jjVprivate);
if (!__jjstate.guessing)
{ v = Visibility.PRIVATE; }}
}
}
}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
id = unmodifiedInterfaceDeclaration();if (!__jjstate.guessing)
{
	    id.setStatic(isStatic);
	    id.setAbstract(isAbstract);
	    id.setFinal(isFinal);
	    id.setVisibility(v);
	    return id;
	}return null;

    }

    private InterfaceDeclaration unmodifiedInterfaceDeclaration() 
     throws com.metamata.parse.ParseException {
	{ }Vector v = null; Node node = null; InterfaceDeclaration id = new InterfaceDeclaration();{ }
	__jjstate.ematch(__jjVinterface);
__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ id.setName(getToken(0).toString()); }
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVextends);
v = nameList(true);if (!__jjstate.guessing)
{ Iterator i=v.iterator(); while(i.hasNext()) { Name n = (Name)i.next(); id.addExtendedInterface(n); }}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
__jjstate.ematch(__jjV_1D);

boolean __jjV4 = __jjstate.guessing;
int __jjV5 = 0;
com.metamata.parse.Token __jjV6 = null;
for (;;) {
if (__jjV4) {
  __jjV5 = __jjstate.la;
  __jjV6 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV7 = true;
for (;;) {
  try {
node = interfaceMemberDeclaration();if (!__jjstate.guessing)
{ id.addDeclaration(node); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV7 = false;
    if (__jjV4) {
      __jjstate.la = __jjV5;
      __jjstate.laToken = __jjV6;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV7) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV7) {
  break;
}
}
__jjstate.ematch(__jjV_3D);
if (!__jjstate.guessing)
{ return id; }return null;
									  
    }

    private Node interfaceMemberDeclaration() 
     throws com.metamata.parse.ParseException {
	{ }Node n = null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
	if (__jjV0 || !__jjstate.guessing) {
n = nestedClassDeclaration();	} else {

for (;;) {
int __jjV5 = __jjstate.la;
com.metamata.parse.Token __jjV6 = __jjstate.laToken;
boolean __jjV7 = true;
try {
int __jjV10 = __jjstate.la;
com.metamata.parse.Token __jjV11 = __jjstate.laToken;
boolean __jjV9 = true;
try {
__jjstate.ematch(__jjVstatic);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV9 = false;
  __jjstate.la = __jjV10;
  __jjstate.laToken = __jjV11;
}
if (!__jjV9) {
int __jjV14 = __jjstate.la;
com.metamata.parse.Token __jjV15 = __jjstate.laToken;
boolean __jjV13 = true;
try {
__jjstate.ematch(__jjVabstract);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV13 = false;
  __jjstate.la = __jjV14;
  __jjstate.laToken = __jjV15;
}
if (!__jjV13) {
int __jjV18 = __jjstate.la;
com.metamata.parse.Token __jjV19 = __jjstate.laToken;
boolean __jjV17 = true;
try {
__jjstate.ematch(__jjVfinal);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV17 = false;
  __jjstate.la = __jjV18;
  __jjstate.laToken = __jjV19;
}
if (!__jjV17) {
int __jjV22 = __jjstate.la;
com.metamata.parse.Token __jjV23 = __jjstate.laToken;
boolean __jjV21 = true;
try {
__jjstate.ematch(__jjVpublic);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV21 = false;
  __jjstate.la = __jjV22;
  __jjstate.laToken = __jjV23;
}
if (!__jjV21) {
int __jjV26 = __jjstate.la;
com.metamata.parse.Token __jjV27 = __jjstate.laToken;
boolean __jjV25 = true;
try {
__jjstate.ematch(__jjVprotected);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV25 = false;
  __jjstate.la = __jjV26;
  __jjstate.laToken = __jjV27;
}
if (!__jjV25) {
__jjstate.ematch(__jjVprivate);
}
}
}
}
}
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV7 = false;
  __jjstate.la = __jjV5;
  __jjstate.laToken = __jjV6;
}
if (!__jjV7) {
  break;
}
}
__jjstate.ematch(__jjVclass);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV28 = __jjstate.guessing;
int __jjV30 = 0;
com.metamata.parse.Token __jjV31 = null;
if (__jjV28) {
  __jjV30 = __jjstate.la;
  __jjV31 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV29 = true;
for (;;) {
  try {
	if (__jjV28 || !__jjstate.guessing) {
n = nestedInterfaceDeclaration();	} else {

for (;;) {
int __jjV33 = __jjstate.la;
com.metamata.parse.Token __jjV34 = __jjstate.laToken;
boolean __jjV35 = true;
try {
int __jjV38 = __jjstate.la;
com.metamata.parse.Token __jjV39 = __jjstate.laToken;
boolean __jjV37 = true;
try {
__jjstate.ematch(__jjVstatic);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV37 = false;
  __jjstate.la = __jjV38;
  __jjstate.laToken = __jjV39;
}
if (!__jjV37) {
int __jjV42 = __jjstate.la;
com.metamata.parse.Token __jjV43 = __jjstate.laToken;
boolean __jjV41 = true;
try {
__jjstate.ematch(__jjVabstract);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV41 = false;
  __jjstate.la = __jjV42;
  __jjstate.laToken = __jjV43;
}
if (!__jjV41) {
int __jjV46 = __jjstate.la;
com.metamata.parse.Token __jjV47 = __jjstate.laToken;
boolean __jjV45 = true;
try {
__jjstate.ematch(__jjVfinal);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV45 = false;
  __jjstate.la = __jjV46;
  __jjstate.laToken = __jjV47;
}
if (!__jjV45) {
int __jjV50 = __jjstate.la;
com.metamata.parse.Token __jjV51 = __jjstate.laToken;
boolean __jjV49 = true;
try {
__jjstate.ematch(__jjVpublic);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV49 = false;
  __jjstate.la = __jjV50;
  __jjstate.laToken = __jjV51;
}
if (!__jjV49) {
int __jjV54 = __jjstate.la;
com.metamata.parse.Token __jjV55 = __jjstate.laToken;
boolean __jjV53 = true;
try {
__jjstate.ematch(__jjVprotected);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV53 = false;
  __jjstate.la = __jjV54;
  __jjstate.laToken = __jjV55;
}
if (!__jjV53) {
__jjstate.ematch(__jjVprivate);
}
}
}
}
}
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV35 = false;
  __jjstate.la = __jjV33;
  __jjstate.laToken = __jjV34;
}
if (!__jjV35) {
  break;
}
}
__jjstate.ematch(__jjVinterface);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV28) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV29 = false;
    if (__jjV28) {
      __jjstate.la = __jjV30;
      __jjstate.laToken = __jjV31;
    }
  }
  if (__jjV28 || !__jjstate.guessing || !__jjV29) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV28;
if (!__jjV29) {
boolean __jjV56 = __jjstate.guessing;
int __jjV58 = 0;
com.metamata.parse.Token __jjV59 = null;
if (__jjV56) {
  __jjV58 = __jjstate.la;
  __jjV59 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV57 = true;
for (;;) {
  try {
	if (__jjV56 || !__jjstate.guessing) {
n = methodDeclaration();	} else {
methodDeclarationLookahead();	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV56) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV57 = false;
    if (__jjV56) {
      __jjstate.la = __jjV58;
      __jjstate.laToken = __jjV59;
    }
  }
  if (__jjV56 || !__jjstate.guessing || !__jjV57) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV56;
if (!__jjV57) {
n = fieldDeclaration();}
}
}
if (!__jjstate.guessing)
{ return n; }return null;

    }

    private FieldDeclaration fieldDeclaration() 
     throws com.metamata.parse.ParseException {
	{ 
	    }FieldDeclaration dec = new FieldDeclaration();
	    Type type;
	    String name;
	    VariableDeclarator v;{
	}
	
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVpublic);
if (!__jjstate.guessing)
{ dec.setVisibility(Visibility.PUBLIC); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVprotected);
if (!__jjstate.guessing)
{ dec.setVisibility(Visibility.PROTECTED); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVprivate);
if (!__jjstate.guessing)
{ dec.setVisibility(Visibility.PRIVATE); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVstatic);
if (!__jjstate.guessing)
{ dec.setStatic(true); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfinal);
if (!__jjstate.guessing)
{ dec.setFinal(true); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
boolean __jjV24 = __jjstate.guessing;
int __jjV26 = 0;
com.metamata.parse.Token __jjV27 = null;
if (__jjV24) {
  __jjV26 = __jjstate.la;
  __jjV27 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV25 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVtransient);
if (!__jjstate.guessing)
{ dec.setTransient(true); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV24) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV25 = false;
    if (__jjV24) {
      __jjstate.la = __jjV26;
      __jjstate.laToken = __jjV27;
    }
  }
  if (__jjV24 || !__jjstate.guessing || !__jjV25) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV24;
if (!__jjV25) {
__jjstate.ematch(__jjVvolatile);
if (!__jjstate.guessing)
{ dec.setVolatile(true); }}
}
}
}
}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
type = type();v = variableDeclarator();if (!__jjstate.guessing)
{dec.addVariable(v); }
boolean __jjV28 = __jjstate.guessing;
int __jjV29 = 0;
com.metamata.parse.Token __jjV30 = null;
for (;;) {
if (__jjV28) {
  __jjV29 = __jjstate.la;
  __jjV30 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV31 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_mB);
v = variableDeclarator();if (!__jjstate.guessing)
{dec.addVariable(v); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV28) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV31 = false;
    if (__jjV28) {
      __jjstate.la = __jjV29;
      __jjstate.laToken = __jjV30;
    }
  }
  if (__jjV28 || !__jjstate.guessing || !__jjV31) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV28;
if (!__jjV31) {
  break;
}
}
__jjstate.ematch(__jjV_1B);
if (!__jjstate.guessing)
{
	    dec.setType(type);
	    return dec;
	}return null;

    }

    private VariableDeclarator variableDeclarator() 
     throws com.metamata.parse.ParseException {
	{ }VariableDeclarator v = new VariableDeclarator(); Node n = null;{ }
	variableDeclaratorId(v);
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_3B);
n = variableInitializer();if (!__jjstate.guessing)
{ v.setInitializer(n); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
if (!__jjstate.guessing)
{ return v; }return null;

    }

    private VariableDeclarator variableDeclaratorId(VariableDeclarator v) 
     throws com.metamata.parse.ParseException {
	{ }String name = ""; int dim = 0;{ }
	__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ name = getToken(0).toString(); }
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_1C);
__jjstate.ematch(__jjV_3C);
if (!__jjstate.guessing)
{dim++;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ v.setName(name); v.setDimension(dim); }return null;
					       
    }

    private Node variableInitializer() 
     throws com.metamata.parse.ParseException {
	{ }Node n = null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
n = arrayInitializer();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
n = expression();}
if (!__jjstate.guessing)
{ return n; }return null;

	
    }

    private Node arrayInitializer() 
     throws com.metamata.parse.ParseException {
	{ }ArrayInitializer a = new ArrayInitializer(); Node n = null;{ }
	__jjstate.ematch(__jjV_1D);

boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
n = variableInitializer();if (!__jjstate.guessing)
{ a.addInit(n); }
boolean __jjV4 = __jjstate.guessing;
int __jjV5 = 0;
com.metamata.parse.Token __jjV6 = null;
for (;;) {
if (__jjV4) {
  __jjV5 = __jjstate.la;
  __jjV6 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV7 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_mB);
n = variableInitializer();if (!__jjstate.guessing)
{a.addInit(n);}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV7 = false;
    if (__jjV4) {
      __jjstate.la = __jjV5;
      __jjstate.laToken = __jjV6;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV7) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV7) {
  break;
}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;

boolean __jjV9 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV9) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV8 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_mB);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV9) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV8 = false;
    if (__jjV9) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV9 || !__jjstate.guessing || !__jjV8) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV9;
__jjstate.ematch(__jjV_3D);
if (!__jjstate.guessing)
{ return a; }return null;

    }

    private MethodDeclaration methodDeclaration() 
     throws com.metamata.parse.ParseException {
	{ }MethodDeclaration dec = new MethodDeclaration();
	Type t = null;
	Block block = null;
	Vector v = null;{
	}
	
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVpublic);
if (!__jjstate.guessing)
{ dec.setVisibility(Visibility.PUBLIC); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVprotected);
if (!__jjstate.guessing)
{ dec.setVisibility(Visibility.PROTECTED); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVprivate);
if (!__jjstate.guessing)
{ dec.setVisibility(Visibility.PRIVATE); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVstatic);
if (!__jjstate.guessing)
{ dec.setStatic(true); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVabstract);
if (!__jjstate.guessing)
{ dec.setAbstract(true); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
boolean __jjV24 = __jjstate.guessing;
int __jjV26 = 0;
com.metamata.parse.Token __jjV27 = null;
if (__jjV24) {
  __jjV26 = __jjstate.la;
  __jjV27 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV25 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfinal);
if (!__jjstate.guessing)
{ dec.setFinal(true); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV24) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV25 = false;
    if (__jjV24) {
      __jjstate.la = __jjV26;
      __jjstate.laToken = __jjV27;
    }
  }
  if (__jjV24 || !__jjstate.guessing || !__jjV25) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV24;
if (!__jjV25) {
boolean __jjV28 = __jjstate.guessing;
int __jjV30 = 0;
com.metamata.parse.Token __jjV31 = null;
if (__jjV28) {
  __jjV30 = __jjstate.la;
  __jjV31 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV29 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVnative);
if (!__jjstate.guessing)
{ dec.setNative(true); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV28) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV29 = false;
    if (__jjV28) {
      __jjstate.la = __jjV30;
      __jjstate.laToken = __jjV31;
    }
  }
  if (__jjV28 || !__jjstate.guessing || !__jjV29) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV28;
if (!__jjV29) {
__jjstate.ematch(__jjVsynchronized);
if (!__jjstate.guessing)
{ dec.setSynchronized(true); }}
}
}
}
}
}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
t = resultType();if (!__jjstate.guessing)
{ dec.setReturnType(t); }methodDeclarator(dec);
boolean __jjV33 = __jjstate.guessing;
int __jjV34 = 0;
com.metamata.parse.Token __jjV35 = null;
if (__jjV33) {
  __jjV34 = __jjstate.la;
  __jjV35 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV32 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVthrows);
v = nameList(true);if (!__jjstate.guessing)
{ dec.setThrowList(v); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV33) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV32 = false;
    if (__jjV33) {
      __jjstate.la = __jjV34;
      __jjstate.laToken = __jjV35;
    }
  }
  if (__jjV33 || !__jjstate.guessing || !__jjV32) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV33;
boolean __jjV36 = __jjstate.guessing;
int __jjV38 = 0;
com.metamata.parse.Token __jjV39 = null;
if (__jjV36) {
  __jjV38 = __jjstate.la;
  __jjV39 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV37 = true;
for (;;) {
  try {
block = block();if (!__jjstate.guessing)
{ dec.setBlock(block); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV36) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV37 = false;
    if (__jjV36) {
      __jjstate.la = __jjV38;
      __jjstate.laToken = __jjV39;
    }
  }
  if (__jjV36 || !__jjstate.guessing || !__jjV37) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV36;
if (!__jjV37) {
__jjstate.ematch(__jjV_1B);
}
if (!__jjstate.guessing)
{
	    return dec;
	}return null;

    }

    private void methodDeclarator(MethodDeclaration dec) 
     throws com.metamata.parse.ParseException {
	{ }String name = ""; Vector v;{ }
	__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ name = getToken(0).toString(); }v = formalParameters();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_1C);
__jjstate.ematch(__jjV_3C);
if (!__jjstate.guessing)
{ debug.Debug.assert(false, "what does this do?"); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ 
	    dec.setName(name);
	    dec.addParameters(v);
	}							
    }

    private Vector formalParameters() 
     throws com.metamata.parse.ParseException {
	{ }Vector v = new Vector();
	  Parameter p;{
	}
	__jjstate.ematch(__jjV_iB);

boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
p = formalParameter();if (!__jjstate.guessing)
{v.addElement(p); }
boolean __jjV4 = __jjstate.guessing;
int __jjV5 = 0;
com.metamata.parse.Token __jjV6 = null;
for (;;) {
if (__jjV4) {
  __jjV5 = __jjstate.la;
  __jjV6 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV7 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_mB);
p = formalParameter();if (!__jjstate.guessing)
{ v.addElement(p); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV7 = false;
    if (__jjV4) {
      __jjstate.la = __jjV5;
      __jjstate.laToken = __jjV6;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV7) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV7) {
  break;
}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
__jjstate.ematch(__jjV_jB);
if (!__jjstate.guessing)
{ return v; }return null;

    }

    private Parameter formalParameter() 
     throws com.metamata.parse.ParseException {
	{ }VariableDeclarator v = new VariableDeclarator();
	  Type t;
	  Parameter p = new Parameter();{
	}
	
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfinal);
if (!__jjstate.guessing)
{ p.setFinal(true); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
t = type();variableDeclaratorId(v);if (!__jjstate.guessing)
{ 
	    p.setVariable(v);
	    p.setType(t);
	    return p; 
	}return null;

    }

    private Node constructorDeclaration() 
     throws com.metamata.parse.ParseException {
	{ }Node n; Block b = new Block(); ConstructorDeclaration con = new ConstructorDeclaration(); Vector v;{  }
	
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVpublic);
if (!__jjstate.guessing)
{con.setVisibility(Visibility.PUBLIC); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVprotected);
if (!__jjstate.guessing)
{con.setVisibility(Visibility.PROTECTED); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
__jjstate.ematch(__jjVprivate);
if (!__jjstate.guessing)
{con.setVisibility(Visibility.PRIVATE);}}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ con.setName(getToken(0).toString()); }v = formalParameters();if (!__jjstate.guessing)
{con.addParameters(v); }
boolean __jjV13 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV13) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV12 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVthrows);
nameList(false);  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV13) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV12 = false;
    if (__jjV13) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV13 || !__jjstate.guessing || !__jjV12) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV13;
__jjstate.ematch(__jjV_1D);

boolean __jjV17 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV17) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV16 = true;
for (;;) {
  try {
	if (__jjV17 || !__jjstate.guessing) {
n = explicitConstructorInvocation();if (!__jjstate.guessing)
{ b.addStatement(n); }	} else {
explicitConstructorInvocation();	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV17) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV16 = false;
    if (__jjV17) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV17 || !__jjstate.guessing || !__jjV16) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV17;

boolean __jjV20 = __jjstate.guessing;
int __jjV21 = 0;
com.metamata.parse.Token __jjV22 = null;
for (;;) {
if (__jjV20) {
  __jjV21 = __jjstate.la;
  __jjV22 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV23 = true;
for (;;) {
  try {
n = blockStatement();if (!__jjstate.guessing)
{ b.addStatement(n); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV23 = false;
    if (__jjV20) {
      __jjstate.la = __jjV21;
      __jjstate.laToken = __jjV22;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV23) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV23) {
  break;
}
}
__jjstate.ematch(__jjV_3D);
if (!__jjstate.guessing)
{ con.setBlock(b); return con; }return null;

    }

    private ConstructorInvocation explicitConstructorInvocation() 
     throws com.metamata.parse.ParseException {
	{ }ConstructorInvocation inv = null; Vector v = null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
	if (__jjV0 || !__jjstate.guessing) {
__jjstate.ematch(__jjVthis);
v = arguments();__jjstate.ematch(__jjV_1B);
if (!__jjstate.guessing)
{ inv = new ConstructorInvocation(null, v, true); }	} else {
__jjstate.ematch(__jjVthis);
arguments();__jjstate.ematch(__jjV_1B);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {

boolean __jjV5 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV5) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV4 = true;
for (;;) {
  try {
primaryExpression();__jjstate.ematch(__jjV_oB);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV5) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV4 = false;
    if (__jjV5) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV5 || !__jjstate.guessing || !__jjV4) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV5;
__jjstate.ematch(__jjVsuper);
v = arguments();if (!__jjstate.guessing)
{ inv = new ConstructorInvocation(null, v, false); }__jjstate.ematch(__jjV_1B);
}
if (!__jjstate.guessing)
{ return inv; }return null;

    }

    private Initializer initializer() 
     throws com.metamata.parse.ParseException {
	{ }Initializer i = new Initializer(); Block b = null;{ }
	
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVstatic);
if (!__jjstate.guessing)
{i.setStatic(true); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
b = block();if (!__jjstate.guessing)
{i.setBlock(b); }if (!__jjstate.guessing)
{ return i; }return null;
 
    }

  /*
   * type, name and expression syntax follows.
   */

    private Type type() 
     throws com.metamata.parse.ParseException {
	{ 
	    }String s = ""; 
	    Name n = null;
	    int d = 0;{
	}
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
s = primitiveType();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
n = name();if (!__jjstate.guessing)
{s=n.getValue(); }}

boolean __jjV4 = __jjstate.guessing;
int __jjV5 = 0;
com.metamata.parse.Token __jjV6 = null;
for (;;) {
if (__jjV4) {
  __jjV5 = __jjstate.la;
  __jjV6 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV7 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_1C);
__jjstate.ematch(__jjV_3C);
if (!__jjstate.guessing)
{ d++; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV7 = false;
    if (__jjV4) {
      __jjstate.la = __jjV5;
      __jjstate.laToken = __jjV6;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV7) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV7) {
  break;
}
}
if (!__jjstate.guessing)
{ 
	    Type t = new Type(s, d);
	    return t; 
	}return null;

    }
    private String primitiveType() 
     throws com.metamata.parse.ParseException {
	{ }String s = "";{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVboolean);
if (!__jjstate.guessing)
{ s = "boolean"; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVchar);
if (!__jjstate.guessing)
{ s= "char"; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVbyte);
if (!__jjstate.guessing)
{ s= "byte"; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVshort);
if (!__jjstate.guessing)
{ s= "short"; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVint);
if (!__jjstate.guessing)
{ s= "int"; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVlong);
if (!__jjstate.guessing)
{ s= "long"; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
boolean __jjV24 = __jjstate.guessing;
int __jjV26 = 0;
com.metamata.parse.Token __jjV27 = null;
if (__jjV24) {
  __jjV26 = __jjstate.la;
  __jjV27 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV25 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfloat);
if (!__jjstate.guessing)
{ s= "float"; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV24) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV25 = false;
    if (__jjV24) {
      __jjstate.la = __jjV26;
      __jjstate.laToken = __jjV27;
    }
  }
  if (__jjV24 || !__jjstate.guessing || !__jjV25) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV24;
if (!__jjV25) {
__jjstate.ematch(__jjVdouble);
if (!__jjstate.guessing)
{ s= "double"; }}
}
}
}
}
}
}
if (!__jjstate.guessing)
{ return s; }return null;

   }

    private Type resultType() 
     throws com.metamata.parse.ParseException {
	{ }Type t = null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVvoid);
if (!__jjstate.guessing)
{ t = new Type("void"); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
t = type();}
if (!__jjstate.guessing)
{ return t; }return null;

    }

    private Name name() 
     throws com.metamata.parse.ParseException {
	/*
	 * A lookahead of 2 is required below since "name" can be followed
	 * by a ".*" when used in the context of an "importDeclaration".
	 */
	{ }Name n = null;{ }
	__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ n = new Name(getToken(0).toString()); }
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_oB);
__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ n.addIdentifier(getToken(0).toString()); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return n; }return null;

    }

    private Vector nameList(boolean asName) 
     throws com.metamata.parse.ParseException {
	{ 
	    }Name n = null;
	    Vector v = new Vector();{
	}
	n = name();if (!__jjstate.guessing)
{ v.addElement(asName ? (Object)n : (Object)n.getValue()); }
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_mB);
n = name();if (!__jjstate.guessing)
{ v.addElement(asName ? (Object)n : (Object)n.getValue()); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return v; }return null;

    }

  /*
   * expression syntax follows.
   */
    
    private Expression expression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null;{ }
	/*
	 * This expansion has been written this way instead of:
	 *   Assignment() | conditionalExpression()
	 * for performance reasons.
	 * However, it is a weakening of the grammar for it allows the LHS of
	 * assignments to be any conditional expression whereas it can only be
	 * a primary expression.  Consider adding a semantic predicate to work
	 * around this.
	 */
	expr = conditionalExpression();
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
assignmentOperator();expression();if (!__jjstate.guessing)
{ debug.Debug.assert(false, "when do I hit this production?"); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private int assignmentOperator() 
     throws com.metamata.parse.ParseException {
	{ }int op = 0;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_3B);
if (!__jjstate.guessing)
{ op = Operator.ASSIGN;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_kB_3B);
if (!__jjstate.guessing)
{ op = Operator.TIMESASSIGN;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_pB_3B);
if (!__jjstate.guessing)
{ op = Operator.DIVIDEASSIGN;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_fB_3B);
if (!__jjstate.guessing)
{ op = Operator.MODULOASSIGN;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_lB_3B);
if (!__jjstate.guessing)
{ op = Operator.PLUSASSIGN;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_nB_3B);
if (!__jjstate.guessing)
{ op = Operator.MINUSASSIGN;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
boolean __jjV24 = __jjstate.guessing;
int __jjV26 = 0;
com.metamata.parse.Token __jjV27 = null;
if (__jjV24) {
  __jjV26 = __jjstate.la;
  __jjV27 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV25 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_2B_2B_3B);
if (!__jjstate.guessing)
{ op = Operator.SLASSIGN;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV24) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV25 = false;
    if (__jjV24) {
      __jjstate.la = __jjV26;
      __jjstate.laToken = __jjV27;
    }
  }
  if (__jjV24 || !__jjstate.guessing || !__jjV25) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV24;
if (!__jjV25) {
boolean __jjV28 = __jjstate.guessing;
int __jjV30 = 0;
com.metamata.parse.Token __jjV31 = null;
if (__jjV28) {
  __jjV30 = __jjstate.la;
  __jjV31 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV29 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_4B_4B_3B);
if (!__jjstate.guessing)
{ op = Operator.SRASSIGN; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV28) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV29 = false;
    if (__jjV28) {
      __jjstate.la = __jjV30;
      __jjstate.laToken = __jjV31;
    }
  }
  if (__jjV28 || !__jjstate.guessing || !__jjV29) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV28;
if (!__jjV29) {
boolean __jjV32 = __jjstate.guessing;
int __jjV34 = 0;
com.metamata.parse.Token __jjV35 = null;
if (__jjV32) {
  __jjV34 = __jjstate.la;
  __jjV35 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV33 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_4B_4B_4B_3B);
if (!__jjstate.guessing)
{ op = Operator.USRASSIGN;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV32) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV33 = false;
    if (__jjV32) {
      __jjstate.la = __jjV34;
      __jjstate.laToken = __jjV35;
    }
  }
  if (__jjV32 || !__jjstate.guessing || !__jjV33) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV32;
if (!__jjV33) {
boolean __jjV36 = __jjstate.guessing;
int __jjV38 = 0;
com.metamata.parse.Token __jjV39 = null;
if (__jjV36) {
  __jjV38 = __jjstate.la;
  __jjV39 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV37 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_gB_3B);
if (!__jjstate.guessing)
{op = Operator.ANDASSIGN;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV36) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV37 = false;
    if (__jjV36) {
      __jjstate.la = __jjV38;
      __jjstate.laToken = __jjV39;
    }
  }
  if (__jjV36 || !__jjstate.guessing || !__jjV37) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV36;
if (!__jjV37) {
boolean __jjV40 = __jjstate.guessing;
int __jjV42 = 0;
com.metamata.parse.Token __jjV43 = null;
if (__jjV40) {
  __jjV42 = __jjstate.la;
  __jjV43 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV41 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_4C_3B);
if (!__jjstate.guessing)
{op = Operator.XORASSIGN;}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV40) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV41 = false;
    if (__jjV40) {
      __jjstate.la = __jjV42;
      __jjstate.laToken = __jjV43;
    }
  }
  if (__jjV40 || !__jjstate.guessing || !__jjV41) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV40;
if (!__jjV41) {
__jjstate.ematch(__jjV_2D_3B);
if (!__jjstate.guessing)
{op = Operator.ORASSIGN;}}
}
}
}
}
}
}
}
}
}
}
if (!__jjstate.guessing)
{ return op; }return 0;

    }

    private Expression conditionalExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp = null; ConditionalExpression exp = null;{ }
	
	tmp = conditionalOrExpression();
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_5B);
expr = expression();if (!__jjstate.guessing)
{ exp = new ConditionalExpression();
					exp.setCondition(tmp);
					exp.setIfTrue(expr);
				       }__jjstate.ematch(__jjV_0B);
tmp = conditionalExpression();if (!__jjstate.guessing)
{ exp.setIfFalse(tmp); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
if (!__jjstate.guessing)
{ return (exp == null) ? tmp : exp; }return null;

    }
    
    private Expression conditionalOrExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp = null;{ }
	expr = conditionalAndExpression();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_2D_2D);
tmp = conditionalAndExpression();if (!__jjstate.guessing)
{ InfixExpression orexp = new InfixExpression();
	    orexp.setOperator(Operator.LOG_OR);
	    orexp.setLeft(expr); 
	    orexp.setRight(tmp);
	    expr=orexp; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression conditionalAndExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp = null;{ }
	expr = inclusiveOrExpression();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_gB_gB);
tmp = inclusiveOrExpression();if (!__jjstate.guessing)
{ InfixExpression andexp = new InfixExpression();
	    andexp.setLeft(expr); 
	    andexp.setOperator(Operator.LOG_AND);
	    andexp.setRight(tmp);
	    expr = andexp; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression inclusiveOrExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp = null;{ }
	expr = exclusiveOrExpression();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_2D);
tmp = exclusiveOrExpression();if (!__jjstate.guessing)
{ InfixExpression orexp = new InfixExpression();
					 orexp.setLeft(expr);
					 orexp.setOperator(Operator.BIT_OR);
					 orexp.setRight(tmp);
					 expr = orexp; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression exclusiveOrExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp = null;{ }
	expr = andExpression();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_4C);
tmp = andExpression();if (!__jjstate.guessing)
{ InfixExpression orexp = new InfixExpression();
				 orexp.setLeft(expr);
				 orexp.setOperator(Operator.BIT_XOR);
				 orexp.setRight(tmp);
				 expr = orexp; 
			       }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression andExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp = null;{ }
	expr = equalityExpression();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_gB);
tmp = equalityExpression();if (!__jjstate.guessing)
{
					InfixExpression exp = new InfixExpression();
					exp.setLeft(expr);
					exp.setOperator(Operator.BIT_AND);
					exp.setRight(tmp);
					expr = exp;
				    }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression equalityExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp = null; int eq = Operator.EQ;{ }
	expr = instanceOfExpression();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_3B_3B);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
__jjstate.ematch(__jjV_bB_3B);
if (!__jjstate.guessing)
{ eq = Operator.NEQ; }}
tmp = instanceOfExpression();if (!__jjstate.guessing)
{
					  InfixExpression exp = new InfixExpression();
					  exp.setLeft(expr);
					  exp.setOperator(eq);
					  exp.setRight(tmp);
					  expr = exp;
				      }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression instanceOfExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null; Type t = null;{ }
	expr = relationalExpression();
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVinstanceof);
t = type();if (!__jjstate.guessing)
{ expr = new InstanceOf(expr,t); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression relationalExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp = null; int op = Operator.LT;{ }
	expr = shiftExpression();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_2B);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_4B);
if (!__jjstate.guessing)
{ op = Operator.GT; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_2B_3B);
if (!__jjstate.guessing)
{ op = Operator.LE; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
__jjstate.ematch(__jjV_4B_3B);
if (!__jjstate.guessing)
{ op = Operator.GE; }}
}
}
tmp = shiftExpression();if (!__jjstate.guessing)
{
	    InfixExpression exp = new InfixExpression();
	    exp.setLeft(expr);
	    exp.setOperator(op);
	    exp.setRight(tmp);
	    expr = exp;
	    }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression shiftExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp = null; int op = Operator.LS;{ }
	expr = additiveExpression();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_2B_2B);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_4B_4B);
if (!__jjstate.guessing)
{op = Operator.RS; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
__jjstate.ematch(__jjV_4B_4B_4B);
if (!__jjstate.guessing)
{op=Operator.URS;}}
}
tmp = additiveExpression();if (!__jjstate.guessing)
{
					InfixExpression exp = new InfixExpression();
					exp.setLeft(expr);
					exp.setOperator(op);
					exp.setRight(tmp);
					expr = exp;
				    }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression additiveExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp=null; int op = Operator.PLUS;{ }
	expr = multiplicativeExpression();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_lB);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
__jjstate.ematch(__jjV_nB);
if (!__jjstate.guessing)
{op = Operator.MINUS; }}
tmp = multiplicativeExpression();if (!__jjstate.guessing)
{
					      InfixExpression exp = new InfixExpression();
					      exp.setLeft(expr);
					      exp.setOperator(op);
					      exp.setRight(tmp);
					      expr = exp;
					  }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression multiplicativeExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null, tmp = null; int op = Operator.MULT;{ }
	expr = unaryExpression();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_kB);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_pB);
if (!__jjstate.guessing)
{ op = Operator.DIVIDE; }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
__jjstate.ematch(__jjV_fB);
if (!__jjstate.guessing)
{ op = Operator.MOD; }}
}
tmp = unaryExpression();if (!__jjstate.guessing)
{
				     InfixExpression exp = new InfixExpression();
				     exp.setLeft(expr);
				     exp.setOperator(op);
				     exp.setRight(tmp);
				     expr = exp;
				 }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression unaryExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null; int op = Operator.PLUS;{  }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_lB);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
__jjstate.ematch(__jjV_nB);
if (!__jjstate.guessing)
{ op = Operator.MINUS; }}
expr = unaryExpression();if (!__jjstate.guessing)
{ expr = new UnaryExpression(expr, op); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
expr = preIncrementExpression();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
expr = preDecrementExpression();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
expr = unaryExpressionNotPlusMinus();}
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression preIncrementExpression() 
     throws com.metamata.parse.ParseException {
	{}Expression expr = null;{ }
	__jjstate.ematch(__jjV_lB_lB);
expr = primaryExpression();if (!__jjstate.guessing)
{ expr = new UnaryExpression(expr, Operator.INCR); }if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression preDecrementExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null;{ }
	__jjstate.ematch(__jjV_nB_nB);
expr = primaryExpression();if (!__jjstate.guessing)
{ expr = new UnaryExpression(expr, Operator.DECR); }if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private Expression unaryExpressionNotPlusMinus() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null; int op = Operator.BIT_NOT;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_4D);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
__jjstate.ematch(__jjV_bB);
if (!__jjstate.guessing)
{ op = Operator.LOG_NOT; }}
expr = unaryExpression();if (!__jjstate.guessing)
{expr = new UnaryExpression(expr, op); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
	if (__jjV8 || !__jjstate.guessing) {
expr = castExpression();	} else {
castLookahead();	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
expr = postfixExpression();}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

  // This production is to determine lookahead only.  The LOOKAHEAD
  // specifications below are not used, but they are there just to
  // indicate that we know about this.
    private void castLookahead() 
     throws com.metamata.parse.ParseException {
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_iB);
primitiveType();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
	if (__jjV4 || !__jjstate.guessing) {
__jjstate.ematch(__jjV_iB);
name();__jjstate.ematch(__jjV_1C);
__jjstate.ematch(__jjV_3C);
	} else {
__jjstate.ematch(__jjV_iB);
name();__jjstate.ematch(__jjV_1C);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
__jjstate.ematch(__jjV_iB);
name();__jjstate.ematch(__jjV_jB);
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_4D);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_bB);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_iB);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
__jjstate.ematch(IDENTIFIER);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
boolean __jjV24 = __jjstate.guessing;
int __jjV26 = 0;
com.metamata.parse.Token __jjV27 = null;
if (__jjV24) {
  __jjV26 = __jjstate.la;
  __jjV27 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV25 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVthis);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV24) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV25 = false;
    if (__jjV24) {
      __jjstate.la = __jjV26;
      __jjstate.laToken = __jjV27;
    }
  }
  if (__jjV24 || !__jjstate.guessing || !__jjV25) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV24;
if (!__jjV25) {
boolean __jjV28 = __jjstate.guessing;
int __jjV30 = 0;
com.metamata.parse.Token __jjV31 = null;
if (__jjV28) {
  __jjV30 = __jjstate.la;
  __jjV31 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV29 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVsuper);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV28) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV29 = false;
    if (__jjV28) {
      __jjstate.la = __jjV30;
      __jjstate.laToken = __jjV31;
    }
  }
  if (__jjV28 || !__jjstate.guessing || !__jjV29) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV28;
if (!__jjV29) {
boolean __jjV32 = __jjstate.guessing;
int __jjV34 = 0;
com.metamata.parse.Token __jjV35 = null;
if (__jjV32) {
  __jjV34 = __jjstate.la;
  __jjV35 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV33 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVnew);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV32) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV33 = false;
    if (__jjV32) {
      __jjstate.la = __jjV34;
      __jjstate.laToken = __jjV35;
    }
  }
  if (__jjV32 || !__jjstate.guessing || !__jjV33) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV32;
if (!__jjV33) {
literal();}
}
}
}
}
}
}
}
}

    }

    private Expression postfixExpression() 
     throws com.metamata.parse.ParseException {
	{ }Expression expr = null;{ }
	expr = primaryExpression();
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_lB_lB);
if (!__jjstate.guessing)
{expr = new PostfixExpression(expr, Operator.INCR); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
__jjstate.ematch(__jjV_nB_nB);
if (!__jjstate.guessing)
{expr = new PostfixExpression(expr, Operator.DECR); }}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
if (!__jjstate.guessing)
{ return expr; }return null;

	
    }

    private Expression castExpression() 
     throws com.metamata.parse.ParseException {
	{ }Cast c = null; Type t = null; Expression e = null;{}

	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
	if (__jjV0 || !__jjstate.guessing) {
__jjstate.ematch(__jjV_iB);
t = type();__jjstate.ematch(__jjV_jB);
e = unaryExpression();if (!__jjstate.guessing)
{ c = new Cast(); c.setType(t); c.setExpression(e); }	} else {
__jjstate.ematch(__jjV_iB);
primitiveType();	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
__jjstate.ematch(__jjV_iB);
t = type();__jjstate.ematch(__jjV_jB);
e = unaryExpressionNotPlusMinus();if (!__jjstate.guessing)
{ c = new Cast(); c.setType(t); c.setExpression(e); }}
if (!__jjstate.guessing)
{ return c; }return null;

    }

    private PrimaryExpression primaryExpression() 
     throws com.metamata.parse.ParseException {
	{ }PrimaryExpression expr = null; Node n;{}
	expr = primaryPrefix();
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
expr = primarySuffix(expr);if (!__jjstate.guessing)
{ }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return expr; }return null;

    }

    private PrimaryExpression primaryPrefix() 
     throws com.metamata.parse.ParseException {
	{ }Type t = null; PrimaryExpression n = null; String s=null; Expression e = null;{}
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
n = literal();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVthis);
if (!__jjstate.guessing)
{
	     n = new Identifier("this");
	 }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVsuper);
__jjstate.ematch(__jjV_oB);
__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{
	     n = new FieldAccess(new Identifier("super"), 
				 getToken(0).toString());
	 }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_iB);
e = expression();__jjstate.ematch(__jjV_jB);
if (!__jjstate.guessing)
{
	     n = new Grouping(e);
	 }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
n = allocationExpression();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
	if (__jjV20 || !__jjstate.guessing) {
t = resultType();__jjstate.ematch(__jjV_oB);
__jjstate.ematch(__jjVclass);
if (!__jjstate.guessing)
{
	     n=new ClassAccess(t);
	 }	} else {
resultType();__jjstate.ematch(__jjV_oB);
__jjstate.ematch(__jjVclass);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
n = name();}
}
}
}
}
}
if (!__jjstate.guessing)
{ return n; }return null;

    }

    private PrimaryExpression primarySuffix(PrimaryExpression expr) 
     throws com.metamata.parse.ParseException {
	{}Vector v = null; PrimaryExpression expr2 = null; Expression e = null;{}
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_oB);
__jjstate.ematch(__jjVthis);
if (!__jjstate.guessing)
{
	   expr2 = new FieldAccess(expr, "this"); 
	}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_oB);
allocationExpression();if (!__jjstate.guessing)
{
	   debug.Debug.assert(false, "I don't know what this production does.");
	}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_1C);
e = expression();__jjstate.ematch(__jjV_3C);
if (!__jjstate.guessing)
{ 
		expr2= new ArrayAccess(expr, e); 
	   }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_oB);
__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ 
		expr2 = new FieldAccess(expr, getToken(0).toString()); 
	   }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
v = arguments();if (!__jjstate.guessing)
{
		expr2 = new MethodInvocation(expr, v);
	   }}
}
}
}
if (!__jjstate.guessing)
{
	   return expr2; 
	}return null;


    }

    private Literal literal() 
     throws com.metamata.parse.ParseException {
	{ }Literal l = null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
__jjstate.ematch(INTEGER_LITERAL);
if (!__jjstate.guessing)
{ l = new Literal(new Type("int")); l.setValue(new Integer(getToken(0).toString())); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
__jjstate.ematch(FLOATING_POINT_LITERAL);
if (!__jjstate.guessing)
{ l = new Literal(new Type("float")); l.setValue(new Float(getToken(0).toString())); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
__jjstate.ematch(CHARACTER_LITERAL);
if (!__jjstate.guessing)
{ l = new Literal(new Type("char")); l.setValue(new Character(getToken(0).toString().charAt(1))); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(STRING_LITERAL);
if (!__jjstate.guessing)
{ l = new Literal(new Type(Type.ST_STRING)); 
	 String s = getToken(0).toString(); 
	 l.setValue(s.substring(1, s.length() - 1)); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVtrue);
if (!__jjstate.guessing)
{ l = new Literal(new Type("boolean")); l.setValue(new Boolean(true)); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfalse);
if (!__jjstate.guessing)
{ l = new Literal(new Type("boolean")); l.setValue(new Boolean(false)); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
nullLiteral();if (!__jjstate.guessing)
{ l = new Literal(new Type("null")); }}
}
}
}
}
}
if (!__jjstate.guessing)
{ return l; }return null;

    }

    private void nullLiteral() 
     throws com.metamata.parse.ParseException {
	__jjstate.ematch(__jjVnull);

    }
    
    private Vector arguments() 
     throws com.metamata.parse.ParseException {
	{ }Vector v = null;{ }
	__jjstate.ematch(__jjV_iB);

boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
v = argumentList();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
__jjstate.ematch(__jjV_jB);
if (!__jjstate.guessing)
{ return (v == null) ?  (new Vector()) : v; }return null;

    }

    private Vector argumentList() 
     throws com.metamata.parse.ParseException {
	{ }Vector v = new Vector(); Expression e=null;{ }
	e = expression();if (!__jjstate.guessing)
{v.addElement(e); }
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_mB);
e = expression();if (!__jjstate.guessing)
{v.addElement(e); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return v; }return null;

    }

    private Initialization allocationExpression() 
     throws com.metamata.parse.ParseException {
	{ }Initialization init = null; 
	Type t = null; 
	String s=null; 
	Vector v=null;
	Name n=null;{}
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVnew);
s = primitiveType();if (!__jjstate.guessing)
{init = new Initialization(); 
				 t = new Type(); 
				 init.setType(t); 
				 t.setName(s); }arrayDimsAndInits(init);if (!__jjstate.guessing)
{t.setDimension(init.getInitCount());}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
__jjstate.ematch(__jjVnew);
n = name();if (!__jjstate.guessing)
{init = new Initialization(); t = new Type(); init.setType(t); t.setName(n.getValue()); }boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
arrayDimsAndInits(init);  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
v = arguments();if (!__jjstate.guessing)
{ java.util.Iterator i=v.iterator(); 
	   while(i.hasNext()) 
	   init.addInit((Expression)i.next()); 
	 }
boolean __jjV9 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV9) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV8 = true;
for (;;) {
  try {
classBody(null);  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV9) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV8 = false;
    if (__jjV9) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV9 || !__jjstate.guessing || !__jjV8) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV9;
}
}
if (!__jjstate.guessing)
{ return init; }return null;

    }

  /*
   * The third LOOKAHEAD specification below is to parse to primarySuffix
   * if there is an expression between the "[...]".
   */
    private void arrayDimsAndInits(Initialization init) 
     throws com.metamata.parse.ParseException {
	{ }Expression e=null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
/*--- BEGIN LOOKAHEAD ---*/
if (__jjstate.guessing) {
}
/*--- END LOOKAHEAD ---*/
__jjstate.ematch(__jjV_1C);
e = expression();if (!__jjstate.guessing)
{init.addInit(e); }__jjstate.ematch(__jjV_3C);

boolean __jjV5 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
for (;;) {
if (__jjV5) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV8 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_1C);
e = expression();if (!__jjstate.guessing)
{init.addInit(e); }__jjstate.ematch(__jjV_3C);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV5) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV8 = false;
    if (__jjV5) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV5 || !__jjstate.guessing || !__jjV8) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV5;
if (!__jjV8) {
  break;
}
}

boolean __jjV9 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
for (;;) {
if (__jjV9) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV12 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_1C);
__jjstate.ematch(__jjV_3C);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV9) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV12 = false;
    if (__jjV9) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV9 || !__jjstate.guessing || !__jjV12) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV9;
if (!__jjV12) {
  break;
}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
__jjstate.ematch(__jjV_1C);
__jjstate.ematch(__jjV_3C);

boolean __jjV13 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
for (;;) {
if (__jjV13) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV16 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_1C);
__jjstate.ematch(__jjV_3C);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV13) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV16 = false;
    if (__jjV13) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV13 || !__jjstate.guessing || !__jjV16) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV13;
if (!__jjV16) {
  break;
}
}
arrayInitializer();}

    }

  /*
   * statement syntax follows.
   */

    private Statement statement() 
     throws com.metamata.parse.ParseException {
	{ }Statement n = null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 1;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
n = labeledStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
n = block();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
n = emptyStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
n = statementExpression(true);__jjstate.ematch(__jjV_1B);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
n = switchStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
boolean __jjV20 = __jjstate.guessing;
int __jjV22 = 0;
com.metamata.parse.Token __jjV23 = null;
if (__jjV20) {
  __jjV22 = __jjstate.la;
  __jjV23 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV21 = true;
for (;;) {
  try {
n = ifStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV20) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV21 = false;
    if (__jjV20) {
      __jjstate.la = __jjV22;
      __jjstate.laToken = __jjV23;
    }
  }
  if (__jjV20 || !__jjstate.guessing || !__jjV21) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV20;
if (!__jjV21) {
boolean __jjV24 = __jjstate.guessing;
int __jjV26 = 0;
com.metamata.parse.Token __jjV27 = null;
if (__jjV24) {
  __jjV26 = __jjstate.la;
  __jjV27 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV25 = true;
for (;;) {
  try {
n = whileStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV24) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV25 = false;
    if (__jjV24) {
      __jjstate.la = __jjV26;
      __jjstate.laToken = __jjV27;
    }
  }
  if (__jjV24 || !__jjstate.guessing || !__jjV25) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV24;
if (!__jjV25) {
boolean __jjV28 = __jjstate.guessing;
int __jjV30 = 0;
com.metamata.parse.Token __jjV31 = null;
if (__jjV28) {
  __jjV30 = __jjstate.la;
  __jjV31 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV29 = true;
for (;;) {
  try {
n = doStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV28) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV29 = false;
    if (__jjV28) {
      __jjstate.la = __jjV30;
      __jjstate.laToken = __jjV31;
    }
  }
  if (__jjV28 || !__jjstate.guessing || !__jjV29) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV28;
if (!__jjV29) {
boolean __jjV32 = __jjstate.guessing;
int __jjV34 = 0;
com.metamata.parse.Token __jjV35 = null;
if (__jjV32) {
  __jjV34 = __jjstate.la;
  __jjV35 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV33 = true;
for (;;) {
  try {
n = forStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV32) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV33 = false;
    if (__jjV32) {
      __jjstate.la = __jjV34;
      __jjstate.laToken = __jjV35;
    }
  }
  if (__jjV32 || !__jjstate.guessing || !__jjV33) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV32;
if (!__jjV33) {
boolean __jjV36 = __jjstate.guessing;
int __jjV38 = 0;
com.metamata.parse.Token __jjV39 = null;
if (__jjV36) {
  __jjV38 = __jjstate.la;
  __jjV39 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV37 = true;
for (;;) {
  try {
n = breakStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV36) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV37 = false;
    if (__jjV36) {
      __jjstate.la = __jjV38;
      __jjstate.laToken = __jjV39;
    }
  }
  if (__jjV36 || !__jjstate.guessing || !__jjV37) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV36;
if (!__jjV37) {
boolean __jjV40 = __jjstate.guessing;
int __jjV42 = 0;
com.metamata.parse.Token __jjV43 = null;
if (__jjV40) {
  __jjV42 = __jjstate.la;
  __jjV43 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV41 = true;
for (;;) {
  try {
n = continueStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV40) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV41 = false;
    if (__jjV40) {
      __jjstate.la = __jjV42;
      __jjstate.laToken = __jjV43;
    }
  }
  if (__jjV40 || !__jjstate.guessing || !__jjV41) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV40;
if (!__jjV41) {
boolean __jjV44 = __jjstate.guessing;
int __jjV46 = 0;
com.metamata.parse.Token __jjV47 = null;
if (__jjV44) {
  __jjV46 = __jjstate.la;
  __jjV47 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV45 = true;
for (;;) {
  try {
n = returnStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV44) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV45 = false;
    if (__jjV44) {
      __jjstate.la = __jjV46;
      __jjstate.laToken = __jjV47;
    }
  }
  if (__jjV44 || !__jjstate.guessing || !__jjV45) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV44;
if (!__jjV45) {
boolean __jjV48 = __jjstate.guessing;
int __jjV50 = 0;
com.metamata.parse.Token __jjV51 = null;
if (__jjV48) {
  __jjV50 = __jjstate.la;
  __jjV51 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV49 = true;
for (;;) {
  try {
n = throwStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV48) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV49 = false;
    if (__jjV48) {
      __jjstate.la = __jjV50;
      __jjstate.laToken = __jjV51;
    }
  }
  if (__jjV48 || !__jjstate.guessing || !__jjV49) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV48;
if (!__jjV49) {
boolean __jjV52 = __jjstate.guessing;
int __jjV54 = 0;
com.metamata.parse.Token __jjV55 = null;
if (__jjV52) {
  __jjV54 = __jjstate.la;
  __jjV55 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV53 = true;
for (;;) {
  try {
n = synchronizedStatement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV52) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV53 = false;
    if (__jjV52) {
      __jjstate.la = __jjV54;
      __jjstate.laToken = __jjV55;
    }
  }
  if (__jjV52 || !__jjstate.guessing || !__jjV53) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV52;
if (!__jjV53) {
n = tryStatement();}
}
}
}
}
}
}
}
}
}
}
}
}
}
if (!__jjstate.guessing)
{ return n; }return null;

    }

    private LabeledStatement  labeledStatement() 
     throws com.metamata.parse.ParseException {

	{ 
	    }LabeledStatement st = new LabeledStatement(); 
	    Statement st1;{ 
	}
	__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ String s = getToken(0).toString(); st.setStatementLabel(s); }__jjstate.ematch(__jjV_0B);
st1 = statement();if (!__jjstate.guessing)
{st.setStatement(st1); }if (!__jjstate.guessing)
{ return st; }return null;
									    
    }

    private Block block() 
     throws com.metamata.parse.ParseException {
	{ }Block b = new Block(); Node n;{   }
	__jjstate.ematch(__jjV_1D);

boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
n = blockStatement();if (!__jjstate.guessing)
{  if(n != null) { b.addStatement(n);} }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
__jjstate.ematch(__jjV_3D);
if (!__jjstate.guessing)
{ return b; }return null;

    }

    private Node blockStatement() 
     throws com.metamata.parse.ParseException {
	{ }Node n = null; LocalVariableDeclaration d = null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
	if (__jjV0 || !__jjstate.guessing) {
d = localVariableDeclaration();if (!__jjstate.guessing)
{ n = d; d.setAsStatement(true); }__jjstate.ematch(__jjV_1B);
	} else {

int __jjV6 = __jjstate.la;
com.metamata.parse.Token __jjV7 = __jjstate.laToken;
boolean __jjV4 = true;
try {
__jjstate.ematch(__jjVfinal);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV4 = false;
  __jjstate.la = __jjV6;
  __jjstate.laToken = __jjV7;
}
type();__jjstate.ematch(IDENTIFIER);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV8 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV8) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV9 = true;
for (;;) {
  try {
n = statement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV8) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV9 = false;
    if (__jjV8) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV8 || !__jjstate.guessing || !__jjV9) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV8;
if (!__jjV9) {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
n = unmodifiedClassDeclaration();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
n = unmodifiedInterfaceDeclaration();}
}
}
if (!__jjstate.guessing)
{ return n; }return null;

    }

    private LocalVariableDeclaration localVariableDeclaration() 
     throws com.metamata.parse.ParseException {
	{ 
	    }LocalVariableDeclaration dec = new LocalVariableDeclaration(); 
	    Type t;
	    VariableDeclarator v;{
	}
	
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfinal);
if (!__jjstate.guessing)
{ dec.setFinal(true); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
t = type();if (!__jjstate.guessing)
{dec.setType(t); }v = variableDeclarator();if (!__jjstate.guessing)
{dec.addVariable(v);}
boolean __jjV4 = __jjstate.guessing;
int __jjV5 = 0;
com.metamata.parse.Token __jjV6 = null;
for (;;) {
if (__jjV4) {
  __jjV5 = __jjstate.la;
  __jjV6 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV7 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_mB);
v = variableDeclarator();if (!__jjstate.guessing)
{dec.addVariable(v); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV7 = false;
    if (__jjV4) {
      __jjstate.la = __jjV5;
      __jjstate.laToken = __jjV6;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV7) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV7) {
  break;
}
}
if (!__jjstate.guessing)
{
	    return dec;
	}return null;

    }

    private EmptyStatement emptyStatement() 
     throws com.metamata.parse.ParseException {
	__jjstate.ematch(__jjV_1B);
if (!__jjstate.guessing)
{ return new EmptyStatement(); }return null;

    }

    private StatementExpression statementExpression(boolean asStatement) 
     throws com.metamata.parse.ParseException {
	{ }StatementExpression s = new StatementExpression(asStatement); Expression e = null; Expression e2 = null; int op = 0;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
e = preIncrementExpression();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
boolean __jjV4 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV4) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV5 = true;
for (;;) {
  try {
e = preDecrementExpression();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV5 = false;
    if (__jjV4) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV5) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV5) {
e = primaryExpression();
boolean __jjV9 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV9) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV8 = true;
for (;;) {
  try {
boolean __jjV12 = __jjstate.guessing;
int __jjV14 = 0;
com.metamata.parse.Token __jjV15 = null;
if (__jjV12) {
  __jjV14 = __jjstate.la;
  __jjV15 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV13 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_lB_lB);
if (!__jjstate.guessing)
{ e = new PostfixExpression(e, Operator.INCR); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV12) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV13 = false;
    if (__jjV12) {
      __jjstate.la = __jjV14;
      __jjstate.laToken = __jjV15;
    }
  }
  if (__jjV12 || !__jjstate.guessing || !__jjV13) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV12;
if (!__jjV13) {
boolean __jjV16 = __jjstate.guessing;
int __jjV18 = 0;
com.metamata.parse.Token __jjV19 = null;
if (__jjV16) {
  __jjV18 = __jjstate.la;
  __jjV19 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV17 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_nB_nB);
if (!__jjstate.guessing)
{ e = new PostfixExpression(e, Operator.DECR); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV16) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV17 = false;
    if (__jjV16) {
      __jjstate.la = __jjV18;
      __jjstate.laToken = __jjV19;
    }
  }
  if (__jjV16 || !__jjstate.guessing || !__jjV17) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV16;
if (!__jjV17) {
op = assignmentOperator();e2 = expression();if (!__jjstate.guessing)
{debug.Debug.assert(e2 != null, e.toString() + " " + Operator.asString(op));  e = new InfixExpression(e, op, e2); }}
}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV9) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV8 = false;
    if (__jjV9) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV9 || !__jjstate.guessing || !__jjV8) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV9;
}
}
if (!__jjstate.guessing)
{ s.setExpression(e); return s; }return null;

  }

    private Switch switchStatement() 
     throws com.metamata.parse.ParseException {
	{ }Switch sw = new Switch(); 
	Expression e = null; 
	Switch.Case ca = null;
	Node s = null;{ }
	__jjstate.ematch(__jjVswitch);
__jjstate.ematch(__jjV_iB);
e = expression();if (!__jjstate.guessing)
{sw.setSwitch(e); }__jjstate.ematch(__jjV_jB);
__jjstate.ematch(__jjV_1D);

boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
ca = switchLabel();
boolean __jjV4 = __jjstate.guessing;
int __jjV5 = 0;
com.metamata.parse.Token __jjV6 = null;
for (;;) {
if (__jjV4) {
  __jjV5 = __jjstate.la;
  __jjV6 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV7 = true;
for (;;) {
  try {
s = blockStatement();if (!__jjstate.guessing)
{ca.addStatement(s);  }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV4) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV7 = false;
    if (__jjV4) {
      __jjstate.la = __jjV5;
      __jjstate.laToken = __jjV6;
    }
  }
  if (__jjV4 || !__jjstate.guessing || !__jjV7) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV4;
if (!__jjV7) {
  break;
}
}
if (!__jjstate.guessing)
{sw.addCase(ca); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
__jjstate.ematch(__jjV_3D);
if (!__jjstate.guessing)
{ return sw; }return null;

    }

    private Switch.Case switchLabel() 
     throws com.metamata.parse.ParseException {
	{ }Switch.Case ca = new Switch.Case(); Expression e = null;{ }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVcase);
e = expression();__jjstate.ematch(__jjV_0B);
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
__jjstate.ematch(__jjVdefault);
__jjstate.ematch(__jjV_0B);
}
if (!__jjstate.guessing)
{ ca.setCaseLabel(e); return ca; }return null;

    }

    private IfStatement ifStatement() 
     throws com.metamata.parse.ParseException {
	{ }IfStatement s = new IfStatement(); Expression e = null; Statement s1 = null; Statement s2 = null;{ }
	__jjstate.ematch(__jjVif);
__jjstate.ematch(__jjV_iB);
e = expression();__jjstate.ematch(__jjV_jB);
s1 = statement();
boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVelse);
s2 = statement();  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
if (!__jjstate.guessing)
{ s.setExpression(e); s.setThen(s1); s.setElse(s2); return s; }return null;

    }

    private WhileStatement whileStatement() 
     throws com.metamata.parse.ParseException {
	{ }WhileStatement w = new WhileStatement(); Expression e = null; Statement s = null;{ }
	__jjstate.ematch(__jjVwhile);
__jjstate.ematch(__jjV_iB);
e = expression();if (!__jjstate.guessing)
{ w.setExpression(e); }__jjstate.ematch(__jjV_jB);
s = statement();if (!__jjstate.guessing)
{ w.setStatement(s); }if (!__jjstate.guessing)
{ return w; }return null;

    }

    private WhileStatement doStatement() 
     throws com.metamata.parse.ParseException {
	{ }WhileStatement w = new WhileStatement(true); Expression e= null; Statement s=null;{ }
	__jjstate.ematch(__jjVdo);
s = statement();if (!__jjstate.guessing)
{ w.setStatement(s); }__jjstate.ematch(__jjVwhile);
__jjstate.ematch(__jjV_iB);
e = expression();if (!__jjstate.guessing)
{ w.setExpression(e); }__jjstate.ematch(__jjV_jB);
__jjstate.ematch(__jjV_1B);
if (!__jjstate.guessing)
{ return w; }return null;
												     
    }

    private ForStatement forStatement() 
     throws com.metamata.parse.ParseException {
	{ }ForStatement fs = new ForStatement(); Statement s = null; Expression e = null;{ }
	__jjstate.ematch(__jjVfor);
__jjstate.ematch(__jjV_iB);

boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
forInit(fs);  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
__jjstate.ematch(__jjV_1B);

boolean __jjV5 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV5) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV4 = true;
for (;;) {
  try {
e = expression();if (!__jjstate.guessing)
{ fs.setConditional(e); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV5) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV4 = false;
    if (__jjV5) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV5 || !__jjstate.guessing || !__jjV4) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV5;
__jjstate.ematch(__jjV_1B);

boolean __jjV9 = __jjstate.guessing;
int __jjV10 = 0;
com.metamata.parse.Token __jjV11 = null;
if (__jjV9) {
  __jjV10 = __jjstate.la;
  __jjV11 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV8 = true;
for (;;) {
  try {
forUpdate(fs);  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV9) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV8 = false;
    if (__jjV9) {
      __jjstate.la = __jjV10;
      __jjstate.laToken = __jjV11;
    }
  }
  if (__jjV9 || !__jjstate.guessing || !__jjV8) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV9;
__jjstate.ematch(__jjV_jB);
s = statement();if (!__jjstate.guessing)
{ fs.setStatement(s); }if (!__jjstate.guessing)
{ return fs; }return null;

    }

    private void forInit(ForStatement fs) 
     throws com.metamata.parse.ParseException {
	{ }LocalVariableDeclaration dec = null; Vector v = null;{  }
	boolean __jjV0 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV0) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 2147483646;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV1 = true;
for (;;) {
  try {
	if (__jjV0 || !__jjstate.guessing) {
dec = localVariableDeclaration();if (!__jjstate.guessing)
{ fs.setInitializer(dec); }	} else {

int __jjV6 = __jjstate.la;
com.metamata.parse.Token __jjV7 = __jjstate.laToken;
boolean __jjV4 = true;
try {
__jjstate.ematch(__jjVfinal);
} catch (com.metamata.parse.GuessFail __jjGC) {
  __jjV4 = false;
  __jjstate.la = __jjV6;
  __jjstate.laToken = __jjV7;
}
type();__jjstate.ematch(IDENTIFIER);
	}
  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV1 = false;
    if (__jjV0) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV1) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV1) {
v = statementExpressionList();if (!__jjstate.guessing)
{ Iterator i = v.iterator(); while(i.hasNext()) fs.addInit((StatementExpression)i.next()); }}

    }

    private Vector statementExpressionList() 
     throws com.metamata.parse.ParseException {
	{ }Vector v = new Vector(); StatementExpression s = null;{  }
	s = statementExpression(false);if (!__jjstate.guessing)
{v.addElement(s); }
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjV_mB);
s = statementExpression(false);if (!__jjstate.guessing)
{v.addElement(s);}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}
if (!__jjstate.guessing)
{ return v; }return null;
						    
    }

    private void forUpdate(ForStatement fs) 
     throws com.metamata.parse.ParseException {
	{ }Vector v = null;{ }
	v = statementExpressionList();if (!__jjstate.guessing)
{ Iterator i = v.iterator(); while(i.hasNext()) fs.addUpdate((StatementExpression)i.next()); }
    }

    private BreakStatement breakStatement() 
     throws com.metamata.parse.ParseException {
	{ }BreakStatement b = new BreakStatement();{ }
	__jjstate.ematch(__jjVbreak);

boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ String s = getToken(0).toString(); b.setBreakLabel(s); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
__jjstate.ematch(__jjV_1B);
if (!__jjstate.guessing)
{ return b; }return null;

    }

    private ContinueStatement continueStatement() 
     throws com.metamata.parse.ParseException {
	{ }ContinueStatement b = new ContinueStatement();{ }
	__jjstate.ematch(__jjVcontinue);

boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
__jjstate.ematch(IDENTIFIER);
if (!__jjstate.guessing)
{ String s= getToken(0).toString(); b.setContinueLabel(s); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
__jjstate.ematch(__jjV_1B);
if (!__jjstate.guessing)
{ return b; }return null;
											       
    }

    private ReturnStatement returnStatement() 
     throws com.metamata.parse.ParseException {
	{ }Expression e = null; ReturnStatement r = new ReturnStatement();{ }
	__jjstate.ematch(__jjVreturn);

boolean __jjV1 = __jjstate.guessing;
int __jjV2 = 0;
com.metamata.parse.Token __jjV3 = null;
if (__jjV1) {
  __jjV2 = __jjstate.la;
  __jjV3 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV0 = true;
for (;;) {
  try {
e = expression();if (!__jjstate.guessing)
{r.setExpression(e);}  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV1) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV0 = false;
    if (__jjV1) {
      __jjstate.la = __jjV2;
      __jjstate.laToken = __jjV3;
    }
  }
  if (__jjV1 || !__jjstate.guessing || !__jjV0) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV1;
__jjstate.ematch(__jjV_1B);
if (!__jjstate.guessing)
{ return r; }return null;
							  
    }

    private ThrowStatement throwStatement() 
     throws com.metamata.parse.ParseException {
	{ }ThrowStatement s = new ThrowStatement(); Expression e = null;{ }
	__jjstate.ematch(__jjVthrow);
e = expression();__jjstate.ematch(__jjV_1B);
if (!__jjstate.guessing)
{ s.setExpression(e); return s; }return null;

    }

    private SynchronizedBlock synchronizedStatement() 
     throws com.metamata.parse.ParseException {
	
	{ }SynchronizedBlock s = new SynchronizedBlock(); Expression e = null; Block b = null;{ }
	__jjstate.ematch(__jjVsynchronized);
__jjstate.ematch(__jjV_iB);
e = expression();__jjstate.ematch(__jjV_jB);
b = block();if (!__jjstate.guessing)
{ s.setExpression(e); s.setBlock(b); return s; }return null;

    }

    private TryBlock tryStatement() 
     throws com.metamata.parse.ParseException {
	{ 
	    }TryBlock tryb = new TryBlock(); 
	    TryBlock.CatchBlock cb = null; 
	    Parameter p = null;
	    Block b = null;{ }

	__jjstate.ematch(__jjVtry);
b = block();if (!__jjstate.guessing)
{ tryb.setTry(b); }
boolean __jjV0 = __jjstate.guessing;
int __jjV1 = 0;
com.metamata.parse.Token __jjV2 = null;
for (;;) {
if (__jjV0) {
  __jjV1 = __jjstate.la;
  __jjV2 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV3 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVcatch);
__jjstate.ematch(__jjV_iB);
p = formalParameter();__jjstate.ematch(__jjV_jB);
b = block();if (!__jjstate.guessing)
{ cb = new TryBlock.CatchBlock(); cb.setParameter(p); cb.setBlock(b); tryb.addCatch(cb); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV0) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV3 = false;
    if (__jjV0) {
      __jjstate.la = __jjV1;
      __jjstate.laToken = __jjV2;
    }
  }
  if (__jjV0 || !__jjstate.guessing || !__jjV3) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV0;
if (!__jjV3) {
  break;
}
}

boolean __jjV5 = __jjstate.guessing;
int __jjV6 = 0;
com.metamata.parse.Token __jjV7 = null;
if (__jjV5) {
  __jjV6 = __jjstate.la;
  __jjV7 = __jjstate.laToken;
} else {
  __jjstate.guessing = true;
  __jjstate.lamax = 0;
  __jjstate.la = 0;
  __jjstate.laToken = __jjstate.token;
}
boolean __jjV4 = true;
for (;;) {
  try {
__jjstate.ematch(__jjVfinally);
b = block();if (!__jjstate.guessing)
{ tryb.setFinally(b); }  } catch (com.metamata.parse.GuessSucc __jjGC) {
    if (__jjV5) {
      throw __jjGC;
    }
  } catch (com.metamata.parse.GuessFail __jjGC) {
    __jjV4 = false;
    if (__jjV5) {
      __jjstate.la = __jjV6;
      __jjstate.laToken = __jjV7;
    }
  }
  if (__jjV5 || !__jjstate.guessing || !__jjV4) {
    break;
  }
  __jjstate.guessing = false;
}
__jjstate.guessing = __jjV5;
if (!__jjstate.guessing)
{ return tryb; }return null;

    }


}
