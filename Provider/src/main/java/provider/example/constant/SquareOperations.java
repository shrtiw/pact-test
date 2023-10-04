package provider.example.constant;

public enum SquareOperations {

    PERIMETER() {
        @Override
        public double getResult(double side) {
            return 4 * side;
        }
    },
    AREA() {
        @Override
        public double getResult(double side) {
            return side * side;
        }
    };

    public abstract double getResult(double side);
}
