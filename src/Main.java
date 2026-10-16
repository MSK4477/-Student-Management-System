
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService service = new StudentService();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            displayMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    service.viewAllStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    System.out.println("Exiting Student Management System.");
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Enter a number from 1 to 6.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
    }

    private static void addStudent() {
        System.out.println("\n--- Add Student ---");

        int id = readPositiveInt("Enter student ID: ");
        String name = readNonEmptyString("Enter student name: ");
        int age = readPositiveInt("Enter age: ");
        String course = readNonEmptyString("Enter course: ");
        double marks = readMarks("Enter marks (0-100): ");

        Student student = new Student(id, name, age, course, marks);

        service.addStudent(student);
    }

    private static void searchStudent() {
        int id = readPositiveInt("Enter student ID to search: ");
        service.searchStudent(id);
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");

        int id = readPositiveInt("Enter student ID to update: ");

        String name = readNonEmptyString("Enter new name: ");
        int age = readPositiveInt("Enter new age: ");
        String course = readNonEmptyString("Enter new course: ");
        double marks = readMarks("Enter new marks (0-100): ");

        service.updateStudent(id, name, age, course, marks);
    }

    private static void deleteStudent() {
        int id = readPositiveInt("Enter student ID to delete: ");
        service.deleteStudent(id);
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static int readPositiveInt(String message) {
        while (true) {
            int value = readInt(message);

            if (value > 0) {
                return value;
            }

            System.out.println("Value must be greater than zero.");
        }
    }

    private static double readMarks(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                double marks = Double.parseDouble(input);

                if (Double.isFinite(marks)
                        && marks >= 0 && marks <= 100) {
                    return marks;
                }

                System.out.println("Marks must be between 0 and 100.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readNonEmptyString(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty() && !input.contains(",")) {
                return input;
            }

            System.out.println(
                    "Input cannot be empty or contain a comma.");
        }
    }
}
