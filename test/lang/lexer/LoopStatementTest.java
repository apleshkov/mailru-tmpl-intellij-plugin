package lang.lexer;

import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;

/**
 * @author apleshkov
 */
public class LoopStatementTest extends StatementTest {

    public void testLoop() throws Exception {
        testStatementTokenization(
                "<!--FOR-->",
                TTokenTypes.L_FOR
        );

        testStatementTokenization(
                "<!--/FOR-->",
                TTokenTypes.L_ENDFOR
        );

        testStatementTokenization(
                "<!--CONTINUE-->",
                TTokenTypes.L_CONTINUE
        );

        testStatementTokenization(
                "<!--BREAK-->",
                TTokenTypes.L_BREAK
        );

        testStatementTokenization(
                "<!--FOR ARR-->",
                TTokenTypes.L_FOR,
                TTokenTypes.WHITESPACE,
                TTokenTypes.VAR_NAME
        );
    }
}
