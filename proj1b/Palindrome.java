public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    private String dequeToWord(Deque<Character> deque) {
        String s = "";
        while (deque.size() > 0) {
            s = s + deque.removeFirst();
        }
        return s;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        if (d.size() <= 1) {
            return true;
        }
        if (d.removeFirst() == d.removeLast()) {
            return isPalindrome(dequeToWord(d));
        } else {
            return false;
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        if (d.size() <= 1) {
            return true;
        }
        if (cc.equalChars(d.removeFirst(), d.removeLast())) {
            return isPalindrome(dequeToWord(d), cc);
        } else {
            return false;
        }
    }
}
