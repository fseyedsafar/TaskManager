package com.example.task.repository;

import com.example.task.model.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    public static final PersonRepository instance = new PersonRepository();
    private List<Person> mPerson = new ArrayList<>();

    private PersonRepository() {
    }

    public static PersonRepository getInstance() {
        return instance;
    }

    public void insert(Person person) {
        mPerson.add(person);
    }

    public int search(String user, String pass) {
        int result = 0;
        for (int i = 0; i < mPerson.size(); i++) {
            if (user.equals(mPerson.get(i).getmUser()) && (pass.equals(mPerson.get(i).getmPass()))) {
                result = 1;
            } else if (user.equals(mPerson.get(i).getmUser())) {
                result = 2;
            } else if (pass.equals(mPerson.get(i).getmPass())) {
                result = 3;
            }
        }
        return result;
    }

    public Person getPerson(String user, String pass) {
        Person person = null;
        for (int i = 0; i < mPerson.size(); i++) {
            if (user.equals(mPerson.get(i).getmUser()) && (pass.equals(mPerson.get(i).getmPass()))) {
                person = mPerson.get(i);
            }
        }
        return person;
    }
}
