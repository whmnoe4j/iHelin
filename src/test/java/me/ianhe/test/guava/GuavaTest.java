package me.ianhe.test.guava;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;

import java.util.List;

/**
 * @author iHelin
 * @create 2017-04-15 10:07
 */
public class GuavaTest {

    /**
     * Ints工具类
     *
     * @author iHelin
     * @since 2017-05-10 15:30
     */
    @Test
    public void intsTest() {
        int[] ints = {10, 20, 30, 9, 40, 80};
        System.out.println(Ints.max(ints));
        List<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(3);
        list.add(2);
        ints = Ints.toArray(list);
        System.out.println(Ints.max(ints));
    }

}
