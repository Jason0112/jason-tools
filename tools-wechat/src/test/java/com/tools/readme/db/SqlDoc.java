package com.tools.readme.db;

/**
 * date: 2017/6/17
 * description :
 *
 * @author : zhencai.cheng
 */
public class SqlDoc {
    /**
     1.【强制】不要使用 count(列名)或 count(常量)来替代 count(*)，count(*)是 SQL92 定义的 标准统计行数的语法，跟数据库无关，跟 NULL 和非 NULL 无关。
     说明:count(*)会统计值为 NULL 的行，而 count(列名)不会统计此列为 NULL 值的行。

     2.【强制】count(distinct col) 计算该列除 NULL 之外的不重复行数，
     注意 count(distinct col1, col2) 如果其中一列全为NULL，那么即使另一列有不同的值，也返回为0。

     3.【强制】当某一列的值全是 NULL 时，count(col)的返回结果为 0，但 sum(col)的返回结果为 NULL，因此使用 sum()时需注意 NPE 问题。
     正例:可以使用如下方式来避免sum的NPE问题:SELECT IF(ISNULL(SUM(g)),0,SUM(g)) FROM table;

     4.【强制】使用 ISNULL()来判断是否为 NULL 值。注意:NULL 与任何值的直接比较都为 NULL。
     说明:
     1) NULL<>NULL的返回结果是NULL，而不是false。
     2) NULL=NULL的返回结果是NULL，而不是true。
     3) NULL<>1的返回结果是NULL，而不是true。

     5.【强制】 在代码中写分页查询逻辑时，若 count 为 0 应直接返回，避免执行后面的分页语句。

     6.【强制】不得使用外键与级联，一切外键概念必须在应用层解决。
     说明:(概念解释)学生表中的 student_id 是主键，那么成绩表中的 student_id 则为外键。
     如果更新学生表中的 student_id，同时触发成绩表中的 student_id 更新，则为级联更新。
     外键与级联更新适用于单机低并发，不适合分布式、高并发集群;
     级联更新是强阻塞，存在数 据库更新风暴的风险;
     外键影响数据库的插入速度。

     7.【强制】禁止使用存储过程，存储过程难以调试和扩展，更没有移植性。

     8.【强制】数据订正时，删除和修改记录时，要先 select，避免出现误删除，确认无误才能执行更新语句。

     9.【推荐】in 操作能避免则避免，若实在避免不了，需要仔细评估 in 后边的集合元素数量，控 制在 1000 个之内。

     10.【参考】如果有全球化需要，所有的字符存储与表示，均以 utf-8 编码，注意字符统计函数的区别。
     说明:
     SELECT LENGTH("轻松工作");
     返回为12
     SELECT CHARACTER_LENGTH("轻松工作"); 返回为4 如果要使用表情，那么使用 utfmb4 来进行存储，注意它与 utf-8 编码的区别。
     */
}
