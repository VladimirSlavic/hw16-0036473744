package hr.fer.zemris.java.hw16.jvdraw.JVDraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw.components.BottomBar;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw.components.Bounds;
import hr.fer.zemris.java.hw16.jvdraw.JVDraw.components.JColorArea;

/**
 * JFrame and starting point of the application
 * 
 * @author vladimir
 *
 */
public class JVDraw extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * colorarea for adding foreground color
	 */
	private JColorArea fgArea;
	/**
	 * colorarea for adding background color
	 */
	private JColorArea bgArea;
	/**
	 * canvas
	 */
	private JDrawingCanvas canvas;
	/**
	 * datamodel
	 */
	private DrawingModel model;
	/**
	 * current active file
	 */
	private Path currentFile;

	/**
	 * public constructor
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(50, 50);
		setSize(900, 900);

		initGui();
		createCanvas();
		addSideList();
		createToolbars();
		createActions();
		createMenus();
	}

	/**
	 * Method for initializing the UI
	 */
	private void initGui() {
		getContentPane().setLayout(new BorderLayout());
		setTitle("JVDraw");
	}

	/**
	 * Method for creating all actions available to user in application
	 */
	private void createActions() {

		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveAction.putValue(Action.NAME, "Save");
		saveAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		saveAsAction.putValue(Action.NAME, "Save as");
		saveAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		exportAction.putValue(Action.NAME, "Export");
		exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
		exportAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

		exitAction.putValue(Action.NAME, "Exit");

	}

	/**
	 * Method for adding the JList to the right for removing and updating
	 * objects.
	 */
	private void addSideList() {
		DrawingObjectListModel lmodel = new DrawingObjectListModel(model);
		model.addDrawingModelListener(lmodel);
		JList<GeometricalObject> list = new JList<>(lmodel);

		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					model.getObject(list.getSelectedIndex()).update();
					model.update(list.getSelectedIndex());
				}
			}

		});

		list.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					int[] indexes = list.getSelectedIndices();
					model.remove(indexes);
				}

			}
		});

		getContentPane().add(new JScrollPane(list), BorderLayout.EAST);

	}

	/**
	 * Method for creating all the menus in the application.
	 */
	private void createMenus() {
		JMenuBar menubar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.add(new JMenuItem(exportAction));
		fileMenu.add(new JMenuItem(exitAction));
		setJMenuBar(menubar);
	}

	/**
	 * Action for opening document.
	 */
	private Action openDocumentAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jvd", "jvd");
			fc.setFileFilter(filter);

			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();

			if (!Files.isReadable(filePath)) {

				JOptionPane.showMessageDialog(JVDraw.this, "File " + filePath + " se ne moze citati!", "Pogreska",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			byte[] data = null;

			try {

				data = Files.readAllBytes(filePath);

			} catch (Exception exc) {

				exc.printStackTrace();
				JOptionPane.showMessageDialog(JVDraw.this, "Error while parsing file.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			currentFile = filePath;

			String text = new String(data, StandardCharsets.UTF_8);
			String[] lInFile = text.split("\\r?\\n");
			model.clear();
			canvas.resetIds();
			for (String obj : lInFile) {
				parseAndDraw(obj);
			}
			canvas.setChanged(false);

		}
	};

	/**
	 * Action for saving current objects on the canvas
	 */
	private Action saveAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentFile != null) {
				if (!canvas.isChanged())
					return;

				try {
					String textToSave = getFileFormat();
					Files.write(currentFile, textToSave.getBytes(StandardCharsets.UTF_8));
					canvas.setChanged(false);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(JVDraw.this, "Error while saving file.", "Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
				return;
			}

			saveAsAction.actionPerformed(null);
		}
	};

	/**
	 * Method for retrieving all the objects on the current canvas in file ready
	 * representation for saving.
	 * 
	 * @return string of the objects
	 */
	public String getFileFormat() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < model.getSize(); i++) {

			sb.append(model.getObject(i).getFileString());

			if (i != (model.getSize() - 1)) {
				sb.append("\r\n");
			}
		}

		return sb.toString();
	}

	/**
	 * Action representing the save as operation
	 */
	private Action saveAsAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jvd", "jvd");
			fc.setFileFilter(filter);

			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {

				return;
			}

			try {

				String checkExt = fc.getSelectedFile().toPath().toString();
				File file = fc.getSelectedFile();
				int dot = checkExt.indexOf(".");

				if (dot < 0 || !checkExt.substring(dot + 1).equals("jvd")) {
					file = new File(file.toString() + ".jvd");
				}

				currentFile = file.toPath();

				String tFormat = getFileFormat();
				Files.write(currentFile, tFormat.getBytes(StandardCharsets.UTF_8));
				canvas.setChanged(false);
				showMessage("Successfully saved", "success");
			} catch (Exception e1) {
				showErrorMessage("Failed saving file", "error");
				e1.printStackTrace();
			}

		}
	};

	/**
	 * Action representing the exit action for leaving the application
	 */
	private Action exitAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			if (!canvas.isChanged()) {
				dispose();
				return;
			}

			Object[] options = { "Save changes", "Reject changes", "Remain in editor" };
			int n = JOptionPane.showOptionDialog(JVDraw.this, "Would you like to save changes prior to exiting?",
					"Pre-exit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[2]);



			if (n == 2) {
				return;
			}

			if (n == 1) {
				dispose();
				return;
			}

			if (n == 0) {
				saveAction.actionPerformed(null);
				dispose();
			}

		}
	};

	/**
	 * Action representing the exportation for generating an image based on the
	 * canvas
	 */
	private Action exportAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String type = getImageType();
			if (type == null) {
				showErrorMessage("Must select the type the file should be saved as", "ERROR");
				return;
			}

			Bounds bounds = model.getBounds();

			if (bounds == null) {
				showErrorMessage("Nothing to export, draw something!", "ERROR");
				return;
			}

			BufferedImage image = new BufferedImage(Math.abs(bounds.getxDown() - bounds.getxUp()),
					Math.abs(bounds.getyDown() - bounds.getyUp()), BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = image.createGraphics();
			g.setPaint(new Color(255, 255, 255));
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
			g.setColor(Color.WHITE);

			for (int i = 0; i < model.getSize(); i++) {
				model.getObject(i).drawShape(g, bounds.getxUp(), bounds.getyUp());
				// model.getObject(i).drawShape(g);
			}

			g.dispose();

			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("*." + type, type);
			fc.setFileFilter(filter);
			// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this, "Error while selecting file.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			String ext = fc.getSelectedFile().toString();
			File chosenFile = fc.getSelectedFile();

			if (!ext.endsWith(type)) {
				chosenFile = new File(ext + "." + type);
			}

			Path pathToImage = chosenFile.toPath();

			try {
				ImageIO.write(image, type, pathToImage.toFile());
			} catch (IOException e1) {
				e1.printStackTrace();
				showErrorMessage("Failed to save image.", "error");
			}

			showMessage("Image successfully saved!", "Success");
			// Tell-user-that-images-is-exported.
		}
	};

	/**
	 * Method for getting the image type that the user selects for generating
	 * the image of the canvas
	 * 
	 * @return image type extension
	 */
	public String getImageType() {
		JPanel panel = new JPanel();
		JToggleButton gifBut = new JToggleButton("gif");
		JToggleButton jpgBut = new JToggleButton("jpg");
		JToggleButton pngBut = new JToggleButton("png");

		ButtonGroup bgroup = new ButtonGroup();
		pngBut.setSelected(true);

		bgroup.add(pngBut);
		bgroup.add(jpgBut);
		bgroup.add(gifBut);

		panel.add(gifBut);
		panel.add(pngBut);
		panel.add(jpgBut);

		int status = JOptionPane.showConfirmDialog(null, panel);
		if (status == JOptionPane.OK_OPTION) {
			if (pngBut.isSelected()) {
				return "png";
			}

			if (jpgBut.isSelected()) {
				return "jpg";
			}

			return "gif";

		}

		return null;
	}

	/**
	 * Method for creating the canvas
	 */
	private void createCanvas() {
		model = new DrawingModelImpl();
		fgArea = new JColorArea(Color.RED);
		bgArea = new JColorArea(Color.BLUE);

		canvas = new JDrawingCanvas(fgArea, bgArea, model, DrawType.LINE);
		model.addDrawingModelListener(canvas);
		getContentPane().add(canvas, BorderLayout.CENTER);
	}

	/**
	 * Method for showing message in dialog
	 * 
	 * @param message
	 * @param messageType
	 */
	public void showMessage(String message, String messageType) {
		JOptionPane.showMessageDialog(JVDraw.this, message, messageType, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Method for showing an error message
	 * 
	 * @param message
	 * @param messageType
	 */
	public void showErrorMessage(String message, String messageType) {
		JOptionPane.showMessageDialog(JVDraw.this, message, messageType, JOptionPane.ERROR_MESSAGE);

	}

	/**
	 * Method for parsing entry from file and drawing to canvas
	 * 
	 * @param line
	 */
	protected void parseAndDraw(String line) {

		try {

			String[] split = line.split(" ");
			if (split[0].equals("LINE")) {
				if (split.length != 8)
					return;

				GeometricalObject gLine = new Line();
				gLine.setS(new Point(Integer.parseInt(split[1]), Integer.parseInt(split[2])));
				gLine.setF(new Point(Integer.parseInt(split[3]), Integer.parseInt(split[4])));
				gLine.setForegroundC(
						new Color(Integer.parseInt(split[5]), Integer.parseInt(split[6]), Integer.parseInt(split[7])));
				// model.clear();
				gLine.setId(canvas.getLineID());
				canvas.setLineID(canvas.getLineID() + 1);
				model.add(gLine);
				// canvas.resetIds();
				return;
			}

			if (split[0].equals("CIRCLE")) {

				// CIRCLE centerx centery radius red green blue
				if (split.length != 7)
					return;

				GeometricalObject gCircle = new Circle();
				gCircle.setS(new Point(Integer.parseInt(split[1]), Integer.parseInt(split[2])));
				// gCircle.setF(new Point(Integer.parseInt(split[3]),
				// Integer.parseInt(split[3])));
				gCircle.setRadius(Integer.parseInt(split[3]));
				gCircle.setForegroundC(
						new Color(Integer.parseInt(split[4]), Integer.parseInt(split[5]), Integer.parseInt(split[6])));
				// model.clear();
				gCircle.setId(canvas.getCircleID());
				canvas.setCircleID(canvas.getCircleID() + 1);
				model.add(gCircle);
				// canvas.resetIds();
				return;
			}

			if (split[0].equals("FCIRCLE")) {
				if (split.length != 10)
					return;

				// FCIRCLE centerx centery radius red green blue red green blue
				GeometricalObject gfCircle = new FilledCircle();
				gfCircle.setS(new Point(Integer.parseInt(split[1]), Integer.parseInt(split[2])));
				gfCircle.setRadius(Integer.parseInt(split[3]));
				gfCircle.setForegroundC(
						new Color(Integer.parseInt(split[4]), Integer.parseInt(split[5]), Integer.parseInt(split[6])));
				gfCircle.setBackgroundC(
						new Color(Integer.parseInt(split[7]), Integer.parseInt(split[8]), Integer.parseInt(split[9])));
				// model.clear();
				gfCircle.setId(canvas.getFcircleID());
				canvas.setFcircleID(canvas.getFcircleID() + 1);
				model.add(gfCircle);
				return;
			}

		} catch (Exception e) {

			JOptionPane.showMessageDialog(JVDraw.this, "Error while parsing file", "Error", JOptionPane.ERROR_MESSAGE);

		}

	}

	/**
	 * Method for creating the toolbar.
	 */
	private void createToolbars() {

		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));

		toolbar.add(fgArea);
		toolbar.add(bgArea);

		JToggleButton lines = new JToggleButton("Line");
		lines.setSelected(true);
		JToggleButton circles = new JToggleButton("Circle");
		JToggleButton fcircles = new JToggleButton("FCircle");

		lines.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setDrawType(DrawType.LINE);

			}
		});

		circles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setDrawType(DrawType.CIRCLE);

			}
		});

		fcircles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setDrawType(DrawType.FILLED_CIRCLE);

			}
		});

		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(lines);
		bGroup.add(circles);
		bGroup.add(fcircles);

		toolbar.add(lines);
		toolbar.add(circles);
		toolbar.add(fcircles);

		getContentPane().add(toolbar, BorderLayout.PAGE_START);

		BottomBar bar = new BottomBar(fgArea, bgArea);
		fgArea.addColorChangeListener(bar);
		bgArea.addColorChangeListener(bar);
		getContentPane().add(bar, BorderLayout.PAGE_END);

	}

	/**
	 * Main method for executing the program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
}
