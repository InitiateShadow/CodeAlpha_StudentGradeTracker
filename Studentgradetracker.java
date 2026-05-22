import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Studentgradetracker{
    static class Student{
        String name;
        ArrayList<Double> grades;

        Student(String name){
            this.name=name;
            this.grades=new ArrayList<>();
        }
        void addGrade(double Grade){
            grades.add(Grade);

        }
        double getAverage(){
            if(grades.isEmpty()) return 0.0;
            double sum=0;
            for(double g:grades) sum+=g;
            return sum/grades.size();
        }
        double getHighest(){
            if(grades.isEmpty()) return 0.0;
            return Collections.max(grades);
        }
        double getLowest(){
            if(grades.isEmpty()) return 0.0;
            return Collections.min(grades);
        }
        String getLetterGrade(){
            double avg=getAverage();
            if(avg>=90) return "A";
            if(avg>=80) return "B";
            if(avg>=70) return "C";
            if(avg>=60) return "D";
            return "F";
        }
        @Override
       public String toString() {
            return String.format("%-20s | Grades: %-30s | Avg: %5.1f | High: %5.1f | Low: %5.1f | Grade: %s",
                    name,
                    grades.toString(),
                    getAverage(),
                    getHighest(),
                    getLowest(),
                    getLetterGrade());
        }
    }

    static Student findStudent(ArrayList<Student> students, String name){
        for(Student s: students){
            if(s.name.equalsIgnoreCase(name)) return s;
        }
        return null;
    }
    static void printDivider(){
        System.out.println("-".repeat(90));
    }
    static void printMenu(){
        System.out.println();
        printDivider();
        System.out.println("           STUDENT GRADE TRACKER - MAIN MENU");
        printDivider();
        System.out.println("1. Add a new Student:");
        System.out.println("2. Add grade(s) to a student:");
        System.out.println("  3. View a student's details");
        System.out.println("  4. Display full summary report");
        System.out.println("  5. Remove a student");
        System.out.println("  6. Exit");
        printDivider();
        System.out.print("  Choose an option (1-6): ");
    }
    static void displaySummary(ArrayList<Student> students) {
        System.out.println();
        printDivider();
        System.out.println("                        FULL SUMMARY REPORT");
        printDivider();
 
        if (students.isEmpty()) {
            System.out.println("  No students enrolled yet.");
            printDivider();
            return;
        }
 
        System.out.printf("  %-20s | %-30s | %-7s | %-7s | %-7s | %s%n",
                "Name", "Grades", "Average", "Highest", "Lowest", "Letter");
        printDivider();
 
        for (Student s : students) {
            System.out.println("  " + s);
        }
        double classTotal = 0;
        double classHigh  = Double.MIN_VALUE;
        double classLow   = Double.MAX_VALUE;
        String topStudent = "";
        String lowStudent = "";
 
        for (Student s : students) {
            if (s.grades.isEmpty()) continue;
            double avg = s.getAverage();
            classTotal += avg;
            if (avg > classHigh) { classHigh = avg; topStudent = s.name; }
            if (avg < classLow)  { classLow  = avg; lowStudent = s.name; }
        }
 
        double classAvg = classTotal / students.size();
        printDivider();
        System.out.printf("  CLASS AVERAGE : %.1f%n", classAvg);
        System.out.printf("  TOP STUDENT   : %s (%.1f)%n", topStudent, classHigh);
        System.out.printf("  LOWEST AVG    : %s (%.1f)%n", lowStudent, classLow);
        printDivider();
    }
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.println();
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.println("  ║       STUDENT GRADE TRACKER          ║");
        System.out.println("  ╚══════════════════════════════════════╝");
        
        boolean running =true;
        while(running){
            printMenu();
            String choice= sc.nextLine().trim();
            
            switch (choice) {
                case "1": {
                    System.out.println("Enter student name: ");
                    String name=sc.nextLine().trim();
                    if(name.isEmpty()){
                        System.out.println("[!] Name cannot be empty.");
                        break;
                    }
                    if (findStudent(students, name) != null) {
                        System.out.println("  [!] Student '" + name + "' already exists.");
                    } else {
                        students.add(new Student(name));
                        System.out.println("  [✓] Student '" + name + "' added successfully.");
                    }
                    break;
                }

                case "2": {
                    System.out.println("Enter student name:");
                    String name=sc.nextLine().trim();
                    Student s= findStudent(students, name);
                    if(s== null){
                        System.out.println("[!] Student not found.");
                        break;
                    }
                    System.out.println("Enter grades seperated by spaces (0-100):");
                    String[] tokens = sc.nextLine().trim().split("\\s+");
                    int added = 0;
                    for(String token:tokens){
                        try{
                            double grade = Double.parseDouble(token);
                            if (grade < 0 || grade > 100) {
                                System.out.println("  [!] Skipping out-of-range value: " + token);
                        } else{
                            s.addGrade(grade);
                            added++;
                        }
                    } catch (NumberFormatException e){
                         System.out.println("  [!] Skipping invalid value: " + token);
                        }
                    }
                    System.out.println("  [✓] " + added + " grade(s) added to " + s.name + ".");
                    break;
                }
                case "3": {
                    System.out.print("  Enter student name: ");
                    String name = sc.nextLine().trim();
                    Student s = findStudent(students, name);
                    if (s == null) {
                        System.out.println("  [!] Student not found.");
                        break;
                    }
                    System.out.println();
                    printDivider();
                    System.out.println("  STUDENT DETAILS");
                    printDivider();
                    System.out.println("  Name    : " + s.name);
                    System.out.println("  Grades  : " + s.grades);
                    System.out.printf ("  Average : %.1f (%s)%n", s.getAverage(), s.getLetterGrade());
                    System.out.printf ("  Highest : %.1f%n", s.getHighest());
                    System.out.printf ("  Lowest  : %.1f%n", s.getLowest());
                    printDivider();
                    break;
                }
                case "4": {
                    displaySummary(students);
                    break;
                }
                case "5": {
                    System.out.print("  Enter student name to remove: ");
                    String name = sc.nextLine().trim();
                    Student s = findStudent(students, name);
                    if (s == null) {
                        System.out.println("  [!] Student not found.");
                    } else {
                        students.remove(s);
                        System.out.println("  [✓] Student '" + name + "' removed.");
                    }
                    break;
                }
 
                case "6": {
                    System.out.println();
                    System.out.println("  Goodbye! Final report:");
                    displaySummary(students);
                    running = false;
                    break;
                }

                    
            
                default:
                    System.out.println("[!] Invalid option. Please enter 1-6.");
            }
        }

        sc.close();
    }
}

