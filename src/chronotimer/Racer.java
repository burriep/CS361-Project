package chronotimer;

public class Racer {
	private int id;

	public Racer(int n) {
		id = n;
	}

	public int getNumber() {
		return id;
	}

	@Override
	public String toString() {
		return "" + id;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Racer) {
			return ((Racer) o).id == id;
		}
		return false;
	}
}
