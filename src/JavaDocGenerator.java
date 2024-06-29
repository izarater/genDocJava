import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class JavaDocGenerator {

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

        System.out.println("Documentación generada exitosamente.");
    }

    public static void generateHTMLDocumentation(Map<String, ClassInfo> classInfoMap, String inputFilePath) throws IOException {
        // Derivar el nombre del archivo de salida del nombre del archivo de entrada
        String outputFileName = "output/documentation.html";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write("<!DOCTYPE html>");
            writer.write("<html>");
            writer.write("<head><title>Documentacion de Codigo Java</title></head>");
            writer.write("<body>");
            writer.write("<h1>Documentacion de Codigo Java</h1>");

            // Información General del Proyecto
            writer.write("<h2>Informacion General del Proyecto</h2>");
            writer.write("<ul>");
            writer.write("<li><strong>Nombre del Proyecto:</strong> " + extractProjectName(inputFilePath) + "</li>");
            writer.write("<li><strong>Descripcion:</strong> " + extractProjectDescription(inputFilePath) + "</li>");
            writer.write("</ul>");

            // Estructura del Código
            writer.write("<h2>Estructura del Codigo</h2>");
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
                        writer.write("<p><strong>Parametros:</strong> " + method.getParameters() + "</p>");
                    }
                }
            }

            writer.write("</body>");
            writer.write("</html>");
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

    private static void generateTextDocumentation(Map<String, ClassInfo> classInfoMap, String outputFileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write("Documentación de Código Java\n\n");

            writer.write("Información General del Proyecto\n");
            writer.write("Nombre del Proyecto: sample\n");
            writer.write("Descripción: Documentación generada para el archivo " + outputFileName + "\n\n");

            writer.write("Estructura del Código\n\n");

            for (String className : classInfoMap.keySet()) {
                ClassInfo classInfo = classInfoMap.get(className);
                writer.write("Clase: " + className + "\n");
                writer.write("  Descripción: " + (classInfo.getClassJavadoc().isEmpty() ? "No hay descripción." : classInfo.getClassJavadoc()) + "\n");

                writer.write("  Campos:\n");
                for (FieldInfo field : classInfo.getFields()) {
                    writer.write("    Nombre: " + field.getFieldName() + "\n");
                    writer.write("    Descripción: " + (field.getFieldJavadoc().isEmpty() ? "No hay descripción." : field.getFieldJavadoc()) + "\n");
                    writer.write("    Modificadores: " + (field.getFieldModifiers().isEmpty() ? "No hay modificadores." : field.getFieldModifiers()) + "\n");
                }

                writer.write("  Métodos:\n");
                for (MethodInfo method : classInfo.getMethods()) {
                    writer.write("    Nombre: " + method.getMethodName() + "\n");
                    writer.write("    Descripción: " + (method.getMethodJavadoc().isEmpty() ? "No hay descripción." : method.getMethodJavadoc()) + "\n");
                    writer.write("    Modificadores: " + (method.getMethodModifiers().isEmpty() ? "No hay modificadores." : method.getMethodModifiers()) + "\n");
                    writer.write("    Parámetros: " + (method.getParameters().isEmpty() ? "Sin parámetros" : method.getParameters()) + "\n");
                }

                writer.write("\n");
            }
        }
    }
}




