package uet.oop.bomberman.entities;

public class Coordinate {
    double x;
    double y;

    public Coordinate() {
        x = 0;
        y = 0;
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate add(Coordinate c) {
        return new Coordinate(this.x + c.x, this.y + c.y);
    }

    public void sub(Coordinate c) {
        this.x -= c.x;
        this.y -= c.y;
    }

    public void addY(double c) {
        this.y += c;
    }

    public void addX(double c) {
        this.y += c;
    }

    public void setTo(Coordinate c) {
        this.x = c.x;
        this.y = c.y;
    }

    public boolean equalTo(Coordinate c) {
        return (x == c.x && y == c.y);
    }

    public void multiple(Coordinate c) {
        this.x *= c.x;
        this.y *= c.y;
    }

    public Coordinate multiple(double c) {
        return new Coordinate(this.x * c, this.y * c);
    }
}
