/**
 * File Game.java
 * @author Duong Do
 */

import java.util.*;
import java.io.*;

import javax.swing.JFileChooser;


public class Game{
	private HashMap <String, Set<String>> connections;
	private HashMap <String, Location> locations;
	private Location currentLocation;
	private int experience = 0;
	private Random generator;
	private int travelTickets = 0;
	private int goldenTickets = 0;
	private final int bossPower = 500;
	private int totalFight = 0;
	private int totalWin = 0;
	private int bossFight = 0;
	private int bossWin = 0;
	private static final String locationFile = "data/locations.dat";
	private static final String connectionFile = "data/connections.dat";
	
	/**
	 * Constructor
	 * @throws FileNotFoundException
	 */
	public Game() throws FileNotFoundException{  
		readLocationFile();
		readConnectionFile();		
		generator = new Random();
	}
	
	/**
	 * Method read the location file
	 * @throws FileNotFoundException
	 */
	public void readLocationFile() throws FileNotFoundException{
		locations = new HashMap<String, Location>();
		int index = 0;
		Location loc = null;
		Scanner sc = new Scanner(new File(locationFile));
		while(sc.hasNextLine()){
			if(index == 0){
				loc = new Location(sc.nextLine());
				if(currentLocation == null){
					currentLocation = loc;
				}    
			}
			else if(index == 1){
				loc.setHero(sc.nextLine());
			}
			else if(index == 2){
				loc.setPower(Integer.parseInt(sc.nextLine()));
			}
			else{
				if(loc!=null){
					locations.put(loc.getName(), loc);
				}
				index = -1;
				sc.nextLine();
			}
			index ++;
		}
		sc.close();
	}

	/**
	 * Method read the connection file
	 * @throws FileNotFoundException
	 */
	public void readConnectionFile() throws FileNotFoundException{
		connections = new HashMap<String, Set<String>>(); 
		Scanner sc = new Scanner(new File(connectionFile));
		int index = 0;
		String label = "";
		HashSet<String> neighbors = new HashSet<String>();
		String line = "";
		while(sc.hasNextLine()){
			line = sc.nextLine();
			if(line.equals("_")){
				connections.put(label, (Set) neighbors.clone());
				neighbors.clear();
				index = -1;
			}
			else if(index == 0){
				label = line;
			}
			else{
				neighbors.add(line);
			}
			index ++;
		}
		sc.close();
	}
	
	/**
	 * Method get your current level
	 * @return your current level
	 */
	public int getLevel(){
		return experience/100+1;
	}
	
	/**
	 * Method get your current power
	 * @return your current level
	 */
	public int getPower(){
		return 3 + getLevel();
	}
	
	/**
	 * Method get info for left tab
	 * @return info
	 */
	public String getInfoLeft(){
		String info = "Level: " + getLevel() + " (" + experience + " EXP)\n";
		info += "Power: " + getPower() + "\n";
		info += "Bonus: " + getBonusPower(); 
		return info;
	}
	
	/**
	 * Method get info for right tab
	 * @return info
	 */
	public String getInfoRight(){
		String info = "Travel Tickets: " + travelTickets + "\n";
		info += "Golden Tickets: " + goldenTickets + "\n";
		info += "Artifacts: " + getTotalArtifacts() + "/9";
		return info;
	}
	
	/**
	 * Method get info for the current level 
	 * @return info
	 */
	public String getLocationInfo(){
		String info = "Location: " + currentLocation.getName() + "\n";
		info += "Hero: " + currentLocation.getHero() +"\n";
		info += "Power: " + currentLocation.getPower();
		return info;
	}
	
	/**
	 * Method get the number of artifacts
	 * @return number of artifact
	 */
	public int getTotalArtifacts(){
		int count = 0;
		for(String label : locations.keySet()){
			Location loc = locations.get(label);
			if (loc.getArtifact()){
				count ++;
			}
		}
		return count;
	}
	
	/**
	 * Method get your bonus power
	 * @return your bonus power
	 */
	public int getBonusPower(){
		return 2 * getTotalArtifacts();
	}
	
	/**
	 * Method get all the cities
	 * @return list of all cities
	 */
	public ArrayList<Location> getLocations(){
		return new ArrayList<Location>(locations.values());
	}
	
