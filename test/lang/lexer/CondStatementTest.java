package lang.lexer;

import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import junit.framework.TestCase;

/**
 * @author apleshkov
 */
public class CondStatementTest extends StatementTest {

    public void testIf() throws Exception {
        testStatementTokenization(
                "<!--IF 1-->",
                TTokenTypes.C_IF,
                TTokenTypes.WHITESPACE,
                TTokenTypes.NUMBER
        );

        testStatementTokenization(
                "<!--IF a-->",
                TTokenTypes.C_IF,
                TTokenTypes.WHITESPACE,
                TTokenTypes.VAR_NAME
        );

        testStatementTokenization(
                "<!--IF !a-->",
                TTokenTypes.C_IF,
                TTokenTypes.WHITESPACE,
                TTokenTypes.OP_NOT,
                TTokenTypes.VAR_NAME
        );

        testStatementTokenization(
                "<!--IF defined(a)-->",
                TTokenTypes.C_IF,
                TTokenTypes.WHITESPACE,
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.RPAREN
        );

        testStatementTokenization(
                "<!--IF a( 1 )-->",
                TTokenTypes.C_IF,
                TTokenTypes.WHITESPACE,
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.WHITESPACE,
                TTokenTypes.NUMBER,
                TTokenTypes.WHITESPACE,
                TTokenTypes.RPAREN
        );

        testStatementTokenization(
                "<!--IF defined(a)&&b(2)-->",
                TTokenTypes.C_IF,
                TTokenTypes.WHITESPACE,
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.RPAREN,
                TTokenTypes.OP_AND,
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.NUMBER,
                TTokenTypes.RPAREN
        );

        testStatementTokenization(
                "<!--IF defined(a) && !b || c(3)-->",
                TTokenTypes.C_IF,
                TTokenTypes.WHITESPACE,
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.RPAREN,
                TTokenTypes.WHITESPACE,
                TTokenTypes.OP_AND,
                TTokenTypes.WHITESPACE,
                TTokenTypes.OP_NOT,
                TTokenTypes.VAR_NAME,
                TTokenTypes.WHITESPACE,
                TTokenTypes.OP_OR,
                TTokenTypes.WHITESPACE,
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.NUMBER,
                TTokenTypes.RPAREN
        );

        testStatementTokenization(
                "<!--IF ##A##-->",
                TTokenTypes.C_IF,
                TTokenTypes.WHITESPACE,
                TTokenTypes.DSHARP,
                TTokenTypes.VAR_NAME,
                TTokenTypes.DSHARP
        );
    }

    public void testSimpleOpening() throws Exception {
        testStatementTokenization(
                "<!--IF-->",
                TTokenTypes.C_IF
        );

        testStatementTokenization(
                "<!--IFNOT-->",
                TTokenTypes.C_IFNOT
        );

        testStatementTokenization(
                "<!--IFDEF-->",
                TTokenTypes.C_IFDEF
        );

        testStatementTokenization(
                "<!--IFNOTDEF-->",
                TTokenTypes.C_IFNOTDEF
        );
    }

    public void testSimpleClosing() throws Exception {
        testStatementTokenization(
                "<!--/IF-->",
                TTokenTypes.C_ENDIF
        );

        testStatementTokenization(
                "<!--/IFNOT-->",
                TTokenTypes.C_ENDIFNOT
        );

        testStatementTokenization(
                "<!--/IFDEF-->",
                TTokenTypes.C_ENDIFDEF
        );

        testStatementTokenization(
                "<!--/IFNOTDEF-->",
                TTokenTypes.C_ENDIFNOTDEF
        );
    }

    public void testSimpleElse() throws Exception {
        testStatementTokenization(
                "<!--ELSE-->",
                TTokenTypes.C_ELSE
        );

        testStatementTokenization(
                "<!--ELSEIF-->",
                TTokenTypes.C_ELSEIF
        );

        testStatementTokenization(
                "<!--ELSEIFNOT-->",
                TTokenTypes.C_ELSEIFNOT
        );

        testStatementTokenization(
                "<!--ELSEIFDEF-->",
                TTokenTypes.C_ELSEIFDEF
        );

        testStatementTokenization(
                "<!--ELSEIFNOTDEF-->",
                TTokenTypes.C_ELSEIFNOTDEF
        );
    }
}
