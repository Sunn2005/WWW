package easy;

public class Palindrome {
    public boolean isPalindrome(int x) {
        if(x < 0) return false;
        String a = String.valueOf(x);
        String reversed = new StringBuilder(a).reverse().toString();
        return a.equals(reversed);

    }
}
