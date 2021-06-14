(
    function() {

        //Find HTML element by id tag:
        var login       = document.getElementById("login_button");
        var errorMsg    = document.getElementById("error-msg");


        login.addEventListener("click",e =>{
            var form = e.target.closest("form");
            setInvisible(errorMsg);
            console.log("Login clicked");
            //send to server
            validateLogin(form)
        });

        validateLogin = function(form){


            var flag = false;
            var error = " One of those Field Missing: ";

            if(form["password-field"].value === "")
            {
                flag = true;
                error +="password, "
            }
            if(form["email-field"].value ==="")
            {
                flag = true;
                error +="email, "
            }
            setText(errorMsg,error);
            if(flag) return;

            setInvisible(errorMsg);

            makeCall("POST","Login",form,
                function(req) {
                    if (req.readyState === XMLHttpRequest.DONE) {
                        var message = req.responseText;
                        switch (req.status) {
                            case 200:
                                sessionStorage.setItem('username', message);
                                console.log(message);
                                window.location.href = "home.html";
                                break;
                            case 400: // bad request
                                setText(errorMsg,message + "error 400");
                                break;
                            case 401: // unauthorized
                                setText(errorMsg,message + " Unautorized action");
                                break;
                            case 500: // server error
                                setText(errorMsg,message + " Server error");
                                break;
                        }
                    }
                }
            )};
})();


