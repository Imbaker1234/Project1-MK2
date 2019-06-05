//Onload ====================================================================================================================================

window.onload = function () {

    if (window.localStorage.getItem("jwt")) {
        console.log(timeStamp() + " " + "window.onload() called : JWT Found: Loading Dashboard");
        loadDashboard();
        
    } else {
        console.log(timeStamp() + " " + "window.onload() called : JWT Not Found: Loading Login");
        loadLogin();
        
    }
    //Removed principals split as they can just be referred to by the names.
};


//Functionalities ===========================================================================================================================

function ajaxCall(method, endPoint, incoming, store) {

    //console.log(timeStamp() + " " + "ajaxCall(method = " + method + "," + "endPoint = " + endPoint + ", incoming = " + incoming + ", store = " + store + ") called"); //DEBUG
    let outgoing = JSON.stringify(incoming);

    let xhr = new XMLHttpRequest();
    xhr.open(method, endPoint, true);
    if (localStorage.getItem("jwt")) { //If the user has a jwt in localstorage then attach it as the authorization.
        xhr.setRequestHeader("Authorization", localStorage.getItem("jwt"));
    }
    xhr.send(outgoing);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let result = JSON.parse(xhr.responseText);
            if (result) {
                //console.log(timeStamp() + " " + "Results retrieved from AJAX call"); //DEBUG
                //console.log(timeStamp() + " " + xhr.responseText); //DEBUG
                if (store) {
                    window.localStorage.setItem(store, xhr.responseText);
                    if (xhr.responseText) {
                        // console.log(timeStamp() + " " + xhr.responseText); //DEBUG
                        if (xhr.getResponseHeader("Authorization")) localStorage.setItem("jwt", xhr.getResponseHeader("Authorization"));
                        //console.log(timeStamp() + " JWT Results\n" + localStorage.jwt);
                        //if (localStorage.getItem("jwt")) console.log(timeStamp() + " " + "JWT STORED!\n\n" + localStorage.getItem("jwt")); //DEBUG
                        return xhr.responseText;
                    }
                }
            }
        }
    };
}

function ajaxLoadDiv(view, targetDiv) {

    //console.log(timeStamp() + " " + "ajaxLoadDiv(" + view + ", " + targetDiv + ") called"); //DEBUG
    let xhr = new XMLHttpRequest();
    xhr.open("GET", view, true);
    xhr.send();

    xhr.onreadystatechange = function () {
        //console.log(timeStamp() + " " + "Ready State " + xhr.readyState + " // Status " + xhr.status + ", " + xhr.statusText); //DEBUG
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById(targetDiv).innerHTML = xhr.responseText;
        }
    }
}

//Load Views =================================================================================================================================

function loadLogin() {

    console.log(timeStamp() + " " + "loadLogin() called"); //DEBUG
    ajaxLoadDiv("login.view", "page");
}

function loadDashboard() {

	if (window.localStorage.getItem("jwt")) {
    console.log(timeStamp() + " " + "loadDashboard() called"); //DEBUG
    ajaxLoadDiv("dashboard.view", "page");
	}
	console.log("load dashboard failed");
	return;
}

//Functions =============================================================================================================================

function login() {
    console.log(timeStamp() + " " + "login() called"); //DEBUG
    let username = document.getElementById("loginusername").value;
    let password = document.getElementById("loginpassword").value;

    let credentials = [username, password];
    ajaxCall("POST", "account", credentials, "principal");
    setTimeout(loadDashboard, 3000);
}

function logout() {

    console.log(timeStamp() + " " + "logout() called"); //DEBUG
    localStorage.removeItem("jwt");
    localStorage.removeItem("principal");
    loadLogin();
}

function register() {

    let username = document.getElementById("registerusername").value;
    let password = document.getElementById("registerpassword").value;
    let firstname = document.getElementById("registerfirst").value;
    let lastname = document.getElementById("registerlast").value;
    let email = document.getElementById("registeremail").value;
    let credentials = [username, password, firstname, lastname, email];

    ajaxCall("POST", "account", credentials, "principal");
    
    if (window.localStorage.getItem("jwt")) {
        loadDashboard();
    }
}

