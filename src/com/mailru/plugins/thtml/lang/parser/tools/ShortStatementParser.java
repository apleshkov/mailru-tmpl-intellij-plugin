package com.mailru.plugins.thtml.lang.parser.tools;

import com.intellij.lang.PsiBuilder;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import com.mailru.plugins.thtml.lang.parser.TElementTypes;
import com.mailru.plugins.thtml.lang.parser.TPsiBuilder;

/**
 * @author apleshkov
 */
public class ShortStatementParser {

    public static void parse(TPsiBuilder builder) {
        final PsiBuilder.Marker statement = builder.mark();

        if (builder.compareAndEat(TTokenTypes.DSHARP)) {
            final int pos = builder.getCurrentOffset();

            if (builder.compare(TTokenTypes.SETVARS)) {
                SetVarsParser.parse(builder);
            } else if (builder.compare(TTokenTypes.VAR_NAME)) {
                VariableParser.parse(builder);
            } else if (builder.compare(TTokenTypes.FUNC_NAME)) {
                FunctionCallParser.parse(builder);
            }

            if (builder.compareAndEat(TTokenTypes.DSHARP) && pos < builder.getCurrentOffset()) {
                statement.done(TElementTypes.SHORT_STATEMENT);
                return;
            } else if (builder.compareAndEat(TTokenTypes.HTML) || builder.eof()) {
                statement.collapse(TElementTypes.HTML);
                return;
            }
        }

        builder.syntaxError(statement);
    }

}
