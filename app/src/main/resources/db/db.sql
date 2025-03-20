USE yako_commerce;

-- Creazione della tabella 'Nazione'
CREATE TABLE
  IF NOT EXISTS Nazione (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Denominazione VARCHAR(255) NOT NULL,
    Sigla VARCHAR(10) NOT NULL
  );

-- Creazione della tabella 'Citta'
CREATE TABLE
  IF NOT EXISTS Citta (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Denominazione VARCHAR(255) NOT NULL
  );

-- Creazione della tabella 'Utente'
CREATE TABLE
  IF NOT EXISTS Utente (
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
  IF NOT EXISTS Articolo (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Titotlo_Eng VARCHAR(255),
    Titotlo_Jap VARCHAR(255),
    Titotlo_Def VARCHAR(255) NOT NULL UNIQUE,
    URL_Immagine VARCHAR(255) NOT NULL,
    Trama TEXT,
    Background TEXT,
    Prezzo DECIMAL(10, 2) NOT NULL,
    Quantita INT NOT NULL
  );

-- Creazione della tabella 'Ordine'
CREATE TABLE
  IF NOT EXISTS Ordine (
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

-- Creazione della tabella 'Carrello'
CREATE TABLE
  IF NOT EXISTS Carrello (
    id VARCHAR(36) PRIMARY KEY,
    id_utente VARCHAR(36) NULL,
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP
  );

-- Creazione della tabella 'Elementi_Carrello'
CREATE TABLE
  IF NOT EXISTS Elementi_Carrello (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_carrello VARCHAR(36) NOT NULL,
    id_articolo INT NOT NULL,
    quantita INT NOT NULL,
    prezzo_unitario DECIMAL(10, 2) NOT NULL,
    stato ENUM ('carrello', 'ordine') DEFAULT 'carrello',
    FOREIGN KEY (id_carrello) REFERENCES Carrello (id) ON DELETE CASCADE,
    FOREIGN KEY (id_articolo) REFERENCES Articolo (id) ON DELETE CASCADE
  );