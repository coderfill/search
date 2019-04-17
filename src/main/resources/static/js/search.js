$(function() {

    $("#searchBtn").on("click", function() {
        let keyword = $("#search_input").val();
        $("#keyword").val(keyword);
        $("#search").submit();
    });

    $("#search_input").keyup(function(event){
        if(event.keyCode ==13){
            $(".searchBtn").trigger("click");
        }
    });

    $("[cmd='openDetail']").click(function(e) {
        let detailUrl = $(this).find("#detailUrl").html();
        alert(detailUrl);
    });
});