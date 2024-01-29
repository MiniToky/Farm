package itu.Farm.bean;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Terrain {
    String id;
    String idLocalisation;
    String description;
    String idUtilisateur;
    int nbParcelle;
    int etat;
    Timestamp daty;
}
