import java.util.ArrayList;


public class Node {
	
	public static final char AND = '&';
	public static final char OR = '|';
	public static final char IMPLY = '>';
	public static final char DOUBLE_IMPLY = '*';
	public static final char PLEFT = '(';
	public static final char PRIGHT = ')';
	public static final char NEGATIVE = '-';
	
	private boolean negative;
	private Node left;
	private Node right;
	
	private String root;
	
	public Node( String expression ) {
		// if there are parentheses around the entire expression, and the entire expression is negated
		if(encased(expression) && expression.startsWith(NEGATIVE+"")) {
			negative = true;						// make the node negative
			expression = expression.substring(1);	// remove the negative sign
			expression = trim(expression);			// trim the useless parentheses
		}
		// if there is a logical operator in there, parse the arguments
		if(	expression.contains(AND + "") || expression.contains(OR + "") || expression.contains(IMPLY + "")) {
			expression = trim(expression);
			findMiddle(expression);
		} else {	// its a leaf, meaning its an argument itself
			negative = expression.startsWith(NEGATIVE+"");
			root = expression.replace(NEGATIVE + "", "");
			left = null;
			right = null;
		}
	}
	
	/** negate()
	 * negate this expression
	 */
	public void negate() {
		negative = !negative;
	}

	/** trim()
	 * trims useless parentheses (ex: (a&b) == a&b)
	 * @param s  the expression to trim
	 * @return the trimmed expression
	 */
	private String trim(String s) {
		if(encased(s)) {
			return s.substring(1, s.length()-1);
		} else {
			return s;
		}
	}
	
	/** returns if the expression is surrounded by parentheses
	 * 
	 * @param s  check if this is encased
	 * @return true if encased, false otherwise
	 */
	private boolean encased(String s) {
		int countP = 0;		// count of parentheses, 1 for every '(', -1 for every ')'
		int matchedP = -1;  // index where the parentheses end up matching
		if(s.startsWith(NEGATIVE+"")) {  // if it starts with a negative sign, ignore the negative
			s = s.substring(1);
		}
	
		if(s.contains(PLEFT + "")) {	
			
			// increment count for every '(', decrement for every ')'
			// when the parentheses match, break out of the loop
			for(int i = 0; i < s.length(); i++) {
				if(			s.charAt(i) == PLEFT) {
					countP++;
				} else if(	s.charAt(i) == PRIGHT) {
					countP--;
				}
				if(countP == 0) {
					matchedP = i;
					break;
				}
			}
		}
		// if they matched only at the end, and there were an equal number of left and right
		// parentheses, then return true, this is encased
		if(matchedP == s.length()-1 && countP == 0) {
			return true;
		} else if(countP != 0 ) {	// mismatched parentheses if count isnt equal
			throw new IllegalArgumentException("Mismatched parentheses.");
		} else {
			return false;
		}
	}

	/** findMiddle()
	 * finds the "middle" operator, and recursively constructs the axiom's tree
	 * based on what's left of it and right of it.
	 * @param s  the expression
	 */
	private void findMiddle(String s) {
		ArrayList<Integer> operations = new ArrayList<Integer>();
		
		// compile the index of each operator
		for(int i = 0; i < s.length(); i++) {
			if( s.charAt(i) == OR || s.charAt(i) == AND || s.charAt(i) == IMPLY) {
				operations.add(i);
			}
		}
		
		// if there is only one operator, then just branch based off of that
		if(operations.size() == 1) {
			left = new Node(s.substring(0, operations.get(0)));
			right = new Node(s.substring(operations.get(0)+1));
			root = s.charAt(operations.get(0)) + "";
		} else {
			// go through each operator and check if it's the middle
			for(Integer i : operations) {
				if(isMiddle(s, i)) {
					left = new Node(s.substring(0, i));
					right = new Node(s.substring(i+1));
					root = s.charAt(i) + "";
					break;
				}
			}
		}
	}
	
	/** isMiddle()
	 * checks if an operator is the middle operator
	 * @param s  the logical expression
	 * @param i  the index to check
	 * @return true if it is, false otherwise
	 */
	private boolean isMiddle(String s, Integer i) {
		boolean isMiddle = false;
		int countLLP = countChar(s.substring(0,i), PLEFT);	// number of left parentheses in substring left of index i
		int countRLP = countChar(s.substring(0,i), PRIGHT);	// number of right parentheses in substring left of index i
		if(countLLP == countRLP) {
			isMiddle = true;
		}
		return isMiddle;
	}
	
	/** countChar()
	 * count number of occurrences of a character in a string
	 * @param s  string to look for characters in
	 * @param c  the character to compare to
	 * @return count  # of occurrences of c in s
	 */
	private int countChar(String s, char c) {
		int count = 0;
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}
	
	/** negativeSign()
	 * 
	 * @return return a "-" if negative, otherwise empty string
	 */
	private String negativeSign() {
		if(negative) {
			return NEGATIVE + "";
		} else {
			return "";
		}
	}

	/** left()
	 * 
	 * @return left  node left of this one
	 */
	public Node left() {
		return left;
	}
	
	/** right()
	 * 
	 * @return right  node right of this one
	 */
	public Node right() {
		return right;
	}
	
	/** isLeaf()
	 * 
	 * @return return true if there are not left and right nodes, false otherwise
	 */
	public boolean isLeaf() {
		return left == null && right == null;
	}
	
	/** toString()
	 * recursive
	 * @return String representation of this node
	 */
	public String toString() {
		if( left != null && right != null) {
			return negativeSign() + "(" + left.toString()  + root + right.toString() + ")";
		} else {
			return negativeSign() + root;
		}
	}
	
	/** convertImply()
	 * apply material implication to this node and to nodes branching from it
	 */
	public void convertImply() {
		if(root.equals(IMPLY + "")) {
			root = OR+"";
			left.negate();
		}
		if(!isLeaf()) {
			left.convertImply();
			right.convertImply();
		}
	}

	/** propogate()
	 * propogate negative signs based on DeMorgan's Laws
	 */
	public void propogate() {
		// if this node is negative and not a leaf
		// use DeMorgan's Law and negate the left and right
		// arguments and turn OR into AND, or AND into OR
		if(negative && !isLeaf()) {
			negate();
			if(root.equals(OR+"")) {
				root = AND+"";
			} else {
				root = OR+"";
			}
			left.negate();
			right.negate();
		}
		// if this is not a leaf, do the same for branching nodes
		if(!isLeaf()) {
			left.propogate();
			right.propogate();
		}
	}
}
