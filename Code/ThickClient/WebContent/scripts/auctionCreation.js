
function AuctionCreation(errorMsg,details)
{
    this.formContainer = document.getElementById("auction-creation-form");
    this.formButton    = document.getElementById("auction-creation-button");
    this.errorMsg       = document.getElementById("error-msg");
    this.details       = details;

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
        var self = this;
        makeCall("POST","CreateAuction",form,
            function(req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        break;
                    case 400: // bad request
                        setText(self.errorMsg,message + "error 400");
                        break;
                    case 401: // unauthorized
                        setText(self.errorMsg,message + "error 401");
                        break;
                    case 500: // server error
                        setText(self.errorMsg,message + "error 500");
                        break;
                }
            }
        })
    }
}
