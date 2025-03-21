# YakoCommerce

## Per il docente

Gentile docente,

con rammarico devo comunicare che, nonostante il mio impegno e la mia dedizione, non sono riuscito a completare tutte le funzionalità richieste entro il tempo previsto.
Il progetto include diverse feature interessanti, ma purtroppo non ho potuto coprire l'intera gamma di requisiti come avrei voluto.
Mi scuso sinceramente per questa mancanza e apprezzo la comprensione per le difficoltà incontrate durante lo sviluppo. Ho dato il massimo per consegnare un lavoro di qualità, ma riconosco che ci sono aspetti che avrebbero meritato ulteriore attenzione e tempo.

Per avviare il server (**è richiesto JDK 17**), è sufficiente seguire i seguenti passaggi:

1. Configurare variabili per connessione al db: dentro la cartella [db](app/src/main/resources/db) va messo un file `.env` con le seguenti proprietà:

```text
DB_URL=jdbc:mysql://localhost:3306/nome_database
DB_USER=utente
DB_PASS="password"
```

dove:

- **DB_URL** è l'url jdbc per connettersi al db
- **DB_USER** è l'utente
- **DB_PASS** è la password per il db

2. Generare le tabelle del database dal file [db.sql](app/src/main/resources/db/db.sql) importando il file in phpmyadmin

3. Eseguire il comando su cmd o powershell per avviare il server locale di sviluppo:

```bash
./gradlew libertyDev
```

4. Eseguire il comando per aprire il browser:

```bash
./gradlew openBrowser
```

5. Attendere che il server si avvii e l'applicazione sia resa disponibile.

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
