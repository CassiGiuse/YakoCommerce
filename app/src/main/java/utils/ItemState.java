package utils;

public enum ItemState {
    CARRELLO("carrello"),
    ORDINE("ordine");

    private final String stato;

    private ItemState(String stato) {
        this.stato = stato;
    }

    public String getStato() {
        return stato;
    }

    public static ItemState fromString(String stato) {
        for (ItemState s : ItemState.values()) {
            if (s.getStato().equalsIgnoreCase(stato)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Stato non valido: " + stato);
    }
}