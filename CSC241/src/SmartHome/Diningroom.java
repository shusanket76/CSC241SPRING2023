package SmartHome;

import java.util.ArrayList;

public class Diningroom implements Room {
    static ArrayList<String> sensorsArray =  new ArrayList<>();
    @Override
    public void deploySensor(Sensor sensor) {
        sensorsArray.add(sensor.toString());

    }

    @Override
    public void removeSensor(Sensor sensor) {
        for(int i = 0;i<= sensorsArray.size()-1;i++){
            if (sensorsArray.get(i).equals(sensor.toString())){
                sensorsArray.remove(i);

            }
        }

    }
}
