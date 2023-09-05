package Lecture08;

import Utility.Benchmark;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonValue;

public class MyClass {
    static Properties prop;

    public static GradeBook createGradeBook(String dataName) throws Exception {
        // input
        String packageName = MyClass.class.getPackage().getName();
        String filePath = prop.getProperty("filepath") + File.separator + packageName + File.separator + "data" + File.separator + dataName + ".json";
        InputStream inputStream = new FileInputStream(filePath);

        // making jsonobject for reading json data
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        inputStream.close();

        // reading json data
        String crn = jsonObject.getString("crn");
        JsonArray jaStudents = jsonObject.getJsonArray("students");
        int numOfStudents = jaStudents.size();

        GradeBook rGradeBook = new GradeBook(crn, numOfStudents);

        int index = 0;
        for (JsonValue jvStudent : jaStudents) {
            rGradeBook.students[index] = new aStudent(jvStudent.asJsonObject().getString("name"), jvStudent.asJsonObject().getInt("finalExam"),
                    jvStudent.asJsonObject().getJsonNumber("grade").doubleValue());
            index++;
        }


        return rGradeBook;
    }

    public static void main(String[] args) throws Exception {
        // load properties
        prop = new Properties();
        InputStream cofIS = new FileInputStream("config.properties");
        prop.load(cofIS);

        GradeBook gradeBook = createGradeBook("grade");
//        gradeBook.printGradeBook();/

        // TODO: Assignment 5
        //  Implement sorting methods declared in GradeBook.java
        //  For sortByFinalScore(), try insertion sort or bubble sort
        //  For sortByGrade(), try merge sort or quick sort
        //  Details in the description of the assignment (will be posted on Wednesday, MArch 30)
        Scanner input = new Scanner(System.in);
        System.out.println("CHOOSE ONE");
        System.out.print("1: sort by name | 2: sort by final score | 3: sort by grade | 4: quick sort by grade(extra credit) | 5: store the sorted data |");
        String option = input.nextLine();
        if(option.compareTo("1") == 0 ) {
            gradeBook.sortByName();
        }
        else if (option.compareTo("2") == 0) {
            gradeBook.sortByFinalScore();
        }
        else if (option.compareTo("3") == 0) {
            gradeBook.sortByGrade();
        }
        else if (option.compareTo("4") == 0) {
            gradeBook.quicksortByGrade();
        }
        else if (option.compareTo("5") == 0) {
            gradeBook.quicksortByGrade();
            gradeBook.savedata();
        }


        else {
            System.out.println("Unknown option number");
            System.exit(0);
        }
        gradeBook.printGradeBook();
        System.out.println(" [Benchmark] The number of comparisons: " + Benchmark.getCounterLS());
    }
}