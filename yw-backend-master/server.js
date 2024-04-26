var express = require("express");
var bodyParser = require("body-parser");
const cors = require("cors");
require("dotenv").config();

const routes = require("./routes");

var app = express();

app.use(require("morgan")("combined"));
app.use(
  bodyParser.urlencoded({
    extended: true,
  })
);
app.use(cors());
app.use(bodyParser.json());
app.use(express.static("public"));
app.use("/", routes);
const port = process.env.PORT || 5000;
const server = app.listen(port, async function () {
  console.log("**********************************");
  //console.log("Establishing host " + process.env.BACKEND_URL);
  console.log("backend v.1.0 started on port " + port);
  console.log("**********************************");
});
module.exports = server;