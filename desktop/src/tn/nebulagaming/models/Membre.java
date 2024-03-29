/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.models;

public class Membre extends User {

    private String description;

    public Membre(String nom, String email, String password, String tel, String photo, String role, String description, String etatCompte, String date) {
        super(nom, email, password, tel, photo, role, etatCompte, date);
        this.description = description;

    }

    public Membre(String nom, String email, String password, String tel, String photo, String role, String description, String etatCompte) {
        super(nom, email, password, tel, photo, role, etatCompte);
        this.description = description;

    }

    public Membre(String nom, String email, String tel, String description) {
        super(nom, email, tel);
        this.description = description;
    }

    public Membre(String password) {
        super(password);
    }

    public Membre() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String offre) {
        this.description = offre;
    }

    @Override
    public String toString() {
        return this.getNom() + " " + this.getEmail() + " " + this.getDescription() + " " + this.getTel() + " " + this.getDate();
    }
}
