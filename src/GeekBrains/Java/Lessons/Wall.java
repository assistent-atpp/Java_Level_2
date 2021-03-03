package GeekBrains.Java.Lessons;

public class Wall implements Obstacle{
    private int height;

    public Wall (int height){
        this.height = height;
    }

    @Override
    public void doIt(Competitor c) {
        c.jump(height);
    }
}
