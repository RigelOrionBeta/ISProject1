import java.util.ArrayList;

public class Resolution {
	
	private static String TAB = "     ";
	private static String NL = "\n";
	
	private String proof;
	private ArrayList<Axiom> axioms;
	private ArrayList<Axiom> theorems;
	
	private ArrayList<AxiomCNF> axiomsCNF;
	private ArrayList<AxiomCNF> theoremsCNF;
	
	public Resolution() {
		axioms = new ArrayList<Axiom>();
		theorems = new ArrayList<Axiom>();
	}
	
	public boolean addAxiom(Axiom axiom) {
		if( axioms.contains(axiom)) {
			return false;
		} else {
			axioms.add(axiom);
			return true;
		}
	}
	
	public boolean addTheorem(Axiom theorem) {
		if(theorems.contains(theorem)) {
			return false;
		} else {
			theorems.add(theorem);
			return true;
		}
	}
	
	public String getProof() {
		return proof;
	}
	
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
		proof += NL + "Theorems" + NL;
		for(AxiomCNF theorem : theoremsCNF) {
			proof+= TAB + line + ". " + theorem.toString()  + NL;
			line++;
		}
		proof += NL + NL + "Separating Axioms" + NL;
		AxiomCNF newAxiom;
		while((newAxiom = separateAxioms()) != null) {
			for(AxiomCNF axiom : axiomsCNF) {
				System.out.println(axiom);
			}
			proof+= TAB + line + ". " + newAxiom.toString() + NL;
			line++;
		}
		proof += NL + NL + "Separating Theorems" + NL;
		AxiomCNF newTheorem;
		while((newTheorem = separateTheorems()) != null) {
			for(AxiomCNF theorem : theoremsCNF) {
				System.out.println(theorem);
			}
			proof+= TAB + line + ". " + newTheorem.toString() + NL;
			line++;
		}
		
		
		//theoremsCNF.get(0).equals(axiomsCNF.get(0));
		//axiomsCNF.get(0).equals(theoremsCNF.get(0));
		//System.out.println("DONE COMPARING");
		
		return result;
	}
	
	

	private AxiomCNF separateAxioms() {
		AxiomCNF[] newAxioms;
		for(AxiomCNF cnf : axiomsCNF) {
			if(cnf.canBeSeparated()) {
				newAxioms = cnf.separate();
				for(AxiomCNF newAxiom : newAxioms)
					if(!checkIfAxiom(newAxiom)) {
						axiomsCNF.add(newAxiom);
						System.out.println(newAxiom);
						return newAxiom;
					}
			}
		}
		return null;
	}
	
	private boolean checkIfAxiom(AxiomCNF axiom) {
		for(AxiomCNF cnf : axiomsCNF) {
			if(cnf.equals(axiom)) {
				System.out.println("Axiom " + axiom + " already exists.");
				return true;
			}
		}
		return false;
	}
	
	private AxiomCNF separateTheorems() {
		AxiomCNF[] newTheorems;
		for(AxiomCNF cnf : theoremsCNF) {
			if(cnf.canBeSeparated()) {
				newTheorems = cnf.separate();
				for(AxiomCNF newTheorem : newTheorems)
					if(!checkIfTheorem(newTheorem)) {
						theoremsCNF.add(newTheorem);
						System.out.println(newTheorem);
						return newTheorem;
					}
			}
		}
		return null;
	}
	
	private boolean checkIfTheorem(AxiomCNF axiom) {
		for(AxiomCNF cnf : theoremsCNF) {
			if(cnf.equals(axiom)) {
				System.out.println("Axiom " + axiom + " already exists.");
				return true;
			}
		}
		return false;
	}

	private AxiomCNF flatten(Axiom axiom) {
		AxiomCNF cnf;
		String flat = axiom.toFlatString();
		cnf = new AxiomCNF(flat);
		return cnf;
	}
	
	public class AxiomCNF {
		private String[] operands;
		
		public AxiomCNF(String axiom) {
			operands = axiom.split("&");
		}
		
		public boolean canBeSeparated() {
			return operands.length > 1;
		}
		
		public AxiomCNF[] separate() {
			AxiomCNF[] separated = new AxiomCNF[operands.length];
			for(int i = 0; i < operands.length; i++) { 
				separated[i] = new AxiomCNF(operands[i]);
			}
			return separated;
		}
		
		public boolean equals(AxiomCNF cnf) {
			int found = 0;
			System.out.println("Comparing: " + this.toString() + " and " + cnf.toString() );
			if(cnf.length() != this.length()) {
				return false;
			} else {
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
