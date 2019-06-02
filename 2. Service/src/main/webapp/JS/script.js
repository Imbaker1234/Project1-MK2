window.onload = function () {
    loadLogin();
};

document.getElementById("dashboard-container").onload = function () {
    document.getElementById("dashboardwelcomeuser").innerText = "Welcome Lumpy Space Princess";
};

//Functionalities =================================================

function ajaxCall(method, endPoint, incoming, store) {
    console.log("ajaxCall(method = " + method + ","  + "endPoint = " + endPoint + ", incoming = " + incoming + ", store = " + store + ") called"); //DEBUG
    let outgoing = JSON.stringify(incoming);

    let xhr = new XMLHttpRequest();

    xhr.open(method, endPoint, true);
    xhr.send(outgoing);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let result = JSON.parse(xhr.responseText);
            if (result) {
                console.log("Results retrieved from AJAX call"); //DEBUG
                console.log(xhr.responseText); //DEBUG
                if (store) {
                    window.localStorage.setItem(store, xhr.responseText);
                    alert("Results valid from AJAX call");
                }
            }
        }
    };
    if (xhr.responseText) {
        console.log(xhr.responseText); //DEBUG
        return xhr.responseText;
    }
}

function ajaxLoadDiv(view, targetDiv) {
    console.log("ajaxLoadDiv(" + view + ", " + targetDiv + ") called");
    let xhr = new XMLHttpRequest();
    xhr.open("GET", view, true);
    xhr.send();

    xhr.onreadystatechange = function () {
        console.log("Ready State " + xhr.readyState + " // Status " + xhr.status + ", " + xhr.statusText);
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById(targetDiv).innerHTML = xhr.responseText;
        }
    }
}

//Load Views ======================================================

function loadLogin() {
    console.log("loadLogin() called"); //DEBUG
    ajaxLoadDiv("login.view", "page");
}

function loadDashboard() {
    console.log("loadDashboard() called"); //DEBUG
    ajaxLoadDiv("dashboard.view", "page");
}

function loadPastTickets() {
    console.log("loadPastTickets() called"); //DEBUG
    ajaxLoadDiv("pasttickets.view", "ticketview");
    //this later to some sort of sub div on the dashboard.
}

//Functions By Page ===============================================

//===== Login =====================================================
function login() {

    let username = document.getElementById("loginusername").value;
    let password = document.getElementById("loginpassword").value;

    console.log("login() called"); //DEBUG
    console.log("username = " + username); //DEBUG
    console.log("password = " + password); //DEBUG

    let credentials = [username, password];
    ajaxCall("POST", "account", credentials, "principal");
    if (window.localStorage.getItem("principal") != "") { //If they have a JWT at this point load the dashboard.
        loadDashboard();
    }
}

function verifyLoginFields() {
    console.log("verifyLoginFields() called"); //DEBUG
    document.getElementById("registerdiv").classList.add("inactive");
    document.getElementById("registerusername").value = '';
    document.getElementById("registerpassword").value = '';
    document.getElementById("registerfirst").value = '';
    document.getElementById("registerlast").value = '';
    document.getElementById("registeremail").value = '';
    
    let username = document.getElementById("loginusername").value;
    let password = document.getElementById("loginpassword").value;
    
    if (verifyField(username) && verifyField(password)) {
        console.log("Credentials valid. toggling button");
        toggleButton("loginsubmitbutton", true);
    } else {
        toggleButton("loginsubmitbutton", false);
    }
}

function logout() {
    console.log("logout() called"); //DEBUG
    localStorage.removeItem("principle");
    LoadLogin();
}

function register() {

    let username = document.getElementById("registerusername").value;
    let password = document.getElementById("registerpassword").value;
    let firstname = document.getElementById("registerfirst").value;
    let lastname = document.getElementById("registerlast").value;
    let email = document.getElementById("registeremail").value;
    console.log("verifyUsername() called"); //DEBUG
    console.log("username =" + username); //DEBUG
    console.log("password =" + password); //DEBUG
    console.log("firstname =" + firstname); //DEBUG
    console.log("lastname =" + lastname); //DEBUG
    console.log("email =" + email); //DEBUG

    if (verifyUsername(username) == false || verifyPassword(password) == false || verifyName(firstname, lastname) == false
        || verifyEmail(email) == false) return;

    let credentials = [username, password, firstname, lastname, email];

    ajaxCall("POST", "account", credentials, "principal");
}

function verifyRegisterFields() {
    console.log("verifyRegisterFields() called");
    document.getElementById("logindiv").classList.add("inactive");
    document.getElementById("loginusername").value = '';
    document.getElementById("loginpassword").value = '';
    let username = document.getElementById("registerusername").value;
    let password = document.getElementById("registerpassword").value;
    let first = document.getElementById("registerfirst").value;
    let last = document.getElementById("registerlast").value;
    let email = document.getElementById("registeremail").value;
    if (verifyField(username) && verifyField(password) && verifyField(first) && verifyField(last) && verifyField(email)) {
        console.log("Credentials valid. toggling button");
        toggleButton("registersubmitbutton", true);
    } else {
        toggleButton("registersubmitbutton", false);
    }
}

//===== Dashboard =================================================

function viewPastTickets() {
	
    console.log("viewPastTickets() called"); //DEBUG
    let content = ["pasttickets"];
    
    ajaxCall("POST", "dashboard", content, "tickets");
    console.log(window.localStorage.tickets);
    
    if (window.localStorage.getItem("principal") != "") { //If they have a JWT, load the page
        loadPastTickets();
    }
}

//=============== Credential Verification =========================


function verifyField(field) {
    console.log("verifyField(" + field + ") called"); //DEBUG
    return !(field == "" || field.includes(" ") || field.length < 3 || field.length > 24);
}

function toggleButton(buttonId, status) {
    if (status) {
        document.getElementById(buttonId).classList.add('animated', 'pulse', 'slow', 'infinite');
        document.getElementById(buttonId).disabled = false;
    } else {
        document.getElementById(buttonId).classList.remove('animated', 'pulse', 'slow', 'infinite');
        document.getElementById(buttonId).disabled = true;
    }
}

function verifyName(firstname, lastname) {
    console.log("verifyName(" + firstname + "," + lastname + ") called"); //DEBUG
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
    console.log("verifyPhone(" + phone + ") called"); //DEBUG
    if ((/^\d{10}$/.test(phone))) {
        return true;
    }
    return false;
}

function verifyEmail(email) {
    console.log("verifyEmail(" + email + ") called"); //DEBUG
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
        return true;
    }
    document.getElementById("registeremail").value = "";
    alert("Invalid email syntax.");
    return false;
}


console.log("JS Loaded");


//==================REFERENCE SCRIPT===============================


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