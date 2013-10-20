
public class Predicate {
	
	private String name;
	private String[] arguments;

	public Predicate(String name, String arguments[]) {
		this.name = name;
		this.arguments = arguments;
	}
	
	public String toString() {
		String retval = name + "(";
		for(int i = 0; i < arguments.length; i++) {
			if(i != 0) {
				retval += "," + arguments[i];
			} else {
				retval += arguments[i];
			}
		}
		retval += ")";
		return retval;
	}
	
}
