package cs230.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Manages and keeps track of high scores.
 * 
 * @author Sam Harper 975431
 * @version 1.1 manages the level high scores
 *
 */
public class HighScoreManager {

	private static final String PROFILE_FILE = "HighScores.txt";
	private static final String SAVES = "saves/";

	private static HashMap<String, ArrayList<Score>> levelScores = new HashMap<>();
	private static ScoreComparator scoreComparator = new ScoreComparator();

	public static ArrayList<Score> getScoresForLevel(String level) {

		return levelScores.get(level);
	}

	/**
	 * @param levelName
	 *            name of level resets a level's associated scores
	 */
	public static void resetScoresForLevel(String levelName) {

		ArrayList<Score> scores = levelScores.get(levelName);

		if (scores != null) {

			scores.clear();
		}
	}

	/**
	 * @param levelName
	 *            name of level
	 * @param score
	 *            time of completion adds score to leaderboard
	 */
	public static void addScore(String levelName, Score score) {

		ArrayList<Score> scores = levelScores.get(levelName);

		if (scores == null) {

			scores = new ArrayList<>();
			scores.add(score);

			levelScores.put(levelName, scores);
		} else {

			if (scores.size() > 10) {

				if (score.getTime() < scores.get(scores.size() - 1).getTime()) {

					scores.add(scores.size() - 1, score);
				}
			} else {

				scores.add(score);
			}

			Collections.sort(scores, scoreComparator);
		}
	}

	/**
	 * gets all scores from file
	 */
	public static void loadScores() {

		ArrayList<String> scoresFile = readFile();

		if (scoresFile != null && scoresFile.size() > 0) {

			parseHighScores(scoresFile);
		}
	}

	/**
	 * @param scoresFile
	 *            file to parse gets data from scores file
	 */
	private static void parseHighScores(ArrayList<String> scoresFile) {

		for (String line : scoresFile) {

			String levelName = getLevelName(line);

			ArrayList<Score> scores = null;
			if (!levelScores.containsKey(levelName)) {

				scores = new ArrayList<>();
				levelScores.put(levelName, scores);
			} else {

				scores = levelScores.get(levelName);
			}

			parseScore(getInfo(line), scores);
		}
	}

	/**
	 * @param info
	 *            line of file
	 * @param scores
	 *            scores aray list adds info on this line to populate scores
	 *            arraylist
	 */
	private static void parseScore(String info, ArrayList<Score> scores) {

		Scanner scoreScanner = new Scanner(info);
		scoreScanner.useDelimiter(LevelIO.FILE_DATA_SEPARATOR);

		String profileName = scoreScanner.next();
		long time = scoreScanner.nextLong();

		scores.add(new Score(profileName, time));
		Collections.sort(scores, scoreComparator);
		scoreScanner.close();
	}

	/**
	 * getter
	 * 
	 * @param line
	 *            line with info
	 * @return returns string of levelname frm file
	 */
	private static String getLevelName(String line) {

		return line.substring(0, line.indexOf(LevelIO.FILE_TOKEN_SEPARATOR));
	}

	/**
	 * @param line
	 *            line to read from
	 * @return string of info between commas getter
	 */
	private static String getInfo(String line) {

		return line.substring(line.indexOf(LevelIO.FILE_TOKEN_SEPARATOR) + 1);
	}

	/**
	 * saves high scores
	 */
	public static void saveScores() {

		ArrayList<String> scoreFile = new ArrayList<>();

		levelScores.entrySet().forEach(entry -> {

			String levelName = entry.getKey();

			for (Score score : entry.getValue()) {

				scoreFile.add(levelName + LevelIO.FILE_TOKEN_SEPARATOR + score.getName() + LevelIO.FILE_DATA_SEPARATOR
						+ score.getTime());
			}
		});

		writeFile(scoreFile);
	}

	/**
	 * @return returns an arraylist ready for parsing
	 */
	private static ArrayList<String> readFile() {

		File pFile = new File(SAVES, PROFILE_FILE);

		ArrayList<String> data = null;

		if (pFile.exists()) {

			data = new ArrayList<>();

			try (Scanner profileScanner = new Scanner(pFile)) {

				while (profileScanner.hasNextLine()) {

					String line = profileScanner.nextLine();

					// remove any possible whitespace
					line = line.replaceAll("\\s+", "");

					// line contains useful data
					if (!line.isEmpty() && !line.startsWith(LevelIO.FILE_COMMENT)) {

						data.add(line);
					}
				}

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
		}

		return data;
	}

	/**
	 * @param data
	 *            arraylist of strings to write writes all info in data string array
	 *            to a file with delimmiter
	 */
	private static void writeFile(ArrayList<String> data) {

		File pFile = new File(SAVES + PROFILE_FILE);

		if (pFile.exists()) {

			pFile.delete();
		} else {
			pFile.getParentFile().mkdirs();
		}

		try (PrintWriter fileWriter = new PrintWriter(pFile)) {

			for (String line : data) {

				fileWriter.println(line);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @author Sam Harper 975431 compares scores neatly
	 *
	 */
	private static class ScoreComparator implements Comparator<Score> {

		@Override
		public int compare(Score score1, Score score2) {

			return (int) (score1.getTime() - score2.getTime());
		}

	}
}
