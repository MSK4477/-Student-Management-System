
import java.util.HashMap;
import java.util.Map;

public class StudentService {

    private final Map<Integer, Student> students;
    private final StudentFileHandler fileHandler;

    public StudentService() {
        fileHandler = new StudentFileHandler();
        students = new HashMap<>(fileHandler.loadStudents());
    }

    public void addStudent(Student student) {
        if (students.containsKey(student.getId())) {
            System.out.println("Student ID already exists.");
            return;
        }

        students.put(student.getId(), student);

        if (saveChanges()) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println(
                    "Student added in memory, but saving failed.");
        }
    }

    public void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n===== ALL STUDENTS =====");

        for (Student student : students.values()) {
            System.out.println(student);
        }
    }

    public void searchStudent(int id) {
        Student student = students.get(id);

        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println(student);
        }
    }

    public void updateStudent(
            int id, String name, int age,
            String course, double marks) {

        Student student = students.get(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        // Keep the original values in case saving fails.
        Student updatedStudent =
                new Student(id, name, age, course, marks);

        students.put(id, updatedStudent);

        if (saveChanges()) {
            System.out.println("Student updated successfully.");
        } else {
            students.put(id, student);
            System.out.println(
                    "Update could not be saved; previous data restored.");
        }
    }

    public void deleteStudent(int id) {
        Student student = students.remove(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        if (saveChanges()) {
            System.out.println("Student deleted successfully.");
        } else {
            students.put(id, student);
            System.out.println(
                    "Deletion could not be saved; record restored.");
        }
    }

    private boolean saveChanges() {
        return fileHandler.saveStudents(students);
    }
}

