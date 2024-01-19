package by.intexsoft.api;

import java.util.Random;

public class StepsImplementation {

    public static int getRandomNumber(int maxNumber) {
        Random random = new Random();
        return random.nextInt(maxNumber) + 1;
    }
}
