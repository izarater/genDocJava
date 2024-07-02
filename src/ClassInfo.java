import java.util.ArrayList;
import java.util.List;

public class ClassInfo {
    private String className;
    private String classJavadoc;
    private List<MethodInfo> methods = new ArrayList<>();
    private List<FieldInfo> fields = new ArrayList<>();
    private List<String> relatedClasses = new ArrayList<>();
    private String superClass;
    private List<String> interfaces = new ArrayList<>();

    public ClassInfo(String className, String classJavadoc) {
        this.className = className;
        this.classJavadoc = classJavadoc;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassJavadoc() {
        return classJavadoc;
    }

    public void setClassJavadoc(String classJavadoc) {
        this.classJavadoc = classJavadoc;
    }

    public List<MethodInfo> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodInfo> methods) {
        this.methods = methods;
    }

    public List<FieldInfo> getFields() {
        return fields;
    }

    public void setFields(List<FieldInfo> fields) {
        this.fields = fields;
    }

    public List<String> getRelatedClasses() {
        return relatedClasses;
    }

    public void setRelatedClasses(List<String> relatedClasses) {
        this.relatedClasses = relatedClasses;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
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

    public void addInterface(String interfaceName) {
        interfaces.add(interfaceName);
    }
}
