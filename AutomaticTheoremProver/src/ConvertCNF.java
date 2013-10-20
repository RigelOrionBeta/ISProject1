public class ConvertCNF {
	public ConvertCNF() {
		
	}
	
	public static void convert(Axiom axiom) {
		axiom.convertImply();
		axiom.applyDeMorgan();
	}
}
