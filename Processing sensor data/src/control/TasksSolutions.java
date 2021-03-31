package control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.MonitoredData;

public class TasksSolutions {

	public List<MonitoredData> getMonitoredData(List<MonitoredData> monitoredData) {

		Task1 obj = (activity) -> {
			String fileName = "C:\\Users\\Lidia\\eclipse-workspace\\Processing sensor data\\Activities.txt";
			List<String> activityList = new ArrayList<String>();
			try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

				activityList = stream.collect(Collectors.toList());
				for (int i = 0; i < activityList.size(); i++) {
					String data = activityList.get(i);
					String[] aux = data.split("\t\t");
					MonitoredData m = new MonitoredData(aux[0], aux[1], aux[2]);
					monitoredData.add(m);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return monitoredData;
		};
		return obj.getMonitoredData(monitoredData);	
	}

	public int countNumberOfDays(List<MonitoredData> monitoredData) {
		Task2 obj = (activity) -> {
			List<String> listOfStartTimes = monitoredData.stream().map(m -> m.getStartTime()).map(s -> s.substring(0, 10))
					.collect(Collectors.toList());
			long numberOfDays = listOfStartTimes.stream().distinct().count();
			return (int) numberOfDays;
		};
		return obj.countNumberOfDays(monitoredData);
	}

	public Map<String, Integer> activityFrequency(List<MonitoredData> monitoredData) {
		Task3 obj = (activity) -> {
			HashMap<String, Integer> activityFrequency = new HashMap<String, Integer>();
			List<String> listOfActivities = monitoredData.stream().map(m -> m.getActivity()).collect(Collectors.toList());
			for (String a : listOfActivities) {
				long count = monitoredData.stream().map(m -> m.getActivity()).filter(currentActivity -> a.equals(currentActivity))
						.count();
				activityFrequency.put(a, (int) count);
			}
			return activityFrequency;
		};
		return obj.activityFrequency(monitoredData);
	}

	public Map<Integer, Map<String, Integer>> activityFrequencyPerDay(List<MonitoredData> monitoredData) {
		Task4 obj = (activity) -> {

			//nr of day, activity name, nr of times
			Map<Integer, Map<String, Integer>> activityFrequencyPerDay = new HashMap<Integer, Map<String, Integer>>();
			List<String> listOfStartTimes = monitoredData.stream().map(m -> m.getStartTime()).map(s -> s.substring(0, 10))
					.distinct().collect(Collectors.toList());
			List<String> listOfActivities = monitoredData.stream().map(m -> m.getActivity()).distinct()
					.collect(Collectors.toList());
			for (String day : listOfStartTimes) {
				Map<String, Integer> activityFrequency = new HashMap<String, Integer>();
				String[] aux = day.split("[- ]+");
				int dayInt = Integer.parseInt(aux[2]);
				for (String currentActivity : listOfActivities) {
					long count = 0;
					count = monitoredData.stream()
							.filter(n -> n.getStartTime().contains(day) && n.getActivity().equals(currentActivity)).count();
					activityFrequency.put(currentActivity, (int)count);
				}
				activityFrequencyPerDay.put(dayInt,activityFrequency);
			}
			return activityFrequencyPerDay;

		};
		return obj.activityFrequencyPerDay(monitoredData);
	}

	Map<MonitoredData, Long> durations;
	public  Map<String, Long> duration(List<MonitoredData> monitoredData) {
		Task5 obj = (activity) -> {
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			durations = monitoredData.stream()
					.collect(Collectors.toMap(a->a,(a->{
						try {
							return date.parse(a.getEndTime()).getTime()/1000-date.parse(a.getStartTime()).getTime()/1000;
						} catch (ParseException e) {
							e.printStackTrace();
						}
						return null;
					})));
			Map<String,Long> durationsGlobal = durations.entrySet().stream()
					.collect(Collectors.groupingBy(entry-> entry.getKey().getActivity(),
							Collectors.summingLong(entry->entry.getValue())));
			return durationsGlobal;
		};
		return obj.duration(monitoredData);
	}

	public List<String> filterActivities(List<MonitoredData> monitoredData) {
		Task6 obj = (activity) -> {
			List<String> activities = new ArrayList<String>();
			List<String> listOfActivities = monitoredData.stream().map(m -> m.getActivity()).distinct()
					.collect(Collectors.toList());
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<MonitoredData> result = monitoredData.stream().filter(m -> {
				try {
					return 5 > TimeUnit.MINUTES.convert(
							date.parse(m.getEndTime()).getTime() - date.parse(m.getStartTime()).getTime(),
							TimeUnit.MILLISECONDS);
				} catch (ParseException e) {
					System.out.println("Error at parsing");
				}
				return false;
			}).collect(Collectors.toList());
			for (String a : listOfActivities) {
				long totalAppearances = monitoredData.stream().filter(act -> act.getActivity().equals(a)).count();
				long totalNumberAfterFilter = result.stream().filter(act -> act.getActivity().equals(a)).count();
				activities = result.stream()
						.filter(r -> totalAppearances != 0 && totalNumberAfterFilter != 0 && r.getActivity().equals(a)
						&& totalNumberAfterFilter >= 0.9 * totalAppearances)
						.map(r -> r.getActivity()).distinct().collect(Collectors.toList());
			}
			return activities;
		};
		return obj.activities(monitoredData);
	}

}