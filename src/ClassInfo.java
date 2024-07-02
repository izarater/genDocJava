import java.util.ArrayList;
import java.util.List;

public class ClassInfo {
    private String className;
    private String classJavadoc;
    private List<MethodInfo> methods = new ArrayList<>();
    private List<FieldInfo> fields = new ArrayList<>();
    private List<String> relatedClasses = new ArrayList<>(); // Añadir este campo

    public ClassInfo(String className, String classJavadoc) {
        this.className = className;
        this.classJavadoc = classJavadoc;
    }

    public void addMethod(MethodInfo method) {
        methods.add(method);
    }

    public void addField(FieldInfo field) {
        fields.add(field);
    }

    public void addRelatedClass(String relatedClass) {
        relatedClasses.add(relatedClass);
    }

    public String getClassName() {
        return className;
    }

    public String getClassJavadoc() {
        return classJavadoc;
    }

    public List<MethodInfo> getMethods() {
        return methods;
    }

    public List<FieldInfo> getFields() {
        return fields;
    }

    public List<String> getRelatedClasses() {
        return relatedClasses;
    }
}
