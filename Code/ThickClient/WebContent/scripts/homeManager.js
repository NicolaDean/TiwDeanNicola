(
    function ()
    {


        window.addEventListener("load", () => {


            checkLogin();

            var auctionsDiv = document.getElementById("auctions-div");
            var nickname    = sessionStorage.getItem('username');
            var welcome     = document.getElementById("welcome-nickname")
            nickname = JSON.parse(nickname);

            console.log(nickname);
            welcome.textContent = nickname.name;


            var page = loadPage();


        }, false);



        function loadPage()
        {
            var logout      = document.getElementById("logout-button");
            var search      = document.getElementById("search-button");
            var userProfile = document.getElementById("user-profile-button");
            var errorMsg    = document.getElementById("error-msg");

            var auctionDiv  = document.getElementById("auctions-div");

            this.auction = new AuctionManager(auctionDiv);
            this.auction.setInvisible();



            logout.addEventListener("click",e =>{
                console.log("logout");
                sessionStorage.removeItem('username');
                window.location.href = "login.html";
            });

            search.addEventListener("click", e=>{
                console.log("search");
                this.auction.setVisible();
                this.auction.fillAuctions(errorMsg);
            });

            userProfile.addEventListener("click", e=>{
                this.auction.setInvisible();
            });
        }

    }
)();