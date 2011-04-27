package com.mailru.plugins.thtml.lang.parser.tools;

import com.intellij.lang.PsiBuilder;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import com.mailru.plugins.thtml.lang.parser.TElementTypes;
import com.mailru.plugins.thtml.lang.parser.TParserErrors;
import com.mailru.plugins.thtml.lang.parser.TPsiBuilder;

/**
 * @author apleshkov
 */
public class SetVarsParser {

    public static void parse(TPsiBuilder builder) {
        final PsiBuilder.Marker setVars = builder.mark();

        if (builder.compareAndEat(TTokenTypes.SETVARS)
                && builder.compareAndEat(TTokenTypes.LPAREN)
                && builder.compareAndEat(TTokenTypes.VAR_NAME)
                && builder.compareAndEat(TTokenTypes.OP_ASSIGN)) {
            FunctionCallParser.parseArgument(builder, TTokenTypes.RPAREN);
            if (builder.compareAndEat(TTokenTypes.RPAREN)) {
                setVars.done(TElementTypes.SETVARS);
                return;
            }
        }

        builder.syntaxError(setVars, TParserErrors.UNEXPECTED_TOKEN);
    }

}
