package com.mailru.plugins.thtml.lang.parser.tools;

import com.intellij.lang.PsiBuilder;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import com.mailru.plugins.thtml.lang.parser.TElementTypes;
import com.mailru.plugins.thtml.lang.parser.TPsiBuilder;

/**
 * @author apleshkov
 */
public class CodeParser {

    public static void parse(TPsiBuilder builder) {
        PsiBuilder.Marker marker = builder.mark();

        if (builder.compare(TTokenTypes.DSHARP)) {
            ShortStatementParser.parse(builder);
        } else if (builder.compare(TTokenTypes.STATEMENT_OPENING_TAG)) {
            StatementParser.parse(builder);
        } else if (builder.compareAndEat(TTokenTypes.HTML)) {
            marker.collapse(TElementTypes.HTML);
            return;
        } else {
            builder.advanceLexer();
        }

        marker.drop();
    }

}
