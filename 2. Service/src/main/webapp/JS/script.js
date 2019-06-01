console.log("LOADING JAVASCRIPT");
//Button action listeners

let loginbutton = document.getElementById("loginsubmitbutton");
loginbutton.addEventListener("click", login);

let registerbutton = document.getElementById("registersubmitbutton");
registerbutton.addEventListener("click", register);

//Functionalities

//Load Views

function loadLogin() {
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

	ajaxLoadDiv("login.view", "page");
}

function loadDashboard() {
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

	ajaxLoadDiv("dashboard.view", "page");
}

function loadPastTickets(pastTickets) {
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


	ajaxLoadDiv("pasttickets.view", "page");
	//this later to some sort of sub div on the dashboard.
}


function login() {
	//console.log("in login()");

	let username = document.getElementById("loginusername").value;
	let password = document.getElementById("loginpassword").value;
	if (verifyUsername(username) == false || verifyPassword(password) == false) return;

	let credentials = [ username, password ];
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
	ajaxCall("POST", credentials, "principal");
}

console.log("Made it to Line 100");


function logout() {
	console.log('in logout()');
	localStorage.removeItem("jwt");
	LoadLogin();
}

function register() {
	console.log("in register()");

	let username = document.getElementById("registerusername").value;
	let password = document.getElementById("registerpassword").value;
	let firstname = document.getElementById("registerfirst").value;
	let lastname = document.getElementById("registerlast").value;
	let email = document.getElementById("registeremail").value;

	if (verifyUsername(username) == false || verifyPassword(password) == false || verifyName(firstname, lastname) == false
		|| verifyEmail(email) == false) return;

	let credentials = [ username, password, firstname, lastname, email ];
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
	ajaxCall("POST", credentials, "principal");
}

function ajaxCall(method, incoming, store) {
	let outgoing = JSON.stringify(incoming);

	let xhr = new XMLHttpRequest();

	xhr.open("POST", "account", true);
	xhr.send(outgoing);

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			let result = JSON.parse(xhr.responseText);
			if (result) {
				console.log("Results retrieved from AJAX call");
				console.log(xhr.responseText);
				if (store) {
					window.localStorage.setItem(store, xhr.responseText);
				}
			}
		}
	};
	if (xhr.responseText) {
		console.log(xhr.responseText);
		return xhr.responseText;
	} else {
		alert("Send/Receive Error");
	}
}

console.log("Made it to Line 169");
function ajaxLoadDiv(endPoint, targetDiv) {
	let xhr = new XMLHttpRequest();
	xhr.open("GET", endpoint, true);
	xhr.send();

	xhr.onreadystatechange = function() {
		if(xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById(targetDiv).innerHTML = xhr.responseText;
		}
	}
}

function viewPastTickets() {
	console.log("in viewPastTickets");

	let content = [ viewPastTickets ];
	let contentJSON = JSON.stringify(content);
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

	loadPastTickets(ajaxCall(content));
}


//Support functions for verifying credentials

function verifyUsername(username) {

	if (username == "" || username.includes(" ") || username.length < 3
		|| username.length > 24) {
		document.getElementById("loginusername").value = "";
		document.getElementById("registerusername").value = "";
		alert("Invalid username syntax.");
		return false;
	}
	return true;
}

console.log("Made it to Line 226");
function verifyPassword(password) {

	if (password == "" || password.includes(" ") || password.length < 3
		|| password.length > 24) {
		document.getElementById("loginpassword").value = "";
		document.getElementById("registerpassword").value = "";
		alert("Invalid password syntax.");
		return false;
	}
	return true;
}

function verifyName(firstname, lastname) {
	if (firstname.length < 3 || lastname.length < 3) {

		document.getElementById("registerfirst").value = "";
		document.getElementById("registerlast").value = "";
		alert("Name cannot be less than 3 characters.");
		return false;

	}
	if (firstname.includes(" ") || lastname.includes(" ")) {

		document.getElementById("registerfirst").value = "";
		document.getElementById("registerlast").value = "";
		alert("Name cannot contain spaces.");
		return false;

	}
	return true;
}

function verifyPhone(phone) {
	if ((/^\d{10}$/.test(phone))) {
		return true;
	}
	return false;
}

