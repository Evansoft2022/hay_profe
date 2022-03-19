function showNotification() {
   const notification = new Notification("New message incoming", {
      body: "Hi there. How are you doing?",
      icon: "yourimageurl.png"
   })
}
function showNotification() {
   const notification = new Notification("New message incoming", {
      body: "Hi there. How are you doing?"
   })
notification.onclick = (e) => {
      window.location.href = "https://google.com";
   };
}