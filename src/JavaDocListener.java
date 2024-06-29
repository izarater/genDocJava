import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

public class JavaDocListener extends Java8BaseListener {
    private Map<String, ClassInfo> classInfoMap = new LinkedHashMap<>();
    private CommonTokenStream tokens;
    private ClassInfo currentClass;

    public JavaDocListener(CommonTokenStream tokens) {
        this.tokens = tokens;
    }

    @Override
    public void enterClassDeclaration(Java8Parser.ClassDeclarationContext ctx) {
        // Verifica que estás accediendo al nombre de la clase correctamente
        String className = ctx.normalClassDeclaration().Identifier().getText();
        String classJavadoc = getJavadoc(ctx);
        currentClass = new ClassInfo(className, classJavadoc);
        classInfoMap.put(className, currentClass);
    }

    @Override
    public void enterFieldDeclaration(Java8Parser.FieldDeclarationContext ctx) {
        // Verifica que estás accediendo al nombre del campo y a sus modificadores correctamente
        String fieldName = ctx.variableDeclaratorList().variableDeclarator(0).variableDeclaratorId().Identifier().getText();
        String fieldModifiers = ctx.fieldModifier().stream()
                .map(modifier -> modifier.getText())
                .reduce((a, b) -> a + " " + b)
                .orElse("");
        String fieldJavadoc = getJavadoc(ctx);
        FieldInfo fieldInfo = new FieldInfo(fieldName, fieldJavadoc, fieldModifiers);
        if (currentClass != null) {
            currentClass.addField(fieldInfo);
        }
    }

    @Override
    public void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        // Verifica que estás accediendo al nombre del método y a sus modificadores correctamente
        String methodName = ctx.methodHeader().methodDeclarator().Identifier().getText();
        String methodModifiers = ctx.methodModifier().stream()
                .map(modifier -> modifier.getText())
                .reduce((a, b) -> a + " " + b)
                .orElse("");
        String parameters = ctx.methodHeader().methodDeclarator().formalParameterList() == null ? "Sin parámetros" :
                ctx.methodHeader().methodDeclarator().formalParameterList().getText();
        String methodJavadoc = getJavadoc(ctx);
        MethodInfo methodInfo = new MethodInfo(methodName, methodJavadoc, methodModifiers, parameters);
        if (currentClass != null) {
            currentClass.addMethod(methodInfo);
        }
    }

    /**
     * Extrae el comentario JavaDoc anterior a un contexto dado.
     * @param ctx El contexto de la regla parser.
     * @return El comentario JavaDoc o una cadena vacía si no hay JavaDoc.
     */
    private String getJavadoc(ParserRuleContext ctx) {
        Token startToken = ctx.getStart();
        int startIndex = startToken.getTokenIndex();

        // Obtiene los tokens del canal oculto (canal 1)
        List<Token> hiddenTokens = tokens.getHiddenTokensToLeft(startIndex, 1);

        if (hiddenTokens != null) {
            for (Token token : hiddenTokens) {
                String text = token.getText();
                // Revisa si el comentario es un JavaDoc (/** ... */)
                if (text.startsWith("/**")) {
                    return text;
                }
            }
        }
        return "";
    }
    public Map<String, ClassInfo> getClassInfoMap() {
        return classInfoMap;
    }
}



