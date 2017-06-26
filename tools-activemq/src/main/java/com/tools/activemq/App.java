package com.tools.activemq;


import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println("服务正在启动");
        System.out.println("................初始化Spring容器............");
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/applicationContext.xml"});
        context.start();
        System.out.println("................Spring容器初始化完成............");
        System.out.println("................准备启动MQ监听............");

        try {
            while (true){
                        System.out.println("................MQ监听启动完成............");
                        System.out.println("                   _ooOoo_                          ");
                        System.out.println("                  o8888888o                         ");
                        System.out.println("                  88\" . \"88                       ");
                        System.out.println("                  (| -_- |)                         ");
                        System.out.println("                  O\\  =  /O                        ");
                        System.out.println("               ____/`---'\\____                     ");
                        System.out.println("             .'  \\\\|     |//  `.                  ");
                        System.out.println("            /  \\\\|||  :  |||//  \\                ");
                        System.out.println("           /  _||||| -:- |||||-  \\                 ");
                        System.out.println("           |   | \\\\\\  -  /// |   |               ");
                        System.out.println("           | \\_|  ''\\---/''  |   |                ");
                        System.out.println("           \\  .-\\__  `-`  ___/-. /                ");
                        System.out.println("         ___`. .'  /--.--\\  `. . __                ");
                        System.out.println("      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".         ");
                        System.out.println("     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |          ");
                        System.out.println("     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /        ");
                        System.out.println("======`-.____`-.___\\_____/___.-`____.-'======      ");
                        System.out.println("                   `=---='                          ");
                        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^       ");
                        System.out.println("            佛祖保佑       永无BUG                    ");
                        break;
            }
            System.out.println("服务启动完成!");
            System.in.read();
        } catch (Exception e) {
            System.out.println( "服务出问题了啊 ,出来吧 神龙~" );
            e.printStackTrace();
        }

    }
}
