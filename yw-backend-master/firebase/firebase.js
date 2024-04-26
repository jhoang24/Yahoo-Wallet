const { initializeApp, applicationDefault, cert } = require('firebase-admin/app');
const { getFirestore, Timestamp, FieldValue } = require('firebase-admin/firestore');

const serviceAccount = {

};

initializeApp({
  credential: cert(serviceAccount)
});

const db = getFirestore();

async function getUsers(){
  const data = await db.collection('users').get()
  var users = []
  data.forEach(doc => {
    users.push( doc.data())
  });
  console.log("Returning " + users.length + " users")
  return users
  }

  async function addUser(user) {
    const data = await db.collection("users").add(user);
    return data.path;
  }
  async function addUserField(id, data) {
    const userRef = db.collection("users").doc(id);

    const res = await userRef.set(data, { merge: true });
    return res;
  }
  async function deleteAllUsers() {
    db.collection("/users")
      .listDocuments()
      .then((val) => {
        val.map((val) => {
          val.delete();
        });
        console.log("Deleted " + val.length + " users.");
      });
  }
  //todo add increment creditcards
  exports.deleteAllUsers = deleteAllUsers;
  exports.getUsers = getUsers;
  exports.addUser = addUser;
  exports.addUserField = addUserField;
  // Run examples comment out later
  //getUsers()

  // const user = {
  //   firstName: "Amin",
  //   lastName: "Hakem",
  //   email: "email@email.com",
  // };
  //addUser(user)

  // addUserField("YAvrZ7b7RiCOGWAVmwpf", { firstName: "Tamir" }).then((res) => {
  //   console.log(res);
  // });

  