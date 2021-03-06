import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieEngine {
	private List<Movie> movies;
	private String currentCommand;

	public MovieEngine(List<Movie> movies) {
		this.movies = movies;
	}

	public String getAvailableCommands() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s%n", "SHOW ALL		show all videos"));
		sb.append(String.format("%s%n", "SHOW GENRES	show the genres of all the videos"));
		sb.append(String.format("%s%n", "SEARCH <term>	show videos that have <term> in the name"));
		sb.append(String.format("%s%n", "GENRE <genre>	show videos in the genre <genre>"));
		sb.append(String.format("%s%n", "KEYWORD <term>	show videos that have <term> in the name, genre, director's name or actors' names."));
		sb.append(String.format("%s%n", "YEAR <year>		show videos released in the year <year>"));
		sb.append(String.format("%s%n", "DECADE <decade>	show videos released in the decade <decade>. "));
		sb.append(String.format("%s%n", "			<decade> can be a full year or just the last 2 digits and can have an 's' on the end"));
		sb.append(String.format("%s%n", "			(examples: '1960', '70', '50s', '1980s', '1954', '88s')"));
		sb.append(String.format("%s%n", "			(NOTE: a decade is always the years 0 - 9 so 'DECADE 1954' shows films from 1950-1959)"));
		sb.append(String.format("%s%n", "SHOW HELP		show this help"));
		return sb.toString();
	}

	public String parseCommand(String command) {
		//each command has an instruction and an argument. split the incoming string on the first whitespace character (or whitespace characterS if they are contiguous)
		currentCommand = command;
		if (currentCommand.trim().equalsIgnoreCase("exit"))
			System.exit(0);
		String[] words = command.split("\\s+", 2);
		if (words.length < 2) {
			return "Syntax: <command> <argument>.";
		}

		//make both strings lower case and trim any excess whitespace to make comparisons easier
		String instruction = words[0].toLowerCase().trim();
		String argument = words[1].toLowerCase().trim();

		switch (instruction) {
			case "show":
				return show(argument);
			case "search":
				return nameSearch(argument);
			case "genre":
				return genreSearch(argument);
			case "year":
				return yearSearch(argument);
			case "decade":
				return decadeSearch(argument);
			case "keyword":
				return keywordSearch(argument);
			default: //everything that isn't a known command
				return "I don't understand '" + instruction + "'.";
		}
	}

	private String keywordSearch(String searchTerm) {
		List<Movie> result = new ArrayList<Movie>();
		for (Movie v : movies) {
			if (v.getName().toLowerCase().contains(searchTerm)) {
				result.add(v);
			}
			if (v.getGenre().toLowerCase().contains(searchTerm)) {
				result.add(v);
			}
		}
		return buildResult(result);
	}

	private String nameSearch(String searchTerm) {
		List<Movie> result = new ArrayList<Movie>();
		for (Movie v : movies) {
			if (v.getName().toLowerCase().contains(searchTerm)) {
				result.add(v);
			}
		}
		return buildResult(result);
	}

	private String genreSearch(String searchTerm) {
		List<Movie> result = new ArrayList<Movie>();
		for (Movie v : movies) {
			if (v.getGenre().toLowerCase().contains(searchTerm)) {
				result.add(v);
			}
		}
		return buildResult(result);
	}

	private String yearSearch(String searchTerm) {
		try {
			int year = Integer.valueOf(searchTerm);
			List<Movie> result = new ArrayList<Movie>();
			for (Movie v : movies) {
				if (v.getYear() == year) {
					result.add(v);
				}
			}
			return buildResult(result);
		} catch (NumberFormatException e) {
			return String.format("Specified year is not a valid year%n");
		}
	}

	private String decadeSearch(String searchTerm) {
		try {
			int decadeStart = parseDecade(searchTerm);
			int decadeEnd = decadeStart + 9;
			List<Movie> results= new ArrayList<Movie>();
			for (Movie v : movies) {
				int year = v.getYear();
				if (year >= decadeStart && year <= decadeEnd) {
					results.add(v);
				}
			}

			return buildResult(results);
		} catch (NumberFormatException e) {
			return String.format("Specified decade is not valid%n");
		}
	}

	private int parseDecade(String input) throws NumberFormatException {
		//input should be an integer unless the length is odd, in which case it should end with an 's'
		if (input.length() % 2 == 1) {
			input = input.substring(0,input.length()-1); //drop the last character
		}
		int decade = Integer.valueOf(input);
		//we now need to make this the first year of a decade
		decade = (decade/10) * 10; //we use integer division to drop the 'units' (e.g. if dec was 99 before this step now it'll be 90, if it was 1999 now it's 1990)
		if (decade < 100) { //convert a 2-digit number into a full year
			if (decade >= 30) { //00s, 10s, and 20s are in the 2000s, everything else in the 1900s
				decade += 1900;
			} else {
				decade += 2000;
			}
		}

		return decade;
	}

	private String show(String command) {
		switch (command.toLowerCase()) {
			case "help":		return getAvailableCommands();
			case "all":			return getAll();
			case "genres":		return getGenres();

			default: 			return "I don't know how to show that!";
		}
	}

	private String getAll() {
		return buildResult(movies);
	}

	private String getGenres() {
		List<String> genres = new ArrayList<String>();

		//TODO WRITE THE SEARCH CODE
		for (Movie v : movies) {
			if(!genres.contains(v.getGenre())) {
				genres.add(v.getGenre());
			}
		}

		Collections.sort(genres);

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("--- Results for '%s' ---%n", currentCommand));
		for (String g : genres) {
			sb.append(String.format("%s%n", g));
		}
		sb.append("--- End of Results ---"+System.lineSeparator());
		return sb.toString();
	}

	private String buildResult(List<Movie> searchResults) {
		if (searchResults.size() == 0) return String.format("no results found for '%s'%n", currentCommand);

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("--- Results for '%s' ---%n", currentCommand));
		for (Movie v : searchResults) {
			sb.append(String.format("%s%n%n", v));
		}
		sb.append("--- End of Results ---"+System.lineSeparator());
		sb.deleteCharAt(sb.length()-1); //delete the last newline
		return sb.toString();
	}
}
