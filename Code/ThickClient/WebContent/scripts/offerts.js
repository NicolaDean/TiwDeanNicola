function OfferManager(containerId)
{
    this.container = document.getElementById(containerId);
    this.data = JSON.parse("{}");
    this.errorMsg = document.getElementById("error-msg");
    this.getPrintedOffer = function(containerId,offer)
    {
        var cont = document.getElementById(containerId);
        var div = document.createElement("div");

        div.className  = "card-body text-center";
        cont.className = "card text-white bg-dark mb-3"

        div.appendChild(getColoredTitle(1,"","MAX OFFERT: ","#A52A2A"));
        div.appendChild(getTitle(3,("max-offer-name"),offer.userName));
        div.appendChild(getTitle(6,("max-offer-offert"),"Offert: " + offer.offer + "$"));
        div.appendChild(getTitle(6,("max-offer-date"),offer.date));

        cont.appendChild(div);
    }

    this.printOffer = function (offer,index)
    {
        var card = document.createElement("div");
        var div = document.createElement("div");


        var of     = getTitle(6,"","Offert: " );
        var value  = getParagraph(("offer-offert-"+index),(offer.offer + "$"));

        div.appendChild(getTitle(2,("offer-name-"+index),offer.userName));
        div.appendChild(of);
        div.appendChild(value);
        div.appendChild(getTitle(6,("offer-date-"+index),offer.date));

        card.className          = "card bg-light mb-3";
        card.style.width        = "80%";
        div.className           = "card-body text-center";
        div.style.marginRight   = "10%";

        of.className    = "d-inline";
        value.className = "d-inline";

        card.appendChild(div);
        this.container.appendChild(card);
    }

    this.parseJsonOffer= function (offerts)
    {
        var self = this;

        var i = 0;
        console.log(offerts);
        offerts.forEach(function (offer){
            self.printOffer(offer,i);
            i++;
        });
    }

    this.reset = function(maxOffer="")
    {
        this.container.innerHTML = "";

        if(maxOffer !== "")
        {
            var a = document.getElementById(maxOffer);
            a.innerHTML = "";
        }
    }
    this.getOfferts = function (offerts)
    {
        this.container.style.width     = "90%";

        this.parseJsonOffer(offerts);
    }
}