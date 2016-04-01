package chronotimer;

import java.util.Date;
import com.google.gson.annotations.JsonAdapter;

/**
 * Extension of the java.util.Date class in order to override the
 * {@link #toString()} method.
 */
@JsonAdapter(TimeJsonAdapter.class)
public class Time extends Date {
	public Time() {
		super();
	}

	public Time(long l) {
		super(l);
	}

	@Override
	public String toString() {
		return Timer.convertToTimeString(this);
	}
}
