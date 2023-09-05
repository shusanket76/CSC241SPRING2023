package SmartHome;

public class MotionSensor extends Sensor{
    String Owner, Location, Phone, NumberofSensors, Sensors, ID,Type,Object;
    double DefaultStatus,Status;

    MotionSensor(double defStat, double curStat) {
        super(defStat, curStat);
    }

    @Override
    void changeStatus(double change)  {
        Status = change;

//
    }
}
