import java.io.*;
import java.util.*;

public class ActivityDiagramGenerator {
    public static void generateActivityDiagram(List<ActivityStep> steps, String outputFileName) throws IOException {
        StringBuilder umlContent = new StringBuilder();
        umlContent.append("@startuml\n");
        umlContent.append("start\n");

        for (ActivityStep step : steps) {
            umlContent.append(":" + step.getAction() + ";\n");
        }

        umlContent.append("stop\n");
        umlContent.append("@enduml\n");

        File umlFile = new File(outputFileName + ".puml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(umlFile))) {
            writer.write(umlContent.toString());
        }

        // Generar el diagrama de actividad usando PlantUML
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

class ActivityStep {
    private String action;

    public ActivityStep(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
