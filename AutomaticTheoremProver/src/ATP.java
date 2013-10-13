import java.awt.EventQueue;

//editing stuff
//testing commit
public class ATP {
	
	private static GUI gui;
	private static Prover prover;

	public static void main(String[] args) {
		prover = new Prover();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui = new GUI();
					gui.setModel(prover);
					prover.setView(gui);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}