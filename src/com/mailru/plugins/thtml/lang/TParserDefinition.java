package com.mailru.plugins.thtml.lang;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.mailru.plugins.thtml.lang.lexer.TFlexLexer;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import com.mailru.plugins.thtml.lang.parser.TElementTypes;
import com.mailru.plugins.thtml.lang.parser.TPsiParser;
import com.mailru.plugins.thtml.lang.psi.TPsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author apleshkov
 */
public class TParserDefinition implements ParserDefinition {

    public static final IFileElementType FILE = new IFileElementType(TFileType.TMPL.getLanguage());

    @NotNull
    public Lexer createLexer(Project project) {
        return new FlexAdapter(new TFlexLexer((java.io.Reader) null));
    }

    public PsiParser createParser(Project project) {
        return new TPsiParser();
    }

    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return TokenSet.create(
                TTokenTypes.WHITESPACE
        );
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        /*return TokenSet.create(
                TTokenTypes.STRING
        );*/
        return TokenSet.EMPTY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return new ASTWrapperPsiElement(node);
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new TPsiFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return null;
    }
}
