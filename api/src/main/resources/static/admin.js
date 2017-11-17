var defaultTimeout = 10000;
var savedPassword;

$(document).ready(function () {
    $("#login-form").submit(function (event) {
        event.preventDefault();
        login();
    });

    $("#db-form").submit(function(event){
        event.preventDefault();
        saveDb();
    });

    $("#password-form").submit(function(event){
        event.preventDefault();
        generatePassword();
    });
});

function login(){
    savedPassword = $("#password").val();
    setEnabled("btn-login", false);
    var dataObject = {'password': savedPassword};
     $('#login-feedback').html("");
    $.ajax({
        type: "GET",
        ContentType: "application/json",
        url: "/system/db",
        data: dataObject,
        dataType: "json",
        cache: false,
        timeout: defaultTimeout,
        success: showDbData,
        error: showDbError
    });
};

function setEnabled(elementId, enabled){
    $("#"+elementId).prop("disabled", !enabled);
}

function setVisible(elementId, visible){
    if (visible) {
        $("#"+elementId).show();
    } else {
         $("#"+elementId).hide();
    }
}

$.fn.setClass = function(classes) {
    this.attr('class', classes);
    return this;
}

function showAlert(type, caption, message, errorCode){
setVisible(type, true);
var isError = (errorCode>=300);
    if (isError) {
    $('#'+type+"-feedback").setClass('alert alert-danger');
    } else{
    $('#'+type+"-feedback").setClass('alert alert-success');
    }
    var inHtml = "<h4>"+(isError?("Error! "+errorCode):"Success! ")+(caption==null?"":caption)+"</h4>";
    if (message!=null && message!=""){
    inHtml+="<pre>" + message+ "</pre>";
    }
    $('#'+type+'-feedback').html(inHtml);
}

function hideAlert(type){
    setVisible(type, false);
}

function showDbData(data){
    hideAlert('login');
    setDbDataFromJson(data);
    setEnabled("btn-login", true);
    setVisible("login-block", false);
    setVisible("db-block", true);
    setVisible("password-block", true);
}

function showDbError(e){
/*    var json = "<h4>Error "+e.status+"</h4>";
    if (e.responseText!=null && e.responseText!=""){
    json+="<pre>" + e.responseText + "</pre>";
    }
    $('#login-feedback').html(json);*/
    showAlert('login', null, e.responseText, e.status);
    setEnabled("btn-login", true);
}

function setDbDataFromJson(json){
    $('#db-dialect').val(json.dialect);
    $('#db-driver').val(json.driver);
    $('#db-password').val(json.password);
    $('#db-url').val(json.url);
    $('#db-username').val(json.user);
}

function saveDb(){
        setEnabled("btn-db", false);
        var dataObject = {'password': savedPassword,
        'dialect': $("#db-dialect").val(),
            'driver': $("#db-driver").val(),
            'dbpass': $("#db-password").val(),
            'url': $("#db-url").val(),
            'user': $("#db-username").val()};
        hideAlert('db');
        $.ajax({
            type: "POST",
            ContentType: "application/text",
            url: "/system/db",
            data: dataObject,
            cache: false,
            timeout: defaultTimeout,
            success: saveDbDataSuccess,
            error: saveDbDataError
        });
}

function saveDbDataSuccess(data){
    /*var json = "<h4>Success</h4>";
    $('#db-feedback').html(json);*/
    showAlert('db', null, null, 0);
    setEnabled("btn-db", true);
}

function saveDbDataError(e){
/*    var json = "<h4>Error "+e.status+"</h4><pre>" + e.responseText + "</pre>";
        $('#db-feedback').html(json);*/
    showAlert('db', null, e.responseText, e.status);
    setEnabled("btn-db", true);
}

function generatePassword(){
        setEnabled("btn-password", false);
        var dataObject = {'old': savedPassword};
        hideAlert('password');
        $.ajax({
            type: "GET",
            ContentType: "application/text",
            url: "/system/password",
            data: dataObject,
            cache: false,
            timeout: defaultTimeout,
            success: generatePasswordSuccess,
            error: generatePasswordError
        });
}

function generatePasswordSuccess(data){
    var temporaryPass = data;
     /*var json = "<h4>Success! Sending receive confirmation to server. New password: </h4><pre>" + temporaryPass + "</pre>";
    $('#password-feedback').html(json);*/
    showAlert('password', "Sending receive confirmation to server. New password:", temporaryPass, 0);
    setEnabled("btn-password", false);
    $.ajax({
            type: "POST",
            ContentType: "application/text",
            url: "/system/password",
            data: {'password': temporaryPass},
            cache: false,
            timeout: defaultTimeout,
            success: function(data){
                /*var json = "<h4>Success! New password: </h4><pre>" + temporaryPass + "</pre>";
                $('#password-feedback').html(json);*/
                showAlert('password', "New password: ", temporaryPass, 0);
                setEnabled("btn-password", true);
                savedPassword = temporaryPass;
            },
            error: generatePasswordError
        });
}

function generatePasswordError(e){
    /*var json = "<h4>Error "+e.status+"</h4><pre>" + + e.responseText + "</pre>";
    $('#password-feedback').html(json);*/
    showAlert('password', null, + e.responseText, e.status);
    setEnabled("btn-password", true);
}
