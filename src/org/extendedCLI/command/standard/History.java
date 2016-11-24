package org.extendedCLI.command.standard;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import org.extendedCLI.argument.Arguments;
import org.extendedCLI.command.AbstractCommand;
import org.extendedCLI.command.Command;
import org.extendedCLI.command.ExtendedCommandLine;

@SuppressWarnings("javadoc")
public class History extends AbstractCommand{
	
	private final Map<Long, Command> history;

	public History(Map<Long, Command> history) {
		super(Arguments.empty(), "Prints the execution history with timestamps");
		this.history = history;
	}

	@Override
	protected void execute(ExtendedCommandLine commandLine) {
		if(history.isEmpty()){
			System.out.println("No commands yet.");
		}
		else{
			for(long date : history.keySet()){
				System.out.println(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault()) + " -> " + history.get(date));
			}
		}
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}
}
