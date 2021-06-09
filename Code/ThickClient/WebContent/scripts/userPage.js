
function UserPage(auctionDetails)
{
    this.id = -1;
    this.container                  = document.getElementById("user-profile");
    this.openAuctions               = document.getElementById("open-owned-auctions");
    this.closedAuctions             = document.getElementById("closed-owned-auctions");
    this.errorMsg                   = document.getElementById("error-msg");
    this.openAuctionsData           = JSON.parse("{}");
    this.closedAuctionsData         = JSON.parse("{}");
    this.detail                     = auctionDetails;

    console.log("Created user page");
    console.log(this.container);
    setInvisible(this.container);


    this.setVisible = function()
    {
        setVisible(this.container);
    }
    this.setInvisible = function()
    {
        console.log(this.container);
        console.log("HIDDEEE");
        setInvisible(this.container);
    }
    this.reset = function()
    {
        this.openAuctions.innerHTML = "";
        this.closedAuctions.innerHTML = "";
    }

    this.setId = function (id)
    {
        this.id = id;
    }

    this.printAuction = function (container,auction)
    {
        var col = getDiv("auction-col" );
        var r1  = getDiv("auction-row1");
        var r2  = getDiv("auction-row2");
        var r3  = getDiv("auction-row2");

        col.className = "col";
        r1.className = "r1";
        r2.className = "r2";
        r3.className = "r3";

        var name = getLinkTitle(5,"#",(auction.salesItem.name + "(ID:" +auction.id +")"));

        var self = this;

        name.addEventListener("click", e=>{
            self.setInvisible();
            self.detail.setVisible();
            self.detail.show(auction);
        })

        r1.appendChild(name);
        r2.appendChild(getTitle(6,"","Max Offert: "));
        r2.appendChild(getParagraph("",auction.maxOffer.offer + " $"));
        r3.appendChild(getParagraph("","Expire: " + auction.date));

        col.appendChild(r1);
        col.appendChild(r2);
        col.appendChild(r3);

        container.appendChild(col)
    }

    this.printAuctions = function(container,auctions)
    {
        var self = this;

        var table = document.createElement("table");
        
        auctions.forEach(function (auction){
            var tr = document.createElement("tr");
            var td = document.createElement("td");

            self.printAuction(td,auction);
            //td.innerText = auction.salesItem.name;

            tr.appendChild(td);
            table.appendChild(tr);
            table.className="table";
        });
        container.appendChild(table);
    }
    this.parseUserData = function ()
    {
        //NOME CODICE MAX OFFER E TEMPO MANCANTE EXPIRING DATE
        this.printAuctions(this.openAuctions,this.openAuctionsData);
        this.printAuctions(this.closedAuctions,this.closedAuctionsData);
    }

    this.assignData = function(json)
    {
        console.log(json);
        this.openAuctionsData = json.open;
        this.closedAuctions   = json.closed;
    }
    this.load = function ()
    {
        if(this.id === -1)return;

        var self = this;
        this.reset();
        makeCall("GET",("UserProfile?userid=" + self.id),null,function(req) {
            if (req.readyState === XMLHttpRequest.DONE) {
                var message = req.responseText;
                switch (req.status) {
                    case 200:
                        self.openAuctionsData   = JSON.parse(message).open;
                        self.closedAuctionsData = JSON.parse(message).closed;
                        self.parseUserData();
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
                }}});
    }
}