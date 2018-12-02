package unq.desapp.grupo_f.backend.utils;

import java.util.List;
import java.util.Random;

public class RandomStrings {

	static public String generateRandomString(Random random, String possibleChars, Integer length) {
		char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = possibleChars.charAt(random.nextInt(possibleChars.length()));
        }
        return new String(text);
	}
	
	static public String generateRandomString(Random random, List<String> possibleWords, Integer maxAmountOfWords) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < maxAmountOfWords; i++) {
			builder.append(possibleWords.get(random.nextInt(possibleWords.size() - 1)));
			builder.append(" ");
		}
		return builder.substring(0, builder.length() -1);
	}
}
