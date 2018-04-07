package fvarrui.sysadmin.challenger.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.function.Consumer;

public class StreamGobbler extends Thread {
	
	private volatile boolean stop;

	private InputStream inputStream;
	private Consumer<String> consumer;

	public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
		this.inputStream = inputStream;
		this.consumer = consumer;
	}

	@Override
	public void run() {
		try {
			stop = false;
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
			while (!stop) {
				if (reader.ready()) {
					consumer.accept(reader.readLine());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void requestStop() {
		this.stop = true;
	}
	
}
