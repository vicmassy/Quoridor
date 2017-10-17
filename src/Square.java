public class Square {

    private final String coordXStr;
    private final int coordX;
    private final int coordY;
    private final int unaryCoord;

    public Square(int coordX, int coordY) {
        this.coordXStr = Common.convertCoordX(coordX);
        this.coordX = coordX;
        this.coordY = coordY;
        unaryCoord = (coordY-1)*9+coordX-1;
    }

    public Square(Square s) {
        this.coordXStr = s.coordXStr;
        this.coordX = s.getCoordinateX();
        this.coordY = s.getCoordinateY();
        this.unaryCoord = s.getUnaryCoord();
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

    @Override
    public String toString() {
        return coordXStr+coordY;
    }
}
