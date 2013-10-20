import java.util.ArrayList;

public class Resolution {
	
	private static String TAB = "     ";
	private static String NL = "\n";
	
	private String proof;
	private ArrayList<Axiom> axioms;
	private ArrayList<Axiom> theorems;
	
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
		boolean result = false;
		int line = 1;
		proof = new String();
		
		proof += "Beginning proof by resolution..." + NL + NL;
		proof += TAB + TAB +"** Note: Axioms and Theorems are converted to CNF **" + NL + NL;
		proof += "Axioms:" + NL;
		for(int i = 0; i < axioms.size(); i++) {
			ConvertCNF.convert(axioms.get(i)); 
			proof+= TAB + (i+line) + ". " + axioms.get(i).toString() + NL;
		}
		proof += NL + "Theorems" + NL;
		for(int i = 0; i < theorems.size(); i++) {
			ConvertCNF.convert(theorems.get(i)); 
			proof+= TAB + (i+line) + ". " + theorems.get(i).toString()  + NL;
		}
		
		
		return result;
	}
	
	public class AxiomCNF {
		private Axiom axiom;
		private ArrayList<String> operands;
		
		public AxiomCNF(Axiom axiom) {
			this.axiom = axiom;
			getOperands(axiom.getNode());
		}
		
		private void getOperands(Node node) {
			
		}
	}

}
