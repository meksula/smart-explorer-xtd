package pl.smartexplorer.cerber.repository.id;

import java.util.Random;

/**
 * @author
 * Karol Meksuła
 * 27-10-2018
 * */

public class UserIdGenerator {
    public final static int ID_LENGTH = 10;
    private Random random;
    private final int[] digitsAndLetters = new int[62];

    public UserIdGenerator() {
        this.random = new Random();
        int c = 0;

        for (int i = 48; i <= 57; i++) {
            digitsAndLetters[c] = i;
            c++;
        }

        for (int i = 65; i <= 122; i++) {
            if (i <= 90 || i >= 97) {
                digitsAndLetters[c] = i;
                c++;
            }
        }
    }

    /**
     * This method build id from username
     * */
    public String generateId(String username) {
        String prefix = username.substring(0, 4);

        while (prefix.length() < ID_LENGTH) {
            prefix = prefix.concat(nextChar());
        }

        return prefix;
    }

    private String nextChar() {
        int asciiIndex = -1;

        while (asciiIndex < 0) {
            asciiIndex = this.random.nextInt(digitsAndLetters.length - 1);
        }

        return String.valueOf(Character.toChars(digitsAndLetters[asciiIndex]));
    }

}
