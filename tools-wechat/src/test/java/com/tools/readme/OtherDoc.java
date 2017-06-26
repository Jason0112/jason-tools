package com.tools.readme;

/**
 * date: 2017/6/17
 * description :
 *
 * @author : zhencai.cheng
 */
public class OtherDoc {
    /**
     1.【强制】在使用正则表达式时，利用好其预编译功能，可以有效加快正则匹配速度。

     说明:不要在方法体内定义:Pattern pattern = Pattern.compile(规则);

     2.【强制】注意 Math.random() 这个方法返回是 double 类型，注意取值的范围 0≤x<1(能够 取到零值，注意除零异常)，
     如果想获取整数类型的随机数，不要将 x 放大 10 的若干倍然后 取整，直接使用 Random 对象的 nextInt 或者 nextLong 方法。

     3.【强制】获取当前毫秒数 System.currentTimeMillis(); 而不是 new Date().getTime();
     说明:如果想获取更加精确的纳秒级时间值，System.nanoTime()的方式。
     在 JDK8 中， 针对统计时间等场景，推荐使用 Instant 类。

     4.【推荐】不要在视图模板中加入任何复杂的逻辑。
     说明:根据 MVC 理论，视图的职责是展示，不要抢模型和控制器的活。

     5.【推荐】任何数据结构的构造或初始化，都应指定大小，避免数据结构无限增长吃光内存。

     6.【推荐】对于“明确停止使用的代码和配置”，如方法、变量、类、配置文件、动态配置属性等要坚决从程序中清理出去，避免造成过多垃圾。
     */
}
