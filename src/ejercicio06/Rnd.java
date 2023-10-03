package ejercicio06;

public class Rnd {
	protected int rnd;

	public int getRnd() {
		return rnd;
	}

	public void setRnd(int rnd) {
		this.rnd = rnd;
	}

	public Rnd() {
		this.rnd = (int) Math.random()*10+5;
	}
	

}
