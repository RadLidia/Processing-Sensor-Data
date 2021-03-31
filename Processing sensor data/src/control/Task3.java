package control;

import java.util.List;
import java.util.Map;

import model.MonitoredData;

public interface Task3 {

	Map<String, Integer> activityFrequency(List<MonitoredData> monitoredData);
}
