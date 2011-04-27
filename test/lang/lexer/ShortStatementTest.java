package lang.lexer;

import com.intellij.psi.tree.IElementType;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import lang.tools.TLexerTest;


/**
 * @author apleshkov
 */
public class ShortStatementTest extends TLexerTest {

    public void testVariable() throws Exception {
        testShortStatementTokenization("##myvar##", TTokenTypes.VAR_NAME);

        testTokenization("##myvar##\n", TTokenTypes.DSHARP, TTokenTypes.VAR_NAME, TTokenTypes.DSHARP, TTokenTypes.HTML);

        testShortStatementTokenization("##0myvar##", TTokenTypes.UNKNOWN_SYMBOL);

        testTokenization(
                "##myvar##foo",
                TTokenTypes.DSHARP,
                TTokenTypes.VAR_NAME,
                TTokenTypes.DSHARP,
                TTokenTypes.HTML
        );

        testTokenization(
                "##var1####var2##",
                TTokenTypes.DSHARP,
                TTokenTypes.VAR_NAME,
                TTokenTypes.DSHARP,
                TTokenTypes.DSHARP,
                TTokenTypes.VAR_NAME,
                TTokenTypes.DSHARP
        );

        testTokenization(
                "##var1##var2##var3##",
                TTokenTypes.DSHARP,
                TTokenTypes.VAR_NAME,
                TTokenTypes.DSHARP,
                TTokenTypes.HTML,
                TTokenTypes.DSHARP,
                TTokenTypes.VAR_NAME,
                TTokenTypes.DSHARP
        );

        testShortStatementTokenization(
                "##myns/myvar##",
                TTokenTypes.VAR_NAME,
                TTokenTypes.UNKNOWN_SYMBOL,
                TTokenTypes.VAR_NAME
        );
    }

