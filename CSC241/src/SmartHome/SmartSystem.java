//Assignment 7
//Name:Shusanket Basyal


package SmartHome;
//import javax.json;
import java.io.File;
import java.io.FileWriter;

import Utility.Benchmark;

import javax.json.*;
import java.io.*;
import java.util.*;

public class SmartSystem {
    public static void main(String[] args) throws IOException {
       ;
//        ===================================================
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("config.properties");     // config.properties MUST be placed in the TOP directory of this project
        prop.load(fis);
        System.out.println("SmartHome System is running now.");
        // Loading sensor data in Json file which is placed in src/Data/sensors.json
        String sensorData = "sensors";
        String sensorFilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + sensorData + ".json";
        File sensorFile = new File(sensorFilePath);
        System.out.println("Sensor data has been loaded.");

        // Loading sensory data in src/Data/senData.txt
        String sensoryData = "senData";
        String sensoryFilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + sensoryData + ".txt";
        File inputFile = new File(sensoryFilePath);
        System.out.println("Sensory data has been loaded.");

        //Loading activities.json
        String activity_json = "activities";
        String afilepath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + activity_json + ".json";


        //Loading space data
        String spaceData = "spaces";
        String spaceFilePath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + spaceData + ".json";

        File spaceFile = new File(spaceFilePath);
        System.out.println("Sensor data has been loaded.");

        InputStream inputStream = new FileInputStream(sensorFile);
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        String owner = jsonObject.getString("Owner");
        String llocation = jsonObject.getString("Location");
        String pphone = jsonObject.getString("Phone");

        System.out.println("Owner==> " + owner);
        System.out.println("Location==> " + llocation);
        System.out.println("Phone Number==> " + pphone);
//=========================================================================


        //=====================================================
        analyzeData(inputFile, sensorFilePath, spaceFilePath, afilepath);
    }



//    ============================================================================================================
    private static void analyzeData(File senData, String sensorFile, String spaceFile, String afilepath) throws IOException {
//        CREATING A HASHSET TO INCLUDE THE UNIQUE SENSORE
        HashSet<String> uniqueSensors = new HashSet<String>();
//        CREATING A HASMAP TO ADD SENSORS AND ITS OCCURANCE.
        HashMap<String, Integer> sensorAndEvents = new HashMap<String, Integer>();
//        CREATING A RUN VARIABLE THROUGH WHICH I CAN STOP THE PROGRAM WHEN PRESSED 3
        boolean run = true;
//        CREATING A SHOW VARIABLE THROUGH WHICH I CAN DISPLAY THE CONTENT WHEN PRESSED 1
        boolean show = true;
        boolean first = true;
        int num = 0;
        boolean secondormore = false;
// START OF THE CODE.
        while (run){
// WHENEVER THE CODE RUNS BOTH THE BOOLEAN VALUE ARE SET TRUE. SO THAT I CAN DISPLAY THE CONTENT OF FILE.
        if ( show ) {

            try {
                Scanner line = new Scanner(senData);
// SKIPPING A LINE SO THAT IT DOESNOT READ TIME ID AND VALUE LINE
                if(secondormore){
                    System.out.println(line.nextLine());
                }
                else{
                    line.nextLine();
                }


                while (line.hasNext()) {
                    String i = line.nextLine();
                    //   PRINTING EACH OF THE LINE.
                    if(secondormore){
                        System.out.println(i);
                    }

//I HAVE TO CHECK WHETHER THIS CODE IS RUNNING FOR FIRST TIME OR NOT IF IT IS RUNNING FOR FIRST TIME
//  THEN THE CODE DOWN BELOW WILL BE EXECUTED. IF THE CODE IS NOT RUNNING FOR THE FIRST TIME THE CODE DOWN BELOW
//  WILL NOT BE EXECUTED.
                    if (first) {


// CHECKING THE INDEX OF 's' AS THERE IS ONLY 1 OCCURANCE OF 's IN EACH LINE
                        num = i.lastIndexOf('s');
// WITH THE HELP OF "num" VARIABLE GETTING THE STRING VALUE OF SENSOR.
                        String sensor = i.substring(num, num + 3);
// AFTER GETTING THE VALUE OF SENSOR ADDING THAT VALUE IN THE HASHSET. HASHSET WILL ONLY ALLOW TO INCLUDE UNIQUE VALUES.
                        uniqueSensors.add(sensor);
// WITH THE HELP OF HASMAP, IT HELPS US TO DECIDE WHETHER THE VALUE IS INCLUDED IN THE HASMAP OR NOT. AND HELPS
//   US TO KEEP TRACK OF THE EVENTS
//                 IF THE VALUE OF SENSOR IS IN HASMAP:
                        if (sensorAndEvents.containsKey(sensor)) {
//                  I WILL GET THE VALUE OF THAT KEY AND UPDATE THE VALUE BY 1
                            int val = sensorAndEvents.get(sensor);
                            val = val + 1;
//                        AFTER UPDATING, REPLACING THE VALUE IN HASMAP
                            sensorAndEvents.replace(sensor, val);
                        }
//                     IF THE VALUE OF SENSOR IS NOT IN HASMAP, I WILL ADD THE VALUE IN HASMAP
                        else {
                            sensorAndEvents.put(sensor, 1);
                        }
                    }


                }


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
//        CREATED AN ARRAYLIST TO CONVERT THE HASHSET WHICH CONTAINS UNIQUE VALUE OF SENSOR TO LIST SO THAT I CAN
//            SORT THE LIST.

            ArrayList<String> sensorlist = new ArrayList<>(uniqueSensors);
            Collections.sort(sensorlist);
//            GUI - ASKING USER TO CHOSE A VALUE FROM MENU

            Scanner menu = new Scanner(System.in);
            System.out.println("Choose menu [1:view 2:analize 3:sensor 0:quit 4:rooms 5:search 6:evaluate]?");
            int val = menu.nextInt();
//            ONCE I HAVE SHOWN THE VALUE I WILL MAKE show variable false. so it will not allow to run the code
//            again
            first = false;

            show = false;
            if(val==1){
//                VIEWING THE CONTENT OF THE TEXT FILE
                show = true;
                secondormore = true;
            } else if (val==0) {
//                EXITING FROM THE PROGRAM
                run=false;

            }
            else if(val==3){
                sensorPress3(sensorFile);
            }
            else if (val==2 ){
//                HERE I THOUGH ADDING THE CODE WOULD MAKE MESSY SO I MADE ANOTHER METHOD.
                analyzingeachLine(senData, sensorlist, sensorAndEvents, num);
            }
            else if (val==4){
                displayingroomData(sensorFile, spaceFile);

            }
            else if(val==5){
                searchingData(sensorFile,senData);
            }

            else if(val==6){
                linkedlistimplement(afilepath, senData);
            }
            else{
                System.out.println("SORRY, I CANNOT UNDERSTAND YOUR VALUE");
            }


        }
    }
//==============================================================================
    private static void linkedlistimplement(String activitiesfilename, File senData) throws IOException {
        int totalActivities= 0;
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("config.properties");     // config.properties MUST be placed in the TOP directory of this project
        prop.load(fis);
        InputStream inputStream = new FileInputStream(activitiesfilename);
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        JsonArray jsonArray = jsonObject.getJsonArray("Activities");
        String activity_json = "activity_";
        Queue<String> queue = new LinkedList<String>();


        for(int i=0; i<=jsonArray.size()-1;i++){


        JsonObject jobject = jsonArray.getJsonObject(i);
        String id = jobject.getString("ID");
        String name = jobject.getString("Name");

        NewLinkedList lname = new NewLinkedList();

        String afilepath = prop.getProperty("filepath") + File.separator + "Data" + File.separator + activity_json +id+ ".txt";

        File activitiesinputFile = new File(afilepath);

        Scanner activitiessc = new Scanner(activitiesinputFile);

        int k = i+1;
        String showingID="01";
        if(k<10){
            showingID = "0"+k;
        }
        else{
            showingID= String.valueOf(k);
        }


        while(activitiessc.hasNext()){
            String val = activitiessc.nextLine();
            String neval = val.replace(',',' ');


            lname.insertLast(neval);
            }

            Scanner textsc = new Scanner(senData);
            boolean firstfound = false;
            NewLinkedList.Node lnameOp = lname.headPtr;
            String previousNode = null;
            String previousID = "0";
            while(textsc.hasNext()){
                String text = textsc.nextLine();
                String textId = text.substring(22,25);
                String activityNode= (String) lnameOp.getData();
                String activityId = activityNode.substring(0,3);
                if(lnameOp.getPrev()!=null){
                     previousNode = (String) lnameOp.getPrev().getData();
                     previousID = previousNode.substring(0,3);
                }

                if(lnameOp.getNext()==null){
                    String filename = prop.getProperty("filepath") + File.separator + "Output" + File.separator +"senData_a"+showingID+".txt";

                    File file = new File(filename);
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileWriter writer = new FileWriter(file);
                    totalActivities = totalActivities+1;
                    System.out.println(totalActivities +" ACTIVITIES FOUND ");
                    System.out.println("[ ACTIVITY " + showingID + " - " + name +" ]" );
                    System.out.printf("%-30s%-10s%-10s%n", "DATE", "ID", "STATUS");
                    while (!queue.isEmpty()){
                        String te = queue.poll();

                        writer.write(te);
                        writer.write(System.getProperty( "line.separator" ));
                        String date = te.substring(0,20);
                        String iddisplay = te.substring(22,25);
                        String status = te.substring(26,te.length());
                        System.out.printf("%-30s%-10s%-10s%n",date,iddisplay,status);
                    }
                    writer.close();
                    break;
                }

                if(textId.equals(activityId) || textId.equals(previousID)){
                    firstfound = true;

                }
                else{
                    firstfound=false;
                    lnameOp = lname.headPtr;
                    queue.clear();
                }
                if(firstfound){
                    queue.offer(text);
                    lnameOp = lnameOp.getNext();
                }
            }

        }



    }


    private static void sensorPress3(String sensorFile) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(sensorFile);
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        JsonArray jsonArray = jsonObject.getJsonArray("Sensors");


        int first = 0;

        for (int i= 0; i< jsonArray.size(); i++){
            JsonObject jobject = jsonArray.getJsonObject(i);

            String id = String.valueOf(jobject.getString("ID"));
            int idnum = 15-id.length();
            String spaceid = new String(new char[idnum]).replace('\0', ' ');
            String type = String.valueOf(jobject.getString("Type"));
            int typenum = 15-type.length();
            String spacetype = new String(new char[typenum]).replace('\0', ' ');
        String object = String.valueOf(jobject.getString("Object"));
            int objectnum = 15-object.length();
            String spaceobject = new String(new char[objectnum]).replace('\0', ' ');
        String Location = String.valueOf(jobject.getString("Location"));
            int locationum = 20-Location.length();
            String spacelocation = new String(new char[locationum]).replace('\0', ' ');
       String defaultStatus = String.valueOf(jobject.get("Default Status"));

           String status = String.valueOf(jobject.get("Status"));
            int statusnum = 15-status.length();
            String spacestatus = new String(new char[statusnum]).replace('\0', ' ');
            if (first==0){
                System.out.println("ID"+spaceid + "  "+"TYPE"+spacetype + "OBJECT"+spaceobject+"LOCATION"+"          "+"STATUS"+ "     "+"Default Status" );
                first = first+1;
             }
           System.out.println(id+spaceid+type+spacetype+object+spaceobject+Location+spacelocation+status+spacestatus+defaultStatus );

        }
    }

    private static void analyzingeachLine(File senData, ArrayList<String> sensorList, HashMap<String, Integer> sensorAndEvents, int num) {
//        GETTING THE LENGTH OF ARRAYLIST WHICH HAS UNIQUE VALUES.
        int length = sensorList.size();


        try {
            for(int i = 0; i<length; i++){

                Scanner newScanner = new Scanner(senData);
//                HERE SINCE OUR ARRAYLIST HAS ALREADY BEEN SORTED WE NOW USED A FOR LOOP TO GET VALUE IN ASCENDING ORDER
//                AND ALSO I DISPLAY THE NUMBER OF EVENTS FROM HASMAP.
                System.out.println(sensorList.get(i) +" " + sensorAndEvents.get(sensorList.get(i)) + " event(s)");
//                PRINTING TIME ID AND VALUE
                System.out.println(newScanner.nextLine());
                while (newScanner.hasNext()){
                    String xyc = newScanner.nextLine();
//                    OVER HERE I AM CHECKING IF THE SENSOR IS EQUAL TO THE sensorList.get(i). IF THEY ARE EQUAL
//                    I WILL DISPLAY THAT LINE
                if (sensorList.get(i).equals(xyc.substring(num,num+3))){
                    System.out.println(xyc);
                }

//AND THAT WAS ALL THANK YOU FOR BEING UNTIL HERE I KNOW THIS IS NOT THE MOST EFFICIENT CODE:)(


            }}
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }



    private static void displayingroomData(String sensorFile, String spaceFile) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(sensorFile);
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        JsonArray sensors = jsonObject.getJsonArray("Sensors");
//        ===========================
        InputStream inputStream1 = new FileInputStream(spaceFile);
        JsonReader jsonReader1 = Json.createReader(inputStream1);
        JsonObject jsonObject1 = jsonReader1.readObject();
        JsonArray rooms = jsonObject1.getJsonArray("Rooms");
        int first = 0;
//=======================================================================
        for(int i = 0; i<= rooms.size()-1; i++){

            JsonObject singleobject = rooms.getJsonObject(i);
            int numberOfSensors = singleobject.getInt("Number of Sensors");
            String name = singleobject.getString("Name");

            if (numberOfSensors==0){
                System.out.println("[ " + name +" ]" + " 0 sensors");

            }
            else{

                int sensorval = singleobject.getInt("Number of Sensors");
                System.out.println("[ " + name +" ] " + sensorval + " sensors");
                JsonArray sensorName = singleobject.getJsonArray("Sensors");
                for (int y = 0; y<=sensorName.size()-1; y++){

                    JsonObject singleobj = sensorName.getJsonObject(y);
                    String singleidFromRooms = singleobj.getString("ID");
                    for (int z = 0; z<=sensors.size()-1; z++){
                        JsonObject sensorFromSensor = sensors.getJsonObject(z);
                        String sensoridFromsensor = sensorFromSensor.getString("ID");
                        if(sensoridFromsensor.equals(singleidFromRooms)){


                            String id = sensorFromSensor.getString("ID");
                            int idnum = 15-id.length();
                            String spaceid = new String(new char[idnum]).replace('\0', ' ');
                            String type = sensorFromSensor.getString("Type");
                            int typenum = 15-type.length();
                            String spacetype = new String(new char[typenum]).replace('\0', ' ');
                            String object = sensorFromSensor.getString("Object");
                            int objectnum = 15-object.length();
                            String spaceobject = new String(new char[objectnum]).replace('\0', ' ');
                            String Location = sensorFromSensor.getString("Location");
                            int locationum = 20-Location.length();
                            String spacelocation = new String(new char[locationum]).replace('\0', ' ');
                            String defaultStatus = String.valueOf(sensorFromSensor.get("Default Status"));

                            String status = String.valueOf(sensorFromSensor.get("Status"));
                            int statusnum = 15-status.length();
                            String spacestatus = new String(new char[statusnum]).replace('\0', ' ');
                            if (first==0){

                                System.out.println("ID"+spaceid + "  "+"TYPE"+spacetype + "OBJECT"+spaceobject+"LOCATION"+"          "+"STATUS"+ "     "+"Default Status" );
                                first = first+1;
                            }


                            System.out.println(id+spaceid+type+spacetype+object+spaceobject+Location+spacelocation+status+spacestatus+defaultStatus );


                        }
                    }


                }
                first=0;
            }

        }






    }
    //////////////////////////////////////////////////////////
//    5=SEARCH
    private static void searchingData(String sensorFile, File sensorData) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("WHAT DATA DO YOU WANT TO SEARCH? [1:event 2:sensors]");
        int oneOrtwo = scanner.nextInt();

        if(oneOrtwo==2){
//IF USER ENTER 2 THEN searchingSensor METHOD WILL BE IMPLEMENTED.
            System.out.println("WHAT SENSOR KEY DO YOU WANT TO SEARCH?");
            String keyTOSearch = scanner.next();
            searchingSensor(sensorFile, keyTOSearch);
        }
        else if(oneOrtwo==1){
//IF USER ENTER 1 searchingDataFromtxt METHOD WILL BE IMPLEMENTED.
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("ENTER THE START TIME");
            String startTime = scanner2.nextLine();
            System.out.println("ENTER THE END TIME");
            String endTime = scanner2.nextLine();

            searchingDataFromtxt(sensorData, startTime,endTime);
        }

    }



