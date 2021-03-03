package GeekBrains.Java.Lessons;

public class Cross implements Obstacle{
    private int dist;

    public Cross (int dist){
        this.dist = dist;
    }

    @Override
    public void doIt(Competitor c) {
        c.run(dist);
    }
}
