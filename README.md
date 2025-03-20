# YakoCommerce

## Indice

## Presentazione del progetto

Il progetto consiste un'applicazione web full-stack sviluppata con tecnologia **java**.
Si è optato per **gradle** come strumento di automazione build e per **open liberty** come server.
L'applicazione riguarda un sito di e-commerce orientato in particolar modo a fumetti e manga.

## Architettura del database

### Analisi dei Dati

Per la progettazzione del database si sono tentute in considerazione le seguenti entità:

- **Utente**, fruitore, nonché cliente del servizio;
- **Articolo**, il prodotto in vendita;
- **Ordine**, ordine effettuato dall'utente;
- **Articoli_Ordinati**, l'articolo acquistato e le info ad esso correlato (quantità, prezzo unitario, ...);
- **Citta**;
- **Nazione**;

### Descrizione delle entità

- **Utente**

  - **ID** _(INT, PK, AI)_: identificativo univoco dell'utente;
  - **Nome** _(VARCHAR, NOT NULL)_;
  - **Cognome** _(VARCHAR, NOT NULL)_;
  - **Data_Nascita** _(DATE, NOT NULL)_;
  - **Domicilio** _(VARCHAR, NOT NULL)_: via/piazza di residenza;
  - **Residenza** _(INT, FK, NOT NULL)_: città di residenza;
  - **Email** _(VARCHAR, NOT NULL)_;
  - **Telefono** _(VARCHAR, NOT NULL)_;
  - **Data_Registrazione** _(TIMESTAMP, NOT NULL)_: data e ora della registrazione dell'utente;
  - **Password_Hash** _(VARCHAR, NOT NULL)_: hash della password dell'utente;

- **Articolo**

  - **ID** _(INT, PK, AI)_: identificativo univoco dell'articolo;
  - **Titotlo_Eng** _(VARCHAR)_: titolo inglese dell'opera;
  - **Titotlo_Jap** _(VARCHAR)_: titolo giapponese dell'opera;
  - **Titotlo_Def** _(VARCHAR, NOT NULL)_: titolo default dell'opera;
  - **URL_Immagine** _(VARCHAR, NOT NULL)_: url dell'immagine (.jpg);
  - **Trama** _(TEXT)_;
  - **Background** _(TEXT)_: storia editoria, distribuzione e riconoscimenti dell'opera;
  - **Prezzo** _(DECIMAL, NOT NULL)_;
  - **Quantita** _(INT, NOT NULL)_;

- **Ordine**

  - **ID** _(INT, PK, AI)_: identificativo univoco dell'ordine;
  - **Data_Ordine** _(TIMESTAMP, NOT NULL)_;
  - **Totale** _(DECIMAL, NOT NULL)_;
  - **Stato** _(ENUM)_: stato dell'ordine, ovvero, in attesa, pagato, spedito, consegnato o annullato;
  - **ID_Utente** _(INT, FK, NOT NULL)_: id dell'utente che ha effettuato l'ordine;

- **Citta**

  - **ID** _(INT, PK, AI)_: identificativo univoco della città;
  - **Denominazione** _(VARCHAR, NOT NULL)_: nome della città;

- **Nazione**

  - **ID** _(INT, PK, AI)_: identificativo univoco della nazione;
  - **Denominazione** _(VARCHAR, NOT NULL)_: nome della città;
  - **Sigla** _(VARCHAR, NOT NULL)_: sigla della nazione (IT, FR, AW, ...);

- **Carello**

  - **ID** _(INT, PK, AI)_: identificativo univoco del carello;
  - **ID_Utente** _(INT, NULL)_: id dell'utente registrato;
  - **Data_Creazione** _(TIMESTAMP)_: data di creazione del carrello;

  - _Note_:
    - Utenti anonimi → Il id della tabella carrello sarà l'ID della sessione Servlet.
    - Utenti loggati → Il id_utente verrà popolato al login.
    - Al login, se un utente aveva un carrello anonimo, lo colleghiamo al suo account.

- **Elemento_Carrello**

  - **ID** _(INT, PK, AI)_: Identificativo univoco dell'elemento nel carrello;
  - **ID_Carrello** _(VARCHAR(36), NOT NULL)_: Identificativo del carrello a cui appartiene l'elemento. Fa riferimento alla tabella **Carrello**;
  - **ID_Articolo** _(INT, NOT NULL)_: Identificativo dell'articolo aggiunto al carrello. Fa riferimento alla tabella **Articolo**;
  - **Quantita** _(INT, NOT NULL)_: Quantità dell'articolo presente nel carrello;
  - **Prezzo_Unitario** _(DECIMAL(10, 2), NOT NULL)_: Prezzo unitario dell'articolo al momento dell'aggiunta al carrello;
  - **Stato** _(ENUM('carrello', 'ordine'), DEFAULT 'carrello')_: Stato dell'elemento, che può essere:
    - **carrello**: l'articolo è ancora nel carrello;
    - **ordine**: l'articolo è stato confermato in un ordine;

## Per il docente
