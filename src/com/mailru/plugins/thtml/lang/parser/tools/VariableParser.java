package com.mailru.plugins.thtml.lang.parser.tools;

import com.intellij.lang.PsiBuilder;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import com.mailru.plugins.thtml.lang.parser.TElementTypes;
import com.mailru.plugins.thtml.lang.parser.TPsiBuilder;

/**
 * @author apleshkov
 */
public class VariableParser {

    public static void parse(TPsiBuilder builder) {
        final PsiBuilder.Marker var = builder.mark();

        if (builder.compareAndEat(TTokenTypes.VAR_NAME)) {
            var.done(TElementTypes.VARIABLE);
        } else {
            builder.syntaxError(var);
        }
    }

}
