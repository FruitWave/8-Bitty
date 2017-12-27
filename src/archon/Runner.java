package archon;

import javax.swing.JFrame;

public class Runner {
	JFrame sym;

	static final int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	static final int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

	GamePanel panelope;

	public Runner() {
		sym = new JFrame();
		panelope = new GamePanel();
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
		panelope.startGame();
		// System.out.println("runner");
	}
}