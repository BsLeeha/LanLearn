import java.text.NumberFormat;
import java.time.LocalDate;

public class OOP {
    public static void main(String[] args) {
        // static factory methods
        LocalDate now=  LocalDate.now();
        LocalDate birthday = LocalDate.of(1997, 9, 04);
        System.out.println(now);
        System.out.println(birthday);

        System.out.println();

        // static factory methods
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        double x = 0.1;
        System.out.println(currencyFormatter.format(x));
        System.out.println(percentFormatter.format(x));
    }
}
