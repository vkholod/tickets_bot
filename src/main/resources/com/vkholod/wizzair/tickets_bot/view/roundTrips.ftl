<#-- @ftlvariable name="" type="com.vkholod.wizzair.tickets_bot.view.RoundTripsView" -->
<html>
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    </head>
    <body>
        <table class="table table-striped table-responsive">
            <tr>
                <th></th>
                <th>Outbound date</th>
                <th>Return date</th>
                <th>Outbound price</th>
                <th>Return price</th>
                <th>Duration</th>
                <th>Total Price</th>
            </tr>
            <#list roundTrips as roundTrip>
            <tr>
                <td>${roundTrip?index + 1}</td>
                <td>${roundTrip.outboundFlight.humanReadableDepartureDateTime}</td>
                <td>${roundTrip.returnFlight.humanReadableDepartureDateTime}</td>
                <td>${roundTrip.outboundFlight.price.dollars}</td>
                <td>${roundTrip.returnFlight.price.dollars}</td>
                <td>${roundTrip.duration}</td>
                <td>${roundTrip.totalPrice.dollars}</td>
            </tr>
            </#list>
        </table>
    </body>
</html>