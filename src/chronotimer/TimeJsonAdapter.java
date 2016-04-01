package chronotimer;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class TimeJsonAdapter extends TypeAdapter<Time> {
	@Override
	public Time read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		return Timer.convertToTime(in.nextString());
	}

	@Override
	public void write(JsonWriter out, Time time) throws IOException {
		if (time == null) {
			out.nullValue();
			return;
		}
		out.value(Timer.convertToTimeString(time));
	}
}
