// Load login page if not logged in, or redirect to readfile page. 
async function loadLogInURL() {
  var response = await fetch('/Login');
  var map = await response.json();

  if (map["loginStatus"]) {
    window.location = "OAuth";
  }
  else {
    window.location = map["loginURL"];
  }
}

