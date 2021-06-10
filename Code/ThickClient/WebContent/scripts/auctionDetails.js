
function AuctionDetails(errorMsg)
{
    this.detailsDiv     = document.getElementById("auction-detail-div");
    this.detailsJson    = JSON.parse("{}");
    this.id             = 0;
    this.errorMsg       = document.getElementById("error-msg");

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
        //var auction = JSON.parse(msg);

        this.detailsJson = auction;
        this.id = auction.id;
        var self = this;
        setImgAttribute("auction-details-img",("ImageGetter/" + auction.imgPath),400,400);
        setContent("auction-details-name"          ,auction.salesItem.name);
        setContent("auction-details-id"            ,"(ID:" + auction.id + ")");
        setContent("auction-details-remaining-time",auction.remainingTime);
        setContent("auction-details-description"   ,auction.salesItem.description);
        setContent("details-expiring-date",auction.date);
        setContent("details-start-price",auction.initialPrice);
        setContent("details-minimum-offert",auction.minimumOffer);


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
        document.getElementById("send-offer-details").addEventListener("click",e=>{

            var form = e.target.closest("form");
            self.offertCreation(form);
        });

        offerts.reset("auction-details-max-offert");
        offerts.getOfferts(auction.offerts);
        offerts.getPrintedOffer("auction-details-max-offert",auction.maxOffer);


    }

    this.retriveData = function(id)
    {
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
                            console.log();
                            self.retriveData(self.id);
                            break;
                        case 400: // bad request
                            setText(self.errorMsg, message + "error 400");
                            break;
                        case 401: // unauthorized
                            setText(self.errorMsg, message + "error 401");
                            break;
                        case 500: // server error
                            setText(self.errorMsg, message + "error 500");
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
                            setText(self.errorMsg, message + "error 400");
                            break;
                        case 401: // unauthorized
                            setText(self.errorMsg, message + "error 401");
                            break;
                        case 500: // server error
                            setText(self.errorMsg, message + "error 500");
                            break;
                    }
                }
            });
    }



}