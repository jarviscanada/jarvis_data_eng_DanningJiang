package ca.jrvs.practice.codingChallenge;

public class StringToInt {
    // implementation of atoi() function in C,which takes a string as an argument and returns its value of type int.
    public static int myAtoi(String str) {
        //if str is NULL or str contains non-numeric characters then return 0 as the number is not valid
        if (str == "" || str.matches("[a-zA-Z]+") || str.matches(".*[^0-9].*")) {
            return 0;
        }
        //Initialize result
        int res = 0;

        // Iterate through all characters of input string and update result
        // take ASCII character of corresponding digit and subtract the code from '0'
        // to get numerical value and multiply res by 10 to shuffle digits left to update running total.
        for (int i=0;i<str.length();i++){
            res = res * 10 + (str.charAt(i) - '0');
        }
        return res;

    }

    public static void main(String[] args) {
        String str = "89789";
        //function call
        int val = myAtoi(str);
        System.out.println(val);
    }


}