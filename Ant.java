/**************************************

Sotirios Rafail Meletiou

AM941797

***************************************/
/***************************************  
* Author:     Sotirios Rafail Meletiou 
* Written:       25/11/2020
* Lastupdated:  7/12/2020

*****************************************/
public class Ant {

  private CellGrid terain;
  private int id;
  private int[] pos;
  private int previousX;
  private int previousY;
  private int[] nestPos;
  private boolean carriesFood;
  private String color;

  public Ant(CellGrid grid, int Id, String col, int[] NestPos){
    terain = grid;
    id = Id;
    color = new String(col);
    pos = new int[2];
    nestPos = new int[2];
    nestPos[0] = NestPos[0];
    nestPos[1] = NestPos[1];
    pos[0] = nestPos[0]; pos[1] = nestPos[1];
    previousX = -1; previousY = -1;
    carriesFood = false;
  }
  
  public String toString(){
   String s = color + " Ant-" + id + " at (" + pos[0] + "," + pos[1] + ").";
   if (terain.getCell(pos[0],pos[1]).hasFood() && !isAtNest() && !terain.getCell(pos[0],pos[1]).withNest()) 
            {s += " It has found food!"; return s;}
   if (isAtNest() && carriesFood) s += " It has returned to its nest and has food to deliver.";
   if (isAtNest() && !carriesFood) s+= " It is at its nest and does not hold any food.";
   if (!isAtNest() && carriesFood) s += " It is going back to its nest with food.";
   if (!isAtNest() && !carriesFood) s += " It is out looking for food.";
   return s;
 }
  
    
  public boolean isAtNest() {//checks if the ant is at his nest
	  return pos[0] == nestPos[0] && pos[1] == nestPos[1];	
  }
  
    
  public boolean wasAt(int x, int y){//checks if the ant was at this x ,y 
	  return previousX == x && previousY == y;	
  }

  public boolean isAt(int x, int y){//checks if the ant is in the current location
	  return pos[0] == x && pos[1] == y;	
  }

  public int getId() { //returns the id of the ant 
	  return id;	 
  }
  
  int[] getPos(){//returns the current position of th ant 
	  return pos;	
  }
    
  int[] getNestPos(){//returns the position of the nest
	  return nestPos;	  
  }
    
  boolean carries_food(){//returns if the ant carries food or not 
	  return carriesFood;	  
  }
    
  public void move(int time){//movement of the ant
	  if (!carriesFood) 
		  searchFood(time);//movement when he is searching food
	  else		 
		  goBackNest(time);//movement when he is going back to his nest
  }

