public class Common {

    public static int convertCoordXStr(String coordXStr) {
        switch (coordXStr) {
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "D":
                return 4;
            case "E":
                return 5;
            case "F":
                return 6;
            case "G":
                return 7;
            case "H":
                return 8;
            case "I":
                return 9;
            default:
                return -1;
        }
    }

    public static String convertCoordX(int coordX) {
        switch (coordX) {
            case 1:
                return "A";
            case 2:
                return "B";
            case 3:
                return "C";
            case 4:
                return "D";
            case 5:
                return "E";
            case 6:
                return "F";
            case 7:
                return "G";
            case 8:
                return "H";
            case 9:
                return "I";
            default:
                return "";
        }
    }

    public static boolean playerWon(Square player1, Square player2) {
        int coordinateY = player1.getCoordinateY();
        int coordinateY2 = player2.getCoordinateY();
        if(coordinateY == 9  || coordinateY2 == 1) return true;
        return false;
    }
}
