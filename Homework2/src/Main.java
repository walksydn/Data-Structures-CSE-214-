public class Main {

    /**
     * Checks whether or not a word is a palindrome through reversing the word
     * using a stack
     * @param word the word to be tested
     * @return true if the word is a palindrome
     */
    public static boolean isPalindrome(String word){
        Stack<Character> s = new Stack<>();
        String rev = "";
        //adds each character to the stack
        for(int i = 0; i < word.length(); i++){
            s.push(word.charAt(i));
        }
        //pops the characters out of the stack in reverse order,
        // concatenating a string throughout the process
        while(!s.isEmpty()){
            rev = s.pop().toString() + rev;
        }
        //checks if the reverse string is equal to the entered word
        if(rev.equals(word)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculates the Levenshtein distance between two strings recursively
     * @param a the first string to be compared
     * @param b the second string to be compared
     * @return the number of characters that need to be changed/swapped
     *   to make the entered strings equal
     */
    public static int editDistance(String a, String b){
        //returns 0 if a and b are the same
        if(a.equals(b)){
            return 0;
        } else {
            //begins recursion to calculate the distance
            int i = a.length();
            int j = b.length();
            return function(a, b, i, j);
        }
    }

    /**
     * Uses the Levenshtein distance formula to calculate the distance between two strings
     * @param a the first string to be compared
     * @param b the second string to be compared
     * @param i the index of a to be checked
     * @param j the index of b to the checked
     * @return the levenshtein distance between a and b
     */
    private static int function(String a, String b, int i, int j){
        //stop conditions
        if(i == 0){
            return j;
        } else if (j == 0){
            return i;
        }
        //calculate the three possible values for the D function
        int x = function(a, b, i-1, j) + 1;
        int y = function(a, b, i, j-1) + 1;
        int z = function(a, b, i-1, j-1) + function2(a, b, i-1, j-1);
        //returns the minimum of the three part function above
        if(x <= y && x <= z){
            return x;
        } else if (y <= x && y <= z){
            return y;
        } else {
            return z;
        }
    }

    /**
     * Calculates the added value for the third clause of the second part
     *  of the Levenshtein distance formula
     * @param a the first string to be compared
     * @param b the second string to be compared
     * @param i the index of a to be checked
     * @param j the index of b to the checked
     * @return 0 if the i index of a is equal to the i index of b, 1 otherwise
     */
    private static int function2(String a, String b, int i, int j){
        if(a.charAt(i) == b.charAt(j)){
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Calculates the Levenshtein distance between two strings recursively
     *  implementing dynamic programming to increase the efficiency through
     *  storing values in a table rather than recalculating them
     * @param a the first string to be compared
     * @param b the second string to be compared
     * @return the number of characters that need to be changed/swapped
     *   to make the entered strings equal
     */
    public static int fastEditDistance(String a, String b){
        if(a.equals(b)){ //return 0 if a and b are the same
            return 0;
        } else {
            int i = a.length();
            int j = b.length();
            Integer[][] table = new Integer[i][j]; //initializes the table to store values in
            //begins the recursive calculation
            return fastFunction(a, b, i, j, table);
        }
    }

    /**
     * Checks whether or not the second part of the Levenshtein distance equation
     *  requested has been calculated yet, if not it is calculated and stored,
     *  otherwise the stored value is returned
     * @param a the first string to be compared
     * @param b the second string to be compared
     * @param i the index of a to be checked
     * @param j the index of b to the checked
     * @param table the table used to store the previously calculated values
     * @return one iteration of the Levenshtein distance between a and b
     */
    private static int fastFunction(String a, String b, int i, int j, Integer[][] table){
        if(i == 0){
            return j;
        } else if (j == 0){
            return i;
        }
        if(table[i-1][j-1] == null){
            table[i-1][j-1] = fastFunction2(a, b, i, j, table);
        }
        return table[i-1][j-1];
    }

    /**
     * Calculates the value of the Levenshtein distance equation and stores it in the array
     * @param a the first string to be compared
     * @param b the second string to be compared
     * @param i the index of a to be checked
     * @param j the index of b to the checked
     * @param table the table used to store the previously calculated values
     * @return one iteration of the Levenshtein distance between a and b
     */
    private static int fastFunction2(String a, String b, int i, int j, Integer[][] table){
        int x = fastFunction(a, b, i-1, j, table) + 1;
        int y = fastFunction(a, b, i, j-1, table) + 1;
        int z = fastFunction(a, b, i-1, j-1, table) + function2(a, b, i-1, j-1);
        if(x <= y && x <= z){
            return x;
        } else if (y <= x && y <= z){
            return y;
        } else {
            return z;
        }
    }

    /**
     * Used to run the isPalindrome, editDistance, and fastEditDistance methods
     */
    public static void main(String[] args) {

    }
}