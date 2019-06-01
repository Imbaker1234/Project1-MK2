//============================================================
//Register

// let credentialsJSON = JSON.stringify(credentials);
//
// let xhr = new XMLHttpRequest();
//
// xhr.open("POST", "account", true);
// xhr.send(credentialsJSON);
//
// xhr.onreadystatechange = function() {
// 	if (xhr.readyState == 4 && xhr.status == 200) {
// 		let user = JSON.parse(xhr.responseText);
// 		if (user) {
// 			alert("Account registration successful!");
// 			loadDashboard();
// 			window.localStorage.setItem("user", xhr.responseText);
// 		} else {
// 			alert("That username/email already exists!");
// 		}
// 	}
// }


//============================================================
//Login

// let credentialsJSON = JSON.stringify(credentials);
//
// let xhr = new XMLHttpRequest();
//
// xhr.open("POST", "account", true);
// xhr.send(credentialsJSON);
//
// xhr.onreadystatechange = function() {
// 	if (xhr.readyState == 4 && xhr.status == 200) {
// 		let user = JSON.parse(xhr.responseText);
//
// 		if (user) {
// 			console.log(user);
// 			alert("Login successful!");
// 			loadDashboard();
// 			window.localStorage.setItem("user", xhr.responseText);
//
// 		} else {
// 			alert("Login failed!");
//
// 		}
// 	}
// }

//============================================================
//LoadPastTickets

// 	 console.log("in loadPastTickets()");
//
// 	 let xhr = new XMLHttpRequest();
//
// 	 xhr.open("GET", "pasttickets.view", true);
// 	 xhr.send();
//
// 	 xhr.onreadystatechange = function() {
// 		 if(xhr.readyState == 4 && xhr.status == 200) {
// 			 document.getElementById('page').innerHTML = xhr.responseText;
// 		 }
// 	 }

//============================================================
//LoadDashBoard

// console.log("in loadDashboard()");
//
// let xhr = new XMLHttpRequest();
//
// xhr.open("GET", "dashboard.view", true);
// xhr.send();
//
// xhr.onreadystatechange = function() {
//  if(xhr.readyState == 4 && xhr.status == 200) {
// 	 document.getElementById('page').innerHTML = xhr.responseText;
//  }
// }

//============================================================
//LoadLogin

// //console.log("in loadLogin()");
//
// let xhr = new XMLHttpRequest();
//
// xhr.open("GET", "login.view", true);
// xhr.send();
//
// xhr.onreadystatechange = function() {
//  if(xhr.readyState == 4 && xhr.status == 200) {
// 	 document.getElementById('page').innerHTML = xhr.responseText;
//  }
// }

//============================================================
//Register

// let credentialsJSON = JSON.stringify(credentials);
//
// let xhr = new XMLHttpRequest();
//
// xhr.open("POST", "account", true);
// xhr.send(credentialsJSON);
//
// xhr.onreadystatechange = function() {
// 	if (xhr.readyState == 4 && xhr.status == 200) {
// 		let user = JSON.parse(xhr.responseText);
// 		if (user) {
// 			alert("Account registration successful!");
// 			loadDashboard();
// 			window.localStorage.setItem("user", xhr.responseText);
// 		} else {
// 			alert("That username/email already exists!");
// 		}
// 	}
// }
//============================================================
//ViewPastTickets

//
// let xhr = new XMLHttpRequest();
//
// xhr.open("POST", "dashboard", true);
// xhr.send(contentJSON);
//
// xhr.onreadystatechange = function() {
// 	if (xhr.readyState == 4 && xhr.status == 200) {
// 		let pastTickets = JSON.parse(xhr.responseText);
//
// 		if (pastTickets) {
// 			console.log(pastTickets);
// 			loadPastTickets();
// 		} else {
// 			alert("Login failed!");
//
// 		}
// 	}
// }