function getTickets(listType) {
	
    console.log(timeStamp() + " " + "getTickets() called"); //DEBUG
    let jwt = window.localStorage.getItem("jwt");
	let principal = JSON.parse(window.localStorage.getItem("principal"));
	let admin = false;
	let employee = false;
	if (principal.role === "admin") admin = true;
	else employee = true;
	
	if (jwt && admin && listType != "pasttickets") {
		
		let contents = [listType];
		ajaxCall("POST", "dashboard", contents, "tickets");
    	let tickets = window.localStorage.getItem("tickets");
    
    	setTimeout(function() {

        	//tickets found
    		if (tickets) tableJSON();
    		
    		//no tickets found
        	else {
        		let noticketsheader = document.createElement("h6");
        		let ticketview = document.getElementById("ticketview");
        		ticketview.appendChild(noticketsheader);
        		noticketsheader.innerText = "No reimbursements found.";
        	
        	}
    	}, 3000);
	    
    	addOtherAdminCommands();
	    
	}
	else if (jwt && listType === "pasttickets") {
		let contents = [listType];
		ajaxCall("POST", "dashboard", contents, "tickets");
    	let tickets = window.localStorage.getItem("tickets");
    
    	setTimeout(function() {

        	//tickets found
    		if (tickets) tableJSON();
    		
    		//no tickets found
        	else {
        		let noticketsheader = document.createElement("h6");
        		let ticketview = document.getElementById("ticketview");
        		ticketview.appendChild(noticketsheader);
        		noticketsheader.innerText = "No reimbursements found.";
        	
        	}
    	}, 3000);
	}
	else return;
}

//TODO Leverage popper.js to call for the full details of ONE ticket and display it on click of any ticket ID.

function addReimbursement() {

    console.log(timeStamp() + " " + "addReimbursement() called"); //DEBUG

    let amt = document.getElementById("reimb_amount").value;
    let desc = document.getElementById("reimb_desc").value;
    let type = document.getElementById("reimb_type").value;
    if (amt == "" || desc == "" || type == "") {
        console.log("empty fields");
        return;
    }
    let content = [amt, desc, type];

    ajaxCall("PUT", "dashboard", content, "addedticket");
    console.log(timeStamp() + " " + window.localStorage.getItem("addedticket"));

    if (window.localStorage.getItem("jwt")) {
    	setTimeout(getTickets("pasttickets"), 3000);
    }
}

function updateReimbursementStatus() {

    console.log(timeStamp() + " " + "updateReimbursementStatus() called"); //DEBUG

    let newstatus = document.getElementById("reimb_amount").value;
    let id = document.getElementById("reimb_id").value;
    let content = [reimb_id, newstatus];

    ajaxCall("PATCH", "dashboard", content, "updatecheck");
    console.log(timeStamp() + " " + window.localStorage.getItem("updatecheck"));
    
}

//Credential Verification ==================================================================================================================


function verifyField(field) {
    //console.log(timeStamp() + " " + "verifyField(" + field + ") called"); //DEBUG
    return !(field == "" || field.includes(" ") || field.length < 3 || field.length > 24);
}

function verifyEmail(email) {
    //console.log(timeStamp() + " " + "verifyEmail(" + email + ") called"); //DEBUG
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
        return true;
    }
    document.getElementById("registeremail").value = "";
    alert("Invalid email syntax.");
    return false;
}

function verifyLoginFields() {
	
    console.log(timeStamp() + " " + "verifyLoginFields() called"); //DEBUG
    document.getElementById("registerusername").value = '';
    document.getElementById("registerpassword").value = '';
    document.getElementById("registerfirst").value = '';
    document.getElementById("registerlast").value = '';
    document.getElementById("registeremail").value = '';
    toggleButton("registersubmitbutton", false);

    let username = document.getElementById("loginusername").value;
    let password = document.getElementById("loginpassword").value;

    if (verifyField(username) && verifyField(password)) {
        console.log(timeStamp() + " " + "Credentials valid. toggling button");
        toggleButton("loginsubmitbutton", true);
    } else {
        toggleButton("loginsubmitbutton", false);
    }
}

