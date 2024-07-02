import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JavaDocGenerator {
    private static final Logger logger = LoggerFactory.getLogger(JavaDocGenerator.class);

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Por favor, proporcione el archivo de entrada.");
            return;
        }

        String inputFile = args[0];
        FileInputStream fis = new FileInputStream(inputFile);

        // Crear un lexer y parser para el archivo de entrada
        CharStream input = CharStreams.fromStream(fis);
        Java8Lexer lexer = new Java8Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(tokens);

        // Crear un listener que extraerá la información JavaDoc
        JavaDocListener extractor = new JavaDocListener(tokens);

        // Crear el árbol de sintaxis del archivo Java
        ParseTree tree = parser.compilationUnit();

        // Recorrer el árbol con el listener
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(extractor, tree);

        // Obtener la información extraída por el listener
        Map<String, ClassInfo> classInfoMap = extractor.getClassInfoMap();

        // Generar documentación en formato HTML
        generateHTMLDocumentation(classInfoMap, inputFile);

        // Generar documentación en formato de texto plano
        generateTextDocumentation(classInfoMap, "output/documentation.txt");

        // Generar un diagrama de clases
        DiagramGenerator.generateClassDiagram(classInfoMap, "output/classDiagram.png");

        // Generar un diagrama de secuencia
        generateSequenceDiagram();

        // Generar un diagrama de actividad
        generateActivityDiagram();

        System.out.println("Documentación y diagramas generados exitosamente.");
    }

    public static void generateHTMLDocumentation(Map<String, ClassInfo> classInfoMap, String inputFilePath) throws IOException {
        // Derivar el nombre del archivo de salida del nombre del archivo de entrada
        String outputFileName = "output/documentation.html";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write("<!DOCTYPE html>");
            writer.write("<html>");
            writer.write("<head>");
            writer.write("<title>Documentacion de Codigo Java</title>");
            writer.write("<style>");
            writer.write("body { font-family: Arial, sans-serif; margin: 20px; line-height: 1.6; }");
            writer.write("h1 { color: #333; }");
            writer.write("h2 { color: #666; }");
            writer.write("h3 { color: #888; }");
            writer.write("ul { list-style-type: none; padding: 0; }");
            writer.write("li { margin: 5px 0; }");
            writer.write("pre { background: #f4f4f4; padding: 10px; border: 1px solid #ddd; border-radius: 5px; }");
            writer.write(".container { max-width: 900px; margin: 0 auto; }");
            writer.write(".class-diagram { text-align: center; }");
            writer.write(".nav { margin-bottom: 20px; }");
            writer.write(".nav a { margin-right: 10px; }");
            writer.write("</style>");
            writer.write("</head>");
            writer.write("<body>");
            writer.write("<div class='container'>");
            writer.write("<h1>Documentacion de Codigo Java</h1>");
            writer.write("<div class='nav'>");
            writer.write("<a href='#project-info'>Informacion General</a>");
            writer.write("<a href='#class-diagram'>Diagrama de Clases</a>");
            writer.write("<a href='#sequence-diagram'>Diagrama de Secuencia</a>");
            writer.write("<a href='#activity-diagram'>Diagrama de Actividad</a>");
            writer.write("<a href='#code-structure'>Estructura del Codigo</a>");
            writer.write("</div>");

            // Información General del Proyecto
            writer.write("<h2 id='project-info'>Informacion General del Proyecto</h2>");
            writer.write("<ul>");
            writer.write("<li><strong>Nombre del Proyecto:</strong> " + extractProjectName(inputFilePath) + "</li>");
            writer.write("<li><strong>Descripcion:</strong> " + extractProjectDescription(inputFilePath) + "</li>");
            writer.write("</ul>");

            // Incluir el diagrama de clases en la documentación
            writer.write("<h2 id='class-diagram'>Diagrama de Clases</h2>");
            writer.write("<div class='class-diagram'><img src='classDiagram.png' alt='Diagrama de Clases'></div>");

            // Incluir el diagrama de secuencia en la documentación
            writer.write("<h2 id='sequence-diagram'>Diagrama de Secuencia</h2>");
            writer.write("<div class='class-diagram'><img src='sequenceDiagram.png' alt='Diagrama de Secuencia'></div>");

            // Incluir el diagrama de actividad en la documentación
            writer.write("<h2 id='activity-diagram'>Diagrama de Actividad</h2>");
            writer.write("<div class='class-diagram'><img src='activityDiagram.png' alt='Diagrama de Actividad'></div>");

            // Estructura del Código
            writer.write("<h2 id='code-structure'>Estructura del Codigo</h2>");
            for (String className : classInfoMap.keySet()) {
                ClassInfo classInfo = classInfoMap.get(className);
                writer.write("<h3>Clase: " + className + "</h3>");
                writer.write("<p><strong>Descripcion:</strong> " + (classInfo.getClassJavadoc().isEmpty() ? "No hay descripcion." : "<pre>" + classInfo.getClassJavadoc() + "</pre>") + "</p>");

                // Campos
                if (!classInfo.getFields().isEmpty()) {
                    writer.write("<h4>Campos</h4>");
                    for (FieldInfo field : classInfo.getFields()) {
                        writer.write("<p><strong>Nombre:</strong> " + field.getFieldName() + "</p>");
                        writer.write("<p><strong>Descripcion:</strong> " + (field.getFieldJavadoc().isEmpty() ? "No hay descripcion." : "<pre>" + field.getFieldJavadoc() + "</pre>") + "</p>");
                        writer.write("<p><strong>Tipo:</strong> " + field.getFieldType() + "</p>");
                        writer.write("<p><strong>Modificadores:</strong> " + field.getFieldModifiers() + "</p>");
                    }
                }

                // Métodos
                if (!classInfo.getMethods().isEmpty()) {
                    writer.write("<h4>Metodos</h4>");
                    for (MethodInfo method : classInfo.getMethods()) {
                        writer.write("<p><strong>Nombre:</strong> " + method.getMethodName() + "</p>");
                        writer.write("<p><strong>Descripcion:</strong> " + (method.getMethodJavadoc().isEmpty() ? "No hay descripcion." : "<pre>" + method.getMethodJavadoc() + "</pre>") + "</p>");
                        writer.write("<p><strong>Modificadores:</strong> " + method.getMethodModifiers() + "</p>");
                        writer.write("<p><strong>Parametros:</strong> " + String.join(", ", method.getParameters()) + "</p>");
                    }
                }
            }

            writer.write("</div>");
            writer.write("</body>");
            writer.write("</html>");
        }
    }

    public static void generateTextDocumentation(Map<String, ClassInfo> classInfoMap, String outputFileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write("Documentacion de Codigo Java\n");
            writer.write("============================\n");

            // Información General del Proyecto
            writer.write("\nInformacion General del Proyecto\n");
            writer.write("-------------------------------\n");
            writer.write("Nombre del Proyecto: " + extractProjectName("input/sample.java") + "\n");
            writer.write("Descripcion: " + extractProjectDescription("input/sample.java") + "\n");

            // Estructura del Código
            writer.write("\nEstructura del Codigo\n");
            writer.write("----------------------\n");
            for (String className : classInfoMap.keySet()) {
                ClassInfo classInfo = classInfoMap.get(className);
                writer.write("Clase: " + className + "\n");
                writer.write("\tDescripcion: " + (classInfo.getClassJavadoc().isEmpty() ? "No hay descripcion." : classInfo.getClassJavadoc()) + "\n");

                // Campos
                if (!classInfo.getFields().isEmpty()) {
                    writer.write("\tCampos:\n");
                    for (FieldInfo field : classInfo.getFields()) {
                        writer.write("\t\tNombre: " + field.getFieldName() + "\n");
                        writer.write("\t\tDescripcion: " + (field.getFieldJavadoc().isEmpty() ? "No hay descripcion." : field.getFieldJavadoc()) + "\n");
                        writer.write("\t\tTipo: " + field.getFieldType() + "\n");
                        writer.write("\t\tModificadores: " + field.getFieldModifiers() + "\n");
                    }
                }

                // Métodos
                if (!classInfo.getMethods().isEmpty()) {
                    writer.write("\tMetodos:\n");
                    for (MethodInfo method : classInfo.getMethods()) {
                        writer.write("\t\tNombre: " + method.getMethodName() + "\n");
                        writer.write("\t\tDescripcion: " + (method.getMethodJavadoc().isEmpty() ? "No hay descripcion." : method.getMethodJavadoc()) + "\n");
                        writer.write("\t\tModificadores: " + method.getMethodModifiers() + "\n");
                        writer.write("\t\tParametros: " + String.join(", ", method.getParameters()) + "\n");
                    }
                }
            }
        }
    }

    private static String extractProjectName(String inputFilePath) {
        Path path = Paths.get(inputFilePath);
        return path.getFileName().toString().replace(".java", "");
    }

    private static String extractProjectDescription(String inputFilePath) {
        // Podríamos mejorar este método para leer una descripción del archivo o de una fuente externa
        return "Documentacion generada para el archivo " + inputFilePath;
    }

    private static void generateSequenceDiagram() throws IOException {
        List<SequenceEvent> events = new ArrayList<>();
        events.add(new SequenceEvent("User", "UserService", "addUser()"));
        events.add(new SequenceEvent("UserService", "UserRepository", "saveUser()"));
        events.add(new SequenceEvent("UserRepository", "Database", "insertUser()"));
        events.add(new SequenceEvent("Database", "UserRepository", "confirmInsert()"));
        events.add(new SequenceEvent("UserRepository", "UserService", "confirmSave()"));
        events.add(new SequenceEvent("UserService", "User", "confirmAdd()"));

        SequenceDiagramGenerator.generateSequenceDiagram(events, "output/sequenceDiagram");
    }

    private static void generateActivityDiagram() throws IOException {
        List<ActivityStep> steps = new ArrayList<>();
        steps.add(new ActivityStep("Inicio"));
        steps.add(new ActivityStep("Login"));
        steps.add(new ActivityStep("Seleccionar Usuario"));
        steps.add(new ActivityStep("Actualizar Información"));
        steps.add(new ActivityStep("Guardar Cambios"));
        steps.add(new ActivityStep("Fin"));

        ActivityDiagramGenerator.generateActivityDiagram(steps, "output/activityDiagram");
    }
}
