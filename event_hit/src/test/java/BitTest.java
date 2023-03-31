
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author weizhenqiang
 * @date 2023/3/28 15:21
 */
public class BitTest {
    @Test
    public void test01() {
        for (int i = 0; i < 10; i++) {
//            intersection2(array[i]);
            weiyunsuan3(10000);
        }
    }

    public static void weiyunsuan3(int length) {
        StringBuffer strB = new StringBuffer();
        for (int i = 0; i < length; i++) {
            strB.append(System.currentTimeMillis() % 2);
        }

        StringBuffer strB2 = new StringBuffer();
        for (int i = 0; i < length; i++) {
            strB2.append(System.currentTimeMillis() % 2);
        }
        System.out.println("list 位运算 单线程 strB：" + strB.toString());
        System.out.println("list 位运算 单线程 strB2：" + strB2.toString());

        BigInteger bi1 = new BigInteger(strB.toString(), 2);
        BigInteger bi2 = new BigInteger(strB2.toString(), 2);

        long start = System.nanoTime();
        BigInteger result = bi1.and(bi2);
        long end = System.nanoTime();
        System.out.println("list 位运算 单线程 消耗时间：" + (end - start) + "纳秒");
        System.out.println("Result of bitwise AND: " + result);
    }

    @Test
    public void test2() {
        int[] array = {10, 100, 200, 1000, 5000, 10000, 50000, 100000};
        for (int i = 0; i < array.length; i++) {
//            intersection2(array[i]);
            intersection2(10000);
        }

    }

    public static void intersection2(int length) {
        int size = length / 64 +(length%64>0?1:0);
        Long[] list1 = new Long[size];
        for (int i = 0; i < size; i++) {
            list1[i]=new Random().nextLong();
        }

        Long[] list2 = new Long[size];
        for (int i = 0; i < size; i++) {
            list2[i]=new Random().nextLong();
        }

        Long[] list3 = new Long[size];
        System.out.println("数量："+length+", 队列1数量：" + size + ", 队列2数量：" + size);
        long start = System.nanoTime();
        for (int i = 0 ;i < list1.length; i++){
            list3[i]= list1[i]&list2[i];
        }
        long end = System.nanoTime();
        System.out.println("list 位运算 单线程 消耗时间："  + (end - start)+"纳秒");

    }


    @Test
    public void test03(){
        int num = 1315; // 待处理的整数

        int bitCount = Integer.toBinaryString(num).length(); // 获取 num 中二进制下的位数（包括高位的 0）
        int highestOneBit = 1 << (bitCount - 1); // 获取最高位的标志位，该标志位的二进制表示中仅有最高位为 1

        int result = num & highestOneBit; // 将待处理的整数与最高位的标志位进行按位与运算

        System.out.println(Integer.numberOfTrailingZeros(highestOneBit) + " " + result); // 输出 10 1024，表示数字1315中最高有效位的下标是10，该位的值是1024
    }


    @Test
    public void test04(){
        long num = -994746288L; // 待转换的 long 类型数值
        String binary = Long.toBinaryString(num); // 将 long 类型数值转换为二进制字符串
        System.out.println(binary); // 输出二进制字符串
    }

    @Test
    public void test05(){
        BigInteger bigInteger = new BigInteger("1000000000000000000000000000000000000000000000000000000000000011", 2);
        Long i  = bigInteger.longValue();
        System.out.println(i);
    }




}