	/**
	 * Method return your stats
	 * @return your stats
	 */
	public String normalStats(){
		String stat = "Total fight: " + totalFight + "\n";
		stat += "Total win: " + totalWin + "\n";
		String percent;
		if (totalFight == 0){
			percent = "N/A";
		}
		else{
			percent = totalWin*100/totalFight + "%";
		}
		stat += "Win percentage: " + percent;
		return stat;
	}
	
	/**
	 * Method return your stats when encountering hero
	 * @return your stats
	 */
	public String bossStats(){
		String stat = "Total fight: " + bossFight + "\n";
		stat += "Total win: " + bossWin + "\n";
		String percent;
		if (bossFight == 0){
			percent = "N/A";
		}
		else{
			percent = bossWin*100/bossFight + "%";
		}
		stat += "Win percentage: " + percent;
		return stat;
	}
	
	/**
	 * Method to start fight
	 * @return String demonstrate your result
	 */
	public String fight(){
		totalFight ++;
		int userPower = getPower() + getBonusPower();
		int heroPower = currentLocation.getPower();
		int dice = generator.nextInt(userPower+heroPower);
		String result;
		if(dice <= userPower){
			totalWin ++;
			experience += 10;
			result = "You defeat " + currentLocation.getHero() + " and earn 10 EXP.\n";
			dice = generator.nextInt(5);
			if(dice == 0){
				travelTickets+= 1;
				result += "You earn a Travel Ticket.\n";
			}
			dice = generator.nextInt(20);
			if(dice == 0){
				goldenTickets+= 1;
				result += "Congratulations. You earn a Golden Ticket.\n";
			}
			if(!currentLocation.getArtifact()){
				dice = generator.nextInt(30);
				if(dice == 0){
					currentLocation.recoverArtifact();;
					result += "Congratulations. You recover the " +currentLocation.getHero() + " Artifact.\n";
				}
			}
		}
		else{
			result = "You lose to " + currentLocation.getHero() + ".\n" ;
		}
		return result;
	}
	
	/**
	 * Method get the boss info
	 * @return boss'information
	 */
	public String getBossInfo(){
		String info = "Boss: Deathwing \n";
		info += "Power: " + bossPower;
		return info;
	}
	
	/**
	 * Method to start fight the Boss
	 * @return String demonstrate your result
	 */
	public String boss(){
		if(getTotalArtifacts() < 9){
			return "You need all artifacts to challenge Deathwing";
		}
		if(goldenTickets == 0){
			return "Do not have any Golden ticket.";
		}
		goldenTickets --;
		bossFight ++;
		int userPower = getPower() + getBonusPower();
		int dice = generator.nextInt(userPower+bossPower);
		if(dice <= userPower){
			experience += 100;
			bossWin ++;
			return "You defeat Deathwing. Congratulations.\n";
		}
		else{
			return"You lose against Deathwing. Good luck.\n";
		}
	}
	
	/**
	 * Method get connections for current location
	 * @return the iterator of connections
	 */
	public Iterator<String> getCurrentConnections(){
		Set<String> neighbors = connections.get(currentLocation.getName());
		return neighbors.iterator();
	}
	
