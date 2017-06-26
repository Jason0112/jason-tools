package com.tools.readme;

/**
 * date: 2017/6/17
 * description :
 *
 * @author : zhencai.cheng
 */
public class CollectionDoc {
    /**
     1.【强制】关于 hashCode 和 equals 的处理，遵循如下规则:
     1) 只要重写equals，就必须重写hashCode。
     2) 因为Set存储的是不重复的对象，依据hashCode和equals进行判断，所以Set存储的 对象必须重写这两个方法。
     3) 如果自定义对象做为Map的键，那么必须重写hashCode和equals。
     说明:String 重写了 hashCode 和 equals 方法，所以我们可以非常愉快地使用 String 对象 作为 key 来使用。

     2.【强制】ArrayList的subList结果不可强转成ArrayList，否则会抛出ClassCastException 异常:java.util.RandomAccessSubList cannot be cast to java.util.ArrayList ;
     说明:subList 返回的是 ArrayList 的内部类 SubList，并不是 ArrayList ，而是 ArrayList 的一个视图，对于SubList子列表的所有操作最终会反映到原列表上。

     3.【强制】 在 subList 场景中，高度注意对原集合元素个数的修改，会导致子列表的遍历、增 加、删除均产生ConcurrentModificationException 异常。

     4.【强制】使用集合转数组的方法，必须使用集合的toArray(T[] array)，传入的是类型完全 一样的数组，大小就是 list.size()。
     说明:使用 toArray 带参方法，入参分配的数组空间不够大时，toArray 方法内部将重新分配 内存空间，并返回新数组地址;如果数组元素大于实际所需，
     下标为[ list.size() ]的数组 元素将被置为 null，其它数组元素保持原值，因此最好将方法入参数组大小定义与集合元素 个数一致。

     正例:
     List<String> list = new ArrayList<String>(2);
     list.add("guan");
     list.add("bao");
     String[] array = new String[list.size()];
     array = list.toArray(array);

     反例:直接使用 toArray 无参方法存在问题，此方法返回值只能是 Object[]类，若强转其它 类型数组将出现 ClassCastException 错误。

     5.【强制】使用工具类 Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方 法，它的 add/remove/clear 方法会抛出 UnsupportedOperationException 异常。
     说明:asList 的返回对象是一个 Arrays 内部类，并没有实现集合的修改方法。Arrays.asList 体现的是适配器模式，只是转换接口，后台的数据仍是数组。
     String[] str = new String[] { "a", "b" };
     List list = Arrays.asList(str);

     第一种情况:list.add("c");
     第二种情况:str[0] = "gujin"; 那么list.get(0)也会随之修改。

     6.【强制】泛型通配符<? extends T>来接收返回的数据，此写法的泛型集合不能使用add方 法，而<? super T>不能使用get方法，做为接口调用赋值时易出错。
     说明:扩展说一下PECS(Producer Extends Consumer Super)原则:
     1)频繁往外读取内容 的，适合用上界 Extends。
     2)经常往里插入的，适合用下界 Super。

     7.【强制】不要在 foreach 循环里进行元素的 remove/add 操作。remove 元素请使用 Iterator

     方式，如果并发操作，需要对 Iterator 对象加锁。
     List<String> a = new ArrayList<String>();
     a.add("1");
     a.add("2");
     正例:
     Iterator<String> it = a.iterator();
     while (it.hasNext()) {
     String temp = it.next();
     if ("1".equals(temp)) {
     it.remove();
     }
     }

     反例:
     for (String temp : a) {
     if ("1".equals(temp)) {
     a.remove(temp);
     }
     }

     说明:以上代码的执行结果肯定会出乎大家的意料，那么试一下把“1”换成“2”，会是同样的 结果吗?

     8.【强制】 在 JDK7 版本及以上，Comparator 要满足如下三个条件，不然 Arrays.sort， Collections.sort 会报 IllegalArgumentException 异常。
     说明:
     1) x，y的比较结果和y，x的比较结果相反。
     2) x>y，y>z，则x>z。
     3) x=y，则x，z比较结果和y，z比较结果相同。

     反例:下例中没有处理相等的情况，实际使用中可能会出现异常:
     new Comparator<Student>() {

    @Override public int compare(Student o1, Student o2) {

    return o1.getId() > o2.getId() ? 1 : -1; }

    };

     9.【推荐】集合初始化时，指定集合初始值大小。

     说明:HashMap使用HashMap(int initialCapacity) 初始化，
     正例:initialCapacity = (需要存储的元素个数 / 负载因子) + 1。注意负载因子(即 loader factor)默认为 0.75，如果暂时无法确定初始值大小，请设置为 16。
     反例:HashMap 需要放置 1024 个元素，由于没有设置容量初始大小，随着元素不断增加，容 量 7 次被迫扩大，resize 需要重建 hash 表，严重影响性能。

     10.【推荐】使用 entrySet 遍历 Map 类集合 KV，而不是 keySet 方式进行遍历。
     说明:keySet 其实是遍历了 2 次，一次是转为 Iterator 对象，另一次是从 hashMap 中取出 key 所对应的 value。而 entrySet 只是遍历了一次就把 key 和 value 都放到了 entry 中，
     效率更高。如果是 JDK8，使用 Map.foreach 方法。
     正例:values()返回的是 V 值集合，是一个 list 集合对象;keySet()返回的是 K 值集合，是 一个 Set 集合对象;entrySet()返回的是 K-V 值组合集合。

     11.【参考】合理利用好集合的有序性(sort)和稳定性(order)，避免集合的无序性(unsort)和 不稳定性(unorder)带来的负面影响。
     说明:有序性是指遍历的结果是按某种比较规则依次排列的。稳定性指集合每次遍历的元素次 序是一定的。
     如:ArrayList 是 order/unsort;HashMap 是 unorder/unsort;TreeSet 是 order/sort。

     12.【参考】利用 Set 元素唯一的特性，可以快速对一个集合进行去重操作，避免使用 List 的 contains 方法进行遍历、对比、去重操作。
     */
}
