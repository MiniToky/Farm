package itu.Farm.conn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;

import lombok.Getter;

@Getter
@Service
public class Connexion {
    // public Connection connect() {
    //     //String url = "jdbc:postgresql://monorail.proxy.rlwy.net:35982/railway";
    //     String url = "jdbc:postgresql://localhost:5432/farm";
    //     String user = "postgres";
    //     String password = "root";

    //     Connection conn = null;
    //     try {
    //         conn = DriverManager.getConnection(url, user, password);
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    //     return conn;
    // }

   @Value("${spring.datasource.url}")
   private String url;

   @Value("${spring.datasource.username}")
   private String username;

   @Value("${spring.datasource.password}")
   private String password;

//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;

   public Connection connect() {
       Connection result = null;

       try {
//            Class.forName(this.getDriverClassName());
           result = DriverManager.getConnection(this.getUrl(), this.getUsername(), this.getPassword());
       } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
       }

       return result;
   }
}
