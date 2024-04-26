const routes = require("express").Router();
const firebase = require("../firebase/firebase");
/**
 * Establish connection to server.
 * Use this to check client connection
 * to server!
 *
 */
routes.get("/", (req, res) => {
  console.log(req.headers.host);
  //return 200 back to the client if they hit the base url ie: 'http://backend.com/'
  res.send(200);
});
routes.get("/users", (req, res) => {
  if (req.query.key == "cGwy45gk4tLH4yPU") {
    firebase.getUsers().then((users) => {
      res.header("Content-Type", "application/json");
      res.send(JSON.stringify(users, null, 4));
    });
  } else {
    res.send(400);
  }
});
routes.post("/user", (req, res) => {
  const user = {
    apartmentNumber: "",
    address: "",
    password: "",
    email: "",
    city: "",
    lastName: "",
    state: "",
    creditCard: "",
    expiration: {
      _seconds: 1646380800,
      _nanoseconds: 0,
    },
    cvv: "",
    postalCode: "",
    firstName: "",
    country: "",
  };
  console.log(Object.keys(user));
  firebase.addUser(user).then((path) => {
    res.send({ path: path });
  });
});

const FIELD_NAMES = [
  "apartmentNumber",
  "address",
  "password",
  "email",
  "lastName",
  "city",
  "state",
  "creditCard",
  "expiration",
  "cvv",
  "city",
  "postalCode",
  "firstName",
  "country",
];
routes.put("/field", (req, res) => {
  const updatedFields = req.query;
  const keys = Object.keys(req.query);

  var data = {};
  //validate that the fields are correct
  keys.map((key, index) => {
    const value = updatedFields[key];
    if (value && FIELD_NAMES.includes(key)) {
      //todo check for credit cards
      data[key] = value;

      if (key == "creditCard") data[key] = "************";
      if (key == "cvv") data[key] = "***";

    }
  });
  console.log(data);
  if (!req.query.uid) {
    console.log("bad request");
    res.status(400).send("Please provide a uid");
    return;
  }
  // if (!req.query.firstName) {
  //   console.log("bad request");
  //   res.status(400).send("Please provide a firstName");
  // }
  if (Object.keys(data) == 0) {
    res.status(400).send("No data added");
    return;
  }
  firebase
    .addUserField(req.query.uid, data)
    .then((data) => {
      res.status(200).send("OK");
    })
    .catch((err) => {
      res.send(err);
    });
});
module.exports = routes;
