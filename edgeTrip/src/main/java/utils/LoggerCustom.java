package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

import com.ibm.automotive.logger.Logger;

public class LoggerCustom {
	/*-
	 *  App Logger setup
	 *  (see Logger API javadoc for how to enable the logger)
	 */
	private Logger logger;
	private File outputFile;
	private BufferedWriter writer;

	public static int LOG_ALL = 0;

	public static int LOG_DEBUG = 1;

	public static int LOG_ERROR = 4;

	public static int LOG_INFO = 2;

	public static int LOG_NONE = 5;

	public static int LOG_WARNING = 3;

	public LoggerCustom(File newFile) throws IOException {
		this.outputFile = newFile;
		writer = new BufferedWriter(new FileWriter(outputFile, true));
	}

	public void printLog(String logLevel, String msg) {
		System.out.println(msg);
	}

	public void printLog(String msg) throws IOException {
		printLog(LoggerCustom.LOG_INFO, msg, null);
	}

	public void printLog(String msg, Throwable e) throws IOException {
		printLog(LoggerCustom.LOG_INFO, msg, e);
	}

	public void printLog(int logLevel, String msg) throws IOException {
		printLog(logLevel, msg, null);
	}

	public void printLog(int logLevel, String msg, Throwable e) throws IOException {
		if (logger == null) {
			// Creates and Starts the logger
			logger = Logger.getLogger();
		}

		// try (FileWriter writer = new FileWriter(outputFile, true)) {
		// writer.write(msg);
		// writer.newLine();

		// } catch (IOException e2) {
		// e.printStackTrace();
		// }
		
		if (logLevel >= 2) {
			writer.write(msg);
			writer.newLine();
			writer.flush();
		}
			


		logger.log(logLevel, msg, e);

		logger.flush();
	}
}