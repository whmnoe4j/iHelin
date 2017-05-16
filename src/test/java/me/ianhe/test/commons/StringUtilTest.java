package me.ianhe.test.commons;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;

/**
 * @author iHelin
 * @since 2017/5/12 14:36
 */
public class StringUtilTest {

    /**
     * 字符串测试
     *
     * @author iHelin
     * @since 2017/5/12 14:37
     */
    @Test
    public void stringUtilTest() {
        //首字母大写
        System.out.println(StringUtils.capitalize("string"));
        //是否为数字
        System.out.println(StringUtils.isNumeric("12355.12"));
        //截取
        System.out.println(StringUtils.left("string", 3));
        //某字符或字符串出现次数
        System.out.println(StringUtils.countMatches("abcdaaa", 'a'));//4
        System.out.println(StringUtils.countMatches("abcdaaa", "ab"));//1
        //随机字母
        System.out.println(RandomStringUtils.randomAlphabetic(4));
        //随机字母或者数字
        System.out.println(RandomStringUtils.randomAlphanumeric(6));
        //随机数字，可用于手机验证码等
        System.out.println(RandomStringUtils.randomNumeric(4));
        System.out.println(RandomStringUtils.random(4, false, true));
    }

    /**
     * WordUtils测试
     *
     * @author iHelin
     * @since 2017-05-10 16:50
     */
    @Test
    public void randomTest() {
        //首字母大写
        System.out.println(WordUtils.capitalize("i am FINE"));//I Am FINE
        //首字母大写，并把其余的改为小写
        System.out.println(WordUtils.capitalizeFully("i am FINE"));//I Am Fine
        //取首字母
        System.out.println(WordUtils.initials("Ben John lee"));//BJL
        //取反操作:大写小写转换
        System.out.println(WordUtils.swapCase("The dog has a BONE"));//tHE DOG HAS A bone
        //首字母小写
        System.out.println(WordUtils.uncapitalize("I Am FInE"));//i am fInE
        //加换行符
        System.out.println(WordUtils.wrap("1234dwd",2));
    }
}
