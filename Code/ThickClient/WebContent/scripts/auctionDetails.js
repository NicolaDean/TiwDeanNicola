
function AuctionDetails(errorMsg)
{
    this.detailsDiv     = document.getElementById("auction-detail-div");
    this.detailsJson    = JSON.parse("{}");
    this.id             = 0;
    this.errorMsg       = document.getElementById("error-msg");
    this.currentEvent   = null;

    this.setInvisible = function ()
    {
        setInvisible(this.detailsDiv);
    }
    this.setVisible  = function ()
    {
        setVisible(this.detailsDiv);
    }


    this.reset = function()
    {
        this.detailsDiv.innerHTML = "";
    }

    this.show = function(auction)
    {
        console.log("Show details");
        //TODO DO A SERVER CALL TO GET THIS AUCTION DATA
        //this.reset();
        //this.getStructure();
        this.retriveData(auction.id);
    }


    this.printData = function(auction)
    {
        //((1/24)/60) = 1 minute in days fraction
        //var auction = JSON.parse(msg);

        console.log("Auction details cookie adding");
        var suggestions = getCookie("suggestions");
        console.log(suggestions);
        if(suggestions === null)
        {
            console.log("initialized suggestions")
            var auctions = [auction.id];
            setCookie("suggestions",JSON.stringify(auctions),30);
        }
        else
        {
            var auctions = JSON.parse(suggestions);
            auctions.push(auction.id);
            console.log("try adding" + auctions);
            setCookie("suggestions",JSON.stringify(auctions),30);
            getCookie("suggestions");
        }

        this.detailsJson = auction;
        this.id = auction.id;
        var self = this;
        setImgAttribute("auction-details-img"      ,("ImageGetter/" + auction.imgPath),400,400);
        setContent("auction-details-name"          ,auction.salesItem.name);
        setContent("auction-details-id"            ,"(ID:" + auction.id + ")");
        setContent("auction-details-remaining-time",auction.remainingTime);
        setContent("auction-details-description"   ,auction.salesItem.description);
        setContent("details-expiring-date"         ,auction.date);
        setContent("details-start-price"           ,auction.initialPrice);
        setContent("details-minimum-offert"        ,auction.minimumOffer);


        setInvisible(document.getElementById("shipping-address"));
        setInvisible(document.getElementById("auction-closed-message"));
        setInvisible(document.getElementById("close-auction-button"));

        if(auction.closable === true)
        {
            setVisible(document.getElementById("close-auction-button"));
        }

        if(auction.closed === true)
        {
            setContent("shipping-address","Shipping: " +auction.address.address + ", " + auction.address.cap + " - " + auction.address.city + " - "+ auction.address.country);
            setVisible(document.getElementById("auction-closed-message"));


        }

        document.getElementById("close-auction-button").addEventListener("click",e=>{
            self.closeAuction(auction.id);
        });

        var offerts = new OfferManager("auction-details-all-offerts");

        document.getElementById("auction-id-offer-input").value = auction.id;


        var button = document.getElementById("send-offer-details");

        if(this.currentEvent !=null)
        {
            console.log("Event removed");
            button.removeEventListener("click",this.currentEvent);
        }

        this.currentEvent = (e)=>{
            setInvisible(self.errorMsg);
            var form = e.target.closest("form");
            self.offertCreation(form);
        };

        button.addEventListener("click",this.currentEvent);


        offerts.reset("auction-details-max-offert");
        offerts.getOfferts(auction.offerts);
        offerts.getPrintedOffer("auction-details-max-offert",auction.maxOffer);
    }

    this.retriveData = function(id)
    {
        setCookie("lastAction","buy",30);

        var last = getCookie("lastAction");
        console.log("LAST ACTION WAS: "+ last);

        console.log("Printing id: " + id);
        var self = this;
        makeCall("GET",("Details?auction="+id),null,
            function(req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        console.log(message);
                        self.detailsJson = JSON.parse(message);
                        self.printData(JSON.parse(message));
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
        });
    }

    this.offertCreation = function (form)
    {

        var self = this;
        makeCall("POST","NewOffer",form,
            function(req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    var message = req.responseText;
                    switch (req.status) {
                        case 200:
                            console.log(message);
                            self.detailsJson = JSON.parse(message);
                            self.printData(JSON.parse(message));
                            alert("Offert created :)");
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
            });
    }

    this.closeAuction = function (id)
    {
        var self = this;
        makeCall("GET","Close?auction="+id,null,
            function(req) {
                if (req.readyState === XMLHttpRequest.DONE) {
                    var message = req.responseText;
                    switch (req.status) {
                        case 200:
                            console.log();
                            self.retriveData(self.id);
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
            });
    }



}