
function AuctionCreation()
{
    this.formContainer = document.getElementById("auction-creation-form");
    this.formButton    = document.getElementById("auction-creation-button");

    this.initialize = function ()
    {
        const self = this;
        this.formButton.addEventListener("click",e =>{
            var form = e.target.closest("form");
            console.log("Creation clicked");
            //send to server
            self.sendAuctionCreation(form);
        });
    }

    this.setInvisible = function ()
    {
        setInvisible(this.formContainer);
    }

    this.setVisible  = function ()
    {
        setVisible(this.formContainer);
    }

    this.sendAuctionCreation = function (form)
    {
        makeCall("POST","CreateAuction",form,
            function(req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        console.log(message);
                        break;
                    case 400: // bad request
                        setText(errorMsg,message + "error 400");
                        break;
                    case 401: // unauthorized
                        setText(errorMsg,message + "error 401");
                        break;
                    case 500: // server error
                        setText(errorMsg,message + "error 500");
                        break;
                }
            }
        })
    }
}
