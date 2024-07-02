import java.io.*;
import java.util.*;

public class SequenceDiagramGenerator {
    public static void generateSequenceDiagram(List<SequenceEvent> events, String outputFileName) throws IOException {
        StringBuilder umlContent = new StringBuilder();
        umlContent.append("@startuml\n");

        for (SequenceEvent event : events) {
            umlContent.append(event.getFrom()).append(" -> ").append(event.getTo()).append(" : ").append(event.getMessage()).append("\n");
        }

        umlContent.append("@enduml\n");

        File umlFile = new File(outputFileName + ".puml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(umlFile))) {
            writer.write(umlContent.toString());
        }

        // Generar el diagrama de secuencia usando PlantUML
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "lib/plantuml-1.2024.5.jar", umlFile.getAbsolutePath());
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}

class SequenceEvent {
    private String from;
    private String to;
    private String message;

    public SequenceEvent(String from, String to, String message) {
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }
}
