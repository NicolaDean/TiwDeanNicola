(
    function() {

        //Find HTML element by id tag:
        var login       = document.getElementById("login_button");
        var errorMsg    = document.getElementById("error-msg");

        var a = 0;

        login.addEventListener("click",e =>{
            var form = e.target.closest("form");
            errorMsg.style.display = 'none';

            a=a+1;
            console.log("Login clicked");
            //send to server
            window.location.href = "index.html";
            validateLogin()
        });



        function validateLogin()
        {
            if(a > 2)
            {
                errorMsg.style.display = 'block';
                errorMsg.textContent = "Login avvenuto";
            }
            else
            {
                errorMsg.style.display = 'block';
                errorMsg.textContent = "ERRORE";
            }
        }

})();