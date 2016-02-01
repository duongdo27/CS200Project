/**
 * File Location.java
 * @author Duong Do
 * version 12/2015
 */

public class Location{
	
	// variable name the city name
	private String name;
	
	// the city's hero 
	private String hero;
	
	// the hero's power
	private int power;

	private boolean mark = false;
	
	// variable whether it contains all artifact or not
	private boolean artifact = false;
	
	// Constructor
	public Location(String name){
		this.name = name;
	}
	
	/**
	 * method return the hero name
	 * @return hero's name
	 */
	public String getHero(){
		return hero;
	}
	
	/**
	 * method return the hero power
	 * @return hero's power
	 */
	public int getPower(){
		return power;
	}
	
	/**
	 * method set the hero name
	 * @param hero
	 */
	public void setHero(String hero){
		this.hero = hero;
	}
	
	/**
	 * method set the hero power
	 * @param power
	 */
	public void setPower(int power){
		this.power = power;
	}
	
	/**
	 * method return the city name
	 * @return city's name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Method toString
	 */
	public String toString(){
		return name;
	}
	
	/**
	 * mark the city
	 */
	public void mark(){
		mark = true;
	}
	
	/**
	 * unmark all the city
	 */
	public void unmark(){
		mark = false;
	}
	
	/**
	 * method check the city is mark or not
	 * @return true if marked, false otherwise.
	 */
	public boolean isMarked(){
		return mark;
	}
	
	/**
	 * method set artifact to true
	 */
	public void recoverArtifact(){
		artifact = true;
	}
	
	public void removeArtifact(){
		artifact = false;
	}
	
	/**
	 * Method to check the artifact
	 * @return true if has artifact, false otherwise
	 */
	public boolean getArtifact(){
		return artifact;
	}
}

