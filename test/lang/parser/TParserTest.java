package lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.application.WriteAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.testFramework.fixtures.IdeaProjectTestFixture;
import com.intellij.testFramework.fixtures.IdeaTestFixtureFactory;
import com.intellij.testFramework.fixtures.TestFixtureBuilder;
import com.mailru.plugins.thtml.lang.lexer.TTokenTypes;
import com.mailru.plugins.thtml.lang.parser.TElementTypes;
import lang.tools.TDirectoryTestCase;

/**
 * @author apleshkov
 */
public class TParserTest extends TDirectoryTestCase {

    private IdeaProjectTestFixture myFixture;

    private Project myProject;

    private static TokenSet excludeTypes = TokenSet.create(
            TokenType.ERROR_ELEMENT
    );

    private static TokenSet typesOnly = TokenSet.orSet(
            TokenSet.create(
                    TElementTypes.HTML,
                    TElementTypes.SHORT_STATEMENT,
                    TElementTypes.FUNCTION_CALL,
                    TElementTypes.SYNTAX_ERROR,
                    TElementTypes.EXPRESSION,
                    TTokenTypes.UNKNOWN_SYMBOL,
                    TTokenTypes.HTML,
                    TTokenTypes.LPAREN,
                    TTokenTypes.RPAREN,
                    TTokenTypes.QLPAREN,
                    TTokenTypes.QRPAREN
            ),
            TTokenTypes.OPERATORS,
            TTokenTypes.COND_KEYWORDS,
            TTokenTypes.LOOP_KEYWORDS
    );

    @Override
    protected void setUp() throws Exception {
        TestFixtureBuilder<IdeaProjectTestFixture> fixtureBuilder = IdeaTestFixtureFactory.getFixtureFactory().createLightFixtureBuilder();
        myFixture = fixtureBuilder.getFixture();
        myFixture.setUp();

        myProject = myFixture.getProject();

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        new WriteAction() {
            @Override
            protected void run(Result result) throws Throwable {
                myFixture.tearDown();
            }
        }.execute();

        super.tearDown();
    }

    @Override
    protected String transformInput(String text) {
        final PsiFile psiFile = PsiFileFactory.getInstance(myProject).createFileFromText("temp.tmpl", text);
        final StringBuffer result = new StringBuffer();
        transformNode(result, psiFile, 0);
        return result.toString();
    }

    private void transformNode(final StringBuffer buffer, final PsiElement root, final int level) {
        if (root instanceof PsiFile) {
            visitPsiElementChildren(buffer, root, level);
        } else {
            final ASTNode node = root.getNode();

            if (node != null) {
                final IElementType el = node.getElementType();

                if (!excludeTypes.contains(el)) {
                    for (int i = 0; i < level; i++) {
                        buffer.append("    ");
                    }

                    buffer.append(el.toString());
                    if (!typesOnly.contains(el)) {
                        buffer.append(" -- ");
                        buffer.append(node.getText());
                    }

                    buffer.append(TDirectoryTestCase.LINE_SEPARATOR);

                    visitPsiElementChildren(buffer, root, level + 1);
                }
            }
        }
    }

    private void visitPsiElementChildren(final StringBuffer buffer, final PsiElement el, final int level) {
        final PsiElement[] children = el.getChildren();

        for (PsiElement child : children) {
            transformNode(buffer, child, level);
        }
    }

    public void testParser() throws Exception {
        startTest("parser");
    }
}
