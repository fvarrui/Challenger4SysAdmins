package fvarrui.sysadmin.challenger.command;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.io.IOUtils;

@XmlType
@XmlSeeAlso(value = { BASHCommand.class, DOSCommand.class, PSCommand.class })
public class Command {
	
	private String command;
	
	@XmlTransient
	private int returnValue;
	
	@XmlTransient
	private String output;
	
	@XmlTransient
	private String error;
	
	@XmlTransient
	private String lastExecutedCommand;
	
	@XmlTransient
	private LocalDateTime lastExecutionTime;
	
	public Command(String command) {
		this.command = command;
	}
	
	public Command() {
	}
	
	public int execute(String ... params) {
		try {
			lastExecutionTime = LocalDateTime.now();
			lastExecutedCommand = String.format(this.command, (Object[]) params);
			String [] splittedCommand = lastExecutedCommand.split("[ ]+");
			ProcessBuilder pb = new ProcessBuilder(splittedCommand);
			Process p = pb.start();
			p.getOutputStream().close();
			output = IOUtils.toString(p.getInputStream(), Charset.defaultCharset());
			error = IOUtils.toString(p.getErrorStream(), Charset.defaultCharset());
			returnValue = p.exitValue();
		} catch (Exception e) {
			returnValue = -1;
			e.printStackTrace();
		}
		return getReturnValue();
	}
	
	public LocalDateTime getLastExecutionTime() {
		return lastExecutionTime;
	}
	
	public String getLastExecutedCommand() {
		return lastExecutedCommand;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	@XmlAttribute
	public String getCommand() {
		return command;
	}

	public int getReturnValue() {
		return returnValue;
	}

	public String getOutput() {
		return output;
	}

	public String getError() {
		return error;
	}

}
