package com.mailru.plugins.thtml.lang.parser.tools;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import com.mailru.plugins.thtml.lang.parser.TElementTypes;
import com.mailru.plugins.thtml.lang.parser.TParserErrors;
import com.mailru.plugins.thtml.lang.parser.TPsiBuilder;

/**
 * @author apleshkov
 */
public class FunctionCallParser {

    public static boolean parseArgument(TPsiBuilder builder, IElementType rParen) {
        if (builder.compare(rParen)) {
            return false;
        }

        int pos = builder.getCurrentOffset();

        TokenSet stopTokens = TokenSet.create(TTokenTypes.OP_COMMA, rParen);

        try {
            ExpressionParser.parse(builder, stopTokens, true);
        } catch (UnsupportedTokenException e) {
            if (e.getToken() == TTokenTypes.DSHARP && builder.compareAndEat(TTokenTypes.DSHARP)) {
                __parse(builder, TTokenTypes.NESTED_FUNC_NAME, TTokenTypes.QLPAREN, TTokenTypes.QRPAREN);
                return builder.compareAndEat(TTokenTypes.DSHARP);
            } else {
                PsiBuilder.Marker string = builder.mark();
                while (!builder.eof() && !builder.compare(stopTokens)) {
                    builder.advanceLexer();
                }
                string.done(TElementTypes.STRING);
            }
        }

        return builder.getCurrentOffset() > pos;
    }

    private static boolean parseArguments(TPsiBuilder builder, IElementType rParen) {
        if (parseArgument(builder, rParen)) {
            if (builder.compareAndEat(TTokenTypes.OP_COMMA)) {
                return parseArguments(builder, rParen);
            } else {
                return builder.compare(rParen);
            }
        } else {
            return false;
        }
    }

    private static void __parse(TPsiBuilder builder, IElementType funcType,
                                IElementType lParen, IElementType rParen) {
        final PsiBuilder.Marker funcCall = builder.mark();

        final PsiBuilder.Marker func = builder.mark();

        if (builder.compareAndEat(funcType)) {
            func.done(TElementTypes.FUNCTION);
            if (builder.compareAndEat(lParen)) {
                if (builder.compareAndEat(rParen)
                        || (parseArguments(builder, rParen) && builder.compareAndEat(rParen))) {
                    funcCall.done(TElementTypes.FUNCTION_CALL);
                    return;
                }
            }
        } else {
            builder.syntaxError(func, TParserErrors.UNEXPECTED_TOKEN);
        }

        builder.syntaxError(funcCall);
    }

    public static void parse(TPsiBuilder builder) {
        __parse(builder, TTokenTypes.FUNC_NAME, TTokenTypes.LPAREN, TTokenTypes.RPAREN);
    }

}
