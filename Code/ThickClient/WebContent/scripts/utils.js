//TODO loading div for the first request
function makeCall(method, APIurl, formData, requestManagment, reset = true)
{
    var req = new XMLHttpRequest(); // visible by closure
    req.onreadystatechange = function() {
        requestManagment(req)
    };+ // closure
    req.open(method, APIurl);
    if (formData == null) {
        req.send();
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
        window.location.href = "login.html";
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

function addRow(col,rowNumber)
{
    for(let i=0;i<rowNumber;i++)
    {
        col.appendChild(getRow());
    }
}

function addCol(row,colNumber)
{
    for(let i=0;i<colNumber;i++)
    {
        row.appendChild(getCol());
    }
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