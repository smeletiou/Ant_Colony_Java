/**************************************

Sotirios Rafail Meletiou

AM941797

***************************************/
/***************************************  
* Author:     Sotirios Rafail Meletiou 
* Written:       25/11/2020
* Lastupdated:  7/12/2020

*****************************************/
public class CellGrid{

  private int size;
  private Cell[][] grid;
  
  public CellGrid(int N){
    size = N;
    grid = new Cell[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
         grid[i][j] = new Cell();
  }

 public String toString(){
   String s = "\nA " + size + "x" + size + " Terain as follows:\n";
   for (int x = 0; x < size; x++)
     for (int y = 0; y < size; y++)
       if (grid[x][y].hasFood() || grid[x][y].withNest() || grid[x][y].getScent() > 0 || 
           grid[x][y].hasAnts())
              s += "Cell(" + x + "," + y + "):\n" + grid[x][y];
   s += "\n\nAll other cells are empty.\n\n";
   return s;
 }
  
 public void draw(){
   StdDraw.setXscale(0.0, (double) size);
   StdDraw.setYscale(0.0, (double) size);
   StdDraw.clear(StdDraw.GRAY.darker().darker());
   StdDraw.setPenColor(StdDraw.GRAY.brighter().brighter());
   StdDraw.setPenRadius(0.05/size);
   for (int d=0; d <= size; d++) StdDraw.line((double)d,0.0,(double)d, (double)size);
   for (int d=0; d <= size; d++) StdDraw.line(0.0,(double)d,(double)size,(double)d);
   
   
   for (int i = 0; i < size; i++)
     for (int j = 0; j < size; j++){
           if (getCell(i,j).withNest() && getCell(i,j).hasFood()){
                  StdDraw.setPenRadius(0.8/size);
                  StdDraw.setPenColor(StdDraw.WHITE);
                  StdDraw.point((double)i,(double)j);
                  StdDraw.setPenRadius(0.6/size);
                  StdDraw.setPenColor(StdDraw.BLACK);
                  StdDraw.point((double)i,(double)j);
           }
           if (getCell(i,j).withNest() && !getCell(i,j).hasFood()){
                  StdDraw.setPenRadius(0.8/size);
                  StdDraw.setPenColor(StdDraw.WHITE);
                  StdDraw.point((double)i,(double)j);
           }
           if (getCell(i,j).hasFood() && !getCell(i,j).withNest()){
                  StdDraw.setPenRadius(0.6/size);
                  StdDraw.setPenColor(StdDraw.BLACK);
                  StdDraw.point((double)i,(double)j);
           }
       }
   }

 public int getSize() 
 {
	 return size;
 }

 public Cell getCell(int x, int y) 
 {
	 if (x < 0 || x > size - 1 || y < 0  || y > size - 1)//checks if the x nad y doesnt go out of bounds 
		 return null;
	 else 
		 return grid[y][x];//return the grid
 }
 
 public void putNest(int x, int y, int[] antIds) 
 {
	 grid[y][x].putNest();//registers the cell as an nest 
	 
	 for (int i = 0; i < antIds.length; i++)
		 grid[y][x].addAnt(antIds[i]);//adds in the nest the ants
 }
 
 public void putFood(int x, int y, int food)
 {
	 grid[y][x].putFood(food);//registers the cell as a food source and puts the amount of food available 
 }

 public void updateScent(int time, int elapsed)
 {
	 for (int x = 0; x < size; x++)
		 for (int y = 0; y < size; y++)
			 grid[y][x].updateScent(time, elapsed);    //updates the scent where its needed on the cells 
 }
  
 public boolean allSeedsCollected()
 {
	 boolean collected = true;//it assumes that all the seeds are collected 
	 int x = 0;
	 int y = 0;
	 //instead of 2 different for's to check every cell
	 while (collected && x < size)
	 {
		 y=0;
		 while (collected && y < size)
		 {
			 if (grid[y][x].hasFood() && !grid[y][x].withNest())//checks every cell if they got seeds and they are not nests
				 collected = false;//if he founds a cell with seeds and its not a nest makes the boolean false 
			 else
				 y++;//next Y	 
		 }
		 x++;//next X
	 }
	 return collected;	 
 }
}