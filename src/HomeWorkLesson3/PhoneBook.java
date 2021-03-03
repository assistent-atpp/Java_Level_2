package HomeWorkLesson3;

import java.util.*;

public class PhoneBook {
    private HashMap<String, Set<Long>> phoneBook = new HashMap<>();
    // метод добавления персонажа в справочник - имя и один номер
    public  void add (String name, Long phone){
        Set <Long> telephone = phoneBook.getOrDefault(name, new HashSet<>());
        telephone.add(phone);
        phoneBook.put(name, telephone);
    }
    // метод добавления персонажа в справочник - имя и неколько номеров
    public void add (String name, Long ... phones){
        Set <Long> telephone = phoneBook.getOrDefault(name, new HashSet<>());
        telephone.addAll(Arrays.asList(phones));
        phoneBook.put(name, telephone);
    }

    public Set <Long> get (String name){
        return Collections.unmodifiableSet(phoneBook.get(name));
    }
}
