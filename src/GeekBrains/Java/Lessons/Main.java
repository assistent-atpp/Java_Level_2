package GeekBrains.Java.Lessons;

public class Main {

    public static void main(String[] args) {
        // массив конкурентов
        Competitor [] competitors = {
                new Human("Vasiliy"),
                new Cat("Tutik"),
                new Robot("T1000"),
                new Cat("Kotik", 200, 3),
                new Human("Iziy", 5000, 5)
        };
        // массив препятствий бег и стена
        Obstacle [] obstacles = {
                new Cross(400),
                new Wall(1)
        };
        // вложенный цикл опроса выполнения действий конкурентов по препятствиям
        for (Competitor c : competitors) {
            for (Obstacle o : obstacles) {
                o.doIt(c);
                if (!c.isOnDistance()){
                    break;
                }
            }
        }
        // цикл вывода инфы по статусу конкурентов
        for (Competitor competitor : competitors) {
            competitor.info();
        }
    }
}