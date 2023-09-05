package SmartHome;

public class TemperatureSensor extends Sensor{
    String Owner, Location, Phone, NumberofSensors, Sensors, ID,Type,Object;
    double DefaultStatus,Status;
    TemperatureSensor(double defStat, double curStat) {
        super(defStat, curStat);
    }

    @Override
    void changeStatus(double change) {
        Status = change;

    }
}
