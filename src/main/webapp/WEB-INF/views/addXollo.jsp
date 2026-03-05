<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <!-- jQuery -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <title>Benvinguda</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-2">
                    <img id="logo" style="width: 128px;" src="../img/totxollo.png"/>
                </div>
                <div class="col-md-10">
                    <nav>
                        <ul class="nav nav-pills">
                            <li role="presentation" class="active">
                                <a href="<spring:url value= '/'/>">
                                    Inici
                                </a>
                            </li>
                        </ul>    
                    </nav>
                </div>
            </div>
        </div>

        <section>
            <div class="jumbotron">
                <div class="container">
                    <h1> ${banner} </h1>

                    <p> ${tagline} </p>
                </div>
            </div>
        </section>
        <section class="container">
            <form:form modelAttribute="newXollo" class="form-horizontal">
                <fieldset>
                    <legend>Afegir Xollo</legend>
                    <div class="form-group">
                        <label class="control-label col-lg-2 col-lg-2" for="codi">Codi</label>
                        <div class="col-lg-10">
                            <form:input id="codi" path="codi" type="text" class="form:input-large"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-2 col-lg-2" for="titol">Títol</label>
                        <div class="col-lg-10">
                            <form:input id="titol" path="titol" type="text" class="form:input-large"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-2 col-lg-2" for="numeroUnitats">Nombre d'unitats</label>
                        <div class="col-lg-10">
                            <form:input id="numeroUnitats" path="numeroUnitats" type="number" class="form:input-large"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-2 col-lg-2" for="numeroReserves">Número de reserves</label>
                        <div class="col-lg-10">
                            <form:input value="0" id="numeroReserves" path="numeroReserves" type="number" class="form:input-large" />
                        </div>
                    </div>                         
                    <div class="form-group">
                        <label class="control-label col-lg-2"
                               for="descripcio">Descripció</label>
                        <div class="col-lg-10">
                            <form:textarea id="descripcio" path="descripcio" rows ="2"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-offset-2 col-lg-10">
                            <input type="submit" id="btnAdd" class="btn btn-primary"
                                   value ="Crear"/>
                        </div>
                    </div>
                </fieldset>
            </form:form>
        </section>
    </body>
</html>