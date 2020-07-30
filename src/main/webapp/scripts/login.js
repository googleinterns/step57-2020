async function loadLogInURL() {
  var response = await fetch('/Login');
  var map = await response.json();
  console.log(map["loginURL"]);
  if (map["loginURL"] != "") {
    window.location = map["loginURL"];
  }
  else {
    window.location = "OAuth";
  }
}

