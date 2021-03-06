public class ConvertCNF {
	public ConvertCNF() { }
	
	public static void convert(Axiom axiom) {
		
		// first, apply Material Implication
		// Material Implication:
		// 		A=>B becomes
		// 		-A|B
		axiom.convertImply();
		
		// second, apply DeMorgan's Laws
		// DeMorgan's Laws:
		// 		-(A&B) becomes
		// 		(-A|-B)
		//
		//		-(A|B) becomes
		//		(-A&-B)
		axiom.applyDeMorgan();
		
		// third, apply Distribution Law
		// Distribution Law:
		// 		(A&B)|C becomes
		//		(A|C)&(B|C)
		// Keep applying until it cannot be applied anymore
		while(!axiom.applyDistribution());
	}
}