	/**
	 * Method check for legal destination
	 * @param newLocation
	 * @return true if able to travel, false otherwise
	 */
	public boolean checkValidMove(String newLocation){
		if (!locations.containsKey(newLocation)){
			System.out.println("WARNING. Something wrong with the connections file");
			return false;
		}
		Iterator<String> it = getCurrentConnections();
		while(it.hasNext()){
			if(newLocation.equals(it.next())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to travel
	 * @param newLocation
	 * @return String demonstrate you can travel or not
	 */
	public String travel(String newLocation){
		if(travelTickets == 0){
			return "Do not have any travel ticket.";
		}
		if (checkValidMove(newLocation)){
			currentLocation = locations.get(newLocation);
			travelTickets -= 1;
			return "Move to " + newLocation;	
		}
		else{
			return "Invalid location. Try different name.";
		}
	}
	
	/**
	 * Method unmark all your cities
	 */
	private void unmarkAll(){
		for(String label : locations.keySet()){
			Location loc = locations.get(label);
			loc.unmark();
		}
	}
	
	/**
	 * Search for the desired hero
	 * @param text
	 * @return string demonstrate you travel or not
	 */
	public String search(String text){
		LinkedList<Location> list = new LinkedList<Location>();
		list.add(currentLocation);
		currentLocation.mark();

		while(!list.isEmpty()){
			Location loc = list.remove();
			if(loc.getHero().equals(text)){
				unmarkAll();
				return text + " can be found in " + loc.getName();
			}

			Set<String> neighbors = connections.get(loc.getName());
			if(neighbors==null){
				break;
			} 
			Iterator<String> it = neighbors.iterator();

			while(it.hasNext()){
				Location neighbor = locations.get(it.next());
				if(neighbor == null){
					break;
				}
				if(!neighbor.isMarked()){
					neighbor.mark();
					list.add(neighbor);
				}    
			}
		}
		unmarkAll();
		return "Not found. Please try different name";
	}
	
	/**
	 * The game story
	 * @return the game story
	 */
	public String story(){
		String story = "Deathwing has stolen the 9 artifacts of our world and scatter them around 9 different cities. ";
		story += "Each city is guarded by a corrupted hero (Druid, Hunter, Mage, Palladin, Rogue, Priest, Shaman, Warlock, and Warrior). ";
		story += "Your quest is to travel to each city, defeat each corrupted hero and recover all the artifacts. ";
		story += "Only then, you are able to face Deathwing himself and defeat him once and for all.";
		return story;
	}
	
	/**
	 * How to play
	 * @return the tutorial
	 */
	public String gameplay(){
		String gameplay = "1. Click Fight button to fight against the hero in the current area. ";
		gameplay += "You get 10 EXP for winning. You may also get a Travel Ticket or a Golden Ticket or Atifacts. \n";
		gameplay += "2. You can travel to different cities by using Travel button. Each travel costs 1 Travel Ticket. \n";
		gameplay += "3. After recovering all artifacts, you can challenge Deathwing by clicking the Boss button. ";
		gameplay += "Each boss hunt cost 1 Golden Ticket. \n";
		gameplay += "4. Your power depends on your current level and number of collected artifacts.";
		return gameplay;
	}
	
	/**
	 * save the file
	 * @param file
	 * @return string that you save file or not
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String save(File file) throws FileNotFoundException, IOException {
		PrintWriter writer = new PrintWriter(file);
		writer.println(currentLocation);
		writer.println(experience);
		writer.println(travelTickets);
		writer.println(goldenTickets);
		writer.println(totalFight);
		writer.println(totalWin);
		writer.println(bossFight);
		writer.println(bossWin);
		for(Location loc : getLocations()){
			if (loc.getArtifact()){
				writer.println(1);
			}
			else{
				writer.println(0);
			}
		}
		writer.close();
		return "Save successful.";
	}
	
	/**
	 * load the existing
	 * @param file
	 * @return string show that you can travel or not
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String load(File file) throws FileNotFoundException, IOException {
		Scanner sc = new Scanner(file);
		try {
			currentLocation = locations.get(sc.nextLine());
			if(currentLocation == null){
				return "Corrupted file";
			}
			experience = Integer.parseInt(sc.nextLine());
			travelTickets = Integer.parseInt(sc.nextLine());
			goldenTickets = Integer.parseInt(sc.nextLine());
			totalFight = Integer.parseInt(sc.nextLine());
			totalWin = Integer.parseInt(sc.nextLine());
			bossFight = Integer.parseInt(sc.nextLine());
			bossWin = Integer.parseInt(sc.nextLine());
			int temp;
			for(Location loc : getLocations()){
				temp = Integer.parseInt(sc.nextLine());
				if (temp == 1){
					loc.recoverArtifact();
				}
				else{
					loc.removeArtifact();
				}
			}
			sc.close();
			return "Load successful";
		}
		catch (Exception e){
			sc.close();
			return "Corrupted file";
		}
	}
	
	/**
	 * reset your game
	 * @return the String that you can travel or not
	 * @throws FileNotFoundException
	 */
	public String reset() throws FileNotFoundException{
		readLocationFile();
		readConnectionFile();
		experience = 0;
		travelTickets = 0;
		goldenTickets = 0;
		totalFight = 0;
		totalWin = 0;
		bossFight = 0;
		bossWin = 0; 
		return "Game reset.";
	}
}

