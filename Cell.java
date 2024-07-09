/**************************************

Sotirios Rafail Meletiou

AM941797

***************************************/
/***************************************  
* Author:     Sotirios Rafail Meletiou 
* Written:       25/11/2020
* Lastupdated:  7/12/2020

*****************************************/
public class Cell {

  private boolean hasNest;
  private int foodAmount;
  private int scentAmount;
  private int[] times;
  private int[] antIds;

  public Cell(){
    hasNest = false;
    foodAmount = 0;
    scentAmount = 0;
    times = null;
    antIds = null;
  }
  
      
  public String toString(){
   String s = "\tCell that ";
   if (hasNest) s += "has a nest.\n"; else s += "does not have a nest.\n";
   if (hasNest && foodAmount > 0) s += "\t- The nest has " + foodAmount + " stored seeds.\n";
   if (!hasNest && foodAmount > 0) s += "\t- The cell has " + foodAmount + " seeds for collection.\n";
   if (foodAmount == 0) s += "\t- There are no seeds.\n"; 
   if (scentAmount > 0) s += "\t- Currently it is annotated with " + scentAmount + " units of scent.\n";
   if (antIds == null) s += "\t- At present there are no ants on it.\n";
   else s+= "\t- At present there are " + antIds.length + " ants on it.\n";

   return s;
  }

  public boolean hasFood(){//checks if theres any food
	  return foodAmount > 0;	  
  }
   
  public void getFood() {//gets food from the cell
	  --foodAmount;	  
  }
  
  public void putFood() {//adds food
	  ++foodAmount;
  }
  
  public void putFood(int food){////adds food in the nests 
	  foodAmount += food;
  }
  
  public boolean hasAnts(){//checks if a cell has any ants on it
	  return antIds != null;
  }
   
  public void putNest() { //registers the cell as a nest
	  hasNest = true;
  }
    
  public boolean withNest(){ //checks if theres a nest in the cell
	  return hasNest;	  
  }
  
  public int getScent() {//returns the scent Amount 
	  return scentAmount;
  }
   
  public void addAnt(int antID){
	  if (!hasAnts())//if the cell doesnt have ants
	  {
		  antIds = new int[1];//creates a list with ant ids
		  antIds[0] = antID;//and adds in the list the id of the ant
	  }
	  else
	  {
		  int[] temp = antIds;//if there are more ants in the cell saves the list
		  
		  antIds = new int[temp.length + 1];//and creates a new one with one extra slot
		  
		  for (int i = 0; i < temp.length; i++)//puts all the previous ants in the nes list
			  antIds[i] = temp[i];
		  
		  antIds[temp.length] = antID;//adds on the last slot the new ant in the cell 
	  }	  
  }
  
  public void removeAnt(int antID){
	  if (antIds.length == 1) //if in the list of the ants in the cell are only one
		  antIds = null;//makes the list empty
	  else 
	  {
		  int[] temp = antIds;//if its not empty save the list 
		  
		  antIds = new int[temp.length - 1];//and makes a new one with one slot less
		  
		  int j = 0;
		  
		  for (int i = 0; i < temp.length; i++)
		  
			  if (temp[i] != antID)//checks if the ant ids is different than the one we want to remove
			  {
				  antIds[j] = temp[i];//puts the rest ants expect the one we want to remove back to the list 
				  j++;
			  }			    		 
	  }
  }
 
  public void addScent(int time){
	  ++scentAmount;//adds one amount of scent
	  
	  if (times == null)//if there are no scent in this cell
	  {
		  times = new int[1];//creates a table with the current time and the current amount of time 
		  times[0] = time;
	  }
	  else
	  {
		  int[] temp = times;//if there are more scents in this cell saves the previous table
		  
		  times = new int[temp.length + 1]; //and creates a new one one slot longer 
		  
		  for (int i = 0; i < temp.length; i++)
			  times[i] = temp[i];
		  
		  times[temp.length] = time;//adds in the last slot the new scent amount
	  }	  
  }
  
  void updateScent(int curTime, int elapsed){
	  if (times != null)//if there are scents in this cell
	  
		  for (int i = 0; i < times.length; i++)	
		  
			  if (curTime - times[i] > elapsed)//and the current time minus the scente of all the time lines are more than the elapsed time
				  --scentAmount;//takes one scent unit out
			  
		  
		  if (scentAmount == 0)//if the scent amount equals to 0
			  times = null;//emptys the table of time lines 
		  else
		  {			 
			  int[] temp = times;//if the are scents in the cell
			  times = new int[scentAmount];//creates a table with the amount on scent in for each time line 
			  int j = 0;
			  for (int i = 0; i < temp.length; i++)
			  
				  if (curTime - temp[i] <= elapsed)
				  {
					  times[j] = temp[i];
					  j++;
				  }				 		  			  			  
		  }
  }
}