import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Prover {
	
	private GUI gui;
	
	final public static String OR = "^";
	final public static String AND = "&";
	final public static String IMPLY = "=>";
	final public static String EQUIVALENCE = "==";
	final public static String DOUBLEIMPLY = "<==>";
	final public static String NL = "\n";
	
	private ArrayList<String> types;
	private HashMap<String, String> constants;
	private HashMap<String, String> variables;
	private ArrayList<Predicate> predicates;
	private ArrayList<Axiom> axioms;
	private ArrayList<Theorem> theorems;


	public Prover() {
		
	}

	/** setTypes
	 *  get all the types and put in ArrayList
	 *  @param s - the types are in this string
	 */
	public void setTypes(String s) throws IllegalArgumentException {
		types = new ArrayList<String>();
		Scanner sc = new Scanner(s);
		while(sc.hasNextLine()) {
			String temp = sc.nextLine().trim();
			if(!temp.equals("")) 
				types.add(temp);
		}
		sc.close();
	}

	public void setConstants(String s) throws IllegalArgumentException {
		// initialize the map 
		constants = new HashMap<String, String>();
		Scanner sc = new Scanner(s);
		while(sc.hasNextLine()) {
			String temp = sc.nextLine();
			String[] tokens = temp.split("=");			// tokens[0] is the constant, tokens[1] is the type of the constant
			trim(tokens);
			if(types.contains(tokens[1])) {				// check if type is valid, (been declared by user as a "Type")
				constants.put(tokens[0], tokens[1]);	// add the constant to the list of constants associated with that type
			} else {
				throw new IllegalArgumentException("ERROR on " + temp + ": Type " + tokens[1] + " has not been declared.");
			}
		}
		sc.close();
	}
	
	public void setVariables(String s) throws IllegalArgumentException {
		// initialize the list 
		variables = new HashMap<String, String>();

		Scanner sc = new Scanner(s);
		while(sc.hasNextLine()) {
			String temp = sc.nextLine();
			String[] tokens = temp.split("=");			// tokens[0] is the constant, tokens[1] is the type of the constant
			trim(tokens);
			if(types.contains(tokens[1])) {				// check if type is valid, (been declared by user as a "Type")
				variables.put(tokens[0], tokens[1]);	// add the constant to the list of constants associated with that type
			} else {
				throw new IllegalArgumentException("ERROR on " + temp + ": Type " + tokens[1] + " has not been declared.");
			}
		}
		sc.close();
	}

	public void setPredicates(String s) throws IllegalArgumentException {
		predicates = new ArrayList<Predicate>();
		Scanner sc = new Scanner(s);
		while(sc.hasNextLine()) {
			String predicate = sc.nextLine();
			String name = predicate.substring(0,predicate.indexOf('('));
			String args = predicate.substring(predicate.indexOf('(')+1, predicate.indexOf(')'));
			String[] arguments = args.split(",");
			trim(arguments);
			if(validArguments(arguments)) {
				Predicate temp = new Predicate(name, arguments);
				predicates.add(temp);
			} else {
				throw new IllegalArgumentException("ERROR on " + predicate + ": one or more arguments have not been defined.");
			}
		}
	}

	public void setAxioms(String axioms) {
		
	}

	public void setTheorems(String theorems) {
		
	}
	
	public void prove() {
		// print all types
		String proof = "";
		proof+="Types:" + NL;
		for( String type : types ) {
			proof+=type + NL;
		}
		proof+=NL;
		// print all types and their associated constants
		proof+="Constants:" + NL;
		for(String constant : constants.keySet()) {
			proof += (constant + " = " + constants.get(constant) + NL);
		}
		proof+=NL;
		// print all types and their associated variables
		proof+="Variables:" + NL;
		for(String variable : variables.keySet()) {
			proof += (variable + " = " + variables.get(variable)) + NL;
		}
		proof+=NL;
		// print all types and their associated variables
		proof+="Predicates:" + NL;
		for(Predicate predicate : predicates) {
			proof += predicate.toString() + NL;
		}
		gui.setProof(proof);
	}
	
	private boolean validArguments(String[] args) {
		boolean valid = true;
		for(String arg : args) {
			if(!types.contains(arg)) {
				valid = false;
				break;
			}
		}
		return valid;
	}
	
	private void trim(String[] s) {
		for(int i = 0; i < s.length; i++) {
			s[i] = s[i].trim();
		}
	}

	public void setView(GUI gui) {
		this.gui = gui;
	}

}
