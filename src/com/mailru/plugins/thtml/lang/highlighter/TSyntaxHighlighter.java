package com.mailru.plugins.thtml.lang.highlighter;

import com.intellij.lang.StdLanguages;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * @author apleshkov
 */
public class TSyntaxHighlighter extends SyntaxHighlighterBase {

    private final SyntaxHighlighter syntaxHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(StdLanguages.HTML, null, null);

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return syntaxHighlighter.getHighlightingLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return syntaxHighlighter.getTokenHighlights(tokenType);
    }
}
