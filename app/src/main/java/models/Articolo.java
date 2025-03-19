package models;

public class Articolo {
  private int id;
  private String titoloEng;
  private String titoloJap;
  private String titoloDef;
  private String urlImmagine;
  private String trama;
  private String background;
  private double prezzo;
  private int quantita;

  public Articolo() {
  }

  public Articolo(
      final int id,
      final String titoloEng,
      final String titoloJap,
      final String titoloDef,
      final String urlImmagine,
      final String trama,
      final String background,
      final double prezzo,
      final int quantita) {
    this.id = id;
    this.titoloEng = titoloEng;
    this.titoloJap = titoloJap;
    this.titoloDef = titoloDef;
    this.urlImmagine = urlImmagine;
    this.trama = trama;
    this.background = background;
    this.prezzo = prezzo;
    this.quantita = quantita;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitoloEng() {
    return titoloEng;
  }

  public void setTitoloEng(String titoloEng) {
    this.titoloEng = titoloEng;
  }

  public String getTitoloJap() {
    return titoloJap;
  }

  public void setTitoloJap(String titoloJap) {
    this.titoloJap = titoloJap;
  }

  public String getTitoloDef() {
    return titoloDef;
  }

  public void setTitoloDef(String titoloDef) {
    this.titoloDef = titoloDef;
  }

  public String getUrlImmagine() {
    return urlImmagine;
  }

  public void setUrlImmagine(String urlImmagine) {
    this.urlImmagine = urlImmagine;
  }

  public String getTrama() {
    return trama;
  }

  public void setTrama(String trama) {
    this.trama = trama;
  }

  public String getBackground() {
    return background;
  }

  public void setBackground(String background) {
    this.background = background;
  }

  public double getPrezzo() {
    return prezzo;
  }

  public void setPrezzo(double prezzo) {
    this.prezzo = prezzo;
  }

  public int getQuantita() {
    return quantita;
  }

  public void setQuantita(int quantita) {
    this.quantita = quantita;
  }

  @Override
  public String toString() {
    return "Articolo{" +
        "id=" + id +
        ", titoloEng='" + titoloEng + '\'' +
        ", titoloJap='" + titoloJap + '\'' +
        ", titoloDef='" + titoloDef + '\'' +
        ", urlImmagine='" + urlImmagine + '\'' +
        ", trama='" + trama + '\'' +
        ", background='" + background + '\'' +
        ", prezzo=" + prezzo +
        ", quantita=" + quantita +
        '}';
  }
}
