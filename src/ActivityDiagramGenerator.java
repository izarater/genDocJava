import java.io.*;
import java.util.List;

public class ActivityDiagramGenerator {

    public static void generateActivityDiagram(List<ActivityStep> steps, String outputFileName) throws IOException {
        String pumlFileName = outputFileName + ".puml";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pumlFileName))) {
            writer.write("@startuml\n");
            writer.write("start\n");

            for (ActivityStep step : steps) {
                writer.write(":" + step.getStepName() + ";\n");
            }

            writer.write("stop\n");
            writer.write("@enduml\n");
        }

        // Convertir el archivo PlantUML a un formato de imagen
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "lib/plantuml-1.2024.5.jar", pumlFileName);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ActivityStep {
    private String stepName;

    public ActivityStep(String stepName) {
        this.stepName = stepName;
    }

    public String getStepName() {
        return stepName;
    }
}
