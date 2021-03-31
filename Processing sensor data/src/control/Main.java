package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import view.FileWriter;
import model.MonitoredData;

public class Main {

	public static void main(String args[]) {
		
		FileWriter f = new FileWriter();
		List<MonitoredData> monitoredData = new ArrayList<MonitoredData>();
		TasksSolutions task = new TasksSolutions();

		monitoredData = task.getMonitoredData(monitoredData);
		StringBuilder s1 = new StringBuilder();
		monitoredData.forEach(value -> s1.append(value.toString()));
		f.write("Task1.txt",s1.toString());

		int nr = task.countNumberOfDays(monitoredData);
		StringBuilder s2 = new StringBuilder();
		s2.append("In the given monitored data are " + nr + " days.\n");
		f.write("Task2.txt", s2.toString());

		Map<String, Integer> activityFrequency = task.activityFrequency(monitoredData);
		StringBuilder s3 = new StringBuilder();
		activityFrequency.forEach((key, value) -> s3.append(key + "\t" + value + " times\n"));
		f.write("Task3.txt", s3.toString());

		Map<Integer, Map<String, Integer>> activityFrequencyPerDay = task.activityFrequencyPerDay(monitoredData);
		StringBuilder s4 = new StringBuilder();
		for(Entry<Integer, Map<String, Integer>> entry : activityFrequencyPerDay.entrySet()) {
			int key = entry.getKey();
			Map<String, Integer> value = entry.getValue();
			s4.append("Day " + key + ":\n");
			for(Entry<String, Integer> entry1 : value.entrySet()) {
				String activity = entry1.getKey();
				int count = entry1.getValue();
				s4.append(activity + " " + count + "\n");
			}
			s4.append("\n");
		}
		f.write("Task4.txt", s4.toString());

		Map<String, Long> duration = task.duration(monitoredData);
		StringBuilder s5 = new StringBuilder();
		for(Map.Entry<String,Long> entry : duration.entrySet()){
			long hours = entry.getValue() / 3600;
			long minutes = (entry.getValue() - hours * 3600) / 60;
			long seconds = entry.getValue() - hours * 3600 - minutes * 60;
			s5.append(entry.getKey() + " Hours: " + hours + ", Minutes: " + minutes + ", Seconds: " + seconds + "\n");
		}

		List<String> filterActivities = task.filterActivities(monitoredData);
		StringBuilder s6 = new StringBuilder();
		filterActivities.forEach(n -> s6.append(n));
		f.write("Task6.txt", s6.toString());
		
		System.out.println("End");
	}
}