function verifyEmail(email) {
	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
		return true;
	}
	document.getElementById("registeremail").value = "";
	alert("Invalid email syntax.");
	return false;
}

console.log("Made it to END OF FILE");
/*
 window.onload = function() {
 loadLogin();
 document.getElementById('toLogin').addEventListener('click', loadLogin);
 document.getElementById('toRegister').addEventListener('click', loadRegister);
 document.getElementById('toHome').addEventListener('click', loadHome);
 document.getElementById('toProfile').addEventListener('click', loadProfile);
 document.getElementById('toLogout').addEventListener('click', logout);

 // determines what navbar links to show if the user is or is not authenticated
 let isAuth = isAuthenticated();
 updateNav(isAuth);
 }

 function updateNav(isAuth) {
 console.log('in updateNav()');

 if(isAuth) {
 $('#toLogin').attr('hidden', true);
 $('#toRegister').attr('hidden', true);

 $('#toHome').attr('hidden', false);
 $('#toProfile').attr('hidden', false);
 $('#toLogout').attr('hidden', false);
 } else {
 $('#toLogin').attr('hidden', false);
 $('#toRegister').attr('hidden', false);

 $('#toHome').attr('hidden', true);
 $('#toProfile').attr('hidden', true);
 $('#toLogout').attr('hidden', true);
 }
 }

 function isAuthenticated() {
 let authenticatedUser = window.localStorage.getItem('user');
 console.log(authenticatedUser);

 if(authenticatedUser) return true
 else return false;
 }

 function loadLogin() {
	 console.log('in loadLogin()');
	
	 let isAuth = isAuthenticated();
	 updateNav(isAuth);
	
	 let xhr = new XMLHttpRequest();
	
	 xhr.open('GET', 'login.view', true);
	 xhr.send();
	
	 xhr.onreadystatechange = function() {
		 if(xhr.readyState == 4 && xhr.status == 200) {
			 document.getElementById('view').innerHTML = xhr.responseText;
			 loadLoginInfo();
		 }
	 }
 }

 function loadRegister() {
 console.log('in loadRegister()');

 let isAuth = isAuthenticated();
 updateNav(isAuth);

 let xhr = new XMLHttpRequest();

 xhr.open('GET', 'register.view', true);
 xhr.send();

 xhr.onreadystatechange = function() {
 if(xhr.readyState == 4 && xhr.status == 200) {
 document.getElementById('view').innerHTML = xhr.responseText;
 loadRegisterInfo();
 }
 }
 }

 function loadHome() {
 console.log('in loadHome()');

 let isAuth = isAuthenticated();
 updateNav(isAuth);

 let xhr = new XMLHttpRequest();

 xhr.open('GET', 'home.view', true);
 xhr.send();

 xhr.onreadystatechange = function() {
 if(xhr.readyState == 4 && xhr.status == 200) {
 document.getElementById('view').innerHTML = xhr.responseText;
 loadHomeInfo();
 }
 }
 }

 function loadHomeInfo() {
 console.log('in loadHomeInfo()');
 let userJSON = window.localStorage.getItem('user');
 let user = JSON.parse(userJSON);
 $('#user_id').html(user.id);
 $('#user_fn').html(user.firstName);
 $('#user_ln').html(user.lastName);
 $('#user_email').html(user.emailAddress);
 $('#user_username').html(user.username);
 $('#user_password').html(user.password);
 }

 function loadProfile() {
 console.log('in loadProfile()');

 let isAuth = isAuthenticated();
 updateNav(isAuth);
 if(!isAuth) {
 loadLogin();
 e.stopImmediatePropagation();
 }
 }

 function loadLoginInfo() {
 console.log('in loadLoginInfo()');

 $('#login-message').hide();
 $('#login').on('click', login);
 $('#toRegisterBtn').on('click', loadRegister);
 }

 function login() {
 console.log('in login()');

 let username = $('#login-username').val();
 let password = $('#login-password').val();

 let credentials = [username, password];
 let credentialsJSON = JSON.stringify(credentials);

 let xhr = new XMLHttpRequest();

 xhr.open('POST', 'login', true);
 xhr.send(credentialsJSON);

 xhr.onreadystatechange = function() {
 if(xhr.readyState == 4 && xhr.status == 200) {
 let user = JSON.parse(xhr.responseText);
 if(user) {
 alert('Login successful!');
 window.localStorage.setItem('user', xhr.responseText);
 loadHome();
 console.log(`User id: ${user.id} login successful`);
 } else {
 $('#login-message').show();
 $('#login-message').html('Invalid credentials');
 }
 }
 }

 }

 function logout() {
 console.log('in logout()');
 window.localStorage.removeItem('user');

 let xhr = new XMLHttpRequest();
 xhr.open('GET', 'logout', true);
 xhr.send();

 xhr.onreadystatechange = function() {
 if(xhr.readyState == 4 && xhr.status == 200) {
 console.log('Session has been invalidated!');
 loadLogin();
 }
 }
 }

 function loadRegisterInfo() {
 console.log('in loadRegisterInfo()');

 $('#reg-message').hide();

 $('#fn').blur(isRegisterFormValid);
 $('#ln').blur(isRegisterFormValid);
 $('#email').blur(isRegisterFormValid);
 $('#reg-username').blur(isRegisterFormValid);
 $('#reg-password').blur(isRegisterFormValid);

 $('#reg-username').blur(validateUsername); // same as document.getElementById('reg-username').addEventListener('blur', function, boolean);
 $('#email').blur(validateEmail);

 $('#register').attr('disabled', true);
 $('#register').on('click', register);

 }

 function isRegisterFormValid() {
 let form = [
 $('#fn').val(), 
 $('#ln').val(), 
 $('#email').val(), 
 $('#reg-username').val(), 
 $('#reg-password').val()
 ];

 if(!(form[0] && form[1] && form[2] && form[3] && form[4])) $('#register').attr('disabled', true);
 else $('#register').attr('disabled', false);
 }

 function validateUsername() {
 console.log('in validateUsername()');

 let username = $('#reg-username').val();
 console.log(username);

 if(username !== '') {
 let usernameJSON = JSON.stringify(username);
 let xhr = new XMLHttpRequest();

 xhr.open('POST', 'username.validate', true);
 xhr.setRequestHeader('Content-type', 'application/json');
 xhr.send(usernameJSON);

 xhr.onreadystatechange = function(){
 if(xhr.readyState == 4 && xhr.status == 200) {
 console.log(xhr.responseText);
 let username = JSON.parse(xhr.responseText);
 if(!username) {
 $('#reg-message').show();
 $('#reg-message').html('Username is already in use! Please try another!');
 $('#register').attr('disabled', true);
 } else {
 $('#reg-message').hide();
 }

 }
 }
 }
 }

 function validateEmail() {
 console.log('in validateEmail()');

 let email = $('#email').val();

 if(email) {
 let emailJSON = JSON.stringify(email);
 let xhr = new XMLHttpRequest();

 xhr.open('POST', 'email.validate', true);
 xhr.setRequestHeader('Content-type', 'application/json');
 xhr.send(emailJSON);

 xhr.onreadystatechange = function(){
 if(xhr.readyState == 4 && xhr.status == 200) {
 console.log(xhr.responseText);
 let email = JSON.parse(xhr.responseText);
 if(!email) {
 $('#reg-message').show();
 $('#reg-message').html('Email is already in use! Please try another!');
 $('#register').attr('disabled', true);
 } else {
 $('#reg-message').hide();
 }
 }
 }
 }
 }

 function register() {
 console.log('in register()');

 $('#register').attr('disabled', true);

 let user = {
 id: 0,
 firstName: $('#fn').val(),
 lastName: $('#ln').val(),
 emailAddress: $('#email').val(),
 username: $('#reg-username').val(),
 password: $('#reg-password').val()
 }

 let userJSON = JSON.stringify(user);

 let xhr = new XMLHttpRequest();

 xhr.open('POST', 'register', true);
 xhr.send(userJSON);

 xhr.onreadystatechange = function() {
 if(xhr.readyState == 4 && xhr.status == 200) {
 if(!xhr.responseText) {
 $('#message').show().html('Something went wrong...');
 } else {
 $('#message').hide();
 alert('Enrollment successful! Please login using your credentials.');
 loadLogin();
 }
 }
 }
 }
 */