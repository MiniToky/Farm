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

    public List<EtatParcelle> getAll(){
        List<EtatParcelle> table = new ArrayList<>();

        try {
            Connection conn = co.connect();

            String sql = "select * from etat_parcelle";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                if(this.checkUpdate(rs.getTimestamp(4), rs.getString(2), rs.getInt(3))){
                    this.updateEtat(rs.getString(1), rs.getTimestamp(4));
                }

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

    public List<EtatParcelle> histoParcelle(String idParcelle){
        List<EtatParcelle> cu = this.getAll();
        List<EtatParcelle> table = new ArrayList<>();
        for (EtatParcelle l: cu){
            if(l.getIdParcelle().equalsIgnoreCase(idParcelle)){
                table.add(l);
            }
        }
        return table;
    }

    public EtatParcelle lastEtatParcelle(String idParcelle){
        List<EtatParcelle> liste = this.histoParcelle(idParcelle);
        EtatParcelle last = liste.get(0);
        for (EtatParcelle l: liste){
            if(l.getPlantation().compareTo(last.getPlantation()) > 0){
                last = l;
            }
        }

        return last;
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

    public boolean checkUpdate(Timestamp plantation, String idCulture, int etat){
        try{
            Connection conn = co.connect();

            PreparedStatement pst1 = conn.prepareStatement("SELECT NOW() - ?");
            PreparedStatement pst2 = conn.prepareStatement("select duree from culture where id=?");

            pst1.setTimestamp(1, plantation);
            pst2.setString(1, idCulture);

            ResultSet rs1 = pst1.executeQuery();
            ResultSet rs2 = pst2.executeQuery();

            rs1.next();
            rs2.next();

            double ecoulee = rs1.getTimestamp(1).getTime() / (60*1000);
            double duree = rs2.getDouble(1);

            boolean update = false;

            if(ecoulee >= duree && etat == 0){
                update = true;
            }
            return update;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
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

    public List<EtatParcelle> getByEtat(int etat){
        List<EtatParcelle> table = new ArrayList<>();
        List<EtatParcelle> all = this.getAll();
        for (EtatParcelle e: all){
            if(e.getEtat() == etat){
                table.add(e);
            }
        }
        return table;
    }

    public List<Double> getDetails(String idParcelle, String plantation){
        List<Double> resultat = new ArrayList<>();

        EtatParcelle ep = this.find(idParcelle, plantation);

        try{
            Connection conn = co.connect();

            PreparedStatement pst1 = conn.prepareStatement("SELECT rendement,prix from culture where id=?");
            PreparedStatement pst2 = conn.prepareStatement("select largeur*longueur from parcelle where id=?");

            pst1.setString(1, ep.getIdCulture());
            pst2.setString(1, ep.getIdParcelle());

            ResultSet rs1 = pst1.executeQuery();
            ResultSet rs2 = pst2.executeQuery();

            rs1.next();
            rs2.next();

            double rendement = rs1.getDouble(1);
            double surface = rs2.getDouble(1);

            double rendementTotal = surface * rendement;
            double prixTotal = rendementTotal * rs1.getDouble(2);

            resultat.add(rendementTotal);
            resultat.add(prixTotal);

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultat;
    }

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
