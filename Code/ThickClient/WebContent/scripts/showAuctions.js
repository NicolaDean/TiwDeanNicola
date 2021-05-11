
function AuctionManager(auctionDiv) {


    this.auctionDiv = auctionDiv;

    this.setVisible = function() {
        setVisible(this.auctionDiv);
    }

    this.setInvisible = function() {
        setInvisible(this.auctionDiv);
    }

    function printAuction(auction,container)
    {
        console.log(auction);

        var row         = document.createElement("tr");
        var id          = document.createElement("td");
        var userId      = document.createElement("td");
        var itemName    = document.createElement("td");
        var itemDesc    = document.createElement("td");

        id.textContent          = auction.id;
        userId.textContent      = auction.userId;
        itemName.textContent    = auction.salesItem.name;
        itemDesc.textContent    = auction.salesItem.description;

        row.appendChild(id);
        row.appendChild(userId);
        row.appendChild(itemName);
        row.appendChild(itemDesc);
        container.appendChild(row);
    }
    function parseJsonAuctions(auctions,container)
    {
        var self = this;
        var table = document.createElement("table");

        var json = JSON.parse(auctions);
        console.log(json);

        json.forEach(function (auction){
            printAuction(auction,table);
        });

        container.appendChild(table);
    }

    this.reset = function()
    {
        var self = this;
        self.auctionDiv.innerHTML = "";

        self.auctionDiv.removeChild();
    }
    this.fillAuctions = function (errorMsg)
    {
        var self = this;
        makeCall("GET","Auctions",null,function(req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        console.log(message);
                        parseJsonAuctions(message,self.auctionDiv);
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
                }}});
    }
}