package by.gsu.paveldzunovich.rental.ifaces;

import java.util.List;

public interface IFilter<T> {

	List<T> filter(List<T> items);

}
