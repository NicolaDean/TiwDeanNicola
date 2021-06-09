(
    function ()
    {
        window.addEventListener("load", () => {
            checkLogin();

            var username = printWelcomeMessage();
            var page = new loadPage(username);

        }, false);


        /**
         * Print a welcome message with the username "welcome, jhon"
         * @returns {*}
         */
        function printWelcomeMessage()
        {
            var welcomeDiv  = document.getElementById("welcome-nickname");
            var nickname    = sessionStorage.getItem('username');

            nickname = JSON.parse(nickname);

            var username = nickname.name;
            welcomeDiv.textContent = username;

            return username;
        }

        /**
         * hide the welcome message at the first user action
         */
        function hideWelcomeMessage()
        {
            var welcome     = document.getElementById("welcome-message");
            setInvisible(welcome);
        }

        /**
         * load all usefull DOM element to controll all possible user action
         * @param username
         */
        function loadPage(username)
        {

            var userId        = JSON.parse(sessionStorage.getItem('username')).id;

            var logout        = document.getElementById("logout-button");
            var search        = document.getElementById("search-button");
            var usrProfileBtn = document.getElementById("user-profile-button");
            var errorMsg      = document.getElementById("error-msg");
            var userLogged    = document.getElementById("user-logged");
            var auctionDiv    = document.getElementById("auctions-div");
            var auctionDetail = document.getElementById("auction-detail-div");
            var filter        = document.getElementById("serach-filter");
            var showCreate    = document.getElementById("create-auction-show-button");


            this.details        = new AuctionDetails();
            this.userProfile    = new UserPage(this.details);
            this.auction        = new AuctionManager(auctionDiv,this.details);
            this.creation       = new AuctionCreation();

            this.creation       .initialize();
            this.userProfile    .setId(userId);

            this.details        .setInvisible();
            this.auction        .setInvisible();
            this.userProfile    .setInvisible();
            this.creation       .setInvisible();
            setInvisible(errorMsg);

            userLogged.innerText = "Logged as: " + username;

            /**
             * Clear session data and redirect to login page
             */
            logout.addEventListener("click",e =>{
                console.log("logout");
                sessionStorage.removeItem('username');
                window.location.href = "Logout";
            });


            showCreate.addEventListener("click", e=>{
                this.hideAllElements();
                this.creation.setVisible();
            })
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
            usrProfileBtn.addEventListener("click", e=>{
                console.log("usereProfile");

                this.hideAllElements();
                this.userProfile.setVisible();
                this.userProfile.load();
            });


            /**
             * hide all auctions (in real time) that dosnt contain the filter inserted with text input
             */
            filter.addEventListener('input', (e) => {
                this.auction.applyFilter(e.target.value);
            });

            /**
             * hide all elements in the page
             */
            this.hideAllElements = function()
            {
                this.details        .setInvisible();
                this.auction        .setInvisible();
                this.userProfile    .setInvisible();
                this.creation       .setInvisible();
                hideWelcomeMessage();
                setInvisible(errorMsg);
            }
        }

    }
)();