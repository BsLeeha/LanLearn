package FunctionalProgramming;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

class UtilHelper {
    /* use Predicate to do filter */
    static <E> List<E> filteredList(List<E> rawList, Predicate<E> predicate) {
        List<E> newList = new ArrayList<>();
        for (E elem : rawList) {
            if (predicate.test(elem))
                newList.add(elem);
        }
        return newList;
    }

    static <T, R> List<R> mappedList(List<T> rawList, Function<T, R> mapper) {
        List<R> newList = new ArrayList<>();

        for (T elem : rawList) {
            newList.add(mapper.apply(elem));
        }

        return newList;
    }
}
