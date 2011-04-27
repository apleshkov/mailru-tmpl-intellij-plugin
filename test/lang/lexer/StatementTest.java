package lang.lexer;

import com.intellij.psi.tree.IElementType;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import lang.tools.TLexerTest;

/**
 * @author apleshkov
 */
public class StatementTest extends TLexerTest {

    final public void testStatement() throws Exception {
        testStatementTokenization(
                "<!--1-->",
                TTokenTypes.NUMBER
        );

        testStatementTokenization(
                "<!--A--->",
                TTokenTypes.VAR_NAME
        );

        testStatementTokenization(
                "<!--       1  -->",
                TTokenTypes.WHITESPACE,
                TTokenTypes.NUMBER,
                TTokenTypes.WHITESPACE
        );

        testStatementTokenization(
                "<!--       A        --->",
                TTokenTypes.WHITESPACE,
                TTokenTypes.VAR_NAME,
                TTokenTypes.WHITESPACE
        );

        testTokenization(
                "<!--\n1-->",
                TTokenTypes.STATEMENT_OPENING_TAG,
                TTokenTypes.HTML,
                TTokenTypes.HTML
        );

        testTokenization(
                "<!-1-->",
                TTokenTypes.HTML,
                TTokenTypes.HTML
        );

        testTokenization(
                "<!---1-->",
                TTokenTypes.HTML,
                TTokenTypes.HTML
        );

        testTokenization(
                "<!----1-->",
                TTokenTypes.HTML,
                TTokenTypes.HTML
        );
    }

    protected void testStatementTokenization(String code, IElementType... expectedTokens) {
        testBoundedTokenization(
                code,
                TTokenTypes.STATEMENT_OPENING_TAG,
                TTokenTypes.STATEMENT_CLOSING_TAG,
                expectedTokens
        );
    }

}
