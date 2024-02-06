package itu.Farm.service;

import itu.Farm.bean.Culture;
import itu.Farm.bean.EtatParcelle;
import itu.Farm.bean.Parcelle;
import itu.Farm.conn.Connexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EtatParcelleServ {
    @Autowired
    Connexion co;

//    @Autowired
//    CultureServ cultServ;
//
//    @Autowired
//    ParcelleServ parcServ;

    public List<EtatParcelle> getAll(){
        List<EtatParcelle> table = new ArrayList<>();

        try {
            Connection conn = co.connect();

            String sql = "select * from etat_parcelle";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                //ResultSet rs1 = stmt.executeQuery("select now() - '"+rs.getTimestamp(4)+"'");
//                rs1.next();

                PreparedStatement pst1 = conn.prepareStatement("SELECT NOW() - ?");
                PreparedStatement pst2 = conn.prepareStatement("select duree from culture where id=?");

                pst1.setTimestamp(1, rs.getTimestamp(4));
                pst2.setString(1, rs.getString(2));

                ResultSet rs1 = pst1.executeQuery();
                ResultSet rs2 = pst2.executeQuery();

                int etat = rs.getInt(3);

//                ResultSet rs2 = stmt.executeQuery("select duree from culture where id='"+rs.getString(2)+"'");
//                rs2.next();
//
                int duree = (int) Math.floor(rs2.getDouble(1));
                if(rs1.getTimestamp(1).toLocalDateTime().getMinute() >= duree && etat == 0){
                    etat = 1;
//                    //this.updateEtat(rs.getString(1), rs.getTimestamp(4));
                }
                EtatParcelle temp = new EtatParcelle(rs.getString(1),rs.getString(2),etat,rs.getTimestamp(4));
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

    public EtatParcelle find(String idParcelle, String pl){
        List<EtatParcelle> cu = this.getAll();
        for (EtatParcelle l: cu){
            if(l.getIdParcelle().equalsIgnoreCase(idParcelle) && l.getPlantation().toString().equals(pl)){
                return l;
            }
        }
        return null;
    }

    public void updateEtat(String idParcelle, Timestamp plantation){
        try {
            Connection conn = co.connect();

            String sql = "update etat_parcelle set etat=1 where idParcelle='" + idParcelle + "' and plantation='" + plantation + "'";

            Statement stmt = conn.createStatement();
            int i = stmt.executeUpdate(sql);

            stmt.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<EtatParcelle> getFinished(){
        List<EtatParcelle> table = new ArrayList<>();

        try {
            Connection conn = co.connect();

            String sql = "select * from etat_parcelle where etat > 0";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                EtatParcelle temp = new EtatParcelle(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getTimestamp(4));
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

    public List<EtatParcelle> getEnCours(){
        List<EtatParcelle> table = new ArrayList<>();

        try {
            Connection conn = co.connect();

            String sql = "select * from etat_parcelle where etat = 0";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                EtatParcelle temp = new EtatParcelle(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getTimestamp(4));
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

//    public List<Double> getDetails(String idParcelle, Timestamp plantation){
//        List<Double> resultat = new ArrayList<>();
//
//        EtatParcelle ep = this.find(idParcelle, plantation);
//        Culture c = cultServ.find(ep.getIdCulture());
//        Parcelle p = parcServ.find(ep.getIdParcelle());
//
//        double surface = p.getLargeur() * p.getLongueur();
//        double rendementTotal = surface * c.getRendement();
//        double prixTotal = rendementTotal * c.getPrix();
//
//        resultat.add(rendementTotal);
//        resultat.add(prixTotal);
//
//        return resultat;
//    }

    public EtatParcelle cultiver(String idParcelle, String idCulture){
        try{
            Connection conn = co.connect();

            String sql = "insert into etat_parcelle values ('"+idParcelle+"','"+idCulture+"',0,now())";

            Statement s = conn.createStatement();
            int i = s.executeUpdate(sql);

            String sql1 = "select plantation from etat_parcelle where plantation=(select max(plantation) from etat_parcelle)";
            ResultSet r = s.executeQuery(sql1);
            r.next();
            Timestamp time = r.getTimestamp(1);

            r.close();
            s.close();
            conn.close();

            return this.find(idParcelle, time.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
