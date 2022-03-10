import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Board {
    int[][] boardGrid;
    int[][] pieceSquareTableForEight;
    int[][] pieceSquareTableForSix;
    ArrayList<BoardPiece> whiteBoardPieces;
    ArrayList<BoardPiece> blackBoardPieces;
    static HashMap<int[][], Integer> boardChecker = new HashMap<>();

    int boardSize;

    boolean whiteTurn;
    boolean blackTurn;
    boolean winnerWhite;
    boolean winnerBlack;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.boardGrid = new int[boardSize][boardSize];

        whiteBoardPieces = new ArrayList<>();
        blackBoardPieces = new ArrayList<>();

        whiteTurn = false;
        blackTurn = true;
        winnerBlack = false;
        winnerWhite = false;

        for (int i=0;i<boardSize;i++){
            for (int j=0;j<boardSize;j++){
                boardGrid[i][j] = 0;
            }
        }

        pieceSquareTableForEight = new int[][]{{-100,-80,-60,-40,-40,-60,-80,-100},
                                                {-80,10,10,10,10,10,10,-80},
                                                {-60,10,30,40,40,30,10,-60},
                                                {-40,10,40,50,50,40,10,-40},
                                                {-40,10,40,50,50,40,10,-40},
                                                {-60,10,30,40,40,30,10,-60},
                                                {-80,10,10,10,10,10,10,-80},
                                                {-100,-80,-60,-40,-40,-60,-80,-100}};

        pieceSquareTableForSix = new int[][]{{-100,-25,-20,-20,-25,-100},
                                             {-25,10,10,10,10,-25},
                                             {-20,10,50,50,10,-20},
                                             {-20,10,50,50,10,-20},
                                             {-25,10,10,10,10,-25},
                                             {-100,-25,-20,-20,-25,-100}};

        for (int i=1;i<boardSize-1;i++){
            this.boardGrid[i][0] = 1;
            this.boardGrid[i][boardSize-1] = 1;

            this.whiteBoardPieces.add(new BoardPiece(1,i,0,boardSize));
            this.whiteBoardPieces.add(new BoardPiece(1,i,boardSize-1,boardSize));

            this.boardGrid[0][i] = 2;
            this.boardGrid[boardSize-1][i] = 2;

            this.blackBoardPieces.add(new BoardPiece(2,0,i,boardSize));
            this.blackBoardPieces.add(new BoardPiece(2,boardSize-1,i,boardSize));
        }
    }

    public Board(Board gameBoard){

        this.boardSize = gameBoard.boardSize;

        this.boardGrid = new int[boardSize][boardSize];
        this.whiteBoardPieces = new ArrayList<>();
        this.blackBoardPieces = new ArrayList<>();

        this.setBoardGrid(gameBoard.boardGrid);
        this.setWhiteBoardPieces(gameBoard.getWhiteBoardPieces());
        this.setBlackBoardPieces(gameBoard.getBlackBoardPieces());

        this.whiteTurn = gameBoard.whiteTurn;
        this.blackTurn = gameBoard.blackTurn;
        winnerBlack = gameBoard.winnerBlack;
        winnerWhite = gameBoard.winnerWhite;
    }

    public boolean isWinnerWhite() {
        return winnerWhite;
    }

    public boolean isWinnerBlack() {
        return winnerBlack;
    }

    public int[][] getBoardGrid() {
        return boardGrid;
    }

    public void setBoardGrid(int[][] boardGrid) {
        for (int i=0;i<boardSize;i++){
            for (int j=0;j<boardSize;j++){
                this.getBoardGrid()[i][j] = boardGrid[i][j];
            }
        }
    }

    public ArrayList<BoardPiece> getWhiteBoardPieces() {
        return whiteBoardPieces;
    }

    public void setWhiteBoardPieces(ArrayList<BoardPiece> whiteBoardPieces) {
        for (BoardPiece bp: whiteBoardPieces) {
            BoardPiece nbp = new BoardPiece(bp.getColor(),bp.getRow(),bp.getColumn(),bp.boardSize);
            this.getWhiteBoardPieces().add(nbp);
        }
    }

    public ArrayList<BoardPiece> getBlackBoardPieces() {
        return blackBoardPieces;
    }

    public void setBlackBoardPieces(ArrayList<BoardPiece> blackBoardPieces) {
        for (BoardPiece bp: blackBoardPieces) {
            BoardPiece nbp = new BoardPiece(bp.getColor(),bp.getRow(),bp.getColumn(),bp.boardSize);
            this.getBlackBoardPieces().add(nbp);
        }
    }

    public ArrayList<Integer[]> generateMoves(int row, int column, int color){
        ArrayList<Integer[]> moveList = new ArrayList<>();

        //check horizontal moves
        int horizontalMoveSize = 0;
        for (int i=0;i<boardSize;i++){
            if (boardGrid[row][i] != 0) horizontalMoveSize++;
        }
        //check vertical moves
        int verticalMoveSize = 0;
        for (int i=0;i<boardSize;i++){
            if (boardGrid[i][column] != 0) verticalMoveSize++;
        }
        //check right diagonal moves
        int rightDiagonalMoveSize = 1;
        int currentX = row;
        int currentY = column;

        while (true){ //downwards
            currentX++;
            currentY--;
            if (currentY < 0 || currentX > boardSize-1){
                break;
            }
            if (boardGrid[currentX][currentY] != 0) rightDiagonalMoveSize++;
        }

        currentX = row;
        currentY = column;
        while (true){ //upwards
            currentX--;
            currentY++;
            if (currentX < 0 || currentY > boardSize-1){
                break;
            }
            if (boardGrid[currentX][currentY] != 0) rightDiagonalMoveSize++;
        }
        //check left diagonal moves
        int leftDiagonalMoveSize = 1;
        currentX = row;
        currentY = column;

        while (true){ //downwards
            currentX++;
            currentY++;
            if (currentX > boardSize-1 || currentY > boardSize-1){
                break;
            }
            if (boardGrid[currentX][currentY] != 0) leftDiagonalMoveSize++;
        }

        currentX = row;
        currentY = column;
        while (true){ //upwards
            currentX--;
            currentY--;
            if (currentY < 0 || currentX < 0){
                break;
            }
            if (boardGrid[currentX][currentY] != 0) leftDiagonalMoveSize++;
        }


        //move horizontally
        if (horizontalMoveSize > 0){
            //move left
            if (column-horizontalMoveSize >= 0){
                boolean insertHL = true;
                for (int j = column-1; j > column-horizontalMoveSize; j--){
                    if ((boardGrid[row][j] == 1 && color == 2) || (boardGrid[row][j] == 2 && color == 1)) insertHL = false;
                }
                if (boardGrid[row][column-horizontalMoveSize] != boardGrid[row][column] && insertHL)
                    moveList.add(new Integer[]{row,column-horizontalMoveSize});
            }
            //move right
            if (column+horizontalMoveSize < boardSize){
                boolean insertHR = true;
                for (int j = column+1; j < column+horizontalMoveSize; j++){
                    if ((boardGrid[row][j] == 1 && color == 2) || (boardGrid[row][j] == 2 && color == 1)) insertHR = false;
                }
                if (boardGrid[row][column+horizontalMoveSize] != boardGrid[row][column] && insertHR)
                    moveList.add(new Integer[]{row,column+horizontalMoveSize});
            }
        }
        //move vertically
        if (verticalMoveSize > 0){
            //move up
            if (row-verticalMoveSize >= 0){
                boolean insertVU = true;
                for (int i = row-1; i > row-verticalMoveSize; i--){
                    if ((boardGrid[i][column] == 1 && color == 2) || (boardGrid[i][column] == 2 && color == 1)) insertVU = false;
                }
                if (boardGrid[row-verticalMoveSize][column] != boardGrid[row][column] && insertVU)
                    moveList.add(new Integer[]{row-verticalMoveSize,column});
            }
            //move down
            if (row+verticalMoveSize < boardSize){
                boolean insertVD = true;
                for (int i = row+1; i < row+verticalMoveSize; i++){
                    if ((boardGrid[i][column] == 1 && color == 2) || (boardGrid[i][column] == 2 && color == 1)) insertVD = false;
                }
                if (boardGrid[row+verticalMoveSize][column] != boardGrid[row][column] && insertVD)
                    moveList.add(new Integer[]{row+verticalMoveSize,column});
            }
        }

        //move diagonally right
        if (rightDiagonalMoveSize > 0){
            //upwards from left to right
            if (row-rightDiagonalMoveSize >= 0 && column+rightDiagonalMoveSize < boardSize){
                boolean insertRDUL2R = true;
                for (int i = 1; i < rightDiagonalMoveSize; i++){
                    if ((boardGrid[row-i][column+i] == 1 && color == 2) || (boardGrid[row-i][column+i] == 2 && color == 1)) insertRDUL2R = false;
                }
                if (boardGrid[row-rightDiagonalMoveSize][column+rightDiagonalMoveSize] != boardGrid[row][column] && insertRDUL2R)
                    moveList.add(new Integer[]{row-rightDiagonalMoveSize,column+rightDiagonalMoveSize});
            }
            //downwards from right to left
            if (row+rightDiagonalMoveSize < boardSize && column-rightDiagonalMoveSize >= 0){
                boolean insertRDDR2L = true;
                for (int i = 1; i < rightDiagonalMoveSize; i++){
                    if ((boardGrid[row+i][column-i] == 1 && color == 2) || (boardGrid[row+i][column-i] == 2 && color == 1)) insertRDDR2L = false;
                }
                if (boardGrid[row+rightDiagonalMoveSize][column-rightDiagonalMoveSize] != boardGrid[row][column] && insertRDDR2L)
                    moveList.add(new Integer[]{row+rightDiagonalMoveSize,column-rightDiagonalMoveSize});
            }
        }
        //move diagonally left
        if (leftDiagonalMoveSize > 0){
            //upwards from right to left
            if (row-leftDiagonalMoveSize >= 0 && column-leftDiagonalMoveSize >= 0){
                boolean insertLDUR2L = true;
                for (int i = 1; i < leftDiagonalMoveSize; i++){
                    if ((boardGrid[row-i][column-i] == 1 && color == 2) || (boardGrid[row-i][column-i] == 2 && color == 1)) insertLDUR2L = false;
                }
                if (boardGrid[row-leftDiagonalMoveSize][column-leftDiagonalMoveSize] != boardGrid[row][column] && insertLDUR2L)
                    moveList.add(new Integer[]{row-leftDiagonalMoveSize,column-leftDiagonalMoveSize});
            }
            //downwards from left to right
            if (row+leftDiagonalMoveSize < boardSize && column+leftDiagonalMoveSize < boardSize){
                boolean insertLDDL2R = true;
                for (int i = 1; i < leftDiagonalMoveSize; i++){
                    if ((boardGrid[row+i][column+i] == 1 && color == 2) || (boardGrid[row+i][column+i] == 2 && color == 1)) insertLDDL2R = false;
                }
                if (boardGrid[row+leftDiagonalMoveSize][column+leftDiagonalMoveSize] != boardGrid[row][column] && insertLDDL2R)
                    moveList.add(new Integer[]{row+leftDiagonalMoveSize,column+leftDiagonalMoveSize});
            }
        }
        if (moveList.size() == 0){
            moveList.add(new Integer[]{-100,-100});
        }
        return moveList;
    }

    public ArrayList<Integer[]> getPossibleMoves(int row, int column){
        ArrayList<Integer[]> moveList = new ArrayList<>();

        if (boardGrid[row][column] == 1 && whiteTurn){ // white piece
            moveList = generateMoves(row,column,1);
        }

        if (boardGrid[row][column] == 2 && blackTurn){ // black piece
            moveList = generateMoves(row,column,2);
        }

        return moveList;
    }

    public void moveBoardPiece(int initRow,int initColumn, int finalRow, int finalColumn){

        if (whiteTurn){

            //System.out.println("Moving white from (" + initRow + "," + initColumn + ") to (" + finalRow + "," + finalColumn + ")");

            for (BoardPiece bbp:blackBoardPieces) {
                if ((bbp.getRow() == finalRow) && (bbp.getColumn() == finalColumn)){
                    blackBoardPieces.remove(bbp);
                    break;
                }
            }

            for (BoardPiece wbp:whiteBoardPieces) {
                if ((wbp.getRow() == initRow) && (wbp.getColumn() == initColumn)){
                    wbp.setRow(finalRow);
                    wbp.setColumn(finalColumn);
                    boardGrid[finalRow][finalColumn] = 1;
                    boardGrid[initRow][initColumn] = 0;
                    break;
                }
            }

            winnerWhite = checkWinnerWhite();
            if (!winnerWhite) {
                winnerBlack = checkWinnerBlack();
            }

//            if (winnerWhite) System.out.println("White won");
//            if (winnerBlack) System.out.println("Black won");

            whiteTurn = false;
            blackTurn = true;

        } else if (blackTurn){

            //System.out.println("Moving black from (" + initRow + "," + initColumn + ") to (" + finalRow + "," + finalColumn + ")");

            for (BoardPiece wbp:whiteBoardPieces) {
                if ((wbp.getRow() == finalRow) && (wbp.getColumn() == finalColumn)){
                    whiteBoardPieces.remove(wbp);
                    break;
                }
            }

            for (BoardPiece bbp:blackBoardPieces) {
                if ((bbp.getRow() == initRow) && (bbp.getColumn() == initColumn)){
                    bbp.setRow(finalRow);
                    bbp.setColumn(finalColumn);
                    boardGrid[finalRow][finalColumn] = 2;
                    boardGrid[initRow][initColumn] = 0;
                    break;
                }
            }

            winnerBlack = checkWinnerBlack();
            if (!winnerBlack) {
                winnerWhite = checkWinnerWhite();
            }

            if (winnerWhite) System.out.println("White won");
            if (winnerBlack) System.out.println("Black won");

            whiteTurn = true;
            blackTurn = false;

        }
    }

    public boolean checkWinnerWhite(){
        if (this.getWhiteBoardPieces().size() == 1){
            return true;
        }

        int marked = -1;

        ArrayList<BoardPiece> connectedWhiteBoardPieces = new ArrayList<>();

        if (this.getWhiteBoardPieces().size()>0){
            connectedWhiteBoardPieces.add(this.getWhiteBoardPieces().get(0));
            this.getWhiteBoardPieces().get(0).setMarked(true);
            marked = 1;
            //marked++;
        }

        while (connectedWhiteBoardPieces.size() > 0){
            BoardPiece boardPiece = connectedWhiteBoardPieces.remove(0);
            int row = boardPiece.getRow();
            int column = boardPiece.getColumn();

            int iStart = (row == 0) ? row : row-1;
            int iEnd = (row == boardSize-1) ? row : row+1;

            int jStart = (column == 0) ? column : column-1;
            int jEnd = (column == boardSize-1) ? column : column+1;

            for (int i = iStart; i <= iEnd ;i++){
                for (int j = jStart; j <= jEnd; j++){
                    if (i == row && j == column) continue;
                    if (boardGrid[i][j] == 1){
                        for (BoardPiece bp: this.getWhiteBoardPieces()) {
                            if ((bp.getRow() == i && bp.getColumn() == j) && !bp.isMarked()){
                                bp.setMarked(true);
                                marked++;
                                connectedWhiteBoardPieces.add(bp);
                            }
                        }
                    }
                }
            }

        }

        for (BoardPiece bp: this.getWhiteBoardPieces()) {
            bp.setMarked(false);
        }

        if (marked == this.getWhiteBoardPieces().size()){
            return true;
        }
        return false;
    }

    public boolean checkWinnerBlack(){
        if (this.getBlackBoardPieces().size() == 1){
            return true;
        }

        int marked = -1;

        ArrayList<BoardPiece> connectedBlackBoardPieces = new ArrayList<>();

        if (this.getBlackBoardPieces().size() > 0){
            connectedBlackBoardPieces.add(this.getBlackBoardPieces().get(0));
            this.getBlackBoardPieces().get(0).setMarked(true);
            marked = 1;
            //marked++;
        }

        while (connectedBlackBoardPieces.size() > 0){
            BoardPiece boardPiece = connectedBlackBoardPieces.remove(0);
            int row = boardPiece.getRow();
            int column = boardPiece.getColumn();

            int iStart = (row == 0) ? row : row-1;
            int iEnd = (row == boardSize-1) ? row : row+1;

            int jStart = (column == 0) ? column : column-1;
            int jEnd = (column == boardSize-1) ? column : column+1;

            for (int i = iStart; i <= iEnd ;i++){
                for (int j = jStart; j <= jEnd; j++){
                    if (i == row && j == column) continue;
                    if (boardGrid[i][j] == 2){
                        for (BoardPiece bp: this.getBlackBoardPieces()) {
                            if ((bp.getRow() == i && bp.getColumn() == j) && !bp.isMarked()){
                                bp.setMarked(true);
                                marked++;
                                connectedBlackBoardPieces.add(bp);
                            }
                        }
                    }
                }
            }

        }

        for (BoardPiece bp: this.getBlackBoardPieces()) {
            bp.setMarked(false);
        }

        if (marked == this.getBlackBoardPieces().size()){
            return true;
        }
        return false;
    }

    public void moveWhiteBoardPieceWithAI() {
        Board newGameBoard = new Board(this);
        MinMaxReturnObj minmaxres = minimax(newGameBoard, 4, -1000000, 1000000, true);

        this.getWhiteBoardPieces().clear();
        this.getBlackBoardPieces().clear();


        this.setBoardGrid(minmaxres.getGrid());

        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if (this.getBoardGrid()[i][j] == 1) {
                    this.getWhiteBoardPieces().add(new BoardPiece(1, i, j, this.boardSize));
                } else if (this.getBoardGrid()[i][j] == 2) {
                    this.getBlackBoardPieces().add(new BoardPiece(2, i, j, this.boardSize));
                }
            }
        }



        winnerWhite = checkWinnerWhite();
        if (!winnerWhite) {
            winnerBlack = checkWinnerBlack();
        }

        if (winnerWhite) System.out.println("White won");
        if (winnerBlack) System.out.println("Black won");

        whiteTurn = false;
        blackTurn = true;

    }

