
import java.util.Random;
import java.util.Scanner;

public
    class S27375_p02 {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        int numPlayers;
        int[][] playerPieces;
        int[] diceRolls;
//        int[] moveDecisions;
        char[][] board = createBoard();
        Random rand = new Random();
        Scanner scan = new Scanner(System.in);
        System.out.print("Podaj liczbę graczy: ");
        numPlayers = scan.nextInt();

        playerPieces = new int[numPlayers][4];
        diceRolls = new int[numPlayers];
//        moveDecisions = new int[numPlayers];

        // Inicjalizacja początkowych pozycji graczy
        for (int i = 0; i < numPlayers; i++) {
            playerPieces[i][2] = 4;
            playerPieces[i][3] = 0;
        }

        int currentPlayer = 0;
        boolean gameOver = false;

        while (!gameOver) {
            System.out.println("Ruch gracza " + (currentPlayer + 1));
            int newPosition;
            int oldPosition;
            int dice;
            int[] xy;
            int x, y;
            switch (playerPieces[currentPlayer][2]) {
                case 4:
                    dice = throwDice();
                    int position = firstMove(currentPlayer, dice);
                    if(position == 100){
//                        currentPlayer = (currentPlayer+1) % numPlayers;
                        break;
                    }else{
                        playerPieces[currentPlayer][2] -= 1;
                        board = removePawn(currentPlayer, playerPieces[currentPlayer][2], board);
                        xy = positionValue(newPlayer(currentPlayer), currentPlayer);
                        x = xy[0];
                        y = xy[1];
                        System.out.println("Aktualny gracz: "+currentPlayer);
                        switch (currentPlayer){
                            case 0:
                                board[x][y] = 'a';
                                playerPieces[currentPlayer][1] = 0;
                                break;
                            case 1:
                                board[x][y] = 'b';
                                playerPieces[currentPlayer][1] = 10;
                                break;
                            case 2:
                                board[x][y] = 'c';
                                playerPieces[currentPlayer][1] = 20;
                                break;
                            case 4:
                                board[x][y] = 'd';
                                playerPieces[currentPlayer][1] = 30;
                                break;
                        }
                        dice = throwDice();
                        if(dice==6){
                            System.out.println("Twój wynik rzutu kostką: 6");
                            System.out.println("Wyjdź nowym pionkiem - 1, kolejny rzut - 2");
                            int decision = scan.nextInt();
                            if(decision == 1){
                                playerPieces[currentPlayer][2] -= 1;
                                board = removePawn(currentPlayer, playerPieces[currentPlayer][2], board);
                                newPosition = newPlayer(currentPlayer);
                                currentPlayer = (currentPlayer+1) % numPlayers;
                                break;
                            } else if (decision == 2) {
                                dice = throwDice()+6;
                                printBoard(board);
                                diceRolls[currentPlayer] = dice;
                                int[] move = move(currentPlayer, dice, diceRolls);
                                oldPosition = move[0];
                                newPosition = move[1];

                                if(newPosition > 39 && currentPlayer == 0){
                                    y = 6;
                                    switch (newPosition-39){
                                        case 1:
                                            x = 2;
                                            break;
                                        case 2:
                                            x = 3;
                                            break;
                                        case 3:
                                            x = 4;
                                            break;
                                        case 4:
                                            x = 5;
                                            break;
                                        default:
                                            x = 1;
                                            break;
                                    }
                                    if(x!=1){
                                        playerPieces[currentPlayer][3] += 1;
                                    }
                                } else if (newPosition > 49 && currentPlayer == 1) {
                                    x = 6;
                                    switch (newPosition-49){
                                        case 1:
                                            y = 10;
                                            break;
                                        case 2:
                                            y = 9;
                                            break;
                                        case 3:
                                            y = 8;
                                            break;
                                        case 4:
                                            y = 7;
                                            break;
                                        default:
                                            y = 11;
                                            break;
                                    }
                                    if(y!=11){
                                        playerPieces[currentPlayer][3] += 1;
                                    }
                                }else if(newPosition > 59 && currentPlayer == 2){
                                    y = 6;
                                    switch (newPosition-59){
                                        case 1:
                                            x = 10;
                                            break;
                                        case 2:
                                            x = 9;
                                            break;
                                        case 3:
                                            x = 8;
                                            break;
                                        case 4:
                                            x = 7;
                                            break;
                                        default:
                                            x = 11;
                                            break;
                                    }
                                    if(x!=11){
                                        playerPieces[currentPlayer][3] += 1;
                                    }
                                } else if (newPosition > 69 && currentPlayer == 3) {
                                    x = 6;
                                    switch (newPosition-69){
                                        case 1:
                                            y = 2;
                                            break;
                                        case 2:
                                            y = 3;
                                            break;
                                        case 3:
                                            y = 4;
                                            break;
                                        case 4:
                                            y = 5;
                                            break;
                                        default:
                                            y = 1;
                                    }
                                    if(y!=1){
                                        playerPieces[currentPlayer][3] += 1;
                                    }
                                }else{
                                    playerPieces[currentPlayer][1] = newPosition;
                                    int[] oldxy = positionValue(oldPosition, currentPlayer);
                                    board[oldxy[0]][oldxy[1]] = 'x';
                                    xy = positionValue(newPosition, currentPlayer);
                                    x = xy[0];
                                    y = xy[1];
                                }

                                board[x][y] = (char) playerPieces[currentPlayer][0];
                                printBoard(board);
                                if(playerPieces[currentPlayer][3]==4){
                                    gameOver = true;
                                }
                                break;
                            }
                        }else{
                            printBoard(board);
                            int[] move = move(currentPlayer, dice, diceRolls);
                            oldPosition = move[0];
                            int[] oldxy = positionValue(oldPosition, currentPlayer);
                            board[oldxy[0]][oldxy[1]] = 'x';
                            newPosition = move[1];
//                            char currentPlayerPiece = (char) playerPieces[currentPlayer][0];
                            xy = positionValue(newPosition, currentPlayer);
                            x = xy[0];
                            y = xy[1];
                            char currentPlayerPiece;
                            switch (currentPlayer){
                                case 0:
                                    currentPlayerPiece = 'a';
                                    break;
                                case 1:
                                    currentPlayerPiece = 'b';
                                    break;
                                case 2:
                                    currentPlayerPiece = 'c';
                                    break;
                                case 3:
                                    currentPlayerPiece = 'd';
                                    break;
                                default:
                                    currentPlayerPiece = ' ';
                            }
                            board[x][y] = currentPlayerPiece;
                            playerPieces[currentPlayer][1] = newPosition;
                            printBoard(board);
                            break;
                        }
                    }
                default:
                    dice = throwDice();
                    if(dice==6){
                        System.out.println("Twój wynik rzutu kostką: 6");
                        System.out.println("Wyjdź nowym pionkiem - 1, kolejny rzut - 2");
                        int decision = scan.nextInt();
                        if(decision==1){
                            playerPieces[currentPlayer][2] -= 1;
                            board = removePawn(currentPlayer, playerPieces[currentPlayer][2], board);
                            newPosition = move(currentPlayer, dice, diceRolls)[1];
                        } else if (decision==2) {
                            dice = throwDice()+6;
                            diceRolls[currentPlayer] = dice;
                        }
                    }
                    int[] move = move(currentPlayer, dice, diceRolls);
                    newPosition = move[1];
                    oldPosition = move[0];
                    if(oldPosition!=100){
                        System.out.println("Old position: "+oldPosition);
                        xy = positionValue(oldPosition, currentPlayer);
                        x = xy[0];
                        y = xy[1];
                        System.out.println("Old position x "+x+", y "+y);
                        board[x][y] = 'x';
                    }

                    char currentPlayerPiece = (char) playerPieces[currentPlayer][0];
                    xy = positionValue(newPosition, currentPlayer);
                    x = xy[0];
                    y = xy[1];

                    board[x][y] = currentPlayerPiece;
                    playerPieces[currentPlayer][1] = newPosition;
                    printBoard(board);
            }


            // Sprawdzenie warunku zakończenia gry
            // (np. jeśli gracz osiągnie przeciwny koniec planszy)

            // gameOver = true; // przykładowy warunek zakończenia gry

            currentPlayer = (currentPlayer+1) % numPlayers;
        }

        System.out.println("Koniec gry!");
        scan.close();
    }


    public static char[][] removePawn(int player, int pawnNumber, char[][] board) {
        switch (player){
            case 0:
                switch (pawnNumber){
                    case 3:
                        board[1][10] = ' ';
                        break;
                    case 2:
                        board[1][11] = ' ';
                        break;
                    case 1:
                        board[2][10] = ' ';
                        break;
                    case 0:
                        board[2][11] = ' ';
                        break;
                }
                break;
            case 1:
                switch (pawnNumber){
                    case 3:
                        board[10][10] = ' ';
                        break;
                    case 2:
                        board[10][11]= ' ';
                        break;
                    case 1:
                        board[11][10] = ' ';
                        break;
                    case 0:
                        board[11][11]= ' ';
                        break;
                }break;
            case 2:
                switch (pawnNumber){
                    case 3:
                        board[2][10] = ' ';
                        break;
                    case 2:
                        board[2][11] = ' ';
                        break;
                    case 1:
                        board[3][10] = ' ';
                        break;
                    case 0:
                        board[3][11] = ' ';
                        break;
                }
                break;
            case 3:
                switch (pawnNumber){
                    case 3:
                        board[1][1] = ' ';
                        break;
                    case 2:
                        board[1][2] = ' ';
                        break;
                    case 1:
                        board[2][1] = ' ';
                        break;
                    case 0:
                        board[2][2] = ' ';
                        break;
                }
                break;
        }
        return board;
    }

    public static int[] positionValue(int newPosition, int currentPlayer) {
        int x,y;
        int[] xy = new int[2];
        if(newPosition>39 && currentPlayer != 0){
            newPosition -= 39;
        }
        if(newPosition == 0 || newPosition == 38 || newPosition == 39){
            x = 1;
            if(x == 0){
                y = 7;
            }else if(x == 38 || x == 39) {
                y = newPosition - 33;
            }else{
                y = 7;
            }
        } else if (newPosition == 1 || newPosition == 37) {
            x = 2;
            if(newPosition == 1){
                y = 7;
            }else {
                y = 5;
            }
        } else if (newPosition == 2 || newPosition == 36) {
            x = 3;
            if(newPosition == 2){
                y = 7;
            } else {
                y = 5;
            }
        } else if (newPosition == 3 || newPosition == 35) {
            x = 4;
            if(newPosition == 3){
                y = 7;
            }else {
                y = 5;
            }
        } else if ((newPosition>=4 && newPosition <=8) || (newPosition>=30 && newPosition<=34)) {
            x = 5;
            if(newPosition>=4 && newPosition <=8){
                y = newPosition+3;
            }else{
                y = newPosition-29;
            }
        } else if (newPosition == 9 || newPosition == 29) {
            x = 6;
            if(newPosition == 9){
                y = 11;
            } else {
                y = 1;
            }
        } else if ((newPosition>=10 && newPosition<=14) || (newPosition>=24 && newPosition<=28)) {
            x = 7;
            if(newPosition == 10 ){
                y = 11;
            } else if (newPosition == 11) {
                y = 10;
            } else if (newPosition == 12) {
                y = 9;
            } else if (newPosition == 13) {
                y = 8;
            } else if (newPosition == 14) {
                y = 7;
            } else if (newPosition == 24) {
                y = 5;
            } else if (newPosition == 25) {
                y = 4;
            } else if (newPosition == 26) {
                y = 3;
            } else if (newPosition == 27) {
                y = 2;
            } else{
                y = 1;
            }
        } else if (newPosition==15 || newPosition == 23) {
            x = 8;
            if(newPosition == 15){
                y = 7;
            } else {
                y = 5;
            }
        } else if (newPosition == 16 || newPosition == 22) {
            x = 9;
            if(newPosition == 16){
                y = 7;
            } else {
                y = 5;
            }
        } else if (newPosition == 17 || newPosition == 21) {
            x = 10;
            if(newPosition == 17){
                y = 7;
            } else {
                y = 5;
            }
        } else if (newPosition>= 18 && newPosition<=20) {
            x = 11;
            if(newPosition == 18){
                y = 7;
            } else if (newPosition == 19) {
                y = 6;
            } else {
                y = 5;
            }
        }else{
            x = 0;
            y = 0;
        }
        xy[0] = x;
        xy[1] = y;
        return xy;
    }

    public static int throwDice(){
        Random rand = new Random();
        int value  = rand.nextInt(6);
        return value+1;
    }

    public static int firstMove(int currentPlayer, int dice) {
        int newPosition;
        int decision;
        Scanner scan = new Scanner(System.in);
        System.out.println("Aby wyjsć musisz wyrzucić 6");
        System.out.println("Czy kontynuować 1 - tak, 0 - nie: ");
        decision = scan.nextInt();
        if(decision == 0){
            scan.close();
        }
        if (dice == 6){
            System.out.println("Wurzucono 6! Wchodzisz do gry.");
            newPosition = newPlayer(currentPlayer);
            return newPosition;
        }else{
            System.out.println("Twój wynik to: "+dice + " , nie udało ci się.");
//            System.out.println("Czy kontynuować (-1 - zakończ ruch, 1 - kontynuj): ");
//            moveDecisions[currentPlayer] = scan.nextInt();
            newPosition = 100;
            return newPosition;
        }
    }

    public static int[] move(int currentPlayer, int dice, int[] diceRolls){
        int[] positions = new int[2];
        int newPosition;
        int oldPosition;
        Scanner scan = new Scanner(System.in);
        System.out.println("Ruch gracza "+(currentPlayer+1));
        diceRolls[currentPlayer] = dice;
        System.out.println("Twój wynik rzutu kostką: "+diceRolls[currentPlayer]);
//        System.out.println("Czy kontynuować (-1 - zakończ ruch, 1 - kontynuj): ");
//        moveDecisions[currentPlayer] = scan.nextInt();
        System.out.println("Podaj pozycje pionka, który chcesz ruszyć");
        oldPosition = scan.nextInt();
        newPosition = oldPosition + diceRolls[currentPlayer];
        positions[0] = oldPosition;
        positions[1] = newPosition;
        return positions;
    }

    public static int newPlayer(int currentPlayer){
        int newPosition;
        switch (currentPlayer){
            case 0:
                newPosition = 0;
                break;
            case 1:
                newPosition = 10;
                break;
            case 2:
                newPosition = 20;
                break;
            case 3:
                newPosition = 30;
                break;
            default:
                newPosition = 0;
        }
        return newPosition;
    }

    public static char[][] createBoard(){
        int[] ints = {10,20,30};
        char n10 = Integer.toString(ints[0]).charAt(0);
        char n11 = Integer.toString(ints[0]).charAt(1);
        char n20 = Integer.toString(ints[1]).charAt(0);
        char n21 = Integer.toString(ints[1]).charAt(1);
        char n30 = Integer.toString(ints[2]).charAt(0);
        char n31 = Integer.toString(ints[2]).charAt(1);

        char[][] board = new char[][]{
                {' ', ' ', ' ', ' ', ' ', ' ', ' ', '0', ' ', ' ', ' ', ' ', ' '},
                {' ', 'd', 'd', ' ', ' ', 'x', 'x', 'x', ' ', ' ', 'a', 'a', ' '},
                {' ', 'd', 'd', ' ', ' ', 'x', ' ', 'x', ' ', ' ', 'a', 'a', ' '},
                {' ', ' ', ' ', ' ', ' ', 'x', ' ', 'x', ' ', ' ', ' ', ' ', ' '},
                {n30, ' ', ' ', ' ', ' ', 'x', ' ', 'x', ' ', ' ', ' ', ' ', ' '},
                {n31, 'x', 'x', 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', 'x', ' '},
                {' ', 'x', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'x', ' '},
                {' ', 'x', 'x', 'x', 'x', 'x', ' ', 'x', 'x', 'x', 'x', 'x', n10, n11},
                {' ', ' ', ' ', ' ', ' ', 'x', ' ', 'x', ' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' ', 'x', ' ', 'x', ' ', ' ', ' ', ' ', ' '},
                {' ', 'c', 'c', ' ', ' ', 'x', ' ', 'x', ' ', ' ', 'b', 'b', ' '},
                {' ', 'c', 'c', ' ', ' ', 'x', 'x', 'x', ' ', ' ', 'b', 'b', ' '},
                {' ', ' ', ' ', ' ', n20, n21, ' ', ' ', ' ', ' ', ' ', ' ', ' '}
        };
        return board;
    }
    public static void printBoard(char[][] board) {
        // Wyświetl aktualny stan planszy
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            if(i != 7){
                System.out.println();
            }
        }
    }
}
