
public class Axiom {
	
	public static String AND = "&";				// logical AND 
	public static String OR = "|";				// logical OR
	public static String IMPLY = "=>";			// logical IMPLICATION (commonly => or ->)
	public static String DOUBLE_IMPLY = "<==>";	// logical DOUBLE IMPLICATION (commonly <--> or <==>)
	public static String NEGATIVE = "-";			// logical NEGATION
	
	private Node axiom;

	public Axiom(String expression) {
		expression = expression.replaceAll(Axiom.IMPLY, Node.IMPLY+"");
		expression = expression.replaceAll(Axiom.DOUBLE_IMPLY, Node.DOUBLE_IMPLY+"");
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
	
	public boolean applyDistribution() {
		return axiom.applyDistribution();
	}
	
	// allow user to set what they'd like the operator symbols to be
	public void setAnd(String and) { 					if(!checkCollisions(and)) 			AND = and; }
	public void setOr(String or) { 						if(!checkCollisions(or)) 			OR = or; }
	public void setImply(String imply) { 				if(!checkCollisions(imply)) 		IMPLY = imply; }
	public void setDoubleImply(String doubleImply) { 	if(!checkCollisions(doubleImply))	DOUBLE_IMPLY = doubleImply; }
	
	// check if there are collisions with other operators
	public boolean checkCollisions(String operator) {
		if(operator.equals(AND))
			return true;
		else if(operator.equals(OR))
			return true;
		else if(operator.equals(IMPLY))
			return true;
		else if(operator.equals(DOUBLE_IMPLY))
			return true;
		else if(operator.equals(NEGATIVE))
			return true;
		else
			return false;
	}
}
