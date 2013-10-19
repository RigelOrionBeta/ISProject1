import java.util.ArrayList;


public class Node {
	
	public static final char AND = '&';
	public static final char OR = '|';
	public static final char IMPLY = '>';
	public static final char DOUBLE_IMPLY = '*';
	public static final char PLEFT = '(';
	public static final char PRIGHT = ')';
	public static final char NEGATIVE = '-';
	
	private boolean isLeaf;
	private boolean negative;
	private Node left;
	private Node right;
	
	private String root;
	
	public Node( String expression ) {
		if(expression.charAt(0) == NEGATIVE) {
			negative = true;
			System.out.println(expression);
			expression = expression.substring(1);
		}
		if(	expression.contains(AND + "") || expression.contains(OR + "") || expression.contains(IMPLY + "")) {
			isLeaf = false;
			expression = trim(expression);
			findMiddle(expression);
		} else {
			isLeaf = true;
			root = expression.replace(NEGATIVE + "", "");
			left = null;
			right = null;
		}
		if(negative) {
			System.out.println("Root: " + root);
			System.out.println("Left: " + left);
			System.out.println("Right: " + right);
			System.out.println();
		}
	}
	
	public void negate() {
		negative = !negative;
	}

	private String trim(String s) {
		int countP = 0;
		int matchedP = -1;
		if(s.contains(PLEFT + "")) {
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
		if(matchedP == s.length()-1 && countP == 0) {
			return s.substring(1,s.length()-1);
		} else if(countP != 0 ) {
			throw new IllegalArgumentException("Mismatched parentheses.");
		} else {
			return s;
		}
	}


	private void findMiddle(String s) {
		ArrayList<Integer> operations = new ArrayList<Integer>();
		for(int i = 0; i < s.length(); i++) {
			if( s.charAt(i) == OR || s.charAt(i) == AND || s.charAt(i) == IMPLY) {
				operations.add(i);
			}
		}
		
		if(operations.size() == 1) {
			left = new Node(s.substring(0, operations.get(0)));
			right = new Node(s.substring(operations.get(0)+1));
			root = s.charAt(operations.get(0)) + "";
		} else {
			for(Integer i : operations) {
				if(isMiddle(s, i)) {
					//System.out.println(i + " " + s);
					left = new Node(s.substring(0, i));
					right = new Node(s.substring(i+1));
					root = s.charAt(i) + "";
					break;
				}
			}
		}
	}
	
	private boolean isMiddle(String s, Integer i) {
		boolean isMiddle = false;
		int countLLP = countChar(s.substring(0,i), PLEFT);	// number of left parentheses in substring left of index i
		int countRLP = countChar(s.substring(0,i), PRIGHT);	// number of right parentheses in substring left of index i
		if(countLLP == countRLP) {
			isMiddle = true;
		}
		return isMiddle;
	}
	
	private int countChar(String s, char c) {
		int count = 0;
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == c) {
				count++;
			}
		}
		return count;
	}
	
	private String negativeSign() {
		if(negative) {
			return NEGATIVE + "";
		} else {
			return "";
		}
	}

	public Node left() {
		return left;
	}
	
	public Node right() {
		return right;
	}
	
	public boolean isLeaf() {
		return isLeaf;
	}
	
	public String toString() {
		if( left != null && right != null) {
			return negativeSign() + "(" + left.toString()  + root + right.toString() + ")";
		} else {
			return negativeSign() + root;
		}
	}

}
