function makeCall(method, APIurl, formData, requestManagment, reset = true)
{
    var req = new XMLHttpRequest(); // visible by closure
    req.onreadystatechange = function() {
        requestManagment(req)
    }; // closure
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