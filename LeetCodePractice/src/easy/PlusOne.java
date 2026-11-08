package easy;

public class PlusOne {
    public int[] plusOne(int[] digits) {
        int sum = 0;

        for(int i = 0; i < digits.length; i++ ){
            sum += (int) (digits[i] * Math.pow(10, digits.length - (i + 1)));
        }
        sum += 1;
        int chieudaiso = String.valueOf(sum).length();
        int[] arrays = new int[chieudaiso];
        for(int i = 0; i < chieudaiso ; i++ ){
            arrays[chieudaiso - i - 1] = sum % 10;
            sum = sum/10;
        }
        return arrays;
    }
}