function verifyRegisterFields() {
	
	console.log(timeStamp() + " " + "verifyRegisterFields() called"); //DEBUG

    document.getElementById("loginusername").value = '';
    document.getElementById("loginpassword").value = '';

    let username = document.getElementById("registerusername").value;
    let password = document.getElementById("registerpassword").value;
    let first = document.getElementById("registerfirst").value;
    let last = document.getElementById("registerlast").value;
    let email = document.getElementById("registeremail").value;

    if (verifyField(username) && verifyField(password) && verifyField(first) && verifyField(last) && verifyEmail(email)) {
        console.log(timeStamp() + " " + "Credentials valid. toggling button"); //DEBUG
        toggleButton("registersubmitbutton", true);

    } else {
        toggleButton("registersubmitbutton", false);
    }
}

console.log(timeStamp() + " " + "JS v1.2 Loaded");


//UTILITY FUNCTIONS=======================================================================================================================


function timeStamp() {
    d = new Date();
    return (d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds());
}

function timeOutCalled() {
    console.log("Timeout Called");
}

function tableJSON() {

	console.log(timeStamp() + " " + "tableJSON() called");
	
	let myJSON = JSON.parse(localStorage.getItem("tickets"));
    // EXTRACT VALUE FOR HTML HEADER.
    let col = [];
    for (let i = 0; i < myJSON.length; i++) {
        for (let key in myJSON[i]) {
            if (col.indexOf(key) === -1) {
                col.push(key);
            }
        }
    }

    // CREATE DYNAMIC TABLE.
    let table = document.createElement("table");

    // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

    let tr = table.insertRow(-1);                   // TABLE ROW.

    for (let i = 0; i < col.length; i++) {
        let th = document.createElement("th");      // TABLE HEADER.
        th.innerHTML = col[i];
        tr.appendChild(th);
    }

    // ADD JSON DATA TO THE TABLE AS ROWS.
    for (let i = 0; i < myJSON.length; i++) {

        tr = table.insertRow(-1);

        for (let j = 0; j < col.length; j++) {
            let tabCell = tr.insertCell(-1);
            tabCell.innerHTML = myJSON[i][col[j]];
        }
    }

    // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
    targetDiv = document.getElementById("ticketview");
    targetDiv.innerHTML = "";
    targetDiv.appendChild(table);
}

function toggleButton(buttonId, status) {
    if (status) {
        document.getElementById(buttonId).classList.add("pulse", "infinite", "slow");
        document.getElementById(buttonId).classList.add("animated", "fadeIn", "slow");
        document.getElementById(buttonId).classList.remove("invisible");
        setTimeout(waitBeforeRemoving => {
            document.getElementById(buttonId).classList.remove("fadeIn");
        }, 250);
        //If the status is true then set the button the pulse endlessly.
        document.getElementById(buttonId).disabled = false;
    } else {
        //If the status is false
        document.getElementById(buttonId).classList.remove('animated', 'pulse', 'infinite');
        document.getElementById(buttonId).disabled = true;
        document.getElementById(buttonId).classList.add("invisible");
    }
}

function addOtherAdminCommands() {
	
    let filterbypending = document.createElement("h6");
    let filterbyapproved = document.createElement("h6");
    let filterbydenied = document.createElement("h6");
    
    let ticketdiv = document.getElementById("ticketdiv");
    ticketdiv.appendChild(filterbypending);
    ticketdiv.appendChild(filterbyapproved);
    ticketdiv.appendChild(filterbydenied);
    
    filterbypending.innerText = "Admin: Pending Filter";
    let pending = getTickets("Pending");
    filterbypending.addEventListener("click", pending);
    
    filterbyapproved.innerText = "Admin: Approved Filter";
    let approved = getTickets("Approved");
    filterbyapproved.addEventListener("click", approved);
    
    filterbydenied.innerText = "Admin: Denied Filter";
    let denied = getTickets("Denied");
    filterbydenied.addEventListener("click", denied);
    
}