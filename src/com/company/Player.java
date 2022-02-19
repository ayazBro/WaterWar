package com.company;
import java.util.Scanner;

public class Player {
    private int num;
    private   Grid userGrid = new Grid();


    Player(int num) {
        this.num=num;
    }

    public void startGame() {
        System.out.println("Player " +num+", place your ships on the game field");
        Ship aircraftCarrier = new Ship("Aircraft Carrier", 5);
        Ship battleship = new Ship("Battleship", 4);
        Ship submarine = new Ship("Submarine", 3);
        Ship cruiser = new Ship("Cruiser", 3);
        Ship destroyer = new Ship("Destroyer", 2);
        userGrid.printGrid();
        Grid.input(aircraftCarrier, userGrid);
        Grid.input(battleship, userGrid);
        Grid.input(submarine, userGrid);
        Grid.input(cruiser, userGrid);
        Grid.input(destroyer, userGrid);
    }

    public static void changeMove()  {
        String str="-a";
        Scanner scanner=new Scanner(System.in);
        while (str.length()!=0) {
            System.out.println("Press Enter and pass the move to another player ");
            //scanner.nextLine();
            str=scanner.nextLine();
        }

    }

    public static void playGame(Player player1, Player player2) {
        //player1.userGrid.createFogGrid();
        player2.userGrid.printFogGrid();
        System.out.println("---------------------");
        player1.userGrid.printGrid();
        System.out.println("Player "+player1.num+", it's your turn: ");
        player1.userGrid.takeShot(player2.userGrid);

    }

    public static boolean checkEnd(Player player1, Player player2) {
        if(player1.userGrid.kol()!=17 && player2.userGrid.kol()!=17) return true;
        else return false;
    }


}
