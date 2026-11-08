package easy;

public class Sqrt {
    public int mySqrt(int x) {
        int result = 1;
        if (x <= 3) return 1;

        while (result < x){
            long multi = (long) result * result;
            if (multi  == x){
                return result;
            }
            if (multi > x){
                return  result - 1;
            }
            result++;
        }
        return result;
    }
}
