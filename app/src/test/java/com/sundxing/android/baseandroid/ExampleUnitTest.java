package com.sundxing.android.baseandroid;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        A b = new B();
        System.out.println(b.getName());
        Map<String,String> map = new HashMap<>();
        map.put("1", "2");
        map.put("a", "b");

        Iterator iter = map.keySet().iterator();
        System.out.println(map.size());
        while (iter.hasNext()) {

            map.remove(iter.next());
        }
        System.out.println(map.size());
    }

    public static class A {
        String getName() {
            return getClass().getName();
        }
    }

    public static class B extends A {

    }
}