public class Tuple<T, U, P> {
    public final T _1;
    public final U _2;
    public final P _3;

    public Tuple(T arg1, U arg2, P arg3) {
        super();
        this._1 = arg1;
        this._2 = arg2;
        this._3 = arg3;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", _1, _2, _3);
    }
}
