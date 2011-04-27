package com.mailru.plugins.thtml.lang.parser.tools;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import com.mailru.plugins.thtml.lang.parser.TElementTypes;
import com.mailru.plugins.thtml.lang.parser.TParserErrors;
import com.mailru.plugins.thtml.lang.parser.TPsiBuilder;

/**
 * @author mushroom
 */
public class ExpressionParser {

    public static void parse(TPsiBuilder builder, TokenSet stopTokens,
                             boolean rollbackMarker) throws UnsupportedTokenException {
        PsiBuilder.Marker expression = builder.mark();

        int pos = builder.getCurrentOffset();

        while (!builder.eof()) {
            if (builder.compare(stopTokens)) {
                break;
            } else {
                PsiBuilder.Marker marker = builder.mark();

                if (builder.compareAndEat(TTokenTypes.NUMBER)) {
                    marker.done(TElementTypes.NUMBER);
                } else if (builder.compareAndEat(TTokenTypes.EXPRESSION_OPERATORS)) {
                    marker.done(TElementTypes.OPERATOR);
                } else if (builder.compareAndEat(TokenSet.create(TTokenTypes.VAR_NAME, TTokenTypes.VAR_VALUE))) {
                    marker.done(TElementTypes.VARIABLE);
                } else {
                    IElementType currentToken = builder.getTokenType();
                    if (rollbackMarker) {
                        expression.rollbackTo();
                    }
                    throw new UnsupportedTokenException(currentToken);
                }
            }
        }

        if (builder.getCurrentOffset() > pos) {
            expression.done(TElementTypes.EXPRESSION);
        } else {
            expression.drop();
        }
    }

}
