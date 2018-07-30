package fauxpas.utils;

import java.security.SecureRandom;

public class TokenGenerator {
    private static SecureRandom random;

    public static String generateToken() {
        initRand();
        byte bytes[] = new byte[128];
        random.nextBytes(bytes);
        return bytes.toString();
    }

    private static void initRand() {
        if (random == null) {
            random = new SecureRandom();
        }
    }
}
