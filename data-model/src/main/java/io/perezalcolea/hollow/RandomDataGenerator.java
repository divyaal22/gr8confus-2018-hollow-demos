package io.perezalcolea.hollow;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomDataGenerator {

    private static final Random rand = new Random();

    private static final List<String> PUBLISHERS = Arrays.asList(
            "Manning Publications",
            "O'Reilly Media",
            "No Starch Press",
            "Addison-Wesley Signature Series",
            "Packt Publishing",
            "Prentice Hall",
            "Pragmatic Bookshelf",
            "Springer"
    );

    private static final List<String> LANGUAGES  = Arrays.asList(
            "English",
            "Spanish",
            "Japanese",
            "French",
            "German",
            "Portuguese"
    );

    public static String randomPublisher() {
        return PUBLISHERS.get(rand.nextInt(PUBLISHERS.size()));
    }

    public static String randomLanguage() {
        return LANGUAGES.get(rand.nextInt(LANGUAGES.size()));
    }
}
