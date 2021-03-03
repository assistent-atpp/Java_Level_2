package HomeWorkLesson5;

public class Main {
    private static final int SIZE = 10_000_000;
    private static final int HALF = SIZE / 2;
    private static final int PART_FIRST = 0;
    private static final int PART_SECOND = 1;
    private static float[] array = new float[SIZE];

    public static void main(String[] args) {
        System.out.println("method1: " + method1() + " millis");
        try {
            System.out.println("method1: " + method2() + " millis");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // метод №1 - расчёт времени вычислений в одном потоке
    public static long method1() {
        for (int i = 0; i < array.length; i++){
            array[i] = 1;
        }
        long time = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++){
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return System.currentTimeMillis() - time;
    }

    // метод №2 - расчёт времени вычислений в двух потоках
    public static long method2() throws InterruptedException {
        for (int i = 0; i < array.length; i++){
            array[i] = 1;
        }
        long time = System.currentTimeMillis();
        float [][] partsArray = prepareParts();
        // разделение двумерного массива halfArray на два массива
        System.arraycopy(array, 0, partsArray[PART_FIRST], 0, HALF);
        System.arraycopy(array, HALF, partsArray[PART_SECOND], 0, HALF);

        Thread thread = new Thread(() -> fillArray(partsArray[PART_SECOND], HALF));
        thread.start();
        fillArray(partsArray[PART_FIRST], 0);
        thread.join();
        // соединение двух частей массива halfArray в один массив
        System.arraycopy(partsArray[PART_FIRST], 0, array, 0, HALF);
        System.arraycopy(partsArray[PART_SECOND], 0, array, HALF, HALF);
        return System.currentTimeMillis() - time;
    }
    // метод подготовки двух половин массива
    public static float [][] prepareParts() {
        return new float[][] {new float[HALF], new float[HALF]};
    }
    // метод заполнения массива значениями по формуле
    public static void fillArray (float[] array, int startPosition) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + (i + startPosition) / 5) * Math.cos(0.2f + (i + startPosition) / 5)
                    * Math.cos(0.4f + (i + startPosition) / 2));
        }
    }
}
