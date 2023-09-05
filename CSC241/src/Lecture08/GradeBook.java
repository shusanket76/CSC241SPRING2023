package Lecture08;

import Utility.Benchmark;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static Lecture08.MyClass.prop;

class GradeBook {

    String crn;
    aStudent[] students;

    GradeBook (String crn, int numOfStudents) {
        this.crn = crn;
        students = new aStudent[numOfStudents];
    }

    // Selection Sort by Name
    void sortByName() {
        Benchmark.resetCounterLS();
        for (int i = 0; i < students.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < students.length; j++) {
                Benchmark.increaseCounterLS();
                if (students[j].name.compareTo(students[min].name) < 0) {
                    min = j;
                }
            }
            swapStudentsGrade(i, min);
        }
    }

    void sortByFinalScore() {

//        BUBBLE SORT
        Benchmark.resetCounterLS();
        for (int i=0;i<=students.length-1;i++){
            for (int y=0; y<=students.length-2-i;y++){
                Benchmark.increaseCounterLS();
                if(students[y].getFinalScore()>students[y+1].getFinalScore()){

                    swapStudentFinal(y,y+1);
                }
            }
        }
    }
//============================================
    void sortByGrade() {
        System.out.println("CHALLENGE: sort by grade based on merge sort");
        // TODO: Assignment 5, Challenge 2
        //  Implement merge sort or quick sort
        //  For Benchmark, use CounterLS in Benchmark class
//        Merge sort
        mergesort(students);
    }
    private void mergesort(aStudent[] students) {
        if (students.length<=1) {
            return;}
        int mid = students.length/2;
        aStudent[] left = new aStudent[mid];
        aStudent[] right = new aStudent[students.length-mid];

        int i =0;
        int j =0;

        for(int k = 0 ;k<students.length;k++){

            if(k<mid){
                left[i] = students[k];
                i=i+1;
            }
            else{
                right[j] = students[k];
                j=j+1;
            }
        }
        mergesort(left);
        mergesort(right);
        mergeTwoList(left,right,students);
    }
    private aStudent[] mergeTwoList(aStudent[] left, aStudent[] right, aStudent[] students) {
        int leftlen = left.length;
        int rightlen = right.length;
        int i = 0;
        int j=0;
        int k=0;
        Benchmark.resetCounterLS();
        while (i<leftlen || j<leftlen){
            Benchmark.increaseCounterLS();

            if (i<leftlen && j<rightlen){

                if (left[i].getGrade() < right[j].getGrade()){
                    students[k] = left[i];
                    i=i+1;
                } else{
                    students[k] = right[j];
                    j=j+1;
                }}
            else{
                if (i<leftlen){
                    students[k] = left[i];
                    i=i+1;}

                else{
                    students[k] = right[j];
                    j=j+1;}}

            k = k+1;

        }
        return students;
    }
//=====================================================================================

//      quicksort
    void quicksortByGrade(){
        Benchmark.resetCounterLS();quickSort(students,0,students.length);
    }
    void quickSort(aStudent[] students, int start, int end){

        if(start<end){
            int parind =    partation(students,  start,  end);
            quickSort(students, start, parind-1);
            quickSort(students,parind+1,end);
        }

    }
    private int partation(aStudent[] students, int start, int end){
        int partition_idx = start;
        double partition_num = students[partition_idx].getGrade();
        int leftIdx = partition_idx+1;
        int rightIdx = students.length-1;

        while (leftIdx<rightIdx) {
            Benchmark.increaseCounterLS();
            while (students[leftIdx].getGrade() <= partition_num) {
                leftIdx = leftIdx + 1;
            }

            while (students[rightIdx].getGrade() > partition_num) {
                rightIdx = rightIdx - 1;
            }

            if (leftIdx < rightIdx) {
                swap(leftIdx, rightIdx, students);
            }
        }

            swap(partition_idx,rightIdx,students);
            return rightIdx;

        }

        void swap(int num1, int num2, aStudent[] students){
        aStudent temp = students[num1];
        students[num1] = students[num2];
        students[num2] = temp;

        }





//    =====================================================================================
    void swapStudentsGrade(int curInd, int minInd) {
        aStudent tmpStudent = students[curInd];
        students[curInd] = students[minInd];
        students[minInd] = tmpStudent;
    }

    void swapStudentFinal(int back, int plusone){
        aStudent tmpStudent = students[plusone];
        students[plusone] = students[back];
        students[back] = tmpStudent;

    }

    void printGradeBook() {
        System.out.printf("%-12s%-7s%-1s\n","Name","Final","Grade");
        for(aStudent student: students) {
            student.printGrade();
        }
    }
//=========================================================================
    public void savedata() throws IOException {

        String packageName = MyClass.class.getPackage().getName();
        String dataName = "grade-by-finalscore";
        String filePath = prop.getProperty("filepath") + File.separator + "Lecture08" + File.separator +"output"+ File.separator+  dataName + ".json";
        OutputStream outputStream = new FileOutputStream(filePath);
        JsonWriter jsonWriter = Json.createWriter(outputStream);
        JsonObjectBuilder joBuilder = Json.createObjectBuilder();
        // creating objectbuilder and arraybuilder to write data to json file
        JsonArrayBuilder jaBuilder = Json.createArrayBuilder();
        for (int i=0;i<students.length-1;i++) {

                JsonObjectBuilder joStudentBuilder = Json.createObjectBuilder();
                joStudentBuilder.add("name", students[i].name);
                joStudentBuilder.add("finalExam", students[i].getFinalScore());
                joStudentBuilder.add("grade", students[i].getGrade());

                jaBuilder.add(joStudentBuilder);

        }
        joBuilder.add("crn","241");
        joBuilder.add("students",jaBuilder);
        JsonObject jsonObject = joBuilder.build();

        // Configuring format for Json file
        Map<String, Boolean> config = new HashMap<String, Boolean>();
        config.put(JsonGenerator.PRETTY_PRINTING,true);
        JsonWriterFactory factory = Json.createWriterFactory(config);
        jsonWriter = factory.createWriter(outputStream);

        jsonWriter.write(jsonObject);
        jsonWriter.close();
        outputStream.close();

        }



    }
//    ==========================================================================


class aStudent implements Comparable<aStudent>{
    String name;
    private Integer finScore;           // final score
    private Double grade;              // grade

    aStudent (String name, int finalScore, double grade) {
        this.name = name;
        this.finScore = finalScore;
        this.grade = grade;
    }

    aStudent (double grade) {
        name = "student";
        finScore = 80;
        this.grade = grade;
    }

    aStudent (int finalscore) {
        name = "student";
        finScore = finalscore;
        grade = 70.0;
    }

    int getFinalScore() {
        return finScore;
    }

    void updateFinalScore(int newScore) {
        this.finScore += newScore;
    }

    double getGrade() {
        return grade;
    }

    void updateGrade(double newGrade) {
        this.grade = newGrade;
    }

    // comparing students by final score
    public int compareTo(aStudent thatStudent) {
        if (finScore.compareTo(thatStudent.finScore) == 0)          // equal case
            return 0;
        else if (finScore.compareTo(thatStudent.finScore) > 0)      // final score of this student is greater
            return 1;
        else                                                        // final score of this student is less
            return -1;
    }

    void printGrade() {
        System.out.printf("%-12s%-7d%.1f\n",name,finScore,grade);
    }
}