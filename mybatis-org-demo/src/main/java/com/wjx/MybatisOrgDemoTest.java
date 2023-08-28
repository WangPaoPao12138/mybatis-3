package com.wjx;

import com.wjx.entity.User;
import com.wjx.mapper.UserMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Mybatis 原生demo
 *
 * @author Gin
 * @description
 * @date 2023/8/28 11:29
 */
public class MybatisOrgDemoTest {
    public static void main(String[] args) throws IOException {
//        testXmlConfig();
        testJavaConfig();
    }

    /**
     * 测试 xml 配置方式
     */
    private static void testXmlConfig() throws IOException {
        String resource = "mybatis-config.xml";//xml配置文件路径
        InputStream inputStream = Resources.getResourceAsStream(resource);//读取配置
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);//构建 SqlSessionFactory
        SqlSession sqlSession = sqlSessionFactory.openSession(true);//通过 SqlSessionfactory 获取 SqlSession

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);//通过 SqlSession 获取 Mapper 接口

        //插入 user
        userMapper.insertUser(new User(1, "wjx"));
        //查询该 user
        User user = userMapper.selectUser(1);
        System.out.println(user);
        //查询所有 user 数据
        List<User> users = userMapper.selectAllUsers();
        System.out.println(users);

        //关闭 SqlSession
        sqlSession.close();
    }

    /**
     * 测试 Java 方式
     */
    private static void testJavaConfig() {
        // 创建数据源
        DataSource dataSource = new PooledDataSource("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/mybatis_plus?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8",
                "root",
                "123456");
        //事务
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        //环境
        Environment environment = new Environment("dev", transactionFactory, dataSource);
        //配置
        Configuration configuration = new Configuration(environment);
        configuration.setLazyLoadingEnabled(true);
        configuration.setUseActualParamName(false);
        //注册
        configuration.getTypeAliasRegistry().registerAlias(User.class);
        configuration.addMapper(UserMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        //插入 user
        userMapper.insertUser(new User(2, "wjx2"));
        //查询该 user
        User user = userMapper.selectUser(2);
        System.out.println(user);
        //查询所有 user 数据
        List<User> users = userMapper.selectAllUsers();
        System.out.println(users);

        //关闭 SqlSession
        sqlSession.close();
    }

}
