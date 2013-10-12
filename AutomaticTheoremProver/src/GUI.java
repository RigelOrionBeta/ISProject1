import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

public class GUI {

	private JFrame frame;
	
	private Prover prover;

	private JTextArea theoremsArea = new JTextArea();
	private JTextArea axiomsArea = new JTextArea();
	private JTextArea predicatesArea = new JTextArea();
	private JTextArea variablesArea = new JTextArea();
	private JTextArea constantsArea = new JTextArea();
	private JTextArea typesArea = new JTextArea();
	private JTextArea proofsArea = new JTextArea();

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		proofsArea.setEditable(false);
		frame.setSize(1025,700);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setBackground(Color.GRAY);
		frame.setBounds(50, 50, 1000, 700);
		frame.setTitle("Automated Theorem Prover");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_2 = new JScrollPane();

		JScrollPane scrollPane_3 = new JScrollPane();

		JScrollPane scrollPane_4 = new JScrollPane();

		JScrollPane scrollPane_5 = new JScrollPane();

		JScrollPane scrollPane_6 = new JScrollPane();

		JLabel lblTypes = new JLabel("Types:");

		JLabel lblConstants = new JLabel("Constants:");

		JLabel lblVariables = new JLabel("Variables:");

		JLabel lblPredicates = new JLabel("Predicates:");

		JLabel lblAxioms = new JLabel("Axioms:");

		JLabel lblTheorems = new JLabel("Theorems:");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTypes)
								.addComponent(lblVariables, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE))
							.addGap(56)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblConstants)
								.addComponent(lblPredicates, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
								.addComponent(scrollPane_4))
							.addContainerGap())
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblAxioms, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblTheorems, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(scrollPane_5, Alignment.LEADING)
								.addComponent(scrollPane_6, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTypes)
						.addComponent(lblConstants))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane_2)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
					.addGap(30)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVariables)
						.addComponent(lblPredicates))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_4, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addComponent(lblAxioms)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_5, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
					.addGap(15)
					.addComponent(lblTheorems)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_6, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(56, Short.MAX_VALUE))
		);

		scrollPane_6.setViewportView(theoremsArea);

		scrollPane_5.setViewportView(axiomsArea);

		scrollPane_4.setViewportView(predicatesArea);

		scrollPane_3.setViewportView(variablesArea);

		scrollPane_2.setViewportView(constantsArea);

		scrollPane.setViewportView(typesArea);

		panel_1.setLayout(gl_panel_1);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 438, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
					.addGap(12))
		);

		JScrollPane scrollPane_1 = new JScrollPane();

		JButton btnProve = new JButton("Prove");
		btnProve.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				prove();
			}
		});

		JLabel lblProof = new JLabel("Proof:");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(182)
							.addComponent(btnProve))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblProof))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(31)
					.addComponent(lblProof)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnProve)
					.addContainerGap(120, Short.MAX_VALUE))
		);

		scrollPane_1.setViewportView(proofsArea);

		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);

		frame.setVisible(true);
	}
	
	protected void setModel(Prover prover) {
		this.prover = prover;
	}

	/*
	 * Run when the "Prove" button is clicked.
	 */
	protected void prove() {
		prover.setTypes(typesArea.getText());
		prover.setConstants(constantsArea.getText());
		prover.setVariables(variablesArea.getText());
		prover.setPredicates(predicatesArea.getText());
		prover.setAxioms(axiomsArea.getText());
		prover.setTheorems(theoremsArea.getText());
		
	}

	public void setProof(String proof) {
		
	}
}