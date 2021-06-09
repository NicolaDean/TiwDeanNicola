function AuctionManager(auctionDiv,auctionDetails) {

    this.detail     = auctionDetails;
    this.auctionDiv = auctionDiv;
    this.table = document.getElementById("auctions-table");
    this.data  = JSON.parse("{}");

    this.setVisible = function() {
        setVisible(this.auctionDiv);
    }

    this.setInvisible = function() {
        setInvisible(this.auctionDiv);
    }

    this.reset = function()
    {
        var self = this;
        self.table.innerHTML = "";
    }

    /**
     * print item sales description and name
     * @param name
     * @param description
     * @param remainingTime
     * @param row
     */
    function printItemData(name,description,remainingTime,row)
    {
        var col          = document.createElement("td");
        var title        = document.createElement("h2");
        var desc         = document.createElement("p");
        var remTime      = document.createElement("h6");

        title.innerText = name;
        desc.innerText  = description;
        remTime.innerText = remainingTime;

        col.appendChild(title);
        col.appendChild(remTime);
        col.appendChild(desc);

        row.appendChild(col);
    }

    /**
     * Print the image of this object
     * @param imgPath
     * @param row
     */
    function printImg(imgPath,row)
    {
        var col          = document.createElement("td");
        var img          = document.createElement("img");

        img.src = "ImageGetter/" + imgPath;
        img.width  = "150";
        img.height = "150";

        col.appendChild(img);
        row.appendChild(col);
    }

    this.printMakeOffer = function (id)
    {

    }
    /**
     * Add button with event handler to open auction details
     * @param auction
     * @param row
     */
    this.printDetailButton = function (auction,row)
    {
        var self = this;
        var col          = document.createElement("td");
        var btn          = document.createElement("button");

        btn.textContent  = "Auction Details"
        btn.className    = "btn btn-outline-success";
        btn.id           = "auction-detail-"+auction.id;

        btn.addEventListener("click",e=>{

            var auc = document.getElementById("auctions-div");
            setInvisible(auc);
            self.detail.setVisible();
            self.detail.show(auction);
        });

        col.appendChild(btn);
        row.appendChild(col);
    }
    this.printAuction = function (auction)
    {
        var self = this;
        console.log(auction);

        var row         = document.createElement("tr");

        row.id = "auction-" + auction.id;

        printImg(auction.imgPath,row);
        printItemData(auction.salesItem.name,auction.salesItem.description,auction.remainingTime,row);
        this.printDetailButton(auction,row);
        this.table.appendChild(row);
        //container.appendChild(row);
    }
    this.parseJsonAuctions = function (auctions)
    {
        var self = this;
        self.table = document.getElementById("auctions-table");

        var json = JSON.parse(auctions);
        console.log(json);

        json.forEach(function (auction){
            self.printAuction(auction);
        });

        //container.appendChild(self.table);
    }



    this.applyFilter = function (filter)
    {
        var self = this;

        self.data.forEach(function (auction) {
            var name =  auction.salesItem.name;
            var desc =  auction.salesItem.description;

            var selectedRow = document.getElementById("auction-" + auction.id);
            if(!(name.includes(filter) || desc.includes(filter)))
            {
                setInvisible(selectedRow);
            }
            else
            {
                setVisible(selectedRow);
            }
        });
    }
    this.fillAuctions = function (errorMsg)
    {
        var self = this;
        self.reset();
        self.setVisible();
        makeCall("GET","Auctions",null,function(req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        console.log(message);
                        self.data= JSON.parse(message);
                        self.parseJsonAuctions(message,self.auctionDiv);
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