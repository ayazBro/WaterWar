package com.company;
import java.util.Scanner;

class Grid {

    private final int ROWS = 10;
    private final int COLUMNS = 10;
    private char[][] battleGrid;
    private char[][] fogGrid=new char[ROWS][COLUMNS];
    private boolean isOkay = false;
    private int countShip = 0;


    public Grid() {
        battleGrid = new char[ROWS][COLUMNS];
        for (int i = 0; i < battleGrid.length; i++) {
            for (int j = 0; j < battleGrid[i].length; j++) {
                battleGrid[i][j] = '~';
            }
        }
        for(int i=0;i<10;i++) {
            for(int j=0;j<10;j++) {
                fogGrid[i][j]='~';
            }
        }
    }

    public int getCountShip() {
        return countShip;
    }

    public boolean isOkay() {
        return isOkay;
    }

    public int kol() {
        int k=0;
        for(int i=0;i< fogGrid.length;i++)
        {
            for(int j=0;j< fogGrid[i].length;j++)
            {
                if(fogGrid[i][j]=='X')
                    k++;
            }
        }
        return k;
    }

    public void printGrid() {
        char startLetter = 'A';
        for (int i = 1; i <= battleGrid.length; i++) {
            if (i == 1) System.out.print("  ");
            else if (i <= 10) System.out.print(" ");
            System.out.print(i);
        }
        System.out.println();
        for (int i = 0; i < battleGrid.length; i++) {
            System.out.print(startLetter++ + " ");
            for (int j = 0; j < battleGrid[i].length; j++) {
                System.out.print(battleGrid[i][j]);
                if (j != battleGrid[i].length - 1) System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void printFogGrid() {
        char startLetter = 'A';
        for (int i = 1; i <= fogGrid.length; i++) {
            if (i == 1) System.out.print("  ");
            else if (i <= 10) System.out.print(" ");
            System.out.print(i);
        }
        System.out.println();
        for (int i = 0; i < fogGrid.length; i++) {
            System.out.print(startLetter++ + " ");
            for (int j = 0; j < fogGrid[i].length; j++) {
                System.out.print(fogGrid[i][j]);
                if (j != fogGrid[i].length - 1) System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void input(Ship ship, Grid grid) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter the coordinates of the %s (%d cells): ", ship.getName(), ship.getCells());
        int firstRow = -1;
        int firstColumn = -1;
        int secondRow = - 1;
        int secondColumn = -1;
        while (!ship.isShipSet()) {
            String firstCoordinate = scanner.next();
            firstCoordinate = firstCoordinate.toUpperCase();
            firstRow = firstCoordinate.charAt(0) - 65;
            String temp = firstCoordinate.substring(1);
            firstColumn = Integer.parseInt(temp);
            String secondCoordinate = scanner.next();
            secondCoordinate = secondCoordinate.toUpperCase();
            secondRow = secondCoordinate.charAt(0) - 65;
            String tmp = secondCoordinate.substring(1);
            secondColumn = Integer.parseInt(tmp);
            grid.updateGrid(ship, firstRow, firstColumn - 1, secondRow, secondColumn - 1);
        }
        grid.printGrid();
    }

    public static void takeShot(Grid grid) {
        int firstRow=-1,firstColumn=-1;
        Scanner scanner = new Scanner(System.in);
        String shot=scanner.next();
        shot=shot.toUpperCase();
        char temp=shot.charAt(0);
        String temp2=Character.toString(temp);
        String temp3 = shot.substring(1);
        int chislo=Integer.parseInt(temp3);
        while(!temp2.matches("[A-J]+")|| (chislo<0||chislo>10) ){
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            shot=scanner.next();
            shot=shot.toUpperCase();
            temp=shot.charAt(0);
            temp2=Character.toString(temp);
            temp3 = shot.substring(1);
            chislo=Integer.parseInt(temp3);
        }
        firstRow = shot.charAt(0) - 65;
        firstColumn = Integer.parseInt(temp3);
        grid.doShot(firstRow,firstColumn-1);
    }

    public void updateGrid(Ship ship, int firstRow, int firstColumn, int secondRow, int secondColumn) {
        if (isValid(ship, firstRow, firstColumn, secondRow, secondColumn)) {
            if (firstRow == secondRow) {
                for (int i = 0; i <= ship.getCells(); i++) {
                    for (int j = Math.min(firstColumn, secondColumn); j <= Math.max(firstColumn, secondColumn); j++) {
                        battleGrid[firstRow][j] = 'O';
                    }
                }
            } else if (firstColumn == secondColumn) {
                for (int i = 0; i <= ship.getCells(); i++) {
                    for (int j = Math.min(firstRow, secondRow); j <= Math.max(firstRow, secondRow); j++) {
                        battleGrid[j][firstColumn] = 'O';
                    }
                }
            }
            ship.setShipSet(true);
            countShip++;
        }
    }

    public boolean isRightLength(Ship ship, int firstRow, int firstColumn, int secondRow, int secondColumn) {
        if (firstColumn > secondColumn && Math.abs(firstColumn - secondColumn + 1) != ship.getCells()) return true;
        if (firstColumn < secondColumn && Math.abs(firstColumn - secondColumn - 1) != ship.getCells()) return true;
        if (firstRow > secondRow && Math.abs(firstRow - secondRow + 1) != ship.getCells()) return true;
        if (firstRow < secondRow && Math.abs(firstRow - secondRow - 1) != ship.getCells()) return true;
        return false;
    }

    public boolean isValid(Ship ship, int firstRow, int firstColumn, int secondRow, int secondColumn) {
        if (isRightLength(ship, firstRow, firstColumn, secondRow, secondColumn)) {
            System.out.print("Error! Wrong length of the Submarine! Try again: ");
            return false;
        } else if (!isClose(ship, firstRow, firstColumn, secondRow, secondColumn)) {
            System.out.print("Error! You placed it too close to another one. Try again: ");
            return false;
        } else if ((firstColumn == secondColumn || firstRow == secondRow) && isClose(ship, firstRow, firstColumn, secondRow, secondColumn)) {
            return true;
        } else {
            System.out.print("Error! Wrong ship location! Try again: ");
            return false;
        }
    }

    public boolean isClose(Ship ship, int firstRow, int firstColumn, int secondRow, int secondColumn) {
        if (firstRow >= 0 && firstRow <= battleGrid.length && secondRow >= 0 && secondColumn <= battleGrid.length) {
            if (firstRow == secondRow) {
                for (int i = 0; i <= ship.getCells(); i++) {
                    for (int j = Math.min(firstColumn, secondColumn); j <= Math.max(firstColumn, secondColumn); j++) {
                        if ((firstRow - 1 >= 0) && battleGrid[firstRow - 1][j] == 'O') return false;
                        if ((firstRow + 1 < battleGrid.length) && battleGrid[firstRow + 1][j] == 'O') return false;
                        if ((j - 1) >= 0 && battleGrid[firstRow][j - 1] == 'O') return false;
                        if ((j + 1) < battleGrid.length && battleGrid[firstRow][j + 1] == 'O') return false;
                    }
                }
                return true;
            }
            else {
                for (int i = 0; i <= ship.getCells(); i++) {
                    for (int j = Math.min(firstRow, secondRow); j <= Math.max(firstRow, secondRow); j++) {
                        if ((firstColumn - 1 >= 0) && battleGrid[j][firstColumn - 1] == 'O') return false;
                        if ((firstColumn + 1 < battleGrid.length) && battleGrid[j][firstColumn + 1] == 'O')
                            return false;
                        if ((j - 1) >= 0 && battleGrid[j - 1][firstColumn] == 'O') return false;
                        if ((j + 1) < battleGrid.length && battleGrid[j + 1][firstColumn] == 'O') return false;
                    }
                }
                return true;
            }
        }
        else {
            System.out.println("Error the coordinates are out of bounds of the grid! Try again: " + firstRow + " " + firstColumn + " " + secondRow + " " + secondColumn);
            return false;
        }
    }

    public void doShot(int firstRow, int firstColumn) {

            if (battleGrid[firstRow][firstColumn] != '~') {
                battleGrid[firstRow][firstColumn] = 'X';
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        fogGrid[i][j] = '~';
                        if (battleGrid[i][j] == 'M') {
                            fogGrid[i][j] = 'M';
                        }
                        if (battleGrid[i][j] == 'X') {
                            fogGrid[i][j] = 'X';
                        }
                    }
                }
                if(kol()!=17) System.out.println("You hit a ship!");
                if(kol()==17) System.out.println("You sank the last ship. You won. Congratulations!");
            }
            else {
                battleGrid[firstRow][firstColumn] = 'M';
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        fogGrid[i][j] = '~';
                        if (battleGrid[i][j] == 'M') {
                            fogGrid[i][j] = 'M';
                        }
                        if (battleGrid[i][j] == 'X') {
                            fogGrid[i][j] = 'X';
                        }
                    }
                }
                System.out.println("You missed!");
            }
    }
}
