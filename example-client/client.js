var io = require('socket.io-client');
var socket = io('http://localhost:9494/', {'transports': ['websocket'], 'forceBase64': false});
socket.on('connect', function(){
  console.log("connected");

  socket.on('test', function(data){
    console.log("received:", data);
    socket.disconnect();
  });

  socket.on('disconnect', function(){
    console.log("disconnected");
  });

  socket.emit("test", {name: "xiaoming"});

});

console.log("started");
