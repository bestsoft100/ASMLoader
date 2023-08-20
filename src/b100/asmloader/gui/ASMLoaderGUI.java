package b100.asmloader.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import b100.asmloader.gui.style.Style;
import b100.asmloader.gui.utils.GridPanel;

public class ASMLoaderGUI implements ActionListener {
	
	private static List<Style> styles;
	private static Style currentStyle;
	
	public JFrame frame;
	public GridPanel panel;
	public JTabbedPane tabbedPane;
	
	public ModExporterGUI modExporterGUI;
	public LogGUI logGUI;
	
	public JMenuBar menuBar;
	public JMenu menuStyle = new JMenu("Style");
	
	private Map<Component, Style> componentToStyleMap = new HashMap<>();
	
	public ASMLoaderGUI() {
		frame = new JFrame("ASMLoader GUI");
		frame.setMinimumSize(new Dimension(400, 400));
		
		panel = new GridPanel();
		
		modExporterGUI = new ModExporterGUI(this);
		logGUI = new LogGUI();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Export", modExporterGUI);
		tabbedPane.addTab("Log", logGUI);
		tabbedPane.setPreferredSize(new Dimension(480, 520));
		
		createMenuBar();
		
		panel.add(menuBar, 0, 0, 1, 0);
		panel.add(tabbedPane, 0, 1, 1, 1);
		
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void createMenuBar() {
		menuBar = new JMenuBar();
		
		menuStyle = new JMenu("Style");
		
		if(styles != null) {
			ButtonGroup buttonGroup = new ButtonGroup();
			for(int i=0; i < styles.size(); i++) {
				Style style = styles.get(i);
				
				JRadioButtonMenuItem component = new JRadioButtonMenuItem(style.getName(), style == currentStyle);
				component.addActionListener(this);
				buttonGroup.add(component);
				componentToStyleMap.put(component, style);
				menuStyle.add(component);
			}
		}else {
			menuStyle.setEnabled(false);
		}
		
		menuBar.add(menuStyle);
	}
	
	public void showLog() {
		tabbedPane.setSelectedIndex(1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Style style = componentToStyleMap.get(e.getSource());
		if(style != null) {
			setStyle(style);
			frame.setVisible(false);
			frame.dispose();
			new ASMLoaderGUI();
			return;
		}
	}
	
	private void setStyle(Style style) {
		try {
			UIManager.setLookAndFeel(style.getClassName());
			currentStyle = style;
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void initStyles() {
		styles = new ArrayList<>();
		styles.add(new Style("Default", UIManager.getCrossPlatformLookAndFeelClassName()));
		styles.add(new Style("System Default", UIManager.getSystemLookAndFeelClassName()));

		LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
		for(int i=0; i < installedLookAndFeels.length; i++) {
			LookAndFeelInfo lookAndFeelInfo = installedLookAndFeels[i];
			
			styles.add(new Style(lookAndFeelInfo.getName(), lookAndFeelInfo.getClassName()));
		}
		
		String selectedLookAndFeelClassName = UIManager.getLookAndFeel().getClass().getName();
		for(int i=0; i < styles.size(); i++) {
			if(styles.get(i).getClassName().equals(selectedLookAndFeelClassName)) {
				currentStyle = styles.get(i);
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		initStyles();
		
		new ASMLoaderGUI();
	}

}
