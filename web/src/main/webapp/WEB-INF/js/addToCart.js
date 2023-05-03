function doAjaxAddToCart(id){

    $(document).ready(function(){
        $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: "POST",
                url: "${pageContext.request.contextPath}/ajaxCart/",
                dataType: "json",
                data: JSON.stringify({
                    phoneId: id,
                    quantity: $("#phoneQuantity-" + id).val()
                }),
                processData: false,
                success: function (response){
                    $("#error-message-" + id).text("");
                    $("#success-message-" + id).text("Added successfully");
                    $("#totalQuantity").text("Cart: " + response.totalQuantity + " items");
                    $("#totalCost").text(response.totalCost + " $");
                },
                error: function (response){
                    $("#error-message-" + id).text(response.responseJSON.message);
                    $("#success-message-" + id).text("");
                }
            }

        )
    });
}