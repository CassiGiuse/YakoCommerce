export class WebSocketClient {
  constructor() {
    if (window.webSocketInstance) {
      return window.webSocketInstance;
    }

    this.socket = null;
    this.connect();

    window.webSocketInstance = this;
  }

  async connect() {
    try {
      const response = await fetch("/app/api/getID");
      const data = await response.json();
      const clientID = data.clientID;

      this.socket = new WebSocket(`/app/ws?clientID=${clientID}`);

      this.socket.onopen = () => {
        console.log("WebSocket connesso con ID:", clientID);
      };

      this.socket.onmessage = (event) => {
        console.log("Messaggio dal server:", event.data);
      };

      this.socket.onclose = () => {
        console.log("WebSocket chiuso");
      };

      window.webSocketInstance.socket = this.socket;
    } catch (error) {
      console.error("Errore nella connessione WebSocket:", error);
    }
  }

  send(message) {
    if (this.socket && this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(JSON.stringify(message));
    }
  }

  close() {
    if (this.socket) {
      this.socket.close();
      window.webSocketInstance = null;
    }
  }
}
