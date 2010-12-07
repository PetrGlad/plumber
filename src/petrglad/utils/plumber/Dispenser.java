package petrglad.utils.plumber;

import java.util.Observable;
import java.util.Observer;

/**
 * List of listeners.
 * <p>
 * If only change events are involved you may consider using {@link Observable} and {@link Observer} instead.
 * 
 * @author Petr Gladkikh
 */
public interface Dispenser<T>
{
  void add(Sink<T> sink);
  
  void remove(Sink<T> sink);
}
