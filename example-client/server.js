var app = require('http').createServer(handler)
var io = require('socket.io')(app);
var fs = require('fs');

app.listen(9494);

function handler (req, res) {
  fs.readFile(__dirname + '/index.html',
  function (err, data) {
    if (err) {
      res.writeHead(500);
      return res.end('Error loading index.html');
    }

    res.writeHead(200);
    res.end(data);
  });
}

io.on('connection', function (socket) {
  console.log("connected");
  socket.emit('test', { hello: 'world' });
  socket.on('test', function (data) {
    console.log(data);
  });
});
