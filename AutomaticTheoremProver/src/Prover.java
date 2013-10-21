import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Prover {
	
	private GUI gui;
	
	// final values for logical operators
	final public static String OR = "^";
	final public static String AND = "&";
	final public static String IMPLY = "=>";
	final public static String EQUIVALENCE = "==";
	final public static String DOUBLEIMPLY = "<==>";
	final public static String NL = "\n";
	
	// collection of different variables, functions, etc
	private ArrayList<String> types;
	private HashMap<String, String> constants;
	private HashMap<String, String> variables;
	private ArrayList<Predicate> predicates;
	private ArrayList<Axiom> axioms;
	private ArrayList<Axiom> theorems;

	// used to solve the argument
	private Resolution resolution;

	/** setTypes
	 *  get all the types and put in ArrayList
	 *  @param s - the types are in this string
	 */
	public void setTypes(String s) throws IllegalArgumentException {
		types = new ArrayList<String>();
		Scanner sc = new Scanner(s);
		while(sc.hasNextLine()) {
			String temp = sc.nextLine().trim();
			System.out.println(temp);
			if(!temp.equals("")) 
				types.add(temp);
		}
		sc.close();
	}

	/** setConstants()
	 * 
	 * @param s  newline separated constants
	 * @throws IllegalArgumentException
	 */
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
				sc.close();
				throw new IllegalArgumentException("ERROR on " + temp + ": Type " + tokens[1] + " has not been declared.");
			}
		}
		sc.close();
	}
	
	/** setVariables()
	 * 
	 * @param s  newline separated variables
	 * @throws IllegalArgumentException
	 */
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
				sc.close();
				throw new IllegalArgumentException("ERROR on " + temp + ": Type " + tokens[1] + " has not been declared.");
			}
		}
		sc.close();
	}

	/** setPredicates()
	 * 
	 * @param s  newline separated predicates
	 * @throws IllegalArgumentException
	 */
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
				sc.close();
				throw new IllegalArgumentException("ERROR on " + predicate + ": one or more types have not been defined.");
			}
		}
		sc.close();
	}

	/** setAxioms()
	 * 
	 * @param s  newline separated axioms
	 */
	public void setAxioms(String s) {
		axioms = new ArrayList<Axiom>();
		Scanner sc = new Scanner(s);
		while(sc.hasNextLine()) {
			axioms.add(new Axiom(sc.nextLine()));
		}
		sc.close();
	}

	/** setTheorems()
	 * 
	 * @param s  newline separated theorems
	 */
	public void setTheorems(String s) {
		theorems = new ArrayList<Axiom>();
		Scanner sc = new Scanner(s);
		while(sc.hasNextLine()) {
			theorems.add(new Axiom(sc.nextLine()));
		}
		sc.close();
	}
	
	/** prove()
	 * begin proving 
	 */
	public void prove() {
		resolution = new Resolution();
		
		if(axioms == null || axioms.size() == 0) {
			gui.setProof("Must have at least one axiom");
			return;
		} else {
			for(Axiom axiom : axioms) resolution.addAxiom(axiom);
		}
		
		if(theorems == null || theorems.size() == 0) {
			gui.setProof("Must have at least one theorem");
			return;
		} else {
			for(Axiom theorem : theorems) resolution.addTheorem(theorem);
		}
		
		resolution.begin();
		
		String proof = "";
		proof += resolution.getProof();
		gui.setProof(proof);
	}

	/** check if arguments of predicate are valid
	 * 
	 * @param args the arguments
	 * @return true if valid, otherwise false
	 */
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
	
	/** trim()
	 * trim whitespaces
	 * @param s  string to trim
	 */
	private void trim(String[] s) {
		for(int i = 0; i < s.length; i++) {
			s[i] = s[i].trim();
		}
	}

	/** setView()
	 * 
	 * @param gui GUI being used
	 */
	public void setView(GUI gui) {
		this.gui = gui;
	}

}
