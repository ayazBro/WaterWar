package com.company;

class Main {

    public static final int ROWS = 10;
    public static final int COLUMNS = 10;

    public static void main(String[] args) {
        Player player1=new Player(1);
        Player player2=new Player(2);

        player1.startGame();
        Player.changeMove();
        player2.startGame();
       while(Player.checkEnd(player1,player2)) {
           Player.changeMove();
           Player.playGame(player1, player2);
           Player.changeMove();
           if(Player.checkEnd(player1,player2)) Player.playGame(player2, player1);
       }
    }


}


