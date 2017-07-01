package regex;

import java.util.regex.Pattern;

import model.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class RegexFinder {
	private Matcher matcher;
	private List<Pair<Integer, Integer>> allMatchesSpans;

	public void findMatches(String inputSentence, String regexePattern) {
		String regexe = regexePattern;
		Pattern pattern = Pattern.compile(regexe, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(inputSentence);
		allMatchesSpans = new ArrayList<Pair<Integer, Integer>>();
		while (matcher.find()) {
			allMatchesSpans.add(new Pair<Integer, Integer>(matcher.start(), matcher.end()));
			System.out.println("find() found the pattern \"" + matcher.group() + "\" starting at index "
					+ matcher.start() + " and ending at index " + matcher.end());
		}
	}

	public List<Pair<Integer, Integer>> getAllMatchesSpans(String inputSenteces, String regexePattern) {

		findMatches(inputSenteces, regexePattern);

		while (matcher.find()) {
			allMatchesSpans.add(new Pair<Integer, Integer>(matcher.start(), matcher.end()));
			System.out.println("find() found the pattern \"" + matcher.group() + "\" starting at index "
					+ matcher.start() + " and ending at index " + matcher.end());
		}
		return allMatchesSpans;

	}

	public Matcher getMatcher() {
		return matcher;
	}

	public List<Pair<Integer, Integer>> getAllMatches() {
		return allMatchesSpans;
	}
}
