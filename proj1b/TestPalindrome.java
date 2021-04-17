import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("Tat"));
        assertFalse(palindrome.isPalindrome("taabavat"));
        assertTrue(palindrome.isPalindrome("taat"));
        assertTrue(palindrome.isPalindrome("t"));
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testIsPalindromeOffByOne() {
        assertTrue(palindrome.isPalindrome("cab", offByOne));
        assertFalse(palindrome.isPalindrome("tat", offByOne));
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("aceddb", offByOne));
        assertFalse(palindrome.isPalindrome("tat", offByOne));
        assertTrue(palindrome.isPalindrome("t", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
    }

}
