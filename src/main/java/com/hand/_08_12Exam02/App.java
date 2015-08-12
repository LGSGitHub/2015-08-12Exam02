package com.hand._08_12Exam02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 注册mysql JDBC驱动程序
			Class.forName("com.mysql.jdbc.Driver");

			// 获取mysql数据库的连接，参数分别是URL，用户名和密码
			// localhost 是当前主机，3306是端口，jsp_db是当前使用的数据库
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sakila", "root", "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;

	}
	
	public static void getFilm(int id)
	{
		Connection conn = getConnection();
		Statement st = null;
		ResultSet customer = null;
		ResultSet film = null;
		
		String csql = "select first_name,last_name from customer where customer_id="+id;
		String fsql = "select film_id,title,rental_duration from film where film_id in(select film_id from inventory where inventory_id in(select inventory_id from rental where rental_id in(select rental_id from customer where customer_id=)))"+id;
		
		try {
			
			st = conn.createStatement();
			customer = st.executeQuery(csql);
			
			while (customer.next()) {
				System.out.print(customer.getString("first_name") + " ");
				System.out.println(customer.getString("last_name") + "租用的film--->");
			}
			System.out.println("filmID" +" " + "|" + " " +"film名称" + " " + "|" + "租用时间");
			
			film = st.executeQuery(fsql);
			
			while (film.next()) {
				System.out.print(film.getInt("film_id") + " " + "|");
				System.out.print(film.getString("title") + " " + "|");
				System.out.println(film.getInt("rental_duration"));
				
			}
			
			film.close();
			customer.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
    public static void main( String[] args )
    {
    	Scanner sc = new Scanner(System.in);
		System.out.println("请输入CountryID：");
		int customerId = sc.nextInt();
		sc.close();
		getFilm(customerId);
    }
}
