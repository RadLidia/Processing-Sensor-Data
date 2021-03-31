package view;

import java.io.PrintWriter;

public class FileWriter {
	public void write(String filename, String taskInfo) {
		try {
			PrintWriter file = new PrintWriter(filename, "UTF-8");
			file.write(taskInfo);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
