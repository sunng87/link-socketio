var io = require('socket.io-client')
var socket = io.connect('http://localhost:9494/');
socket.on('connect', function(){
  console.log("connected");
  socket.on('message', function(data){
    console.log("received:", data);
//    socket.disconnect();
  });
  socket.on('disconnect', function(){
    console.log("disconnected");
  });

  socket.send("hello world");
});
console.log("started");
