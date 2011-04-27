package lang.tools;

import com.intellij.lexer.FlexAdapter;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ArrayUtil;
import com.mailru.plugins.thtml.lang.lexer.TFlexLexer;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.Reader;

/**
 * @author apleshkov
 */
public class TLexerTest extends TestCase {

    protected void testTokenization(String code, IElementType... expectedTokens) {
        final FlexAdapter lexer = new FlexAdapter(new TFlexLexer((Reader) null));

        lexer.start(code);

        int i = 1;

        for (IElementType expectedToken : expectedTokens) {
            final IElementType actualToken = lexer.getTokenType();
            Assert.assertEquals(
                    "Wrong match at #" + i + " (" + lexer.getTokenText() + ")",
                    expectedToken, actualToken);
            lexer.advance();
            i++;
        }

        Assert.assertTrue("Lexer has tokens left: " + lexer.getTokenType(), lexer.getTokenType() == null);
    }

    protected void testBoundedTokenization(String code, IElementType begin,
                                                  IElementType end, IElementType... expectedTokens) {
        IElementType[] tokens = {begin};
        tokens = ArrayUtil.mergeArrays(tokens, expectedTokens, IElementType.class);
        tokens = ArrayUtil.append(tokens, end);

        testTokenization(code, tokens);
    }

}
