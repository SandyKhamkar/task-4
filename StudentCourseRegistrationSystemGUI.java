import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int registeredStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = 0;
    }

    // Getters and setters

    public int getAvailableSlots() {
        return capacity - registeredStudents;
    }

    public boolean registerStudent() {
        if (registeredStudents < capacity) {
            registeredStudents++;
            return true;
        }
        return false;
    }

    public boolean removeStudent() {
        if (registeredStudents > 0) {
            registeredStudents--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return courseCode + " - " + title;
    }
}

class Student {
    private int studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    // Getters and setters

    public void registerForCourse(Course course) {
        if (course.registerStudent()) {
            registeredCourses.add(course);
        }
    }

    public void removeCourse(Course course) {
        if (course.removeStudent()) {
            registeredCourses.remove(course);
        }
    }

    @Override
    public String toString() {
        return name + " (ID: " + studentId + ")";
    }
}

public class StudentCourseRegistrationSystemGUI {
    private List<Student> students;
    private List<Course> courses;
    private JFrame frame;
    private JComboBox<Student> studentComboBox;
    private JComboBox<Course> courseComboBox;

    public StudentCourseRegistrationSystemGUI() {
        students = new ArrayList<>();
        courses = new ArrayList<>();

        // Create sample data
        students.add(new Student(101, "Sandip"));
        students.add(new Student(102, "Tanmay"));
        courses.add(new Course("CS101", "Introduction to Programming", "An introductory programming course", 50, "MWF 9:00 AM"));
        courses.add(new Course("Math201", "Calculus I", "Calculus for beginners", 40, "TTH 10:30 AM"));
	courses.add(new Course("CS301", "Java Programming", "Java programming course", 50, "MWF 9:00 AM"));

        frame = new JFrame("Student Course Registration System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));

        createStudentComboBox();
        createCourseComboBox();
        createRegisterButton();
        createRemoveButton();

        frame.pack();
        frame.setVisible(true);
    }

    private void createStudentComboBox() {
        studentComboBox = new JComboBox<>(students.toArray(new Student[0]));
        frame.add(new JLabel("Select Student:"));
        frame.add(studentComboBox);
    }

    private void createCourseComboBox() {
        courseComboBox = new JComboBox<>(courses.toArray(new Course[0]));
        frame.add(new JLabel("Select Course:"));
        frame.add(courseComboBox);
    }

    private void createRegisterButton() {
        JButton registerButton = new JButton("Register");
        frame.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student selectedStudent = (Student) studentComboBox.getSelectedItem();
                Course selectedCourse = (Course) courseComboBox.getSelectedItem();

                if (selectedStudent != null && selectedCourse != null) {
                    selectedStudent.registerForCourse(selectedCourse);
                    JOptionPane.showMessageDialog(frame, "Registration successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a student and a course.");
                }
            }
        });
    }

    private void createRemoveButton() {
        JButton removeButton = new JButton("Remove");
        frame.add(removeButton);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student selectedStudent = (Student) studentComboBox.getSelectedItem();
                Course selectedCourse = (Course) courseComboBox.getSelectedItem();

                if (selectedStudent != null && selectedCourse != null) {
                    selectedStudent.removeCourse(selectedCourse);
                    JOptionPane.showMessageDialog(frame, "Course removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a student and a course.");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentCourseRegistrationSystemGUI();
            }
        });
    }
}
