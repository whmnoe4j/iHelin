package me.ianhe.spring.cache;

import me.ianhe.model.Person;
import org.junit.Test;
import org.springframework.cache.annotation.Cacheable;

import static org.junit.Assert.assertEquals;

/**
 * Created by iHelin on 17/5/10 15:48.
 */
public class CacheableTest {

    public Person findAnotherEmployee(String surname, int age) {
        return new Person(surname, age);
    }

    @Cacheable(value = "person", key = "#surname")
    public Person findEmployeeBySurname(String surname, int age) {
        System.out.println(surname + "   " + age);
        return new Person(surname, age);
    }

    @Test
    public void testCache() {
        Person employee1 = this.findEmployeeBySurname("Smith", 33);
        Person employee2 = this.findEmployeeBySurname("Smith", 33);
        assertEquals(employee1, employee2);
    }

}
