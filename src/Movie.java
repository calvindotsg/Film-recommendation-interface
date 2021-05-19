public class Movie {
	String name;
	String genre;
	int year;
	String rating;

	public Movie(String name, String genre, int year, String rating) {
		this.name = name;
		this.genre = genre;
		this.year = year;
		this.rating = rating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Name: " + name +
				", Genre: " + genre +
				", Year: " + year +
				", Rating: " + rating;
	}
}