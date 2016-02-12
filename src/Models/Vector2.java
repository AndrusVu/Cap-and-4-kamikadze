package Models;


/**
 * Simple vector like class.
 */
public class Vector2 {
    private double x;
    private double y;

    public Vector2(){

    }

    public Vector2(Vector2 toClone){
        this.x = toClone.getX();
        this.y = toClone.getY();
    }

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public static Vector2 add(Vector2 vec1, Vector2 vec2){
        return new Vector2(vec1.x + vec2.x, vec1.y + vec2.y);
    }

    public static Vector2 subtract(Vector2 vec1, Vector2 vec2){
        return new Vector2(vec1.x - vec2.x, vec1.y - vec2.y);
    }

    public static Vector2 neg(Vector2 vec) {
        return new Vector2(-vec.x, -vec.y);
    }

    public static double distance(Vector2 vec1, Vector2 vec2){
        Vector2 projection = new Vector2(Math.abs(vec1.x - vec2.x), Math.abs(vec1.y - vec2.y));
        return abs(projection);
    }

    public static Vector2 normalize(Vector2 vec){
        double length = abs(vec);
        return new Vector2(vec.x/length, vec.y/length);
    }

    public static double abs(Vector2 vec){
        return Math.sqrt(vec.x*vec.x + vec.y*vec.y);
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void shiftX(double x){
        this.x += x;
    }

    public void shiftY(double y){
        this.y += y;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }
}
