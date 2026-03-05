<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
                     <img id="logo" style="width: 128px;" src="img/totxollo.png"/>
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
            <div class="row">
                    <div class="col-sm-6 col-md-6" style="padding-bottom: 15px">
                        <div class="thumbnail">
                            <div class="caption">
                                <h3>Venda</h3>
                                <p>Heu de canviar la URL per aquest patró:</p>
                                <p>/vendaXollo/codi</p>
                                <p>Per exemple si voleu fer una venda pel vol 0 seria /vendaXollo/desti0
                                    <a href=" <spring:url value= "/vendaXollo/vol0" /> " class="btn btn-primary">
                                        <span class="${item.icon}"/></span> Comprar
                                    </a>
                                </p>
                            </div>
                        </div>
                    </div>                                
            </div>
        </section>                    
    </body>
</html>