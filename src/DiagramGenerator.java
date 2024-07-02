import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizCmdLineEngine;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.Node;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
            nodes.put(className, node(className));
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
}
