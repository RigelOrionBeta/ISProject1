public class ConvertCNF {
	public ConvertCNF() { }
	
	public static void convert(Axiom axiom) {
		System.out.println("Before: " + axiom);
		// first, apply Material Implication
		// Material Implication:
		// 		A=>B becomes
		// 		-A|B
		axiom.convertImply();
		System.out.println("Apply Material Implication: " + axiom);
		
		// second, apply DeMorgan's Laws
		// DeMorgan's Laws:
		// 		-(A&B) becomes
		// 		(-A|-B)
		//
		//		-(A|B) becomes
		//		(-A&-B)
		axiom.applyDeMorgan();
		System.out.println("Apply DeMorgan's: " + axiom);
		
		// third, apply Distribution Law
		// Distribution Law:
		// 		(A&B)|C becomes
		//		(A|C)&(B|C)
		while(!axiom.applyDistribution());
		System.out.println("Apply Distribution: " + axiom);
	}
}
