import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizCmdLineEngine;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.Node;
import guru.nidi.graphviz.attribute.Label;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Factory.to;

public class DiagramGenerator {

    public static void generateClassDiagram(Map<String, ClassInfo> classInfoMap, String outputFileName) throws IOException {
        // Configurar Graphviz para usar GraphvizCmdLineEngine
        Graphviz.useEngine(new GraphvizCmdLineEngine());

        // Crear nodos para cada clase
        Map<String, Node> nodes = new HashMap<>();
        for (String className : classInfoMap.keySet()) {
            ClassInfo classInfo = classInfoMap.get(className);
            Node classNode = node(className)
                    .with(Label.html("<b>" + className + "</b><br/>" + getClassDetailsHtml(classInfo)));
            nodes.put(className, classNode);
        }

        // Crear enlaces (relaciones entre clases)
        Graph g = graph("classDiagram").directed().with(
                nodes.values().toArray(new Node[0])
        );

        // Añadir relaciones entre clases (por ejemplo, herencia o uso de métodos)
        for (String className : classInfoMap.keySet()) {
            ClassInfo classInfo = classInfoMap.get(className);
            for (String relatedClass : classInfo.getRelatedClasses()) {
                g = g.with(node(className).link(to(node(relatedClass)).with(Link.to(node(relatedClass)))));
            }
        }

        // Renderizar el diagrama en formato PNG
        Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(outputFileName));
    }

    private static String getClassDetailsHtml(ClassInfo classInfo) {
        StringBuilder details = new StringBuilder();
        details.append("<b>Fields:</b><br/>");
        for (FieldInfo field : classInfo.getFields()) {
            details.append(escapeHtml(field.getFieldName())).append(" : ").append(escapeHtml(field.getFieldType())).append("<br/>");
        }
        details.append("<b>Methods:</b><br/>");
        for (MethodInfo method : classInfo.getMethods()) {
            details.append(escapeHtml(method.getMethodName())).append("(").append(escapeHtml(String.join(", ", method.getParameters()))).append(")<br/>");
        }
        return details.toString();
    }

    private static String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
