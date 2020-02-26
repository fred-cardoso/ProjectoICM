var pId = document.getElementById("productId").value;

const socketConn = new WebSocket('ws://localhost:8080/api');

function sendUpdate() {
        socketConn.send(pId);
}

socketConn.onmessage = (e) => {}