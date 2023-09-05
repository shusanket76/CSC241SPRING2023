package SmartHome;

public class ContactSensor extends Sensor{
    String Owner, Location, Phone, NumberofSensors, Sensors, ID,Type,Object;
    double DefaultStatus,Status;

    ContactSensor(double defStat, double curStat) {
        super(defStat, curStat);
    }
    @Override
    void changeStatus(double change)  {
        Status = change;

//
    }
}
