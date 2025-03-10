package homework8;
/**
 * Дан список студентов с полями:
 * - Имя
 * - Фамилия
 * - Номер курса в университете
 * - Список оценок за учебу
 * Преобразовать этот список студентов в ассоциативный массив, где ключом является номер курса, а значением:
 * Средняя оценка студентов этого курса, количество оценок у которых больше 3-х
 * Список студентов данного курса, но только с полями Имя и Фамилия.
 * Список должен быть отсортированы по этим двум полям
 * Объект с двумя полями:
 * - Отсортированный список студентов с пункта 2
 * - Средняя оценка этих студентов
 * Подумать, как ассоциативный массив можно было представить в коде в виде отсортированного - TreeMap
 **/


import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

public class StudentList {
    public static void main(String[] args) {
        List<Student> students = List.of(
                new Student("Ivan", "Ivanov", 2, List.of(5, 3, 4, 4)),
                new Student("Petr", "Petrov", 3, List.of(4, 3, 5)),
                new Student("Sveta", "Svetikova", 3, List.of(4, 5, 4, 3)),
                new Student("Kate", "Ivanova", 1, List.of(4, 5, 5)),
                new Student("Slava", "Slavikov", 4, List.of(2, 3, 3)),
                new Student("Arni", "Kutuzov", 2, List.of(4, 5, 4, 5)));

        Map<Integer, Double> averageRatingsByCourse = students.stream()
                .filter(student -> student.getListOfRatings().size() > 3)
                .collect(Collectors.groupingBy(Student::getCourse,
                        Collectors.averagingDouble(student -> student.getListOfRatings()
                                .stream()
                                .mapToDouble(Integer::doubleValue)
                                .average()
                                .orElse(0))));
        System.out.println(averageRatingsByCourse);

        Map<Integer, List<String>> mapStudents = students.stream()
                .sorted(comparing(Student::getName).thenComparing((Student::getSurname)))
                .collect(Collectors.groupingBy(Student::getCourse,
                        Collectors.mapping(Student::getFullName, Collectors.toList())));
        System.out.println(mapStudents);

        Map<Integer, Map<Double, List<String>>> mapResult = students.stream()
                .sorted(comparing(Student::getSurname).thenComparing((Student::getName)))
                .collect(Collectors.groupingBy(Student::getCourse, TreeMap::new, Collectors.groupingBy(value -> value.getListOfRatings()
                        .stream()
                        .mapToDouble(Integer::doubleValue)
                        .average()
                        .orElse(0), TreeMap::new, Collectors.mapping(Student::getFullName, Collectors.toList()))));
        System.out.println(mapResult);

    }
}
