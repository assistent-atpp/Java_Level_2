package HomeWorkLesson2;

public class Main {
    static int size = 4;
    public static void main(String[] args) {
        // массив строк
        String[][] stringArray = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11g", "12"},
                {"13", "14", "15", "16"}
        };
        try {
            System.out.println("sum = " + getSum(stringArray));
        } catch (MyArraySizeException e) {
            System.out.println(e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println(e.getMessage());
//            System.out.println(e.getCol() + " " + e.getRow());
            System.out.println(stringArray[e.getCol()][e.getRow()]);
        }
    }
    // метод, возвращающий сумму элементов массива в случае отсутствия исключений
    public static int getSum (String[][]stringArray) throws MyArraySizeException, MyArrayDataException {
        if (stringArray.length != size) {
            throw new MyArraySizeException();
        }
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray.length != size) {
                throw new MyArraySizeException();
            }
        }
        int sum = 0;
        for (int i = 0; i < stringArray.length; i++) {
            for (int j = 0; j < stringArray[i].length; j++) {
                try {
                    sum += Integer.parseInt(stringArray[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("format " + i + " " + j, i, j);
                }
            }
        }
        return sum;
    }
}

