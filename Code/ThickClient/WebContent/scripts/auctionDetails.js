
function AuctionDetails()
{
    this.detailsDiv = document.getElementById("auction-detail-div");

    this.setInvisible = function ()
    {
        setInvisible(this.detailsDiv);
    }
    this.setVisible  = function ()
    {
        setVisible(this.detailsDiv);
    }


    this.getThirdColumns = function(c3)
    {
        //MAX offer
        var r1 = c3.getElementsByClassName("row")[0];
        r1.appendChild(getDiv("auction-details-max-offert"));
        //ALL OFFER
        var r2 = c3.getElementsByClassName("row")[1];
        r2.appendChild(getDiv("auction-details-all-offerts"));

    }

    this.getSecondColumns = function(c2)
    {
        //NAME
        var r1 = c2.getElementsByClassName("row")[0];
            r1.appendChild(getTitle(1,"auction-details-name"));
        //EXPIRING
        var r2 = c2.getElementsByClassName("row")[1];
            r2.appendChild(getTitle(4,"auction-details-remaining-time"));
        //DESCRIPTION
        var r3 = c2.getElementsByClassName("row")[2];
            r3.appendChild(getParagraph("auction-details-description"));
        //DO AN OFFER
        var r4 = c2.getElementsByClassName("row")[3];
        //INPUT and BUTTON
        var r5 = c2.getElementsByClassName("row")[4];
    }
    this.getFirstColumns = function (c1)
    {
        console.log("first col");
        //IMG
        var r1 = c1.getElementsByClassName("row")[0];
            r1.appendChild(getImage("auction-details-img"));
        //MIN OFFER
        var r2 = c1.getElementsByClassName("row")[1];
        //MAX OFFER
        var r3 = c1.getElementsByClassName("row")[2];
    }
    this.getStructure = function ()
    {
        var container = getRow(3);
        container.id ="auction-details-container";

        //Generete col 1
        var c1 = container.getElementsByClassName("col")[0];
        addRow(c1,3);
        this.getFirstColumns(c1);

        //Generete col 2
        var c2 = container.getElementsByClassName("col")[1];
        addRow(c2,5);
        this.getSecondColumns(c2);

        //Generete col 3
        var c3 = container.getElementsByClassName("col")[2];
        addRow(c3,2);
        this.getThirdColumns(c3);

        //c3.style.boxShadow =  "-10px 10px 5px grey";
        //Max sum 12 (12 colonne)
        c1.className="col-4";
        c2.className="col-6";
        c3.className="col-2";

        console.log (container);
        this.detailsDiv.appendChild(container);
    }

    this.reset = function()
    {
        this.detailsDiv.innerHTML = "";
    }

    this.show = function(auction)
    {
        this.reset();
        this.getStructure();

        setImgAttribute("auction-details-img",("ImageGetter/" + auction.imgPath),400,400);
        setContent("auction-details-name",auction.salesItem.name);
        setContent("auction-details-remaining-time",auction.remainingTime);
        setContent("auction-details-description",auction.salesItem.description);


        var offerts = new OfferManager("auction-details-all-offerts");

        offerts.reset("auction-details-max-offert");
        offerts.getOfferts(auction.id);
        offerts.getPrintedOffer("auction-details-max-offert",auction.maxOffer);

    }
}