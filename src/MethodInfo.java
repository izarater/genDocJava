public class MethodInfo {
    private String methodName;
    private String methodJavadoc;
    private String methodModifiers;  // Añadido para capturar los modificadores de acceso y tipo
    private String parameters;       // Añadido para capturar los parámetros del método

    public MethodInfo(String methodName, String methodJavadoc, String methodModifiers, String parameters) {
        this.methodName = methodName;
        this.methodJavadoc = methodJavadoc;
        this.methodModifiers = methodModifiers;
        this.parameters = parameters;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodJavadoc() {
        return methodJavadoc;
    }

    public String getMethodModifiers() {
        return methodModifiers;
    }

    public String getParameters() {
        return parameters;
    }
}