    private static void searchingSensor(String sensorFile, String keyTOSearch) throws FileNotFoundException  {
        InputStream inputStream = new FileInputStream(sensorFile);
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        JsonArray sensors = jsonObject.getJsonArray("Sensors");
//        =====================================================================
//            LINEAR SEARCH
        System.out.println("IMPLEMENTING LINEAR AND BINARY SEARCH");
        int twoKey = Integer.parseInt(keyTOSearch.substring(1,3));
        if(twoKey> sensors.size()-1){
            System.out.println("TO MUCH DATA");
        }
        else{
            Benchmark.resetCounterBS();
            for (int i=0;i< sensors.size()-1;i++){
                Benchmark.increaseCounterBS();
                JsonObject singleSensorVal = (JsonObject) sensors.get(i);
                String sensorID= singleSensorVal.getString("ID");
                String spaceid = "            ";
                String spacetype = "          ";
                String spaceobject = "          ";


                if(sensorID.equals(keyTOSearch)){
                    System.out.println("SENSOR FOUND");
                    System.out.println("ID"+spaceid + "  "+"TYPE"+spacetype + "OBJECT"+spaceobject+"LOCATION"+"          "+"STATUS"+ "     "+"Default Status" );
                    String id = singleSensorVal.getString("ID");
                    int idnum = 15-id.length();
                    spaceid = new String(new char[idnum]).replace('\0', ' ');
                    String type = singleSensorVal.getString("Type");
                    int typenum = 15-type.length();
                    spacetype = new String(new char[typenum]).replace('\0', ' ');
                    String object = singleSensorVal.getString("Object");
                    int objectnum = 15-object.length();
                    spaceobject = new String(new char[objectnum]).replace('\0', ' ');
                    String Location = singleSensorVal.getString("Location");
                    int locationum = 20-Location.length();
                    String spacelocation = new String(new char[locationum]).replace('\0', ' ');
                    String defaultStatus = String.valueOf(singleSensorVal.get("Default Status"));

                    String status = String.valueOf(singleSensorVal.get("Status"));
                    int statusnum = 15-status.length();
                    String spacestatus = new String(new char[statusnum]).replace('\0', ' ');

                    System.out.println(id+spaceid+type+spacetype+object+spaceobject+Location+spacelocation+status+spacestatus+defaultStatus );
                    System.out.println("BY LINEAR SEARCH " + Benchmark.getCounterBS() +" COMPARISONS");



                }
            }
//        ========================================================================
//        DONE WITH LINEAR SEARCH
//        =======================================================================
//    IMPLEMENTING BINARY SEARCH

            int leftPointer=0;
            int rightpointer = sensors.size()-1;
            Benchmark.resetCounterBS();
            while(leftPointer!=rightpointer){

                Benchmark.increaseCounterBS();
                int mid = (leftPointer+rightpointer)/2;

                if(mid==twoKey){

                    System.out.println("BY BINARY SEARCH " + Benchmark.getCounterBS()+" COMPARISON");



                    break;

                }
                else if(mid>twoKey){
                    rightpointer=mid;
                }
                else if (mid<twoKey){
                    leftPointer=mid;
                }
            }

        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
//WHEN USER WANT TO SEARCH DATA FROM TXT FILE
    private static void searchingDataFromtxt(File sensorData, String startTime, String endTime) throws FileNotFoundException {


        Scanner fs = new Scanner(sensorData);
        ArrayList<ArrayList> senArrayofArray = new ArrayList<>();
//GETTING THE VALUE FROM TXT AND ADDING IT TO THE ARRAY
//        AGAIN ADDING THAT ARRAY TO ANOTHER ARRAY.
        while(fs.hasNext()){
            ArrayList<String> senDataArray = new ArrayList<>();
            senDataArray.add(fs.nextLine());
            senArrayofArray.add(senDataArray);
        }


// GETTING THE TIME STRING OF START TIME FROM USER GIVEN VALUE
        String givenLeftStringTime = startTime.substring(12,20);
//        CHANGING THE TIME STRING TO INT
        int givenLeftIntTime = Integer.parseInt(givenLeftStringTime.replace(":",""));
//        GETTING TH TIME STRING OF END TIME FROM USER GIVEN VALUE
        String givenRightStringTime = endTime.substring(12,20);
//        CHANGING THE TIME STRING TO INT
        int givenrighIntTime = Integer.parseInt(givenRightStringTime.replace(":",""));
//        ALSO I NEED TO MAKE SURE THE DATE FROM USER GIVEN VALUE IS ALWAYS JAN-25-2023 BOTH IN START TIME AND END TIME.
        String startdate = startTime.substring(0,11);
        String endDate = endTime.substring(0,11);


//        SINCE WE ONLY HAVE DATA FOR JAN-25-2023. FIRST I WILL COMPARE WHETHER THE DATE IS SAME OR DIFFERENT.
        if(startdate.equals("Jan-25-2023") && endDate.equals("Jan-25-2023")){
//            NOW I WILL CHECK WHETHER THE USER GIVEN TIME FALLS UNDER OUR DATA OR NOT.
            if(givenLeftIntTime<returnTime(senArrayofArray, senArrayofArray.size()-1) && givenrighIntTime<returnTime(senArrayofArray, senArrayofArray.size()-1)  && givenLeftIntTime>returnTime(senArrayofArray, 0)  && givenrighIntTime>returnTime(senArrayofArray, 0) ){
//                I WILL FIRST IMPLEMENT BINARY SEARCH ON LEFT TIME VALUE
                int leftAnswer = 0;
                int Rightanswer=0;

                int subtract1;
                boolean leftIteration = true;

                int binarySearchLeftPointerForLeft = 0;
                int binarySearchRightPointerForLeft =senArrayofArray.size()-1;
//                ====================================================SEARCHING LEFT VALUE USING BINARY SERACH========================
              while(leftIteration) {
                  int index = (binarySearchLeftPointerForLeft + binarySearchRightPointerForLeft) / 2;
                  subtract1 = givenLeftIntTime - returnTime(senArrayofArray, index);

                  if (subtract1 == 0) {


                      leftAnswer = index + 1;
                      leftIteration = false;
                  }
                  else if (binarySearchLeftPointerForLeft + 1 == binarySearchRightPointerForLeft ) {
                      leftAnswer = binarySearchRightPointerForLeft;
                      leftIteration = false;
                  }

                  else if (subtract1 < 0) {

                      binarySearchRightPointerForLeft = index;
                  } else if (subtract1 > 0) {

                      binarySearchLeftPointerForLeft = index;
                  }


              }

//              =================SEARCHING FOR CORRECT VALUE IN END TIME USING BINARY SEARCH====================================================


                int subtract2;
                boolean rightIteration = true;

                int binarySearchLeftPointerForRight = 0;
                int binarySearchRightPointerForRight =senArrayofArray.size()-1;
//          ====================================================SEARCHING LEFT VALUE USING BINARY SERACH========================
                while(rightIteration) {
                    int index = (binarySearchLeftPointerForRight + binarySearchRightPointerForRight) / 2;
                    subtract2 = givenrighIntTime - returnTime(senArrayofArray, index);

                    if (subtract2 == 0) {
                        Rightanswer = index - 1;
                        rightIteration = false;

                    }
                    else if (binarySearchLeftPointerForRight + 1 == binarySearchRightPointerForRight) {
                        Rightanswer = binarySearchLeftPointerForRight;
                        rightIteration = false;

                    } else if (subtract2 < 0) {
                        binarySearchRightPointerForRight = index;

                    } else if (subtract2 > 0) {
                        binarySearchLeftPointerForRight = index;

                    }


                }
// SINCE I FOUND THE STARTING AND ENDING POINT, I CAN SIMPLY USE FOR LOOP TO ITERATE OVER THE ARRAY AND SHOW THE
//                DESIRED OUTPUT

            displayDataForEvents(senArrayofArray,leftAnswer,Rightanswer);


            }

            else{
                System.out.println("TIME OUT OF BOUND");
            }

        }
        else{
            System.out.println("DATE OUT OF BOUND");
        }



    }

    private static void displayDataForEvents(ArrayList<ArrayList> senArrayofArray, int leftAnswer, int rightanswer) {
        System.out.printf("%-30s%-10s%-10s%n", "TIME", "ID", "VALUE" );
        for(int i = leftAnswer;i<=rightanswer;i++){
            String singledata = (String) senArrayofArray.get(i).get(0);
            String date = singledata.substring(0,20);
            String id = singledata.substring(22,25);
            String value = singledata.substring(26,singledata.length());
            System.out.printf("%-30s%-10s%-10s%n", date, id, value);
        }
    }

    private static int returnTime(ArrayList<ArrayList> arrayL, int val){
        //        GETTING THE  VALUE FROM ARRAY
        String pointer = String.valueOf(arrayL.get(val));
        //        GETTIN THE JUST THE TIME STRING FROM POINTER
        String stringTime = pointer.substring(13,21);
//        CHANGING THE TIME STRING TO INT
        int time = Integer.parseInt(stringTime.replace(":",""));
        return time;


    }
}





