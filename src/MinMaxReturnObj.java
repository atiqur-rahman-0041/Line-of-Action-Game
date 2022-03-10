public class MinMaxReturnObj {
    int gridSize;
    int[][] grid;
    int cost;

    public MinMaxReturnObj(int gridSize, int[][] grid, int cost) {
        this.gridSize = gridSize;
        this.grid = new int[gridSize][gridSize];
        this.grid = grid;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public int[][] getGrid() {
        return grid;
    }

}