//    public MinMaxReturnObj minimax(Board gameBoard, int depth, int  alpha, int beta, boolean maximizingPlayer) {
//        if (depth == 0 || gameBoard.isWinnerBlack() || gameBoard.isWinnerWhite()) {
//            if (gameBoard.isWinnerBlack()) return new MinMaxReturnObj(gameBoard.boardSize, gameBoard.getBoardGrid(), -1000000000);
//            if (gameBoard.isWinnerWhite()) return new MinMaxReturnObj(gameBoard.boardSize, gameBoard.getBoardGrid(), 1000000000);
//            return new MinMaxReturnObj(gameBoard.boardSize, gameBoard.getBoardGrid(), evalFunc(gameBoard.boardGrid));
//        }
//
//        if (maximizingPlayer) {
//
//            int maxCost = -1000000;
//
//            int[][] maxBoardGrid = new int[gameBoard.boardSize][gameBoard.boardSize];
//
//            for (BoardPiece bp : gameBoard.getWhiteBoardPieces()) {
//
//                ArrayList<Integer[]> moveList = gameBoard.getPossibleMoves(bp.getRow(), bp.getColumn());
//
//                if (moveList.size() > 0){
//                    if (moveList.get(0)[0] == -100) continue;
//                }
//
//                for (Integer[] move : moveList) {
//
//                    Board newGameBoard = new Board(gameBoard);
//                    newGameBoard.moveBoardPiece(bp.getRow(), bp.getColumn(), move[0], move[1]);
//
//                    MinMaxReturnObj maxReturnObj = minimax(newGameBoard, depth - 1, alpha, beta, false);
//
//                    int cost = maxReturnObj.getCost();
//
//                    if (maxCost < cost){
//                        maxCost = cost;
//                        maxBoardGrid = newGameBoard.getBoardGrid();
//                    }
//                    alpha = (alpha > maxCost) ? alpha : maxCost;
//                    if (alpha >= beta) break;
//                }
//                if (alpha >= beta) break;
//            }
//            return new MinMaxReturnObj(gameBoard.boardSize, maxBoardGrid, maxCost);
//        } else {
//
//            int minCost = 1000000;
//
//            int[][] minBoardGrid = new int[gameBoard.boardSize][gameBoard.boardSize];
//
//            for (BoardPiece bp : gameBoard.getBlackBoardPieces()) {
//
//                ArrayList<Integer[]> moveList = gameBoard.getPossibleMoves(bp.getRow(), bp.getColumn());
//
//                if (moveList.get(0)[0] == -100) continue;
//
//                for (Integer[] move : moveList) {
//                    Board newGameBoard = new Board(gameBoard);
//
//                    newGameBoard.moveBoardPiece(bp.getRow(), bp.getColumn(), move[0], move[1]);
//
//                    MinMaxReturnObj minReturnObj = minimax(newGameBoard,depth - 1, alpha, beta, true);
//                    int cost = minReturnObj.getCost();
//                    if (minCost > cost){
//                        minCost = cost;
//                        minBoardGrid = newGameBoard.getBoardGrid();
//                    }
//                    beta = (beta < minCost) ? alpha : minCost;
//                    if (beta <= alpha) break;
//                }
//                if (beta <= alpha) break;
//            }
//            return new MinMaxReturnObj(gameBoard.boardSize, minBoardGrid, minCost);
//        }
//    }

    public MinMaxReturnObj minimax(Board gameBoard, int depth, int  alpha, int beta, boolean maximizingPlayer) {
        int cost;
        ArrayList<Board> newGameBoardList = new ArrayList<>();

        if (depth == 0 || gameBoard.isWinnerBlack() || gameBoard.isWinnerWhite()) {
            if (gameBoard.isWinnerBlack())
                return new MinMaxReturnObj(gameBoard.boardSize, gameBoard.getBoardGrid(), -1000000000);
            else if (gameBoard.isWinnerWhite())
                return new MinMaxReturnObj(gameBoard.boardSize, gameBoard.getBoardGrid(), 1000000000);
            else {
                if (boardChecker.containsKey(gameBoard.getBoardGrid())) {
//                    System.out.println();
//                    System.out.println();
//                    System.out.println("Taking value from hashmap");
//                    System.out.println();
//                    System.out.println();
                    cost = boardChecker.get(gameBoard.getBoardGrid());
                    return new MinMaxReturnObj(gameBoard.boardSize, gameBoard.getBoardGrid(), cost);
                } else {
                    cost = evalFunc(gameBoard.getBoardGrid());
                    return new MinMaxReturnObj(gameBoard.boardSize, gameBoard.getBoardGrid(), cost);
                }
            }
        }

        if (maximizingPlayer) {

            int maxCost = -100000000;

            int[][] maxBoardGrid = new int[gameBoard.boardSize][gameBoard.boardSize];

            for (BoardPiece bp : gameBoard.getWhiteBoardPieces()) {

                ArrayList<Integer[]> moveList = gameBoard.getPossibleMoves(bp.getRow(), bp.getColumn());

                if (moveList.size() > 0) {
                    if (moveList.get(0)[0] == -100) continue;
                }

                for (Integer[] move : moveList) {

                    Board newGameBoard = new Board(gameBoard);
                    newGameBoard.moveBoardPiece(bp.getRow(), bp.getColumn(), move[0], move[1]);
                    if (newGameBoard.isWinnerWhite()){
                        return new MinMaxReturnObj(gameBoard.boardSize,newGameBoard.getBoardGrid(),1000000000);
                    }
                    newGameBoardList.add(newGameBoard);
                }
            }

            newGameBoardList.sort(new Comparator<Board>() {
                @Override
                public int compare(Board board1, Board board2) {
                    int board1Cost;
                    int board2Cost;
                    if (boardChecker.containsKey(board1.getBoardGrid())) {
//                        System.out.println();
//                        System.out.println();
//                        System.out.println("Taking value from hashmap");
//                        System.out.println();
//                        System.out.println();
                        board1Cost = boardChecker.get(board1.getBoardGrid());
                    } else {
                        board1Cost = evalFunc(board1.getBoardGrid());
                    }

                    if (boardChecker.containsKey(board2.getBoardGrid())) {
//                        System.out.println();
//                        System.out.println();
//                        System.out.println("Taking value from hashmap");
//                        System.out.println();
//                        System.out.println();
                        board2Cost = boardChecker.get(board2.getBoardGrid());
                    } else {
                        board2Cost = evalFunc(board2.getBoardGrid());
                    }
                    return board2Cost - board1Cost;
                }
            });
            for (Board nb : newGameBoardList) {

                MinMaxReturnObj maxReturnObj = minimax(nb, depth - 1, alpha, beta, false);
                cost = maxReturnObj.getCost();

                if (maxCost < cost) {
                    maxCost = cost;
                    maxBoardGrid = nb.getBoardGrid();
                }

                alpha = (alpha > maxCost) ? alpha : maxCost;
                if (alpha >= beta) break;
            }

            return new MinMaxReturnObj(gameBoard.boardSize, maxBoardGrid, maxCost);
        } else {

            int minCost = 100000000;

            int[][] minBoardGrid = new int[gameBoard.boardSize][gameBoard.boardSize];

            for (BoardPiece bp : gameBoard.getBlackBoardPieces()) {

                ArrayList<Integer[]> moveList = gameBoard.getPossibleMoves(bp.getRow(), bp.getColumn());

                if (moveList.size() > 0) {
                    if (moveList.get(0)[0] == -100) continue;
                }

                for (Integer[] move : moveList) {

                    Board newGameBoard = new Board(gameBoard);
                    newGameBoard.moveBoardPiece(bp.getRow(), bp.getColumn(), move[0], move[1]);
                    if (newGameBoard.isWinnerBlack()){
                        return new MinMaxReturnObj(gameBoard.boardSize,newGameBoard.getBoardGrid(),-1000000000);
                    }
                    newGameBoardList.add(newGameBoard);
                }
            }


            newGameBoardList.sort(new Comparator<Board>() {
                @Override
                public int compare(Board board1, Board board2) {
                    int board1Cost;
                    int board2Cost;
                    if (boardChecker.containsKey(board1.getBoardGrid())) {
//                        System.out.println();
//                        System.out.println();
//                        System.out.println("Taking value from hashmap");
//                        System.out.println();
//                        System.out.println();
                        board1Cost = boardChecker.get(board1.getBoardGrid());
                    } else {
                        board1Cost = evalFunc(board1.getBoardGrid());
                    }

                    if (boardChecker.containsKey(board2.getBoardGrid())) {
//                        System.out.println();
//                        System.out.println();
//                        System.out.println("Taking value from hashmap");
//                        System.out.println();
//                        System.out.println();
                        board2Cost = boardChecker.get(board2.getBoardGrid());
                    } else {
                        board2Cost = evalFunc(board2.getBoardGrid());
                    }
                    return board1Cost - board2Cost;
                }
            });

            for (Board nb : newGameBoardList) {

                MinMaxReturnObj minReturnObj = minimax(nb, depth - 1, alpha, beta, true);
                cost = minReturnObj.getCost();

                if (minCost > cost) {
                    minCost = cost;
                    minBoardGrid = nb.getBoardGrid();
                }

                beta = (beta < minCost) ? beta : minCost;
                if (beta <= alpha) break;
            }

            return new MinMaxReturnObj(gameBoard.boardSize, minBoardGrid, minCost);
        }
    }

    public int evalFunc(int[][] boardGrid){
        int cost = 50*pieceSquareTable(boardGrid,this.boardSize) + 100*connectivity(boardGrid,this.boardSize) - 20*area(boardGrid,this.boardSize) + 300*quad(boardGrid,this.boardSize);
//        System.out.println("Total Cost: " + cost);
        boardChecker.put(boardGrid,cost);
        return cost;
    }

    public int pieceSquareTable(int[][] boardGrid, int boardSize){
        int blackCount = 0;
        int blackSum =  0;
        int whiteCount = 0;
        int whiteSum = 0;

        if (boardSize == 8){
            for (int i=0;i<boardSize;i++){
                for (int j=0;j<boardSize;j++){
                    if (boardGrid[i][j] == 2){
                        blackSum += this.pieceSquareTableForEight[i][j];
                        blackCount++;
                    }
                    if (boardGrid[i][j] == 1){
//                        System.out.println(this.pieceSquareTableForEight[i][j]);
                        whiteSum += this.pieceSquareTableForEight[i][j];
                        whiteCount++;
                    }
                }
            }
        }
        if (boardSize == 6){
            for (int i=0;i<boardSize;i++){
                for (int j=0;j<boardSize;j++){
                    if (boardGrid[i][j] == 2){
                        blackSum += this.pieceSquareTableForSix[i][j];
                        blackCount++;
                    }
                    if (boardGrid[i][j] == 1){
                        whiteSum += this.pieceSquareTableForSix[i][j];
                        whiteCount++;
                    }
                }
            }
        }

//        System.out.println("White PS Value: " + whiteSum + " and White Count: " + whiteCount);
//        System.out.println("Black PS Value: " + blackSum + " and Black Count: " + blackCount);

        return whiteSum - blackSum;
    }

    public int connectivity(int[][] boardGrid, int boardSize){
        int blackConnectivity = 0;
        int blackCount = 0;
        int whiteConnectivity = 0;
        int whiteCount = 0;

        for (int i=0;i<boardSize;i++){
            for (int j=0;j<boardSize;j++){
                if (boardGrid[i][j] == 2){
                    blackCount++;

                    int kStart = (i == 0) ? i : i-1;
                    int kEnd = (i == boardSize-1) ? i : i+1;

                    int lStart = (j == 0) ? j : j-1;
                    int lEnd = (j == boardSize-1) ? j : j+1;

                    for (int k = kStart; k <= kEnd ;k++){
                        for (int l = lStart; l <= lEnd; l++){
                            if (k == i && l == j) continue;
                            if (boardGrid[k][l] == 2){
                                blackConnectivity++;
                            }
                        }
                    }

                }
                if (boardGrid[i][j] == 1){
                    whiteCount++;

                    int kStart = (i == 0) ? i : i-1;
                    int kEnd = (i == boardSize-1) ? i : i+1;

                    int lStart = (j == 0) ? j : j-1;
                    int lEnd = (j == boardSize-1) ? j : j+1;

                    for (int k = kStart; k <= kEnd ;k++){
                        for (int l = lStart; l <= lEnd; l++){
                            if (k == i && l == j) continue;
                            if (boardGrid[k][l] == 1){
                                whiteConnectivity++;
                            }
                        }
                    }
                }
            }
        }

//        System.out.println("White Connectivity: " + whiteConnectivity + " and White Count: " + whiteCount);
//        System.out.println("Black Connectivity: " + blackConnectivity + " and Black Count: " + blackCount);

        return whiteConnectivity - blackConnectivity;
    }

    public int area(int[][] boardGrid, int boardSize){
        int blackStartRow = boardSize-1;
        int blackEndRow = 0;
        int blackStartColumn = boardSize-1;
        int blackEndColumn = 0;
        int blackCount = 0;
        int blackArea = 0;

        int whiteStartRow = boardSize-1;
        int whiteEndRow = 0;
        int whiteStartColumn = boardSize-1;
        int whiteEndColumn = 0;
        int whiteCount =0;
        int whiteArea = 0;

        for (int i=0;i<boardSize;i++){
            for (int j=0;j<boardSize;j++){
                if (boardGrid[i][j] == 2){
                    blackCount++;

                    blackStartRow = (i<blackStartRow) ? i:blackStartRow;
                    blackEndRow = (i>blackEndRow) ? i:blackEndRow;
                    blackStartColumn = (j<blackStartColumn) ? j:blackStartColumn;
                    blackEndColumn = (j>blackEndColumn) ? j:blackEndColumn;
                }

                if (boardGrid[i][j] == 1){
                    whiteCount++;

                    whiteStartRow = (i<whiteStartRow) ? i:whiteStartRow;
                    whiteEndRow = (i>whiteEndRow) ? i:whiteEndRow;
                    whiteStartColumn = (j<whiteStartColumn) ? j:whiteStartColumn;
                    whiteEndColumn = (j>whiteEndColumn) ? j:whiteEndColumn;
                }
            }
        }

        int blackHeight = (Math.abs(blackEndRow-blackStartRow) == 0) ? 1: Math.abs(blackEndRow-blackStartRow) +1;
        int blackWidth = (Math.abs(blackEndColumn-blackStartColumn) == 0) ? 1: Math.abs(blackEndColumn-blackStartColumn) + 1;

        blackArea = blackHeight*blackWidth;

        int whiteHeight = (Math.abs(whiteEndRow-whiteStartRow) == 0) ? 1: Math.abs(whiteEndRow-whiteStartRow) + 1;
        int whiteWidth = (Math.abs(whiteEndColumn-whiteStartColumn) == 0) ? 1: Math.abs(whiteEndColumn-whiteStartColumn) + 1;

        whiteArea = whiteHeight*whiteWidth;

//        System.out.println(whiteStartRow + " " +  whiteEndRow + " " + whiteStartColumn + " " + whiteEndColumn);
//        System.out.println("White Area: " + whiteArea + " and White Count: " + whiteCount);
//        System.out.println(blackStartRow + " " +  blackEndRow + " " + blackStartColumn + " " + blackEndColumn);
//        System.out.println("Black Area: " + blackArea + " and Black Count: " + blackCount);

        return whiteArea - blackArea;

    }

    public int quad(int[][] boardGrid, int boardSize){
        int whiteCenterOfMassX = 0;
        int whiteCenterOfMassY = 0;
        int whiteCount = 0;

        int blackCenterOfMassX = 0;
        int blackCenterOfMassY = 0;
        int blackCount = 0;

        for (int i=0; i<boardSize; i++){
            for (int j=0; j<boardSize; j++){
                if (boardGrid[i][j] == 2){
                    blackCenterOfMassX += i;
                    blackCenterOfMassY += j;
                    blackCount++;
                }
                if (boardGrid[i][j] == 1){
                    whiteCenterOfMassX += i;
                    whiteCenterOfMassY += j;
                    whiteCount++;
                }
            }
        }


        blackCenterOfMassX = (int)Math.round(blackCenterOfMassX/(blackCount*1.0));
        blackCenterOfMassY = (int)Math.round(blackCenterOfMassY/(blackCount*1.0));
        whiteCenterOfMassX = (int)Math.round(whiteCenterOfMassX/(whiteCount*1.0));
        whiteCenterOfMassY = (int)Math.round(whiteCenterOfMassY/(whiteCount*1.0));

        int iStart = (blackCenterOfMassX-2 < 0) ? 0 : blackCenterOfMassX-2;
        int iEnd = (blackCenterOfMassX+2 > boardSize-1) ? boardSize-1 : blackCenterOfMassX+2;

        int jStart = (blackCenterOfMassY-2 < 0) ? 0 : blackCenterOfMassY-2;
        int jEnd = (blackCenterOfMassY+2 > boardSize-1) ? boardSize-1 : blackCenterOfMassY+2;

//        System.out.println("Black Quad Check Starts: " + iStart + "," + jStart);
//        System.out.println("Black Quad Check Ends: " + iEnd + "," + jEnd);

        int blackQuadCount = 0;
        int blackQuad = 0;

        for (int i = iStart; i < iEnd ;i++){
            for (int j = jStart; j < jEnd; j++){
                if (boardGrid[i][j] == 2) blackQuadCount++;
                if (boardGrid[i+1][j] == 2) blackQuadCount++;
                if (boardGrid[i][j+1] == 2) blackQuadCount++;
                if (boardGrid[i+1][j+1] == 2) blackQuadCount++;

                if (blackQuadCount == 4 || blackQuadCount == 3){
//                    System.out.println(i + "," + j);
                    blackQuad++;
                }

                blackQuadCount = 0;
            }
        }

        int kStart = (whiteCenterOfMassX-2 < 0) ? 0 : whiteCenterOfMassX-2;
        int kEnd = (whiteCenterOfMassX+2 > boardSize-1) ? boardSize-1 : whiteCenterOfMassX+2;

        int lStart = (whiteCenterOfMassY-2 < 0) ? 0 : whiteCenterOfMassY-2;
        int lEnd = (whiteCenterOfMassY+2 > boardSize-1) ? boardSize-1 : whiteCenterOfMassY+2;

//        System.out.println("White Quad Check Starts: " + kStart + "," + lStart);
//        System.out.println("White Quad Check Ends: " + kEnd + "," + lEnd);

        int whiteQuadCount = 0;
        int whiteQuad = 0;

        for (int k = kStart; k < kEnd ;k++){
            for (int l = lStart; l < lEnd; l++){
                if (boardGrid[k][l] == 1) whiteQuadCount++;
                if (boardGrid[k+1][l] == 1) whiteQuadCount++;
                if (boardGrid[k][l+1] == 1) whiteQuadCount++;
                if (boardGrid[k+1][l+1] == 1) whiteQuadCount++;

                if (whiteQuadCount == 4 || whiteQuadCount == 3){
                    whiteQuad++;
                }
                whiteQuadCount = 0;
            }
        }

//        System.out.println("White CoM: " + whiteCenterOfMassX + "," + whiteCenterOfMassY);
//        System.out.println("White Quad: " + whiteQuad + " and White Count: " + whiteCount);
//        System.out.println("Black CoM: " + blackCenterOfMassX + "," + blackCenterOfMassY);
//        System.out.println("Black Quad: " + blackQuad + " and Black Count: " + blackCount);

        return whiteQuad - blackQuad;
    }

//    public static void main(String[] args) {
//        int[][] checker8 = new int[][]{{0,0,2,2,0,0,0,0},
//                                        {0,0,1,2,1,0,0,0},
//                                        {0,0,1,2,2,0,0,0},
//                                        {0,0,0,0,2,2,2,0},
//                                        {0,0,0,0,1,0,2,1},
//                                        {0,0,0,0,0,1,2,0},
//                                        {1,0,0,1,0,0,0,2},
//                                        {0,0,0,0,0,0,0,0}};
//
//
//
//        Board gameBoard = new Board(8);
//
//        gameBoard.evalFunc(checker8);
//    }
}