  private void searchFood(int time) {
  	  
	  int maxScentAmount = 0;//scent for the cells 
	  Cell temp = null;
	  Cell bestCell = null;//best cell for the fastest movement 
	  int x = -1;//the next X 
	  int y = -1;//the next Y
	  	  
	  //upwards movement
	  if (!wasAt(pos[0], pos[1] - 1) && (temp = terain.getCell(pos[0], pos[1] - 1)) != null)//if the ant wasnt previous on the next cell and the cell has units of scents
	  {
		  if (temp.getScent() > maxScentAmount)//if the current cell scent is more than the max
		  {			
			  maxScentAmount = temp.getScent(); //saves the new max scented cell
			  bestCell = temp;//saves the best cell for the fastest movement 
			  x = pos[0];//keeps the X the same
			  y = pos[1] - 1;//goes one cell up
		  }
	  }
	  
	  //downwards movement
	  if (!wasAt(pos[0], pos[1] + 1) && (temp = terain.getCell(pos[0], pos[1] + 1)) != null)//if the ant wasnt previous on the next cell and the cell has units of scents
	  {
		  if (temp.getScent() > maxScentAmount)//if the current cell scent is more than the max
		  {
			  maxScentAmount = temp.getScent(); //saves the new max scented cell
			  bestCell = temp;//saves the best cell for the fastest movement 
			  x = pos[0];//keeps the cell the same 
			  y = pos[1] + 1; //goes one cell up
		  }
	  }
	  
	  //leftwards movement
	  if (!wasAt(pos[0] - 1, pos[1]) && (temp = terain.getCell(pos[0] - 1, pos[1])) != null)//if the ant wasnt previous on the next cell and the cell has units of scents
	  {
		  if (temp.getScent() > maxScentAmount)
		  {
			  maxScentAmount = temp.getScent(); 
			  bestCell = temp;
			  x = pos[0] - 1; //goes one cell on the left
			  y = pos[1]; //keeps the Y the same 
		  }
	  }
	  
	  //rightwards movement
	  if (!wasAt(pos[0] + 1, pos[1]) && (temp = terain.getCell(pos[0] + 1, pos[1])) != null)//if the ant wasnt previous on the next cell and the cell has units of scents
		  if (temp.getScent() > maxScentAmount)
		  {
			  maxScentAmount = temp.getScent(); 
			  bestCell = temp;
			  x = pos[0] + 1;
			  y = pos[1];
		  }
	  
	 //if there are no scents on the cells the ant starts moving randomly until it finds a cell with scent to follow  	  
	  if (maxScentAmount == 0)		  
	  {
		  int direction = StdRandom.uniform(0,4);//random generator for movements 
		  boolean found = false;//boolean for when the ant founds a cell with scent 
		  
		  while (!found)
		  {
			  switch(direction)
			  {
			  	case 0://up
			  		 if (!wasAt(pos[0], pos[1] - 1) && (temp = terain.getCell(pos[0], pos[1] - 1)) != null)//if the ant wasnt previous on the next cell and the cell has units of scents
			  		  {			  			 			  				  
			  				  bestCell = temp;//saves the next best cellto go
			  				  x = pos[0];//keeps the X the same
			  				  y = pos[1] - 1;//goes one cell up
			  				  found=true;			  			 			  			 
			  		  }		
			  		 else {//if not generates a new move 
			  			direction = StdRandom.uniform(0,4);
			  		 }
			  		 break;
			  		 
			  	case 1://down
			  	  if (!wasAt(pos[0], pos[1] + 1) && (temp = terain.getCell(pos[0], pos[1] + 1)) != null)//if the ant wasnt previous on the next cell and the cell has units of scents
				  {
						  bestCell = temp;//saves the next best cell to go
						  x = pos[0];//keeps the cell the same 
						  y = pos[1] + 1; //goes one cell up
						  found = true;
				  }
			  	 else {//if not generates a new move 
			  			direction = StdRandom.uniform(0,4);//random generator for movements
			  		 }
			  		 break;
			  		 
			  	case 2://left
			  		 if (!wasAt(pos[0] - 1, pos[1]) && (temp = terain.getCell(pos[0] - 1, pos[1])) != null)//if the ant wasnt previous on the next cell and the cell has units of scents
			  		  {
			  				  bestCell = temp;//saves the next best cell to go
			  				  x = pos[0] - 1; //goes one cell on the left
			  				  y = pos[1]; //keeps the Y the same 
			  				  found = true;
			  		  }
			  		 else {//if not generates a new move 
			  			direction = StdRandom.uniform(0,4);//random generator for movements
			  		 }
			  		 break;
			  		 
			  		 default://right
			  			  //rightwards movement
			  			  if (!wasAt(pos[0] + 1, pos[1]) && (temp = terain.getCell(pos[0] + 1, pos[1])) != null)//if the ant wasnt previous on the next cell and the cell has units of scents
			  			  {
			  					  bestCell = temp;
			  					  x = pos[0] + 1;
			  					  y = pos[1];
			  				  found=true;
			  			  }
			  			 else {//if not generates a new move 
					  			direction = StdRandom.uniform(0,4);//random generator for movements
					  		 }
					  		 break; 
			  }
		  }
	  }
	 
	  carriesFood = bestCell.hasFood() && !bestCell.withNest();//if the cell has food and it isnt a nest
	  
	  if (carriesFood)
	  {
		  bestCell.getFood();//it gets one food from the source 
		  
		  if (bestCell.hasFood()) //if the source has more food 
			  bestCell.addScent(time);	  //the ant starts to leave behind a scent trail
	  }
	  
	  bestCell.addAnt(id);//adds on the best cell option the ant
	  temp = terain.getCell(pos[0], pos[1]);//from the previous position of the ant 
	  temp.removeAnt(id);//removes  it from the cell list 
	  
	  previousX = pos[0];//keeps the new previous X
	  previousY = pos[1];//keeps the new previous Y
	  pos[0] = x; //puts in the current pos the new X
	  pos[1] = y;//puts in the current pos the new Y
	    	  
  }

