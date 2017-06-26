package com.tools.readme.db;

/**
 * date: 2017/6/17
 * description :
 *
 * @author : zhencai.cheng
 */
public class DBDoc {
    /**
     1.【强制】表达是与否概念的字段，必须使用 is_xxx 的方式命名，数据类型是 unsigned tinyint ( 1表示是，0表示否)。
     说明:任何字段如果为非负数，必须是 unsigned。
     正例:表达逻辑删除的字段名 is_deleted，1 表示删除，0 表示未删除。

     2.【强制】表名、字段名必须使用小写字母或数字，禁止出现数字开头，禁止两个下划线中间只 出现数字。
     数据库字段名的修改代价很大，因为无法进行预发布，所以字段名称需要慎重考虑。
     正例:getter_admin，task_config，level3_name
     反例:GetterAdmin，taskConfig，level_3_name

     3.【强制】表名不使用复数名词。 说明:表名应该仅仅表示表里面的实体内容，不应该表示实体数量，对应于 DO 类名也是单数 形式，符合表达习惯。

     4.【强制】禁用保留字，如 desc、range、match、delayed 等，请参考 MySQL 官方保留字。

     5.【强制】主键索引名为 pk_字段名;唯一索引名为 uk_字段名;普通索引名则为 idx_字段名。

     说明:pk_ 即 primary key;uk_ 即 unique key;idx_ 即 index 的简称。

     6.【强制】小数类型为 decimal，禁止使用 float 和 double。
     说明:float 和 double 在存储的时候，存在精度损失的问题，很可能在值的比较时，得到不正确的结果。
     如果存储的数据范围超过decimal的范围，建议将数据拆成整数和小数分开存储。

     7.【强制】如果存储的字符串长度几乎相等，使用 char 定长字符串类型。

     8.【强制】varchar 是可变长字符串，不预先分配存储空间，长度不要超过 5000，如果存储长 度大于此值，
     定义字段类型为 text，独立出来一张表，用主键来对应，避免影响其它字段索 引效率。

     9.【强制】表必备三字段:id, gmt_create, gmt_modified。 说明:其中id必为主键，类型为unsigned bigint、单表时自增、步长为1。
     gmt_create, gmt_modified 的类型均为 date_time 类型。

     10.【推荐】表的命名最好是加上“业务名称_表的作用”。 正例:tiger_task / tiger_reader / mpp_config

     11.【推荐】库名与应用名称尽量一致。
     12.【推荐】如果修改字段含义或对字段表示的状态追加时，需要及时更新字段注释。

     13.【推荐】字段允许适当冗余，以提高查询性能，但必须考虑数据一致。冗余字段应遵循:

     1)不是频繁修改的字段。

     2)不是 varchar 超长字段，更不能是 text 字段。
     正例:商品类目名称使用频率高，字段长度短，名称基本一成不变，可在相关联的表中冗余存 储类目名称，避免关联查询。

     14.【推荐】单表行数超过 500 万行或者单表容量超过 2GB，才推荐进行分库分表。
     说明:如果预计三年后的数据量根本达不到这个级别，请不要在创建表时就分库分表。

     15.【参考】合适的字符存储长度，不但节约数据库表空间、节约索引存储，更重要的是提升检索速度
     */
}
