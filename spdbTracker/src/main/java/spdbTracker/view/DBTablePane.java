package spdbTracker.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class DBTablePane extends JPanel {

	/**
	 * Create the panel.
	 */
	public DBTablePane() {
		setLayout(null);

		JLabel lblNewLabel = new JLabel("Test");
		lblNewLabel.setBounds(12, 20, 271, 15);
		add(lblNewLabel);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(341, 20, 259, 14);
		add(progressBar);

	}
}
