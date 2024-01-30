package itu.Farm.service;

import itu.Farm.bean.Culture;
import itu.Farm.bean.Parcelle;
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
public class ParcelleServ {
    @Autowired
    Connexion co;

    @Autowired
    CultureServ cultServ;


    public List<Parcelle> getAll(){
        List<Parcelle> table = new ArrayList<>();

        try {
            Connection conn = co.connect();

            String sql = "select * from parcelle";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Parcelle temp = new Parcelle(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4));
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

    public Parcelle find(String id){
        List<Parcelle> cu = this.getAll();
        for (Parcelle l: cu){
            if(l.getId().equalsIgnoreCase(id)){
                return l;
            }
        }
        return null;
    }

    public List<Culture> getCulturePossible(String id){
        List<String> table = new ArrayList<>();
        try {
            Connection conn = co.connect();

            String sql = "select * from parcelle_culture_possible where idParcelle='"+ id +"'";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String temp = rs.getString(2);
                table.add(temp);
            }

            rs.close();
            stmt.close();
            conn.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        List<Culture> liste = new ArrayList<>();
        for (String l: table){
            liste.add(cultServ.find(l));
        }
        return liste;
    }

    public Parcelle create(String idTerrain, double largeur, double longueur){
        try{
            Connection conn = co.connect();

            String sql = "insert into parcelle values ('parc'||nextVal('idParcelle'),'"+idTerrain+"',"+largeur+","+longueur+")";

            Statement s = conn.createStatement();
            int i = s.executeUpdate(sql);

            String sql1 = "select * from parcelle where id = (select max(id) from parcelle)";

            ResultSet rs = s.executeQuery(sql1);
            rs.next();
            Parcelle temp = new Parcelle(rs.getString(1),rs.getString(2),rs.getDouble(3),rs.getDouble(4));

            rs.close();
            s.close();
            conn.close();

            return temp;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void setCulturePossible(String idParcelle, List<String> idCulture){
        try{
            Connection conn = co.connect();

            Statement s = conn.createStatement();

            for (String cult: idCulture){
                String sql = "insert into parcelle_culture_possible values ('"+idParcelle+"','"+cult+"')";
                int i = s.executeUpdate(sql);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }



}
