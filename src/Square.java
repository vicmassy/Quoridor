public class Square {

    private final String coordXStr;
    private final int coordX;
    private final int coordY;
    private final int unaryCoord;
    private boolean filled;

    public Square(int coordX, int coordY) {
        this.coordXStr = Common.convertCoordX(coordX);
        this.coordX = coordX;
        this.coordY = coordY;
        unaryCoord = (coordY-1)*9+coordX-1;
        filled = false;
    }

    public int getCoordinateX() {
        return coordX;
    }

    public int getCoordinateY() {
        return coordY;
    }

    public int getUnaryCoord() {
        return unaryCoord;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled() {
        filled = true;
    }

    @Override
    public String toString() {
        return coordXStr+coordY;
    }
}
