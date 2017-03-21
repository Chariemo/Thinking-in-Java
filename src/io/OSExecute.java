package io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OSExecute {
	public static void command(String command) {
		boolean err = false;
		try {
			Process process = new ProcessBuilder(command.split(" ")).start();
			BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String string;
			while ((string = results.readLine()) != null) {
				System.out.println(string);
			}
			BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((string = errors.readLine()) != null) {
				System.out.println(string);
				err = true;
			}
		} catch(Exception exception) {
			if (!command.startsWith("CMD /C")) {
				command("CMD /C" + command);
			}
			else {
				throw new RuntimeException();
			}
			if (err) {
				throw new OSExecuteException("Errors executeing " + command);
			}
		}
	}
}
