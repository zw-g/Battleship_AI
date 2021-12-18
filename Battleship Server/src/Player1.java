// This player is as rudimentary as it gets.  It simply puts the ships in a static 
// location, and makes random moves until one sticks.  Your player can use this 
// as a base to expand upon. It is a good idea to play against this player until yours
// gets good enough to beat it regularly.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Player1 extends Player {

  Random rnd = new Random();
  ArrayList<int[]> noNoSpot = new ArrayList();
  ArrayList<int[]> played = new ArrayList();

  //to check how manny boat is left and how big is the boat
  int[] restBoat = new int[]{1,1,2,1};
  int maxSize = 5;

  //record where enemies boat is at
  ArrayList<int[]> hit = new ArrayList();
  ArrayList<int[]> potentialRowN = new ArrayList();
  ArrayList<int[]> potentialColE = new ArrayList();
  ArrayList<int[]> potentialRowS = new ArrayList();
  ArrayList<int[]> potentialColW = new ArrayList();
  ArrayList<int[]> moveLocation = new ArrayList(Arrays.asList(
      new int[]{1, 1}, new int[]{1, 3}, new int[]{1, 5}, new int[]{1, 7}, new int[]{1, 9},
      new int[]{2,2}, new int[]{2,4}, new int[]{2,6}, new int[]{2,8}, new int[]{2,10},
      new int[]{3,1}, new int[]{3,3}, new int[]{3,5}, new int[]{3,7}, new int[]{3,9},
      new int[]{4,2}, new int[]{4,4}, new int[]{4,6}, new int[]{4,8}, new int[]{4,10},
      new int[]{5,1}, new int[]{5,3}, new int[]{5,5}, new int[]{5,7}, new int[]{5,9},
      new int[]{6,2}, new int[]{6,4}, new int[]{6,6}, new int[]{6,8}, new int[]{6,10},
      new int[]{7,1}, new int[]{7,3}, new int[]{7,5}, new int[]{7,7}, new int[]{7,9},
      new int[]{8,2}, new int[]{8,4}, new int[]{8,6}, new int[]{8,8}, new int[]{8,10},
      new int[]{9,1}, new int[]{9,3}, new int[]{9,5}, new int[]{9,7}, new int[]{9,9},
      new int[]{10,2}, new int[]{10,4}, new int[]{10,6}, new int[]{10,8}, new int[]{10,10}
  ));
  int[] searchDir = new int[]{1,1,1,1};
  boolean skip = false;


  // You must call the super to establish the necessary game variables
  // and register the game.
  public Player1(int playerNum) {
    super(playerNum);
    setName("GZW");
  }

  @Override
  public void makeMove() {
    int x, row, col;
    if(moveLocation.size() > 0){
      x = rnd.nextInt(moveLocation.size());
      row = moveLocation.get(x)[0];
      col = moveLocation.get(x)[1];
      moveLocation.remove(x);
    } else {
      row = randomRow();
      col = randomCol();
    }

    // Try making a move until successful
    if (hit.size() == 0) {
      while (!game.makeMove(hisShips, myMoves, row, col)) {
        if(moveLocation.size() > 0){
          x = rnd.nextInt(moveLocation.size());
          row = moveLocation.get(x)[0];
          col = moveLocation.get(x)[1];
          moveLocation.remove(x);
        } else {
          row = randomRow();
          col = randomCol();
        }
      }
    }
    else{
      if (searchDir[0] == 1 && potentialRowN.size() != 0){
        row = potentialRowN.get(0)[0];
        col = potentialRowN.get(0)[1];
        potentialRowN.remove(0);
        while (!game.makeMove(hisShips, myMoves, row, col) && potentialRowN.size() != 0){
          row = potentialRowN.get(0)[0];
          col = potentialRowN.get(0)[1];
          potentialRowN.remove(0);
        }
      }
      else if (searchDir[1] == 1 && potentialColE.size() != 0){
        row = potentialColE.get(0)[0];
        col = potentialColE.get(0)[1];
        potentialColE.remove(0);
        while (!game.makeMove(hisShips, myMoves, row, col) && potentialColE.size() != 0){
          row = potentialColE.get(0)[0];
          col = potentialColE.get(0)[1];
          potentialColE.remove(0);
        }
      }
      else if (searchDir[2] == 1 && potentialRowS.size() != 0){
        row = potentialRowS.get(0)[0];
        col = potentialRowS.get(0)[1];
        potentialRowS.remove(0);
        while (!game.makeMove(hisShips, myMoves, row, col) && potentialRowS.size() != 0){
          row = potentialRowS.get(0)[0];
          col = potentialRowS.get(0)[1];
          potentialRowS.remove(0);
        }
      }
      else if (searchDir[3] == 1 && potentialColW.size() != 0){
        row = potentialColW.get(0)[0];
        col = potentialColW.get(0)[1];
        potentialColW.remove(0);
        while (!game.makeMove(hisShips, myMoves, row, col) && potentialColW.size() != 0){
          row = potentialColW.get(0)[0];
          col = potentialColW.get(0)[1];
          potentialColW.remove(0);
        }
      }
    }
    played.add(new int[]{row, col});
    numMoves++;
    addDbgText("Player " + myPlayerNum + " (" + row + ", " + col + ") num Moves = " + numMoves);
    //when it hits
    if (game.getMoveBoardValue(BSGame.BOARD_P1MOVES, row, col) == BSGame.PEG_HIT) {
      addDbgText("HIT!!!");
      if (skip){
        skip = false;
      } else {
        hit.add(new int[]{row, col});
      }
      if ((potentialRowN.size() + potentialColE.size() + potentialRowS.size() + potentialColW.size()) == 0 && hit.size() == 1) {
        searchDir = new int[]{1,1,1,1};
        for (int i = 1; i < maxSize; i++) {
          if(row + i < 11) potentialRowS.add(new int[]{row + i, col});
          if(row - i > 0) potentialRowN.add(new int[]{row - i, col});
          if (col + i < 11) potentialColE.add(new int[]{row, col + i});
          if (col - i > 0) potentialColW.add(new int[]{row, col - i});
        }
      }
      if(potentialRowN.size() == 0){
        searchDir[0] = 0;
      }
      if(potentialColE.size() == 0){
        searchDir[1] = 0;
      }
      if(potentialRowS.size() == 0){
        searchDir[2] = 0;
      }
      if(potentialColW.size() == 0){
        searchDir[3] = 0;
      }
    }
    //when it did not hit
    else{
      if(hit.size() != 0){
        if (searchDir[0] == 1){
          searchDir[0] = 0;
        }
        else if (searchDir[1] == 1){
          searchDir[1] = 0;
        }
        else if (searchDir[2] == 1){
          searchDir[2] = 0;
        }
        else if (searchDir[3] == 1){
          searchDir[3] = 0;
        }
      }
    }
  }

  public boolean addShips() {
    //init data
    noNoSpot.clear();
    played.clear();
    restBoat = new int[]{1,1,2,1};
    maxSize = 5;
    hit.clear();
    potentialRowN.clear();
    potentialColE.clear();
    potentialRowS.clear();
    potentialColW.clear();
    moveLocation = new ArrayList(Arrays.asList(
        new int[]{1, 1}, new int[]{1, 3}, new int[]{1, 5}, new int[]{1, 7}, new int[]{1, 9},
        new int[]{2,2}, new int[]{2,4}, new int[]{2,6}, new int[]{2,8}, new int[]{2,10},
        new int[]{3,1}, new int[]{3,3}, new int[]{3,5}, new int[]{3,7}, new int[]{3,9},
        new int[]{4,2}, new int[]{4,4}, new int[]{4,6}, new int[]{4,8}, new int[]{4,10},
        new int[]{5,1}, new int[]{5,3}, new int[]{5,5}, new int[]{5,7}, new int[]{5,9},
        new int[]{6,2}, new int[]{6,4}, new int[]{6,6}, new int[]{6,8}, new int[]{6,10},
        new int[]{7,1}, new int[]{7,3}, new int[]{7,5}, new int[]{7,7}, new int[]{7,9},
        new int[]{8,2}, new int[]{8,4}, new int[]{8,6}, new int[]{8,8}, new int[]{8,10},
        new int[]{9,1}, new int[]{9,3}, new int[]{9,5}, new int[]{9,7}, new int[]{9,9},
        new int[]{10,2}, new int[]{10,4}, new int[]{10,6}, new int[]{10,8}, new int[]{10,10}
    ));

    // randomly place ships
    int row = randomRow();
    int col = randomCol();
    int direction = getDirection();
    while (notInTheNoNoSpot(row, col, direction, 5)) {
      row = randomRow();
      col = randomCol();
      direction = getDirection();
    }
    game.putShip(this, myShips, Ships.SHIP_CARRIER, row, col, direction);
    addNoNoSpot(row, col, direction, 5);

    row = randomRow();
    col = randomCol();
    direction = getDirection();
    while (notInTheNoNoSpot(row, col, direction, 4)) {
      row = randomRow();
      col = randomCol();
      direction = getDirection();
    }
    game.putShip(this, myShips, Ships.SHIP_BATTLESHIP, row, col, direction);
    addNoNoSpot(row, col, direction, 4);

    row = randomRow();
    col = randomCol();
    direction = getDirection();
    while (notInTheNoNoSpot(row, col, direction, 3)) {
      row = randomRow();
      col = randomCol();
      direction = getDirection();
    }
    game.putShip(this, myShips, Ships.SHIP_CRUISER, row, col, direction);
    addNoNoSpot(row, col, direction, 3);

    row = randomRow();
    col = randomCol();
    direction = getDirection();
    while (notInTheNoNoSpot(row, col, direction, 2)) {
      row = randomRow();
      col = randomCol();
      direction = getDirection();
    }
    game.putShip(this, myShips, Ships.SHIP_DESTROYER, row, col, direction);
    addNoNoSpot(row, col, direction, 2);

    row = randomRow();
    col = randomCol();
    direction = getDirection();
    while (notInTheNoNoSpot(row, col, direction, 3)) {
      row = randomRow();
      col = randomCol();
      direction = getDirection();
    }
    game.putShip(this, myShips, Ships.SHIP_SUBMARINE, row, col, direction);
    addNoNoSpot(row, col, direction, 3);
    return true;
  }

  private void removeHit(){
    searchDir = new int[]{1,1,1,1};
    hit.clear();
    skip = true;
    potentialRowN.clear();
    potentialColE.clear();
    potentialRowS.clear();
    potentialColW.clear();
  }

  private void setMaxSize(){
    if(restBoat[0] == 0){
      if(restBoat[1] == 0){
        if(restBoat[2] == 0){
          maxSize = 2;
        } else maxSize = 3;
      } else maxSize = 4;
    } else maxSize = 5;
  }

  //to see if arraylist has the array
  private static boolean isInList(final List<int[]> list, final int[] candidate) {
    return list.stream().anyMatch(a -> Arrays.equals(a, candidate));
  }

  private boolean notInTheNoNoSpot(int row, int col, int direction, int length) {
    if (direction == 0) {
      for (int i = 0; i < length; i++) {
        if (isInList(noNoSpot, new int[]{row - i, col})) {
          return true;
        }
        if (row - i < 1) {
          return true;
        }
      }
    } else if (direction == 1) {
      for (int i = 0; i < length; i++) {
        if (isInList(noNoSpot, new int[]{row, col + i})) {
          return true;
        }
        if (col + i > 10) {
          return true;
        }
      }
    } else if (direction == 2) {
      for (int i = 0; i < length; i++) {
        if (isInList(noNoSpot, new int[]{row + i, col})) {
          return true;
        }
        if (row + i > 10) {
          return true;
        }
      }
    } else {
      for (int i = 0; i < length; i++) {
        if (isInList(noNoSpot, new int[]{row, col - i})) {
          return true;
        }
        if (col - i < 1) {
          return true;
        }
      }
    }
    return false;
  }

  private void addNoNoSpot(int row, int col, int direction, int length) {
    if (direction == 0) {
      if (row > 1 + length - 1) {
        if (!isInList(noNoSpot, new int[]{row - length, col})) {
          noNoSpot.add(new int[]{row - length, col});
        }
      }
      if (row < 10) {
        if (!isInList(noNoSpot, new int[]{row + 1, col})) {
          noNoSpot.add(new int[]{row + 1, col});
        }
      }
      for (int i = 0; i < length; i++) {
        if (!isInList(noNoSpot, new int[]{row - i, col})) {
          noNoSpot.add(new int[]{row - i, col});
        }
        if (col > 1) {
          if (!isInList(noNoSpot, new int[]{row - i, col - 1})) {
            noNoSpot.add(new int[]{row - i, col - 1});
          }
        }
        if (col < 10) {
          if (!isInList(noNoSpot, new int[]{row - i, col + 1})) {
            noNoSpot.add(new int[]{row - i, col + 1});
          }
        }
      }
    } else if (direction == 1) {
      if (col > 1) {
        if (!isInList(noNoSpot, new int[]{row, col - 1})) {
          noNoSpot.add(new int[]{row, col - 1});
        }
      }
      if (col + length - 1 < 10) {
        if (!isInList(noNoSpot, new int[]{row, col + length})) {
          noNoSpot.add(new int[]{row, col + length});
        }
      }
      for (int i = 0; i < length; i++) {
        if (!isInList(noNoSpot, new int[]{row, col + i})) {
          noNoSpot.add(new int[]{row, col + i});
        }
        if (row > 1) {
          if (!isInList(noNoSpot, new int[]{row - 1, col + i})) {
            noNoSpot.add(new int[]{row - 1, col + i});
          }
        }
        if (row < 10) {
          if (!isInList(noNoSpot, new int[]{row + 1, col + i})) {
            noNoSpot.add(new int[]{row + 1, col + i});
          }
        }
      }
    } else if (direction == 2) {
      if (row > 1) {
        if (!isInList(noNoSpot, new int[]{row - 1, col})) {
          noNoSpot.add(new int[]{row - 1, col});
        }
      }
      if (row + length - 1 < 10) {
        if (!isInList(noNoSpot, new int[]{row + length, col})) {
          noNoSpot.add(new int[]{row + length, col});
        }
      }
      for (int i = 0; i < length; i++) {
        if (!isInList(noNoSpot, new int[]{row + i, col})) {
          noNoSpot.add(new int[]{row + i, col});
        }
        if (col > 1) {
          if (!isInList(noNoSpot, new int[]{row + i, col - 1})) {
            noNoSpot.add(new int[]{row + i, col - 1});
          }
        }
        if (col < 10) {
          if (!isInList(noNoSpot, new int[]{row + i, col + 1})) {
            noNoSpot.add(new int[]{row + i, col + 1});
          }
        }
      }
    } else {
      if (col > 1 + length - 1) {
        if (!isInList(noNoSpot, new int[]{row, col - length})) {
          noNoSpot.add(new int[]{row, col - length});
        }
      }
      if (col < 10) {
        if (!isInList(noNoSpot, new int[]{row, col + 1})) {
          noNoSpot.add(new int[]{row, col + 1});
        }
      }
      for (int i = 0; i < length; i++) {
        if (!isInList(noNoSpot, new int[]{row, col - i})) {
          noNoSpot.add(new int[]{row, col - i});
        }
        if (row > 1) {
          if (!isInList(noNoSpot, new int[]{row - 1, col - i})) {
            noNoSpot.add(new int[]{row - 1, col - i});
          }
        }
        if (row < 10) {
          if (!isInList(noNoSpot, new int[]{row + 1, col - i})) {
            noNoSpot.add(new int[]{row + 1, col - i});
          }
        }
      }
    }
  }

  private int getDirection() {
    return rnd.nextInt(4);
  }

  public void sankCarrier() {
    addDbgText("You Sank my Carrier(p" + myPlayerNum + ")");
    System.out.println("You Sank my Carrier(p" + myPlayerNum + ")");
    restBoat[0] --;
    setMaxSize();
    removeHit();
  }

  public void sankBattleShip() {
    addDbgText("You Sank my Battleship(p" + myPlayerNum + ")");
    System.out.println("You Sank my Battleship(p" + myPlayerNum + ")");
    restBoat[1] --;
    setMaxSize();
    removeHit();
  }

  public void sankCruiser() {
    addDbgText("You Sank my Cruiser(p" + myPlayerNum + ")");
    System.out.println("You Sank my Cruiser(p" + myPlayerNum + ")");
    restBoat[2] --;
    setMaxSize();
    removeHit();
  }

  public void sankDestroyer() {
    addDbgText("You Sank my Destroyer(p" + myPlayerNum + ")");
    System.out.println("You Sank my Destroyer(p" + myPlayerNum + ")");
    restBoat[3] --;
    setMaxSize();
    removeHit();
  }

  public void sankSubmarine() {
    addDbgText("You Sank my Submarine(p" + myPlayerNum + ")");
    System.out.println("You Sank my Submarine(p" + myPlayerNum + ")");
    restBoat[2] --;
    setMaxSize();
    removeHit();
  }
}