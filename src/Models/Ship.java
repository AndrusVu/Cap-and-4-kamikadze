package Models;

public class Ship {
    private Vector2 position;
    private Vector2 direction;

    public Ship(){
        position = new Vector2();
        direction = new Vector2();
    }

    public void setPosition(double x, double y){
        position.setX(x);
        position.setY(y);
    }

    public void setDirection(double x, double y){
        direction.setX(x);
        direction.setY(y);
    }

    public Vector2 getPosition(){
        return position;
    }

    public Vector2 getDirection(){
        return direction;
    }

    public void shiftPosition(double x, double y){
        position.shiftX(x);
        position.shiftY(y);
    }
}
