package SmartHome;


import java.io.FileNotFoundException;
import java.io.IOException;

abstract class Sensor {
    private double defaultStatus;               // default status
    private double currentStatus;               // current status

    Sensor (double defStat, double curStat) {
        defaultStatus = defStat;
        currentStatus = curStat;
    }

    abstract void changeStatus (double change); // needed to be implemented in a subclass






    // TODO: Declare more methods if needed
}

//  TODO: Define classes for various classes which extend the abstract class Sensor
//      Each sensor class needs to declare variables which are presented in sensors.json
//      Check the assignment description and find proper data types for the variables
