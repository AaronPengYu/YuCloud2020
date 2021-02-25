package com.aaron.seckill;

import com.mysql.jdbc.jdbc2.optional.MysqlXAConnection;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;
import javax.sql.XAConnection;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 模拟两阶段提交协议的底层实现
 * 模拟AtomikosJTA userTransaction.commit()方法的底层实现
 */
public class XATest {
    public static void main(String[] args) throws SQLException {
        // true 表示打印 XA 语句，用于调试
        boolean logXaCommands = true;

        // 获得资源管理器操作接口实例 RM1
        Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/xadb_user",
                "root", "root");
        XAConnection xaConn1 = new MysqlXAConnection((com.mysql.jdbc.Connection) conn1, logXaCommands);
        // 获取数据源对应的 XAResource，相当于该数据库对应的资源管理器
        // 该资源管理器有一系列的API
        XAResource rm1 = xaConn1.getXAResource();

        // 获得资源管理器操作接口实例 RM2
        Connection conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/xadb_account",
                "root", "root");
        XAConnection xaConn2 = new MysqlXAConnection((com.mysql.jdbc.Connection) conn2, logXaCommands);
        // 获取数据源对应的 XAResource，相当于该数据库对应的资源管理器
        // 该资源管理器有一系列的API
        XAResource rm2 = xaConn2.getXAResource();

        // AP请求TM执行一个分布式事务，TM生成全局事务ID
        byte[] gtrid = "g12345".getBytes();
        int formateId = 1;

        try {
            // ==========================分别执行RM1和RM2上的事务分支==========================
            //TM 生成rm1上的事务分支ID
            byte[] bqual1 = "b00001".getBytes();
            Xid xid1 = new MysqlXid(gtrid, bqual1, formateId);
            // 执行rm1上的事务分支
            rm1.start(xid1, XAResource.TMNOFLAGS); // one of TMNOFLAGS, TMJOIN
            // 第一个数据源执行一条插入语句
            PreparedStatement ps1 = conn1.prepareStatement("select into user(name) values ('pengyu')");
            ps1.execute();
            // 给第一个数据源的资源管理器rm1打一个标签，执行完了SQL但没有提交
            rm1.end(xid1, XAResource.TMSUCCESS);

            //TM 生成rm2上的事务分支ID
            byte[] bqual2 = "b00002".getBytes();
            Xid xid2 = new MysqlXid(gtrid, bqual2, formateId);
            // 执行rm2上的事务分支
            rm2.start(xid2, XAResource.TMNOFLAGS);
            PreparedStatement ps2 = conn2.prepareStatement("insert into account(name) values ('yueyue')");
            ps2.execute();
            rm2.end(xid2, XAResource.TMSUCCESS);

            // ==========================两阶段提交（核心代码）==========================
            // phase1: 询问所有的rm 准备提交事务分支
            // SQL执行完之后的预提交操作，调用数据库对应的资源管理器的API
            int rm1_prepare = rm1.prepare(xid1);
            int rm2_prepare = rm2.prepare(xid2);

            // phase2: 提交所有事务分支
            boolean onePhase = false;  //TM判断有两个事务分支，所以不能优化为一阶段提交
            // 比对两个预提交的结果
            // 所有事务分支都prepare成功，把每一个数据源对应的资源管理器的事务commit
            if (rm1_prepare == XAResource.XA_OK && rm2_prepare == XAResource.XA_OK) {
                rm1.commit(xid1, onePhase);
                rm2.commit(xid2, onePhase);
                // 如果rm1 commit成功，rm2 commit失败，可以重试；
                // 重试多次还不成功，记录日志，在后台用定时任务做补偿操作；
                // 或根据日志让人工处理，做回滚
            } else {  // 如果某个事务分支没有成功，则回滚
                rm1.rollback(xid1);
                rm2.rollback(xid2);
            }

        } catch (XAException e) {
            e.printStackTrace();
            //如果出现异常也要进行回滚
        }
    }
}
