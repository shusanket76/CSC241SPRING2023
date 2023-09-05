package SmartHome;

public class LightSensor extends Sensor{
    String Owner, Location, Phone, NumberofSensors, Sensors, ID,Type,Object;
    double DefaultStatus,Status;
    LightSensor(double defStat, double curStat) {
        super(defStat, curStat);
    }

    @Override
    void changeStatus(double change)  {
        Status = change;

//
    }
}
