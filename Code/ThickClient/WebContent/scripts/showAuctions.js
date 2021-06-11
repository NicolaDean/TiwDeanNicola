function AuctionManager(auctionDiv,auctionDetails,errorMsg) {

    this.detail           = auctionDetails;

    this.auctionDiv       = auctionDiv;
    this.suggestionButton = document.getElementById("suggested-button");
    this.showNewButton    = document.getElementById("new-button");
    this.table            = document.getElementById("auctions-table");
    this.data             = JSON.parse("{}");
    this.errorMsg         = document.getElementById("error-msg");
    this.showType         = document.getElementById("show-type");

    this.suggestionButton.addEventListener("click",e=>{
        var suggestions = getCookie("suggestions");

        if(!(suggestions===null))
            this.getSuggestions(suggestions);
        else {
            alert("No Suggestions available");
        }
    });

    this.showNewButton.addEventListener("click",e=>{
        this.getAuctions();
    });

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
    this.printDetailButton = function (auction,container)
    {

        container.appendChild(getTitle(6,"","Expiring Date: "));
        container.appendChild(getParagraph("",auction.date));

        var button = getLinkTitle(6,"#","Auction Details");

        var self = this;
        button.addEventListener("click",e=>{

            var auc = document.getElementById("auctions-div");
            setInvisible(auc);
            self.detail.setVisible();
            self.detail.show(auction);
        });

        container.appendChild(button);
    }

    this.printPrices = function (auction,container)
    {

        //Start PRICE
        var r1 = getRow(0);
        var c1 = getCol();
        var c2 = getCol();

        c1.appendChild(getTitle(6,"","Initial Price"));
        c2.appendChild(getParagraph("",auction.initialPrice + "$"));
        r1.appendChild(c1);
        r1.appendChild(c2);

        // MINIMUM OFFERT

        var r2 = getRow(0);
        c1 = getCol();
        c2 = getCol();

        c1.appendChild(getTitle(6,"","MinimumOffert"));
        c2.appendChild(getParagraph("",auction.minimumOffer + "$"));
        r2.appendChild(c1);
        r2.appendChild(c2);

        // Current Price

        var r3 = getRow(0);
        c1 = getCol();
        c2 = getCol();

        c1.appendChild(getTitle(6,"","Max Offert"));
        c2.appendChild(getParagraph("",auction.maxOffer.offer + "$"));
        r3.appendChild(c1);
        r3.appendChild(c2);

        container.appendChild(r1);
        container.appendChild(r2);
        container.appendChild(r3);


    }
    this.printAuction = function (auction)
    {
        var self = this;

        var row         = document.createElement("tr");

        var col1        = document.createElement("td");
        var col2        = document.createElement("td");
        var col3        = document.createElement("td");
        var col4        = document.createElement("td");


        row.id = "auction-" + auction.id;

        printImg(auction.imgPath,col1);
        printItemData(auction.salesItem.name,auction.salesItem.description,auction.remainingTime,col2);
        this.printPrices(auction,col3);
        this.printDetailButton(auction,col4);

        row.appendChild(col1);
        row.appendChild(col2);
        row.appendChild(col3);
        row.appendChild(col4);

        this.table.appendChild(row);
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
            var name =  auction.salesItem.name.toLowerCase();
            var desc =  auction.salesItem.description.toLowerCase();

            filter = filter.toLowerCase();
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

    this.fillAuctions = function ()
    {
        //TRUE  = NEW AUCTIONS
        //FALSE = SUGGESTION WITH COOKIE

        setCookie("lastAction","buy",30);

        var suggestions = getCookie("suggestions");

        if(!(suggestions===null))
        {
            this.getSuggestions(suggestions);
        }
        else
        {
            this.getAuctions();
        }

    }

    this.getAuctions = function ()
    {
        this.showType.innerText = "Auctions";
        var self = this;
        self.reset();
        self.setVisible();
        makeCall("GET","Auctions",null,function(req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        self.data= JSON.parse(message);
                        self.parseJsonAuctions(message,self.auctionDiv);
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
                }}});
    }

    this.getSuggestions = function (suggestions)
    {
        this.showType.innerText = "Suggestions";
        var self = this;
        self.reset();
        self.setVisible();
        makeCall("POST","Suggestions",null,function(req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        self.data= JSON.parse(message);
                        self.parseJsonAuctions(message,self.auctionDiv);
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
                }}},suggestions);
    }
}