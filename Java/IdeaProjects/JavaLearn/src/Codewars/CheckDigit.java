package Codewars;

public class CheckDigit {
    public boolean isDigit(String s) {

        try {
            Double.parseDouble(s);
            return true;
        } catch (Exception e) {
            return false;
        }

        /*
         * ?: 0 or 1 time
         * (-)2(.34)
         */
//        return s.matches("-?\\d+(\\.\\d+)?");
    }
}
