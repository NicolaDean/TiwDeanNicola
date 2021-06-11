//TODO loading div for the first request
function makeCall(method, APIurl, formData, requestManagment, json = null,reset = false)
{
    var req = new XMLHttpRequest(); // visible by closure
    req.onreadystatechange = function() {
        requestManagment(req)
    };+ // closure
    req.open(method, APIurl);
    if (formData == null) {

        if(json===null){
            req.send();
        }
        else{
            console.log("IL JSON é INVIATO: -> " + json)
            req.send(json);
        }
    } else {
        req.send(new FormData(formData));
    }
    if (formData !== null && reset === true) {
        formData.reset();
    }
}


/**
 * If username is not saved in session then redirect to login page
 */
function checkLogin()
{
    if (sessionStorage.getItem("username") == null) {
        window.location.href = "index.html";
    }
}


function setVisible(component)
{
    component.style.display = 'block';
}

function setInvisible(component)
{
    component.style.display = 'none';
}

function setText(component,content)
{
    setVisible(component);
    component.innerHTML = content;
}

function getCol()
{
    var col = document.createElement("div");
    col.className = "col";

    return col;
}

/**
 * Create a bootstrap column
 * @param colsNumber
 */
function getRow(colsNumber)
{
    var row = document.createElement("div");
    row.className = "row";
    for(let i=0;i<colsNumber;i++)
    {
        row.appendChild(getCol());
    }

    return row;
}


function getImage(id,path ="")
{
    var img =  document.createElement("img");
    img.id = id;
    img.src = path;
    return img;
}

function getColoredTitle(size,id,text ="",color)
{
    var hn =  document.createElement("h"+size);
    hn.id = id;
    hn.innerText = text;
    hn.style.color = color;
    return hn;
}
function getTitle(size,id,text ="")
{
    var hn =  document.createElement("h"+size);
    hn.id = id;
    hn.innerText = text;
    return hn;
}

function getParagraph(id,text="")
{
    var p =  document.createElement("p");
    p.id = id;
    p.innerText = text;

    return p;
}

function setContent(id,text)
{

    var elem = document.getElementById(id);
    setVisible(elem);
    elem.innerText = text;
}

function setImgAttribute(id,src,witdh="100",height="100")
{
    var elem = document.getElementById(id);
    elem.src    = src;
    elem.width  = witdh;
    elem.heigth =height;
}
function getDiv(id)
{
    var elem = document.createElement("div");
    elem.id = id;

    return elem;
}

function getLinkTitle(size,link,text)
{
    var a = document.createElement("a");
    a.href = link;
    a.appendChild(getTitle(size,"",text));
    console.log(a);
    return a;
}

function setCookie(cname, cvalue, exdays) {
    var userId = JSON.parse(sessionStorage.getItem('username')).id;
    var d = new Date();
    //d.setTime(d.getTime() + (exdays*24*60*60*1000));//days
    d.setTime(d.getTime() + (exdays*60*1000));// min
    var expires = "expires="+ d.toUTCString();

    document.cookie = cname+"-"+userId + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {

    var userId = JSON.parse(sessionStorage.getItem('username')).id;
    cname = cname + "-" + userId;

    var cookies = document.cookie.split(";").map(cookie => cookie.split("="));
    console.log(cookies);

    var out = null;
    var flag = false;
    cookies.forEach(c => {

        if(c[0].replace(/\s+/g, ' ').trim() === (cname))
        {
            if(!flag)
            {
                console.log("COOKIEEEE READ");
                console.log("Name :" + c[0] + "-> content: " + c[1]);
                out =  c[1];
                flag = true;
            }

        }
    });

    return out;
}

function sendJson(url,content)
{
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance
    xmlhttp.open("POST", url);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(content);
}