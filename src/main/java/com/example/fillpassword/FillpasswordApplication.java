package com.example.fillpassword;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FillpasswordApplication {

  public static void fillPassword(){
    System.out.println("hola");

    try
    {
      // Se registra el Driver de MySQL
      DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

      // Se obtiene una conexión con la base de datos. Hay que
      // cambiar el usuario "root" y la clave "la_clave" por las
      // adecuadas a la base de datos que estemos usando.
      Connection conexion = DriverManager.getConnection (
          "jdbc:mysql://srv1107.hstgr.io/u784713072_lexi","u784713072_lexi", "S3b1t4s2024.");

      // Se crea un Statement, para realizar la consulta
      Statement s = conexion.createStatement();

      // Se realiza la consulta. Los resultados se guardan en el
      // ResultSet rs
      ResultSet rs = s.executeQuery ("select * from users");

      // Se recorre el ResultSet, mostrando por pantalla los resultados.
      while (rs.next())
      {
        if(rs.getString (12).isEmpty()){
          System.out.println (rs.getInt ("Id") + " | CI: "+ rs.getString (4)+" | Password:"+ rs.getString (12));
          UserDetails user = User.withDefaultPasswordEncoder()
              .username("user")
              .password(rs.getString (4))
              .roles("user")
              .build();
          System.out.println(user.getPassword());
          String passwordGen = user.getPassword();
          String passwordFinal = passwordGen.substring(8, passwordGen.length());
          System.out.println("Password final = "+passwordFinal);
          PreparedStatement updateEXP = conexion.prepareStatement("update `users` set `password` = ? where id=?");
          updateEXP.setString(1, passwordFinal);
          updateEXP.setInt(2, rs.getInt ("Id"));
          updateEXP.executeUpdate();

          System.out.println("Updated!!!");
        }

      }

      // Se cierra la conexión con la base de datos.
      conexion.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    fillPassword();
    //SpringApplication.run(FillpasswordApplication.class, args);
  }

}
