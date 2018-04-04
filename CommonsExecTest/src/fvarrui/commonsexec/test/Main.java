package fvarrui.commonsexec.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;

public class Main {

	public static void main(String[] args) throws ExecuteException, IOException, InterruptedException {

		HashMap<String, Object> map = new HashMap<>();
		map.put("command", "Get-Date");

		CommandLine cmdLine = new CommandLine("ping");
		cmdLine.addArgument("8.8.8.8");
		cmdLine.setSubstitutionMap(map);
		System.out.println(cmdLine);
		
		DefaultExecuteResultHandler result = new DefaultExecuteResultHandler();

		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
		
		PumpStreamHandler streamHandler = new PumpStreamHandler(new LogOutputStream() {
			protected void processLine(String line, int logLevel) {
				System.out.println(line);
			}
		});
		
		Executor executor = new DefaultExecutor();
		executor.setWatchdog(watchdog);
		executor.setStreamHandler(streamHandler);
		executor.execute(cmdLine, result);

		result.waitFor();

		Thread.sleep(3000L);
		System.out.println("catapun");
		watchdog.destroyProcess();
		
		int exitValue = result.getExitValue();
		System.out.println(exitValue);

	}

}
