package archon;

import javax.swing.JFrame;

public class Runner {
	JFrame sym;

	// static final int width =
	// java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	// static final int height =
	// java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	static final int width = 1920;
	static final int height = 1044;
	Panel panelope;

	public Runner() {
		sym = new JFrame();
		panelope = new Panel();
		run();
	}

	public static void main(String[] args) {
		Runner archonite = new Runner();
	}

	void run() {
		sym.add(panelope);
		sym.setTitle("Ready Player 1");
		sym.addKeyListener(panelope);
		sym.setSize(width, height);
		sym.setVisible(true);
		sym.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panelope.startGame(true);
		// System.out.println("runner");
	}
}