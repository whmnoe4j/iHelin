package me.ianhe.test;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

/**
 * @author iHelin
 * @create 2017-04-15 10:07
 */
public class GuavaTest {

    public static void main(String[] args) {
        int[] ints = {10, 20, 30, 9, 40, 80};
        System.out.println(Ints.max(ints));
        List<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(3);
        list.add(2);
        ints = Ints.toArray(list);
        System.out.println(Ints.max(ints));
        System.out.println(StringUtils.capitalize("string"));
        System.out.println(StringUtils.isNumeric("12355.12"));
        System.out.println(StringUtils.left("string", 3));
        System.out.println(StringUtils.countMatches("abcdaaa", 'a'));//4
        System.out.println(StringUtils.countMatches("abcdaaa", "ab"));//1
        System.out.println(RandomStringUtils.randomAlphabetic(4));
        System.out.println(RandomStringUtils.randomAlphanumeric(6));
        System.out.println(RandomStringUtils.randomNumeric(4));
        System.out.println(RandomStringUtils.random(4, false, true));
        System.out.println(WordUtils.capitalize("abc def"));//Abc Def
    }

}
