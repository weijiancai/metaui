var fs = require('fs');

var contents = fs.readFileSync('index.html');
console.log(contents);

fs.readFile('index.html', function(err, contents) {
    console.log(contents);
});