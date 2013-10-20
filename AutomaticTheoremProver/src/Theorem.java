
public class Theorem {
	
	private Axiom theorem;

	public Theorem(String expression) {
		theorem = new Axiom(expression);
	}
	
	public String toString() {
		return theorem.toString();
	}

}
