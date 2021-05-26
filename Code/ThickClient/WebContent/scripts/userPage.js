
function UserPage()
{
    this.id = -1;
    this.container                  = document.getElementById("user-profile");
    this.openAuctions               = document.getElementById("open-owned-auctions");
    this.closedAuctions             = document.getElementById("closed-owned-auctions");
    this.errorMsg                   = document.getElementById("closed-owned-auctions");
    this.openAuctionsData           = JSON.parse("{}");
    this.closedAuctionsData         = JSON.parse("{}");

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

    this.parseUserData = function ()
    {
        //NOME CODICE MAX OFFER E TEMPO MANCANTE EXPIRING DATE

        var self = this;

        var table = document.createElement("table");



        self.closedAuctionsData.forEach(function (auction){
            var tr = document.createElement("tr");
            var td = document.createElement("td");

            td.innerText = auction.salesItem.name;

            tr.appendChild(td);
            table.appendChild(tr);
        });
        self.closedAuctions.appendChild(table);

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