
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentFileHandler {

    private final Path filePath = Paths.get("students.txt");

    public Map<Integer, Student> loadStudents() {
        Map<Integer, Student> students = new HashMap<>();

        if (!Files.exists(filePath)) {
            return students;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] data = line.split(",", -1);

                    if (data.length != 5) {
                        System.out.println(
                                "Skipping invalid record: " + line);
                        continue;
                    }

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    int age = Integer.parseInt(data[2]);
                    String course = data[3];
                    double marks = Double.parseDouble(data[4]);

                    Student student = new Student(
                            id, name, age, course, marks);

                    if (students.containsKey(id)) {
                        System.out.println(
                                "Skipping duplicate student ID: " + id);
                        continue;
                    }

                    students.put(id, student);

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Skipping invalid record: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Could not load student records: "
                            + e.getMessage());
        }

        return students;
    }

    public boolean saveStudents(Map<Integer, Student> students) {
        List<String> lines = new ArrayList<>();

        for (Student student : students.values()) {
            String line = student.getId() + ","
                    + student.getName() + ","
                    + student.getAge() + ","
                    + student.getCourse() + ","
                    + student.getMarks();

            lines.add(line);
        }

        try {
            Files.write(
                    filePath,
                    lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE
            );

            return true;

        } catch (IOException e) {
            System.out.println(
                    "Could not save student records: "
                            + e.getMessage());
            return false;
        }
    }
}

