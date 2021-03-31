package control;

import java.util.List;
import java.util.Map;

import model.MonitoredData;

public interface Task4 {

	Map<Integer, Map<String, Integer>> activityFrequencyPerDay(List<MonitoredData> monitoredData);

}
