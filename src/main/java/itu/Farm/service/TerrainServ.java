package itu.Farm.service;

import itu.Farm.bean.Parcelle;
import itu.Farm.bean.Terrain;
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
public class TerrainServ {
    @Autowired
    Connexion co;

    @Autowired
    ParcelleServ parcServ;

    @Autowired
    UtilisateurServ userServ;

    public Terrain create(String loc, String desc, String idUser, int nbP){

        try {
            Connection conn = co.connect();

            String sql = "Insert into terrain values ('ter'||nextVal('idTerrain'),'"+loc+"','"+desc+"','"+idUser+"',"+nbP+",0,now())";

            Statement stmt = conn.createStatement();
            int i = stmt.executeUpdate(sql);

            String sql1 = "select * from terrain where id= ( select max(id) from terrain )";

            ResultSet rs = stmt.executeQuery(sql1);
            rs.next();

            Terrain temp = new Terrain(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getTimestamp(7));

            rs.close();
            stmt.close();
            conn.close();

            return temp;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Terrain> getAll(){
        List<Terrain> table = new ArrayList<>();

        try {
            Connection conn = co.connect();

            String sql = "select * from terrain";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Terrain temp = new Terrain(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getTimestamp(7));
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

    public Terrain find(String id){
        List<Terrain> cu = this.getAll();
        for (Terrain l: cu){
            if(l.getId().equalsIgnoreCase(id)){
                return l;
            }
        }
        return null;
    }

    public List<String> getPhotos(String id){
        List<String> table = new ArrayList<>();

        try {
            Connection conn = co.connect();

            String sql = "select * from terrain_photo where idTerrain='"+ id +"''";

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
        return table;
    }

    public List<Parcelle> getParcelles(String id){
        List<String> table = new ArrayList<>();
        try {
            Connection conn = co.connect();

            String sql = "select * from parcelle where idTerrain='"+ id +"'";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String temp = rs.getString(1);
                table.add(temp);
            }

            rs.close();
            stmt.close();
            conn.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        List<Parcelle> liste = new ArrayList<>();
        for (String l: table){
            liste.add(parcServ.find(l));
        }
        return liste;
    }

    public Terrain valider(String id){
        try{
            Connection conn = co.connect();
            String sql = "update terrain set etat=1 where id='"+id+"'";

            Statement s = conn.createStatement();
            int i = s.executeUpdate(sql);

            s.close();
            conn.close();

            return find(id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> setPhotos(String id, List<String> path){
        try{
            Connection conn = co.connect();
            Statement s = conn.createStatement();

            for (String p: path){
                String sql = "insert into terrain_photo values ('"+id+"','"+p+"')";
                int i = s.executeUpdate(sql);
            }
            s.close();
            conn.close();

            return this.getPhotos(id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Terrain> terrainProprio(String id){
        List<Terrain> table = new ArrayList<>();

        try {
            Connection conn = co.connect();

            String sql = "select * from terrain where idUtilisateur='"+id+"'";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Terrain temp = new Terrain(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getTimestamp(7));
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

    public Utilisateur getProprio(String idTerrain){
        try {
            Connection conn = co.connect();

            String sql = "select idUtilisateur from terrain where id='"+idTerrain+"'";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            rs.next();
            String idUtilisateur = rs.getString(1);

            rs.close();
            stmt.close();
            conn.close();

            return userServ.findById(idUtilisateur);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
