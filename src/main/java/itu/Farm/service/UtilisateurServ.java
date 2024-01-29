package itu.Farm.service;

import itu.Farm.bean.Utilisateur;
import itu.Farm.conn.Connexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurServ {
    @Autowired
    Connexion co;

    public List<Utilisateur> getAll() {
        List<Utilisateur> table = new ArrayList<>();
        try {
            Connection conn = co.connect();

            String sql = "select * from utilisateur";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Utilisateur temp = new Utilisateur(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
                table.add(temp);
            }

            rs.close();
            stmt.close();
            conn.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    public Utilisateur find(String email, String pwd){
        List<Utilisateur> users = this.getAll();
        for (Utilisateur u: users){
            if(u.getEmail().equalsIgnoreCase(email) && u.getMdp().equals(pwd)){
                return u;
            }
        }
        return null;
    }

    public Utilisateur findById(String id){
        try{
            Connection conn = co.connect();

            String sql = "select * from utilisateur where id='"+id+"'";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            Utilisateur u = new Utilisateur(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));

            rs.close();
            stmt.close();
            conn.close();

            return u;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Utilisateur newUser(String nom, String email, String pass){
        try{
            Connection conn = co.connect();

            String sql = "insert into utilisateur values ('user'||nextval('idUtilisateur'),'"+nom+"','"+email+"','"+pass+"',1)";

            Statement s = conn.createStatement();
            int i = s.executeUpdate(sql);

            String sql1 = "select * from utilisateur where id = (select max(id) from utilisateur)";
            ResultSet r = s.executeQuery(sql1);
            r.next();

            Utilisateur u = new Utilisateur(r.getString(1),r.getString(2),r.getString(3),r.getString(4),r.getInt(5));

            r.close();
            s.close();
            conn.close();

            return u;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
