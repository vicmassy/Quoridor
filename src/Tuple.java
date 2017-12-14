public class Tuple<T, U, P, R> {
    public  T _1;
    public  U _2;
    public  P _3;
    public  R _4;

    public Tuple(T arg1, U arg2, P arg3, R arg4) {
        super();
        this._1 = arg1;
        this._2 = arg2;
        this._3 = arg3;
        this._4 = arg4;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s, %s)", _1, _2, _3, _4);
    }

}
