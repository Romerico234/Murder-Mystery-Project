public class GameState {
    public final static int DISPLAY_WIDTH = 100;
    public final static int TOTAL_NO_OF_ITEMS = 4;
    public final int SAFE_CODE = 10211225;
    private boolean running;
    private Location[][] grid;
    private Location currentLocation;

    public GameState(boolean running, int rows, int cols) {
        this.running = running;
        this.grid = new Location[rows][cols];
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Location[][] getGrid() {
        return grid;
    }

    public void setLocationInGrid(int row, int col, Location location) {
        grid[row][col] = location;
    }

    public Location getLocationFromGrid(int row, int col) {
        return grid[row][col];
    }
}
