package petrglad.utils.plumber;

/**
 * Any single argument function not returning value.
 * 
 * @author Petr Gladkikh
 */
public interface Sink<T>
{
	void put(T value);
}
