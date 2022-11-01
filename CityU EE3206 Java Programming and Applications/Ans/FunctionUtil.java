/*
    The class FunctionalUtil provides generic methods for
    processing a List using Functional objects.
*/

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionUtil 
{
    // Apply an action (computation) on each item of type T in input list. 
    // Transform the item to type R, and aggregate/save the results as a List<R>.
    public static <T, R> List<R> transform(List<T> list, 
                                           BiConsumer<List<R>, ? super T> action)
    {
        List<R> resultList = new ArrayList();
        for (T item : list)
            action.accept(resultList, item);
        
        return resultList; 
    }
    
    // Apply an action on each item in a list
    public static <T> void forEach(List<T> list, Consumer<? super T> action)
    {
        for(T item : list)
            action.accept(item);
    }

    // Apply a filter to a list, returned a list of items that satisfy the predicate.
    public static <T> List<T> filter(List<T> list, Predicate<? super T> predicate)
    {
        List<T> filteredList = new ArrayList<>();
        for(T item : list)
            if (predicate.test(item)) {
                filteredList.add(item);
        }
        return filteredList;
    }
    
    // Map each item of Type T in a list to a value of Type R,
    // and save the mapped value to a new List of Type R.
    public static <T, R> List<R> map(List<T> list, Function<? super T, R> mapper)
    {
        List<R> mappedList = new ArrayList<>();
        for(T item : list)
            mappedList.add(mapper.apply(item));
        return mappedList;
    }

    // Return the average value based on the given measurer.
    public static<T> double average(List<T> list, Function<? super T, Double> measurer)
    {
        double avg = 0.0;   
        if (list.size() > 0)
        {
            for (T item : list)        
                avg += measurer.apply(item);
        
            avg /= list.size();
        }
        return avg;
    }
}
