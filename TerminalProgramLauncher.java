/*
 * This program will create an execuitable file that can run your game. 
 * It will create a terminal window and launch your game from that window. 
 * 
 * You will need to do the following:
 * 1. Create a .jar file for your game.
 * 2. Rename the .jar file whatever something like 'GameData.jar'.
 * 3. Update the JAR_FILE path to the filename you selected.
 * 4. Create a .jar file out of this program.
 * 5. Rename this .jar file whatever you want to call your game.
 * 
 * Then you should be able to click on and run this program to launch your game without needing VSCode.
 * You can send the two .jar files to friends etc. They just have to be in the same folder to run.
 * 
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TerminalProgramLauncher {
    public static void main(String[] args) {

        // Edit the JAR_FILE string to match the name of your game .jar file.
        // Keep the ./ in front of the filename.
        final String JAR_FILE = "./GameData.jar";

        String os = System.getProperty("os.name").toLowerCase();

        try {
            String[] command;
            if (os.contains("win")) {
                command = new String[] { "cmd.exe", "/c", "start", "java", "-jar", JAR_FILE };
            } else if (os.contains("mac")) {
                command = new String[] { "/bin/bash", "-c", "java -jar " + JAR_FILE };
            } else {
                System.out.println("Unsupported operating system.");
                return;
            }
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                System.out.println(inputLine);
            }
            input.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
