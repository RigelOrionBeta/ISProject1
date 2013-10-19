
public class Theorem {
	
	private Node theorem;

	public Theorem(String expression) {
		expression.replaceAll("=>", Node.IMPLY+"");
		expression.replaceAll("<=>", Node.DOUBLE_IMPLY+"");
		theorem = new Node(expression);
	}
	
	public String toString() {
		String exp = theorem.toString();
		exp.replaceAll(Node.IMPLY+"", "=>");
		exp.replaceAll(Node.DOUBLE_IMPLY+"", "<=>");
		return exp;
	}

}
