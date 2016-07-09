var http = require('http');

http.createServer(function(request, response) {
    response.writeHead(200);
    response.write('Hello, this is a dog.');
    response.write('Dog is running.');
    // response.end();
    setTimeout(function() {
        response.write('Dog is done');
        response.end();
    }, 5000);
}).listen(8080);

console.log('Listening on port 8080');