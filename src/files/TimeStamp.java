package files;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class TimeStamp {
	FileOutputStream outputStream;
	PrintWriter printWriter;
	BufferedWriter bufferedWriter;

	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}

	public void setBufferedWriter(BufferedWriter bufferedWriter) {
		this.bufferedWriter = bufferedWriter;
	}

	public LocalDateTime getCurrent() {
		return current;
	}

	public void setCurrent(LocalDateTime current) {
		this.current = current;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	String fileName = "out.txt";
	FileWriter fileWriter;

	public FileWriter getFileWriter() {
		return fileWriter;
	}

	public void setFileWriter(FileWriter fileWriter) {
		this.fileWriter = fileWriter;
	}

	LocalDateTime current;

	public LocalDateTime getCurrentTime() {
		return current;
	}

	public void setCurrentTime(LocalDateTime currentTime) {
		this.current = currentTime;
	}

	public FileOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(FileOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	public void setPrintWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public String getFileName() {
		return fileName;
	}

	public TimeStamp() {
		try {

			setFileWriter(new FileWriter(new File(getFileName()), true));
			setBufferedWriter(new BufferedWriter(getFileWriter()));
			setPrintWriter(new PrintWriter(getBufferedWriter(), true));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void writer(String data) throws IOException {

		try {
			setCurrentTime(LocalDateTime.now());
			LocalDate date = getCurrentTime().toLocalDate();
			LocalTime time = getCurrentTime().toLocalTime();
			getPrintWriter().println(data);
			getPrintWriter().flush();

			getPrintWriter().println(date.toString());
			getPrintWriter().flush();
			getPrintWriter().println(time.toString());
			getPrintWriter().flush();

		} catch (Exception e) {
			System.err.println(e.getCause());
			getFileWriter().close();
			getBufferedWriter().close();
			getPrintWriter().close();
		} finally {

		}
	}
}
