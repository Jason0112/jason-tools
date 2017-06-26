package com.tools.readme;

/**
 * date: 2017/6/17
 * description :
 *
 * @author : zhencai.cheng
 */
public class OOPDoc {
    /**
     1、【强制】避免通过一个类的对象引用访问此类的静态变量或静态方法，无谓增加编译器解析成本，
     直接用类名来访问即可。

     2、【强制】所有的覆写方法，必须加@Override 注解。
     说明:getObject()与 get0bject()的问题。一个是字母的 O，一个是数字的 0，加@Override 可以准确判断是否覆盖成功。
     另外，如果在抽象类中对方法签名进行修改，其实现类会马上编 译报错。

     3、【强制】相同参数类型，相同业务含义，才可以使用 Java 的可变参数，避免使用 Object。
     说明:可变参数必须放置在参数列表的最后。
     正例:public User getUsers(String type, Integer... ids) {...}

     4、【强制】外部正在调用或者二方库依赖的接口，不允许修改方法签名，避免对接口调用方产生影响。
     接口过时必须加@Deprecated 注解，并清晰地说明采用的新接口或者新服务是什么。

     5、【强制】不能使用过时的类或方法。
     说明:java.net.URLDecoder 中的方法 decode(String encodeStr) 这个方法已经过时，应该使用双参数 decode(String source, String encode)。
     接口提供方既然明确是过时接口， 那么有义务同时提供新的接口;作为调用方来说，有义务去考证过时方法的新实现是什么。

     6、【强制】Object 的 equals 方法容易抛空指针异常，应使用常量或确定有值的对象来调用 equals。
     正例: "test".equals(object);
     反例: object.equals("test"); 说明:推荐使用java.util.Objects#equals (JDK7引入的工具类)

     7、【强制】所有的相同类型的包装类对象之间值的比较，全部使用 equals 方法比较。
     说明:对于 Integer var = ? 在-128 至 127 范围内的赋值，Integer 对象是在 IntegerCache.cache 产生，会复用已有对象，
     这个区间内的 Integer值可以直接使用==进行 判断，但是这个区间之外的所有数据，都会在堆上产生，并不会复用已有对象，这是一个大坑， 推荐使用 equals 方法进行判断。

     8、关于基本数据类型与包装数据类型的使用标准如下:
     1) 【强制】所有的POJO类属性必须使用包装数据类型。
     2) 【强制】RPC方法的返回值和参数必须使用包装数据类型。
     3) 【推荐】所有的局部变量使用基本数据类型。

     说明:POJO 类属性没有初值是提醒使用者在需要使用时，必须自己显式地进行赋值，任何NPE 问题，或者入库检查，都由使用者来保证。

     正例:数据库的查询结果可能是 null，因为自动拆箱，用基本数据类型接收有 NPE 风险。
     反例:比如显示成交总额涨跌情况，即正负 x%，x 为基本数据类型，调用的 RPC 服务，调用不成功时，返回的是默认值，页面显示:0%，
     这是不合理的，应该显示成中划线-。所以包装 数据类型的 null 值，能够表示额外的信息，如:远程调用失败，异常退出。

     9.【强制】定义 DO/DTO/VO 等 POJO 类时，不要设定任何属性默认值。
     反例:POJO类的gmtCreate默认值为new Date();但是这个属性在数据提取时并没有置入具 体值，在更新其它字段时又附带更新了此字段，导致创建时间被修改成当前时间。

     10.【强制】序列化类新增属性时，请不要修改 serialVersionUID 字段，避免反序列失败;如 果完全不兼容升级，避免反序列化混乱，那么请修改 serialVersionUID 值。
     说明:注意 serialVersionUID 不一致会抛出序列化运行时异常。

     11.【强制】构造方法里面禁止加入任何业务逻辑，如果有初始化逻辑，请放在 init 方法中。

     12.【强制】POJO 类必须写 toString 方法。使用 IDE 的中工具:code> generate toString 时，如果继承了另一个 POJO 类，注意在前面加一下 super.toString。
     说明:在方法执行抛出异常时，可以直接调用 POJO 的 toString()方法打印其属性值，便于排 查问题。

     13.【推荐】使用索引访问用 String 的 split 方法得到的数组时，需做最后一个分隔符后有无
     内容的检查，否则会有抛 IndexOutOfBoundsException 的风险。

     说明:
     String str = "a,b,c,,";
     String[] ary = str.split(","); //预期大于 3，结果是 3 System.out.println(ary.length);

     14.【推荐】当一个类有多个构造方法，或者多个同名方法，这些方法应该按顺序放置在一起， 便于阅读。

     15.【推荐】 类内方法定义顺序依次是:公有方法或保护方法 > 私有方法 > getter/setter 方法。

     说明:公有方法是类的调用者和维护者最关心的方法，首屏展示最好;保护方法虽然只是子类 关心，也可能是“模板设计模式”下的核心方法;
     而私有方法外部一般不需要特别关心，是一个黑盒实现;
     因为方法信息价值较低，所有 Service 和 DAO 的 getter/setter 方法放在类体最 后。

     16.【推荐】setter 方法中，参数名称与类成员变量名称一致，this.成员名 = 参数名。在getter/setter 方法中，不要增加业务逻辑，增加排查问题的难度。

     反例:
     public Integer getData() {
     if (true) {
     return this.data + 100;
     } else {
     return this.data - 100;
     }

     17.【推荐】循环体内，字符串的连接方式，使用 StringBuilder 的 append 方法进行扩展。
     说明:反编译出的字节码文件显示每次循环都会 new 出一个 StringBuilder 对象，然后进行 append 操作，
     最后通过 toString 方法返回 String 对象，造成内存资源浪费。
     反例:
     String str = "start";
     for (int i = 0; i < 100; i++) {
     str = str + "hello";
     }

     18.【推荐】final 可以声明类、成员变量、方法、以及本地变量，下列情况使用 final 关键字:
     1) 不允许被继承的类，如:String 类。
     2) 不允许修改引用的域对象，如:POJO 类的域变量。
     3) 不允许被重写的方法，如:POJO 类的 setter 方法。
     4) 不允许运行过程中重新赋值的局部变量。
     5) 避免上下文重复使用一个变量，使用 final 描述可以强制重新定义一个变量，方便更好 地进行重构。

     19.【推荐】慎用 Object 的 clone 方法来拷贝对象。
     说明:对象的 clone 方法默认是浅拷贝，若想实现深拷贝需要重写 clone 方法实现属性对象 的拷贝。

     20.【推荐】类成员与方法访问控制从严:
     1) 如果不允许外部直接通过new来创建对象，那么构造方法必须是private。
     2) 工具类不允许有public或default构造方法。
     3) 类非static成员变量并且与子类共享，必须是protected。
     4) 类非static成员变量并且仅在本类使用，必须是private。
     5) 类static成员变量如果仅在本类使用，必须是private。
     6) 若是static成员变量，必须考虑是否为final。
     7) 类成员方法只供类内部调用，必须是private。
     8) 类成员方法只对继承类公开，那么限制为protected。

     说明:任何类、方法、参数、变量，严控访问范围。过于宽泛的访问范围，不利于模块解耦。
     思考:如果是一个 private 的方法，想删除就删除，可是一个 public 的 service 方法，或者 一个 public 的成员变量，
     删除一下，不得手心冒点汗吗?
     变量像自己的小孩，尽量在自己的 视线内，变量作用域太大，如果无限制的到处跑，那么你会担心的。
     */
}

