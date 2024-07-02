import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.ParserRuleContext;
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
        String className = ctx.normalClassDeclaration().Identifier().getText();
        String classJavadoc = getJavadoc(ctx);
        currentClass = new ClassInfo(className, classJavadoc);
        classInfoMap.put(className, currentClass);

        if (ctx.normalClassDeclaration().superclass() != null) {
            String superClass = ctx.normalClassDeclaration().superclass().classType().getText();
            currentClass.setSuperClass(superClass);
            currentClass.addRelatedClass(superClass);
        }

        if (ctx.normalClassDeclaration().superinterfaces() != null) {
            for (Java8Parser.InterfaceTypeContext interfaceCtx : ctx.normalClassDeclaration().superinterfaces().interfaceTypeList().interfaceType()) {
                String interfaceName = interfaceCtx.getText();
                currentClass.addInterface(interfaceName);
                currentClass.addRelatedClass(interfaceName);
            }
        }
    }

    @Override
    public void enterFieldDeclaration(Java8Parser.FieldDeclarationContext ctx) {
        String fieldName = ctx.variableDeclaratorList().variableDeclarator(0).variableDeclaratorId().Identifier().getText();
        String fieldModifiers = ctx.fieldModifier().stream()
                .map(modifier -> modifier.getText())
                .reduce((a, b) -> a + " " + b)
                .orElse("");
        String fieldJavadoc = getJavadoc(ctx);
        String fieldType = ctx.unannType().getText();
        FieldInfo fieldInfo = new FieldInfo(fieldName, fieldJavadoc, fieldType, fieldModifiers);
        if (currentClass != null) {
            currentClass.addField(fieldInfo);
            currentClass.addDependency(fieldType);
        }
    }

    @Override
    public void enterMethodDeclaration(Java8Parser.MethodDeclarationContext ctx) {
        String methodName = ctx.methodHeader().methodDeclarator().Identifier().getText();
        String methodModifiers = ctx.methodModifier().stream()
                .map(modifier -> modifier.getText())
                .reduce((a, b) -> a + " " + b)
                .orElse("");
        List<String> parameters = new ArrayList<>();
        List<String> parameterDescriptions = new ArrayList<>();
        if (ctx.methodHeader().methodDeclarator().formalParameterList() != null) {
            if (ctx.methodHeader().methodDeclarator().formalParameterList().formalParameters() != null) {
                for (Java8Parser.FormalParameterContext paramCtx : ctx.methodHeader().methodDeclarator().formalParameterList().formalParameters().formalParameter()) {
                    parameters.add(paramCtx.variableDeclaratorId().Identifier().getText() + ": " + paramCtx.unannType().getText());
                    parameterDescriptions.add(getJavadoc(paramCtx));
                    currentClass.addDependency(paramCtx.unannType().getText());
                }
            }
        }
        String returnType = ctx.methodHeader().result().getText();
        String methodJavadoc = getJavadoc(ctx);
        MethodInfo methodInfo = new MethodInfo(methodName, methodJavadoc, methodModifiers, parameters, returnType, parameterDescriptions);
        if (currentClass != null) {
            currentClass.addMethod(methodInfo);
            currentClass.addDependency(returnType);
        }
    }

    private String getJavadoc(ParserRuleContext ctx) {
        Token startToken = ctx.getStart();
        int startIndex = startToken.getTokenIndex();
        List<Token> hiddenTokens = tokens.getHiddenTokensToLeft(startIndex, 1);
        if (hiddenTokens != null) {
            for (Token token : hiddenTokens) {
                String text = token.getText();
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
