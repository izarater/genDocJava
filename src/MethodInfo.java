import java.util.ArrayList;
import java.util.List;

public class MethodInfo {
    private String methodName;
    private String methodJavadoc;
    private List<String> parameters = new ArrayList<>();
    private String methodModifiers;
    private String returnType;
    private List<String> parameterDescriptions = new ArrayList<>();

    public MethodInfo(String methodName, String methodJavadoc, String methodModifiers, List<String> parameters, String returnType, List<String> parameterDescriptions) {
        this.methodName = methodName;
        this.methodJavadoc = methodJavadoc;
        this.methodModifiers = methodModifiers;
        this.parameters = parameters;
        this.returnType = returnType;
        this.parameterDescriptions = parameterDescriptions;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodJavadoc() {
        return methodJavadoc;
    }

    public void setMethodJavadoc(String methodJavadoc) {
        this.methodJavadoc = methodJavadoc;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(String parameter) {
        parameters.add(parameter);
    }

    public String getMethodModifiers() {
        return methodModifiers;
    }

    public void setMethodModifiers(String methodModifiers) {
        this.methodModifiers = methodModifiers;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<String> getParameterDescriptions() {
        return parameterDescriptions;
    }

    public void setParameterDescriptions(List<String> parameterDescriptions) {
        this.parameterDescriptions = parameterDescriptions;
    }
}
