package utils;

public enum CartActionType {
  ADD("ADD"),
  REMOVE("REM"),
  CLEAR("CLS"),
  CHECKOUT("CHECKOUT"),
  GET_ITEMS("GET");

  private final String action;

  CartActionType(String action) {
    this.action = action;
  }

  public String getAction() {
    return action;
  }

  public static CartActionType fromString(String action) {
    for (CartActionType type : CartActionType.values()) {
      if (type.getAction().equalsIgnoreCase(action)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Azione non valida: " + action);
  }
}
