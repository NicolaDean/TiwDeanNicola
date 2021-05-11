(
    function ()
    {


        window.addEventListener("load", () => {
            checkLogin();

            var username = printWelcomeMessage();
            var page = new loadPage(username);

        }, false);


        function printWelcomeMessage()
        {
            var welcomeDiv  = document.getElementById("welcome-nickname");
            var nickname    = sessionStorage.getItem('username');

            nickname = JSON.parse(nickname);

            var username = nickname.name;
            welcomeDiv.textContent = username;

            return username;
        }

        function hideWelcomeMessage()
        {
            var welcome     = document.getElementById("welcome-message");
            setInvisible(welcome);
        }

        function loadPage(username)
        {

            var logout        = document.getElementById("logout-button");
            var search        = document.getElementById("search-button");
            var userProfile   = document.getElementById("user-profile-button");
            var errorMsg      = document.getElementById("error-msg");
            var userLogged    = document.getElementById("user-logged");
            var auctionDiv    = document.getElementById("auctions-div");
            var auctionDetail = document.getElementById("auction-detail-div");
            var filter = document.getElementById("serach-filter");

            this.auction = new AuctionManager(auctionDiv);
            this.auction.setInvisible();


            userLogged.innerText = "Logged as: " + username;

            /**
             * Clear session data and redirect to login page
             */
            logout.addEventListener("click",e =>{
                console.log("logout");
                sessionStorage.removeItem('username');
                window.location.href = "login.html";
            });

            /**
             *  1.Hide all other elements
             *  2.Reset content table
             *  3.Set auctions visible
             *  4.Fill auctions table with new data
             */
            search.addEventListener("click", e=>{
                console.log("search");
                this.hideAllElements();
                this.auction.fillAuctions(errorMsg);
            });

            /**
             *  1. Hide all other elements
             *  2. Show user profile data (doneOfferts,Winned auction,AuctionsPage)
             */
            userProfile.addEventListener("click", e=>{
                console.log("usereProfile")
                this.hideAllElements();
            });



            filter.addEventListener('input', (e) => {
                this.auction.applyFilter(e.target.value);
            });


            /**
             * hide all elements in the page
             */
            this.hideAllElements = function()
            {
                this.auction.setInvisible();
                hideWelcomeMessage();
                setInvisible(errorMsg);
                setInvisible(auctionDetail);
            }
        }

    }
)();