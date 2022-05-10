package cracker;

import java.security.MessageDigest;

import static util.CrackUtilities.MAX_CHAR_VALUE;
import static util.CrackUtilities.MIN_CHAR_VALUE;

public class PasswordCracker {
    private final MessageDigest md;
    private final char minCharValue;
    private final char maxCharValue;
    private final int maxNumChars;
    private char[] guess;
    
    public PasswordCracker(String hashAlgo) throws Exception {
        md = MessageDigest.getInstance(hashAlgo);
        maxNumChars = 10;
        minCharValue = MIN_CHAR_VALUE;
        maxCharValue = MAX_CHAR_VALUE;
        guess = null;
    }
    
    public String crack(String hash, int numberOfMembers, int memberNumber) {
        String guessHash;
    
        int diff = (maxCharValue - minCharValue + 1) / numberOfMembers;
        
        char minFirstChar = (char) (minCharValue + memberNumber * diff);
        char maxFirstChar = memberNumber == (numberOfMembers - 1) ? maxCharValue : (char) (minFirstChar + diff - 1);
    
        System.out.println("================");
        System.out.println(String.format("Cracking on node number %d start with %c and ending with %c", memberNumber + 1, minFirstChar, maxFirstChar));
        System.out.println("================");
    
        for (int num_chars = 0; num_chars < maxNumChars; num_chars++) {
            guess = new char[num_chars];
            for (int x = 0; x < num_chars; x++) {
                guess[x] = x == 0 ? minFirstChar : minCharValue;
            }
            while (canIncrementGuess(minFirstChar, maxFirstChar)) {
                incrementGuess(minFirstChar, maxFirstChar);
                md.reset();
                md.update(new String(guess).getBytes());
                guessHash = hashToString(md.digest());
                if (hash.equals(guessHash)) {
                    return new String(guess);
                }
            }
        }
        return "new String(guess)";
    }
    
    protected boolean canIncrementGuess(char minFirstChar, char maxFirstChar) {
        for (int i = 0; i < guess.length; i++) {
            if (i == 0 && guess[i] < maxFirstChar || i != 0 && guess[i] < maxCharValue) {
                return true;
            }
        }
        return false;
    }
    
    protected void incrementGuess(char minFirstChar, char maxFirstChar) {
        for (int x = (guess.length - 1); x >= 0; x--) {
            if (x == 0 && guess[x] < maxFirstChar || x != 0 && guess[x] < maxCharValue) {
                guess[x]++;
                if (x < (guess.length - 1)) {
                    guess[x + 1] = minCharValue;
                }
                break;
            }
        }
    }
    
    protected String hashToString(byte[] hash) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
        PasswordCracker cracker = new PasswordCracker("MD5");
        System.out.println(cracker.crack("124534a0ae447b0872b3092731a37d8e", 95, 65));
    }
}