package ngrams;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.In;


import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private final Map<String, TimeSeries> wordHistory; // word -> TimeSeries (year -> single word count)
    private final TimeSeries totalCountHistory; // year -> total word count

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordHistory = new HashMap<>();
        totalCountHistory = new TimeSeries();

        /* 1. Read in the data from the words file and get thethe history of a
        particular word  */
        In inWords = new In(wordsFilename);
        while (inWords.hasNextLine()) {
            String line = inWords.readLine();
            String[] parts = line.split("\t");
            String word = parts[0];
            int year = Integer.parseInt(parts[1]);
            double count = Double.parseDouble(parts[2]);

            if (!wordHistory.containsKey(word)) {
                wordHistory.put(word, new TimeSeries());
            }
            wordHistory.get(word).put(year, count);
        }

        /* 2. Read in the data from the counts file and get the total word count */
        In inCounts = new In(countsFilename);
        while (inCounts.hasNextLine()) {
            String line = inCounts.readLine();
            String[] parts = line.split(",");
            int year = Integer.parseInt(parts[0]);
            double count = Double.parseDouble(parts[1]);

            totalCountHistory.put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }

        return new TimeSeries(wordHistory.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }

        return new TimeSeries(wordHistory.get(word), MIN_YEAR, MAX_YEAR); // Require three arguments!
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalCountHistory, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordHistory.containsKey(word)) {
            return new TimeSeries();
        }

        TimeSeries wordCount = new TimeSeries(wordHistory.get(word), startYear, endYear);
        TimeSeries totalWordCount = new TimeSeries(totalCountHistory, startYear, endYear);

        return wordCount.dividedBy(totalWordCount);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            if (wordHistory.containsKey(word)) {
                TimeSeries wordWeight = weightHistory(word, startYear, endYear);
                sum = sum.plus(wordWeight);
            }
        }

        return sum;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }

}