    public void testFunctionCall() throws Exception {
        testShortStatementTokenization(
                "##myfunc()##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##0func()##",
                TTokenTypes.UNKNOWN_SYMBOL,
                TTokenTypes.LPAREN,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myfunc(A)##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myfunc(A,1)##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.OP_COMMA,
                TTokenTypes.NUMBER,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myfunc(A+1,##B##+3)##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.OP_PLUS,
                TTokenTypes.NUMBER,
                TTokenTypes.OP_COMMA,
                TTokenTypes.VAR_VALUE,
                TTokenTypes.OP_PLUS,
                TTokenTypes.NUMBER,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myfunc('abc',3*2)##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.UNKNOWN_SYMBOL,
                TTokenTypes.VAR_NAME,
                TTokenTypes.UNKNOWN_SYMBOL,
                TTokenTypes.OP_COMMA,
                TTokenTypes.NUMBER,
                TTokenTypes.UNKNOWN_SYMBOL,
                TTokenTypes.NUMBER,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myns/myfunc(1)##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.NUMBER,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myfunc( 1 )##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.WHITESPACE,
                TTokenTypes.NUMBER,
                TTokenTypes.WHITESPACE,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myfunc(1, A)##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.NUMBER,
                TTokenTypes.OP_COMMA,
                TTokenTypes.WHITESPACE,
                TTokenTypes.VAR_NAME,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myfunc(12abc,A )##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.UNKNOWN_SYMBOL,
                TTokenTypes.OP_COMMA,
                TTokenTypes.VAR_NAME,
                TTokenTypes.WHITESPACE,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myfunc(##A\\(1\\)##)##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.DSHARP,
                TTokenTypes.NESTED_FUNC_NAME,
                TTokenTypes.QLPAREN,
                TTokenTypes.NUMBER,
                TTokenTypes.QRPAREN,
                TTokenTypes.DSHARP,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myfunc(##B/A\\(##var1##\\)##)##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.DSHARP,
                TTokenTypes.NESTED_FUNC_NAME,
                TTokenTypes.QLPAREN,
                TTokenTypes.VAR_VALUE,
                TTokenTypes.QRPAREN,
                TTokenTypes.DSHARP,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##myfunc(qq##A\\(1\\)##)##",
                TTokenTypes.FUNC_NAME,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.DSHARP,
                TTokenTypes.NESTED_FUNC_NAME,
                TTokenTypes.QLPAREN,
                TTokenTypes.NUMBER,
                TTokenTypes.QRPAREN,
                TTokenTypes.DSHARP,
                TTokenTypes.RPAREN
        );
    }

    public void testSetVars() throws Exception {
        testShortStatementTokenization(
                "##SetVars(var=##var2##)##",
                TTokenTypes.SETVARS,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.OP_ASSIGN,
                TTokenTypes.VAR_VALUE,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##SetVars(var=1)##",
                TTokenTypes.SETVARS,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.OP_ASSIGN,
                TTokenTypes.NUMBER,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##SetVars(var = 1)##",
                TTokenTypes.SETVARS,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.WHITESPACE,
                TTokenTypes.OP_ASSIGN,
                TTokenTypes.WHITESPACE,
                TTokenTypes.NUMBER,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##SetVars(var=2*3)##",
                TTokenTypes.SETVARS,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.OP_ASSIGN,
                TTokenTypes.NUMBER,
                TTokenTypes.UNKNOWN_SYMBOL,
                TTokenTypes.NUMBER,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##SetVars(var=qq##var2##)##",
                TTokenTypes.SETVARS,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.OP_ASSIGN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.VAR_VALUE,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##SetVars(var=qq##var2####func\\(A\\)##)##",
                TTokenTypes.SETVARS,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.OP_ASSIGN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.VAR_VALUE,
                TTokenTypes.DSHARP,
                TTokenTypes.NESTED_FUNC_NAME,
                TTokenTypes.QLPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.QRPAREN,
                TTokenTypes.DSHARP,
                TTokenTypes.RPAREN
        );

        testShortStatementTokenization(
                "##SetVars(var=qq##var2####myns/func\\(A\\)##)##",
                TTokenTypes.SETVARS,
                TTokenTypes.LPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.OP_ASSIGN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.VAR_VALUE,
                TTokenTypes.DSHARP,
                TTokenTypes.NESTED_FUNC_NAME,
                TTokenTypes.QLPAREN,
                TTokenTypes.VAR_NAME,
                TTokenTypes.QRPAREN,
                TTokenTypes.DSHARP,
                TTokenTypes.RPAREN
        );
    }

    public void testArrayField() throws Exception {
        testShortStatementTokenization(
                "##.field1##",
                TTokenTypes.ARRAY_FIELD
        );

        testShortStatementTokenization(
                "##ARR_1_field2##",
                TTokenTypes.ARRAY_FIELD
        );

        testShortStatementTokenization(
                "##.field0##",
                TTokenTypes.UNKNOWN_SYMBOL,
                TTokenTypes.VAR_NAME
        );

        testShortStatementTokenization(
                "##ARR_0_field0##",
                TTokenTypes.VAR_NAME
        );
    }

    public void testOther() throws Exception {
        testTokenization(
                "##\nvar1##",
                TTokenTypes.DSHARP,
                TTokenTypes.HTML,
                TTokenTypes.HTML,
                TTokenTypes.DSHARP
        );

        testTokenization(
                "##\r\nvar1##",
                TTokenTypes.DSHARP,
                TTokenTypes.HTML,
                TTokenTypes.HTML,
                TTokenTypes.DSHARP
        );

        testTokenization(
                "##foo\nbar##ooooo",
                TTokenTypes.DSHARP,
                TTokenTypes.VAR_NAME,
                TTokenTypes.HTML,
                TTokenTypes.HTML,
                TTokenTypes.DSHARP,
                TTokenTypes.VAR_NAME
        );
    }

    private void testShortStatementTokenization(String code, IElementType... expectedTokens) {
        testBoundedTokenization(code, TTokenTypes.DSHARP, TTokenTypes.DSHARP, expectedTokens);
    }

}
