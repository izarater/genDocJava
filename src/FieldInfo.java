public class FieldInfo {
    private String fieldName;
    private String fieldJavadoc;
    private String fieldModifiers;  // Añadido para capturar los modificadores de acceso y tipo

    public FieldInfo(String fieldName, String fieldJavadoc, String fieldModifiers) {
        this.fieldName = fieldName;
        this.fieldJavadoc = fieldJavadoc;
        this.fieldModifiers = fieldModifiers;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldJavadoc() {
        return fieldJavadoc;
    }

    public String getFieldModifiers() {
        return fieldModifiers;
    }
}
