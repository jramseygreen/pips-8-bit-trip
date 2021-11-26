package cs230.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * holds the methods to get the message of the day.
 * 
 * @author Ben Nettleship
 *
 */
public class MessageOfTheDay {

	private static final String URL = "http://cswebcat.swan.ac.uk/";
	private static final String URL_PUZZLE = URL + "puzzle";
	private static final String URL_SOLUTION = URL + "message?solution=";
	private static final String ERROR_MSG = "Can not get the message from the server.";

	/**
	 * gets the message of the day.
	 * 
	 * @return a string containing the message of the day, and a timestamp.
	 * @throws IOException
	 */
	public static String getMessage() {

		String result = ERROR_MSG;

		try {

			String puzzle = getPuzzle();
			String answer = solvePuzzle(puzzle);
			result = getRequest(getURLAnswer(answer));

		} catch (IOException e) {
		}

		return result;
	}

	/**
	 * gets the URL that needs to be connected to to get the message of the day.
	 * 
	 * @param PuzzleAnswer
	 *            takes the answer to the puzzle to add to the url
	 * @return the URL to get the message of the Day
	 */
	private static String getURLAnswer(String puzzleAnswer) {
		return URL_SOLUTION + puzzleAnswer;
	}

	/**
	 * does a get request to get the puzzle
	 * 
	 * @return the puzzle to be solved
	 * @throws IOException
	 */
	private static String getPuzzle() throws IOException {
		return getRequest(URL_PUZZLE);
	}

	/**
	 * performs a get request and returns the line from the server
	 * 
	 * @param url
	 *            to make the get request to
	 * @return the
	 * @throws IOException
	 */
	private static String getRequest(String url) throws IOException {

		// creates a url object, starts a connection and sets the request method to GET
		URL test = new URL(url);
		HttpURLConnection con = (HttpURLConnection) test.openConnection();
		con.setRequestMethod("GET");

		// response code from the server
		int status = con.getResponseCode();

		// checks for error
		if (status == -1 || status >= HttpURLConnection.HTTP_MULT_CHOICE) {

			throw new IOException("Invalid response: " + status);
		}

		InputStreamReader streamReader = new InputStreamReader(con.getInputStream());

		BufferedReader in = new BufferedReader(streamReader);
		String line;
		line = in.readLine();
		in.close();
		con.disconnect();
		return line;
	}

	/**
	 * solves the puzzle
	 * 
	 * @param Puzzle
	 *            the puzzle retrieved from the get request
	 * @return the solved puzzle
	 */
	private static String solvePuzzle(String puzzle) {

		String answer = "";
		for (int i = 0; i < puzzle.length(); i++) {

			// if the index is even, then the character needs to move forward in the
			// alphabet.
			if (i % 2 == 0) {

				// checks if the character is Z, as this loops around the alphabet therefore
				// adding one to the character value wont work
				if (puzzle.charAt(i) == 'Z') {

					answer += 'A';
				} else {

					// moves the char up one in the alphabet and appends it to the answer string
					answer += (char) (puzzle.charAt(i) + 1);
				}
			}
			// if the index is not even, then the char needs to move backwards.
			else {

				// checks if the character is a, as this loops around the alphabet therefore
				// adding one to the character value wont work
				if (puzzle.charAt(i) == 'A') {

					answer += 'Z';
				} else {

					answer += (char) (puzzle.charAt(i) - 1);
				}
			}
		}
		return answer;
	}
}
