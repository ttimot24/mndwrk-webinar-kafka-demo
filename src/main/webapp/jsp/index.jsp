<!DOCTYPE html>
<!--
====================================
Copyright (C) 2020 Commsignia Ltd
http://commsignia.com
All rights reserved
------------------------------------
Date: Nov 10, 2020
Author: Timót Tarjáni <timot.tarjani@commsignia.com>
Project: MessageLake 
====================================
-->
<html>
    <head>
        <title>${buildInfo.getName()} - ${buildInfo.getVersion()}</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    </head>
    <body>
        <div class="container text-center p-5">
            <h1 class="font-weight-bold">${buildInfo.getName()}</h1>
            <h5><b>version:</b> ${buildInfo.getVersion()}</h5>
            <h5><b>built:</b> ${buildInfo.getTime()}</h5>
            <div>Up and Running</div>
            <br><br>
            <div><b>current depth:</b> ${depth}</div>
        </div>
    </body>
</html>
