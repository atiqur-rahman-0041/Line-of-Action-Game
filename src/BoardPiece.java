public class BoardPiece {
    int color;
    int xPosition;
    int yPosition;
    int row;
    int column;
    boolean isMarked;
    int boardSize;

    public BoardPiece(int color, int row, int column, int boardSize){
        this.color = color;
        this.row = row;
        this.column = column;
        this.boardSize = boardSize;

        this.isMarked = false;

        if (boardSize == 8){
            this.yPosition = 130+ 80*row + 15;
            this.xPosition = 80 + 80*column + 15;
        }

        if (boardSize == 6){
            this.yPosition = 210 + 80*row + 15;
            this.xPosition = 160 + 80*column + 15;
        }
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
        if (boardSize == 8){
            this.yPosition = 130+ 80*row + 15;
        }

        if (boardSize == 6){
            this.yPosition = 210 + 80*row + 15;
        }
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
        if (boardSize == 8){
            this.xPosition = 80 + 80*column + 15;
        }

        if (boardSize == 6){
            this.xPosition = 160 + 80*column + 15;
        }
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        this.isMarked = marked;
    }
}
