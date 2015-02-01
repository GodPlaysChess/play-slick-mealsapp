/**
 * Created by Gleb on 1/31/2015.
 */
$(document).ready(function () {
    $("#g-ok").on('click', function () {
        $(this).toggleClass("glyphicon-ok");
        $(this).toggleClass("glyphicon-remove");
    });
});

$(document).ready(function () {
    $("#meal-table").tablesorter({
        headers: {
            3: {sorter: false},
            4: {sorter: false}
        }

    });
});
/** add like call */
$(document).ready(function () {
    $(".plus-like").click(function () {
        /*   jsRoutes.controllers.Application.addDish().ajax();
         console.log("jquery called");*/
        var id = $(this).closest("tr").attr("database-id");
        
        var likeButton = $(this).find("span.glyphicon");
        var oldval = +likeButton.text().trim()
        $.ajax(jsRoutes.controllers.Application.addLike(+id))
            .done(function () {
                var newval = oldval + 1;
                likeButton.text(" " + newval + " ");
            })
            .fail(function () {
                console.log("failed")
            });
    });
});

$(document).ready(function () {
    $("#sort-button").on('click', function () {
        var icon = $(this).find("span");
        icon.toggleClass("glyphicon-chevron-up");
        icon.toggleClass("glyphicon-chevron-down");
        /* sorting */
        var ord = $(this).val();

        var newValue = ord == "asc" ? "desc" : "asc";
        console.log("value changed to " + newValue);
        $(this).val(newValue);
    });
});
