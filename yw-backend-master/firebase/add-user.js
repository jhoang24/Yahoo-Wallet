firebase = require("./firebase");

const user = {
  apartmentNumber: "",
  address: "",
  password: "",
  email: "",
  lastName: "",
  city: "",
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
  console.log("User added:");
  console.log(path);
});
