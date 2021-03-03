package HomeWorkLesson3;

import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // массив слов
        String [] arrayWords = {
                "яблоко",
                "груша",
                "киви",
                "банан",
                "яблоко",
                "апельсин",
                "лимон",
                "банан",
                "мандарин",
                "киви",
                "груша"};
        System.out.println(Arrays.toString(arrayWords));
        HashMap <String, Integer> hashMap = new HashMap<>();
        for (String words: arrayWords){
            hashMap.put(words, hashMap.getOrDefault(words, 0) + 1);
        }
        System.out.println(hashMap);

        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Иванов", +7917456781L);
        phoneBook.add("Петров", +7927789921L);
        phoneBook.add("Петров", +7904036984L);
        phoneBook.add("Сидоров", +7950521983L, +7927712935L);
        phoneBook.add("Иванов", +79066345128L);

        System.out.println("Тел: " + phoneBook.get("Иванов"));
        System.out.println("Тел: " + phoneBook.get("Сидоров"));
    }
}
