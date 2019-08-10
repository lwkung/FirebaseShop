const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();
var db = admin.firestore();
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

exports.watchCreated = functions.firestore
      .document('users/{userId}/watchItems/{itemId}')
      .onCreate((snap, context) => {
        const value = snap.data();
        console.log(context.params.userId + "/" + context.params.itemId);
        console.log(value);
        const userId = context.params.userId;
        const itemId = context.params.itemId;
        //write to watchLists
        return db.collection("watchLists").doc(itemId)
                  .collection("watchers")
                  .doc(userId)
                  .set({
                    user: userId
                  })
                  .then(function() {
                    return console.log("watch list added");
                  })
                  .catch(function(error) {
                    console.error("Error adding watch list");
                  })
      });