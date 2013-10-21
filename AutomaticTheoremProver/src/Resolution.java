import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Resolution {
	
	// oft used strings
	private static final String TAB = "     ";
	private static final String NL = "\n";
	
	// proof
	private String proof;
	
	// axioms and theorems
	private ArrayList<Axiom> axioms;
	private ArrayList<Axiom> theorems;
	
	// axioms and theorems in CNF
	private ArrayList<AxiomCNF> axiomsCNF;
	private ArrayList<AxiomCNF> theoremsCNF;
	
	/** Resolution
	 */
	public Resolution() {
		axioms = new ArrayList<Axiom>();
		theorems = new ArrayList<Axiom>();
	}
	
	/** addAxiom()
	 * @param axiom  axiom to be added to axiom list
	 * @return true if it was added, false if it wasnt
	 */
	public boolean addAxiom(Axiom axiom) {
		if( axioms.contains(axiom)) {
			return false;
		} else {
			axioms.add(axiom);
			return true;
		}
	}
	
	/** addTheorem()
	 * @param theorem  theorem to be added to theorem list
	 * @return true if it was added, false if it wasnt
	 */
	public boolean addTheorem(Axiom theorem) {
		if(theorems.contains(theorem)) {
			return false;
		} else {
			theorems.add(theorem);
			return true;
		}
	}
	
	/** getProof()
	 * 
	 * @return A string representation of the proof by resolution
	 */
	public String getProof() {
		return proof;
	}
	
	/**
	 * Begins resolution algorithm, which tries to figure out
	 * if the axioms list satisfies the theorem list
	 * @return
	 */
	public boolean begin() {
		axiomsCNF = new ArrayList<AxiomCNF>();
		theoremsCNF = new ArrayList<AxiomCNF>();
		
		boolean result = false;
		int line = 1;
		proof = new String();
		
		for(Axiom axiom : axioms) {
			ConvertCNF.convert(axiom); 
			axiomsCNF.add(flatten(axiom));
		}
		for(Axiom theorem : theorems) {
			ConvertCNF.convert(theorem);
			theoremsCNF.add(flatten(theorem));
		}
		
		proof += "Beginning proof by resolution..." + NL + NL;
		proof += TAB + TAB +"** Note: Axioms and Theorems are converted to CNF **" + NL + NL;
		proof += "Axioms:" + NL;
		for(AxiomCNF axiom : axiomsCNF) {
			proof+= TAB + line + ". " + axiom.toString() + NL;
			line++;
		}
		proof += NL + NL + "Separating Axioms" + NL;
		AxiomCNF newAxiom;
		while((newAxiom = separateAxioms()) != null) {
			proof+= TAB + line + ". " + newAxiom.toString() + NL;
			line++;
		}
		proof += NL + NL + "Applying Modus Ponens" + NL;
		while((newAxiom = modusPonens()) != null) {
			proof+= TAB + line + ". " + newAxiom.toString() + NL;
			line++;
		}
		
		proof += NL + "Theorems" + NL;
		for(AxiomCNF theorem : theoremsCNF) {
			proof+= TAB + line + ". " + theorem.toString()  + NL;
			line++;
		}
		
		proof += NL + NL + "Separating Theorems" + NL;
		AxiomCNF newTheorem;
		while((newTheorem = separateTheorems()) != null) {
			proof+= TAB + line + ". " + newTheorem.toString() + NL;
			line++;
		}
		int matches = 0;
		proof += NL + NL + "Matching theorems with axioms..." + NL;
		for(int i = 0; i < theoremsCNF.size(); i++) {
			AxiomCNF theorem = theoremsCNF.get(i);
			for(int j = 0; j < axiomsCNF.size(); j++) {
				AxiomCNF axiom = axiomsCNF.get(j);
				if(theorem.equals(axiom)) {
					matches++;
				}
			}
		}
		
		if(matches == theoremsCNF.size()) {
			proof += NL + NL + "Theorem follows from axioms.";
		} else {
			proof += NL + NL + "Theorem does not follow from axioms.";
		}
		
		return matches == theoremsCNF.size();
	}
	
	/** modusPonens()
	 * applies modus ponens to each axiom to find new axioms
	 * @return null if more can't be found, otherwise the new axiom
	 */
	private AxiomCNF modusPonens() {
		AxiomCNF newAxiom;
		for(AxiomCNF axiom1 : axiomsCNF) {
			for(AxiomCNF axiom2 : axiomsCNF ) {
				newAxiom = axiom1.modusPonens(axiom2);
				if(newAxiom != null && !checkIfAxiom(newAxiom)) {
					axiomsCNF.add(newAxiom);
					return newAxiom;
				}
			}
		}
		return null;
	}
	
	
	/** separateAxioms()
	 * adds a separated axiom to the list of axioms
	 * @return an AxiomCNF object that isn't already in the axiom list
	 */
	private AxiomCNF separateAxioms() {
		AxiomCNF[] newAxioms;
		for(AxiomCNF cnf : axiomsCNF) {
			if(cnf.canBeSeparated()) {
				newAxioms = cnf.separate();
				for(AxiomCNF newAxiom : newAxioms) {
					if(!checkIfAxiom(newAxiom)) {
						axiomsCNF.add(newAxiom);
						return newAxiom;
					}
				}
			}
		}
		return null;
	}
	
	/** checkIfAxiom()
	 * 
	 * @param axiom  axiom to check if it is a axiom
	 * @return true if in axioms list
	 */
	private boolean checkIfAxiom(AxiomCNF axiom) {
		for(AxiomCNF cnf : axiomsCNF) {
			if(cnf.equals(axiom)) {
				return true;
			}
		}
		return false;
	}
	
	/** separateTheorems()
	 * 
	 * @return an AxiomCNF object that isn't already in the theorem list
	 */
	private AxiomCNF separateTheorems() {
		AxiomCNF[] newTheorems;
		for(AxiomCNF cnf : theoremsCNF) {
			if(cnf.canBeSeparated()) {
				newTheorems = cnf.separate();
				for(AxiomCNF newTheorem : newTheorems)
					if(!checkIfTheorem(newTheorem)) {
						theoremsCNF.add(newTheorem);
						return newTheorem;
					}
			}
		}
		return null;
	}
	
	/** checkIfTheorem()
	 *  
	 * @param theorem  theorem to check if it is a theorem
	 * @return true if in theorems list
	 */
	private boolean checkIfTheorem(AxiomCNF theorem) {
		for(AxiomCNF cnf : theoremsCNF) {
			if(cnf.equals(theorem)) {
				return true;
			}
		}
		return false;
	}

	/** flatten()
	 * essentially removes parentheses when not necessary (ex. A|(C|B) becomes A|C|B)
	 * @param axiom  axiom to flatten
	 * @return  new axiom in CNF form, and flattened
	 */
	private AxiomCNF flatten(Axiom axiom) {
		AxiomCNF cnf;
		String flat = axiom.toFlatString();
		cnf = new AxiomCNF(flat);
		return cnf;
	}
	
	/** AxiomCNF
	 * In-class object specially for resolution.
	 * Inputs MUST be in CNF for it to work properly.
	 */
	public class AxiomCNF {
		private String[] operands;		// OR separated values
		
		/** AxionCNF()
		 * creates an array of expressions that only have the OR operator
		 * @param axiom  String presentation of axiom, must be in CNF and be flat
		 */
		public AxiomCNF(String axiom) {
			operands = axiom.split("&");	// splits it based on AND operator
		}
		
		// only can be separated if size is greater than 1
		public boolean canBeSeparated() {
			return operands.length > 1;
		}
		
		// returns array of separated values
		public AxiomCNF[] separate() {
			AxiomCNF[] separated = new AxiomCNF[operands.length];
			for(int i = 0; i < operands.length; i++) { 
				separated[i] = new AxiomCNF(operands[i]);
			}
			return separated;
		}
		
		// two axioms are equal if they have the same operands, regardless of positioning
		public boolean equals(AxiomCNF cnf) {
			int found = 0;
			if(cnf == null || cnf.length() != this.length()) {
				return false;
			} else {
				// compare each operand to every other operand
				for(String operand1 : operands) {
					String ors1[] = operand1.split("\\|");
					for(String operand2 : cnf.getOperands()) {
						String ors2[] = operand2.split("\\|");
						if(equalOperands(ors1, ors2)) {
							found++;
						}
					}
				}
			}
			return found == cnf.length();
		}
		
		/** modusPonens()
		 * apply modus ponens to two axioms
		 * @param test  the axiom to apply to
		 * @return a new axiom if a new one can be created, otherwise null
		 */
		public AxiomCNF modusPonens(AxiomCNF test) {
			// OR'd values arraylist
			ArrayList<String> ors1 = new ArrayList<String>();
			ArrayList<String> ors2 = new ArrayList<String>();
			
			// only accepts completely separated axioms
			if(test.canBeSeparated() || this.canBeSeparated()) return null;
			if(test.length() > this.length()) return null;
			
			// convert array of OR'd values to arraylist
			for(String s : operands[0].split("\\|")) ors1.add(s);
			for(String s : test.getOperands()[0].split("\\|")) ors2.add(s);
			
			// reverse the polarity of the individual values
			for(int i = 0; i < ors2.size(); i++) {
				String string = new String(ors2.get(i));
				if(string.startsWith("-")) {
					ors2.set(i, string.substring(1, string.length()));
				} else {
					ors2.set(i,"-" + string);
				}
			}
			
			// remove all instances in ors2 from ors1
			ors1.removeAll(ors2);
			
			// return null if they are exactly the same
			// this means a contradiction exists, 
			// but this functionality isn't implemented
			if(ors1.size() == 0) return null;
			
			// create a new axiom 
			String newAxiom = "";
			for(int i = 0; i < ors1.size(); i++) {
				if(i==0) {
					newAxiom += ors1.get(0);
				} else {
					newAxiom += "|" + ors1.get(i);
				}
			}
			return new AxiomCNF(newAxiom);
		}
		
		// compares two arrays and sees if contents are equal.
		private boolean equalOperands( String[] var1, String[] var2 ) {
			int found = 0;
			if(var1.length != var2.length) {
				return false;
			}
			for(String x : var1) {
				for(String y : var2) {
					if(x.equals(y)) {
						found++;
						break;
					}
				}
			}
			return found == var1.length;
		}

		// string representation of this axiom
		public String toString() {
			String axiom;
			if(operands.length == 1) {
				axiom = operands[0];
			} else {
				axiom = "(" + operands[0] + ")";
				for(int i = 1; i < operands.length; i++ ) {
					axiom += "&" + "(" + operands[i] + ")";
				}
			}
			
			return axiom;
		}
		
		private int length() { return operands.length; }
		private String[] getOperands() { return operands; }		
	}
}
