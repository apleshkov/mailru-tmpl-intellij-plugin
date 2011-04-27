package com.mailru.plugins.thtml.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.mailru.plugins.thtml.lang.parser.tools.CodeParser;
import org.jetbrains.annotations.NotNull;

/**
 * @author apleshkov
 */
public class TPsiParser implements PsiParser {

    private void parse(final TPsiBuilder builder) {
        while (!builder.eof()) {
            CodeParser.parse(builder);
        }
    }

    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder psiBuilder) {
        final TPsiBuilder builder = new TPsiBuilder(psiBuilder);

        final PsiBuilder.Marker rootMarker = builder.mark();

        parse(builder);

        rootMarker.done(root);

        return builder.getTreeBuilt();
    }
}
