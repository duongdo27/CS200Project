import java.util.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class MainFrame extends JFrame{
	private JTextArea infoLeft, infoRight, locationInfo, logText, normalText, bossText, logText2, creditText;
	private JComboBox travelBox;
	private JButton travelButton, fightButton, searchButton, bossButton, saveButton, loadButton, resetButton;
	private JTextField searchText;
	private JFileChooser fileChooser;

	private Game game;
	private HashMap<String, ImageIcon> iconMap;
	private HashMap<String, JLabel> artifactMap;
	private ArrayList<Location> locationList;

	public MainFrame() throws FileNotFoundException, IOException{
		// main frame
		super("Final Project - Hearthstone Lite");

		// game
		game = new Game();

		// listener
		ActionListener listener = new GameListener(); 

		// tabbed pane
		JTabbedPane tabbedPane = new JTabbedPane();
		this.add(tabbedPane);

		// 1. main tab
		JPanel mainTab = new JPanel(new GridLayout(0, 1));
		tabbedPane.addTab("Play", mainTab);

		// 1.1. Info section
		JPanel infoSection = new JPanel(new GridLayout(0, 2));
		mainTab.add(infoSection);

		// 1.1.1. info section border
		TitledBorder infoBorder = BorderFactory.createTitledBorder("Info");
		infoBorder.setTitleJustification(TitledBorder.CENTER);
		infoSection.setBorder(infoBorder);

		// 1.1.2. info section left
		infoLeft = new JTextArea();
		infoLeft.setEditable(false);
		infoSection.add(infoLeft);

		// 1.1.3. info section right
		infoRight = new JTextArea();
		infoRight.setEditable(false);
		infoSection.add(infoRight);

		// 1.2 log section
		JPanel logSection = new JPanel(new GridLayout(0, 1));
		mainTab.add(logSection);

		// 1.2.1 log section border
		TitledBorder logBorder = BorderFactory.createTitledBorder("Log");
		logBorder.setTitleJustification(TitledBorder.CENTER);
		logSection.setBorder(logBorder);

		// 1.2.2 log text
		logText = new JTextArea("Welcome to Hearthstone Lite");
		logText.setEditable(false);
		logSection.add(logText);

		// 1.3. fight section
		JPanel fightSection = new JPanel(new GridLayout(0, 4));
		mainTab.add(fightSection);

		// 1.3.1. fight section border
		TitledBorder fightBorder = BorderFactory.createTitledBorder("Fight");
		fightBorder.setTitleJustification(TitledBorder.CENTER);
		fightSection.setBorder(fightBorder);

		// 1.3.2. current location info
		locationInfo = new JTextArea();
		locationInfo.setEditable(false);
		fightSection.add(locationInfo);

		// 1.3.3. fight button
		fightButton = new JButton("Fight");
		fightButton.addActionListener(listener);
		fightSection.add(fightButton);

		// 1.3.4. travel option
		travelBox = new JComboBox();
		fightSection.add(travelBox);

		// 1.3.5. travel button
		travelButton = new JButton("Travel");
		travelButton.addActionListener(listener);
		fightSection.add(travelButton);

		// 1.4 search Section
		JPanel searchSection = new JPanel(new GridLayout(0, 2));
		mainTab.add(searchSection);

		// 1.4.1. search section border
		TitledBorder searchBorder = BorderFactory.createTitledBorder("Search");
		searchBorder.setTitleJustification(TitledBorder.CENTER);
		searchSection.setBorder(searchBorder);

		// 1.4.2. search text
		searchText = new JTextField();
		searchSection.add(searchText);

		// 1.4.3 search button
		searchButton = new JButton("Search");
		searchButton.addActionListener(listener);
		searchSection.add(searchButton);

		// 1.3. boss section
		JPanel bossSection = new JPanel(new GridLayout(0, 2));
		mainTab.add(bossSection);

		// 1.3.1. boss section border
		TitledBorder bossBorder = BorderFactory.createTitledBorder("Boss");
		bossBorder.setTitleJustification(TitledBorder.CENTER);
		bossSection.setBorder(bossBorder);

		// 1.3.2. boss info
		JTextArea bossInfo = new JTextArea(game.getBossInfo());
		bossInfo.setEditable(false);
		bossSection.add(bossInfo);

		// 1.3.3. boss button
		bossButton = new JButton("Boss");
		bossButton.addActionListener(listener);
		bossSection.add(bossButton);

		// 2. help tab
		JPanel helpTab = new JPanel(new GridLayout(0, 1));
		tabbedPane.addTab("Help", helpTab);

		// 2.1. story section
		JPanel storySection = new JPanel(new BorderLayout());
		helpTab.add(storySection);

		// 2.1.1 story section border
		TitledBorder storyBorder = BorderFactory.createTitledBorder("Story");
		storyBorder.setTitleJustification(TitledBorder.CENTER);
		storySection.setBorder(storyBorder);

		// 2.1.2 story text
		JTextArea storyText = new JTextArea(game.story());
		storyText.setLineWrap(true);
		storySection.add(storyText);

		// 2.2 game play section
		JPanel gameplaySection = new JPanel(new BorderLayout());
		helpTab.add(gameplaySection);

		// 2.1.1 story section border
		TitledBorder gameplayBorder = BorderFactory.createTitledBorder("Gameplay");
		gameplayBorder.setTitleJustification(TitledBorder.CENTER);
		gameplaySection.setBorder(gameplayBorder);

		// 2.1.2 story text
		JTextArea gameplayText = new JTextArea(game.gameplay());
		gameplayText.setLineWrap(true);
		gameplaySection.add(gameplayText);

		// 3. Summary tab
		JPanel summaryTab = new JPanel(new GridLayout(0, 1));
		tabbedPane.addTab("Summary", summaryTab);

		// 3.1. stats section
		JPanel statsSection = new JPanel(new GridLayout(0, 2));
		summaryTab.add(statsSection);

		// 3.1.1 stats section border
		TitledBorder statsBorder = BorderFactory.createTitledBorder("Statistics");
		statsBorder.setTitleJustification(TitledBorder.CENTER);
		statsSection.setBorder(statsBorder);

		// 3.1.2 normal text
		normalText = new JTextArea();
		statsSection.add(normalText);

		// 3.1.3 boss text
		bossText = new JTextArea();
		statsSection.add(bossText);

		// 3.2 artifact section
		JPanel artifactSection = new JPanel(new GridLayout(3, 3));
		summaryTab.add(artifactSection);

		// 3.2.1 artifact section border
		TitledBorder artifactBorder = BorderFactory.createTitledBorder("Artifacts");
		artifactBorder.setTitleJustification(TitledBorder.CENTER);
		artifactSection.setBorder(artifactBorder);

		// 3.2.2 artifact map
		locationList = game.getLocations();
		artifactMap = new HashMap<String, JLabel>();
		for(Location loc : locationList){
			JLabel label = new JLabel(loc.getHero());
			artifactMap.put(loc.getHero(), label);
			artifactSection.add(label);
		}

		// icon map
		iconMap = new HashMap<String, ImageIcon>();
		iconMap.put("Unknown", new ImageIcon("data/Unknown.png"));
		for(Location loc : locationList){
			ImageIcon icon = new ImageIcon("data/" + loc.getHero() + ".png");
			iconMap.put(loc.getHero(), icon);
		}

		// 4. setting tab
		JPanel settingTab = new JPanel(new GridLayout(0, 1));
		tabbedPane.addTab("Setting", settingTab);

		// 4.1. log section 
		JPanel logSection2 = new JPanel(new GridLayout(0, 1));
		settingTab.add(logSection2);

		// 4.1.1 log section border
		TitledBorder logBorder2 = BorderFactory.createTitledBorder("Log");
		logBorder2.setTitleJustification(TitledBorder.CENTER);
		logSection2.setBorder(logBorder2);

		// 4.1.2 log text
		logText2 = new JTextArea("You can save, load or reset game here");
		logText2.setEditable(false);
		logSection2.add(logText2);

		// 4.2. option section
		JPanel optionSection = new JPanel(new GridLayout(0, 1));
		settingTab.add(optionSection);

		// 4.2.1 option section border
		TitledBorder optionBorder = BorderFactory.createTitledBorder("Option");
		optionBorder.setTitleJustification(TitledBorder.CENTER);
		optionSection.setBorder(optionBorder);

		// 4.2.2 save button
		saveButton = new JButton("Save game");
		saveButton.addActionListener(listener);
		optionSection.add(saveButton);

		// 4.2.3 load button
		loadButton = new JButton("Load game");
		loadButton.addActionListener(listener);
		optionSection.add(loadButton);

		// 4.2.4 reset button
		resetButton = new JButton("Reset game");
		resetButton.addActionListener(listener);
		optionSection.add(resetButton);

		// 4.3. credit section 
		JPanel creditSection = new JPanel(new GridLayout(0, 1));
		settingTab.add(creditSection);
		
		// 4.3.1 credit text
		creditText = new JTextArea();
		creditText.setEditable(false);
		String text = "© Duong Do 2015\n";
		text += "Final project CSCI 200\n";
		text += "Unauthorised reproduction prohibited";
		creditText.setText(text);
		creditSection.add(creditText);
		
		// file chooser
		fileChooser = new JFileChooser();

		update();
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * update the game
	 */
	public void update(){
		infoLeft.setText(game.getInfoLeft());
		infoRight.setText(game.getInfoRight());
		locationInfo.setText(game.getLocationInfo());
		normalText.setText(game.normalStats());
		bossText.setText(game.bossStats());
		populateTravelBox();
		populateArtifact();
	}
	
	/**
	 * poplulate the travel box
	 */
	public void populateTravelBox(){
		travelBox.removeAllItems();
		Iterator<String> it = game.getCurrentConnections();
		travelBox.addItem("--");
		while(it.hasNext()){
			travelBox.addItem(it.next());
		}
	}
	
	/**
	 * populate artifact
	 */
	public void populateArtifact(){
		for(Location loc : locationList){
			JLabel label = artifactMap.get(loc.getHero());
			if (loc.getArtifact()){
				label.setIcon(iconMap.get(loc.getHero()));
			}
			else{
				label.setIcon(iconMap.get("Unknown"));
			}
		}
	}
	
	/**
	 * Inner class GameListener
	 */
	private class GameListener implements ActionListener{
		public void actionPerformed( ActionEvent event ){
			Object object = event.getSource();
			if(object == fightButton){
				String temp = game.fight();	
				logText.setText(temp);
			}
			else if(object == travelButton){
				String newLocation = (String) travelBox.getSelectedItem();
				String temp = game.travel(newLocation);	
				logText.setText(temp);
			}
			else if(object == searchButton){
				String text = searchText.getText();
				String temp = game.search(text);	
				logText.setText(temp);
			}
			else if(object == bossButton){
				String temp = game.boss();	
				logText.setText(temp);
			}
			else if(object == saveButton){
				int value = fileChooser.showSaveDialog(MainFrame.this);
				if (value == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					try {
						String temp = game.save(file);
						logText2.setText(temp);
					} catch (Exception e) {
						logText2.setText("Unexpected Error");
					}
				}
			}
			else if(object == loadButton){
				int value2 = fileChooser.showOpenDialog(MainFrame.this);
				if (value2 == JFileChooser.APPROVE_OPTION){
					File file2 = fileChooser.getSelectedFile();
					try {
						String temp = game.load(file2);
						logText2.setText(temp);
					} catch (Exception e) {
						logText2.setText("Unexpected Error");
					}
				}
			}
			else if(object == resetButton){
				int value3 = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure to reset?");
				if(value3 == JOptionPane.OK_OPTION){
					try {
						String temp = game.reset();
						logText2.setText(temp);
					} catch (Exception e) {
						logText2.setText("Unexpected error");
					}	
				}
			}
			update();
		}
	}
	
	public static void main(String args[]) throws FileNotFoundException, IOException {
		new MainFrame();
	}
}