  private void goBackNest(int time)
  {	  
	  Cell bestCell = null;//best cell to return back(fastest way)
	  Cell temp = null;
	  double minDistance = -1;//min distance to return
	  int minX = -1;
	  int minY = -1;
	  
	  double distance = -1;//distance from the nest to the current pos
	  int x1 = -1;//next move
	  int y1 = -1;
	  int x2 = nestPos[0];//X of nest
	  int y2 = nestPos[1];//Y of nest
	  
	  //upwards movement
	  if ((temp = terain.getCell(pos[0], pos[1] - 1)) != null)
	  {
		  x1 = pos[0];//keeps the X the same
		  y1 = pos[1] - 1; //goes up one cell
		  distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));//equation for distance 
		  
		  if (distance < minDistance || minDistance == -1)//if the distance from the nest is lower than the min distance
		  {
			  minDistance = distance;//saves the new min distance
			  bestCell = temp;//saves the next cell as the best one to return
			  minX = x1;//saves the min X
			  minY = y1;//saves the min Y					  			
		  }		 		 
	  }
	  
	  //downwards movement
	  if ((temp = terain.getCell(pos[0], pos[1] + 1)) != null)
	  {
		  x1 = pos[0];//keeps the X the same
		  y1 = pos[1] + 1; // goes one cell up 
		  distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));//equation for distance 
		  
		  if (distance < minDistance || minDistance == -1)//if the distance from the nest is lower than the min distance
		  {
			  minDistance = distance;//saves the new min distance
			  bestCell = temp;//saves the next cell as the best one to return
			  minX = x1;//saves the min X
			  minY = y1;//saves the min Y					  			
		  }		 		 
	  }
	  
	  //leftwards movement
	  if ((temp = terain.getCell(pos[0] - 1, pos[1])) != null)
	  {
		  x1 = pos[0] - 1; //goes left on the cells  
		  y1 = pos[1];   // keeps the Y the same
		  distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));//equation for distance 
		  
		  if (distance < minDistance || minDistance == -1)//if the distance from the nest is lower than the min distance
		  {
			  minDistance = distance;//saves the new min distance
			  bestCell = temp;//saves the next cell as the best one to return
			  minX = x1;//saves the min X
			  minY = y1;//saves the min Y					  			
		  }		 		 
	  }
	  
	  //rightwards movement
	  if ((temp = terain.getCell(pos[0] + 1, pos[1])) != null)
	  {
		  x1 = pos[0] + 1;//adds one on the X
		  y1 = pos[1];  //keeps the Y the same 
		  distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));//equation for distance 
		  
		  if (distance < minDistance || minDistance == -1)//if the distance from the nest is lower than the min distance
		  {
			  minDistance = distance;//saves the new min distance
			  bestCell = temp;//saves the next cell as the best one to return
			  minX = x1;//saves the min X
			  minY = y1;//saves the min Y					  			
		  }		 		 
	  }
	  
	  bestCell.addAnt(id);//adds the id of the ant on the next cell
	  temp = terain.getCell(pos[0], pos[1]);//saves the previous location
	  temp.removeAnt(id);//and remove the ant id from the previous one 
	  	  	  
	  previousX = pos[0];//saves the previous X
	  previousY = pos[1];//saves the previous Y
	  pos[0] = minX;//puts the current X
	  pos[1] = minY;//puts the current Y
	  
	  if (isAtNest())//if the ant is in a nest
	  {		  
		  carriesFood = false;//stops carrying food
		  bestCell.putFood();//adds on the nest the food he brought
		  
	  }	
	  else 
		  bestCell.addScent(time);//if his not on a nest adds a scent 
  }
}