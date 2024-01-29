package itu.Farm.service;

import itu.Farm.bean.Localisation;
import itu.Farm.conn.Connexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalisationServ {
    @Autowired
    Connexion co;

    public List<Localisation> getAll() {
        List<Localisation> table = new ArrayList<>();
        try {
            Connection conn = co.connect();

            String sql = "select * from localisation";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Localisation temp = new Localisation(rs.getString(1),rs.getString(2));
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

    public Localisation find(String id){
        List<Localisation> loc = this.getAll();
        for (Localisation l: loc){
            if(l.getId().equalsIgnoreCase(id)){
                return l;
            }
        }
        return null;
    }
}
