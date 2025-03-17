USE yako_commerce;

-- Creazione della tabella 'Nazione'
CREATE TABLE
  Nazione (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Denominazione VARCHAR(255) NOT NULL,
    Sigla VARCHAR(10) NOT NULL
  );

-- Creazione della tabella 'Citta'
CREATE TABLE
  Citta (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Denominazione VARCHAR(255) NOT NULL
  );

-- Creazione della tabella 'Utente'
CREATE TABLE
  Utente (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Cognome VARCHAR(255) NOT NULL,
    Data_Nascita DATE NOT NULL,
    Domicilio VARCHAR(255) NOT NULL,
    Residenza INT NOT NULL,
    Email VARCHAR(255) NOT NULL,
    Telefono VARCHAR(255) NOT NULL,
    Data_Registrazione TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Password_Hash VARCHAR(255) NOT NULL,
    FOREIGN KEY (Residenza) REFERENCES Citta (ID)
  );

-- Creazione della tabella 'Articolo'
CREATE TABLE
  Articolo (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Titotlo_Eng VARCHAR(255),
    Titotlo_Jap VARCHAR(255),
    Titotlo_Def VARCHAR(255) NOT NULL,
    URL_Immagine VARCHAR(255) NOT NULL,
    Trama TEXT,
    Background TEXT,
    Prezzo DECIMAL(10, 2) NOT NULL,
    Quantità INT NOT NULL
  );

-- Creazione della tabella 'Ordine'
CREATE TABLE
  Ordine (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Data_Ordine TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Totale DECIMAL(10, 2) NOT NULL,
    Stato ENUM (
      'in attesa',
      'pagato',
      'spedito',
      'consegnato',
      'annullato'
    ) NOT NULL,
    ID_Utente INT NOT NULL,
    FOREIGN KEY (ID_Utente) REFERENCES Utente (ID)
  );

-- Creazione della tabella 'Articoli_Ordinati'
CREATE TABLE
  Articoli_Ordinati (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    QT INT NOT NULL,
    Prezzo_Unitario DECIMAL(10, 2) NOT NULL,
    ID_Ordine INT NOT NULL,
    ID_Articolo INT NOT NULL,
    FOREIGN KEY (ID_Ordine) REFERENCES Ordine (ID),
    FOREIGN KEY (ID_Articolo) REFERENCES Articolo (ID)
  );