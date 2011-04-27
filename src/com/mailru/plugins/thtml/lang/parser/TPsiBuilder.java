package com.mailru.plugins.thtml.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

/**
 * @author apleshkov
 */
public class TPsiBuilder {

    private final PsiBuilder builder;

    public TPsiBuilder(final PsiBuilder builder) {
        this.builder = builder;
        this.builder.setDebugMode(true);
    }

    public boolean compare(final IElementType... tokens) {
        final IElementType currentToken = getTokenType();
        for (IElementType token : tokens) {
            if (token == currentToken) {
                return true;
            }
        }
        return false;
    }

    public boolean compare(final TokenSet... sets) {
        final IElementType currentToken = getTokenType();
        for (TokenSet set : sets) {
            if (set.contains(currentToken)) {
                return true;
            }
        }
        return false;
    }

    public boolean compareAndEat(final IElementType... tokens) {
        if (compare(tokens)) {
            advanceLexer();
            return true;
        } else {
            return false;
        }
    }

    public boolean compareAndEat(final TokenSet... sets) {
        if (compare(sets)) {
            advanceLexer();
            return true;
        } else {
            return false;
        }
    }

    public int getCurrentOffset() {
        return builder.getCurrentOffset();
    }

    public IElementType getTokenType() {
        return builder.getTokenType();
    }

    public String getTokenText() {
        return builder.getTokenText();
    }

    public void advanceLexer() {
        builder.advanceLexer();
    }

    public boolean eof() {
        return builder.eof();
    }

    public PsiBuilder.Marker mark() {
        return builder.mark();
    }

    public ASTNode getTreeBuilt() {
        return builder.getTreeBuilt();
    }

    public void syntaxError(final PsiBuilder.Marker marker, final String message) {
        marker.done(TElementTypes.SYNTAX_ERROR);
        builder.error(message);
    }

    public void syntaxError(final PsiBuilder.Marker marker) {
        syntaxError(marker, TParserErrors.UNEXPECTED_END_OF_STATEMENT);
    }

}
