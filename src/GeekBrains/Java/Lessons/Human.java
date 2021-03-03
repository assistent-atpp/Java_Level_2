package GeekBrains.Java.Lessons;

public class Human implements Competitor{
    private String name;
    private int maxRunDistance;
    private int maxJumpHeight;
    private boolean onDistance;

    public Human (String name, int maxRunDistance, int maxJumpHeight){
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
        this.onDistance = true;
    }

    public Human (String name){
        this(name, 1000, 1);
    }

    @Override
    public void run(int dist) {
        if (dist <= maxRunDistance){
            System.out.println(name + " успешно пробежал " + dist + " метров");
        } else {
            System.out.println(name + " не смог пробежать " + dist + " метров");
            onDistance = false;
        }
    }

    @Override
    public void jump(int height) {
        if (height <= maxJumpHeight){
            System.out.println(name + " успешно прыгнул " + height + " метров");
        } else {
            System.out.println(name + " не смог прыгнуть " + height + " метров");
        }
    }

    @Override
    public boolean isOnDistance() {
        return onDistance;
    }

    @Override
    public void info() {
        System.out.println(name + " " + onDistance);
    }
}

