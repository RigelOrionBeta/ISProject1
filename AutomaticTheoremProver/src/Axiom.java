
public class Axiom {
	
	private Node axiom;

	public Axiom(String expression) {
		expression = expression.replaceAll("=>", Node.IMPLY+"");
		expression = expression.replaceAll("<=>", Node.DOUBLE_IMPLY+"");
		axiom = new Node(expression);
	}
	
	public String toString() {
		String exp = axiom.toString();
		exp.replaceAll( Node.IMPLY+"", "=>");
		exp.replaceAll( "\\" + Node.DOUBLE_IMPLY, "<=>");
		return exp;
	}

	public void applyDeMorgan() {
		axiom.propogate();
	}
	
	public void convertImply() {
		axiom.convertImply();
	}
	
}
