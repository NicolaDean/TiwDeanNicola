
function AuctionDetails()
{
    this.detailsDiv     = document.getElementById("auction-detail-div");
    this.detailsJson    = JSON.parse("{}");
    this.id             =0;
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
        console.log(auction);
        setImgAttribute("auction-details-img",("ImageGetter/" + auction.imgPath),400,400);
        setContent("auction-details-name"          ,auction.salesItem.name);
        setContent("auction-details-id"            ,"(ID:" + auction.id + ")");
        setContent("auction-details-remaining-time",auction.remainingTime);
        setContent("auction-details-description"   ,auction.salesItem.description);
        setContent("details-expiring-date",auction.date);
        setContent("details-start-price",auction.initialPrice);
        setContent("details-minimum-offert",auction.minimumOffer);
        var offerts = new OfferManager("auction-details-all-offerts");

        document.getElementById("auction-id-offer-input").value = auction.id;
        document.getElementById("send-offer-details").addEventListener("click",e=>{

            console.log("OFFERTAAA")
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

    this.refresh


}