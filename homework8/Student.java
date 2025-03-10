package homework8;

import java.util.List;
import java.util.Objects;

public class Student {
    private String name;
    private String surname;
    private int course;
    private List<Integer> listOfRatings;

    public Student(String name, String surname, int course, List<Integer> listOfRatings) {
        this.name = name;
        this.surname = surname;
        this.course = course;
        this.listOfRatings = listOfRatings;
    }
    public String getFullName() {
        return name + " " + surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getCourse() {
        return course;
    }

    public List<Integer> getListOfRatings() {
        return listOfRatings;
    }

    @Override
    public String toString() {
        return "Student{" +
                "course=" + course +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", listOfRatings=" + listOfRatings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return course == student.course && Objects.equals(name, student.name) && Objects.equals(surname, student.surname) && Objects.equals(listOfRatings, student.listOfRatings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, course, listOfRatings);
    }
}
