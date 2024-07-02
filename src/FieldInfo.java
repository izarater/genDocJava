public class FieldInfo {
    private String fieldName;
    private String fieldJavadoc;
    private String fieldType;
    private String fieldModifiers;

    public FieldInfo(String fieldName, String fieldJavadoc, String fieldType, String fieldModifiers) {
        this.fieldName = fieldName;
        this.fieldJavadoc = fieldJavadoc;
        this.fieldType = fieldType;
        this.fieldModifiers = fieldModifiers;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldJavadoc() {
        return fieldJavadoc;
    }

    public void setFieldJavadoc(String fieldJavadoc) {
        this.fieldJavadoc = fieldJavadoc;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldModifiers() {
        return fieldModifiers;
    }

    public void setFieldModifiers(String fieldModifiers) {
        this.fieldModifiers = fieldModifiers;
    }
}
