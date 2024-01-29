package itu.Farm.service;

import itu.Farm.bean.Culture;
import itu.Farm.conn.Connexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class CultureServ {
    @Autowired
    Connexion co;

    public List<Culture> getAll() {
        List<Culture> table = new ArrayList<>();
        try {
            Connection conn = co.connect();

            String sql = "select * from culture";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Culture temp = new Culture(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4),rs.getDouble(5));
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

    public Culture find(String id){
        List<Culture> cu = this.getAll();
        for (Culture l: cu){
            if(l.getId().equalsIgnoreCase(id)){
                return l;
            }
        }
        return null;
    }

    public Culture newCulture(String nom, double rend, double prix, double duree){
        try{
            Connection conn = co.connect();

            String sql = "insert into culture values ('cult'||nextval('idCulture'),'"+nom+"',"+rend+","+prix+","+duree+")";

            Statement s = conn.createStatement();
            int i = s.executeUpdate(sql);

            String sql1 = "select * from culture where id= ( select max(id) from culture )";

            ResultSet r = s.executeQuery(sql1);
            r.next();

            Culture cult = new Culture(r.getString(1),r.getString(2),r.getDouble(3),r.getDouble(4),r.getDouble(5));

            r.close();
            s.close();
            conn.close();

            return cult;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
