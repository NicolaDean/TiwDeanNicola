
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

        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth()+1; //January is 0 so need to add 1 to make it 1!
        var yyyy = today.getFullYear();
        var hours = today.getHours();
        var minutes = today.getMinutes();
        if(dd.toString().length === 1){
            dd = "0"+dd;
        }
        if(mm.toString().length === 1){
            mm = "0"+mm;
        }
        if(hours.toString().length === 1){
            hours = "0"+hours;
        }
        if(minutes.toString().length === 1){
            minutes = "0"+minutes;
        }
        today = yyyy+'-'+mm+'-'+dd+"T"+hours +":"+minutes;

        var expDate  = document.getElementById("expiringDate-form");

        expDate.setAttribute("min",today);
        expDate.setAttribute("value",today);
        setVisible(this.formContainer);
    }

    this.sendAuctionCreation = function (form)
    {
        var self = this;
        var flag = false;
            var error = " One of those Field Missing: ";
            if(form["name-field"].value === "")
            {
                flag = true;
                error +="name, "
            }
            if(form["desc-field"].value ==="")
            {
                flag = true;
                error +="description, "
            }
            if(form["expiringDate-form"].value === "")
            {
                flag = true;
                error +="expiring date,"
            }
            if(form["file-field"].value.length === 0)
            {
                flag = true;
                error +="image, "
            }
            if(form["min-field"].value.length === 0)
            {
                flag = true;
                error +="minimum offer, "
            }
            if(form["start-field"].value.length === 0)
            {
                flag = true;
                error +="start offer, "
            }


            setText(self.errorMsg,error);
            console.log(error);
            if(flag) return;

        setInvisible(self.errorMsg);
        makeCall("POST","CreateAuction",form,
            function(req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        setCookie("lastAction","create",30);
                        form.reset();
                        alert("Auction created :)");
                        break;
                    case 400: // bad request
                        setText(self.errorMsg,message + "");
                        break;
                    case 401: // unauthorized
                        setText(self.errorMsg,message + " Unautorized action");
                        break;
                    case 500: // server error
                        setText(self.errorMsg,message + " Server error");
                        break;
                }
            }
        })
    }
}
