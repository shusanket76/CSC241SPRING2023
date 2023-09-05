package SmartHome;
import javax.json.*;
import java.io.*;
import java.util.*;

public class PressureSensor extends Sensor{
    String Owner, Location, Phone, NumberofSensors, Sensors, ID,Type,Object;
    double DefaultStatus,Status;

    PressureSensor(double defStat, double curStat) {
        super(defStat, curStat);
    }

    @Override
    void changeStatus(double change)  {
        Status = change;

//
    }

}
