package com.mailru.plugins.thtml.lang.parser.tools;

import com.intellij.lang.PsiBuilder;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import com.mailru.plugins.thtml.lang.parser.TElementTypes;
import com.mailru.plugins.thtml.lang.parser.TParserErrors;
import com.mailru.plugins.thtml.lang.parser.TPsiBuilder;

/**
 * @author apleshkov
 */
public class StatementParser {

    public static void parse(TPsiBuilder builder) {
        final PsiBuilder.Marker statement = builder.mark();

        if (builder.compareAndEat(TTokenTypes.STATEMENT_OPENING_TAG)) {
            if (builder.compare(TTokenTypes.COND_KEYWORDS)) {
                
            } else {
                statement.done(TElementTypes.HTML);
            }
        } else {
            builder.syntaxError(statement, TParserErrors.UNEXPECTED_TOKEN);
        }
    }

}
