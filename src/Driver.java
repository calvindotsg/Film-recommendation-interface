import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Driver {

	static MovieEngine engine;
	static Scanner scanner;
	public static void main(String[] args) {
		String fileName;

		fileName = "MovieDatabase.csv";
		ArrayList<Movie> movies = new ArrayList<>();
		try {
			scanner = new Scanner(new FileReader(fileName));
			String z = scanner.nextLine(); // read away header of file
			while (scanner.hasNext()) {
				z = scanner.nextLine();
				String[] row = z.split(",");
				String name = String.valueOf(row[0]);
				String genre = String.valueOf(row[1]);
				int year = parseInt((row[2]).trim());
				String rating = String.valueOf(row[3]);

				/*
				// Debugging
				System.out.println("Name: " + name +
						" Genre: " + genre +
						" Year: " + year +
						" Rating: " + rating
				);
				 */
				movies.add(new MovieBulider(name, genre, year, rating));
				// Debugging
				//System.out.println(movies);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e1){
			e1.printStackTrace();
		}
		engine = new MovieEngine(movies);

		welcomeClient();

		//user interaction starts (and ends) here
		mainLoop();
	}

	public static void welcomeClient(){
		String commands = engine.getAvailableCommands();
		System.out.println(commands);
	}

	public static void mainLoop() {
		//receive and respond to input
		boolean moreInput = true;
		while (moreInput) {
			try {
				String inputLine = null;
				System.out.print("Command: ");
				scanner = new Scanner(System.in);
				inputLine = scanner.nextLine();
				while ((inputLine != null)) {
					String output = engine.parseCommand(inputLine.trim());
					System.out.println(output);
					inputLine = null;
				}
				// moreInput = false;
			} catch (Exception e) {
				//engine has thrown some sort of student-based hissy fit;
				e.printStackTrace();;
			}
		}
	}
}
